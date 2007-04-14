/*
 * Created on May 6, 2006 by wyatt
 */
package org.homeunix.drummer.view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.util.Collection;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.homeunix.drummer.controller.DocumentController;
import org.homeunix.drummer.controller.TransactionController;
import org.homeunix.drummer.controller.TransactionsFramePreLoader;
import org.homeunix.drummer.controller.Translate;
import org.homeunix.drummer.controller.TranslateKeys;
import org.homeunix.drummer.model.Account;
import org.homeunix.drummer.model.DataInstance;
import org.homeunix.drummer.model.ModelFactory;
import org.homeunix.drummer.model.Transaction;
import org.homeunix.drummer.prefs.PrefsInstance;
import org.homeunix.drummer.prefs.WindowAttributes;
import org.homeunix.drummer.util.FormatterWrapper;
import org.homeunix.drummer.view.components.EditableTransaction;
import org.homeunix.drummer.view.components.TransactionCellRenderer;
import org.homeunix.drummer.view.model.TransactionListModel;
import org.homeunix.thecave.moss.gui.JSearchField;
import org.homeunix.thecave.moss.gui.JSearchField.SearchTextChangedEvent;
import org.homeunix.thecave.moss.gui.JSearchField.SearchTextChangedEventListener;
import org.homeunix.thecave.moss.gui.abstractwindows.AbstractFrame;
import org.homeunix.thecave.moss.gui.abstractwindows.StandardWindow;
import org.homeunix.thecave.moss.util.Formatter;
import org.homeunix.thecave.moss.util.Log;
import org.homeunix.thecave.moss.util.OperatingSystemUtil;

import de.schlichtherle.swing.filter.FilteredDynamicListModel;

public class TransactionsFrame extends AbstractBuddiFrame {
	public static final long serialVersionUID = 0;	

//	private static final Map<Account, TransactionsFrame> transactionInstances = new HashMap<Account, TransactionsFrame>();
	private static TransactionsFramePreLoader preloader = null;
	
	private final JList list;
	private final JScrollPane listScroller;

	private final EditableTransaction editableTransaction;
	private final JButton recordButton;
	private final JButton clearButton;
	private final JButton deleteButton;
	private final JSearchField searchField;
	private final JComboBox filterComboBox;
	private final JLabel creditRemaining;

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

	private final Account account;

	private boolean disableListEvents = false;

	//The values for the date chooser combo box.
	private static final TranslateKeys[] availableFilters = new TranslateKeys[] {
		TranslateKeys.TRANSACTION_FILTER_ALL,
		TranslateKeys.TRANSACTION_FILTER_TODAY,
		TranslateKeys.TRANSACTION_FILTER_THIS_WEEK,
		TranslateKeys.TRANSACTION_FILTER_THIS_MONTH,
		TranslateKeys.TRANSACTION_FILTER_THIS_QUARTER,
		TranslateKeys.TRANSACTION_FILTER_THIS_YEAR,
		TranslateKeys.TRANSACTION_FILTER_LAST_YEAR,
		null,
		TranslateKeys.TRANSACTION_FILTER_NOT_RECONCILED,
		TranslateKeys.TRANSACTION_FILTER_NOT_CLEARED
	};
	
