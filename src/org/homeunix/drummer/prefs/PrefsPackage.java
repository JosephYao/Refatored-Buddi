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
	 * The meta object id for the '{@link org.homeunix.drummer.prefs.impl.DictEntryImpl <em>Dict Entry</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.homeunix.drummer.prefs.impl.DictEntryImpl
	 * @see org.homeunix.drummer.prefs.impl.PrefsPackageImpl#getDictEntry()
	 * @generated
	 */
	int DICT_ENTRY = 0;

	/**
	 * The feature id for the '<em><b>Entry</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DICT_ENTRY__ENTRY = 0;

	/**
	 * The number of structural features of the the '<em>Dict Entry</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DICT_ENTRY_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link org.homeunix.drummer.prefs.impl.PrefsImpl <em>Prefs</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.homeunix.drummer.prefs.impl.PrefsImpl
	 * @see org.homeunix.drummer.prefs.impl.PrefsPackageImpl#getPrefs()
	 * @generated
	 */
	int PREFS = 1;

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
	 * The feature id for the '<em><b>Memo Dict</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PREFS__MEMO_DICT = 5;

	/**
	 * The feature id for the '<em><b>Transactions Window</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PREFS__TRANSACTIONS_WINDOW = 6;

	/**
	 * The feature id for the '<em><b>Graphs Window</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PREFS__GRAPHS_WINDOW = 7;

	/**
	 * The feature id for the '<em><b>Reports Window</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PREFS__REPORTS_WINDOW = 8;

	/**
	 * The feature id for the '<em><b>Desc Dict</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PREFS__DESC_DICT = 9;

	/**
	 * The feature id for the '<em><b>Main Window</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PREFS__MAIN_WINDOW = 10;

	/**
	 * The number of structural features of the the '<em>Prefs</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PREFS_FEATURE_COUNT = 11;


	/**
	 * The meta object id for the '{@link org.homeunix.drummer.prefs.impl.WindowAttributesImpl <em>Window Attributes</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.homeunix.drummer.prefs.impl.WindowAttributesImpl
	 * @see org.homeunix.drummer.prefs.impl.PrefsPackageImpl#getWindowAttributes()
	 * @generated
	 */
	int WINDOW_ATTRIBUTES = 2;

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
	 * Returns the meta object for the containment reference list '{@link org.homeunix.drummer.prefs.Prefs#getMemoDict <em>Memo Dict</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Memo Dict</em>'.
	 * @see org.homeunix.drummer.prefs.Prefs#getMemoDict()
	 * @see #getPrefs()
	 * @generated
	 */
	EReference getPrefs_MemoDict();

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
