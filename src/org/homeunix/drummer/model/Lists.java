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
 * A representation of the model object '<em><b>Lists</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.homeunix.drummer.model.Lists#getAllLists <em>All Lists</em>}</li>
 *   <li>{@link org.homeunix.drummer.model.Lists#getAllAutoSave <em>All Auto Save</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.homeunix.drummer.model.ModelPackage#getLists()
 * @model
 * @generated
 */
public interface Lists extends EObject {
	/**
	 * Returns the value of the '<em><b>All Lists</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link org.homeunix.drummer.model.DataModel#getAllLists <em>All Lists</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>All Lists</em>' container reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>All Lists</em>' container reference.
	 * @see #setAllLists(DataModel)
	 * @see org.homeunix.drummer.model.ModelPackage#getLists_AllLists()
	 * @see org.homeunix.drummer.model.DataModel#getAllLists
	 * @model opposite="allLists" required="true"
	 * @generated
	 */
	DataModel getAllLists();

	/**
	 * Sets the value of the '{@link org.homeunix.drummer.model.Lists#getAllLists <em>All Lists</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>All Lists</em>' container reference.
	 * @see #getAllLists()
	 * @generated
	 */
	void setAllLists(DataModel value);

	/**
	 * Returns the value of the '<em><b>All Auto Save</b></em>' containment reference list.
	 * The list contents are of type {@link org.homeunix.drummer.model.AutoSaveInfo}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>All Auto Save</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>All Auto Save</em>' containment reference list.
	 * @see org.homeunix.drummer.model.ModelPackage#getLists_AllAutoSave()
	 * @model type="org.homeunix.drummer.model.AutoSaveInfo" containment="true"
	 * @generated
	 */
	EList getAllAutoSave();

} // Lists