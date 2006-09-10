/*
 * Created on May 8, 2006 by wyatt
 * 
 * A utility class which allows editing of transactions.
 */
package org.homeunix.drummer.view.components;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Date;
import java.util.Vector;

import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import org.homeunix.drummer.controller.Translate;
import org.homeunix.drummer.controller.TranslateKeys;
import org.homeunix.drummer.model.Category;
import org.homeunix.drummer.model.DataInstance;
import org.homeunix.drummer.model.Source;
import org.homeunix.drummer.model.Transaction;
import org.homeunix.drummer.prefs.DictData;
import org.homeunix.drummer.prefs.PrefsInstance;
import org.homeunix.drummer.util.Formatter;
import org.homeunix.drummer.util.Log;
import org.homeunix.drummer.view.TransactionsFrameLayout;
import org.homeunix.drummer.view.components.text.JDecimalField;
import org.homeunix.drummer.view.components.text.JHintAutoCompleteTextField;
import org.homeunix.drummer.view.components.text.JHintTextArea;
import org.homeunix.drummer.view.components.text.JHintTextField;

import com.toedter.calendar.JDateChooser;

public class EditableTransaction extends JPanel {
	public static final long serialVersionUID = 0;
	
	private final Vector<JComponent> components;

	private Transaction transaction; //Set when editing existing one; null otherwise
	
	private final JDateChooser date;
	private final JDecimalField amount;
	private final JScrollingComboBox from;
	private final JScrollingComboBox to;
	private final JHintTextField number;
	private final JHintAutoCompleteTextField description;
	private final JHintTextArea memo;
	private final JCheckBox cleared;
	private final JCheckBox reconciled;
	
	private final DefaultComboBoxModel toModel;
	private final DefaultComboBoxModel fromModel;
		
	private TransactionsFrameLayout parent;
	
	private boolean changed;
	
