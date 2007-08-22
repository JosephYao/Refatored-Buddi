/*
 * Created on May 6, 2006 by wyatt
 */
package org.homeunix.thecave.buddi.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.homeunix.thecave.buddi.Buddi;
import org.homeunix.thecave.buddi.Const;
import org.homeunix.thecave.buddi.i18n.BuddiKeys;
import org.homeunix.thecave.buddi.i18n.keys.ButtonKeys;
import org.homeunix.thecave.buddi.i18n.keys.MessageKeys;
import org.homeunix.thecave.buddi.i18n.keys.TransactionDateFilterKeys;
import org.homeunix.thecave.buddi.model.Account;
import org.homeunix.thecave.buddi.model.DataModel;
import org.homeunix.thecave.buddi.model.Transaction;
import org.homeunix.thecave.buddi.model.prefs.PrefsModel;
import org.homeunix.thecave.buddi.model.swing.TransactionListModel;
import org.homeunix.thecave.buddi.util.InternalFormatter;
import org.homeunix.thecave.buddi.view.menu.bars.TransactionsFrameMenuBar;
import org.homeunix.thecave.buddi.view.swing.TransactionCellRenderer;
import org.homeunix.thecave.buddi.view.swing.TranslatorListCellRenderer;
import org.homeunix.thecave.moss.model.DocumentChangeEvent;
import org.homeunix.thecave.moss.model.DocumentChangeListener;
import org.homeunix.thecave.moss.swing.components.MossSearchField;
import org.homeunix.thecave.moss.swing.components.MossSearchField.SearchTextChangedEvent;
import org.homeunix.thecave.moss.swing.components.MossSearchField.SearchTextChangedEventListener;
import org.homeunix.thecave.moss.swing.window.MossAssociatedDocumentFrame;
import org.homeunix.thecave.moss.util.Log;
import org.homeunix.thecave.moss.util.OperatingSystemUtil;
import org.jdesktop.swingx.JXList;
import org.jdesktop.swingx.decorator.HighlighterFactory;

public class TransactionFrame extends MossAssociatedDocumentFrame implements ActionListener {
	public static final long serialVersionUID = 0;	

	private static final int MIN_WIDTH = 400;
	private static final int MIN_HEIGHT = 200;

	private final JXList list;

	private final TransactionEditor transactionEditor;
	private final JButton recordButton;
	private final JButton clearButton;
	private final JButton deleteButton;
	private final MossSearchField searchField;
	private final JComboBox dateFilterComboBox;

	private final TransactionListModel listModel;

	private final Account associatedAccount;

	private boolean disableListEvents = false;

	public TransactionFrame(MainFrame parent, Account account){
		super(parent, "Transactions" + ((DataModel) parent.getDocument()).getUid() + account.getFullName());
		this.associatedAccount = account;
		this.listModel = new TransactionListModel((DataModel) parent.getDocument(), account);
		
		dateFilterComboBox = new JComboBox();

		//Set up the transaction list.  We don't set the model here, for performance reasons.
		// We set it after we have already established the prototype value.
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

							if (transaction.getNumber() != null){
								if (transaction.getNumber().length() > 0){
									sb.append("<br>");
									sb.append("#");
									sb.append(transaction.getNumber());
								}
							}

							sb.append("<br>");
							if (InternalFormatter.isRed(transaction, transaction.getTo().equals(TransactionFrame.this.associatedAccount)))
								sb.append("<font color='red'>");
							sb.append(InternalFormatter.getFormattedCurrency(transaction.getAmount()));
							if (InternalFormatter.isRed(transaction, transaction.getTo().equals(TransactionFrame.this.associatedAccount)))
								sb.append("</font>");

							sb.append("  ");
							sb.append(transaction.getFrom().getFullName())
							.append(" ")
							.append(PrefsModel.getInstance().getTranslator().get(BuddiKeys.TO))
							.append(" ")
							.append(transaction.getTo().getFullName());

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


		//Set up the editing portion
		transactionEditor = new TransactionEditor((DataModel) parent.getDocument(), associatedAccount, false);

		recordButton = new JButton(PrefsModel.getInstance().getTranslator().get(ButtonKeys.BUTTON_RECORD));
		clearButton = new JButton(PrefsModel.getInstance().getTranslator().get(ButtonKeys.BUTTON_CLEAR));
		deleteButton = new JButton(PrefsModel.getInstance().getTranslator().get(ButtonKeys.BUTTON_DELETE));
		searchField = new MossSearchField(PrefsModel.getInstance().getTranslator().get(BuddiKeys.DEFAULT_SEARCH));
	}

