/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.homeunix.drummer.prefs;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Prefs</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.homeunix.drummer.prefs.Prefs#getDataFile <em>Data File</em>}</li>
 *   <li>{@link org.homeunix.drummer.prefs.Prefs#getLanguage <em>Language</em>}</li>
 *   <li>{@link org.homeunix.drummer.prefs.Prefs#isShowDeletedAccounts <em>Show Deleted Accounts</em>}</li>
 *   <li>{@link org.homeunix.drummer.prefs.Prefs#isShowDeletedCategories <em>Show Deleted Categories</em>}</li>
 *   <li>{@link org.homeunix.drummer.prefs.Prefs#getDateFormat <em>Date Format</em>}</li>
 *   <li>{@link org.homeunix.drummer.prefs.Prefs#getMemoDict <em>Memo Dict</em>}</li>
 *   <li>{@link org.homeunix.drummer.prefs.Prefs#getTransactionsWindow <em>Transactions Window</em>}</li>
 *   <li>{@link org.homeunix.drummer.prefs.Prefs#getGraphsWindow <em>Graphs Window</em>}</li>
 *   <li>{@link org.homeunix.drummer.prefs.Prefs#getReportsWindow <em>Reports Window</em>}</li>
 *   <li>{@link org.homeunix.drummer.prefs.Prefs#getDescDict <em>Desc Dict</em>}</li>
 *   <li>{@link org.homeunix.drummer.prefs.Prefs#getMainWindow <em>Main Window</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.homeunix.drummer.prefs.PrefsPackage#getPrefs()
 * @model
 * @generated
 */
public interface Prefs extends EObject{
	/**
	 * Returns the value of the '<em><b>Data File</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Data File</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Data File</em>' attribute.
	 * @see #setDataFile(String)
	 * @see org.homeunix.drummer.prefs.PrefsPackage#getPrefs_DataFile()
	 * @model required="true"
	 * @generated
	 */
	String getDataFile();

	/**
	 * Sets the value of the '{@link org.homeunix.drummer.prefs.Prefs#getDataFile <em>Data File</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Data File</em>' attribute.
	 * @see #getDataFile()
	 * @generated
	 */
	void setDataFile(String value);

	/**
	 * Returns the value of the '<em><b>Language</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Language</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Language</em>' attribute.
	 * @see #setLanguage(String)
	 * @see org.homeunix.drummer.prefs.PrefsPackage#getPrefs_Language()
	 * @model required="true"
	 * @generated
	 */
	String getLanguage();

	/**
	 * Sets the value of the '{@link org.homeunix.drummer.prefs.Prefs#getLanguage <em>Language</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Language</em>' attribute.
	 * @see #getLanguage()
	 * @generated
	 */
	void setLanguage(String value);

	/**
	 * Returns the value of the '<em><b>Show Deleted Accounts</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Show Deleted Accounts</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Show Deleted Accounts</em>' attribute.
	 * @see #setShowDeletedAccounts(boolean)
	 * @see org.homeunix.drummer.prefs.PrefsPackage#getPrefs_ShowDeletedAccounts()
	 * @model required="true"
	 * @generated
	 */
	boolean isShowDeletedAccounts();

	/**
	 * Sets the value of the '{@link org.homeunix.drummer.prefs.Prefs#isShowDeletedAccounts <em>Show Deleted Accounts</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Show Deleted Accounts</em>' attribute.
	 * @see #isShowDeletedAccounts()
	 * @generated
	 */
	void setShowDeletedAccounts(boolean value);

	/**
	 * Returns the value of the '<em><b>Show Deleted Categories</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Show Deleted Categories</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Show Deleted Categories</em>' attribute.
	 * @see #setShowDeletedCategories(boolean)
	 * @see org.homeunix.drummer.prefs.PrefsPackage#getPrefs_ShowDeletedCategories()
	 * @model required="true"
	 * @generated
	 */
	boolean isShowDeletedCategories();

	/**
	 * Sets the value of the '{@link org.homeunix.drummer.prefs.Prefs#isShowDeletedCategories <em>Show Deleted Categories</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Show Deleted Categories</em>' attribute.
	 * @see #isShowDeletedCategories()
	 * @generated
	 */
	void setShowDeletedCategories(boolean value);

	/**
	 * Returns the value of the '<em><b>Date Format</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Date Format</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Date Format</em>' attribute.
	 * @see #setDateFormat(String)
	 * @see org.homeunix.drummer.prefs.PrefsPackage#getPrefs_DateFormat()
	 * @model required="true"
	 * @generated
	 */
	String getDateFormat();

	/**
	 * Sets the value of the '{@link org.homeunix.drummer.prefs.Prefs#getDateFormat <em>Date Format</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Date Format</em>' attribute.
	 * @see #getDateFormat()
	 * @generated
	 */
	void setDateFormat(String value);

	/**
	 * Returns the value of the '<em><b>Transactions Window</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Transactions Window</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Transactions Window</em>' containment reference.
	 * @see #setTransactionsWindow(WindowAttributes)
	 * @see org.homeunix.drummer.prefs.PrefsPackage#getPrefs_TransactionsWindow()
	 * @model containment="true" required="true"
	 * @generated
	 */
	WindowAttributes getTransactionsWindow();

	/**
	 * Sets the value of the '{@link org.homeunix.drummer.prefs.Prefs#getTransactionsWindow <em>Transactions Window</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Transactions Window</em>' containment reference.
	 * @see #getTransactionsWindow()
	 * @generated
	 */
	void setTransactionsWindow(WindowAttributes value);

	/**
	 * Returns the value of the '<em><b>Graphs Window</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Graphs Window</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Graphs Window</em>' containment reference.
	 * @see #setGraphsWindow(WindowAttributes)
	 * @see org.homeunix.drummer.prefs.PrefsPackage#getPrefs_GraphsWindow()
	 * @model containment="true" required="true"
	 * @generated
	 */
	WindowAttributes getGraphsWindow();

	/**
	 * Sets the value of the '{@link org.homeunix.drummer.prefs.Prefs#getGraphsWindow <em>Graphs Window</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Graphs Window</em>' containment reference.
	 * @see #getGraphsWindow()
	 * @generated
	 */
	void setGraphsWindow(WindowAttributes value);

	/**
	 * Returns the value of the '<em><b>Main Window</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Main Window</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Main Window</em>' containment reference.
	 * @see #setMainWindow(WindowAttributes)
	 * @see org.homeunix.drummer.prefs.PrefsPackage#getPrefs_MainWindow()
	 * @model containment="true" required="true"
	 * @generated
	 */
	WindowAttributes getMainWindow();

	/**
	 * Sets the value of the '{@link org.homeunix.drummer.prefs.Prefs#getMainWindow <em>Main Window</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Main Window</em>' containment reference.
	 * @see #getMainWindow()
	 * @generated
	 */
	void setMainWindow(WindowAttributes value);

	/**
	 * Returns the value of the '<em><b>Memo Dict</b></em>' containment reference list.
	 * The list contents are of type {@link org.homeunix.drummer.prefs.DictEntry}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Memo Dict</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Memo Dict</em>' containment reference list.
	 * @see org.homeunix.drummer.prefs.PrefsPackage#getPrefs_MemoDict()
	 * @model type="org.homeunix.drummer.prefs.DictEntry" containment="true"
	 * @generated
	 */
	EList getMemoDict();

	/**
	 * Returns the value of the '<em><b>Reports Window</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Reports Window</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Reports Window</em>' containment reference.
	 * @see #setReportsWindow(WindowAttributes)
	 * @see org.homeunix.drummer.prefs.PrefsPackage#getPrefs_ReportsWindow()
	 * @model containment="true" required="true"
	 * @generated
	 */
	WindowAttributes getReportsWindow();

	/**
	 * Sets the value of the '{@link org.homeunix.drummer.prefs.Prefs#getReportsWindow <em>Reports Window</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Reports Window</em>' containment reference.
	 * @see #getReportsWindow()
	 * @generated
	 */
	void setReportsWindow(WindowAttributes value);

	/**
	 * Returns the value of the '<em><b>Desc Dict</b></em>' containment reference list.
	 * The list contents are of type {@link org.homeunix.drummer.prefs.DictEntry}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Desc Dict</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Desc Dict</em>' containment reference list.
	 * @see org.homeunix.drummer.prefs.PrefsPackage#getPrefs_DescDict()
	 * @model type="org.homeunix.drummer.prefs.DictEntry" containment="true"
	 * @generated
	 */
	EList getDescDict();

} // Prefs
