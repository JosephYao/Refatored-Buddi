/*
 * Created on May 8, 2006 by wyatt
 * 
 * A utility class which allows editing of transactions.
 */
package org.homeunix.thecave.buddi.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.text.JTextComponent;

import org.homeunix.thecave.buddi.i18n.BuddiKeys;
import org.homeunix.thecave.buddi.model.Account;
import org.homeunix.thecave.buddi.model.BudgetCategory;
import org.homeunix.thecave.buddi.model.DataModel;
import org.homeunix.thecave.buddi.model.Source;
import org.homeunix.thecave.buddi.model.Transaction;
import org.homeunix.thecave.buddi.model.prefs.PrefsModel;
import org.homeunix.thecave.buddi.model.swing.AutoCompleteComboBoxModel;
import org.homeunix.thecave.buddi.model.swing.AutoCompleteEntryModel;
import org.homeunix.thecave.buddi.model.swing.SourceComboBoxModel;
import org.homeunix.thecave.buddi.model.swing.AutoCompleteEntryModel.AutoCompleteEntry;
import org.homeunix.thecave.buddi.view.swing.MaxLengthListCellRenderer;
import org.homeunix.thecave.buddi.view.swing.SourceListCellRenderer;
import org.homeunix.thecave.moss.swing.components.JScrollingComboBox;
import org.homeunix.thecave.moss.swing.formatted.JDecimalField;
import org.homeunix.thecave.moss.swing.hint.JHintComboBox;
import org.homeunix.thecave.moss.swing.hint.JHintTextArea;
import org.homeunix.thecave.moss.swing.hint.JHintTextField;
import org.homeunix.thecave.moss.swing.window.MossPanel;
import org.homeunix.thecave.moss.util.Log;
import org.homeunix.thecave.moss.util.OperatingSystemUtil;
import org.jdesktop.swingx.JXDatePicker;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

/**
 * The transaction editing pane, which includes text fields to enter
 * date, descriptions, number, amount, etc.  Currently used in 
 * TransactionFrame and ScheduledTransaction.
 * @author wyatt
 *
 */
public class TransactionEditor extends MossPanel {
	public static final long serialVersionUID = 0;

	private final Vector<JComponent> components;

	private Transaction transaction; //Set when editing existing one; null otherwise

	private final JXDatePicker date;
	private final JHintComboBox description;
	private final JHintTextField number;
	private final JDecimalField amount;
	private final JScrollingComboBox from;
	private final JScrollingComboBox to;
	private final JCheckBox cleared;
	private final JCheckBox reconciled;
	private final JHintTextArea memo;

	private final DataModel model;
	private final Account associatedAccount;
	
	private final AutoCompleteEntryModel autoCompleteEntries;

	private boolean changed;

	public TransactionEditor(DataModel model, Account associatedAccount, boolean scheduledTransactionPane){
		super(true);
		this.model = model;
		this.associatedAccount = associatedAccount;
		
		autoCompleteEntries = new AutoCompleteEntryModel(model);

		date = new JXDatePicker();
		amount = new JDecimalField();
		from = new JScrollingComboBox();
		to = new JScrollingComboBox();
		number = new JHintTextField(PrefsModel.getInstance().getTranslator().get(BuddiKeys.HINT_NUMBER));
		description = new JHintComboBox(new AutoCompleteComboBoxModel(this.model), PrefsModel.getInstance().getTranslator().get(BuddiKeys.HINT_DESCRIPTION));
		memo = new JHintTextArea(PrefsModel.getInstance().getTranslator().get(BuddiKeys.HINT_MEMO));
		cleared = new JCheckBox(PrefsModel.getInstance().getTranslator().get(BuddiKeys.SHORT_CLEARED));
		reconciled = new JCheckBox(PrefsModel.getInstance().getTranslator().get(BuddiKeys.SHORT_RECONCILED));

		components = new Vector<JComponent>();
		
		date.setVisible(!scheduledTransactionPane);

		open();
	}