	/**
	 * Opens the window, and selects the given transaction.
	 * @param account
	 * @param transaction
	 */
	public TransactionFrame(MainFrame parent, Account account, Transaction transaction) {
		this(parent, account);

		// Iterate backwards through the list of transacations, looking for the transaction
		// which was just passed in.

		for (int index = this.listModel.getSize() - 1; index >= 0; --index) {
			Transaction transactionToCheck = (Transaction) this.listModel.getElementAt(index);
			if (transactionToCheck.equals(transaction)) {
				list.ensureIndexIsVisible(index);
				list.setSelectedIndex(index);
				break;
			}
		}
		
		this.requestFocusInWindow();
	}

	@Override
	public void initPostPack() {
		super.initPostPack();
		
//		if (getDataModel().getTransactions().size() > 0)
//			list.setPrototypeCellValue(getDataModel().getTransactions().get(0));
//		Transaction prototype = new Transaction(getDataModel(), new Date(), "Description", 123456, getDataModel().getBudgetCategories().get(0), getDataModel().getBudgetCategories().get(0));
//		prototype.setNumber("Number");
//		prototype.setMemo("Testing 1, 2, 3, 4, 5");
//		list.setPrototypeCellValue(prototype);
		list.setPrototypeCellValue(new Transaction((DataModel) getDocument(), new Date(), "Relatively long description", 12345678, null, null));

		list.setModel(listModel);		
		list.ensureIndexIsVisible(listModel.getSize() - 1);

	}

	@Override
	public void init(){
		super.init();
		
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setCellRenderer(new TransactionCellRenderer(associatedAccount, Buddi.isSimpleFont()));

		recordButton.setPreferredSize(new Dimension(Math.max(100, recordButton.getPreferredSize().width), recordButton.getPreferredSize().height));
		clearButton.setPreferredSize(new Dimension(Math.max(100, clearButton.getPreferredSize().width), clearButton.getPreferredSize().height));
		deleteButton.setPreferredSize(new Dimension(Math.max(100, deleteButton.getPreferredSize().width), deleteButton.getPreferredSize().height));
		searchField.setPreferredSize(new Dimension(160, searchField.getPreferredSize().height));
		searchField.setMaximumSize(searchField.getPreferredSize());
		dateFilterComboBox.setPreferredSize(InternalFormatter.getComboBoxSize(dateFilterComboBox));
//		dateFilterComboBox.setMaximumSize(dateFilterComboBox.getPreferredSize());


		JPanel topPanel = new JPanel();//new FlowLayout(FlowLayout.RIGHT));
		topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));
		topPanel.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
		topPanel.add(Box.createHorizontalGlue());
		topPanel.add(new JLabel(PrefsModel.getInstance().getTranslator().get(BuddiKeys.TRANSACTION_FILTER)));
		topPanel.add(dateFilterComboBox);
		topPanel.add(searchField);

		this.getRootPane().setDefaultButton(recordButton);

		JPanel buttonPanelRight = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		buttonPanelRight.add(clearButton);
		buttonPanelRight.add(recordButton);

		JPanel buttonPanelLeft = new JPanel(new FlowLayout(FlowLayout.LEFT));
		buttonPanelLeft.add(deleteButton);

		JPanel buttonPanel = new JPanel(new BorderLayout());
		buttonPanel.add(buttonPanelRight, BorderLayout.EAST);
		buttonPanel.add(buttonPanelLeft, BorderLayout.WEST);

		JScrollPane listScroller = new JScrollPane(list);
		
		JPanel scrollPanel = new JPanel(new BorderLayout());
		scrollPanel.add(topPanel, BorderLayout.NORTH);
		scrollPanel.add(listScroller, BorderLayout.CENTER);
		scrollPanel.add(transactionEditor, BorderLayout.SOUTH);

		JPanel mainPanel = new JPanel(); 
		mainPanel.setLayout(new BorderLayout());

		mainPanel.add(scrollPanel, BorderLayout.CENTER);
		mainPanel.add(buttonPanel, BorderLayout.SOUTH);

		this.setLayout(new BorderLayout());
		this.add(mainPanel, BorderLayout.CENTER);
		
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

		if (OperatingSystemUtil.isMac()){
//			list.putClientProperty("Quaqua.List.style", "striped");
			listScroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
//			listScroller.setBorder(BorderFactory.createCompoundBorder(
//					BorderFactory.createEmptyBorder(5, 10, 5, 10),
//					listScroller.getBorder()));
//			this.getRootPane().setBorder(BorderFactory.createTitledBorder(""));
//			editableTransaction.setBorder(BorderFactory.createEmptyBorder(2, 8, 5, 8));
//			searchField.putClientProperty("Quaqua.Component.visualMargin", new Insets(0,0,0,0));
		}
		else {
			transactionEditor.setBorder(BorderFactory.createEmptyBorder(3, 0, 3, 0));
		}
		
