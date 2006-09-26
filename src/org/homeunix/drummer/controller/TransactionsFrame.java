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
import java.util.Collections;
import java.util.Date;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.homeunix.drummer.Const;
import org.homeunix.drummer.model.Account;
import org.homeunix.drummer.model.DataInstance;
import org.homeunix.drummer.model.Transaction;
import org.homeunix.drummer.prefs.PrefsInstance;
import org.homeunix.drummer.util.DateUtil;
import org.homeunix.drummer.util.Formatter;
import org.homeunix.drummer.util.Log;
import org.homeunix.drummer.view.AbstractFrame;
import org.homeunix.drummer.view.GraphFrameLayout;
import org.homeunix.drummer.view.ReportFrameLayout;
import org.homeunix.drummer.view.TransactionsFrameLayout;

import de.schlichtherle.swing.filter.FilteredStaticListModel;
import de.schlichtherle.swing.filter.ListElementFilter;

public class TransactionsFrame extends TransactionsFrameLayout {
	public static final long serialVersionUID = 0;	

	protected final FilteredStaticListModel filteredListModel;
	protected final TransactionListElementFilter transactionFilter;

	private Account account;

	private static final TranslateKeys[] dateRangeFilters = new TranslateKeys[] {
		TranslateKeys.ALL,
		TranslateKeys.TODAY,
		TranslateKeys.THIS_WEEK,
		TranslateKeys.THIS_MONTH,
		TranslateKeys.THIS_QUARTER,
		TranslateKeys.THIS_YEAR,
		TranslateKeys.LAST_YEAR
	};


	public TransactionsFrame(Account account){
		super(account);

		transactionFilter = new TransactionListElementFilter();
		filteredListModel = new FilteredStaticListModel();

		filteredListModel.setFilter(transactionFilter);

		this.account = account;

		this.setTitle(Translate.getInstance().get(TranslateKeys.TRANSACTIONS) + " - " + account.getName());

		editableTransaction.setTransaction(null, true);
		updateContent();
	}

	public TransactionsFrame(Account account, Transaction transaction) {
		this(account);

		list.setSelectedValue(transaction, true);
	}

