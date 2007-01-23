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
public interface PrefsPackage extends EPackage {
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
	 * The number of structural features of the '<em>Dict Data</em>' class.
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
	 * The number of structural features of the '<em>Dict Entry</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DICT_ENTRY_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link org.homeunix.drummer.prefs.impl.IntervalImpl <em>Interval</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.homeunix.drummer.prefs.impl.IntervalImpl
	 * @see org.homeunix.drummer.prefs.impl.PrefsPackageImpl#getInterval()
	 * @generated
	 */
	int INTERVAL = 2;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTERVAL__NAME = 0;

	/**
	 * The feature id for the '<em><b>Length</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTERVAL__LENGTH = 1;

	/**
	 * The feature id for the '<em><b>Days</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTERVAL__DAYS = 2;

	/**
	 * The number of structural features of the '<em>Interval</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTERVAL_FEATURE_COUNT = 3;

	/**
	 * The meta object id for the '{@link org.homeunix.drummer.prefs.impl.IntervalsImpl <em>Intervals</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.homeunix.drummer.prefs.impl.IntervalsImpl
	 * @see org.homeunix.drummer.prefs.impl.PrefsPackageImpl#getIntervals()
	 * @generated
	 */
	int INTERVALS = 3;

	/**
	 * The feature id for the '<em><b>Intervals</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTERVALS__INTERVALS = 0;

	/**
	 * The number of structural features of the '<em>Intervals</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTERVALS_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link org.homeunix.drummer.prefs.impl.ListAttributesImpl <em>List Attributes</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.homeunix.drummer.prefs.impl.ListAttributesImpl
	 * @see org.homeunix.drummer.prefs.impl.PrefsPackageImpl#getListAttributes()
	 * @generated
	 */
	int LIST_ATTRIBUTES = 4;

	/**
	 * The feature id for the '<em><b>Unrolled</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LIST_ATTRIBUTES__UNROLLED = 0;

	/**
	 * The number of structural features of the '<em>List Attributes</em>' class.
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
	int LIST_ENTRY = 5;

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
	 * The number of structural features of the '<em>List Entry</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LIST_ENTRY_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link org.homeunix.drummer.prefs.impl.ListsImpl <em>Lists</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.homeunix.drummer.prefs.impl.ListsImpl
	 * @see org.homeunix.drummer.prefs.impl.PrefsPackageImpl#getLists()
	 * @generated
	 */
	int LISTS = 6;

	/**
	 * The feature id for the '<em><b>Desc Dict</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LISTS__DESC_DICT = 0;

	/**
	 * The feature id for the '<em><b>Plugins</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LISTS__PLUGINS = 1;

	/**
	 * The feature id for the '<em><b>List Entries</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LISTS__LIST_ENTRIES = 2;

	/**
	 * The number of structural features of the '<em>Lists</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LISTS_FEATURE_COUNT = 3;

	/**
	 * The meta object id for the '{@link org.homeunix.drummer.prefs.impl.PluginImpl <em>Plugin</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.homeunix.drummer.prefs.impl.PluginImpl
	 * @see org.homeunix.drummer.prefs.impl.PrefsPackageImpl#getPlugin()
	 * @generated
	 */
	int PLUGIN = 7;

	/**
	 * The feature id for the '<em><b>Jar File</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PLUGIN__JAR_FILE = 0;

	/**
	 * The feature id for the '<em><b>Class Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PLUGIN__CLASS_NAME = 1;

	/**
	 * The number of structural features of the '<em>Plugin</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PLUGIN_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link org.homeunix.drummer.prefs.impl.PrefsImpl <em>Prefs</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.homeunix.drummer.prefs.impl.PrefsImpl
	 * @see org.homeunix.drummer.prefs.impl.PrefsPackageImpl#getPrefs()
	 * @generated
	 */
	int PREFS = 8;

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
	 * The feature id for the '<em><b>Selected Interval</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PREFS__SELECTED_INTERVAL = 8;

