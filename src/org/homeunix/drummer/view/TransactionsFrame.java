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
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
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

import net.sourceforge.buddi.api.manager.DataManager;
import net.sourceforge.buddi.api.manager.ImportManager;
import net.sourceforge.buddi.impl_2_4.manager.ImportManagerImpl;

import org.homeunix.drummer.Buddi;
import org.homeunix.drummer.Const;
import org.homeunix.drummer.controller.AutoCompleteController;
import org.homeunix.drummer.controller.DocumentController;
import org.homeunix.drummer.controller.SourceController;
import org.homeunix.drummer.controller.TransactionController;
import org.homeunix.drummer.controller.Translate;
import org.homeunix.drummer.controller.TranslateKeys;
import org.homeunix.drummer.model.Account;
import org.homeunix.drummer.model.DataInstance;
import org.homeunix.drummer.model.ModelFactory;
import org.homeunix.drummer.model.Transaction;
import org.homeunix.drummer.prefs.PrefsInstance;
import org.homeunix.drummer.prefs.WindowAttributes;
import org.homeunix.drummer.util.BuddiInternalFormatter;
import org.homeunix.drummer.view.components.EditableTransaction;
import org.homeunix.drummer.view.components.TransactionCellRenderer;
import org.homeunix.drummer.view.components.TransactionCellRendererSimple;
import org.homeunix.drummer.view.model.TransactionListModel;
import org.homeunix.thecave.moss.gui.JSearchField;
import org.homeunix.thecave.moss.gui.JSearchField.SearchTextChangedEvent;
import org.homeunix.thecave.moss.gui.JSearchField.SearchTextChangedEventListener;
import org.homeunix.thecave.moss.gui.abstractwindows.AbstractFrame;
import org.homeunix.thecave.moss.gui.abstractwindows.StandardWindow;
import org.homeunix.thecave.moss.util.Formatter;
import org.homeunix.thecave.moss.util.Log;
import org.homeunix.thecave.moss.util.OperatingSystemUtil;
import org.jdesktop.swingx.JXList;
import org.jdesktop.swingx.decorator.HighlighterFactory;

import de.schlichtherle.swing.filter.FilteredDynamicListModel;

public class TransactionsFrame extends AbstractBuddiFrame {
	public static final long serialVersionUID = 0;	

	private static final int MIN_WIDTH = 400;
	private static final int MIN_HEIGHT = 200;

	private static final Map<Account, TransactionsFrame> transactionInstances = new HashMap<Account, TransactionsFrame>();
//	private static TransactionsFramePreLoader preloader = null;

	private final JXList list;
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
	private final static TransactionListModel baseModel = new TransactionListModel(DataInstance.getInstance().getDataModel() != null ? DataInstance.getInstance().getDataModel().getAllTransactions().getTransactions() : ModelFactory.eINSTANCE.createTransactions().getTransactions());

	/* This model is a filtered list model that is obtained from the
	 * baseModel.  It is a view which contains all transactions in
	 * the given account which match the String and Date criteria.
	 */
	private final FilteredDynamicListModel model;

	private final Account account;

	private boolean disableListEvents = false;

	//The values for the date chooser combo box.
	private final Vector<TranslateKeys> availableFilters;


	public TransactionsFrame(Account account){
		this.account = account;

		if (transactionInstances.get(account) != null)
			transactionInstances.get(account).setVisible(false);

		transactionInstances.put(account, this);

		availableFilters = new Vector<TranslateKeys>();
		filterComboBox = new JComboBox();
		creditRemaining = new JLabel();

		//Set up the transaction list
		list = new JXList(){
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
							if (BuddiInternalFormatter.isRed(transaction, transaction.getTo().equals(TransactionsFrame.this.account)))
								sb.append("<font color='red'>");
							sb.append(BuddiInternalFormatter.getFormattedCurrency(transaction.getAmount()));
							if (BuddiInternalFormatter.isRed(transaction, transaction.getTo().equals(TransactionsFrame.this.account)))
								sb.append("</font>");

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

		if (Buddi.isSimpleFont()){
			TransactionCellRendererSimple renderer = new TransactionCellRendererSimple(account);
			list.setCellRenderer(renderer);
		}
		else {
			TransactionCellRenderer renderer = new TransactionCellRenderer(account);
			list.setCellRenderer(renderer);			
		}

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
		searchField.setMaximumSize(searchField.getPreferredSize());
		filterComboBox.setPreferredSize(new Dimension(100, filterComboBox.getPreferredSize().height));
		filterComboBox.setMaximumSize(filterComboBox.getPreferredSize());

		model = baseModel.getFilteredListModel(account, this);
//		list.setModel(model);

		JPanel topPanel = new JPanel();//new FlowLayout(FlowLayout.RIGHT));
		topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));
		topPanel.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
		topPanel.add(creditRemaining);
		topPanel.add(Box.createHorizontalGlue());
		topPanel.add(new JLabel(Translate.getInstance().get(TranslateKeys.TRANSACTION_FILTER)));
		topPanel.add(filterComboBox);
		topPanel.add(searchField);

//		JPanel creditRemainingPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
//		creditRemainingPanel.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
//		creditRemainingPanel.add(creditRemaining);

//		JPanel topPanel = new JPanel(new BorderLayout());
//		topPanel.add(searchPanel, BorderLayout.EAST);
//		topPanel.add(creditRemainingPanel, BorderLayout.WEST);

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

