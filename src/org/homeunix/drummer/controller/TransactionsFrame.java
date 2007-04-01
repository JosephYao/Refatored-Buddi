/*
 * Created on May 6, 2006 by wyatt
 */
package org.homeunix.drummer.controller;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.homeunix.drummer.Const;
import org.homeunix.drummer.model.Account;
import org.homeunix.drummer.model.DataInstance;
import org.homeunix.drummer.model.Transaction;
import org.homeunix.drummer.prefs.PrefsInstance;
import org.homeunix.drummer.view.AbstractFrame;
import org.homeunix.drummer.view.GraphFrameLayout;
import org.homeunix.drummer.view.ReportFrameLayout;
import org.homeunix.drummer.view.TransactionsFrameLayout;
import org.homeunix.drummer.view.model.TransactionListModel;
import org.homeunix.thecave.moss.gui.JSearchField.SearchTextChangedEvent;
import org.homeunix.thecave.moss.gui.JSearchField.SearchTextChangedEventListener;
import org.homeunix.thecave.moss.util.Formatter;
import org.homeunix.thecave.moss.util.Log;

import de.schlichtherle.swing.filter.FilteredDynamicListModel;

public class TransactionsFrame extends TransactionsFrameLayout {
	public static final long serialVersionUID = 0;	

	@SuppressWarnings("unchecked")
	/* The baseModel is a custom list model which contains the 
	 * same EList object that the DataModel contains.  This holds 
	 * all of the transactions.  All data access to the transactions 
	 * list that you want to show up immediately (i.e., everything that
	 * is done through the GUI at a minimum) needs to go through 
	 * this model, instead of calling DataInstance directly.  When 
	 * you go through the model, it automatically fires the correct 
	 * updates to the list.
	 */
	private final static TransactionListModel baseModel = new TransactionListModel(DataInstance.getInstance().getDataModel().getAllTransactions().getTransactions());

	/* This model is a filtered list model that is obtained from the
	 * baseModel.  It is a view which contains all transactions in
	 * the given account which match the String and Date criteria.
	 */
	private final FilteredDynamicListModel model;

	private Account account;

	private boolean disableListEvents = false;

	//The values for the date chooser combo box.
	private static final TranslateKeys[] availableFilters = new TranslateKeys[] {
		TranslateKeys.ALL,
		TranslateKeys.TODAY,
		TranslateKeys.THIS_WEEK,
		TranslateKeys.THIS_MONTH,
		TranslateKeys.THIS_QUARTER,
		TranslateKeys.THIS_YEAR,
		TranslateKeys.LAST_YEAR,
		null,
		TranslateKeys.NOT_RECONCILED,
		TranslateKeys.NOT_CLEARED
	};


	public TransactionsFrame(Account account){
		super(account);
		this.account = account;

		Transaction prototype = DataInstance.getInstance().getDataModelFactory().createTransaction();
		prototype.setDate(new Date());
		prototype.setDescription("Description");
		prototype.setNumber("Number");
		prototype.setAmount(123456789);
		prototype.setTo(null);
		prototype.setFrom(null);
		prototype.setMemo("Testing 1, 2, 3, 4, 5");
		list.setPrototypeCellValue(prototype);

		model = baseModel.getFilteredListModel(account, this);
		list.setModel(model);

		editableTransaction.setTransaction(null, true);
		updateContent();

		list.ensureIndexIsVisible(list.getModel().getSize() - 1);
	}

	public TransactionsFrame(Account account, Transaction transaction) {
		this(account);

		list.setSelectedValue(transaction, true);
	}

	protected AbstractFrame initActions(){

		filterComboBox.setModel(new DefaultComboBoxModel(availableFilters));
		filterComboBox.setRenderer(new DefaultListCellRenderer() {
			private static final long serialVersionUID = 1L;

			@Override
			public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
				super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
				if (value == null){
					setText("\u2014");
				}
				else {
					TranslateKeys key = (TranslateKeys) value;
					setText(Translate.getInstance().get(key));
				}
				return this;
			}			
		});
		
		filterComboBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (filterComboBox.getSelectedItem() == null){
					if (e.getItem().equals(filterComboBox.getItemAt(0))){
						filterComboBox.setSelectedIndex(1);
					}
					Log.debug("null; e.getItem == " + e.getItem());
					filterComboBox.setSelectedIndex(0);
				}
				if (e.getStateChange() == ItemEvent.SELECTED) {
					model.update();
				}
			}			
		});

		searchField.addSearchTextChangedEventListener(new SearchTextChangedEventListener(){
			public void searchTextChangedEventOccurred(SearchTextChangedEvent evt) {
				model.update();
			}
		});