	/**
	 * The feature id for the '<em><b>Show Auto Complete</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PREFS__SHOW_AUTO_COMPLETE = 9;

	/**
	 * The feature id for the '<em><b>Currency Symbol</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PREFS__CURRENCY_SYMBOL = 10;

	/**
	 * The feature id for the '<em><b>Show Credit Limit</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PREFS__SHOW_CREDIT_LIMIT = 11;

	/**
	 * The feature id for the '<em><b>Show Interest Rate</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PREFS__SHOW_INTEREST_RATE = 12;

	/**
	 * The feature id for the '<em><b>Show Advanced</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PREFS__SHOW_ADVANCED = 13;

	/**
	 * The feature id for the '<em><b>Number Of Backups</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PREFS__NUMBER_OF_BACKUPS = 14;

	/**
	 * The feature id for the '<em><b>Look And Feel Class</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PREFS__LOOK_AND_FEEL_CLASS = 15;

	/**
	 * The feature id for the '<em><b>Prompt For File At Startup</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PREFS__PROMPT_FOR_FILE_AT_STARTUP = 16;

	/**
	 * The feature id for the '<em><b>Intervals</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PREFS__INTERVALS = 17;

	/**
	 * The feature id for the '<em><b>Windows</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PREFS__WINDOWS = 18;

	/**
	 * The feature id for the '<em><b>Lists</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PREFS__LISTS = 19;

	/**
	 * The feature id for the '<em><b>Last Version Run</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PREFS__LAST_VERSION_RUN = 20;

	/**
	 * The number of structural features of the '<em>Prefs</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PREFS_FEATURE_COUNT = 21;


	/**
	 * The meta object id for the '{@link org.homeunix.drummer.prefs.impl.UserPrefsImpl <em>User Prefs</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.homeunix.drummer.prefs.impl.UserPrefsImpl
	 * @see org.homeunix.drummer.prefs.impl.PrefsPackageImpl#getUserPrefs()
	 * @generated
	 */
	int USER_PREFS = 9;

	/**
	 * The feature id for the '<em><b>Prefs</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int USER_PREFS__PREFS = 0;

	/**
	 * The number of structural features of the '<em>User Prefs</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int USER_PREFS_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link org.homeunix.drummer.prefs.impl.VersionImpl <em>Version</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.homeunix.drummer.prefs.impl.VersionImpl
	 * @see org.homeunix.drummer.prefs.impl.PrefsPackageImpl#getVersion()
	 * @generated
	 */
	int VERSION = 10;

	/**
	 * The feature id for the '<em><b>Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSION__VERSION = 0;

	/**
	 * The number of structural features of the '<em>Version</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSION_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link org.homeunix.drummer.prefs.impl.WindowAttributesImpl <em>Window Attributes</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.homeunix.drummer.prefs.impl.WindowAttributesImpl
	 * @see org.homeunix.drummer.prefs.impl.PrefsPackageImpl#getWindowAttributes()
	 * @generated
	 */
	int WINDOW_ATTRIBUTES = 11;

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
	 * The number of structural features of the '<em>Window Attributes</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WINDOW_ATTRIBUTES_FEATURE_COUNT = 4;


	/**
	 * The meta object id for the '{@link org.homeunix.drummer.prefs.impl.WindowsImpl <em>Windows</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.homeunix.drummer.prefs.impl.WindowsImpl
	 * @see org.homeunix.drummer.prefs.impl.PrefsPackageImpl#getWindows()
	 * @generated
	 */
	int WINDOWS = 12;

	/**
	 * The feature id for the '<em><b>Main Window</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WINDOWS__MAIN_WINDOW = 0;

	/**
	 * The feature id for the '<em><b>Transactions Window</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WINDOWS__TRANSACTIONS_WINDOW = 1;

	/**
	 * The feature id for the '<em><b>Graphs Window</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WINDOWS__GRAPHS_WINDOW = 2;

	/**
	 * The feature id for the '<em><b>Reports Window</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WINDOWS__REPORTS_WINDOW = 3;

	/**
	 * The number of structural features of the '<em>Windows</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WINDOWS_FEATURE_COUNT = 4;


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
	 * Returns the meta object for class '{@link org.homeunix.drummer.prefs.Interval <em>Interval</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Interval</em>'.
	 * @see org.homeunix.drummer.prefs.Interval
	 * @generated
	 */
	EClass getInterval();

