/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.homeunix.drummer.model;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Transactions</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.homeunix.drummer.model.Transactions#getScheduledTransactions <em>Scheduled Transactions</em>}</li>
 *   <li>{@link org.homeunix.drummer.model.Transactions#getTransactions <em>Transactions</em>}</li>
 *   <li>{@link org.homeunix.drummer.model.Transactions#getAllTransactions <em>All Transactions</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.homeunix.drummer.model.ModelPackage#getTransactions()
 * @model
 * @generated
 */
public interface Transactions extends EObject {
	/**
	 * Returns the value of the '<em><b>Transactions</b></em>' containment reference list.
	 * The list contents are of type {@link org.homeunix.drummer.model.Transaction}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Transactions</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Transactions</em>' containment reference list.
	 * @see org.homeunix.drummer.model.ModelPackage#getTransactions_Transactions()
	 * @model type="org.homeunix.drummer.model.Transaction" containment="true"
	 * @generated
	 */
	EList getTransactions();

	/**
	 * Returns the value of the '<em><b>All Transactions</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link org.homeunix.drummer.model.DataModel#getAllTransactions <em>All Transactions</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>All Transactions</em>' container reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>All Transactions</em>' container reference.
	 * @see #setAllTransactions(DataModel)
	 * @see org.homeunix.drummer.model.ModelPackage#getTransactions_AllTransactions()
	 * @see org.homeunix.drummer.model.DataModel#getAllTransactions
	 * @model opposite="allTransactions" required="true"
	 * @generated
	 */
	DataModel getAllTransactions();

	/**
	 * Sets the value of the '{@link org.homeunix.drummer.model.Transactions#getAllTransactions <em>All Transactions</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>All Transactions</em>' container reference.
	 * @see #getAllTransactions()
	 * @generated
	 */
	void setAllTransactions(DataModel value);

	/**
	 * Returns the value of the '<em><b>Scheduled Transactions</b></em>' containment reference list.
	 * The list contents are of type {@link org.homeunix.drummer.model.Schedule}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Scheduled Transactions</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Scheduled Transactions</em>' containment reference list.
	 * @see org.homeunix.drummer.model.ModelPackage#getTransactions_ScheduledTransactions()
	 * @model type="org.homeunix.drummer.model.Schedule" containment="true"
	 * @generated
	 */
	EList getScheduledTransactions();

} // Transactions