//		availableFilters.add(BuddiKeys.TRANSACTION_FILTER_ALL);
//		availableFilters.add(BuddiKeys.TRANSACTION_FILTER_TODAY);
//		availableFilters.add(BuddiKeys.TRANSACTION_FILTER_THIS_WEEK);
//		availableFilters.add(BuddiKeys.TRANSACTION_FILTER_THIS_MONTH);
//		availableFilters.add(BuddiKeys.TRANSACTION_FILTER_THIS_QUARTER);
//		availableFilters.add(BuddiKeys.TRANSACTION_FILTER_THIS_YEAR);
//		availableFilters.add(BuddiKeys.TRANSACTION_FILTER_LAST_YEAR);
//		if (PrefsModel.getInstance().isShowCleared() || PrefsModel.getInstance().isShowReconciled()){
//			availableFilters.add(null);
//			if (PrefsModel.getInstance().isShowReconciled())
//				availableFilters.add(BuddiKeys.TRANSACTION_FILTER_NOT_RECONCILED);
//			if (PrefsModel.getInstance().isShowCleared())
//				availableFilters.add(BuddiKeys.TRANSACTION_FILTER_NOT_CLEARED);
//		}

		dateFilterComboBox.setModel(new DefaultComboBoxModel(TransactionDateFilterKeys.values()));
		dateFilterComboBox.setRenderer(new TranslatorListCellRenderer());

		dateFilterComboBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (dateFilterComboBox.getSelectedItem() == null){
					if (e.getItem().equals(dateFilterComboBox.getItemAt(0))){
						dateFilterComboBox.setSelectedIndex(1);
					}
					Log.debug("null; e.getItem == " + e.getItem());
					dateFilterComboBox.setSelectedIndex(0);
				}
				if (e.getStateChange() == ItemEvent.SELECTED) {
					listModel.setDateFilter((TransactionDateFilterKeys) dateFilterComboBox.getSelectedItem());
				}
			}			
		});
		
		//Once we have the listeners all set up, we will load the saved filter.
		// Since it is saved a string, and the model contains enum's, we have
		// to check manually.  Yes, it's ugly.  Please let me know if I am 
		// stupid and have missed a completely obvious alternative.
