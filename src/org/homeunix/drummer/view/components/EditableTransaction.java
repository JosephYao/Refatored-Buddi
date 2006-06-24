/*
 * Created on May 8, 2006 by wyatt
 * 
 * A utility class which allows editing of transactions.
 */
package org.homeunix.drummer.view.components;

import java.awt.Color;
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
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.JTextComponent;

import org.homeunix.drummer.Translate;
import org.homeunix.drummer.TranslateKeys;
import org.homeunix.drummer.controller.DataInstance;
import org.homeunix.drummer.controller.PrefsInstance;
import org.homeunix.drummer.model.Category;
import org.homeunix.drummer.model.Source;
import org.homeunix.drummer.model.Transaction;
import org.homeunix.drummer.prefs.DictData;
import org.homeunix.drummer.util.Formatter;
import org.homeunix.drummer.util.Log;
import org.homeunix.drummer.view.components.autocomplete.AutoCompleteTextField;
import org.homeunix.drummer.view.layout.TransactionsFrameLayout;

import com.toedter.calendar.JDateChooser;

public class EditableTransaction extends JPanel {
	public static final long serialVersionUID = 0;
	
	private final Vector<JComponent> components;

	private Transaction transaction; //Set when editing existing one; null otherwise
	
	private final JDateChooser date;
	private final JDecimalField amount;
	private final JComboBox from;
	private final JComboBox to;
	private final JTextField number;
	private final AutoCompleteTextField description;
	private final JTextArea memo;
	
	private final DefaultComboBoxModel toModel;
	private final DefaultComboBoxModel fromModel;
		
	private TransactionsFrameLayout parent;
	
	private boolean changed;
	
	public EditableTransaction(TransactionsFrameLayout parent){
		this.parent = parent;
				
		date = new JDateChooser(new Date(), PrefsInstance.getInstance().getPrefs().getDateFormat());
		amount = new JDecimalField(0, 5, Formatter.getInstance().getDecimalFormat());
		from = new JComboBox();
		to = new JComboBox();
		number = new JTextField();
		description = new AutoCompleteTextField(PrefsInstance.getInstance().getDescDict());
		memo = new JTextArea();
		
		components = new Vector<JComponent>();
		
		toModel = new DefaultComboBoxModel();
		fromModel = new DefaultComboBoxModel();
		
		to.setModel(toModel);
		from.setModel(fromModel);
		
		//Add the tooltips
		date.setToolTipText(Translate.inst().get(TranslateKeys.TOOLTIP_DATE));
		amount.setToolTipText(Translate.inst().get(TranslateKeys.TOOLTIP_AMOUNT));
		from.setToolTipText(Translate.inst().get(TranslateKeys.TOOLTIP_FROM));
		to.setToolTipText(Translate.inst().get(TranslateKeys.TOOLTIP_TO));
		number.setToolTipText(Translate.inst().get(TranslateKeys.TOOLTIP_NUMBER));
		description.setToolTipText(Translate.inst().get(TranslateKeys.TOOLTIP_DESC));
		memo.setToolTipText(Translate.inst().get(TranslateKeys.TOOLTIP_MEMO));
		
		//Add the default text
		addHintListeners(description, Translate.inst().get(TranslateKeys.DEFAULT_DESCRIPTION));
		addHintListeners(number, Translate.inst().get(TranslateKeys.DEFAULT_NUMBER));
		addHintListeners(memo, Translate.inst().get(TranslateKeys.DEFAULT_MEMO));
		
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

		bottomPanel.add(amount);
		
		bottomPanel.add(from);
		bottomPanel.add(new JLabel(Translate.inst().get(TranslateKeys.TO)));
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
			number.setText(transaction.getNumber());
			description.setText(transaction.getDescription());
			memo.setText(transaction.getMemo());
			amount.setValue(transaction.getAmount());
			from.setSelectedItem(transaction.getFrom());
			to.setSelectedItem(transaction.getTo());
		}
		else{
			if (date.getDate() == null)
				date.setDate(new Date());
			number.setText("");
			description.setText("");
			amount.setValue(0);
			to.setSelectedIndex(0);
			from.setSelectedIndex(0);
			memo.setText("");
			date.requestFocus();
			setChanged(false);
		}
		
		setDefaultText(true, description, Translate.inst().get(TranslateKeys.DEFAULT_DESCRIPTION));
		setDefaultText(true, number, Translate.inst().get(TranslateKeys.DEFAULT_NUMBER));
		setDefaultText(true, memo, Translate.inst().get(TranslateKeys.DEFAULT_MEMO));
		
		setChanged(false);
		
		this.transaction = transaction;
	}
			
	public boolean isChanged(){
		return (changed == true);
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
		if (number.getForeground().equals(Color.GRAY))
			return "";
		else
			return number.getText();
	}
	
	public String getDescription(){
		if (description.getForeground().equals(Color.GRAY))
			return "";
		else
			return description.getText();
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
		if (memo.getForeground().equals(Color.GRAY))
			return "";
		else
			return memo.getText();
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
				if (description.getText().length() > 0 
						&& description.getForeground().equals(Color.BLACK)){
					DictData dd = PrefsInstance.getInstance().getAutoCompleteEntry(description.getText());
					if (dd != null){
						if (dd.getNumber() != null){
							number.setText(dd.getNumber());
							number.setForeground(Color.BLACK);
						}
						amount.setValue(dd.getAmount());
						if (dd.getMemo() != null){
							memo.setText(dd.getMemo());
							memo.setForeground(Color.BLACK);
						}
						
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
		});
		
		from.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
//				EditableTransaction.this.setChanged(true);
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
		});
		
		to.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
//				EditableTransaction.this.setChanged(true);
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
		});
	}

	@Override
	public void setEnabled(boolean arg0) {
		for (JComponent c : components) {
			c.setEnabled(arg0);
		}
	}
	
	public void select(){
		date.requestFocusInWindow();
	}
	
	private void addHintListeners(final JTextComponent c, final String hint){
		c.setText(hint);
		c.setForeground(Color.GRAY);
		c.addFocusListener(new FocusAdapter(){
			@Override
			public void focusGained(FocusEvent arg0) {
				setDefaultText(false, c, hint);
				super.focusGained(arg0);
			}

			@Override
			public void focusLost(FocusEvent arg0) {
				setDefaultText(true, c, hint);
				super.focusLost(arg0);
			}
		});
	}
	
	private void setDefaultText(boolean on, JTextComponent c, String hint){
		if (on){
			if (c.getText().equals("")){
				c.setText(hint);
				c.setForeground(Color.GRAY);
			}
			else{
				c.setForeground(Color.BLACK);
			}
		}
		else{
			if (c.getText().equals(hint)){
				c.setText("");
			}
			c.setForeground(Color.BLACK);			
		}
	}
}
