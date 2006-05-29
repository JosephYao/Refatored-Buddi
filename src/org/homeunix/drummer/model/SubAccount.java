/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.homeunix.drummer.model;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Sub Account</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.homeunix.drummer.model.SubAccount#getParent <em>Parent</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.homeunix.drummer.model.ModelPackage#getSubAccount()
 * @model
 * @generated
 */
public interface SubAccount extends Account{
	/**
	 * Returns the value of the '<em><b>Parent</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Parent</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Parent</em>' reference.
	 * @see #setParent(Account)
	 * @see org.homeunix.drummer.model.ModelPackage#getSubAccount_Parent()
	 * @model required="true"
	 * @generated
	 */
	Account getParent();

	/**
	 * Sets the value of the '{@link org.homeunix.drummer.model.SubAccount#getParent <em>Parent</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Parent</em>' reference.
	 * @see #getParent()
	 * @generated
	 */
	void setParent(Account value);

} // SubAccount
