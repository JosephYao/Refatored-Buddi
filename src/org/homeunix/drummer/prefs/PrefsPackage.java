/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.homeunix.drummer.prefs;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see org.homeunix.drummer.prefs.PrefsFactory
 * @model kind="package"
 * @generated
 */
public interface PrefsPackage extends EPackage{
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "prefs";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "urn:org.homeunix.drummer.prefs.ecore";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "org.homeunix.drummer.prefs";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	PrefsPackage eINSTANCE = org.homeunix.drummer.prefs.impl.PrefsPackageImpl.init();

	/**
	 * The meta object id for the '{@link org.homeunix.drummer.prefs.impl.DictDataImpl <em>Dict Data</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.homeunix.drummer.prefs.impl.DictDataImpl
	 * @see org.homeunix.drummer.prefs.impl.PrefsPackageImpl#getDictData()
	 * @generated
	 */
	int DICT_DATA = 0;

	/**
	 * The feature id for the '<em><b>Number</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DICT_DATA__NUMBER = 0;

	/**
	 * The feature id for the '<em><b>Memo</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DICT_DATA__MEMO = 1;

	/**
	 * The feature id for the '<em><b>Amount</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DICT_DATA__AMOUNT = 2;

	/**
	 * The feature id for the '<em><b>To</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DICT_DATA__TO = 3;

	/**
	 * The feature id for the '<em><b>From</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DICT_DATA__FROM = 4;

	/**
	 * The number of structural features of the the '<em>Dict Data</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DICT_DATA_FEATURE_COUNT = 5;

	/**
	 * The meta object id for the '{@link org.homeunix.drummer.prefs.impl.DictEntryImpl <em>Dict Entry</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.homeunix.drummer.prefs.impl.DictEntryImpl
	 * @see org.homeunix.drummer.prefs.impl.PrefsPackageImpl#getDictEntry()
	 * @generated
	 */
	int DICT_ENTRY = 1;

	/**
	 * The feature id for the '<em><b>Entry</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DICT_ENTRY__ENTRY = 0;

	/**
	 * The feature id for the '<em><b>Data</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DICT_ENTRY__DATA = 1;

	/**
	 * The number of structural features of the the '<em>Dict Entry</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DICT_ENTRY_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link org.homeunix.drummer.prefs.impl.ListAttributesImpl <em>List Attributes</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.homeunix.drummer.prefs.impl.ListAttributesImpl
	 * @see org.homeunix.drummer.prefs.impl.PrefsPackageImpl#getListAttributes()
	 * @generated
	 */
	int LIST_ATTRIBUTES = 2;

	/**
	 * The feature id for the '<em><b>Unrolled</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LIST_ATTRIBUTES__UNROLLED = 0;

	/**
	 * The number of structural features of the the '<em>List Attributes</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LIST_ATTRIBUTES_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link org.homeunix.drummer.prefs.impl.ListEntryImpl <em>List Entry</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.homeunix.drummer.prefs.impl.ListEntryImpl
	 * @see org.homeunix.drummer.prefs.impl.PrefsPackageImpl#getListEntry()
	 * @generated
	 */
	int LIST_ENTRY = 3;

	/**
	 * The feature id for the '<em><b>Entry</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LIST_ENTRY__ENTRY = 0;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LIST_ENTRY__ATTRIBUTES = 1;

	/**
	 * The number of structural features of the the '<em>List Entry</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LIST_ENTRY_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link org.homeunix.drummer.prefs.impl.PrefsImpl <em>Prefs</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.homeunix.drummer.prefs.impl.PrefsImpl
	 * @see org.homeunix.drummer.prefs.impl.PrefsPackageImpl#getPrefs()
	 * @generated
	 */
	int PREFS = 4;

