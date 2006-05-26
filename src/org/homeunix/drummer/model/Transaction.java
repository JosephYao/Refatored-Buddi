/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.homeunix.drummer.model;

import java.util.Date;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Transaction</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.homeunix.drummer.model.Transaction#getAmount <em>Amount</em>}</li>
 *   <li>{@link org.homeunix.drummer.model.Transaction#getDescription <em>Description</em>}</li>
 *   <li>{@link org.homeunix.drummer.model.Transaction#getDate <em>Date</em>}</li>
 *   <li>{@link org.homeunix.drummer.model.Transaction#isDeleted <em>Deleted</em>}</li>
 *   <li>{@link org.homeunix.drummer.model.Transaction#getFrom <em>From</em>}</li>
 *   <li>{@link org.homeunix.drummer.model.Transaction#getTo <em>To</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.homeunix.drummer.model.ModelPackage#getTransaction()
 * @model
 */
public interface Transaction extends EObject, Comparable<Transaction> {
	/**
	 * Returns the value of the '<em><b>Amount</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Amount</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Amount</em>' attribute.
	 * @see #setAmount(long)
	 * @see org.homeunix.drummer.model.ModelPackage#getTransaction_Amount()
	 * @model required="true"
	 * @generated
	 */
	long getAmount();

	/**
	 * Sets the value of the '{@link org.homeunix.drummer.model.Transaction#getAmount <em>Amount</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Amount</em>' attribute.
	 * @see #getAmount()
	 * @generated
	 */
	void setAmount(long value);

	/**
	 * Returns the value of the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Description</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Description</em>' attribute.
	 * @see #setDescription(String)
	 * @see org.homeunix.drummer.model.ModelPackage#getTransaction_Description()
	 * @model required="true"
	 * @generated
	 */
	String getDescription();

	/**
	 * Sets the value of the '{@link org.homeunix.drummer.model.Transaction#getDescription <em>Description</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Description</em>' attribute.
	 * @see #getDescription()
	 * @generated
	 */
	void setDescription(String value);

	/**
	 * Returns the value of the '<em><b>Date</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Date</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Date</em>' attribute.
	 * @see #setDate(Date)
	 * @see org.homeunix.drummer.model.ModelPackage#getTransaction_Date()
	 * @model dataType="org.homeunix.drummer.model.Date" required="true"
	 * @generated
	 */
	Date getDate();

	/**
	 * Sets the value of the '{@link org.homeunix.drummer.model.Transaction#getDate <em>Date</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Date</em>' attribute.
	 * @see #getDate()
	 * @generated
	 */
	void setDate(Date value);

	/**
	 * Returns the value of the '<em><b>Number</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Number</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Number</em>' attribute.
	 * @see #setNumber(String)
	 * @see org.homeunix.drummer.model.ModelPackage#getTransaction_Number()
	 * @model required="true"
	 * @generated
	 */
	String getNumber();

	/**
	 * Sets the value of the '{@link org.homeunix.drummer.model.Transaction#getNumber <em>Number</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Number</em>' attribute.
	 * @see #getNumber()
	 * @generated
	 */
	void setNumber(String value);

	/**
	 * Returns the value of the '<em><b>Memo</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Memo</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Memo</em>' attribute.
	 * @see #setMemo(String)
	 * @see org.homeunix.drummer.model.ModelPackage#getTransaction_Memo()
	 * @model required="true"
	 * @generated
	 */
	String getMemo();

	/**
	 * Sets the value of the '{@link org.homeunix.drummer.model.Transaction#getMemo <em>Memo</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Memo</em>' attribute.
	 * @see #getMemo()
	 * @generated
	 */
	void setMemo(String value);

	/**
	 * Returns the value of the '<em><b>Balance From</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Balance From</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Balance From</em>' attribute.
	 * @see #setBalanceFrom(long)
	 * @see org.homeunix.drummer.model.ModelPackage#getTransaction_BalanceFrom()
	 * @model required="true"
	 * @generated
	 */
	long getBalanceFrom();

	/**
	 * Sets the value of the '{@link org.homeunix.drummer.model.Transaction#getBalanceFrom <em>Balance From</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Balance From</em>' attribute.
	 * @see #getBalanceFrom()
	 * @generated
	 */
	void setBalanceFrom(long value);

	/**
	 * Returns the value of the '<em><b>Balance To</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Balance To</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Balance To</em>' attribute.
	 * @see #setBalanceTo(long)
	 * @see org.homeunix.drummer.model.ModelPackage#getTransaction_BalanceTo()
	 * @model required="true"
	 * @generated
	 */
	long getBalanceTo();

	/**
	 * Sets the value of the '{@link org.homeunix.drummer.model.Transaction#getBalanceTo <em>Balance To</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Balance To</em>' attribute.
	 * @see #getBalanceTo()
	 * @generated
	 */
	void setBalanceTo(long value);

	/**
	 * Returns the value of the '<em><b>From</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>From</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>From</em>' reference.
	 * @see #setFrom(Source)
	 * @see org.homeunix.drummer.model.ModelPackage#getTransaction_From()
	 * @model required="true"
	 * @generated
	 */
	Source getFrom();

	/**
	 * Sets the value of the '{@link org.homeunix.drummer.model.Transaction#getFrom <em>From</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>From</em>' reference.
	 * @see #getFrom()
	 * @generated
	 */
	void setFrom(Source value);

	/**
	 * Returns the value of the '<em><b>To</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>To</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>To</em>' reference.
	 * @see #setTo(Source)
	 * @see org.homeunix.drummer.model.ModelPackage#getTransaction_To()
	 * @model required="true"
	 * @generated
	 */
	Source getTo();

	/**
	 * Sets the value of the '{@link org.homeunix.drummer.model.Transaction#getTo <em>To</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>To</em>' reference.
	 * @see #getTo()
	 * @generated
	 */
	void setTo(Source value);

	public int compareTo(Transaction arg0);

	public void calculateBalance();

} // Transaction