	public TransactionsFrame(Account account){
		this.account = account;

//		if (transactionInstances.get(account) != null)
//			transactionInstances.get(account).setVisible(false);

//		transactionInstances.put(account, this);

		filterComboBox = new JComboBox();
		creditRemaining = new JLabel();

		//Set up the transaction list
		list = new JList(){
			public final static long serialVersionUID = 0;

			@Override
			public String getToolTipText(MouseEvent event) {
				int i = locationToIndex(event.getPoint());
				if (i >= 0 && i < getModel().getSize()){
					Object o = getModel().getElementAt(i);

					if (o instanceof Transaction){
						Transaction transaction = (Transaction) o;

						if (transaction != null){
							StringBuilder sb = new StringBuilder();

							sb.append("<html>");
							sb.append(transaction.getDescription());

							if (transaction.getNumber().length() > 0){
								sb.append("<br>");
								sb.append("#");
								sb.append(transaction.getNumber());
							}

							sb.append("<br>");
							sb.append(FormatterWrapper.getFormattedCurrencyForTransaction(transaction.getAmount(), transaction.getTo().equals(TransactionsFrame.this.account)));
							sb.append("  ");
							sb.append(transaction.getFrom())
							.append(" ")
							.append(Translate.getInstance().get(TranslateKeys.TO))
							.append(" ")
							.append(transaction.getTo());

							if (transaction.getMemo().length() > 0){
								sb.append("<br>");
								sb.append(transaction.getMemo());
							}

							sb.append("</html>");

							return sb.toString();
						}
					}
				}

				return "";
			}
		};
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		TransactionCellRenderer renderer = new TransactionCellRenderer();
		renderer.setAccount(account);
		list.setCellRenderer(renderer);

		listScroller = new JScrollPane(list);

		JPanel scrollPanel = new JPanel(new BorderLayout());

		//Set up the editing portion
		editableTransaction = new EditableTransaction(this);

		recordButton = new JButton(Translate.getInstance().get(TranslateKeys.BUTTON_RECORD));
		clearButton = new JButton(Translate.getInstance().get(TranslateKeys.CLEAR));
		deleteButton = new JButton(Translate.getInstance().get(TranslateKeys.BUTTON_DELETE));
		searchField = new JSearchField(Translate.getInstance().get(TranslateKeys.DEFAULT_SEARCH));

		recordButton.setPreferredSize(new Dimension(Math.max(100, recordButton.getPreferredSize().width), recordButton.getPreferredSize().height));
		clearButton.setPreferredSize(new Dimension(Math.max(100, clearButton.getPreferredSize().width), clearButton.getPreferredSize().height));
		deleteButton.setPreferredSize(new Dimension(Math.max(100, deleteButton.getPreferredSize().width), deleteButton.getPreferredSize().height));
		searchField.setPreferredSize(new Dimension(160, searchField.getPreferredSize().height));
		filterComboBox.setPreferredSize(new Dimension(100, filterComboBox.getPreferredSize().height));

		model = baseModel.getFilteredListModel(account, this);
		list.setModel(model);
		
		JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		searchPanel.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
		searchPanel.add(new JLabel(Translate.getInstance().get(TranslateKeys.TRANSACTION_FILTER)));
		searchPanel.add(filterComboBox);
		searchPanel.add(searchField);

		JPanel creditRemainingPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		creditRemainingPanel.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
		creditRemainingPanel.add(creditRemaining);

		JPanel topPanel = new JPanel(new BorderLayout());
		topPanel.add(searchPanel, BorderLayout.EAST);
		topPanel.add(creditRemainingPanel, BorderLayout.WEST);

		this.getRootPane().setDefaultButton(recordButton);

		JPanel buttonPanelRight = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		buttonPanelRight.add(clearButton);
		buttonPanelRight.add(recordButton);

		JPanel buttonPanelLeft = new JPanel(new FlowLayout(FlowLayout.LEFT));
		buttonPanelLeft.add(deleteButton);

		JPanel buttonPanel = new JPanel(new BorderLayout());
		buttonPanel.add(buttonPanelRight, BorderLayout.EAST);
		buttonPanel.add(buttonPanelLeft, BorderLayout.WEST);

		scrollPanel.add(topPanel, BorderLayout.NORTH);
		scrollPanel.add(listScroller, BorderLayout.CENTER);
		scrollPanel.add(editableTransaction, BorderLayout.SOUTH);

		JPanel mainPanel = new JPanel(); 
		mainPanel.setLayout(new BorderLayout());

		mainPanel.add(scrollPanel, BorderLayout.CENTER);
		mainPanel.add(buttonPanel, BorderLayout.SOUTH);

		
		this.setLayout(new BorderLayout());
		this.setTitle(Translate.getInstance().get(TranslateKeys.TRANSACTIONS) + " - " + account.toString());
		this.add(mainPanel, BorderLayout.CENTER);

		if (OperatingSystemUtil.isMac()){
			list.putClientProperty("Quaqua.List.style", "striped");
			listScroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
			listScroller.setBorder(BorderFactory.createCompoundBorder(
					BorderFactory.createEmptyBorder(5, 10, 5, 10),
					listScroller.getBorder()));
			scrollPanel.setBorder(BorderFactory.createTitledBorder(""));
			editableTransaction.setBorder(BorderFactory.createEmptyBorder(2, 8, 5, 8));
			searchField.putClientProperty("Quaqua.Component.visualMargin", new Insets(0,0,0,0));
		}
		else {
			editableTransaction.setBorder(BorderFactory.createEmptyBorder(3, 0, 3, 0));
		}
	}