//		searchField.getDocument().addDocumentListener(new DocumentListener(){
//		public void changedUpdate(DocumentEvent arg0) {
//		model.update();
//		System.out.println("Change");
//		}

//		public void insertUpdate(DocumentEvent arg0) {
//		model.update();
//		System.out.println("Add");
//		}

//		public void removeUpdate(DocumentEvent arg0) {
//		model.update();		
//		System.out.println("Remove");
//		};
//		});

		list.addListSelectionListener(new ListSelectionListener(){
			public void valueChanged(ListSelectionEvent arg0) {
				if (!arg0.getValueIsAdjusting() && !TransactionsFrame.this.disableListEvents){
					if (editableTransaction.isChanged() 
							&& editableTransaction.getTransaction() != (Transaction) list.getSelectedValue()){
						int ret;

						if (isValidRecord()){
							ret = JOptionPane.showConfirmDialog(
									null, 
									Translate.getInstance().get(TranslateKeys.TRANSACTION_CHANGED_MESSAGE), 
									Translate.getInstance().get(TranslateKeys.TRANSACTION_CHANGED_TITLE),
									JOptionPane.YES_NO_CANCEL_OPTION);
							if (ret == JOptionPane.YES_OPTION){
								recordButton.doClick();
							}
							else if (ret == JOptionPane.NO_OPTION){
								editableTransaction.setChanged(false);
//								list.setSelectedValue(editableTransaction.getTransaction(), true);
//								return;
							}
							else if (ret == JOptionPane.CANCEL_OPTION){
								editableTransaction.setChanged(false);
								list.setSelectedValue(editableTransaction.getTransaction(), true);
								editableTransaction.setChanged(true);
								return;
							}
						}
						else{
							ret = JOptionPane.showConfirmDialog(
									null, 
									Translate.getInstance().get(TranslateKeys.TRANSACTION_CHANGED_INVALID_MESSAGE), 
									Translate.getInstance().get(TranslateKeys.TRANSACTION_CHANGED_TITLE),
									JOptionPane.YES_NO_OPTION);
							if (ret == JOptionPane.NO_OPTION){
								editableTransaction.setChanged(false);

								if (editableTransaction.getTransaction() == null)
									list.clearSelection();
								else
									list.setSelectedValue(editableTransaction.getTransaction(), true);

								editableTransaction.setChanged(true);
								return;
							}
							else if (ret == JOptionPane.YES_OPTION){
								editableTransaction.setChanged(false);
							}
						}
					}

					if (list.getSelectedValue() instanceof Transaction) {
						Transaction t = (Transaction) list.getSelectedValue();

						editableTransaction.setTransaction(t, false);
					}
					else if (list.getSelectedValue() == null){
						editableTransaction.setTransaction(null, false);
						editableTransaction.updateContent();
					}

					updateButtons();
				}
			}
		});

		recordButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				if (!isValidRecord()){
					JOptionPane.showMessageDialog(
							TransactionsFrame.this,
							Translate.getInstance().get(TranslateKeys.RECORD_BUTTON_ERROR),
							Translate.getInstance().get(TranslateKeys.ERROR),
							JOptionPane.ERROR_MESSAGE
					);
					return;
				}

				disableListEvents = true;

				Transaction t;
				boolean isUpdate = false;
				if (recordButton.getText().equals(Translate.getInstance().get(TranslateKeys.RECORD))){
					t = DataInstance.getInstance().getDataModelFactory().createTransaction();
				}
				else if (recordButton.getText().equals(Translate.getInstance().get(TranslateKeys.UPDATE))){
					t = editableTransaction.getTransaction();
					isUpdate = true;
				}
				else {
					Log.error("Unknown record button state: " + recordButton.getText());
					return;
				}

				if (editableTransaction.getFrom().getCreationDate() != null
						&& editableTransaction.getFrom().getCreationDate().after(editableTransaction.getDate()))
					editableTransaction.getFrom().setCreationDate(editableTransaction.getDate());
				if (editableTransaction.getTo().getCreationDate() != null
						&& editableTransaction.getTo().getCreationDate().after(editableTransaction.getDate()))
					editableTransaction.getTo().setCreationDate(editableTransaction.getDate());

				t.setDate(editableTransaction.getDate());
				t.setDescription(editableTransaction.getDescription());
				t.setAmount(editableTransaction.getAmount());
				t.setTo(editableTransaction.getTo());
				t.setFrom(editableTransaction.getFrom());
				t.setMemo(editableTransaction.getMemo());
				t.setNumber(editableTransaction.getNumber());
				t.setCleared(editableTransaction.isCleared());
				t.setReconciled(editableTransaction.isReconciled());

				if (recordButton.getText().equals(Translate.getInstance().get(TranslateKeys.RECORD))) {
					baseModel.add(t);
				}
				else {
					//These should not be needed anymore, as it is done
					// within the baseModel.update() method
//					DataInstance.getInstance().calculateAllBalances();
//					DataInstance.getInstance().saveDataModel();
					baseModel.update(t, model);
				}

				//Update the autocomplete entries
				if (PrefsInstance.getInstance().getPrefs().isShowAutoComplete()){
					PrefsInstance.getInstance().addDescEntry(editableTransaction.getDescription());
					if (editableTransaction != null && editableTransaction.getFrom() != null && editableTransaction.getTo() != null)
						PrefsInstance.getInstance().setAutoCompleteEntry(
								editableTransaction.getDescription(),
								editableTransaction.getNumber(),
								editableTransaction.getAmount(),
								editableTransaction.getFrom().toString(),
								editableTransaction.getTo().toString(),
								editableTransaction.getMemo());
				}

				updateAllTransactionWindows();