	/**
	 * Returns the meta object for the attribute '{@link org.homeunix.drummer.prefs.Interval#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.homeunix.drummer.prefs.Interval#getName()
	 * @see #getInterval()
	 * @generated
	 */
	EAttribute getInterval_Name();

	/**
	 * Returns the meta object for the attribute '{@link org.homeunix.drummer.prefs.Interval#getLength <em>Length</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Length</em>'.
	 * @see org.homeunix.drummer.prefs.Interval#getLength()
	 * @see #getInterval()
	 * @generated
	 */
	EAttribute getInterval_Length();

	/**
	 * Returns the meta object for the attribute '{@link org.homeunix.drummer.prefs.Interval#isDays <em>Days</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Days</em>'.
	 * @see org.homeunix.drummer.prefs.Interval#isDays()
	 * @see #getInterval()
	 * @generated
	 */
	EAttribute getInterval_Days();

	/**
	 * Returns the meta object for class '{@link org.homeunix.drummer.prefs.Intervals <em>Intervals</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Intervals</em>'.
	 * @see org.homeunix.drummer.prefs.Intervals
	 * @generated
	 */
	EClass getIntervals();

	/**
	 * Returns the meta object for the containment reference list '{@link org.homeunix.drummer.prefs.Intervals#getIntervals <em>Intervals</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Intervals</em>'.
	 * @see org.homeunix.drummer.prefs.Intervals#getIntervals()
	 * @see #getIntervals()
	 * @generated
	 */
	EReference getIntervals_Intervals();

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
	 * Returns the meta object for class '{@link org.homeunix.drummer.prefs.Lists <em>Lists</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Lists</em>'.
	 * @see org.homeunix.drummer.prefs.Lists
	 * @generated
	 */
	EClass getLists();

	/**
	 * Returns the meta object for the containment reference list '{@link org.homeunix.drummer.prefs.Lists#getDescDict <em>Desc Dict</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Desc Dict</em>'.
	 * @see org.homeunix.drummer.prefs.Lists#getDescDict()
	 * @see #getLists()
	 * @generated
	 */
	EReference getLists_DescDict();

	/**
	 * Returns the meta object for the containment reference list '{@link org.homeunix.drummer.prefs.Lists#getListEntries <em>List Entries</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>List Entries</em>'.
	 * @see org.homeunix.drummer.prefs.Lists#getListEntries()
	 * @see #getLists()
	 * @generated
	 */
	EReference getLists_ListEntries();

	/**
	 * Returns the meta object for the containment reference list '{@link org.homeunix.drummer.prefs.Lists#getPlugins <em>Plugins</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Plugins</em>'.
	 * @see org.homeunix.drummer.prefs.Lists#getPlugins()
	 * @see #getLists()
	 * @generated
	 */
	EReference getLists_Plugins();

	/**
	 * Returns the meta object for class '{@link org.homeunix.drummer.prefs.Plugin <em>Plugin</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Plugin</em>'.
	 * @see org.homeunix.drummer.prefs.Plugin
	 * @generated
	 */
	EClass getPlugin();

	/**
	 * Returns the meta object for the attribute '{@link org.homeunix.drummer.prefs.Plugin#getJarFile <em>Jar File</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Jar File</em>'.
	 * @see org.homeunix.drummer.prefs.Plugin#getJarFile()
	 * @see #getPlugin()
	 * @generated
	 */
	EAttribute getPlugin_JarFile();

	/**
	 * Returns the meta object for the attribute '{@link org.homeunix.drummer.prefs.Plugin#getClassName <em>Class Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Class Name</em>'.
	 * @see org.homeunix.drummer.prefs.Plugin#getClassName()
	 * @see #getPlugin()
	 * @generated
	 */
	EAttribute getPlugin_ClassName();

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
	 * Returns the meta object for the containment reference '{@link org.homeunix.drummer.prefs.Prefs#getLastVersionRun <em>Last Version Run</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Last Version Run</em>'.
	 * @see org.homeunix.drummer.prefs.Prefs#getLastVersionRun()
	 * @see #getPrefs()
	 * @generated
	 */
	EReference getPrefs_LastVersionRun();