//		String savedFilter = PrefsModel.getInstance().getSelectedFilter(); //TODO Save filter
//		if (savedFilter != null){
//			for (int i = 0; i < availableFilters.size(); i++){
//				if (availableFilters.get(i) != null 
//						&& availableFilters.get(i).toString().equals(savedFilter)){
//					filterComboBox.setSelectedIndex(i);
//					break;
//				}
//			}
//		}
		

		searchField.addSearchTextChangedEventListener(new SearchTextChangedEventListener(){
			public void searchTextChangedEventOccurred(SearchTextChangedEvent evt) {
				listModel.setSearchText(searchField.getText());
//				list.ensureIndexIsVisible(listModel.getSize() - 1);
			}
		});

		list.addListSelectionListener(new ListSelectionListener(){
			public void valueChanged(ListSelectionEvent arg0) {
				if (!arg0.getValueIsAdjusting() && !TransactionFrame.this.disableListEvents){
					//Check if the user has changed the selected transaction
					if (transactionEditor.isChanged() 
							&& transactionEditor.getTransaction() != (Transaction) list.getSelectedValue()){
						int ret;

						if (transactionEditor.isTransactionValid(TransactionFrame.this.associatedAccount)){
							String[] options = new String[2];
							options[0] = PrefsModel.getInstance().getTranslator().get(ButtonKeys.BUTTON_YES);
							options[1] = PrefsModel.getInstance().getTranslator().get(ButtonKeys.BUTTON_NO);

							ret = JOptionPane.showOptionDialog(
									null, 
									PrefsModel.getInstance().getTranslator().get(BuddiKeys.TRANSACTION_CHANGED_MESSAGE), 
									PrefsModel.getInstance().getTranslator().get(BuddiKeys.TRANSACTION_CHANGED_TITLE),
									JOptionPane.YES_NO_CANCEL_OPTION,
									JOptionPane.PLAIN_MESSAGE,
									null,
									options,
									options[0]);
							if (ret == JOptionPane.YES_OPTION){
								recordButton.doClick();
							}
							else if (ret == JOptionPane.NO_OPTION){
								transactionEditor.setChanged(false);
//								list.setSelectedValue(editableTransaction.getTransaction(), true);
//								return;
							}
							else if (ret == JOptionPane.CANCEL_OPTION){
								transactionEditor.setChanged(false);
								list.setSelectedValue(transactionEditor.getTransaction(), true);
								transactionEditor.setChanged(true);
								return;
							}
						}
						else{
							String[] options = new String[2];
							options[0] = PrefsModel.getInstance().getTranslator().get(ButtonKeys.BUTTON_YES);
							options[1] = PrefsModel.getInstance().getTranslator().get(ButtonKeys.BUTTON_NO);

							ret = JOptionPane.showOptionDialog(
									null, 
									PrefsModel.getInstance().getTranslator().get(BuddiKeys.TRANSACTION_CHANGED_INVALID_MESSAGE), 
									PrefsModel.getInstance().getTranslator().get(BuddiKeys.TRANSACTION_CHANGED_TITLE),
									JOptionPane.YES_NO_OPTION,
									JOptionPane.PLAIN_MESSAGE,
									null,
									options,
									options[0]);
							if (ret == JOptionPane.NO_OPTION){
								transactionEditor.setChanged(false);

								if (transactionEditor.getTransaction() == null)
									list.clearSelection();
								else
									list.setSelectedValue(transactionEditor.getTransaction(), true);

								transactionEditor.setChanged(true);
								return;
							}
							else if (ret == JOptionPane.YES_OPTION){
								transactionEditor.setChanged(false);
							}
						}
					}

					if (list.getSelectedValue() instanceof Transaction) {
						Transaction t = (Transaction) list.getSelectedValue();

						transactionEditor.setTransaction(t, false);
						
						Log.debug("Set transaction to " + t);
					}
					else if (list.getSelectedValue() == null){
						transactionEditor.setTransaction(null, false);
						transactionEditor.updateContent();
						
						Log.debug("Set transaction to null");
					}

					updateButtons();
				}
			}
		});

		getDocument().addDocumentChangeListener(new DocumentChangeListener(){
			public void documentChange(DocumentChangeEvent event) {
				listModel.update();
			}
		});
		
		String dataFile = getDocument().getFile() == null ? "" : " - " + getDocument().getFile();
		this.setTitle(PrefsModel.getInstance().getTranslator().get(BuddiKeys.TRANSACTIONS) + " - " + associatedAccount.getFullName() + dataFile + " - " + PrefsModel.getInstance().getTranslator().get(BuddiKeys.BUDDI));
		this.setJMenuBar(new TransactionsFrameMenuBar(this));
		
//		list.setListData(DataInstance.getInstance().getTransactions(account));
//		editableTransaction.setTransaction(null, true);