//				updateButtons();
				ReportFrameLayout.updateAllReportWindows();
				GraphFrameLayout.updateAllGraphWindows();
				MainBuddiFrame.getInstance().getAccountListPanel().updateContent();
				MainBuddiFrame.getInstance().getCategoryListPanel().updateContent();

				list.setSelectedValue(t, true);

				if (isUpdate){
					editableTransaction.setTransaction(t, true);
				}
				else {
					editableTransaction.setTransaction(null, true);
					list.ensureIndexIsVisible(list.getSelectedIndex());
					list.clearSelection();
				}

				editableTransaction.setChanged(false);
				list.ensureIndexIsVisible(list.getSelectedIndex());

				disableListEvents = false;

				editableTransaction.resetSelection();
			}

		});


		clearButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				if (!editableTransaction.isChanged()
						|| JOptionPane.showConfirmDialog(
								TransactionsFrame.this,
								Translate.getInstance().get(TranslateKeys.CLEAR_TRANSACTION_LOSE_CHANGES),
								Translate.getInstance().get(TranslateKeys.CLEAR_TRANSACTION),
								JOptionPane.YES_NO_OPTION,
								JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){

					editableTransaction.setTransaction(null, true);
					editableTransaction.updateContent();
					list.ensureIndexIsVisible(list.getModel().getSize() - 1);
					list.clearSelection();

					updateButtons();
				}
			}
		});

		deleteButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				if (JOptionPane.showConfirmDialog(
						TransactionsFrame.this, 
						Translate.getInstance().get(TranslateKeys.DELETE_TRANSACTION_LOSE_CHANGES),
						Translate.getInstance().get(TranslateKeys.DELETE_TRANSACTION),
						JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){

					Transaction t = (Transaction) list.getSelectedValue();
					int position = list.getSelectedIndex();
//					DataInstance.getInstance().deleteTransaction(t);
					baseModel.remove(t, model);

					updateAllTransactionWindows();
					updateButtons();
					MainBuddiFrame.getInstance().getAccountListPanel().updateContent();

					list.setSelectedIndex(position);
					if (list.getSelectedValue() instanceof Transaction){
						t = (Transaction) list.getSelectedValue();
						editableTransaction.setTransaction(t, true);
						list.ensureIndexIsVisible(position);
					}
					else{
						editableTransaction.setTransaction(null, true);
						list.clearSelection();
					}

					editableTransaction.setChanged(false);
				}
			}
		});

//		clearSearchField.addActionListener(new ActionListener(){
//		public void actionPerformed(ActionEvent arg0) {
//		TransactionsFrame.this.searchField.setValue("");