	/**
	 * Returns the meta object for the containment reference '{@link org.homeunix.drummer.prefs.Prefs#getIntervals <em>Intervals</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Intervals</em>'.
	 * @see org.homeunix.drummer.prefs.Prefs#getIntervals()
	 * @see #getPrefs()
	 * @generated
	 */
	EReference getPrefs_Intervals();

	/**
	 * Returns the meta object for the attribute '{@link org.homeunix.drummer.prefs.Prefs#getSelectedInterval <em>Selected Interval</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Selected Interval</em>'.
	 * @see org.homeunix.drummer.prefs.Prefs#getSelectedInterval()
	 * @see #getPrefs()
	 * @generated
	 */
	EAttribute getPrefs_SelectedInterval();

	/**
	 * Returns the meta object for the attribute '{@link org.homeunix.drummer.prefs.Prefs#isShowAutoComplete <em>Show Auto Complete</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Show Auto Complete</em>'.
	 * @see org.homeunix.drummer.prefs.Prefs#isShowAutoComplete()
	 * @see #getPrefs()
	 * @generated
	 */
	EAttribute getPrefs_ShowAutoComplete();

	/**
	 * Returns the meta object for the attribute '{@link org.homeunix.drummer.prefs.Prefs#getCurrencySymbol <em>Currency Symbol</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Currency Symbol</em>'.
	 * @see org.homeunix.drummer.prefs.Prefs#getCurrencySymbol()
	 * @see #getPrefs()
	 * @generated
	 */
	EAttribute getPrefs_CurrencySymbol();

	/**
	 * Returns the meta object for the attribute '{@link org.homeunix.drummer.prefs.Prefs#isShowCreditLimit <em>Show Credit Limit</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Show Credit Limit</em>'.
	 * @see org.homeunix.drummer.prefs.Prefs#isShowCreditLimit()
	 * @see #getPrefs()
	 * @generated
	 */
	EAttribute getPrefs_ShowCreditLimit();

	/**
	 * Returns the meta object for the attribute '{@link org.homeunix.drummer.prefs.Prefs#isShowInterestRate <em>Show Interest Rate</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Show Interest Rate</em>'.
	 * @see org.homeunix.drummer.prefs.Prefs#isShowInterestRate()
	 * @see #getPrefs()
	 * @generated
	 */
	EAttribute getPrefs_ShowInterestRate();

	/**
	 * Returns the meta object for the attribute '{@link org.homeunix.drummer.prefs.Prefs#isShowAdvanced <em>Show Advanced</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Show Advanced</em>'.
	 * @see org.homeunix.drummer.prefs.Prefs#isShowAdvanced()
	 * @see #getPrefs()
	 * @generated
	 */
	EAttribute getPrefs_ShowAdvanced();

	/**
	 * Returns the meta object for the attribute '{@link org.homeunix.drummer.prefs.Prefs#getNumberOfBackups <em>Number Of Backups</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Number Of Backups</em>'.
	 * @see org.homeunix.drummer.prefs.Prefs#getNumberOfBackups()
	 * @see #getPrefs()
	 * @generated
	 */
	EAttribute getPrefs_NumberOfBackups();

	/**
	 * Returns the meta object for the attribute '{@link org.homeunix.drummer.prefs.Prefs#getLookAndFeelClass <em>Look And Feel Class</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Look And Feel Class</em>'.
	 * @see org.homeunix.drummer.prefs.Prefs#getLookAndFeelClass()
	 * @see #getPrefs()
	 * @generated
	 */
	EAttribute getPrefs_LookAndFeelClass();

	/**
	 * Returns the meta object for the attribute '{@link org.homeunix.drummer.prefs.Prefs#isPromptForFileAtStartup <em>Prompt For File At Startup</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Prompt For File At Startup</em>'.
	 * @see org.homeunix.drummer.prefs.Prefs#isPromptForFileAtStartup()
	 * @see #getPrefs()
	 * @generated
	 */
	EAttribute getPrefs_PromptForFileAtStartup();

