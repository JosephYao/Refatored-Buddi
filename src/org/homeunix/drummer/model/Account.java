/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.homeunix.drummer.model;



import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Account</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.homeunix.drummer.model.Account#getBalance <em>Balance</em>}</li>
 *   <li>{@link org.homeunix.drummer.model.Account#getStartingBalance <em>Starting Balance</em>}</li>
 *   <li>{@link org.homeunix.drummer.model.Account#getAccountType <em>Account Type</em>}</li>
 *   <li>{@link org.homeunix.drummer.model.Account#getSub <em>Sub</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.homeunix.drummer.model.ModelPackage#getAccount()
 * @model
 * @generated
 */
public interface Account extends Source{
	/**
	 * Returns the value of the '<em><b>Balance</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Balance</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Balance</em>' attribute.
	 * @see #setBalance(long)
	 * @see org.homeunix.drummer.model.ModelPackage#getAccount_Balance()
	 * @model required="true"
	 * @generated
	 */
	long getBalance();

	/**
	 * Sets the value of the '{@link org.homeunix.drummer.model.Account#getBalance <em>Balance</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Balance</em>' attribute.
	 * @see #getBalance()
	 * @generated
	 */
	void setBalance(long value);

	/**
	 * Returns the value of the '<em><b>Starting Balance</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Starting Balance</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Starting Balance</em>' attribute.
	 * @see #setStartingBalance(long)
	 * @see org.homeunix.drummer.model.ModelPackage#getAccount_StartingBalance()
	 * @model required="true"
	 * @generated
	 */
	long getStartingBalance();

	/**
	 * Sets the value of the '{@link org.homeunix.drummer.model.Account#getStartingBalance <em>Starting Balance</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Starting Balance</em>' attribute.
	 * @see #getStartingBalance()
	 * @generated
	 */
	void setStartingBalance(long value);

	/**
	 * Returns the value of the '<em><b>Account Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Account Type</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Account Type</em>' reference.
	 * @see #setAccountType(Type)
	 * @see org.homeunix.drummer.model.ModelPackage#getAccount_AccountType()
	 * @model required="true"
	 * @generated
	 */
	Type getAccountType();

	/**
	 * Sets the value of the '{@link org.homeunix.drummer.model.Account#getAccountType <em>Account Type</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Account Type</em>' reference.
	 * @see #getAccountType()
	 * @generated
	 */
	void setAccountType(Type value);

	/**
	 * Returns the value of the '<em><b>Sub</b></em>' reference list.
	 * The list contents are of type {@link org.homeunix.drummer.model.SubAccount}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Sub</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Sub</em>' reference list.
	 * @see org.homeunix.drummer.model.ModelPackage#getAccount_Sub()
	 * @model type="org.homeunix.drummer.model.SubAccount"
	 * @generated
	 */
	EList getSub();

	public void calculateBalance();
	
	public boolean isCredit();

} // Account
