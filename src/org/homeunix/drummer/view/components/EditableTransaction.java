/*
 * Created on May 8, 2006 by wyatt
 * 
 * A utility class which allows editing of transactions.
 */
package org.homeunix.drummer.view.components;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Date;
import java.util.Vector;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import javax.swing.ScrollPaneConstants;
import javax.swing.text.JTextComponent;

import org.homeunix.drummer.controller.AutoCompleteController;
import org.homeunix.drummer.controller.SourceController;
import org.homeunix.drummer.controller.Translate;
import org.homeunix.drummer.controller.TranslateKeys;
import org.homeunix.drummer.model.Category;
import org.homeunix.drummer.model.Source;
import org.homeunix.drummer.model.Transaction;
import org.homeunix.drummer.prefs.PrefsInstance;
import org.homeunix.drummer.view.TransactionsFrame;
import org.homeunix.drummer.view.model.AutoCompleteEntry;
import org.homeunix.thecave.moss.gui.JScrollingComboBox;
import org.homeunix.thecave.moss.gui.formatted.JDecimalField;
import org.homeunix.thecave.moss.gui.hint.JHintComboBox;
import org.homeunix.thecave.moss.gui.hint.JHintTextArea;
import org.homeunix.thecave.moss.gui.hint.JHintTextField;
import org.homeunix.thecave.moss.util.Log;
import org.homeunix.thecave.moss.util.OperatingSystemUtil;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

import com.toedter.calendar.JDateChooser;

/**
 * The transaction editing pane, which includes text fields to enter
 * date, descriptions, number, amount, etc.  Currently used in 
 * TransactionFrame and ScheduledTransaction.
 * @author wyatt
 *
 */
public class EditableTransaction extends JPanel {
	public static final long serialVersionUID = 0;

	private final Vector<JComponent> components;

	private Transaction transaction; //Set when editing existing one; null otherwise

	private final JDateChooser date;
	private final JDecimalField amount;
	private final JScrollingComboBox from;
	private final JScrollingComboBox to;
	private final JHintTextField number;
	private final JHintComboBox description;
	private final JHintTextArea memo;
	private final JCheckBox cleared;
	private final JCheckBox reconciled;

	private final DefaultComboBoxModel toModel;
	private final DefaultComboBoxModel fromModel;

	private TransactionsFrame parent;

	private boolean changed;

	public EditableTransaction(TransactionsFrame parent){
		this.parent = parent;

		date = new JDateChooser(new Date(), PrefsInstance.getInstance().getPrefs().getDateFormat());
		amount = new JDecimalField();
		from = new JScrollingComboBox();
		to = new JScrollingComboBox();
		number = new JHintTextField(Translate.getInstance().get(TranslateKeys.HINT_NUMBER));
		description = new JHintComboBox(AutoCompleteController.getAutoCompleteEntries(), Translate.getInstance().get(TranslateKeys.HINT_DESCRIPTION));
		memo = new JHintTextArea(Translate.getInstance().get(TranslateKeys.HINT_MEMO));
		cleared = new JCheckBox(Translate.getInstance().get(TranslateKeys.SHORT_CLEARED));
		reconciled = new JCheckBox(Translate.getInstance().get(TranslateKeys.SHORT_RECONCILED));

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
		cleared.setToolTipText(Translate.getInstance().get(TranslateKeys.TOOLTIP_CLEARED));
		reconciled.setToolTipText(Translate.getInstance().get(TranslateKeys.TOOLTIP_RECONCILED));

		JPanel topPanel = new JPanel();
		JPanel bottomPanel = new JPanel();
		topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));
		bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.X_AXIS));

		JScrollPane memoScroller = new JScrollPane(memo);
		memo.setWrapStyleWord(true);
		memo.setLineWrap(true);
		memoScroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		memoScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

		components.add(date);
		components.add(amount);
		components.add(from);
		components.add(to);

		components.add(number);
		components.add(description);
		components.add(memo);
		components.add(memoScroller);

		int textHeight = date.getPreferredSize().height;
//		date.setPreferredSize(new Dimension(250, textHeight));
		date.setMaximumSize(new Dimension(300, textHeight));
//		description.setPreferredSize(new Dimension(250, description.getPreferredSize().height));
		description.setMinimumSize(new Dimension(150, description.getPreferredSize().height));