	/**
	 * Returns the meta object for the containment reference '{@link org.homeunix.drummer.prefs.Prefs#getLists <em>Lists</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Lists</em>'.
	 * @see org.homeunix.drummer.prefs.Prefs#getLists()
	 * @see #getPrefs()
	 * @generated
	 */
	EReference getPrefs_Lists();

	/**
	 * Returns the meta object for the containment reference '{@link org.homeunix.drummer.prefs.Prefs#getWindows <em>Windows</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Windows</em>'.
	 * @see org.homeunix.drummer.prefs.Prefs#getWindows()
	 * @see #getPrefs()
	 * @generated
	 */
	EReference getPrefs_Windows();

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
	 * Returns the meta object for class '{@link org.homeunix.drummer.prefs.Version <em>Version</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Version</em>'.
	 * @see org.homeunix.drummer.prefs.Version
	 * @generated
	 */
	EClass getVersion();

	/**
	 * Returns the meta object for the attribute '{@link org.homeunix.drummer.prefs.Version#getVersion <em>Version</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Version</em>'.
	 * @see org.homeunix.drummer.prefs.Version#getVersion()
	 * @see #getVersion()
	 * @generated
	 */
	EAttribute getVersion_Version();

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
	 * Returns the meta object for class '{@link org.homeunix.drummer.prefs.Windows <em>Windows</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Windows</em>'.
	 * @see org.homeunix.drummer.prefs.Windows
	 * @generated
	 */
	EClass getWindows();

	/**
	 * Returns the meta object for the containment reference '{@link org.homeunix.drummer.prefs.Windows#getTransactionsWindow <em>Transactions Window</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Transactions Window</em>'.
	 * @see org.homeunix.drummer.prefs.Windows#getTransactionsWindow()
	 * @see #getWindows()
	 * @generated
	 */
	EReference getWindows_TransactionsWindow();

	/**
	 * Returns the meta object for the containment reference '{@link org.homeunix.drummer.prefs.Windows#getReportsWindow <em>Reports Window</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Reports Window</em>'.
	 * @see org.homeunix.drummer.prefs.Windows#getReportsWindow()
	 * @see #getWindows()
	 * @generated
	 */
	EReference getWindows_ReportsWindow();

	/**
	 * Returns the meta object for the containment reference '{@link org.homeunix.drummer.prefs.Windows#getGraphsWindow <em>Graphs Window</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Graphs Window</em>'.
	 * @see org.homeunix.drummer.prefs.Windows#getGraphsWindow()
	 * @see #getWindows()
	 * @generated
	 */
	EReference getWindows_GraphsWindow();

