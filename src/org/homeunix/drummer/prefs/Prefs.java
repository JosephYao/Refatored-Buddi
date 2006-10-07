/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.homeunix.drummer.prefs;

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
 *   <li>{@link org.homeunix.drummer.prefs.Prefs#getBudgetPeriod <em>Budget Period</em>}</li>
 *   <li>{@link org.homeunix.drummer.prefs.Prefs#isShowAccountTypes <em>Show Account Types</em>}</li>
 *   <li>{@link org.homeunix.drummer.prefs.Prefs#isEnableUpdateNotifications <em>Enable Update Notifications</em>}</li>
 *   <li>{@link org.homeunix.drummer.prefs.Prefs#getSelectedInterval <em>Selected Interval</em>}</li>
 *   <li>{@link org.homeunix.drummer.prefs.Prefs#isShowAutoComplete <em>Show Auto Complete</em>}</li>
 *   <li>{@link org.homeunix.drummer.prefs.Prefs#getCurrencySymbol <em>Currency Symbol</em>}</li>
 *   <li>{@link org.homeunix.drummer.prefs.Prefs#isShowCreditLimit <em>Show Credit Limit</em>}</li>
 *   <li>{@link org.homeunix.drummer.prefs.Prefs#isShowInterestRate <em>Show Interest Rate</em>}</li>
 *   <li>{@link org.homeunix.drummer.prefs.Prefs#isShowAdvanced <em>Show Advanced</em>}</li>
 *   <li>{@link org.homeunix.drummer.prefs.Prefs#getNumberOfBackups <em>Number Of Backups</em>}</li>
 *   <li>{@link org.homeunix.drummer.prefs.Prefs#getLookAndFeelClass <em>Look And Feel Class</em>}</li>
 *   <li>{@link org.homeunix.drummer.prefs.Prefs#getLastVersionRun <em>Last Version Run</em>}</li>
 *   <li>{@link org.homeunix.drummer.prefs.Prefs#getLists <em>Lists</em>}</li>
 *   <li>{@link org.homeunix.drummer.prefs.Prefs#getCustomPlugins <em>Custom Plugins</em>}</li>
 *   <li>{@link org.homeunix.drummer.prefs.Prefs#getWindows <em>Windows</em>}</li>
 *   <li>{@link org.homeunix.drummer.prefs.Prefs#getIntervals <em>Intervals</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.homeunix.drummer.prefs.PrefsPackage#getPrefs()
 * @model
 * @generated
 */
public interface Prefs extends EObject {
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
	 * Returns the value of the '<em><b>Budget Period</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Budget Period</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Budget Period</em>' attribute.
	 * @see #setBudgetPeriod(String)
	 * @see org.homeunix.drummer.prefs.PrefsPackage#getPrefs_BudgetPeriod()
	 * @model required="true"
	 * @generated
	 */
	String getBudgetPeriod();

	/**
	 * Sets the value of the '{@link org.homeunix.drummer.prefs.Prefs#getBudgetPeriod <em>Budget Period</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Budget Period</em>' attribute.
	 * @see #getBudgetPeriod()
	 * @generated
	 */
	void setBudgetPeriod(String value);

	/**
	 * Returns the value of the '<em><b>Show Account Types</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Show Account Types</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Show Account Types</em>' attribute.
	 * @see #setShowAccountTypes(boolean)
	 * @see org.homeunix.drummer.prefs.PrefsPackage#getPrefs_ShowAccountTypes()
	 * @model required="true"
	 * @generated
	 */
	boolean isShowAccountTypes();

	/**
	 * Sets the value of the '{@link org.homeunix.drummer.prefs.Prefs#isShowAccountTypes <em>Show Account Types</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Show Account Types</em>' attribute.
	 * @see #isShowAccountTypes()
	 * @generated
	 */
	void setShowAccountTypes(boolean value);

	/**
	 * Returns the value of the '<em><b>Enable Update Notifications</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Enable Update Notifications</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Enable Update Notifications</em>' attribute.
	 * @see #setEnableUpdateNotifications(boolean)
	 * @see org.homeunix.drummer.prefs.PrefsPackage#getPrefs_EnableUpdateNotifications()
	 * @model required="true"
	 * @generated
	 */
	boolean isEnableUpdateNotifications();

	/**
	 * Sets the value of the '{@link org.homeunix.drummer.prefs.Prefs#isEnableUpdateNotifications <em>Enable Update Notifications</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Enable Update Notifications</em>' attribute.
	 * @see #isEnableUpdateNotifications()
	 * @generated
	 */
	void setEnableUpdateNotifications(boolean value);

	/**
	 * Returns the value of the '<em><b>Last Version Run</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Last Version Run</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Last Version Run</em>' containment reference.
	 * @see #setLastVersionRun(Version)
	 * @see org.homeunix.drummer.prefs.PrefsPackage#getPrefs_LastVersionRun()
	 * @model containment="true"
	 * @generated
	 */
	Version getLastVersionRun();

	/**
	 * Sets the value of the '{@link org.homeunix.drummer.prefs.Prefs#getLastVersionRun <em>Last Version Run</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Last Version Run</em>' containment reference.
	 * @see #getLastVersionRun()
	 * @generated
	 */
	void setLastVersionRun(Version value);

	/**
	 * Returns the value of the '<em><b>Intervals</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Intervals</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Intervals</em>' containment reference.
	 * @see #setIntervals(Intervals)
	 * @see org.homeunix.drummer.prefs.PrefsPackage#getPrefs_Intervals()
	 * @model containment="true" required="true"
	 * @generated
	 */
	Intervals getIntervals();

	/**
	 * Sets the value of the '{@link org.homeunix.drummer.prefs.Prefs#getIntervals <em>Intervals</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Intervals</em>' containment reference.
	 * @see #getIntervals()
	 * @generated
	 */
	void setIntervals(Intervals value);

	/**
	 * Returns the value of the '<em><b>Selected Interval</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Selected Interval</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Selected Interval</em>' attribute.
	 * @see #setSelectedInterval(String)
	 * @see org.homeunix.drummer.prefs.PrefsPackage#getPrefs_SelectedInterval()
	 * @model required="true"
	 * @generated
	 */
	String getSelectedInterval();

	/**
	 * Sets the value of the '{@link org.homeunix.drummer.prefs.Prefs#getSelectedInterval <em>Selected Interval</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Selected Interval</em>' attribute.
	 * @see #getSelectedInterval()
	 * @generated
	 */
	void setSelectedInterval(String value);

	/**
	 * Returns the value of the '<em><b>Show Auto Complete</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Show Auto Complete</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Show Auto Complete</em>' attribute.
	 * @see #setShowAutoComplete(boolean)
	 * @see org.homeunix.drummer.prefs.PrefsPackage#getPrefs_ShowAutoComplete()
	 * @model required="true"
	 * @generated
	 */
	boolean isShowAutoComplete();

	/**
	 * Sets the value of the '{@link org.homeunix.drummer.prefs.Prefs#isShowAutoComplete <em>Show Auto Complete</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Show Auto Complete</em>' attribute.
	 * @see #isShowAutoComplete()
	 * @generated
	 */
	void setShowAutoComplete(boolean value);

	/**
	 * Returns the value of the '<em><b>Currency Symbol</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Currency Symbol</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Currency Symbol</em>' attribute.
	 * @see #setCurrencySymbol(String)
	 * @see org.homeunix.drummer.prefs.PrefsPackage#getPrefs_CurrencySymbol()
	 * @model required="true"
	 * @generated
	 */
	String getCurrencySymbol();

	/**
	 * Sets the value of the '{@link org.homeunix.drummer.prefs.Prefs#getCurrencySymbol <em>Currency Symbol</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Currency Symbol</em>' attribute.
	 * @see #getCurrencySymbol()
	 * @generated
	 */
	void setCurrencySymbol(String value);

	/**
	 * Returns the value of the '<em><b>Show Credit Limit</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Show Credit Limit</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Show Credit Limit</em>' attribute.
	 * @see #setShowCreditLimit(boolean)
	 * @see org.homeunix.drummer.prefs.PrefsPackage#getPrefs_ShowCreditLimit()
	 * @model required="true"
	 * @generated
	 */
	boolean isShowCreditLimit();

	/**
	 * Sets the value of the '{@link org.homeunix.drummer.prefs.Prefs#isShowCreditLimit <em>Show Credit Limit</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Show Credit Limit</em>' attribute.
	 * @see #isShowCreditLimit()
	 * @generated
	 */
	void setShowCreditLimit(boolean value);

	/**
	 * Returns the value of the '<em><b>Show Interest Rate</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Show Interest Rate</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Show Interest Rate</em>' attribute.
	 * @see #setShowInterestRate(boolean)
	 * @see org.homeunix.drummer.prefs.PrefsPackage#getPrefs_ShowInterestRate()
	 * @model required="true"
	 * @generated
	 */
	boolean isShowInterestRate();

	/**
	 * Sets the value of the '{@link org.homeunix.drummer.prefs.Prefs#isShowInterestRate <em>Show Interest Rate</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Show Interest Rate</em>' attribute.
	 * @see #isShowInterestRate()
	 * @generated
	 */
	void setShowInterestRate(boolean value);

	/**
	 * Returns the value of the '<em><b>Show Advanced</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Show Advanced</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Show Advanced</em>' attribute.
	 * @see #setShowAdvanced(boolean)
	 * @see org.homeunix.drummer.prefs.PrefsPackage#getPrefs_ShowAdvanced()
	 * @model required="true"
	 * @generated
	 */
	boolean isShowAdvanced();

	/**
	 * Sets the value of the '{@link org.homeunix.drummer.prefs.Prefs#isShowAdvanced <em>Show Advanced</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Show Advanced</em>' attribute.
	 * @see #isShowAdvanced()
	 * @generated
	 */
	void setShowAdvanced(boolean value);

	/**
	 * Returns the value of the '<em><b>Number Of Backups</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Number Of Backups</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Number Of Backups</em>' attribute.
	 * @see #setNumberOfBackups(int)
	 * @see org.homeunix.drummer.prefs.PrefsPackage#getPrefs_NumberOfBackups()
	 * @model
	 * @generated
	 */
	int getNumberOfBackups();

	/**
	 * Sets the value of the '{@link org.homeunix.drummer.prefs.Prefs#getNumberOfBackups <em>Number Of Backups</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Number Of Backups</em>' attribute.
	 * @see #getNumberOfBackups()
	 * @generated
	 */
	void setNumberOfBackups(int value);

	/**
	 * Returns the value of the '<em><b>Look And Feel Class</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Look And Feel Class</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Look And Feel Class</em>' attribute.
	 * @see #setLookAndFeelClass(String)
	 * @see org.homeunix.drummer.prefs.PrefsPackage#getPrefs_LookAndFeelClass()
	 * @model
	 * @generated
	 */
	String getLookAndFeelClass();

	/**
	 * Sets the value of the '{@link org.homeunix.drummer.prefs.Prefs#getLookAndFeelClass <em>Look And Feel Class</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Look And Feel Class</em>' attribute.
	 * @see #getLookAndFeelClass()
	 * @generated
	 */
	void setLookAndFeelClass(String value);

	/**
	 * Returns the value of the '<em><b>Custom Plugins</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Custom Plugins</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Custom Plugins</em>' containment reference.
	 * @see #setCustomPlugins(CustomPlugins)
	 * @see org.homeunix.drummer.prefs.PrefsPackage#getPrefs_CustomPlugins()
	 * @model containment="true"
	 * @generated
	 */
	CustomPlugins getCustomPlugins();

	/**
	 * Sets the value of the '{@link org.homeunix.drummer.prefs.Prefs#getCustomPlugins <em>Custom Plugins</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Custom Plugins</em>' containment reference.
	 * @see #getCustomPlugins()
	 * @generated
	 */
	void setCustomPlugins(CustomPlugins value);

	/**
	 * Returns the value of the '<em><b>Lists</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Lists</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Lists</em>' containment reference.
	 * @see #setLists(Lists)
	 * @see org.homeunix.drummer.prefs.PrefsPackage#getPrefs_Lists()
	 * @model containment="true" required="true"
	 * @generated
	 */
	Lists getLists();

	/**
	 * Sets the value of the '{@link org.homeunix.drummer.prefs.Prefs#getLists <em>Lists</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Lists</em>' containment reference.
	 * @see #getLists()
	 * @generated
	 */
	void setLists(Lists value);

	/**
	 * Returns the value of the '<em><b>Windows</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Windows</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Windows</em>' containment reference.
	 * @see #setWindows(Windows)
	 * @see org.homeunix.drummer.prefs.PrefsPackage#getPrefs_Windows()
	 * @model containment="true" required="true"
	 * @generated
	 */
	Windows getWindows();

	/**
	 * Sets the value of the '{@link org.homeunix.drummer.prefs.Prefs#getWindows <em>Windows</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Windows</em>' containment reference.
	 * @see #getWindows()
	 * @generated
	 */
	void setWindows(Windows value);

} // Prefs
