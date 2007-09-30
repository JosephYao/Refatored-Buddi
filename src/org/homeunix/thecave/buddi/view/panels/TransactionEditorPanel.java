/*
 * Created on May 8, 2006 by wyatt
 * 
 * A utility class which allows editing of transactions.
 */
package org.homeunix.thecave.buddi.view.panels;

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
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
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
import org.homeunix.thecave.buddi.model.Document;
import org.homeunix.thecave.buddi.model.Source;
import org.homeunix.thecave.buddi.model.Transaction;
import org.homeunix.thecave.buddi.model.impl.ModelFactory;
import org.homeunix.thecave.buddi.model.prefs.PrefsModel;
import org.homeunix.thecave.buddi.model.swing.AutoCompleteEntryModel;
import org.homeunix.thecave.buddi.model.swing.DescriptionList;
import org.homeunix.thecave.buddi.model.swing.SourceComboBoxModel;
import org.homeunix.thecave.buddi.model.swing.AutoCompleteEntryModel.AutoCompleteEntry;
import org.homeunix.thecave.buddi.plugin.api.exception.InvalidValueException;
import org.homeunix.thecave.buddi.plugin.api.util.TextFormatter;
import org.homeunix.thecave.buddi.view.swing.MaxLengthListCellRenderer;
import org.homeunix.thecave.buddi.view.swing.SourceListCellRenderer;
import org.homeunix.thecave.moss.swing.MossDecimalField;
import org.homeunix.thecave.moss.swing.MossHintComboBox;
import org.homeunix.thecave.moss.swing.MossHintTextArea;
import org.homeunix.thecave.moss.swing.MossHintTextField;
import org.homeunix.thecave.moss.swing.MossPanel;
import org.homeunix.thecave.moss.swing.MossScrollingComboBox;
import org.homeunix.thecave.moss.swing.model.AutoCompleteMossHintComboBoxModel;
import org.homeunix.thecave.moss.swing.model.BackedComboBoxModel;
import org.homeunix.thecave.moss.util.Log;
import org.homeunix.thecave.moss.util.OperatingSystemUtil;
import org.jdesktop.swingx.JXDatePicker;

/**
 * The transaction editing pane, which includes text fields to enter
 * date, descriptions, number, amount, etc.  Currently used in 
 * TransactionFrame and ScheduledTransaction.
 * @author wyatt
 *
 */
public class TransactionEditorPanel extends MossPanel {
	public static final long serialVersionUID = 0;

	private final Vector<JComponent> components;

	private Transaction transaction; //Set when editing existing one; null otherwise


	private final JXDatePicker date;
	private final MossHintComboBox description;
	private final MossHintTextField number;
	private final MossDecimalField amount;
	private final MossScrollingComboBox from;
	private final MossScrollingComboBox to;
	private final JCheckBox cleared;
	private final JCheckBox reconciled;
	private final MossHintTextArea memo;



	private final Document model;
	private final Account associatedAccount;

	private final AutoCompleteEntryModel autoCompleteEntries;

	private boolean changed;

	public TransactionEditorPanel(Document model, Account associatedAccount, boolean scheduledTransactionPane){
		super(true);
		this.model = model;
		this.associatedAccount = associatedAccount;

		autoCompleteEntries = new AutoCompleteEntryModel(model);

		date = new JXDatePicker();
		amount = new MossDecimalField();
		from = new MossScrollingComboBox();
		to = new MossScrollingComboBox();
		number = new MossHintTextField(PrefsModel.getInstance().getTranslator().get(BuddiKeys.HINT_NUMBER));
		description = new MossHintComboBox(PrefsModel.getInstance().getTranslator().get(BuddiKeys.HINT_DESCRIPTION));

		if (PrefsModel.getInstance().isShowAutoComplete())
			description.setModel(new AutoCompleteMossHintComboBoxModel<String>(description, new DescriptionList(this.model)));
		else
			description.setModel(new BackedComboBoxModel<String>(new DescriptionList(this.model)));
		memo = new MossHintTextArea(PrefsModel.getInstance().getTranslator().get(BuddiKeys.HINT_MEMO));
		cleared = new JCheckBox(PrefsModel.getInstance().getTranslator().get(BuddiKeys.SHORT_CLEARED));
		reconciled = new JCheckBox(PrefsModel.getInstance().getTranslator().get(BuddiKeys.SHORT_RECONCILED));

		components = new Vector<JComponent>();

		date.setVisible(!scheduledTransactionPane);
		cleared.setVisible(!scheduledTransactionPane);
		reconciled.setVisible(!scheduledTransactionPane);

		open();
	}