//		number.setPreferredSize(new Dimension(100, textHeight));

		amount.setMaximumSize(new Dimension(150, textHeight));
		amount.setPreferredSize(new Dimension(150, textHeight));
		from.setPreferredSize(new Dimension(100, from.getPreferredSize().height));
		to.setPreferredSize(from.getPreferredSize());

		memoScroller.setPreferredSize(new Dimension(100, memo.getPreferredSize().height));		

		topPanel.add(date);
		if (!OperatingSystemUtil.isMac()) topPanel.add(Box.createHorizontalStrut(3));
		topPanel.add(description);
		if (!OperatingSystemUtil.isMac()) topPanel.add(Box.createHorizontalStrut(3));
		topPanel.add(number);
		if (PrefsInstance.getInstance().getPrefs().isShowAdvanced()){
			if (!OperatingSystemUtil.isMac()) topPanel.add(Box.createHorizontalStrut(3));
			topPanel.add(cleared);
			if (!OperatingSystemUtil.isMac()) topPanel.add(Box.createHorizontalStrut(3));
			topPanel.add(reconciled);
		}

		bottomPanel.add(amount);
		if (!OperatingSystemUtil.isMac()) bottomPanel.add(Box.createHorizontalStrut(3));
		bottomPanel.add(from);
		if (!OperatingSystemUtil.isMac()) bottomPanel.add(Box.createHorizontalStrut(3));
		bottomPanel.add(new JLabel(Translate.getInstance().get(TranslateKeys.TO)));
		if (!OperatingSystemUtil.isMac()) bottomPanel.add(Box.createHorizontalStrut(3));
		bottomPanel.add(to);

		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		mainPanel.add(topPanel);
		if (!OperatingSystemUtil.isMac()) mainPanel.add(Box.createVerticalStrut(3));
		mainPanel.add(bottomPanel);

		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		this.add(mainPanel);
		if (!OperatingSystemUtil.isMac()) this.add(Box.createHorizontalStrut(3));
		this.add(memoScroller);

		if (parent == null)
			date.setVisible(false);

		updateContent();
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
//			if (Const.DEVEL) Log.debug("Set to: " + transaction.getTo());
//			if (Const.DEVEL) Log.debug("Set from: " + transaction.getFrom());
//			if (Const.DEVEL) Log.debug("Selected to: " + to.getSelectedItem());
//			if (Const.DEVEL) Log.debug("Selected from: " + from.getSelectedItem());
		}
		else{
			if (date.getDate() == null)
				date.setDate(new Date());
			number.setValue("");
			description.setValue(null);
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
//		|| transaction == null
//		|| (!transaction.getDescription().equals(getDescription()))
//		|| (!transaction.getNumber().equals(getNumber()))
//		|| (transaction.getAmount() != (getAmount()))
//		|| (!transaction.getFrom().equals(getTransferFrom()))
//		|| (!transaction.getTo().equals(getTransferTo()))
//		|| (!transaction.getDate().equals(getDate()))
//		);
	}

	/**
	 * @return a boolean value indicating whether it's likely that this transaction was
	 *  inadvertently edited by the user when they were trying to create a new transaction instead.
	 */
	public boolean isDangerouslyChanged() {
		if (transaction == null) 
			return false;
		boolean changedDesc = !(getDescription().equals(transaction.getDescription()));
		boolean changedAmt  = !(getAmount() == transaction.getAmount());
		boolean changedDate = !(getDate().equals(transaction.getDate()));
		return changedDesc && changedAmt && changedDate;
	}

	public void setChanged(boolean changed){
		this.changed = changed;
	}

	public Date getDate(){
		return date.getDate();
	}

	public String getNumber(){
		return number.getValue().toString();
	}

	public String getDescription(){
		return description.getSelectedItem().toString();
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
		return memo.getValue().toString();
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
		for (Source source : SourceController.getAccounts()) {
			toModel.addElement(source);
			fromModel.addElement(source);
		}

		toModel.addElement(null);
		fromModel.addElement(null);		

		for (Category c : SourceController.getCategories()){
			if (c.isIncome())
				fromModel.addElement(c);
			else
				toModel.addElement(c);	
		}
	}

	private void initActions(){				
		description.setEditable(true);
		AutoCompleteDecorator.decorate(description);

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
		
		description.addItemListener(new ItemListener(){
			public void itemStateChanged(ItemEvent arg0) {
				fillInOtherFields(true);
//				description.setPopupVisible(false);
			}
		});

		// Load the other information (amount, number, etc) from memory
		((JTextComponent) description.getEditor().getEditorComponent()).addFocusListener(new FocusAdapter(){
			@Override
			public void focusLost(FocusEvent arg0) {
				fillInOtherFields(false);
				description.setPopupVisible(false);
			}
			
			@Override
			public void focusGained(FocusEvent e) {
				super.focusGained(e);
			}
		});

		((JTextComponent) description.getEditor().getEditorComponent()).addKeyListener(new KeyAdapter(){
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER){
					fillInOtherFields(true);
				}
				super.keyPressed(e);
			}
		});

		// When you select one source, automatically select the other if possible
		from.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