	public TransactionsFrame(Account account, Transaction transaction) {
		this(account);

		list.setSelectedValue(transaction, true);
	}

	@Override
	public StandardWindow initPostPack() {
		Transaction prototype = ModelFactory.eINSTANCE.createTransaction();
		prototype.setDate(new Date());
		prototype.setDescription("Description");
		prototype.setNumber("Number");
		prototype.setAmount(123456);
		prototype.setTo(null);
		prototype.setFrom(null);
		prototype.setMemo("Testing 1, 2, 3, 4, 5");
		list.setPrototypeCellValue(prototype);

		return super.initPostPack();
	}
	
	public AbstractFrame init(){

		recordButton.addActionListener(this);
		clearButton.addActionListener(this);
		deleteButton.addActionListener(this);

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

		list.addListSelectionListener(new ListSelectionListener(){
			public void valueChanged(ListSelectionEvent arg0) {
				if (!arg0.getValueIsAdjusting() && !TransactionsFrame.this.disableListEvents){
					if (editableTransaction.isChanged() 
							&& editableTransaction.getTransaction() != (Transaction) list.getSelectedValue()){
						int ret;

						if (TransactionController.isRecordValid(
								editableTransaction.getDescription(), 
								editableTransaction.getDate(), 
								editableTransaction.getAmount(), 
								editableTransaction.getTo(), 
								editableTransaction.getFrom(),
								TransactionsFrame.this.account)){
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

//		list.setListData(DataInstance.getInstance().getTransactions(account));
//		editableTransaction.setTransaction(null, true);

		list.ensureIndexIsVisible(list.getModel().getSize() - 1);
		
		return this;
	}
	
	@Override
	public Object closeWindow() {
		PrefsInstance.getInstance().checkWindowSanity();

		WindowAttributes wa = PrefsInstance.getInstance().getPrefs().getWindows().getTransactionsWindow(); 

		wa.setX(this.getX());
		wa.setY(this.getY());
		wa.setHeight(this.getHeight());
		wa.setWidth(this.getWidth());

		PrefsInstance.getInstance().savePrefs();

//		transactionInstances.put(account, null);

		return super.closeWindow();
		
//		this.setVisible(false);
		
//		return this;
	}

	public AbstractFrame updateContent(){
		if (PrefsInstance.getInstance().getPrefs().isShowCreditLimit() 
				&& account != null  
				&& account.getCreditLimit() != 0){
			long amountLeft = (account.getCreditLimit() + account.getBalance());
			double percentLeft = ((double) (account.getCreditLimit() + account.getBalance())) / account.getCreditLimit() * 100.0;

			StringBuffer sb = new StringBuffer();
			sb.append("<html>");
//			if (amountLeft < 0)
//				sb.append("<html><font color='red'>");
			sb.append(Translate.getInstance().get((account.isCredit() ? TranslateKeys.AVAILABLE_CREDIT : TranslateKeys.AVAILABLE_OVERDRAFT)))
			.append(": ")
			.append(FormatterWrapper.getFormattedCurrencyForAccount(amountLeft, account.isCredit()))
			.append(" (")
			.append(Formatter.getDecimalFormat().format(percentLeft))
			.append("%)");
			if (amountLeft < 0)
				sb.append("</font></html>");

			creditRemaining.setText(sb.toString());
		}
		else
			creditRemaining.setText("");

		return updateButtons();
	}

	public AbstractFrame updateButtons(){
		if (editableTransaction == null 
				|| editableTransaction.getTransaction() == null){
			recordButton.setText(Translate.getInstance().get(TranslateKeys.BUTTON_RECORD));
			clearButton.setText(Translate.getInstance().get(TranslateKeys.CLEAR));
			deleteButton.setEnabled(false);
		}
		else{
			recordButton.setText(Translate.getInstance().get(TranslateKeys.BUTTON_UPDATE));
			clearButton.setText(Translate.getInstance().get(TranslateKeys.BUTTON_NEW));
			deleteButton.setEnabled(true);
		}

		return this;
	}

	public Account getAccount(){
		return account;
	}

	public Component getPrintedComponent() {
		return list;
	}

	@Override
	public StandardWindow openWindow() {
		editableTransaction.resetSelection();
		return super.openWindow();
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
//		for (TransactionsFrame tf : getPreloader().getAll()) {
//			if (tf != null)
//				tf.updateContent();
//		}
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
		baseModel.updateNoOrderChange(t);
		editableTransaction.updateClearedAndReconciled();
	}

	/**
	 * Forces a toggle on the Reconciled state, without needing to save manually.
	 */
	public void toggleReconciled(){
		Transaction t = (Transaction) list.getSelectedValue();
		t.setReconciled(!t.isReconciled());
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
		baseModel.addAll(transactions);
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

	public static void reloadModel(){
		baseModel.loadModel(DataInstance.getInstance().getDataModel().getAllTransactions().getTransactions());
		preloader = new TransactionsFramePreLoader();
	}
	
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(recordButton)){
			if (!TransactionController.isRecordValid(
					editableTransaction.getDescription(), 
					editableTransaction.getDate(), 
					editableTransaction.getAmount(), 
					editableTransaction.getTo(), 
					editableTransaction.getFrom(),
					this.account)){
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
			if (recordButton.getText().equals(Translate.getInstance().get(TranslateKeys.BUTTON_RECORD))){
				t = ModelFactory.eINSTANCE.createTransaction();
			}
			else if (recordButton.getText().equals(Translate.getInstance().get(TranslateKeys.BUTTON_UPDATE))){
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

			if (recordButton.getText().equals(Translate.getInstance().get(TranslateKeys.BUTTON_RECORD))) {
				baseModel.add(t);
			}
			else {
				baseModel.update(t, model);
			}

			//Update the autocomplete entries
			if (PrefsInstance.getInstance().getPrefs().isShowAutoComplete()){
				PrefsInstance.getInstance().getDescDict().add(editableTransaction.getDescription());
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
//			ReportFrame.updateAllReportWindows();
//			GraphFrame.updateAllGraphWindows();
//			MainFrame.getInstance().getAccountListPanel().updateContent();
//			MainFrame.getInstance().getCategoryListPanel().updateContent();

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
			
			DocumentController.saveFileSoon();
		}
		else if (e.getSource().equals(clearButton)){
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
		else if (e.getSource().equals(deleteButton)){
			if (JOptionPane.showConfirmDialog(
					TransactionsFrame.this, 
					Translate.getInstance().get(TranslateKeys.DELETE_TRANSACTION_LOSE_CHANGES),
					Translate.getInstance().get(TranslateKeys.DELETE_TRANSACTION),
					JOptionPane.YES_NO_OPTION,
					JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){

				Transaction t = (Transaction) list.getSelectedValue();
				int position = list.getSelectedIndex();
				baseModel.remove(t, model);

				updateAllTransactionWindows();
				updateButtons();
//				MainFrame.getInstance().getAccountListPanel().updateContent();

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
	}

	public StandardWindow clear() {
		return this;
	}
	
	public static TransactionsFramePreLoader getPreloader(){
		if (preloader == null) 
			preloader = new TransactionsFramePreLoader();
		return preloader;
	}
}