	@Override
	public void init() {
		super.init();

		description.setText("");

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
		GridBagConstraints cTop = new GridBagConstraints();
		GridBagConstraints cBottom = new GridBagConstraints();

		cTop.weighty = 0;
		cTop.gridy = 0;
		cTop.fill = GridBagConstraints.HORIZONTAL;

		cTop.weightx = 0.2;
		cTop.gridx = 0;
		topPanel.add(date, cTop);

		if (!OperatingSystemUtil.isMac()){
			cTop.weightx = 0.0;
			cTop.ipadx = 5;
			cTop.gridx = 1;
			topPanel.add(new JLabel(), cTop);
		}
		
		cTop.weightx = 0.5;
		cTop.gridx = 2;
		topPanel.add(description, cTop);
		
		if (!OperatingSystemUtil.isMac()){
			cTop.weightx = 0.0;
			cTop.ipadx = 5;
			cTop.gridx = 3;
			topPanel.add(new JLabel(), cTop);
		}

		cTop.weightx = 0.4;
		cTop.gridx = 4;
		topPanel.add(number, cTop);

		if (PrefsModel.getInstance().isShowCleared()){
			cTop.weightx = 0.0;
			cTop.gridx = 5;
			topPanel.add(cleared, cTop);
		}
		if (PrefsModel.getInstance().isShowReconciled()){	
			cTop.weightx = 0.0;
			cTop.gridx = 6;
			topPanel.add(reconciled, cTop);
		}

		cBottom.weighty = 0;
		cBottom.gridy = 0;
		cBottom.fill = GridBagConstraints.HORIZONTAL;

		cBottom.weightx = 0.2;
		cBottom.ipadx = 150;
		cBottom.gridx = 0;
		bottomPanel.add(amount, cBottom);

		if (!OperatingSystemUtil.isMac()){
			cBottom.weightx = 0.0;
			cBottom.ipadx = 5;
			cBottom.gridx = 1;
			bottomPanel.add(new JLabel(), cBottom);
		}
		
		cBottom.weightx = 0.5;
		cBottom.ipadx = 0;
		cBottom.gridx = 2;
		bottomPanel.add(from, cBottom);

		cBottom.weightx = 0.0;
		cBottom.ipadx = 0;
		cBottom.gridx = 3;
		bottomPanel.add(new JLabel(TextFormatter.getTranslation(BuddiKeys.TO)), cBottom);

		cBottom.weightx = 0.5;
		cBottom.ipadx = 0;
		cBottom.gridx = 4;
		bottomPanel.add(to, cBottom);

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


//		AutoCompleteDecorator.decorate(description);

		for (JComponent component : components) {
			component.addKeyListener(new KeyAdapter(){
				public void keyTyped(KeyEvent arg0) {
					TransactionEditorPanel.this.setChanged(true);
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
		});

		((JTextComponent) description.getEditor().getEditorComponent()).addKeyListener(new KeyAdapter(){

			@Override
			public void keyReleased(KeyEvent e) {
				super.keyReleased(e);
				fillInOtherFields(false);
			}

//			@Override
//			public void keyPressed(KeyEvent e) {
////			if (e.getKeyCode() == KeyEvent.VK_ENTER){
//			fillInOtherFields(false);
////			}
//			super.keyPressed(e);
//			}
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
				TransactionEditorPanel.this.setChanged(true);
			}
		});

		cleared.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				TransactionEditorPanel.this.setChanged(true);
			}
		});

		final MaxLengthListCellRenderer renderer = new MaxLengthListCellRenderer(description);
		description.setRenderer(renderer);
		description.addPropertyChangeListener(new PropertyChangeListener(){
			public void propertyChange(PropertyChangeEvent evt) {
				renderer.computeLength();
			}
		});
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
			if (associatedAccount != null){
				if (associatedAccount.equals(from.getSelectedItem())){
					cleared.setSelected(transaction.isClearedFrom());
					reconciled.setSelected(transaction.isReconciledFrom());				
				}
				else if (associatedAccount.equals(to.getSelectedItem())){
					cleared.setSelected(transaction.isClearedTo());
					reconciled.setSelected(transaction.isReconciledTo());
				}
			}
		}
		else{
			if (date.getDate() == null)
				date.setDate(new Date());
			number.setText("");
			description.setText("");
			amount.setValue(0);
			to.setSelectedItem(null);
			from.setSelectedItem(null);
			memo.setText("");
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
		boolean changedDesc = !(description.getText().equals(transaction.getDescription()));
		boolean changedAmt  = !(amount.getValue() == transaction.getAmount());
		boolean changedDate = !(date.getDate().equals(transaction.getDate()));
		return changedDesc && changedAmt && changedDate;
	}

	public void setChanged(boolean changed){
		this.changed = changed;
	}

	/**
	 * Returns the updated transaction.  If called from a new transaction, returns null.
	 * @return
	 */
	public Transaction getTransactionUpdated() throws InvalidValueException {
		if (transaction == null)
			throw new InvalidValueException("This transaction is not already created; call getTransactionNew() to get it.");

		transaction.setDate(date.getDate());
		transaction.setDescription(description.getText());
		transaction.setAmount(amount.getValue());
		transaction.setFrom((Source) from.getSelectedItem());
		transaction.setTo((Source) to.getSelectedItem());
		transaction.setNumber(number.getText().toString());
		transaction.setMemo(memo.getText().toString());
		if (associatedAccount != null){
			if (associatedAccount.equals(from.getSelectedItem())){
				transaction.setClearedFrom(cleared.isSelected());
				transaction.setReconciledFrom(reconciled.isSelected());			
			}
			else if (associatedAccount.equals(to.getSelectedItem())){
				transaction.setClearedTo(cleared.isSelected());
				transaction.setReconciledTo(reconciled.isSelected());	
			}
		}
		return transaction;
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
	public Transaction getTransactionNew() throws InvalidValueException {
		if (!isTransactionValid())
			throw new InvalidValueException("New transaction is not completely filled in");

		Transaction t = ModelFactory.createTransaction(date.getDate(), description.getText(), amount.getValue(), (Source) from.getSelectedItem(), (Source) to.getSelectedItem());
		t.setNumber(number.getText().toString());
		t.setMemo(memo.getText().toString());
		if (associatedAccount != null){
			if (associatedAccount.equals(from.getSelectedItem())){
				t.setClearedFrom(cleared.isSelected());
				t.setReconciledFrom(reconciled.isSelected());				
			}
			else if (associatedAccount.equals(to.getSelectedItem())){
				t.setClearedTo(cleared.isSelected());
				t.setReconciledTo(reconciled.isSelected());				
			}
		}
		return t;
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

	public boolean isTransactionValid(){
		return this.isTransactionValid(null);
	}

	public boolean isTransactionValid(Account thisAccount){
		if (description.getText().length() == 0)
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
		if (associatedAccount.equals(from.getSelectedItem())){
			cleared.setSelected(transaction.isClearedFrom());
			reconciled.setSelected(transaction.isReconciledFrom());				
		}
		else if (associatedAccount.equals(to.getSelectedItem())){
			cleared.setSelected(transaction.isClearedTo());
			reconciled.setSelected(transaction.isReconciledTo());
		}
	}

	private void fillInOtherFields(boolean forceAll){		
		//If we lose focus on the description field, we check if
		// there is something there.  If so, we fill in others with
		// the dictionary map, but only if they haven't been modified from their default values!
		//
		//We only will auto complete if this is a new transaction - we don't want to change anything
		// automatically with existing transactions! (Bug #1800429)
		if (PrefsModel.getInstance().isShowAutoComplete() && transaction == null){
			if (description != null 
					&& description.getText() != null 
					&& description.getText().length() > 0){
				AutoCompleteEntry ace = autoCompleteEntries.getEntry(description.getText());
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
					if (forceAll){
//						number.setValue("");
						amount.setValue(0);
//						memo.setValue("");
						from.setSelectedItem(null);
						to.setSelectedItem(null);
					}
				}
			}
		}
	}
}