//		list.ensureIndexIsVisible(list.getModel().getSize() - 1);

//		return this;
	}

	@Override
	public Object closeWindow() {
		PrefsModel.getInstance().setTransactionWindowSize(this.getSize());
		PrefsModel.getInstance().setTransactionWindowLocation(this.getLocation());
		PrefsModel.getInstance().save();

		//TODO Save the filter state
//		PrefsInstance.getInstance().getPrefs().setSelectedFilter(filterComboBox.getSelectedItem().toString());
//		
//		PrefsInstance.getInstance().savePrefs();


		return super.closeWindow();
	}

//	public void updateToFromComboBox(){
//		editableTransaction.updateContent();
//	}

	public void updateButtons(){
		super.updateButtons();
		
		if (transactionEditor == null 
				|| transactionEditor.getTransaction() == null){
			recordButton.setText(PrefsModel.getInstance().getTranslator().get(ButtonKeys.BUTTON_RECORD));
			clearButton.setText(PrefsModel.getInstance().getTranslator().get(ButtonKeys.BUTTON_CLEAR));
			deleteButton.setEnabled(false);
		}
		else{
			recordButton.setText(PrefsModel.getInstance().getTranslator().get(ButtonKeys.BUTTON_UPDATE));
			clearButton.setText(PrefsModel.getInstance().getTranslator().get(ButtonKeys.BUTTON_NEW));
			deleteButton.setEnabled(true);
		}
	}

//	public Account getAssociatedAccount(){
//		return associatedAccount;
//	}

//	public Component getPrintedComponent() {
//	return list;
//	}

//	@Override
//	public StandardWindow openWindow() {
//		editableTransaction.resetSelection();
//		return super.openWindow();
//	}


	/**
	 * Force an update of every transaction window.
	 * 
	 * To plugin writers: you probably don't need to call this manually;
	 * instead, register all changes to Transactions with the methods
	 * addToTransactionListModel(), removeFromTransactionListModel(), and
	 * updateTransactionListModel().  This should fire updates in all open
	 * windows as well as save the data model, do misc. housecleaning, etc.
	 */
	//TODO We probably don't need to do this anymore... double check, though.
//	public static void updateAllTransactionWindows(){
//		for (TransactionsFrame tf : transactionInstances.values()) {
//			if (tf != null){
//				tf.updateContent();
//				tf.updateToFromComboBox();
//			}
//		}
//	}

//	/**
//	 * Gets the filter text in the search box
//	 * @return The contents of the search box
//	 */
//	public String getFilterText(){
//		return searchField.getText();
//	}

//	/**
//	 * Gets the selected item in the filter pulldown
//	 * @return The selected item in the filter pulldown
//	 */
//	public BuddiKeys getFilterComboBox(){
//		return (BuddiKeys) filterComboBox.getSelectedItem();
//	}

	/**
	 * Forces a toggle on the Cleared state, without needing to save manually.
	 */
//	public void toggleCleared(){
//		Transaction t = (Transaction) list.getSelectedValue();
//		t.setCleared(!t.isCleared());
//		baseModel.updateNoOrderChange(t);
//		editableTransaction.updateClearedAndReconciled();
//	}

	/**
	 * Forces a toggle on the Reconciled state, without needing to save manually.
	 */
//	public void toggleReconciled(){
//		Transaction t = (Transaction) list.getSelectedValue();
//		t.setReconciled(!t.isReconciled());
//		baseModel.updateNoOrderChange(t);
//		editableTransaction.updateClearedAndReconciled();
//
//	}

//	public void clickClear(){
//		clearButton.doClick();
//	}
//	public void clickRecord(){
//		recordButton.doClick();
//	}
//	public void clickDelete(){
//		deleteButton.doClick();
//	}