//				EditableTransaction.this.setChanged(true);
//				if (parent != null && parent.getAccount() != null){
				if (from.getSelectedItem() instanceof Source) {
					if (parent != null && parent.getAccount() != null){
						if (!parent.getAccount().equals(from.getSelectedItem())){
							to.setSelectedItem(parent.getAccount());
						}

						if (parent.getAccount().equals(from.getSelectedItem())
								&& parent.getAccount().equals(to.getSelectedItem())){
							to.setSelectedItem(null);
						}
					}
					else {
						if (from.getSelectedItem() instanceof Category){
							if (to.getSelectedItem() instanceof Category){
								to.setSelectedItem(null);
							}
						}
						else if (from.getSelectedItem() != null
								&& to.getSelectedItem() != null
								&& from.getSelectedItem().equals(to.getSelectedItem())){
							to.setSelectedItem(null);
						}
					}
				}
			}
		});

		// When you select one source, automatically select the other if possible
		to.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
//				EditableTransaction.this.setChanged(true);
				if (to.getSelectedItem() instanceof Source) {
					if (parent != null && parent.getAccount() != null){
						if (!parent.getAccount().equals(to.getSelectedItem())){
							from.setSelectedItem(parent.getAccount());
						}

						if (parent.getAccount().equals(from.getSelectedItem())
								&& parent.getAccount().equals(to.getSelectedItem())){
							from.setSelectedItem(null);
						}
					}
					else {
						if (to.getSelectedItem() instanceof Category){
							if (from.getSelectedItem() instanceof Category){
								from.setSelectedItem(null);
							}
						}
						else if (from.getSelectedItem() != null
								&& to.getSelectedItem() != null
								&& from.getSelectedItem().equals(to.getSelectedItem())){
							from.setSelectedItem(null);
						}
					}

				}
			}
		});

		reconciled.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				EditableTransaction.this.setChanged(true);
			}
		});

		cleared.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				EditableTransaction.this.setChanged(true);
			}
		});

		ListCellRenderer renderer = new DefaultListCellRenderer(){
			public static final long serialVersionUID = 0;

			@Override
			public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
				super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
				if (value == null)
//					setText("\u2014");
					this.setText(" ");
				else if (value instanceof Source){
					if (((Source) value).isDeleted())
						this.setText("<html><s>" + value + "</s></html>");
					else
						this.setText(value.toString());
				}

				return this;
			}
		};

		to.setRenderer(renderer);
		from.setRenderer(renderer);

//		ListCellRenderer descriptionRenderer = new DefaultListCellRenderer(){
//			public static final long serialVersionUID = 0;
//
//			@Override
//			public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
//				super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
//				if (value == null)
//					this.setText(Translate.getInstance().get(TranslateKeys.HINT_DESCRIPTION));
//				else
//					this.setText(value.toString());
//
//				return this;
//			}
//		};
//
//		description.setRenderer(descriptionRenderer);

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

	/**
	 * Forces the Cleared and reconciled boxes to update to the current
	 * values as stored in the model.  Should only be used for the 
	 * Clear / Reconcile shortcuts. 
	 */
	public void updateClearedAndReconciled(){
		cleared.setSelected(transaction.isCleared());
		reconciled.setSelected(transaction.isReconciled());
	}

	private void fillInOtherFields(boolean forceAll){
		//If we lose focus on the description field, we check if
		// there is something there.  If so, we fill in others with
		// the dictionary map, but only if they haven't been modified from their default values!
		if (PrefsInstance.getInstance().getPrefs().isShowAutoComplete()){
			if (description != null 
					&& description.getSelectedItem() != null 
					&& description.getSelectedItem().toString().length() > 0){
				AutoCompleteEntry ace = AutoCompleteController.getAutoCompleteEntry(description.getSelectedItem().toString());
				if (ace != null){
					if (forceAll || (ace.getNumber() != null && number.isHintShowing()))
						number.setValue(ace.getNumber());
					if (forceAll || amount.getValue() == 0)
						amount.setValue(ace.getAmount());

					//We don't do the memos any more, as this tends to be unique to one transaction.
//					if (forceAll || (asi.getMemo() != null && memo.isHintShowing()))
//						memo.setValue(asi.getMemo());

					//This doesn't always work when you go to a different account; it should
					// be good enough for the vast majorty of cases, though, and does a pretty
					// good job at guessing which account is the correct one...
					if (ace.getFrom() != null){
						for (int i = 0; i < from.getModel().getSize(); i++){
							if (from.getModel().getElementAt(i) != null
									&& from.getModel().getElementAt(i).toString().equals(ace.getFrom())){
								from.setSelectedIndex(i);
								break;
							}
						}
					}
					if (ace.getTo() != null){
						for (int i = 0; i < to.getModel().getSize(); i++){
							if (to.getModel().getElementAt(i) != null
									&& to.getModel().getElementAt(i).toString().equals(ace.getTo())){
								to.setSelectedIndex(i);
								break;
							}
						}
					}
				}
				else {
					number.setValue("");
					amount.setValue(0);
					memo.setValue("");
					from.setSelectedItem(null);
					to.setSelectedItem(null);
				}
			}
		}
	}
}