	@Override
	public void init() {
		super.init();

		from.setModel(new SourceComboBoxModel(this.model, true));
		to.setModel(new SourceComboBoxModel(this.model, false));

		from.setRenderer(new SourceListCellRenderer());
		to.setRenderer(new SourceListCellRenderer());

		from.setSelectedItem(null);
		to.setSelectedItem(null);
		
		date.setEditor(new JFormattedTextField(new SimpleDateFormat(PrefsModel.getInstance().getDateFormat())));
		date.setDate(new Date());

		//Add the tooltips
		date.setToolTipText(PrefsModel.getInstance().getTranslator().get(BuddiKeys.TOOLTIP_DATE));
		amount.setToolTipText(PrefsModel.getInstance().getTranslator().get(BuddiKeys.TOOLTIP_AMOUNT));
		from.setToolTipText(PrefsModel.getInstance().getTranslator().get(BuddiKeys.TOOLTIP_FROM));
		to.setToolTipText(PrefsModel.getInstance().getTranslator().get(BuddiKeys.TOOLTIP_TO));
		number.setToolTipText(PrefsModel.getInstance().getTranslator().get(BuddiKeys.TOOLTIP_NUMBER));
		description.setToolTipText(PrefsModel.getInstance().getTranslator().get(BuddiKeys.TOOLTIP_DESC));
		memo.setToolTipText(PrefsModel.getInstance().getTranslator().get(BuddiKeys.TOOLTIP_MEMO));
		cleared.setToolTipText(PrefsModel.getInstance().getTranslator().get(BuddiKeys.TOOLTIP_CLEARED));
		reconciled.setToolTipText(PrefsModel.getInstance().getTranslator().get(BuddiKeys.TOOLTIP_RECONCILED));

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

		memoScroller.setPreferredSize(new Dimension(130, memo.getPreferredSize().height));		

		JPanel topPanel = new JPanel(new GridBagLayout());
		JPanel bottomPanel = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		c.weighty = 0;
		c.gridy = 0;
		c.fill = GridBagConstraints.HORIZONTAL;

		c.weightx = 0.2;
		c.gridx = 0;
		topPanel.add(date, c);

		c.weightx = 0.5;
		c.gridx = 1;
		topPanel.add(description, c);

		c.weightx = 0.4;
		c.gridx = 2;
		topPanel.add(number, c);

		if (PrefsModel.getInstance().isShowCleared()){
			c.weightx = 0.0;
			c.gridx = 3;
			topPanel.add(cleared, c);
		}
		if (PrefsModel.getInstance().isShowReconciled()){	
			c.weightx = 0.0;
			c.gridx = 4;
			topPanel.add(reconciled, c);
		}


		c.weightx = 0.8;
		c.gridx = 0;
		bottomPanel.add(amount, c);

		c.weightx = 0.5;
		c.gridx = 1;
		bottomPanel.add(from, c);

		c.weightx = 0.0;
		c.gridx = 2;
		bottomPanel.add(new JLabel(PrefsModel.getInstance().getTranslator().get(BuddiKeys.TO)), c);

		c.weightx = 0.5;
		c.gridx = 3;
		bottomPanel.add(to, c);


		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
		centerPanel.add(topPanel);
		if (!OperatingSystemUtil.isMac()) centerPanel.add(Box.createVerticalStrut(3));
		centerPanel.add(bottomPanel);

		this.setLayout(new BorderLayout());
		this.add(centerPanel, BorderLayout.CENTER);
		this.add(memoScroller, BorderLayout.EAST);		
		if (OperatingSystemUtil.isMac())
			this.setBorder(BorderFactory.createTitledBorder((String) null));

		
		description.setEditable(true);
		AutoCompleteDecorator.decorate(description);

		for (JComponent component : components) {
			component.addKeyListener(new KeyAdapter(){
				public void keyTyped(KeyEvent arg0) {
					TransactionEditor.this.setChanged(true);
					super.keyTyped(arg0);
				}
			});

			component.addFocusListener(new FocusAdapter(){
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
			}
		});

		// Load the other information (amount, number, etc) from memory
		((JTextComponent) description.getEditor().getEditorComponent()).addFocusListener(new FocusAdapter(){
			@Override
			public void focusLost(FocusEvent arg0) {
				fillInOtherFields(false);
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
				if (from.getSelectedItem() instanceof Source) {
					if (associatedAccount != null){
						if (!associatedAccount.equals(from.getSelectedItem())){
							to.setSelectedItem(associatedAccount);
						}

						if (associatedAccount.equals(from.getSelectedItem())
								&& associatedAccount.equals(to.getSelectedItem())){
							to.setSelectedItem(null);
						}
					}
					else {
						if (from.getSelectedItem() instanceof BudgetCategory){
							if (to.getSelectedItem() instanceof BudgetCategory){
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
				if (to.getSelectedItem() instanceof Source) {
					if (associatedAccount != null){
						if (!associatedAccount.equals(to.getSelectedItem())){
							from.setSelectedItem(associatedAccount);
						}

						if (associatedAccount.equals(from.getSelectedItem())
								&& associatedAccount.equals(to.getSelectedItem())){
							from.setSelectedItem(null);
						}
					}
					else {
						if (to.getSelectedItem() instanceof BudgetCategory){
							if (from.getSelectedItem() instanceof BudgetCategory){
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
				TransactionEditor.this.setChanged(true);
			}
		});

		cleared.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				TransactionEditor.this.setChanged(true);
			}
		});

		description.setRenderer(new MaxLengthListCellRenderer());
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
		}
		else{
			if (date.getDate() == null)
				date.setDate(new Date());
			number.setValue("");
			description.setValue(null);
			amount.setValue(0);
			to.setSelectedItem(null);
			from.setSelectedItem(null);
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
		if (description.getSelectedItem() != null)
			return description.getSelectedItem().toString();
		return null;
	}

	public Transaction getTransaction(){
		return transaction;
	}
	
	/**
	 * Creates and returns a new transaction, given the currently entered values
	 * in TransactinEditor.  If all the required fields are not filled in, 
	 * returns null.
	 * @return
	 */
	public Transaction getNewTransaction(){
		if (!isTransactionValid())
			return null;
		
		Transaction t = new Transaction(model, date.getDate(), description.getValue().toString(), amount.getValue(), (Source) from.getSelectedItem(), (Source) to.getSelectedItem());
		t.setNumber(number.getValue().toString());
		t.setMemo(memo.getValue().toString());
		t.setCleared(cleared.isSelected());
		t.setReconciled(reconciled.isSelected());
		
		return t;
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
	
	public boolean isTransactionValid(){
		return this.isTransactionValid(null);
	}
	
	public boolean isTransactionValid(Account thisAccount){
		if (description.getValue().toString().length() == 0)
			return false;
		if (date.getDate() == null)
			return false;
		if (to.getSelectedItem() == null)
			return false;
		if (from.getSelectedItem() == null)
			return false;
		if (thisAccount != null){
			if (!from.getSelectedItem().equals(thisAccount) && !to.getSelectedItem().equals(thisAccount))
				return false;
		}
		
		return true;
	}

	public void updateContent(){
		super.updateContent();

		//Update the dropdown lists
		setEnabled(true);

//		Object to = toModel.getSelectedItem();
//		Object from = fromModel.getSelectedItem();

//		toModel.removeAllElements();
//		fromModel.removeAllElements();
//		toModel.addElement(null);
//		fromModel.addElement(null);
//		for (Source source : SourceController.getAccounts()) {
//		toModel.addElement(source);
//		fromModel.addElement(source);
//		}

//		toModel.addElement(null);
//		fromModel.addElement(null);		

//		for (Category c : SourceController.getCategories()){
//		if (c.isIncome())
//		fromModel.addElement(c);
//		else
//		toModel.addElement(c);	
//		}

//		toModel.setSelectedItem(to);
//		fromModel.setSelectedItem(from);
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
		if (PrefsModel.getInstance().isShowAutoComplete()){
			if (description != null 
					&& description.getSelectedItem() != null 
					&& description.getSelectedItem().toString().length() > 0){
				AutoCompleteEntry ace = autoCompleteEntries.getEntry(description.getSelectedItem().toString());
				if (ace != null){
					//We don't do the numbers any more, as this tends to be unique to one transaction.
//					if (forceAll || (ace.getNumber() != null && number.isHintShowing()))
//					number.setValue(ace.getNumber());
					if (forceAll || amount.getValue() == 0)
						amount.setValue(ace.getAmount());

					//We don't do the memos any more, as this tends to be unique to one transaction.
//					if (forceAll || (asi.getMemo() != null && memo.isHintShowing()))
//					memo.setValue(asi.getMemo());

					//This doesn't always work when you go to a different account; it should
					// be good enough for the vast majorty of cases, though, and does a pretty
					// good job at guessing which account is the correct one..
					if (ace.getFrom() != null){
						for (int i = 0; i < from.getModel().getSize(); i++){
							if (from.getModel().getElementAt(i) != null
									&& from.getModel().getElementAt(i).equals(ace.getFrom())){
								from.setSelectedIndex(i);
								break;
							}
						}
					}
					if (ace.getTo() != null){
						for (int i = 0; i < to.getModel().getSize(); i++){
							if (to.getModel().getElementAt(i) != null
									&& to.getModel().getElementAt(i).equals(ace.getTo())){
								to.setSelectedIndex(i);
								break;
							}
						}
					}
				}
				else {
//					number.setValue("");
					amount.setValue(0);
//					memo.setValue("");
					from.setSelectedItem(null);
					to.setSelectedItem(null);
				}
			}
		}
	}
}