	/**
	 * Returns the meta object for the containment reference '{@link org.homeunix.drummer.prefs.Windows#getMainWindow <em>Main Window</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Main Window</em>'.
	 * @see org.homeunix.drummer.prefs.Windows#getMainWindow()
	 * @see #getWindows()
	 * @generated
	 */
	EReference getWindows_MainWindow();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	PrefsFactory getPrefsFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals  {
		/**
		 * The meta object literal for the '{@link org.homeunix.drummer.prefs.impl.DictDataImpl <em>Dict Data</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.homeunix.drummer.prefs.impl.DictDataImpl
		 * @see org.homeunix.drummer.prefs.impl.PrefsPackageImpl#getDictData()
		 * @generated
		 */
		EClass DICT_DATA = eINSTANCE.getDictData();

		/**
		 * The meta object literal for the '<em><b>Number</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DICT_DATA__NUMBER = eINSTANCE.getDictData_Number();

		/**
		 * The meta object literal for the '<em><b>Memo</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DICT_DATA__MEMO = eINSTANCE.getDictData_Memo();

		/**
		 * The meta object literal for the '<em><b>Amount</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DICT_DATA__AMOUNT = eINSTANCE.getDictData_Amount();

		/**
		 * The meta object literal for the '<em><b>To</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DICT_DATA__TO = eINSTANCE.getDictData_To();

		/**
		 * The meta object literal for the '<em><b>From</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DICT_DATA__FROM = eINSTANCE.getDictData_From();

		/**
		 * The meta object literal for the '{@link org.homeunix.drummer.prefs.impl.DictEntryImpl <em>Dict Entry</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.homeunix.drummer.prefs.impl.DictEntryImpl
		 * @see org.homeunix.drummer.prefs.impl.PrefsPackageImpl#getDictEntry()
		 * @generated
		 */
		EClass DICT_ENTRY = eINSTANCE.getDictEntry();

		/**
		 * The meta object literal for the '<em><b>Entry</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DICT_ENTRY__ENTRY = eINSTANCE.getDictEntry_Entry();

		/**
		 * The meta object literal for the '<em><b>Data</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DICT_ENTRY__DATA = eINSTANCE.getDictEntry_Data();

		/**
		 * The meta object literal for the '{@link org.homeunix.drummer.prefs.impl.IntervalImpl <em>Interval</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.homeunix.drummer.prefs.impl.IntervalImpl
		 * @see org.homeunix.drummer.prefs.impl.PrefsPackageImpl#getInterval()
		 * @generated
		 */
		EClass INTERVAL = eINSTANCE.getInterval();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute INTERVAL__NAME = eINSTANCE.getInterval_Name();

		/**
		 * The meta object literal for the '<em><b>Length</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute INTERVAL__LENGTH = eINSTANCE.getInterval_Length();

		/**
		 * The meta object literal for the '<em><b>Days</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute INTERVAL__DAYS = eINSTANCE.getInterval_Days();

		/**
		 * The meta object literal for the '{@link org.homeunix.drummer.prefs.impl.IntervalsImpl <em>Intervals</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.homeunix.drummer.prefs.impl.IntervalsImpl
		 * @see org.homeunix.drummer.prefs.impl.PrefsPackageImpl#getIntervals()
		 * @generated
		 */
		EClass INTERVALS = eINSTANCE.getIntervals();

		/**
		 * The meta object literal for the '<em><b>Intervals</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference INTERVALS__INTERVALS = eINSTANCE.getIntervals_Intervals();

		/**
		 * The meta object literal for the '{@link org.homeunix.drummer.prefs.impl.ListAttributesImpl <em>List Attributes</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.homeunix.drummer.prefs.impl.ListAttributesImpl
		 * @see org.homeunix.drummer.prefs.impl.PrefsPackageImpl#getListAttributes()
		 * @generated
		 */
		EClass LIST_ATTRIBUTES = eINSTANCE.getListAttributes();

		/**
		 * The meta object literal for the '<em><b>Unrolled</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute LIST_ATTRIBUTES__UNROLLED = eINSTANCE.getListAttributes_Unrolled();

		/**
		 * The meta object literal for the '{@link org.homeunix.drummer.prefs.impl.ListEntryImpl <em>List Entry</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.homeunix.drummer.prefs.impl.ListEntryImpl
		 * @see org.homeunix.drummer.prefs.impl.PrefsPackageImpl#getListEntry()
		 * @generated
		 */
		EClass LIST_ENTRY = eINSTANCE.getListEntry();

		/**
		 * The meta object literal for the '<em><b>Entry</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute LIST_ENTRY__ENTRY = eINSTANCE.getListEntry_Entry();

		/**
		 * The meta object literal for the '<em><b>Attributes</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference LIST_ENTRY__ATTRIBUTES = eINSTANCE.getListEntry_Attributes();

		/**
		 * The meta object literal for the '{@link org.homeunix.drummer.prefs.impl.ListsImpl <em>Lists</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.homeunix.drummer.prefs.impl.ListsImpl
		 * @see org.homeunix.drummer.prefs.impl.PrefsPackageImpl#getLists()
		 * @generated
		 */
		EClass LISTS = eINSTANCE.getLists();

		/**
		 * The meta object literal for the '<em><b>Desc Dict</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference LISTS__DESC_DICT = eINSTANCE.getLists_DescDict();

		/**
		 * The meta object literal for the '<em><b>List Entries</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference LISTS__LIST_ENTRIES = eINSTANCE.getLists_ListEntries();

		/**
		 * The meta object literal for the '<em><b>Plugins</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference LISTS__PLUGINS = eINSTANCE.getLists_Plugins();

		/**
		 * The meta object literal for the '{@link org.homeunix.drummer.prefs.impl.PluginImpl <em>Plugin</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.homeunix.drummer.prefs.impl.PluginImpl
		 * @see org.homeunix.drummer.prefs.impl.PrefsPackageImpl#getPlugin()
		 * @generated
		 */
		EClass PLUGIN = eINSTANCE.getPlugin();

		/**
		 * The meta object literal for the '<em><b>Jar File</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PLUGIN__JAR_FILE = eINSTANCE.getPlugin_JarFile();

		/**
		 * The meta object literal for the '<em><b>Class Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PLUGIN__CLASS_NAME = eINSTANCE.getPlugin_ClassName();

		/**
		 * The meta object literal for the '{@link org.homeunix.drummer.prefs.impl.PrefsImpl <em>Prefs</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.homeunix.drummer.prefs.impl.PrefsImpl
		 * @see org.homeunix.drummer.prefs.impl.PrefsPackageImpl#getPrefs()
		 * @generated
		 */
		EClass PREFS = eINSTANCE.getPrefs();

		/**
		 * The meta object literal for the '<em><b>Data File</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PREFS__DATA_FILE = eINSTANCE.getPrefs_DataFile();

		/**
		 * The meta object literal for the '<em><b>Language</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PREFS__LANGUAGE = eINSTANCE.getPrefs_Language();

		/**
		 * The meta object literal for the '<em><b>Show Deleted Accounts</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PREFS__SHOW_DELETED_ACCOUNTS = eINSTANCE.getPrefs_ShowDeletedAccounts();

		/**
		 * The meta object literal for the '<em><b>Show Deleted Categories</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PREFS__SHOW_DELETED_CATEGORIES = eINSTANCE.getPrefs_ShowDeletedCategories();

		/**
		 * The meta object literal for the '<em><b>Date Format</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PREFS__DATE_FORMAT = eINSTANCE.getPrefs_DateFormat();

		/**
		 * The meta object literal for the '<em><b>Budget Period</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PREFS__BUDGET_PERIOD = eINSTANCE.getPrefs_BudgetPeriod();

		/**
		 * The meta object literal for the '<em><b>Show Account Types</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PREFS__SHOW_ACCOUNT_TYPES = eINSTANCE.getPrefs_ShowAccountTypes();

		/**
		 * The meta object literal for the '<em><b>Enable Update Notifications</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PREFS__ENABLE_UPDATE_NOTIFICATIONS = eINSTANCE.getPrefs_EnableUpdateNotifications();

		/**
		 * The meta object literal for the '<em><b>Selected Interval</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PREFS__SELECTED_INTERVAL = eINSTANCE.getPrefs_SelectedInterval();

		/**
		 * The meta object literal for the '<em><b>Show Auto Complete</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PREFS__SHOW_AUTO_COMPLETE = eINSTANCE.getPrefs_ShowAutoComplete();

		/**
		 * The meta object literal for the '<em><b>Currency Symbol</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PREFS__CURRENCY_SYMBOL = eINSTANCE.getPrefs_CurrencySymbol();

		/**
		 * The meta object literal for the '<em><b>Show Credit Limit</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PREFS__SHOW_CREDIT_LIMIT = eINSTANCE.getPrefs_ShowCreditLimit();

		/**
		 * The meta object literal for the '<em><b>Show Interest Rate</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PREFS__SHOW_INTEREST_RATE = eINSTANCE.getPrefs_ShowInterestRate();

		/**
		 * The meta object literal for the '<em><b>Show Advanced</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PREFS__SHOW_ADVANCED = eINSTANCE.getPrefs_ShowAdvanced();

		/**
		 * The meta object literal for the '<em><b>Number Of Backups</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PREFS__NUMBER_OF_BACKUPS = eINSTANCE.getPrefs_NumberOfBackups();

		/**
		 * The meta object literal for the '<em><b>Look And Feel Class</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PREFS__LOOK_AND_FEEL_CLASS = eINSTANCE.getPrefs_LookAndFeelClass();

		/**
		 * The meta object literal for the '<em><b>Prompt For File At Startup</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PREFS__PROMPT_FOR_FILE_AT_STARTUP = eINSTANCE.getPrefs_PromptForFileAtStartup();

		/**
		 * The meta object literal for the '<em><b>Lists</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PREFS__LISTS = eINSTANCE.getPrefs_Lists();

		/**
		 * The meta object literal for the '<em><b>Windows</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PREFS__WINDOWS = eINSTANCE.getPrefs_Windows();

		/**
		 * The meta object literal for the '<em><b>Intervals</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PREFS__INTERVALS = eINSTANCE.getPrefs_Intervals();

		/**
		 * The meta object literal for the '<em><b>Last Version Run</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PREFS__LAST_VERSION_RUN = eINSTANCE.getPrefs_LastVersionRun();

		/**
		 * The meta object literal for the '{@link org.homeunix.drummer.prefs.impl.UserPrefsImpl <em>User Prefs</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.homeunix.drummer.prefs.impl.UserPrefsImpl
		 * @see org.homeunix.drummer.prefs.impl.PrefsPackageImpl#getUserPrefs()
		 * @generated
		 */
		EClass USER_PREFS = eINSTANCE.getUserPrefs();

		/**
		 * The meta object literal for the '<em><b>Prefs</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference USER_PREFS__PREFS = eINSTANCE.getUserPrefs_Prefs();

		/**
		 * The meta object literal for the '{@link org.homeunix.drummer.prefs.impl.VersionImpl <em>Version</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.homeunix.drummer.prefs.impl.VersionImpl
		 * @see org.homeunix.drummer.prefs.impl.PrefsPackageImpl#getVersion()
		 * @generated
		 */
		EClass VERSION = eINSTANCE.getVersion();

		/**
		 * The meta object literal for the '<em><b>Version</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute VERSION__VERSION = eINSTANCE.getVersion_Version();

		/**
		 * The meta object literal for the '{@link org.homeunix.drummer.prefs.impl.WindowAttributesImpl <em>Window Attributes</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.homeunix.drummer.prefs.impl.WindowAttributesImpl
		 * @see org.homeunix.drummer.prefs.impl.PrefsPackageImpl#getWindowAttributes()
		 * @generated
		 */
		EClass WINDOW_ATTRIBUTES = eINSTANCE.getWindowAttributes();

		/**
		 * The meta object literal for the '<em><b>X</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute WINDOW_ATTRIBUTES__X = eINSTANCE.getWindowAttributes_X();

		/**
		 * The meta object literal for the '<em><b>Y</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute WINDOW_ATTRIBUTES__Y = eINSTANCE.getWindowAttributes_Y();

		/**
		 * The meta object literal for the '<em><b>Width</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute WINDOW_ATTRIBUTES__WIDTH = eINSTANCE.getWindowAttributes_Width();

		/**
		 * The meta object literal for the '<em><b>Height</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute WINDOW_ATTRIBUTES__HEIGHT = eINSTANCE.getWindowAttributes_Height();

		/**
		 * The meta object literal for the '{@link org.homeunix.drummer.prefs.impl.WindowsImpl <em>Windows</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.homeunix.drummer.prefs.impl.WindowsImpl
		 * @see org.homeunix.drummer.prefs.impl.PrefsPackageImpl#getWindows()
		 * @generated
		 */
		EClass WINDOWS = eINSTANCE.getWindows();

		/**
		 * The meta object literal for the '<em><b>Transactions Window</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference WINDOWS__TRANSACTIONS_WINDOW = eINSTANCE.getWindows_TransactionsWindow();

		/**
		 * The meta object literal for the '<em><b>Reports Window</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference WINDOWS__REPORTS_WINDOW = eINSTANCE.getWindows_ReportsWindow();

		/**
		 * The meta object literal for the '<em><b>Graphs Window</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference WINDOWS__GRAPHS_WINDOW = eINSTANCE.getWindows_GraphsWindow();

		/**
		 * The meta object literal for the '<em><b>Main Window</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference WINDOWS__MAIN_WINDOW = eINSTANCE.getWindows_MainWindow();

	}

} //PrefsPackage
