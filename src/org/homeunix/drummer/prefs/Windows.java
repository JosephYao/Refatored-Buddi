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
 * A representation of the model object '<em><b>Windows</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.homeunix.drummer.prefs.Windows#getReportsWindow <em>Reports Window</em>}</li>
 *   <li>{@link org.homeunix.drummer.prefs.Windows#getGraphsWindow <em>Graphs Window</em>}</li>
 *   <li>{@link org.homeunix.drummer.prefs.Windows#getTransactionsWindow <em>Transactions Window</em>}</li>
 *   <li>{@link org.homeunix.drummer.prefs.Windows#getMainWindow <em>Main Window</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.homeunix.drummer.prefs.PrefsPackage#getWindows()
 * @model
 * @generated
 */
public interface Windows extends EObject {
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
	 * @see org.homeunix.drummer.prefs.PrefsPackage#getWindows_TransactionsWindow()
	 * @model containment="true" required="true"
	 * @generated
	 */
	WindowAttributes getTransactionsWindow();

	/**
	 * Sets the value of the '{@link org.homeunix.drummer.prefs.Windows#getTransactionsWindow <em>Transactions Window</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Transactions Window</em>' containment reference.
	 * @see #getTransactionsWindow()
	 * @generated
	 */
	void setTransactionsWindow(WindowAttributes value);

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
	 * @see org.homeunix.drummer.prefs.PrefsPackage#getWindows_ReportsWindow()
	 * @model containment="true" required="true"
	 * @generated
	 */
	WindowAttributes getReportsWindow();

	/**
	 * Sets the value of the '{@link org.homeunix.drummer.prefs.Windows#getReportsWindow <em>Reports Window</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Reports Window</em>' containment reference.
	 * @see #getReportsWindow()
	 * @generated
	 */
	void setReportsWindow(WindowAttributes value);

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
	 * @see org.homeunix.drummer.prefs.PrefsPackage#getWindows_GraphsWindow()
	 * @model containment="true" required="true"
	 * @generated
	 */
	WindowAttributes getGraphsWindow();

	/**
	 * Sets the value of the '{@link org.homeunix.drummer.prefs.Windows#getGraphsWindow <em>Graphs Window</em>}' containment reference.
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
	 * @see org.homeunix.drummer.prefs.PrefsPackage#getWindows_MainWindow()
	 * @model containment="true" required="true"
	 * @generated
	 */
	WindowAttributes getMainWindow();

	/**
	 * Sets the value of the '{@link org.homeunix.drummer.prefs.Windows#getMainWindow <em>Main Window</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Main Window</em>' containment reference.
	 * @see #getMainWindow()
	 * @generated
	 */
	void setMainWindow(WindowAttributes value);

} // Windows