	public EditableTransaction(TransactionsFrameLayout parent){
		this.parent = parent;
				
		date = new JDateChooser(new Date(), PrefsInstance.getInstance().getPrefs().getDateFormat());
		amount = new JDecimalField(0, 5, Formatter.getInstance().getDecimalFormat());
		from = new JScrollingComboBox();
		to = new JScrollingComboBox();
		number = new JHintTextField(Translate.getInstance().get(TranslateKeys.DEFAULT_NUMBER));
		description = new JHintAutoCompleteTextField(PrefsInstance.getInstance().getDescDict(), Translate.getInstance().get(TranslateKeys.DEFAULT_DESCRIPTION));
		memo = new JHintTextArea(Translate.getInstance().get(TranslateKeys.DEFAULT_MEMO));
		cleared = new JCheckBox(Translate.getInstance().get(TranslateKeys.CLEARED_SHORT));
		reconciled = new JCheckBox(Translate.getInstance().get(TranslateKeys.RECONCILED_SHORT));
		
		components = new Vector<JComponent>();
		
		toModel = new DefaultComboBoxModel();
		fromModel = new DefaultComboBoxModel();
		
		to.setModel(toModel);
		from.setModel(fromModel);
		
		//Add the tooltips
		date.setToolTipText(Translate.getInstance().get(TranslateKeys.TOOLTIP_DATE));
		amount.setToolTipText(Translate.getInstance().get(TranslateKeys.TOOLTIP_AMOUNT));
		from.setToolTipText(Translate.getInstance().get(TranslateKeys.TOOLTIP_FROM));
		to.setToolTipText(Translate.getInstance().get(TranslateKeys.TOOLTIP_TO));
		number.setToolTipText(Translate.getInstance().get(TranslateKeys.TOOLTIP_NUMBER));
		description.setToolTipText(Translate.getInstance().get(TranslateKeys.TOOLTIP_DESC));
		memo.setToolTipText(Translate.getInstance().get(TranslateKeys.TOOLTIP_MEMO));
				
		JPanel topPanel = new JPanel();
		JPanel bottomPanel = new JPanel();
		topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));
		bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.X_AXIS));
						
		components.add(date);
		components.add(amount);
		components.add(from);
		components.add(to);

		components.add(number);
		components.add(description);
		components.add(memo);
		
		topPanel.add(date);
		topPanel.add(description);
		topPanel.add(number);
		if (PrefsInstance.getInstance().getPrefs().isShowAdvanced()){
			topPanel.add(cleared);
			topPanel.add(reconciled);
		}

		bottomPanel.add(amount);

		bottomPanel.add(from);
		bottomPanel.add(new JLabel(Translate.getInstance().get(TranslateKeys.TO)));
		bottomPanel.add(to);
		
		
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		mainPanel.add(topPanel);
		mainPanel.add(bottomPanel);
		
		JScrollPane memoScroller = new JScrollPane(memo);
		memoScroller.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		memoScroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		this.add(mainPanel);
		this.add(memoScroller);
		
		int textHeight = date.getPreferredSize().height;
		date.setPreferredSize(new Dimension(50, textHeight));
		description.setPreferredSize(new Dimension(100, textHeight));
		number.setPreferredSize(new Dimension(40, textHeight));
		
		amount.setMinimumSize(new Dimension(180, textHeight));
		amount.setMaximumSize(new Dimension(210, textHeight));
		from.setPreferredSize(new Dimension(100, from.getPreferredSize().height));
		to.setPreferredSize(from.getPreferredSize());
		
		memoScroller.setPreferredSize(new Dimension(100, memo.getPreferredSize().height));
		
		if (parent == null)
			date.setVisible(false);
		
		initActions();
	}
	
	public void setTransaction(Transaction transaction, boolean force){
		//If the new transaction is not the same as the old one, then we 
		// want to reset the form.  Since this can include nulls, we need
		// to consider a few cases.
		// 1) Both are null - do nothing
		// 2) Both are not null - check the equals() method:
		//   a) Both are equal - do nothing
		//   b) Both are not equal - update
		// 3) One is null, the other is not - update.
		if (!force && this.transaction == null && transaction == null)
			return;
		if (!force && this.transaction != null && this.transaction.equals(transaction))
			return;
		if (transaction != null){
			date.setDate(transaction.getDate());			
			number.setValue(transaction.getNumber());
			description.setValue(transaction.getDescription());
			memo.setValue(transaction.getMemo());
			amount.setValue(transaction.getAmount());
			from.setSelectedItem(transaction.getFrom());
			to.setSelectedItem(transaction.getTo());
			cleared.setSelected(transaction.isCleared());
			reconciled.setSelected(transaction.isReconciled());
//			TODO Remove commented section
//			Log.debug("Set to: " + transaction.getTo());
//			Log.debug("Set from: " + transaction.getFrom());
//			Log.debug("Selected to: " + to.getSelectedItem());
//			Log.debug("Selected from: " + from.getSelectedItem());
		}
		else{
			if (date.getDate() == null)
				date.setDate(new Date());
			number.setValue("");
			description.setValue("");
			amount.setValue(0);
			to.setSelectedIndex(0);
			from.setSelectedIndex(0);
			memo.setValue("");
			cleared.setSelected(false);
			reconciled.setSelected(false);
			
			resetSelection();
		}
		
		setChanged(false);
		
		this.transaction = transaction;
	}
			
	public boolean isChanged(){
		return changed;
//				|| transaction == null
//				|| (!transaction.getDescription().equals(getDescription()))
//				|| (!transaction.getNumber().equals(getNumber()))
//				|| (transaction.getAmount() != (getAmount()))
//				|| (!transaction.getFrom().equals(getTransferFrom()))
//				|| (!transaction.getTo().equals(getTransferTo()))
//				|| (!transaction.getDate().equals(getDate()))
//		);
	}
	
	public void setChanged(boolean changed){
		this.changed = changed;
	}
	
	public Date getDate(){
		return date.getDate();
	}
	
	public String getNumber(){
		return number.getValue();
	}
	
	public String getDescription(){
		return description.getValue();
	}
	
	public Transaction getTransaction(){
		return transaction;
	}
	
	public long getAmount(){
		if (this.amount != null)
			return this.amount.getValue();
		else
			return 0;
	}
	
	public Source getFrom(){
		if (from.getSelectedItem() instanceof Source) {
			return (Source) from.getSelectedItem();	
		}
		else if (from.getSelectedItem() != null)
			Log.error("Unknown object selected in TransferFrom combobox; returning null.");
		return null;
	}

	public Source getTo(){
		if (to.getSelectedItem() instanceof Source) {
			return (Source) to.getSelectedItem();	
		}
		else if (to.getSelectedItem() != null)
			Log.error("Unknown object selected in TransferTo combobox; returning null.");
		
		return null;
	}

	
	public String getMemo(){
		return memo.getValue();
	}
	
	public boolean isCleared(){
		return cleared.isSelected();
	}
	
	public boolean isReconciled(){
		return reconciled.isSelected();
	}
	
	public void updateContent(){
		//Update the dropdown lists
		setEnabled(true);
		
		toModel.removeAllElements();
		fromModel.removeAllElements();
		toModel.addElement(null);
		fromModel.addElement(null);
		for (Source source : DataInstance.getInstance().getAccounts()) {
			toModel.addElement(source);
			fromModel.addElement(source);
		}
		
		toModel.addElement(null);
		fromModel.addElement(null);		
		
		for (Category c : DataInstance.getInstance().getCategories()){
			if (c.isIncome())
				fromModel.addElement(c);
			else
				toModel.addElement(c);	
		}
	}
	
	private void initActions(){
		for (JComponent c : components) {
			c.addKeyListener(new KeyAdapter(){
				public void keyTyped(KeyEvent arg0) {
					EditableTransaction.this.setChanged(true);
					super.keyTyped(arg0);
				}
			});
			
			c.addFocusListener(new FocusAdapter(){
				public void focusGained(FocusEvent arg0) {
					if (arg0.getSource() instanceof JTextField 
							|| arg0.getSource() instanceof JFormattedTextField){
						JTextField text = (JTextField) arg0.getSource();
						text.selectAll();
					}
				}
			});
		}
		
		description.addFocusListener(new FocusAdapter(){
			@Override
			public void focusLost(FocusEvent arg0) {
				//If we loose focus on the description field, we check if
				// there is something there.  If so, we fill in others with
				// the dictionary map.
				if (PrefsInstance.getInstance().getPrefs().isShowAutoComplete()){
					if (description.getValue().length() > 0){
						DictData dd = PrefsInstance.getInstance().getAutoCompleteEntry(description.getText());
						if (dd != null){
							if (dd.getNumber() != null)
								number.setValue(dd.getNumber());
							amount.setValue(dd.getAmount());
							if (dd.getMemo() != null)
								memo.setValue(dd.getMemo());

							//TODO This doesn't always work when you go to a different account...
							if (dd.getFrom() != null){
								for (int i = 0; i < from.getModel().getSize(); i++){
									if (from.getModel().getElementAt(i) != null
											&& from.getModel().getElementAt(i).toString().equals(dd.getFrom())){
										from.setSelectedIndex(i);
										break;
									}
								}
							}
							if (dd.getTo() != null){
								for (int i = 0; i < to.getModel().getSize(); i++){
									if (to.getModel().getElementAt(i) != null
											&& to.getModel().getElementAt(i).toString().equals(dd.getTo())){
										to.setSelectedIndex(i);
										break;
									}
								}
							}
						}
					}
				}
			}
		});
		
		from.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
//				EditableTransaction.this.setChanged(true);
				if (parent != null){
					if (parent.getAccount() != null) {
						if (!parent.getAccount().equals(from.getSelectedItem())){
							to.setSelectedItem(parent.getAccount());
						}

						if (parent.getAccount().equals(from.getSelectedItem())
								&& parent.getAccount().equals(to.getSelectedItem())){
							to.setSelectedItem(null);
						}
					}
				}
			}
		});
		
		to.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
//				EditableTransaction.this.setChanged(true);
				if (parent != null){
					if (to.getSelectedItem() instanceof Source) {
						if (!parent.getAccount().equals(to.getSelectedItem())){
							from.setSelectedItem(parent.getAccount());
						}

						if (parent.getAccount().equals(from.getSelectedItem())
								&& parent.getAccount().equals(to.getSelectedItem())){
							from.setSelectedItem(null);
						}
					}
				}
			}
		});
	}

	@Override
	public void setEnabled(boolean arg0) {
		for (JComponent c : components) {
			c.setEnabled(arg0);
		}
	}
	
	public void resetSelection(){
		date.requestFocusInWindow();
	}
}