	protected AbstractFrame initActions(){

		dateRangeComboBox.setModel(new DefaultComboBoxModel(dateRangeFilters));
		dateRangeComboBox.setRenderer(new DefaultListCellRenderer() {
			private static final long serialVersionUID = 1L;

			@Override
			public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
				super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
				TranslateKeys key = (TranslateKeys) value;
				setText(Translate.getInstance().get(key));
				return this;
			}			
		});
		dateRangeComboBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (ItemEvent.SELECTED == e.getStateChange()) {
					transactionFilter.setFilterDateRange((TranslateKeys) e.getItem());
					filteredListModel.update();
				}
			}			
		});

		searchField.getDocument().addDocumentListener(new DocumentListener(){
			private void update(){
				if (!searchField.getText().equals(searchField.getHint()))
					transactionFilter.setFilterText(searchField.getText());
				else
					transactionFilter.setFilterText("");

				filteredListModel.update();
				if (Const.DEVEL) if (Const.DEVEL) Log.debug(transactionFilter.getFilterText());
			}

			public void changedUpdate(DocumentEvent arg0) {
				update();
			}

			public void insertUpdate(DocumentEvent arg0) {
				update();				
			}

			public void removeUpdate(DocumentEvent arg0) {
				update();				
			};
		});

		list.addListSelectionListener(new ListSelectionListener(){
			public void valueChanged(ListSelectionEvent arg0) {
				if (!arg0.getValueIsAdjusting()){
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
									list.setSelectedIndex(list.getModel().getSize() - 1);
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

				if (recordButton.getText().equals(Translate.getInstance().get(TranslateKeys.RECORD)))
					DataInstance.getInstance().addTransaction(t);
				else {
					DataInstance.getInstance().calculateAllBalances();
					DataInstance.getInstance().saveDataModel();
				}

				//Update the autocomplete entries
				if (PrefsInstance.getInstance().getPrefs().isShowAutoComplete()){
					PrefsInstance.getInstance().addDescEntry(editableTransaction.getDescription());
					PrefsInstance.getInstance().setAutoCompleteEntry(
							editableTransaction.getDescription(),
							editableTransaction.getNumber(),
							editableTransaction.getAmount(),
							editableTransaction.getFrom().toString(),
							editableTransaction.getTo().toString(),
							editableTransaction.getMemo());
				}

				editableTransaction.setTransaction(null, true);
				editableTransaction.setChanged(false);

				updateAllTransactionWindows();
				ReportFrameLayout.updateAllReportWindows();
				GraphFrameLayout.updateAllGraphWindows();
				
				if (isUpdate){
					editableTransaction.setTransaction(t, true);
					list.setSelectedValue(editableTransaction.getTransaction(), true);
				}
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
					list.setSelectedIndex(list.getModel().getSize() - 1);
					list.ensureIndexIsVisible(list.getModel().getSize() - 1);

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
					DataInstance.getInstance().deleteTransaction(t);
					editableTransaction.setTransaction(null, true);
					list.clearSelection();

					updateAllTransactionWindows();
				}
			}
		});

		clearSearchField.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				TransactionsFrame.this.searchField.setValue("");

			}
		});

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

				PrefsInstance.getInstance().getPrefs().getTransactionsWindow().setX(arg0.getComponent().getX());
				PrefsInstance.getInstance().getPrefs().getTransactionsWindow().setY(arg0.getComponent().getY());
				PrefsInstance.getInstance().getPrefs().getTransactionsWindow().setHeight(arg0.getComponent().getHeight());
				PrefsInstance.getInstance().getPrefs().getTransactionsWindow().setWidth(arg0.getComponent().getWidth());

				PrefsInstance.getInstance().savePrefs();

				transactionInstances.put(account, null);

				super.componentHidden(arg0);
			}
		});

		return this;
	}

	protected AbstractFrame initContent(){
		this.setTitle(account.getName() + " - " + Translate.getInstance().get(TranslateKeys.TRANSACTIONS));
		list.setListData(DataInstance.getInstance().getTransactions(account));
		editableTransaction.setTransaction(null, true);

		return this;
	}

	public AbstractFrame updateContent(){
		if (Const.DEVEL) Log.info("TransactionsFrame.updateContent()");
		Vector<Transaction> data = DataInstance.getInstance().getTransactions(account);
		data.add(null);
		list.setListData(data);

		if (PrefsInstance.getInstance().getPrefs().isShowCreditLimit() 
				&& account != null  
				&& account.getCreditLimit() != 0){
			double amountLeft = (double) (account.getCreditLimit() + account.getBalance()) / 100.0;
			double percentLeft = ((double) (account.getCreditLimit() + account.getBalance())) / account.getCreditLimit() * 100.0;

			StringBuffer sb = new StringBuffer();
			if (amountLeft < 0)
				sb.append("<html><font color='red'>");
			sb.append(Translate.getInstance().get((account.isCredit() ? TranslateKeys.AVAILABLE_CREDIT : TranslateKeys.AVAILABLE_OVERDRAFT)))
			.append(": ")
			.append(PrefsInstance.getInstance().getPrefs().getCurrencySymbol())
			.append(Formatter.getInstance().getDecimalFormat().format(amountLeft))
			.append(" (")
			.append(Formatter.getInstance().getDecimalFormat().format(percentLeft))
			.append("%)");
			if (amountLeft < 0)
				sb.append("</font></html>");

			creditRemaining.setText(sb.toString());
		}
		else
			creditRemaining.setText("");

		//Update the search
		if (filteredListModel != null && list != null){
			filteredListModel.setSource(list.getModel());
			list.setModel(filteredListModel);
		}

		editableTransaction.updateContent();
		if (Const.DEVEL) Log.info("TransactionsFrame.updateContent() preScroll");
		list.ensureIndexIsVisible(list.getModel().getSize() - 1);
		
		if (Const.DEVEL) Log.info("TransactionsFrame.updateContent() postScroll");
		updateButtons();

		if (Const.DEVEL) Log.info("TransactionsFrame.updateContent()");
		MainBuddiFrame.getInstance().getAccountListPanel().updateContent();
		MainBuddiFrame.getInstance().getCategoryListPanel().updateContent();
		if (Const.DEVEL) Log.info("TransactionsFrame.updateContent()");
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

	public class TransactionListElementFilter implements ListElementFilter {
		static final long serialVersionUID = 0;

		private TranslateKeys filterDateRange = TranslateKeys.ALL;
		private String filterText = "";

		public boolean accept(Object obj) {
			if (null == obj) {
				return true;
			}
			else if (obj instanceof Transaction) {
				Transaction t = (Transaction) obj;
				return acceptText(t) && acceptDate(t);
			}
			else 
				return false;			
		}

		private boolean acceptDate(Transaction t) {
			if (null == filterDateRange || TranslateKeys.ALL == filterDateRange) {
				return true;
			}

			Date today = new Date();

			if (TranslateKeys.TODAY == filterDateRange) {
				return DateUtil.getEndOfDay(today).equals(DateUtil.getEndOfDay(t.getDate()));
			}
			else if (TranslateKeys.THIS_WEEK == filterDateRange) {
				return DateUtil.getStartOfDay(DateUtil.getNextNDay(today, -7)).before(t.getDate());
			}
			else if (TranslateKeys.THIS_MONTH == filterDateRange) {
				return DateUtil.getStartOfDay(DateUtil.getBeginOfMonth(today, 0)).before(t.getDate());
			}
			else if (TranslateKeys.THIS_QUARTER == filterDateRange) {
				return DateUtil.getStartOfDay(DateUtil.getBeginOfQuarter(today, 0)).before(t.getDate());
			} 
			else if (TranslateKeys.THIS_YEAR == filterDateRange) {
				return DateUtil.getStartOfDay(DateUtil.getBeginOfYear(today)).before(t.getDate());				
			}
			else if (TranslateKeys.LAST_YEAR == filterDateRange) {
				Date startOfLastYear = DateUtil.getStartOfDay(DateUtil.getStartOfYear(DateUtil.getNextNDay(today, -365)));
				Date endOfLastYear = DateUtil.getEndOfYear(startOfLastYear);
				return startOfLastYear.before(t.getDate()) && endOfLastYear.after(t.getDate()); 
			}
			else 
				Log.error("Unknown filter date range: " + filterDateRange);
				return false;
		}

		private boolean acceptText(Transaction t) {
			if (null == filterText || 0 == filterText.length()) {
				return true;
			}
			return (t.getDescription().toLowerCase().contains(filterText.toLowerCase())
					|| t.getNumber().toLowerCase().contains(filterText.toLowerCase())
					|| t.getMemo().toLowerCase().contains(filterText.toLowerCase())
					|| t.getFrom().getName().toLowerCase().contains(filterText.toLowerCase())
					|| t.getTo().getName().toLowerCase().contains(filterText.toLowerCase()));
		}	

		public TranslateKeys getFilterDateRange() {
			return this.filterDateRange;
		}

		public void setFilterDateRange(TranslateKeys key) {
			this.filterDateRange = key;
		}

		public String getFilterText() {
			return filterText;
		}

		public void setFilterText(String filterText) {
			this.filterText = filterText;
		}
	}


	/**
	 * Force an update of every transaction window  
	 */
	public static void updateAllTransactionWindows(){
		for (TransactionsFrameLayout tfl : Collections.unmodifiableCollection(transactionInstances.values())) {
			if (tfl != null)
				tfl.updateContent();
		}
	}
}
