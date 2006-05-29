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
 * A representation of the model object '<em><b>Types</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.homeunix.drummer.model.Types#getAllTypes <em>All Types</em>}</li>
 *   <li>{@link org.homeunix.drummer.model.Types#getTypes <em>Types</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.homeunix.drummer.model.ModelPackage#getTypes()
 * @model
 * @generated
 */
public interface Types extends EObject{
	/**
	 * Returns the value of the '<em><b>All Types</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link org.homeunix.drummer.model.DataModel#getAllTypes <em>All Types</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>All Types</em>' container reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>All Types</em>' container reference.
	 * @see #setAllTypes(DataModel)
	 * @see org.homeunix.drummer.model.ModelPackage#getTypes_AllTypes()
	 * @see org.homeunix.drummer.model.DataModel#getAllTypes
	 * @model opposite="allTypes" required="true"
	 * @generated
	 */
	DataModel getAllTypes();

	/**
	 * Sets the value of the '{@link org.homeunix.drummer.model.Types#getAllTypes <em>All Types</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>All Types</em>' container reference.
	 * @see #getAllTypes()
	 * @generated
	 */
	void setAllTypes(DataModel value);

	/**
	 * Returns the value of the '<em><b>Types</b></em>' containment reference list.
	 * The list contents are of type {@link org.homeunix.drummer.model.Type}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Types</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Types</em>' containment reference list.
	 * @see org.homeunix.drummer.model.ModelPackage#getTypes_Types()
	 * @model type="org.homeunix.drummer.model.Type" containment="true"
	 * @generated
	 */
	EList getTypes();

} // Types