//		}
//		});

		this.addWindowListener(new WindowAdapter(){
			@Override
			public void windowClosed(WindowEvent e) {
				if (Const.DEVEL) Log.debug("Closed window; removing from list");
				transactionInstances.put(TransactionsFrame.this.account, null);
				super.windowClosed(e);
			}
		});

		this.addComponentListener(new ComponentAdapter(){
//			@Override
//			public void componentResized(ComponentEvent arg0) {
//			if (Const.DEVEL) Log.debug("Transactions window resized");

//			PrefsInstance.getInstance().getPrefs().getTransactionsWindow().setHeight(arg0.getComponent().getHeight());
//			PrefsInstance.getInstance().getPrefs().getTransactionsWindow().setWidth(arg0.getComponent().getWidth());

//			PrefsInstance.getInstance().savePrefs();

//			super.componentResized(arg0);
//			}

			@Override
			public void componentHidden(ComponentEvent arg0) {
				PrefsInstance.getInstance().checkWindowSanity();

				PrefsInstance.getInstance().getPrefs().getWindows().getTransactionsWindow().setX(arg0.getComponent().getX());
				PrefsInstance.getInstance().getPrefs().getWindows().getTransactionsWindow().setY(arg0.getComponent().getY());
				PrefsInstance.getInstance().getPrefs().getWindows().getTransactionsWindow().setHeight(arg0.getComponent().getHeight());
				PrefsInstance.getInstance().getPrefs().getWindows().getTransactionsWindow().setWidth(arg0.getComponent().getWidth());

				PrefsInstance.getInstance().savePrefs();

				transactionInstances.put(account, null);

				super.componentHidden(arg0);
			}
		});

		return this;
	}

	protected AbstractFrame initContent(){
//		this.setTitle(account.toString() + " - " + Translate.getInstance().get(TranslateKeys.TRANSACTIONS));
		list.setListData(DataInstance.getInstance().getTransactions(account));
		editableTransaction.setTransaction(null, true);

		return this;
	}

	public AbstractFrame updateContent(){
//		if (Const.DEVEL) Log.info("TransactionsFrame.updateContent()");
//		Vector<Transaction> data = DataInstance.getInstance().getTransactions(account);
//		data.add(null);
//		list.setListData(data);

		if (PrefsInstance.getInstance().getPrefs().isShowCreditLimit() 
				&& account != null  
				&& account.getCreditLimit() != 0){
			long amountLeft = (account.getCreditLimit() + account.getBalance());
			double percentLeft = ((double) (account.getCreditLimit() + account.getBalance())) / account.getCreditLimit() * 100.0;

			StringBuffer sb = new StringBuffer();
			if (amountLeft < 0)
				sb.append("<html><font color='red'>");
			sb.append(Translate.getInstance().get((account.isCredit() ? TranslateKeys.AVAILABLE_CREDIT : TranslateKeys.AVAILABLE_OVERDRAFT)))
			.append(": ")
			.append(Translate.getFormattedCurrency(amountLeft))
			.append(" (")
			.append(Formatter.getInstance().getDecimalFormat().format(percentLeft))
			.append("%)");
			if (amountLeft < 0)
				sb.append("</font></html>");

			creditRemaining.setText(sb.toString());
		}
		else
			creditRemaining.setText("");

//		//Update the search
//		if (filteredListModel != null && list != null){
//		filteredListModel.setSource(list.getModel());
//		list.setModel(filteredListModel);
//		}

//		editableTransaction.updateContent();
//		if (Const.DEVEL) Log.info("TransactionsFrame.updateContent() preScroll");
//		list.ensureIndexIsVisible(list.getModel().getSize() - 1);

//		if (Const.DEVEL) Log.info("TransactionsFrame.updateContent() postScroll");
		updateButtons();

		return this;
	}

	public AbstractFrame updateButtons(){
		if (editableTransaction == null 
				|| editableTransaction.getTransaction() == null){
			recordButton.setText(Translate.getInstance().get(TranslateKeys.RECORD));
			clearButton.setText(Translate.getInstance().get(TranslateKeys.CLEAR));
			deleteButton.setEnabled(false);
		}
		else{
			recordButton.setText(Translate.getInstance().get(TranslateKeys.UPDATE));
			clearButton.setText(Translate.getInstance().get(TranslateKeys.NEW));
			deleteButton.setEnabled(true);
		}

//		if (editableTransaction.getDescription().length() > 0 
//		&& editableTransaction.getDate() != null
//		&& editableTransaction.getAmount() != 0
//		&& editableTransaction.getTransferTo() != null
//		&& editableTransaction.getTransferFrom() != null){
//		recordButton.setEnabled(true);
//		}
//		else
//		recordButton.setEnabled(false);

		return this;
	}

	public Account getAccount(){
		return account;
	}

	@Override
	public Component getPrintedComponent() {
		return list;
	}

	@Override
	public AbstractFrame openWindow() {
//		list.setSelectedIndex(list.getModel().getSize() - 1);
		editableTransaction.resetSelection();
		return super.openWindow();
	}

	protected boolean isValidRecord(){
		return (!(
				editableTransaction.getDescription().length() == 0 
				|| editableTransaction.getDate() == null
				|| editableTransaction.getAmount() < 0
				|| editableTransaction.getTo() == null
				|| editableTransaction.getFrom() == null
				|| (editableTransaction.getFrom() != account
						&& editableTransaction.getTo() != account)
		));
	}

	/**
	 * Force an update of every transaction window.
	 * 
	 * To plugin writers: you probably don't need to call this manually;
	 * instead, register all changes to Transactions with the methods
	 * addToTransactionListModel(), removeFromTransactionListModel(), and
	 * updateTransactionListModel().  This should fire updates in all open
	 * windows as well as save the data model, do misc. housecleaning, etc.
	 */
	public static void updateAllTransactionWindows(){
		for (TransactionsFrameLayout tfl : Collections.unmodifiableCollection(transactionInstances.values())) {
			if (tfl != null)
				tfl.updateContent();
		}
	}

	/**
	 * Gets the filter text in the search box
	 * @return The contents of the search box
	 */
	public String getFilterText(){
		return searchField.getText();
	}

	/**
	 * Gets the selected item in the filter pulldown
	 * @return The selected item in the filter pulldown
	 */
	public TranslateKeys getDateRangeFilter(){
		return (TranslateKeys) filterComboBox.getSelectedItem();
	}

	/**
	 * Forces a toggle on the Cleared state, without needing to save manually.
	 */
	public void toggleCleared(){
		Transaction t = (Transaction) list.getSelectedValue();
		t.setCleared(!t.isCleared());
//		DataInstance.getInstance().saveDataModel();
		baseModel.updateNoOrderChange(t);
		editableTransaction.updateClearedAndReconciled();
	}

	/**
	 * Forces a toggle on the Reconciled state, without needing to save manually.
	 */
	public void toggleReconciled(){
		Transaction t = (Transaction) list.getSelectedValue();
		t.setReconciled(!t.isReconciled());
//		DataInstance.getInstance().saveDataModel();
		baseModel.updateNoOrderChange(t);
		editableTransaction.updateClearedAndReconciled();

	}

	public void clickClear(){
		clearButton.doClick();
	}
	public void clickRecord(){
		recordButton.doClick();
	}
	public void clickDelete(){
		deleteButton.doClick();
	}

	/**
	 * After creating a Collection of new Transactions via 
	 * DataInstance.getInstance().getDataModelFactory().createTransaction(),
	 * and filling in all the needed details, you call this method to
	 * add them to the data model and update all windows automatically.
	 * 
	 * Note that you should *not* call DataInstance.getInstance().addTransaction() directly, as
	 * you will not update the windows properly.
	 * @param t Transaction to add to the data model
	 */
	public static void addToTransactionListModel(Collection<Transaction> transactions){
		baseModel.add(transactions);
	}

	/**
	 * After creating a new Transaction via DataInstance.getInstance().getDataModelFactory().createTransaction(),
	 * and filling in all the needed details, you call this method to
	 * add it to the data model and update all windows automatically.
	 * 
	 * Note that you should *not* call DataInstance.getInstance().addTransaction() directly, as
	 * you will not update the windows properly.
	 * @param t Transaction to add to the data model
	 */
	public static void addToTransactionListModel(Transaction t){
		baseModel.add(t);
	}

	/**
	 * Remove a transaction from the data model and all open windows.
	 * 
	 * Note that you should *not* call DataInstance.getInstance().deleteTransaction() directly, as
	 * you will not update the windows properly.
	 * @param t Transaction to delete
	 * @param fdlm The filtered dynamic list model in which the transaction exists.  If you 
	 * don't have this, you can use null, although you should be aware that there may be some
	 * problems updating transaction windows with the new data, as the windows will not
	 * have the update() method called on their FilteredDynamicListModels. 
	 */
	public static void removeFromTransactionListModel(Transaction t, FilteredDynamicListModel fdlm){
		baseModel.remove(t, fdlm);
	}

	/**
	 * Notifies all windows that a transaction has been updated.  If you 
	 * change a transaction and do not register it here after all the changes
	 * are complete, you will not get the transaction updated in the 
	 * Transaction windows.
	 * 
	 * @param t Transaction to update
	 * @param fdlm The filtered dynamic list model in which the transaction exists.  If you 
	 * don't have this, you can use null, although you should be aware that there may be some
	 * problems updating transaction windows with the new data, as the windows will not
	 * have the update() method called on their FilteredDynamicListModels. 
	 */
	public static void updateTransactionListModel(Transaction t, FilteredDynamicListModel fdlm){
		baseModel.update(t, fdlm);
	}
}