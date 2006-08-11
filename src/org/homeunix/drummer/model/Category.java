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
 * A representation of the model object '<em><b>Category</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.homeunix.drummer.model.Category#getBudgetedAmount <em>Budgeted Amount</em>}</li>
 *   <li>{@link org.homeunix.drummer.model.Category#isIncome <em>Income</em>}</li>
 *   <li>{@link org.homeunix.drummer.model.Category#getParent <em>Parent</em>}</li>
 *   <li>{@link org.homeunix.drummer.model.Category#getChildren <em>Children</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.homeunix.drummer.model.ModelPackage#getCategory()
 * @model
 * @generated
 */
public interface Category extends Source {
	/**
	 * Returns the value of the '<em><b>Budgeted Amount</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Budgeted Amount</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Budgeted Amount</em>' attribute.
	 * @see #setBudgetedAmount(long)
	 * @see org.homeunix.drummer.model.ModelPackage#getCategory_BudgetedAmount()
	 * @model required="true"
	 * @generated
	 */
	long getBudgetedAmount();

	/**
	 * Sets the value of the '{@link org.homeunix.drummer.model.Category#getBudgetedAmount <em>Budgeted Amount</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Budgeted Amount</em>' attribute.
	 * @see #getBudgetedAmount()
	 * @generated
	 */
	void setBudgetedAmount(long value);

	/**
	 * Returns the value of the '<em><b>Income</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Income</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Income</em>' attribute.
	 * @see #setIncome(boolean)
	 * @see org.homeunix.drummer.model.ModelPackage#getCategory_Income()
	 * @model required="true"
	 * @generated
	 */
	boolean isIncome();

	/**
	 * Sets the value of the '{@link org.homeunix.drummer.model.Category#isIncome <em>Income</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Income</em>' attribute.
	 * @see #isIncome()
	 * @generated
	 */
	void setIncome(boolean value);

	/**
	 * Returns the value of the '<em><b>Parent</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Parent</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Parent</em>' reference.
	 * @see #setParent(Category)
	 * @see org.homeunix.drummer.model.ModelPackage#getCategory_Parent()
	 * @model required="true"
	 * @generated
	 */
	Category getParent();

	/**
	 * Sets the value of the '{@link org.homeunix.drummer.model.Category#getParent <em>Parent</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Parent</em>' reference.
	 * @see #getParent()
	 * @generated
	 */
	void setParent(Category value);

	/**
	 * Returns the value of the '<em><b>Children</b></em>' reference list.
	 * The list contents are of type {@link org.homeunix.drummer.model.Category}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Children</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Children</em>' reference list.
	 * @see org.homeunix.drummer.model.ModelPackage#getCategory_Children()
	 * @model type="org.homeunix.drummer.model.Category"
	 * @generated
	 */
	EList getChildren();

} // Category