//	/**
//	 * After creating a Collection of new Transactions via 
//	 * DataInstance.getInstance().getDataModelFactory().createTransaction(),
//	 * and filling in all the needed details, you call this method to
//	 * add them to the data model and update all windows automatically.
//	 * 
//	 * Note that you should *not* call DataInstance.getInstance().addTransaction() directly, as
//	 * you will not update the windows properly.
//	 * @param t Transaction to add to the data model
//	 */
//	public static void addToTransactionListModel(Collection<Transaction> transactions){
//		baseModel.addAll(transactions);
//	}
//
//	/**
//	 * After creating a new Transaction via DataInstance.getInstance().getDataModelFactory().createTransaction(),
//	 * and filling in all the needed details, you call this method to
//	 * add it to the data model and update all windows automatically.
//	 * 
//	 * Note that you should *not* call DataInstance.getInstance().addTransaction() directly, as
//	 * you will not update the windows properly.
//	 * @param t Transaction to add to the data model
//	 */
//	public static void addToTransactionListModel(Transaction t){
//		baseModel.add(t);
//	}
//
//	/**
//	 * Remove a transaction from the data model and all open windows.
//	 * 
//	 * Note that you should *not* call DataInstance.getInstance().deleteTransaction() directly, as
//	 * you will not update the windows properly.
//	 * @param t Transaction to delete
//	 * @param fdlm The filtered dynamic list model in which the transaction exists.  If you 
//	 * don't have this, you can use null, although you should be aware that there may be some
//	 * problems updating transaction windows with the new data, as the windows will not
//	 * have the update() method called on their FilteredDynamicListModels. 
//	 */
//	public static void removeFromTransactionListModel(Transaction t, FilteredDynamicListModel fdlm){
//		baseModel.remove(t, fdlm);
//	}
//
//	/**
//	 * Notifies all windows that a transaction has been updated.  If you 
//	 * change a transaction and do not register it here after all the changes
//	 * are complete, you will not get the transaction updated in the 
//	 * Transaction windows.
//	 * 
//	 * @param t Transaction to update
//	 * @param fdlm The filtered dynamic list model in which the transaction exists.  If you 
//	 * don't have this, you can use null, although you should be aware that there may be some
//	 * problems updating transaction windows with the new data, as the windows will not
//	 * have the update() method called on their FilteredDynamicListModels. 
//	 */
//	public static void updateTransactionListModel(Transaction t, FilteredDynamicListModel fdlm){
//		baseModel.update(t, fdlm);
//	}
//
//	public static void reloadModel(){
//		baseModel.loadModel(DataInstance.getInstance().getDataModel().getAllTransactions().getTransactions());
//	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(recordButton)){
			if (!transactionEditor.isTransactionValid(this.associatedAccount)){
				String[] options = new String[1];
				options[0] = PrefsModel.getInstance().getTranslator().get(ButtonKeys.BUTTON_OK);

				JOptionPane.showOptionDialog(
						TransactionFrame.this,
						PrefsModel.getInstance().getTranslator().get(BuddiKeys.RECORD_BUTTON_ERROR),
						PrefsModel.getInstance().getTranslator().get(BuddiKeys.ERROR),
						JOptionPane.DEFAULT_OPTION,
						JOptionPane.ERROR_MESSAGE,
						null,
						options,
						options[0]
				);
				return;
			}

			//Don't fire updates for a while
			getDocument().startBatchChange();

			Transaction t;
			boolean isUpdate;
			
			if (recordButton.getText().equals(PrefsModel.getInstance().getTranslator().get(ButtonKeys.BUTTON_UPDATE)))
				isUpdate = true;
			else if (recordButton.getText().equals(PrefsModel.getInstance().getTranslator().get(ButtonKeys.BUTTON_RECORD)))
				isUpdate = false;
			else {
				Log.error("Unknown record button state: " + recordButton.getText());
				return;
			}
			
			if (isUpdate && transactionEditor.isDangerouslyChanged()) {
				String[] options = new String[2];
				options[0] = PrefsModel.getInstance().getTranslator().get(ButtonKeys.BUTTON_CREATE_NEW_TRANSACTION);
				options[1] = PrefsModel.getInstance().getTranslator().get(ButtonKeys.BUTTON_OVERWRITE_TRANSACTON);
				
				int ret = JOptionPane.showOptionDialog(
						null, 
						PrefsModel.getInstance().getTranslator().get(MessageKeys.MESSAGE_CHANGE_EXISTING_TRANSACTION),
						PrefsModel.getInstance().getTranslator().get(MessageKeys.MESSAGE_CHANGE_EXISTING_TRANSACTION_TITLE),
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
				t = transactionEditor.getTransaction();
			} else {
				t = transactionEditor.getNewTransaction();
				getDataModel().addTransaction(t);
			}

			getDataModel().updateAllBalances();
			
			getDataModel().finishBatchChange();
			
			//TODO This should be done via a listener on AccountFrame.
//			MainFrame.getInstance().getAccountListPanel().updateNetWorth();

			transactionEditor.setTransaction(null, true);
			list.clearSelection();
			updateButtons();

			// Scroll the list view to make sure the newly-created transaction is visible.
			if (!isUpdate){
				Date currentTransactionDate = t.getDate();

				// Iterate backwards through the list of transacations, looking for a transaction
				// with the most recently inserted date, and scroll to that transaction's index.
				// For the common case of inserted new transactions near the current day, this
				// will be almost as fast as just scrolling to the end of the list, but always correct.

				for (int index = listModel.getSize() - 1; index >= 0; --index) {
					Transaction transactionToCheck = (Transaction) listModel.getElementAt(index);
					if (transactionToCheck.getDate().equals(currentTransactionDate)) {
						list.ensureIndexIsVisible(index);
						break;
					}
				}

			}

			disableListEvents = false;

			transactionEditor.resetSelection();
			transactionEditor.setChanged(false);

//			DocumentController.saveFileSoon();
		}
		else if (e.getSource().equals(clearButton)){
			String[] options = new String[2];
			options[0] = PrefsModel.getInstance().getTranslator().get(ButtonKeys.BUTTON_YES);
			options[1] = PrefsModel.getInstance().getTranslator().get(ButtonKeys.BUTTON_NO);

			if (!transactionEditor.isChanged()
					|| JOptionPane.showOptionDialog(
							TransactionFrame.this,
							PrefsModel.getInstance().getTranslator().get(BuddiKeys.CLEAR_TRANSACTION_LOSE_CHANGES),
							PrefsModel.getInstance().getTranslator().get(BuddiKeys.CLEAR_TRANSACTION),
							JOptionPane.YES_NO_OPTION,
							JOptionPane.QUESTION_MESSAGE,
							null,
							options,
							options[0]) == JOptionPane.YES_OPTION){

				transactionEditor.setTransaction(null, true);
				transactionEditor.updateContent();
				list.ensureIndexIsVisible(list.getModel().getSize() - 1);
				list.clearSelection();

				updateButtons();
			}
		}
		else if (e.getSource().equals(deleteButton)){
			String[] options = new String[2];
			options[0] = PrefsModel.getInstance().getTranslator().get(ButtonKeys.BUTTON_YES);
			options[1] = PrefsModel.getInstance().getTranslator().get(ButtonKeys.BUTTON_NO);

			if (JOptionPane.showOptionDialog(
					TransactionFrame.this, 
					PrefsModel.getInstance().getTranslator().get(BuddiKeys.DELETE_TRANSACTION_LOSE_CHANGES),
					PrefsModel.getInstance().getTranslator().get(BuddiKeys.DELETE_TRANSACTION),
					JOptionPane.YES_NO_OPTION,
					JOptionPane.QUESTION_MESSAGE,
					null,
					options,
					options[0]) == JOptionPane.YES_OPTION){

				Transaction t = (Transaction) list.getSelectedValue();
				int position = list.getSelectedIndex();
//				baseModel.remove(t, listModel);

//				updateAllTransactionWindows();
				updateButtons();
//				MainFrame.getInstance().getAccountListPanel().updateContent();

				list.setSelectedIndex(position);
				if (list.getSelectedValue() instanceof Transaction){
					t = (Transaction) list.getSelectedValue();
					transactionEditor.setTransaction(t, true);
					list.ensureIndexIsVisible(position);
				}
				else{
					transactionEditor.setTransaction(null, true);
					list.clearSelection();
				}

				transactionEditor.setChanged(false);

//				DocumentController.saveFileSoon();
			}
		}
	}
	
	private DataModel getDataModel(){
		return (DataModel) getDocument();
	}
}