	/**
	 * The feature id for the '<em><b>Data File</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PREFS__DATA_FILE = 0;

	/**
	 * The feature id for the '<em><b>Language</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PREFS__LANGUAGE = 1;

	/**
	 * The feature id for the '<em><b>Show Deleted Accounts</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PREFS__SHOW_DELETED_ACCOUNTS = 2;

	/**
	 * The feature id for the '<em><b>Show Deleted Categories</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PREFS__SHOW_DELETED_CATEGORIES = 3;

	/**
	 * The feature id for the '<em><b>Date Format</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PREFS__DATE_FORMAT = 4;

	/**
	 * The feature id for the '<em><b>Budget Period</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PREFS__BUDGET_PERIOD = 5;

	/**
	 * The feature id for the '<em><b>Show Account Types</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PREFS__SHOW_ACCOUNT_TYPES = 6;

	/**
	 * The feature id for the '<em><b>Enable Update Notifications</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PREFS__ENABLE_UPDATE_NOTIFICATIONS = 7;

	/**
	 * The feature id for the '<em><b>Graphs Window</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PREFS__GRAPHS_WINDOW = 8;

	/**
	 * The feature id for the '<em><b>Desc Dict</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PREFS__DESC_DICT = 9;

	/**
	 * The feature id for the '<em><b>Reports Window</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PREFS__REPORTS_WINDOW = 10;

	/**
	 * The feature id for the '<em><b>Main Window</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PREFS__MAIN_WINDOW = 11;

	/**
	 * The feature id for the '<em><b>Transactions Window</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PREFS__TRANSACTIONS_WINDOW = 12;

	/**
	 * The feature id for the '<em><b>List Entries</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PREFS__LIST_ENTRIES = 13;

	/**
	 * The number of structural features of the the '<em>Prefs</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PREFS_FEATURE_COUNT = 14;


	/**
	 * The meta object id for the '{@link org.homeunix.drummer.prefs.impl.UserPrefsImpl <em>User Prefs</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.homeunix.drummer.prefs.impl.UserPrefsImpl
	 * @see org.homeunix.drummer.prefs.impl.PrefsPackageImpl#getUserPrefs()
	 * @generated
	 */
	int USER_PREFS = 5;

	/**
	 * The feature id for the '<em><b>Prefs</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int USER_PREFS__PREFS = 0;

	/**
	 * The number of structural features of the the '<em>User Prefs</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int USER_PREFS_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link org.homeunix.drummer.prefs.impl.WindowAttributesImpl <em>Window Attributes</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.homeunix.drummer.prefs.impl.WindowAttributesImpl
	 * @see org.homeunix.drummer.prefs.impl.PrefsPackageImpl#getWindowAttributes()
	 * @generated
	 */
	int WINDOW_ATTRIBUTES = 6;

	/**
	 * The feature id for the '<em><b>X</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WINDOW_ATTRIBUTES__X = 0;

	/**
	 * The feature id for the '<em><b>Y</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WINDOW_ATTRIBUTES__Y = 1;

	/**
	 * The feature id for the '<em><b>Width</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WINDOW_ATTRIBUTES__WIDTH = 2;

	/**
	 * The feature id for the '<em><b>Height</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WINDOW_ATTRIBUTES__HEIGHT = 3;

	/**
	 * The number of structural features of the the '<em>Window Attributes</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WINDOW_ATTRIBUTES_FEATURE_COUNT = 4;


	/**
	 * Returns the meta object for class '{@link org.homeunix.drummer.prefs.DictData <em>Dict Data</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Dict Data</em>'.
	 * @see org.homeunix.drummer.prefs.DictData
	 * @generated
	 */
	EClass getDictData();

	/**
	 * Returns the meta object for the attribute '{@link org.homeunix.drummer.prefs.DictData#getNumber <em>Number</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Number</em>'.
	 * @see org.homeunix.drummer.prefs.DictData#getNumber()
	 * @see #getDictData()
	 * @generated
	 */
	EAttribute getDictData_Number();

