/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.homeunix.drummer.model;



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
 *   <li>{@link org.homeunix.drummer.model.Account#getCreditLimit <em>Credit Limit</em>}</li>
 *   <li>{@link org.homeunix.drummer.model.Account#getInterestRate <em>Interest Rate</em>}</li>
 *   <li>{@link org.homeunix.drummer.model.Account#getAccountType <em>Account Type</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.homeunix.drummer.model.ModelPackage#getAccount()
 * @model
 * @generated
 */
public interface Account extends Source, Comparable<Source> {
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
	 * Returns the value of the '<em><b>Credit Limit</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Credit Limit</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Credit Limit</em>' attribute.
	 * @see #setCreditLimit(long)
	 * @see org.homeunix.drummer.model.ModelPackage#getAccount_CreditLimit()
	 * @model
	 * @generated
	 */
	long getCreditLimit();

	/**
	 * Sets the value of the '{@link org.homeunix.drummer.model.Account#getCreditLimit <em>Credit Limit</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Credit Limit</em>' attribute.
	 * @see #getCreditLimit()
	 * @generated
	 */
	void setCreditLimit(long value);

	/**
	 * Returns the value of the '<em><b>Interest Rate</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Interest Rate</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Interest Rate</em>' attribute.
	 * @see #setInterestRate(long)
	 * @see org.homeunix.drummer.model.ModelPackage#getAccount_InterestRate()
	 * @model
	 * @generated
	 */
	long getInterestRate();

	/**
	 * Sets the value of the '{@link org.homeunix.drummer.model.Account#getInterestRate <em>Interest Rate</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Interest Rate</em>' attribute.
	 * @see #getInterestRate()
	 * @generated
	 */
	void setInterestRate(long value);

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

	public void calculateBalance();
	
	public boolean isCredit();

} // Account