	/**
	 * Opens the window, and selects the given transaction.
	 * @param account
	 * @param transaction
	 */
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

		list.setModel(model);		
		list.ensureIndexIsVisible(model.getSize() - 1);

		return super.initPostPack();
	}

	public AbstractFrame init(){

		recordButton.addActionListener(this);
		clearButton.addActionListener(this);
		deleteButton.addActionListener(this);

		this.addComponentListener(new ComponentAdapter(){
			public void componentResized(ComponentEvent e) {
				if (e.getComponent().getWidth() < MIN_WIDTH)
					e.getComponent().setSize(MIN_WIDTH, e.getComponent().getHeight());
				if (e.getComponent().getHeight() < MIN_HEIGHT)
					e.getComponent().setSize(e.getComponent().getWidth(), MIN_HEIGHT);
			}
		});

		list.addHighlighter(HighlighterFactory.createAlternateStriping(Const.COLOR_EVEN_ROW, Const.COLOR_ODD_ROW));

		availableFilters.add(TranslateKeys.TRANSACTION_FILTER_ALL);
		availableFilters.add(TranslateKeys.TRANSACTION_FILTER_TODAY);
		availableFilters.add(TranslateKeys.TRANSACTION_FILTER_THIS_WEEK);
		availableFilters.add(TranslateKeys.TRANSACTION_FILTER_THIS_MONTH);
		availableFilters.add(TranslateKeys.TRANSACTION_FILTER_THIS_QUARTER);
		availableFilters.add(TranslateKeys.TRANSACTION_FILTER_THIS_YEAR);
		availableFilters.add(TranslateKeys.TRANSACTION_FILTER_LAST_YEAR);
		if (PrefsInstance.getInstance().getPrefs().isShowAdvanced()){
			availableFilters.add(null);
			availableFilters.add(TranslateKeys.TRANSACTION_FILTER_NOT_RECONCILED);
			availableFilters.add(TranslateKeys.TRANSACTION_FILTER_NOT_CLEARED);
		}

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
		
		//Once we have the listeners all set up, we will load the saved filter.
		// Since it is saved a string, and the model contains enum's, we have
		// to check manually.  Yes, it's ugly.  Please let me know if I am 
		// stupid and have missed a completely obvious alternative.
		String savedFilter = PrefsInstance.getInstance().getPrefs().getSelectedFilter();
		if (savedFilter != null){
			for (int i = 0; i < availableFilters.size(); i++){
				if (availableFilters.get(i) != null 
						&& availableFilters.get(i).toString().equals(savedFilter)){
					filterComboBox.setSelectedIndex(i);
					break;
				}
			}
		}
		

		searchField.addSearchTextChangedEventListener(new SearchTextChangedEventListener(){
			public void searchTextChangedEventOccurred(SearchTextChangedEvent evt) {
				model.update();
				list.ensureIndexIsVisible(model.getSize() - 1);
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
							String[] options = new String[2];
							options[0] = Translate.getInstance().get(TranslateKeys.BUTTON_YES);
							options[1] = Translate.getInstance().get(TranslateKeys.BUTTON_NO);

							ret = JOptionPane.showOptionDialog(
									null, 
									Translate.getInstance().get(TranslateKeys.TRANSACTION_CHANGED_MESSAGE), 
									Translate.getInstance().get(TranslateKeys.TRANSACTION_CHANGED_TITLE),
									JOptionPane.YES_NO_CANCEL_OPTION,
									JOptionPane.PLAIN_MESSAGE,
									null,
									options,
									options[0]);
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
							String[] options = new String[2];
							options[0] = Translate.getInstance().get(TranslateKeys.BUTTON_YES);
							options[1] = Translate.getInstance().get(TranslateKeys.BUTTON_NO);

							ret = JOptionPane.showOptionDialog(
									null, 
									Translate.getInstance().get(TranslateKeys.TRANSACTION_CHANGED_INVALID_MESSAGE), 
									Translate.getInstance().get(TranslateKeys.TRANSACTION_CHANGED_TITLE),
									JOptionPane.YES_NO_OPTION,
									JOptionPane.PLAIN_MESSAGE,
									null,
									options,
									options[0]);
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

//		list.ensureIndexIsVisible(list.getModel().getSize() - 1);

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

		//Save the filter state
		PrefsInstance.getInstance().getPrefs().setSelectedFilter(filterComboBox.getSelectedItem().toString());
		
		PrefsInstance.getInstance().savePrefs();

		transactionInstances.put(account, null);

		return super.closeWindow();

//		this.setVisible(false);

//		return this;
	}

	public AbstractFrame updateContent(){
		if (PrefsInstance.getInstance().getPrefs().isShowCreditLimit() 
				&& account != null  
				&& account.getCreditLimit() != 0){
			long amountLeft;
			//We need to use different logic for debit and credit accounts.  
			// Remember that getBalance() returns a negative number for 
			// credit accounts
//			if (account.isCredit())
			amountLeft = (account.getCreditLimit() + account.getBalance());
//			else
//			amountLeft = (account.getBalance() - account.getCreditLimit());
			double percentLeft = ((double) amountLeft) / account.getCreditLimit() * 100.0;

			StringBuffer sb = new StringBuffer();
			sb.append("<html>");
//			if (amountLeft < 0)
//			sb.append("<html><font color='red'>");
			sb.append(Translate.getInstance().get((account.isCredit() ? TranslateKeys.AVAILABLE_CREDIT : TranslateKeys.AVAILABLE_FUNDS)))
			.append(": ")
			.append(BuddiInternalFormatter.isRed(account, amountLeft) ? "<font color='red'>" : "")
			.append(BuddiInternalFormatter.getFormattedCurrency(amountLeft))
			.append(BuddiInternalFormatter.isRed(account, (long) percentLeft) ? "</font>" : "")
			.append(" (")
			.append(Formatter.getDecimalFormat().format(percentLeft))
			.append("%)");
			if (amountLeft < 0)
				sb.append("</font></html>");

			creditRemaining.setText(sb.toString());
			if (!account.isCredit())
				creditRemaining.setToolTipText(Translate.getInstance().get(TranslateKeys.TOOLTIP_AVAILABLE_FUNDS));
		}
		else {
			creditRemaining.setText("");
			creditRemaining.setToolTipText("");
		}

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

//	public Component getPrintedComponent() {
//	return list;
//	}

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
		for (TransactionsFrame tf : transactionInstances.values()) {
			if (tf != null)
				tf.updateContent();
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
	public TranslateKeys getFilterComboBox(){
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
				String[] options = new String[1];
				options[0] = Translate.getInstance().get(TranslateKeys.BUTTON_OK);

				JOptionPane.showOptionDialog(
						TransactionsFrame.this,
						Translate.getInstance().get(TranslateKeys.RECORD_BUTTON_ERROR),
						Translate.getInstance().get(TranslateKeys.ERROR),
						JOptionPane.DEFAULT_OPTION,
						JOptionPane.ERROR_MESSAGE,
						null,
						options,
						options[0]
				);
				return;
			}

			disableListEvents = true;

			Transaction t;
			
			boolean isUpdate;
			
			if (recordButton.getText().equals(Translate.getInstance().get(TranslateKeys.BUTTON_UPDATE)))
				isUpdate = true;
			else if (recordButton.getText().equals(Translate.getInstance().get(TranslateKeys.BUTTON_RECORD)))
				isUpdate = false;
			else {
				Log.error("Unknown record button state: " + recordButton.getText());
				return;
			}
			
			if (isUpdate && editableTransaction.isDangerouslyChanged()) {
				String[] options = new String[2];
				options[0] = Translate.getInstance().get(TranslateKeys.BUTTON_CREATE_NEW_TRANSACTION);
				options[1] = Translate.getInstance().get(TranslateKeys.BUTTON_OVERWRITE_TRANSACTON);
				
				int ret = JOptionPane.showOptionDialog(
						null, 
						Translate.getInstance().get(TranslateKeys.MESSAGE_CHANGE_EXISTING_TRANSACTION),
						Translate.getInstance().get(TranslateKeys.MESSAGE_CHANGE_EXISTING_TRANSACTION_TITLE),
						JOptionPane.YES_NO_OPTION,
						JOptionPane.WARNING_MESSAGE,
						null,
						options,
						options[0]);
				
			    if (ret == JOptionPane.YES_OPTION){ // create new transaction
					isUpdate = false;
				} // else continue with update
			}
			
			if(isUpdate) {
				t = editableTransaction.getTransaction();
			} else {
				t = ModelFactory.eINSTANCE.createTransaction();
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

			if (isUpdate) {
				baseModel.update(t, model);
			} else {
				baseModel.add(t);
			}

			//Update the autocomplete entries
			if (PrefsInstance.getInstance().getPrefs().isShowAutoComplete())
				AutoCompleteController.setAutoCompleteEntry(t);

			SourceController.calculateAllBalances();
			updateAllTransactionWindows();
			MainFrame.getInstance().getAccountListPanel().updateNetWorth();

			editableTransaction.setTransaction(null, true);
			list.clearSelection();
			updateButtons();

			// Scroll the list view to make sure the newly-created transaction is visible.
			if (!isUpdate){
				Date currentTransactionDate = t.getDate();

				// Iterate backwards through the list of transacations, looking for a transaction
				// with the most recently inserted date, and scroll to that transaction's index.
				// For the common case of inserted new transactions near the current day, this
				// will be almost as fast as just scrolling to the end of the list, but always correct.

				for (int index = model.getSize() - 1; index >= 0; --index) {
					Transaction transactionToCheck = (Transaction) model.getElementAt(index);
					if (transactionToCheck.getDate().equals(currentTransactionDate)) {
						list.ensureIndexIsVisible(index);
						break;
					}
				}

			}

			disableListEvents = false;

			editableTransaction.resetSelection();
			editableTransaction.setChanged(false);

			DocumentController.saveFileSoon();
		}
		else if (e.getSource().equals(clearButton)){
			String[] options = new String[2];
			options[0] = Translate.getInstance().get(TranslateKeys.BUTTON_YES);
			options[1] = Translate.getInstance().get(TranslateKeys.BUTTON_NO);

			if (!editableTransaction.isChanged()
					|| JOptionPane.showOptionDialog(
							TransactionsFrame.this,
							Translate.getInstance().get(TranslateKeys.CLEAR_TRANSACTION_LOSE_CHANGES),
							Translate.getInstance().get(TranslateKeys.CLEAR_TRANSACTION),
							JOptionPane.YES_NO_OPTION,
							JOptionPane.QUESTION_MESSAGE,
							null,
							options,
							options[0]) == JOptionPane.YES_OPTION){

				editableTransaction.setTransaction(null, true);
				editableTransaction.updateContent();
				list.ensureIndexIsVisible(list.getModel().getSize() - 1);
				list.clearSelection();

				updateButtons();
			}
		}
		else if (e.getSource().equals(deleteButton)){
			String[] options = new String[2];
			options[0] = Translate.getInstance().get(TranslateKeys.BUTTON_YES);
			options[1] = Translate.getInstance().get(TranslateKeys.BUTTON_NO);

			if (JOptionPane.showOptionDialog(
					TransactionsFrame.this, 
					Translate.getInstance().get(TranslateKeys.DELETE_TRANSACTION_LOSE_CHANGES),
					Translate.getInstance().get(TranslateKeys.DELETE_TRANSACTION),
					JOptionPane.YES_NO_OPTION,
					JOptionPane.QUESTION_MESSAGE,
					null,
					options,
					options[0]) == JOptionPane.YES_OPTION){

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

				DocumentController.saveFileSoon();
			}
		}
	}

	public StandardWindow clear() {
		return this;
	}

	@Override
	public DataManager getDataManager() {
		return getImportManager();
	}

	@Override
	public ImportManager getImportManager() {
		return new ImportManagerImpl(getAccount(), null, (Transaction) list.getSelectedValue());
	}
}