	/**
	 * Returns the meta object for the attribute '{@link org.homeunix.drummer.prefs.DictData#getMemo <em>Memo</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Memo</em>'.
	 * @see org.homeunix.drummer.prefs.DictData#getMemo()
	 * @see #getDictData()
	 * @generated
	 */
	EAttribute getDictData_Memo();

	/**
	 * Returns the meta object for the attribute '{@link org.homeunix.drummer.prefs.DictData#getAmount <em>Amount</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Amount</em>'.
	 * @see org.homeunix.drummer.prefs.DictData#getAmount()
	 * @see #getDictData()
	 * @generated
	 */
	EAttribute getDictData_Amount();

	/**
	 * Returns the meta object for the attribute '{@link org.homeunix.drummer.prefs.DictData#getTo <em>To</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>To</em>'.
	 * @see org.homeunix.drummer.prefs.DictData#getTo()
	 * @see #getDictData()
	 * @generated
	 */
	EAttribute getDictData_To();

	/**
	 * Returns the meta object for the attribute '{@link org.homeunix.drummer.prefs.DictData#getFrom <em>From</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>From</em>'.
	 * @see org.homeunix.drummer.prefs.DictData#getFrom()
	 * @see #getDictData()
	 * @generated
	 */
	EAttribute getDictData_From();

	/**
	 * Returns the meta object for class '{@link org.homeunix.drummer.prefs.DictEntry <em>Dict Entry</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Dict Entry</em>'.
	 * @see org.homeunix.drummer.prefs.DictEntry
	 * @generated
	 */
	EClass getDictEntry();

	/**
	 * Returns the meta object for the attribute '{@link org.homeunix.drummer.prefs.DictEntry#getEntry <em>Entry</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Entry</em>'.
	 * @see org.homeunix.drummer.prefs.DictEntry#getEntry()
	 * @see #getDictEntry()
	 * @generated
	 */
	EAttribute getDictEntry_Entry();

	/**
	 * Returns the meta object for the containment reference '{@link org.homeunix.drummer.prefs.DictEntry#getData <em>Data</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Data</em>'.
	 * @see org.homeunix.drummer.prefs.DictEntry#getData()
	 * @see #getDictEntry()
	 * @generated
	 */
	EReference getDictEntry_Data();

	/**
	 * Returns the meta object for class '{@link org.homeunix.drummer.prefs.ListAttributes <em>List Attributes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>List Attributes</em>'.
	 * @see org.homeunix.drummer.prefs.ListAttributes
	 * @generated
	 */
	EClass getListAttributes();

	/**
	 * Returns the meta object for the attribute '{@link org.homeunix.drummer.prefs.ListAttributes#isUnrolled <em>Unrolled</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Unrolled</em>'.
	 * @see org.homeunix.drummer.prefs.ListAttributes#isUnrolled()
	 * @see #getListAttributes()
	 * @generated
	 */
	EAttribute getListAttributes_Unrolled();

	/**
	 * Returns the meta object for class '{@link org.homeunix.drummer.prefs.ListEntry <em>List Entry</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>List Entry</em>'.
	 * @see org.homeunix.drummer.prefs.ListEntry
	 * @generated
	 */
	EClass getListEntry();

	/**
	 * Returns the meta object for the attribute '{@link org.homeunix.drummer.prefs.ListEntry#getEntry <em>Entry</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Entry</em>'.
	 * @see org.homeunix.drummer.prefs.ListEntry#getEntry()
	 * @see #getListEntry()
	 * @generated
	 */
	EAttribute getListEntry_Entry();

	/**
	 * Returns the meta object for the containment reference '{@link org.homeunix.drummer.prefs.ListEntry#getAttributes <em>Attributes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Attributes</em>'.
	 * @see org.homeunix.drummer.prefs.ListEntry#getAttributes()
	 * @see #getListEntry()
	 * @generated
	 */
	EReference getListEntry_Attributes();

	/**
	 * Returns the meta object for class '{@link org.homeunix.drummer.prefs.Prefs <em>Prefs</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Prefs</em>'.
	 * @see org.homeunix.drummer.prefs.Prefs
	 * @generated
	 */
	EClass getPrefs();

