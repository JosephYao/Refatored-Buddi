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
 * A representation of the model object '<em><b>Categories</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.homeunix.drummer.model.Categories#getCategories <em>Categories</em>}</li>
 *   <li>{@link org.homeunix.drummer.model.Categories#getAllCategories <em>All Categories</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.homeunix.drummer.model.ModelPackage#getCategories()
 * @model
 * @generated
 */
public interface Categories extends EObject {
	/**
	 * Returns the value of the '<em><b>All Categories</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link org.homeunix.drummer.model.DataModel#getAllCategories <em>All Categories</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>All Categories</em>' container reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>All Categories</em>' container reference.
	 * @see #setAllCategories(DataModel)
	 * @see org.homeunix.drummer.model.ModelPackage#getCategories_AllCategories()
	 * @see org.homeunix.drummer.model.DataModel#getAllCategories
	 * @model opposite="allCategories" required="true"
	 * @generated
	 */
	DataModel getAllCategories();

	/**
	 * Sets the value of the '{@link org.homeunix.drummer.model.Categories#getAllCategories <em>All Categories</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>All Categories</em>' container reference.
	 * @see #getAllCategories()
	 * @generated
	 */
	void setAllCategories(DataModel value);

	/**
	 * Returns the value of the '<em><b>Categories</b></em>' containment reference list.
	 * The list contents are of type {@link org.homeunix.drummer.model.Category}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Categories</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Categories</em>' containment reference list.
	 * @see org.homeunix.drummer.model.ModelPackage#getCategories_Categories()
	 * @model type="org.homeunix.drummer.model.Category" containment="true"
	 * @generated
	 */
	EList getCategories();

} // Categories
