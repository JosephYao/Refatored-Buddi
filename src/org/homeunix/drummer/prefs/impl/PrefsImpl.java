/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.homeunix.drummer.prefs.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.homeunix.drummer.prefs.DictEntry;
import org.homeunix.drummer.prefs.Intervals;
import org.homeunix.drummer.prefs.ListEntry;
import org.homeunix.drummer.prefs.Prefs;
import org.homeunix.drummer.prefs.PrefsPackage;
import org.homeunix.drummer.prefs.Version;
import org.homeunix.drummer.prefs.WindowAttributes;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Prefs</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.homeunix.drummer.prefs.impl.PrefsImpl#getDataFile <em>Data File</em>}</li>
 *   <li>{@link org.homeunix.drummer.prefs.impl.PrefsImpl#getLanguage <em>Language</em>}</li>
 *   <li>{@link org.homeunix.drummer.prefs.impl.PrefsImpl#isShowDeletedAccounts <em>Show Deleted Accounts</em>}</li>
 *   <li>{@link org.homeunix.drummer.prefs.impl.PrefsImpl#isShowDeletedCategories <em>Show Deleted Categories</em>}</li>
 *   <li>{@link org.homeunix.drummer.prefs.impl.PrefsImpl#getDateFormat <em>Date Format</em>}</li>
 *   <li>{@link org.homeunix.drummer.prefs.impl.PrefsImpl#getBudgetPeriod <em>Budget Period</em>}</li>
 *   <li>{@link org.homeunix.drummer.prefs.impl.PrefsImpl#isShowAccountTypes <em>Show Account Types</em>}</li>
 *   <li>{@link org.homeunix.drummer.prefs.impl.PrefsImpl#isEnableUpdateNotifications <em>Enable Update Notifications</em>}</li>
 *   <li>{@link org.homeunix.drummer.prefs.impl.PrefsImpl#getSelectedInterval <em>Selected Interval</em>}</li>
 *   <li>{@link org.homeunix.drummer.prefs.impl.PrefsImpl#isShowAutoComplete <em>Show Auto Complete</em>}</li>
 *   <li>{@link org.homeunix.drummer.prefs.impl.PrefsImpl#getCurrencySymbol <em>Currency Symbol</em>}</li>
 *   <li>{@link org.homeunix.drummer.prefs.impl.PrefsImpl#isShowCreditLimit <em>Show Credit Limit</em>}</li>
 *   <li>{@link org.homeunix.drummer.prefs.impl.PrefsImpl#isShowInterestRate <em>Show Interest Rate</em>}</li>
 *   <li>{@link org.homeunix.drummer.prefs.impl.PrefsImpl#getReportsWindow <em>Reports Window</em>}</li>
 *   <li>{@link org.homeunix.drummer.prefs.impl.PrefsImpl#getDescDict <em>Desc Dict</em>}</li>
 *   <li>{@link org.homeunix.drummer.prefs.impl.PrefsImpl#getIntervals <em>Intervals</em>}</li>
 *   <li>{@link org.homeunix.drummer.prefs.impl.PrefsImpl#getListEntries <em>List Entries</em>}</li>
 *   <li>{@link org.homeunix.drummer.prefs.impl.PrefsImpl#getTransactionsWindow <em>Transactions Window</em>}</li>
 *   <li>{@link org.homeunix.drummer.prefs.impl.PrefsImpl#getGraphsWindow <em>Graphs Window</em>}</li>
 *   <li>{@link org.homeunix.drummer.prefs.impl.PrefsImpl#getMainWindow <em>Main Window</em>}</li>
 *   <li>{@link org.homeunix.drummer.prefs.impl.PrefsImpl#getLastVersionRun <em>Last Version Run</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class PrefsImpl extends EObjectImpl implements Prefs {
	/**
	 * The default value of the '{@link #getDataFile() <em>Data File</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDataFile()
	 * @generated
	 * @ordered
	 */
	protected static final String DATA_FILE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getDataFile() <em>Data File</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDataFile()
	 * @generated
	 * @ordered
	 */
	protected String dataFile = DATA_FILE_EDEFAULT;

	/**
	 * The default value of the '{@link #getLanguage() <em>Language</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLanguage()
	 * @generated
	 * @ordered
	 */
	protected static final String LANGUAGE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getLanguage() <em>Language</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLanguage()
	 * @generated
	 * @ordered
	 */
	protected String language = LANGUAGE_EDEFAULT;

	/**
	 * The default value of the '{@link #isShowDeletedAccounts() <em>Show Deleted Accounts</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isShowDeletedAccounts()
	 * @generated
	 * @ordered
	 */
	protected static final boolean SHOW_DELETED_ACCOUNTS_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isShowDeletedAccounts() <em>Show Deleted Accounts</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isShowDeletedAccounts()
	 * @generated
	 * @ordered
	 */
	protected boolean showDeletedAccounts = SHOW_DELETED_ACCOUNTS_EDEFAULT;

	/**
	 * The default value of the '{@link #isShowDeletedCategories() <em>Show Deleted Categories</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isShowDeletedCategories()
	 * @generated
	 * @ordered
	 */
	protected static final boolean SHOW_DELETED_CATEGORIES_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isShowDeletedCategories() <em>Show Deleted Categories</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isShowDeletedCategories()
	 * @generated
	 * @ordered
	 */
	protected boolean showDeletedCategories = SHOW_DELETED_CATEGORIES_EDEFAULT;

	/**
	 * The default value of the '{@link #getDateFormat() <em>Date Format</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDateFormat()
	 * @generated
	 * @ordered
	 */
	protected static final String DATE_FORMAT_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getDateFormat() <em>Date Format</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDateFormat()
	 * @generated
	 * @ordered
	 */
	protected String dateFormat = DATE_FORMAT_EDEFAULT;

	/**
	 * The default value of the '{@link #getBudgetPeriod() <em>Budget Period</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBudgetPeriod()
	 * @generated
	 * @ordered
	 */
	protected static final String BUDGET_PERIOD_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getBudgetPeriod() <em>Budget Period</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBudgetPeriod()
	 * @generated
	 * @ordered
	 */
	protected String budgetPeriod = BUDGET_PERIOD_EDEFAULT;

	/**
	 * The default value of the '{@link #isShowAccountTypes() <em>Show Account Types</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isShowAccountTypes()
	 * @generated
	 * @ordered
	 */
	protected static final boolean SHOW_ACCOUNT_TYPES_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isShowAccountTypes() <em>Show Account Types</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isShowAccountTypes()
	 * @generated
	 * @ordered
	 */
	protected boolean showAccountTypes = SHOW_ACCOUNT_TYPES_EDEFAULT;

	/**
	 * The default value of the '{@link #isEnableUpdateNotifications() <em>Enable Update Notifications</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isEnableUpdateNotifications()
	 * @generated
	 * @ordered
	 */
	protected static final boolean ENABLE_UPDATE_NOTIFICATIONS_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isEnableUpdateNotifications() <em>Enable Update Notifications</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isEnableUpdateNotifications()
	 * @generated
	 * @ordered
	 */
	protected boolean enableUpdateNotifications = ENABLE_UPDATE_NOTIFICATIONS_EDEFAULT;

	/**
	 * The default value of the '{@link #getSelectedInterval() <em>Selected Interval</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSelectedInterval()
	 * @generated
	 * @ordered
	 */
	protected static final String SELECTED_INTERVAL_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getSelectedInterval() <em>Selected Interval</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSelectedInterval()
	 * @generated
	 * @ordered
	 */
	protected String selectedInterval = SELECTED_INTERVAL_EDEFAULT;

	/**
	 * The default value of the '{@link #isShowAutoComplete() <em>Show Auto Complete</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isShowAutoComplete()
	 * @generated
	 * @ordered
	 */
	protected static final boolean SHOW_AUTO_COMPLETE_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isShowAutoComplete() <em>Show Auto Complete</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isShowAutoComplete()
	 * @generated
	 * @ordered
	 */
	protected boolean showAutoComplete = SHOW_AUTO_COMPLETE_EDEFAULT;

	/**
	 * The default value of the '{@link #getCurrencySymbol() <em>Currency Symbol</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCurrencySymbol()
	 * @generated
	 * @ordered
	 */
	protected static final String CURRENCY_SYMBOL_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getCurrencySymbol() <em>Currency Symbol</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCurrencySymbol()
	 * @generated
	 * @ordered
	 */
	protected String currencySymbol = CURRENCY_SYMBOL_EDEFAULT;

	/**
	 * The default value of the '{@link #isShowCreditLimit() <em>Show Credit Limit</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isShowCreditLimit()
	 * @generated
	 * @ordered
	 */
	protected static final boolean SHOW_CREDIT_LIMIT_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isShowCreditLimit() <em>Show Credit Limit</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isShowCreditLimit()
	 * @generated
	 * @ordered
	 */
	protected boolean showCreditLimit = SHOW_CREDIT_LIMIT_EDEFAULT;

	/**
	 * The default value of the '{@link #isShowInterestRate() <em>Show Interest Rate</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isShowInterestRate()
	 * @generated
	 * @ordered
	 */
	protected static final boolean SHOW_INTEREST_RATE_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isShowInterestRate() <em>Show Interest Rate</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isShowInterestRate()
	 * @generated
	 * @ordered
	 */
	protected boolean showInterestRate = SHOW_INTEREST_RATE_EDEFAULT;

	/**
	 * The cached value of the '{@link #getReportsWindow() <em>Reports Window</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getReportsWindow()
	 * @generated
	 * @ordered
	 */
	protected WindowAttributes reportsWindow = null;

	/**
	 * The cached value of the '{@link #getDescDict() <em>Desc Dict</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDescDict()
	 * @generated
	 * @ordered
	 */
	protected EList descDict = null;

	/**
	 * The cached value of the '{@link #getIntervals() <em>Intervals</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIntervals()
	 * @generated
	 * @ordered
	 */
	protected Intervals intervals = null;

	/**
	 * The cached value of the '{@link #getListEntries() <em>List Entries</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getListEntries()
	 * @generated
	 * @ordered
	 */
	protected EList listEntries = null;

	/**
	 * The cached value of the '{@link #getTransactionsWindow() <em>Transactions Window</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTransactionsWindow()
	 * @generated
	 * @ordered
	 */
	protected WindowAttributes transactionsWindow = null;

	/**
	 * The cached value of the '{@link #getGraphsWindow() <em>Graphs Window</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getGraphsWindow()
	 * @generated
	 * @ordered
	 */
	protected WindowAttributes graphsWindow = null;

	/**
	 * The cached value of the '{@link #getMainWindow() <em>Main Window</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMainWindow()
	 * @generated
	 * @ordered
	 */
	protected WindowAttributes mainWindow = null;

	/**
	 * The cached value of the '{@link #getLastVersionRun() <em>Last Version Run</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLastVersionRun()
	 * @generated
	 * @ordered
	 */
	protected Version lastVersionRun = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected PrefsImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return PrefsPackage.Literals.PREFS;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getDataFile() {
		return dataFile;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDataFile(String newDataFile) {
		String oldDataFile = dataFile;
		dataFile = newDataFile;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PrefsPackage.PREFS__DATA_FILE, oldDataFile, dataFile));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getLanguage() {
		return language;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLanguage(String newLanguage) {
		String oldLanguage = language;
		language = newLanguage;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PrefsPackage.PREFS__LANGUAGE, oldLanguage, language));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isShowDeletedAccounts() {
		return showDeletedAccounts;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setShowDeletedAccounts(boolean newShowDeletedAccounts) {
		boolean oldShowDeletedAccounts = showDeletedAccounts;
		showDeletedAccounts = newShowDeletedAccounts;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PrefsPackage.PREFS__SHOW_DELETED_ACCOUNTS, oldShowDeletedAccounts, showDeletedAccounts));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isShowDeletedCategories() {
		return showDeletedCategories;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setShowDeletedCategories(boolean newShowDeletedCategories) {
		boolean oldShowDeletedCategories = showDeletedCategories;
		showDeletedCategories = newShowDeletedCategories;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PrefsPackage.PREFS__SHOW_DELETED_CATEGORIES, oldShowDeletedCategories, showDeletedCategories));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getDateFormat() {
		return dateFormat;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDateFormat(String newDateFormat) {
		String oldDateFormat = dateFormat;
		dateFormat = newDateFormat;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PrefsPackage.PREFS__DATE_FORMAT, oldDateFormat, dateFormat));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getBudgetPeriod() {
		return budgetPeriod;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setBudgetPeriod(String newBudgetPeriod) {
		String oldBudgetPeriod = budgetPeriod;
		budgetPeriod = newBudgetPeriod;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PrefsPackage.PREFS__BUDGET_PERIOD, oldBudgetPeriod, budgetPeriod));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isShowAccountTypes() {
		return showAccountTypes;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setShowAccountTypes(boolean newShowAccountTypes) {
		boolean oldShowAccountTypes = showAccountTypes;
		showAccountTypes = newShowAccountTypes;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PrefsPackage.PREFS__SHOW_ACCOUNT_TYPES, oldShowAccountTypes, showAccountTypes));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isEnableUpdateNotifications() {
		return enableUpdateNotifications;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setEnableUpdateNotifications(boolean newEnableUpdateNotifications) {
		boolean oldEnableUpdateNotifications = enableUpdateNotifications;
		enableUpdateNotifications = newEnableUpdateNotifications;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PrefsPackage.PREFS__ENABLE_UPDATE_NOTIFICATIONS, oldEnableUpdateNotifications, enableUpdateNotifications));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public WindowAttributes getTransactionsWindow() {
		return transactionsWindow;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetTransactionsWindow(WindowAttributes newTransactionsWindow, NotificationChain msgs) {
		WindowAttributes oldTransactionsWindow = transactionsWindow;
		transactionsWindow = newTransactionsWindow;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, PrefsPackage.PREFS__TRANSACTIONS_WINDOW, oldTransactionsWindow, newTransactionsWindow);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTransactionsWindow(WindowAttributes newTransactionsWindow) {
		if (newTransactionsWindow != transactionsWindow) {
			NotificationChain msgs = null;
			if (transactionsWindow != null)
				msgs = ((InternalEObject)transactionsWindow).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - PrefsPackage.PREFS__TRANSACTIONS_WINDOW, null, msgs);
			if (newTransactionsWindow != null)
				msgs = ((InternalEObject)newTransactionsWindow).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - PrefsPackage.PREFS__TRANSACTIONS_WINDOW, null, msgs);
			msgs = basicSetTransactionsWindow(newTransactionsWindow, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PrefsPackage.PREFS__TRANSACTIONS_WINDOW, newTransactionsWindow, newTransactionsWindow));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public WindowAttributes getGraphsWindow() {
		return graphsWindow;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetGraphsWindow(WindowAttributes newGraphsWindow, NotificationChain msgs) {
		WindowAttributes oldGraphsWindow = graphsWindow;
		graphsWindow = newGraphsWindow;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, PrefsPackage.PREFS__GRAPHS_WINDOW, oldGraphsWindow, newGraphsWindow);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setGraphsWindow(WindowAttributes newGraphsWindow) {
		if (newGraphsWindow != graphsWindow) {
			NotificationChain msgs = null;
			if (graphsWindow != null)
				msgs = ((InternalEObject)graphsWindow).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - PrefsPackage.PREFS__GRAPHS_WINDOW, null, msgs);
			if (newGraphsWindow != null)
				msgs = ((InternalEObject)newGraphsWindow).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - PrefsPackage.PREFS__GRAPHS_WINDOW, null, msgs);
			msgs = basicSetGraphsWindow(newGraphsWindow, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PrefsPackage.PREFS__GRAPHS_WINDOW, newGraphsWindow, newGraphsWindow));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public WindowAttributes getMainWindow() {
		return mainWindow;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetMainWindow(WindowAttributes newMainWindow, NotificationChain msgs) {
		WindowAttributes oldMainWindow = mainWindow;
		mainWindow = newMainWindow;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, PrefsPackage.PREFS__MAIN_WINDOW, oldMainWindow, newMainWindow);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMainWindow(WindowAttributes newMainWindow) {
		if (newMainWindow != mainWindow) {
			NotificationChain msgs = null;
			if (mainWindow != null)
				msgs = ((InternalEObject)mainWindow).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - PrefsPackage.PREFS__MAIN_WINDOW, null, msgs);
			if (newMainWindow != null)
				msgs = ((InternalEObject)newMainWindow).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - PrefsPackage.PREFS__MAIN_WINDOW, null, msgs);
			msgs = basicSetMainWindow(newMainWindow, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PrefsPackage.PREFS__MAIN_WINDOW, newMainWindow, newMainWindow));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Version getLastVersionRun() {
		return lastVersionRun;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetLastVersionRun(Version newLastVersionRun, NotificationChain msgs) {
		Version oldLastVersionRun = lastVersionRun;
		lastVersionRun = newLastVersionRun;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, PrefsPackage.PREFS__LAST_VERSION_RUN, oldLastVersionRun, newLastVersionRun);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLastVersionRun(Version newLastVersionRun) {
		if (newLastVersionRun != lastVersionRun) {
			NotificationChain msgs = null;
			if (lastVersionRun != null)
				msgs = ((InternalEObject)lastVersionRun).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - PrefsPackage.PREFS__LAST_VERSION_RUN, null, msgs);
			if (newLastVersionRun != null)
				msgs = ((InternalEObject)newLastVersionRun).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - PrefsPackage.PREFS__LAST_VERSION_RUN, null, msgs);
			msgs = basicSetLastVersionRun(newLastVersionRun, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PrefsPackage.PREFS__LAST_VERSION_RUN, newLastVersionRun, newLastVersionRun));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getListEntries() {
		if (listEntries == null) {
			listEntries = new EObjectContainmentEList(ListEntry.class, this, PrefsPackage.PREFS__LIST_ENTRIES);
		}
		return listEntries;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Intervals getIntervals() {
		return intervals;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetIntervals(Intervals newIntervals, NotificationChain msgs) {
		Intervals oldIntervals = intervals;
		intervals = newIntervals;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, PrefsPackage.PREFS__INTERVALS, oldIntervals, newIntervals);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setIntervals(Intervals newIntervals) {
		if (newIntervals != intervals) {
			NotificationChain msgs = null;
			if (intervals != null)
				msgs = ((InternalEObject)intervals).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - PrefsPackage.PREFS__INTERVALS, null, msgs);
			if (newIntervals != null)
				msgs = ((InternalEObject)newIntervals).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - PrefsPackage.PREFS__INTERVALS, null, msgs);
			msgs = basicSetIntervals(newIntervals, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PrefsPackage.PREFS__INTERVALS, newIntervals, newIntervals));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public WindowAttributes getReportsWindow() {
		return reportsWindow;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetReportsWindow(WindowAttributes newReportsWindow, NotificationChain msgs) {
		WindowAttributes oldReportsWindow = reportsWindow;
		reportsWindow = newReportsWindow;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, PrefsPackage.PREFS__REPORTS_WINDOW, oldReportsWindow, newReportsWindow);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setReportsWindow(WindowAttributes newReportsWindow) {
		if (newReportsWindow != reportsWindow) {
			NotificationChain msgs = null;
			if (reportsWindow != null)
				msgs = ((InternalEObject)reportsWindow).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - PrefsPackage.PREFS__REPORTS_WINDOW, null, msgs);
			if (newReportsWindow != null)
				msgs = ((InternalEObject)newReportsWindow).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - PrefsPackage.PREFS__REPORTS_WINDOW, null, msgs);
			msgs = basicSetReportsWindow(newReportsWindow, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PrefsPackage.PREFS__REPORTS_WINDOW, newReportsWindow, newReportsWindow));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getDescDict() {
		if (descDict == null) {
			descDict = new EObjectContainmentEList(DictEntry.class, this, PrefsPackage.PREFS__DESC_DICT);
		}
		return descDict;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case PrefsPackage.PREFS__REPORTS_WINDOW:
				return basicSetReportsWindow(null, msgs);
			case PrefsPackage.PREFS__DESC_DICT:
				return ((InternalEList)getDescDict()).basicRemove(otherEnd, msgs);
			case PrefsPackage.PREFS__INTERVALS:
				return basicSetIntervals(null, msgs);
			case PrefsPackage.PREFS__LIST_ENTRIES:
				return ((InternalEList)getListEntries()).basicRemove(otherEnd, msgs);
			case PrefsPackage.PREFS__TRANSACTIONS_WINDOW:
				return basicSetTransactionsWindow(null, msgs);
			case PrefsPackage.PREFS__GRAPHS_WINDOW:
				return basicSetGraphsWindow(null, msgs);
			case PrefsPackage.PREFS__MAIN_WINDOW:
				return basicSetMainWindow(null, msgs);
			case PrefsPackage.PREFS__LAST_VERSION_RUN:
				return basicSetLastVersionRun(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case PrefsPackage.PREFS__DATA_FILE:
				return getDataFile();
			case PrefsPackage.PREFS__LANGUAGE:
				return getLanguage();
			case PrefsPackage.PREFS__SHOW_DELETED_ACCOUNTS:
				return isShowDeletedAccounts() ? Boolean.TRUE : Boolean.FALSE;
			case PrefsPackage.PREFS__SHOW_DELETED_CATEGORIES:
				return isShowDeletedCategories() ? Boolean.TRUE : Boolean.FALSE;
			case PrefsPackage.PREFS__DATE_FORMAT:
				return getDateFormat();
			case PrefsPackage.PREFS__BUDGET_PERIOD:
				return getBudgetPeriod();
			case PrefsPackage.PREFS__SHOW_ACCOUNT_TYPES:
				return isShowAccountTypes() ? Boolean.TRUE : Boolean.FALSE;
			case PrefsPackage.PREFS__ENABLE_UPDATE_NOTIFICATIONS:
				return isEnableUpdateNotifications() ? Boolean.TRUE : Boolean.FALSE;
			case PrefsPackage.PREFS__SELECTED_INTERVAL:
				return getSelectedInterval();
			case PrefsPackage.PREFS__SHOW_AUTO_COMPLETE:
				return isShowAutoComplete() ? Boolean.TRUE : Boolean.FALSE;
			case PrefsPackage.PREFS__CURRENCY_SYMBOL:
				return getCurrencySymbol();
			case PrefsPackage.PREFS__SHOW_CREDIT_LIMIT:
				return isShowCreditLimit() ? Boolean.TRUE : Boolean.FALSE;
			case PrefsPackage.PREFS__SHOW_INTEREST_RATE:
				return isShowInterestRate() ? Boolean.TRUE : Boolean.FALSE;
			case PrefsPackage.PREFS__REPORTS_WINDOW:
				return getReportsWindow();
			case PrefsPackage.PREFS__DESC_DICT:
				return getDescDict();
			case PrefsPackage.PREFS__INTERVALS:
				return getIntervals();
			case PrefsPackage.PREFS__LIST_ENTRIES:
				return getListEntries();
			case PrefsPackage.PREFS__TRANSACTIONS_WINDOW:
				return getTransactionsWindow();
			case PrefsPackage.PREFS__GRAPHS_WINDOW:
				return getGraphsWindow();
			case PrefsPackage.PREFS__MAIN_WINDOW:
				return getMainWindow();
			case PrefsPackage.PREFS__LAST_VERSION_RUN:
				return getLastVersionRun();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case PrefsPackage.PREFS__DATA_FILE:
				setDataFile((String)newValue);
				return;
			case PrefsPackage.PREFS__LANGUAGE:
				setLanguage((String)newValue);
				return;
			case PrefsPackage.PREFS__SHOW_DELETED_ACCOUNTS:
				setShowDeletedAccounts(((Boolean)newValue).booleanValue());
				return;
			case PrefsPackage.PREFS__SHOW_DELETED_CATEGORIES:
				setShowDeletedCategories(((Boolean)newValue).booleanValue());
				return;
			case PrefsPackage.PREFS__DATE_FORMAT:
				setDateFormat((String)newValue);
				return;
			case PrefsPackage.PREFS__BUDGET_PERIOD:
				setBudgetPeriod((String)newValue);
				return;
			case PrefsPackage.PREFS__SHOW_ACCOUNT_TYPES:
				setShowAccountTypes(((Boolean)newValue).booleanValue());
				return;
			case PrefsPackage.PREFS__ENABLE_UPDATE_NOTIFICATIONS:
				setEnableUpdateNotifications(((Boolean)newValue).booleanValue());
				return;
			case PrefsPackage.PREFS__SELECTED_INTERVAL:
				setSelectedInterval((String)newValue);
				return;
			case PrefsPackage.PREFS__SHOW_AUTO_COMPLETE:
				setShowAutoComplete(((Boolean)newValue).booleanValue());
				return;
			case PrefsPackage.PREFS__CURRENCY_SYMBOL:
				setCurrencySymbol((String)newValue);
				return;
			case PrefsPackage.PREFS__SHOW_CREDIT_LIMIT:
				setShowCreditLimit(((Boolean)newValue).booleanValue());
				return;
			case PrefsPackage.PREFS__SHOW_INTEREST_RATE:
				setShowInterestRate(((Boolean)newValue).booleanValue());
				return;
			case PrefsPackage.PREFS__REPORTS_WINDOW:
				setReportsWindow((WindowAttributes)newValue);
				return;
			case PrefsPackage.PREFS__DESC_DICT:
				getDescDict().clear();
				getDescDict().addAll((Collection)newValue);
				return;
			case PrefsPackage.PREFS__INTERVALS:
				setIntervals((Intervals)newValue);
				return;
			case PrefsPackage.PREFS__LIST_ENTRIES:
				getListEntries().clear();
				getListEntries().addAll((Collection)newValue);
				return;
			case PrefsPackage.PREFS__TRANSACTIONS_WINDOW:
				setTransactionsWindow((WindowAttributes)newValue);
				return;
			case PrefsPackage.PREFS__GRAPHS_WINDOW:
				setGraphsWindow((WindowAttributes)newValue);
				return;
			case PrefsPackage.PREFS__MAIN_WINDOW:
				setMainWindow((WindowAttributes)newValue);
				return;
			case PrefsPackage.PREFS__LAST_VERSION_RUN:
				setLastVersionRun((Version)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void eUnset(int featureID) {
		switch (featureID) {
			case PrefsPackage.PREFS__DATA_FILE:
				setDataFile(DATA_FILE_EDEFAULT);
				return;
			case PrefsPackage.PREFS__LANGUAGE:
				setLanguage(LANGUAGE_EDEFAULT);
				return;
			case PrefsPackage.PREFS__SHOW_DELETED_ACCOUNTS:
				setShowDeletedAccounts(SHOW_DELETED_ACCOUNTS_EDEFAULT);
				return;
			case PrefsPackage.PREFS__SHOW_DELETED_CATEGORIES:
				setShowDeletedCategories(SHOW_DELETED_CATEGORIES_EDEFAULT);
				return;
			case PrefsPackage.PREFS__DATE_FORMAT:
				setDateFormat(DATE_FORMAT_EDEFAULT);
				return;
			case PrefsPackage.PREFS__BUDGET_PERIOD:
				setBudgetPeriod(BUDGET_PERIOD_EDEFAULT);
				return;
			case PrefsPackage.PREFS__SHOW_ACCOUNT_TYPES:
				setShowAccountTypes(SHOW_ACCOUNT_TYPES_EDEFAULT);
				return;
			case PrefsPackage.PREFS__ENABLE_UPDATE_NOTIFICATIONS:
				setEnableUpdateNotifications(ENABLE_UPDATE_NOTIFICATIONS_EDEFAULT);
				return;
			case PrefsPackage.PREFS__SELECTED_INTERVAL:
				setSelectedInterval(SELECTED_INTERVAL_EDEFAULT);
				return;
			case PrefsPackage.PREFS__SHOW_AUTO_COMPLETE:
				setShowAutoComplete(SHOW_AUTO_COMPLETE_EDEFAULT);
				return;
			case PrefsPackage.PREFS__CURRENCY_SYMBOL:
				setCurrencySymbol(CURRENCY_SYMBOL_EDEFAULT);
				return;
			case PrefsPackage.PREFS__SHOW_CREDIT_LIMIT:
				setShowCreditLimit(SHOW_CREDIT_LIMIT_EDEFAULT);
				return;
			case PrefsPackage.PREFS__SHOW_INTEREST_RATE:
				setShowInterestRate(SHOW_INTEREST_RATE_EDEFAULT);
				return;
			case PrefsPackage.PREFS__REPORTS_WINDOW:
				setReportsWindow((WindowAttributes)null);
				return;
			case PrefsPackage.PREFS__DESC_DICT:
				getDescDict().clear();
				return;
			case PrefsPackage.PREFS__INTERVALS:
				setIntervals((Intervals)null);
				return;
			case PrefsPackage.PREFS__LIST_ENTRIES:
				getListEntries().clear();
				return;
			case PrefsPackage.PREFS__TRANSACTIONS_WINDOW:
				setTransactionsWindow((WindowAttributes)null);
				return;
			case PrefsPackage.PREFS__GRAPHS_WINDOW:
				setGraphsWindow((WindowAttributes)null);
				return;
			case PrefsPackage.PREFS__MAIN_WINDOW:
				setMainWindow((WindowAttributes)null);
				return;
			case PrefsPackage.PREFS__LAST_VERSION_RUN:
				setLastVersionRun((Version)null);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case PrefsPackage.PREFS__DATA_FILE:
				return DATA_FILE_EDEFAULT == null ? dataFile != null : !DATA_FILE_EDEFAULT.equals(dataFile);
			case PrefsPackage.PREFS__LANGUAGE:
				return LANGUAGE_EDEFAULT == null ? language != null : !LANGUAGE_EDEFAULT.equals(language);
			case PrefsPackage.PREFS__SHOW_DELETED_ACCOUNTS:
				return showDeletedAccounts != SHOW_DELETED_ACCOUNTS_EDEFAULT;
			case PrefsPackage.PREFS__SHOW_DELETED_CATEGORIES:
				return showDeletedCategories != SHOW_DELETED_CATEGORIES_EDEFAULT;
			case PrefsPackage.PREFS__DATE_FORMAT:
				return DATE_FORMAT_EDEFAULT == null ? dateFormat != null : !DATE_FORMAT_EDEFAULT.equals(dateFormat);
			case PrefsPackage.PREFS__BUDGET_PERIOD:
				return BUDGET_PERIOD_EDEFAULT == null ? budgetPeriod != null : !BUDGET_PERIOD_EDEFAULT.equals(budgetPeriod);
			case PrefsPackage.PREFS__SHOW_ACCOUNT_TYPES:
				return showAccountTypes != SHOW_ACCOUNT_TYPES_EDEFAULT;
			case PrefsPackage.PREFS__ENABLE_UPDATE_NOTIFICATIONS:
				return enableUpdateNotifications != ENABLE_UPDATE_NOTIFICATIONS_EDEFAULT;
			case PrefsPackage.PREFS__SELECTED_INTERVAL:
				return SELECTED_INTERVAL_EDEFAULT == null ? selectedInterval != null : !SELECTED_INTERVAL_EDEFAULT.equals(selectedInterval);
			case PrefsPackage.PREFS__SHOW_AUTO_COMPLETE:
				return showAutoComplete != SHOW_AUTO_COMPLETE_EDEFAULT;
			case PrefsPackage.PREFS__CURRENCY_SYMBOL:
				return CURRENCY_SYMBOL_EDEFAULT == null ? currencySymbol != null : !CURRENCY_SYMBOL_EDEFAULT.equals(currencySymbol);
			case PrefsPackage.PREFS__SHOW_CREDIT_LIMIT:
				return showCreditLimit != SHOW_CREDIT_LIMIT_EDEFAULT;
			case PrefsPackage.PREFS__SHOW_INTEREST_RATE:
				return showInterestRate != SHOW_INTEREST_RATE_EDEFAULT;
			case PrefsPackage.PREFS__REPORTS_WINDOW:
				return reportsWindow != null;
			case PrefsPackage.PREFS__DESC_DICT:
				return descDict != null && !descDict.isEmpty();
			case PrefsPackage.PREFS__INTERVALS:
				return intervals != null;
			case PrefsPackage.PREFS__LIST_ENTRIES:
				return listEntries != null && !listEntries.isEmpty();
			case PrefsPackage.PREFS__TRANSACTIONS_WINDOW:
				return transactionsWindow != null;
			case PrefsPackage.PREFS__GRAPHS_WINDOW:
				return graphsWindow != null;
			case PrefsPackage.PREFS__MAIN_WINDOW:
				return mainWindow != null;
			case PrefsPackage.PREFS__LAST_VERSION_RUN:
				return lastVersionRun != null;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getSelectedInterval() {
		return selectedInterval;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSelectedInterval(String newSelectedInterval) {
		String oldSelectedInterval = selectedInterval;
		selectedInterval = newSelectedInterval;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PrefsPackage.PREFS__SELECTED_INTERVAL, oldSelectedInterval, selectedInterval));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isShowAutoComplete() {
		return showAutoComplete;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setShowAutoComplete(boolean newShowAutoComplete) {
		boolean oldShowAutoComplete = showAutoComplete;
		showAutoComplete = newShowAutoComplete;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PrefsPackage.PREFS__SHOW_AUTO_COMPLETE, oldShowAutoComplete, showAutoComplete));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getCurrencySymbol() {
		return currencySymbol;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCurrencySymbol(String newCurrencySymbol) {
		String oldCurrencySymbol = currencySymbol;
		currencySymbol = newCurrencySymbol;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PrefsPackage.PREFS__CURRENCY_SYMBOL, oldCurrencySymbol, currencySymbol));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isShowCreditLimit() {
		return showCreditLimit;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setShowCreditLimit(boolean newShowCreditLimit) {
		boolean oldShowCreditLimit = showCreditLimit;
		showCreditLimit = newShowCreditLimit;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PrefsPackage.PREFS__SHOW_CREDIT_LIMIT, oldShowCreditLimit, showCreditLimit));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isShowInterestRate() {
		return showInterestRate;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setShowInterestRate(boolean newShowInterestRate) {
		boolean oldShowInterestRate = showInterestRate;
		showInterestRate = newShowInterestRate;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PrefsPackage.PREFS__SHOW_INTEREST_RATE, oldShowInterestRate, showInterestRate));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (dataFile: ");
		result.append(dataFile);
		result.append(", language: ");
		result.append(language);
		result.append(", showDeletedAccounts: ");
		result.append(showDeletedAccounts);
		result.append(", showDeletedCategories: ");
		result.append(showDeletedCategories);
		result.append(", dateFormat: ");
		result.append(dateFormat);
		result.append(", budgetPeriod: ");
		result.append(budgetPeriod);
		result.append(", showAccountTypes: ");
		result.append(showAccountTypes);
		result.append(", enableUpdateNotifications: ");
		result.append(enableUpdateNotifications);
		result.append(", selectedInterval: ");
		result.append(selectedInterval);
		result.append(", showAutoComplete: ");
		result.append(showAutoComplete);
		result.append(", currencySymbol: ");
		result.append(currencySymbol);
		result.append(", showCreditLimit: ");
		result.append(showCreditLimit);
		result.append(", showInterestRate: ");
		result.append(showInterestRate);
		result.append(')');
		return result.toString();
	}

} //PrefsImpl