	/**
	 * Returns the meta object for the attribute '{@link org.homeunix.drummer.prefs.Prefs#getDataFile <em>Data File</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Data File</em>'.
	 * @see org.homeunix.drummer.prefs.Prefs#getDataFile()
	 * @see #getPrefs()
	 * @generated
	 */
	EAttribute getPrefs_DataFile();

	/**
	 * Returns the meta object for the attribute '{@link org.homeunix.drummer.prefs.Prefs#getLanguage <em>Language</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Language</em>'.
	 * @see org.homeunix.drummer.prefs.Prefs#getLanguage()
	 * @see #getPrefs()
	 * @generated
	 */
	EAttribute getPrefs_Language();

	/**
	 * Returns the meta object for the attribute '{@link org.homeunix.drummer.prefs.Prefs#isShowDeletedAccounts <em>Show Deleted Accounts</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Show Deleted Accounts</em>'.
	 * @see org.homeunix.drummer.prefs.Prefs#isShowDeletedAccounts()
	 * @see #getPrefs()
	 * @generated
	 */
	EAttribute getPrefs_ShowDeletedAccounts();

	/**
	 * Returns the meta object for the attribute '{@link org.homeunix.drummer.prefs.Prefs#isShowDeletedCategories <em>Show Deleted Categories</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Show Deleted Categories</em>'.
	 * @see org.homeunix.drummer.prefs.Prefs#isShowDeletedCategories()
	 * @see #getPrefs()
	 * @generated
	 */
	EAttribute getPrefs_ShowDeletedCategories();

	/**
	 * Returns the meta object for the attribute '{@link org.homeunix.drummer.prefs.Prefs#getDateFormat <em>Date Format</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Date Format</em>'.
	 * @see org.homeunix.drummer.prefs.Prefs#getDateFormat()
	 * @see #getPrefs()
	 * @generated
	 */
	EAttribute getPrefs_DateFormat();

	/**
	 * Returns the meta object for the attribute '{@link org.homeunix.drummer.prefs.Prefs#getBudgetPeriod <em>Budget Period</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Budget Period</em>'.
	 * @see org.homeunix.drummer.prefs.Prefs#getBudgetPeriod()
	 * @see #getPrefs()
	 * @generated
	 */
	EAttribute getPrefs_BudgetPeriod();

	/**
	 * Returns the meta object for the attribute '{@link org.homeunix.drummer.prefs.Prefs#isShowAccountTypes <em>Show Account Types</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Show Account Types</em>'.
	 * @see org.homeunix.drummer.prefs.Prefs#isShowAccountTypes()
	 * @see #getPrefs()
	 * @generated
	 */
	EAttribute getPrefs_ShowAccountTypes();

	/**
	 * Returns the meta object for the attribute '{@link org.homeunix.drummer.prefs.Prefs#isEnableUpdateNotifications <em>Enable Update Notifications</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Enable Update Notifications</em>'.
	 * @see org.homeunix.drummer.prefs.Prefs#isEnableUpdateNotifications()
	 * @see #getPrefs()
	 * @generated
	 */
	EAttribute getPrefs_EnableUpdateNotifications();

	/**
	 * Returns the meta object for the containment reference '{@link org.homeunix.drummer.prefs.Prefs#getTransactionsWindow <em>Transactions Window</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Transactions Window</em>'.
	 * @see org.homeunix.drummer.prefs.Prefs#getTransactionsWindow()
	 * @see #getPrefs()
	 * @generated
	 */
	EReference getPrefs_TransactionsWindow();

	/**
	 * Returns the meta object for the containment reference '{@link org.homeunix.drummer.prefs.Prefs#getGraphsWindow <em>Graphs Window</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Graphs Window</em>'.
	 * @see org.homeunix.drummer.prefs.Prefs#getGraphsWindow()
	 * @see #getPrefs()
	 * @generated
	 */
	EReference getPrefs_GraphsWindow();

