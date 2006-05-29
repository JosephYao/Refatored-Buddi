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
 * A representation of the model object '<em><b>Accounts</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.homeunix.drummer.model.Accounts#getAccounts <em>Accounts</em>}</li>
 *   <li>{@link org.homeunix.drummer.model.Accounts#getSubAccounts <em>Sub Accounts</em>}</li>
 *   <li>{@link org.homeunix.drummer.model.Accounts#getAllAccounts <em>All Accounts</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.homeunix.drummer.model.ModelPackage#getAccounts()
 * @model
 * @generated
 */
public interface Accounts extends EObject{
	/**
	 * Returns the value of the '<em><b>Accounts</b></em>' containment reference list.
	 * The list contents are of type {@link org.homeunix.drummer.model.Account}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Accounts</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Accounts</em>' containment reference list.
	 * @see org.homeunix.drummer.model.ModelPackage#getAccounts_Accounts()
	 * @model type="org.homeunix.drummer.model.Account" containment="true"
	 * @generated
	 */
	EList getAccounts();

	/**
	 * Returns the value of the '<em><b>Sub Accounts</b></em>' containment reference list.
	 * The list contents are of type {@link org.homeunix.drummer.model.SubAccount}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Sub Accounts</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Sub Accounts</em>' containment reference list.
	 * @see org.homeunix.drummer.model.ModelPackage#getAccounts_SubAccounts()
	 * @model type="org.homeunix.drummer.model.SubAccount" containment="true"
	 * @generated
	 */
	EList getSubAccounts();

	/**
	 * Returns the value of the '<em><b>All Accounts</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link org.homeunix.drummer.model.DataModel#getAllAccounts <em>All Accounts</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>All Accounts</em>' container reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>All Accounts</em>' container reference.
	 * @see #setAllAccounts(DataModel)
	 * @see org.homeunix.drummer.model.ModelPackage#getAccounts_AllAccounts()
	 * @see org.homeunix.drummer.model.DataModel#getAllAccounts
	 * @model opposite="allAccounts" required="true"
	 * @generated
	 */
	DataModel getAllAccounts();

	/**
	 * Sets the value of the '{@link org.homeunix.drummer.model.Accounts#getAllAccounts <em>All Accounts</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>All Accounts</em>' container reference.
	 * @see #getAllAccounts()
	 * @generated
	 */
	void setAllAccounts(DataModel value);

} // Accounts
