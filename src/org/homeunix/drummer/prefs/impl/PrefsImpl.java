/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.homeunix.drummer.prefs.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.homeunix.drummer.prefs.Intervals;
import org.homeunix.drummer.prefs.Lists;
import org.homeunix.drummer.prefs.Prefs;
import org.homeunix.drummer.prefs.PrefsPackage;
import org.homeunix.drummer.prefs.Version;
import org.homeunix.drummer.prefs.Windows;

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
 *   <li>{@link org.homeunix.drummer.prefs.impl.PrefsImpl#isShowAdvanced <em>Show Advanced</em>}</li>
 *   <li>{@link org.homeunix.drummer.prefs.impl.PrefsImpl#getNumberOfBackups <em>Number Of Backups</em>}</li>
 *   <li>{@link org.homeunix.drummer.prefs.impl.PrefsImpl#getLookAndFeelClass <em>Look And Feel Class</em>}</li>
 *   <li>{@link org.homeunix.drummer.prefs.impl.PrefsImpl#getLists <em>Lists</em>}</li>
 *   <li>{@link org.homeunix.drummer.prefs.impl.PrefsImpl#getLastVersionRun <em>Last Version Run</em>}</li>
 *   <li>{@link org.homeunix.drummer.prefs.impl.PrefsImpl#getIntervals <em>Intervals</em>}</li>
 *   <li>{@link org.homeunix.drummer.prefs.impl.PrefsImpl#getWindows <em>Windows</em>}</li>
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
	 * The default value of the '{@link #isShowAdvanced() <em>Show Advanced</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isShowAdvanced()
	 * @generated
	 * @ordered
	 */
	protected static final boolean SHOW_ADVANCED_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isShowAdvanced() <em>Show Advanced</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isShowAdvanced()
	 * @generated
	 * @ordered
	 */
	protected boolean showAdvanced = SHOW_ADVANCED_EDEFAULT;

	/**
	 * The default value of the '{@link #getNumberOfBackups() <em>Number Of Backups</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNumberOfBackups()
	 * @generated
	 * @ordered
	 */
	protected static final int NUMBER_OF_BACKUPS_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getNumberOfBackups() <em>Number Of Backups</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNumberOfBackups()
	 * @generated
	 * @ordered
	 */
	protected int numberOfBackups = NUMBER_OF_BACKUPS_EDEFAULT;

	/**
	 * The default value of the '{@link #getLookAndFeelClass() <em>Look And Feel Class</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLookAndFeelClass()
	 * @generated
	 * @ordered
	 */
	protected static final String LOOK_AND_FEEL_CLASS_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getLookAndFeelClass() <em>Look And Feel Class</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLookAndFeelClass()
	 * @generated
	 * @ordered
	 */
	protected String lookAndFeelClass = LOOK_AND_FEEL_CLASS_EDEFAULT;

	/**
	 * The cached value of the '{@link #getLists() <em>Lists</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLists()
	 * @generated
	 * @ordered
	 */
	protected Lists lists = null;

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
	 * The cached value of the '{@link #getIntervals() <em>Intervals</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIntervals()
	 * @generated
	 * @ordered
	 */
	protected Intervals intervals = null;

	/**
	 * The cached value of the '{@link #getWindows() <em>Windows</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getWindows()
	 * @generated
	 * @ordered
	 */
	protected Windows windows = null;

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
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case PrefsPackage.PREFS__LISTS:
				return basicSetLists(null, msgs);
			case PrefsPackage.PREFS__LAST_VERSION_RUN:
				return basicSetLastVersionRun(null, msgs);
			case PrefsPackage.PREFS__INTERVALS:
				return basicSetIntervals(null, msgs);
			case PrefsPackage.PREFS__WINDOWS:
				return basicSetWindows(null, msgs);
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
			case PrefsPackage.PREFS__SHOW_ADVANCED:
				return isShowAdvanced() ? Boolean.TRUE : Boolean.FALSE;
			case PrefsPackage.PREFS__NUMBER_OF_BACKUPS:
				return new Integer(getNumberOfBackups());
			case PrefsPackage.PREFS__LOOK_AND_FEEL_CLASS:
				return getLookAndFeelClass();
			case PrefsPackage.PREFS__LISTS:
				return getLists();
			case PrefsPackage.PREFS__LAST_VERSION_RUN:
				return getLastVersionRun();
			case PrefsPackage.PREFS__INTERVALS:
				return getIntervals();
			case PrefsPackage.PREFS__WINDOWS:
				return getWindows();
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
			case PrefsPackage.PREFS__SHOW_ADVANCED:
				setShowAdvanced(((Boolean)newValue).booleanValue());
				return;
			case PrefsPackage.PREFS__NUMBER_OF_BACKUPS:
				setNumberOfBackups(((Integer)newValue).intValue());
				return;
			case PrefsPackage.PREFS__LOOK_AND_FEEL_CLASS:
				setLookAndFeelClass((String)newValue);
				return;
			case PrefsPackage.PREFS__LISTS:
				setLists((Lists)newValue);
				return;
			case PrefsPackage.PREFS__LAST_VERSION_RUN:
				setLastVersionRun((Version)newValue);
				return;
			case PrefsPackage.PREFS__INTERVALS:
				setIntervals((Intervals)newValue);
				return;
			case PrefsPackage.PREFS__WINDOWS:
				setWindows((Windows)newValue);
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
			case PrefsPackage.PREFS__SHOW_ADVANCED:
				setShowAdvanced(SHOW_ADVANCED_EDEFAULT);
				return;
			case PrefsPackage.PREFS__NUMBER_OF_BACKUPS:
				setNumberOfBackups(NUMBER_OF_BACKUPS_EDEFAULT);
				return;
			case PrefsPackage.PREFS__LOOK_AND_FEEL_CLASS:
				setLookAndFeelClass(LOOK_AND_FEEL_CLASS_EDEFAULT);
				return;
			case PrefsPackage.PREFS__LISTS:
				setLists((Lists)null);
				return;
			case PrefsPackage.PREFS__LAST_VERSION_RUN:
				setLastVersionRun((Version)null);
				return;
			case PrefsPackage.PREFS__INTERVALS:
				setIntervals((Intervals)null);
				return;
			case PrefsPackage.PREFS__WINDOWS:
				setWindows((Windows)null);
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
			case PrefsPackage.PREFS__SHOW_ADVANCED:
				return showAdvanced != SHOW_ADVANCED_EDEFAULT;
			case PrefsPackage.PREFS__NUMBER_OF_BACKUPS:
				return numberOfBackups != NUMBER_OF_BACKUPS_EDEFAULT;
			case PrefsPackage.PREFS__LOOK_AND_FEEL_CLASS:
				return LOOK_AND_FEEL_CLASS_EDEFAULT == null ? lookAndFeelClass != null : !LOOK_AND_FEEL_CLASS_EDEFAULT.equals(lookAndFeelClass);
			case PrefsPackage.PREFS__LISTS:
				return lists != null;
			case PrefsPackage.PREFS__LAST_VERSION_RUN:
				return lastVersionRun != null;
			case PrefsPackage.PREFS__INTERVALS:
				return intervals != null;
			case PrefsPackage.PREFS__WINDOWS:
				return windows != null;
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
	public boolean isShowAdvanced() {
		return showAdvanced;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setShowAdvanced(boolean newShowAdvanced) {
		boolean oldShowAdvanced = showAdvanced;
		showAdvanced = newShowAdvanced;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PrefsPackage.PREFS__SHOW_ADVANCED, oldShowAdvanced, showAdvanced));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getNumberOfBackups() {
		return numberOfBackups;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setNumberOfBackups(int newNumberOfBackups) {
		int oldNumberOfBackups = numberOfBackups;
		numberOfBackups = newNumberOfBackups;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PrefsPackage.PREFS__NUMBER_OF_BACKUPS, oldNumberOfBackups, numberOfBackups));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getLookAndFeelClass() {
		return lookAndFeelClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLookAndFeelClass(String newLookAndFeelClass) {
		String oldLookAndFeelClass = lookAndFeelClass;
		lookAndFeelClass = newLookAndFeelClass;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PrefsPackage.PREFS__LOOK_AND_FEEL_CLASS, oldLookAndFeelClass, lookAndFeelClass));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Lists getLists() {
		return lists;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetLists(Lists newLists, NotificationChain msgs) {
		Lists oldLists = lists;
		lists = newLists;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, PrefsPackage.PREFS__LISTS, oldLists, newLists);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLists(Lists newLists) {
		if (newLists != lists) {
			NotificationChain msgs = null;
			if (lists != null)
				msgs = ((InternalEObject)lists).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - PrefsPackage.PREFS__LISTS, null, msgs);
			if (newLists != null)
				msgs = ((InternalEObject)newLists).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - PrefsPackage.PREFS__LISTS, null, msgs);
			msgs = basicSetLists(newLists, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PrefsPackage.PREFS__LISTS, newLists, newLists));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Windows getWindows() {
		return windows;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetWindows(Windows newWindows, NotificationChain msgs) {
		Windows oldWindows = windows;
		windows = newWindows;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, PrefsPackage.PREFS__WINDOWS, oldWindows, newWindows);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setWindows(Windows newWindows) {
		if (newWindows != windows) {
			NotificationChain msgs = null;
			if (windows != null)
				msgs = ((InternalEObject)windows).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - PrefsPackage.PREFS__WINDOWS, null, msgs);
			if (newWindows != null)
				msgs = ((InternalEObject)newWindows).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - PrefsPackage.PREFS__WINDOWS, null, msgs);
			msgs = basicSetWindows(newWindows, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PrefsPackage.PREFS__WINDOWS, newWindows, newWindows));
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
		result.append(", showAdvanced: ");
		result.append(showAdvanced);
		result.append(", numberOfBackups: ");
		result.append(numberOfBackups);
		result.append(", lookAndFeelClass: ");
		result.append(lookAndFeelClass);
		result.append(')');
		return result.toString();
	}

} //PrefsImpl