	/**
	 * Returns the meta object for the containment reference '{@link org.homeunix.drummer.prefs.Prefs#getMainWindow <em>Main Window</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Main Window</em>'.
	 * @see org.homeunix.drummer.prefs.Prefs#getMainWindow()
	 * @see #getPrefs()
	 * @generated
	 */
	EReference getPrefs_MainWindow();

	/**
	 * Returns the meta object for the containment reference list '{@link org.homeunix.drummer.prefs.Prefs#getListEntries <em>List Entries</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>List Entries</em>'.
	 * @see org.homeunix.drummer.prefs.Prefs#getListEntries()
	 * @see #getPrefs()
	 * @generated
	 */
	EReference getPrefs_ListEntries();

	/**
	 * Returns the meta object for the containment reference '{@link org.homeunix.drummer.prefs.Prefs#getReportsWindow <em>Reports Window</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Reports Window</em>'.
	 * @see org.homeunix.drummer.prefs.Prefs#getReportsWindow()
	 * @see #getPrefs()
	 * @generated
	 */
	EReference getPrefs_ReportsWindow();

	/**
	 * Returns the meta object for the containment reference list '{@link org.homeunix.drummer.prefs.Prefs#getDescDict <em>Desc Dict</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Desc Dict</em>'.
	 * @see org.homeunix.drummer.prefs.Prefs#getDescDict()
	 * @see #getPrefs()
	 * @generated
	 */
	EReference getPrefs_DescDict();

	/**
	 * Returns the meta object for class '{@link org.homeunix.drummer.prefs.UserPrefs <em>User Prefs</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>User Prefs</em>'.
	 * @see org.homeunix.drummer.prefs.UserPrefs
	 * @generated
	 */
	EClass getUserPrefs();

	/**
	 * Returns the meta object for the containment reference '{@link org.homeunix.drummer.prefs.UserPrefs#getPrefs <em>Prefs</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Prefs</em>'.
	 * @see org.homeunix.drummer.prefs.UserPrefs#getPrefs()
	 * @see #getUserPrefs()
	 * @generated
	 */
	EReference getUserPrefs_Prefs();

	/**
	 * Returns the meta object for class '{@link org.homeunix.drummer.prefs.WindowAttributes <em>Window Attributes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Window Attributes</em>'.
	 * @see org.homeunix.drummer.prefs.WindowAttributes
	 * @generated
	 */
	EClass getWindowAttributes();

	/**
	 * Returns the meta object for the attribute '{@link org.homeunix.drummer.prefs.WindowAttributes#getX <em>X</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>X</em>'.
	 * @see org.homeunix.drummer.prefs.WindowAttributes#getX()
	 * @see #getWindowAttributes()
	 * @generated
	 */
	EAttribute getWindowAttributes_X();

	/**
	 * Returns the meta object for the attribute '{@link org.homeunix.drummer.prefs.WindowAttributes#getY <em>Y</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Y</em>'.
	 * @see org.homeunix.drummer.prefs.WindowAttributes#getY()
	 * @see #getWindowAttributes()
	 * @generated
	 */
	EAttribute getWindowAttributes_Y();

	/**
	 * Returns the meta object for the attribute '{@link org.homeunix.drummer.prefs.WindowAttributes#getWidth <em>Width</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Width</em>'.
	 * @see org.homeunix.drummer.prefs.WindowAttributes#getWidth()
	 * @see #getWindowAttributes()
	 * @generated
	 */
	EAttribute getWindowAttributes_Width();

	/**
	 * Returns the meta object for the attribute '{@link org.homeunix.drummer.prefs.WindowAttributes#getHeight <em>Height</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Height</em>'.
	 * @see org.homeunix.drummer.prefs.WindowAttributes#getHeight()
	 * @see #getWindowAttributes()
	 * @generated
	 */
	EAttribute getWindowAttributes_Height();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	PrefsFactory getPrefsFactory();

} //PrefsPackage
