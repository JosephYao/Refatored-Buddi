/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.homeunix.drummer.model;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Data Model</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.homeunix.drummer.model.DataModel#getAllTypes <em>All Types</em>}</li>
 *   <li>{@link org.homeunix.drummer.model.DataModel#getAllTransactions <em>All Transactions</em>}</li>
 *   <li>{@link org.homeunix.drummer.model.DataModel#getAllAccounts <em>All Accounts</em>}</li>
 *   <li>{@link org.homeunix.drummer.model.DataModel#getAllCategories <em>All Categories</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.homeunix.drummer.model.ModelPackage#getDataModel()
 * @model
 * @generated
 */
public interface DataModel extends EObject {
	/**
	 * Returns the value of the '<em><b>All Categories</b></em>' containment reference.
	 * It is bidirectional and its opposite is '{@link org.homeunix.drummer.model.Categories#getAllCategories <em>All Categories</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>All Categories</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>All Categories</em>' containment reference.
	 * @see #setAllCategories(Categories)
	 * @see org.homeunix.drummer.model.ModelPackage#getDataModel_AllCategories()
	 * @see org.homeunix.drummer.model.Categories#getAllCategories
	 * @model opposite="allCategories" containment="true" required="true"
	 * @generated
	 */
	Categories getAllCategories();

	/**
	 * Sets the value of the '{@link org.homeunix.drummer.model.DataModel#getAllCategories <em>All Categories</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>All Categories</em>' containment reference.
	 * @see #getAllCategories()
	 * @generated
	 */
	void setAllCategories(Categories value);

	/**
	 * Returns the value of the '<em><b>All Types</b></em>' containment reference.
	 * It is bidirectional and its opposite is '{@link org.homeunix.drummer.model.Types#getAllTypes <em>All Types</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>All Types</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>All Types</em>' containment reference.
	 * @see #setAllTypes(Types)
	 * @see org.homeunix.drummer.model.ModelPackage#getDataModel_AllTypes()
	 * @see org.homeunix.drummer.model.Types#getAllTypes
	 * @model opposite="allTypes" containment="true" required="true"
	 * @generated
	 */
	Types getAllTypes();

	/**
	 * Sets the value of the '{@link org.homeunix.drummer.model.DataModel#getAllTypes <em>All Types</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>All Types</em>' containment reference.
	 * @see #getAllTypes()
	 * @generated
	 */
	void setAllTypes(Types value);

	/**
	 * Returns the value of the '<em><b>All Transactions</b></em>' containment reference.
	 * It is bidirectional and its opposite is '{@link org.homeunix.drummer.model.Transactions#getAllTransactions <em>All Transactions</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>All Transactions</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>All Transactions</em>' containment reference.
	 * @see #setAllTransactions(Transactions)
	 * @see org.homeunix.drummer.model.ModelPackage#getDataModel_AllTransactions()
	 * @see org.homeunix.drummer.model.Transactions#getAllTransactions
	 * @model opposite="allTransactions" containment="true" required="true"
	 * @generated
	 */
	Transactions getAllTransactions();

	/**
	 * Sets the value of the '{@link org.homeunix.drummer.model.DataModel#getAllTransactions <em>All Transactions</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>All Transactions</em>' containment reference.
	 * @see #getAllTransactions()
	 * @generated
	 */
	void setAllTransactions(Transactions value);

	/**
	 * Returns the value of the '<em><b>All Accounts</b></em>' containment reference.
	 * It is bidirectional and its opposite is '{@link org.homeunix.drummer.model.Accounts#getAllAccounts <em>All Accounts</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>All Accounts</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>All Accounts</em>' containment reference.
	 * @see #setAllAccounts(Accounts)
	 * @see org.homeunix.drummer.model.ModelPackage#getDataModel_AllAccounts()
	 * @see org.homeunix.drummer.model.Accounts#getAllAccounts
	 * @model opposite="allAccounts" containment="true" required="true"
	 * @generated
	 */
	Accounts getAllAccounts();

	/**
	 * Sets the value of the '{@link org.homeunix.drummer.model.DataModel#getAllAccounts <em>All Accounts</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>All Accounts</em>' containment reference.
	 * @see #getAllAccounts()
	 * @generated
	 */
	void setAllAccounts(Accounts value);

} // DataModel
