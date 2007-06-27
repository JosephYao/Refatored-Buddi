/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.homeunix.drummer.prefs;

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
 *   <li>{@link org.homeunix.drummer.prefs.Lists#getListEntries <em>List Entries</em>}</li>
 *   <li>{@link org.homeunix.drummer.prefs.Lists#getPlugins <em>Plugins</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.homeunix.drummer.prefs.PrefsPackage#getLists()
 * @model
 * @generated
 */
public interface Lists extends EObject {
	/**
	 * Returns the value of the '<em><b>List Entries</b></em>' containment reference list.
	 * The list contents are of type {@link org.homeunix.drummer.prefs.ListEntry}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>List Entries</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>List Entries</em>' containment reference list.
	 * @see org.homeunix.drummer.prefs.PrefsPackage#getLists_ListEntries()
	 * @model type="org.homeunix.drummer.prefs.ListEntry" containment="true"
	 * @generated
	 */
	EList getListEntries();

	/**
	 * Returns the value of the '<em><b>Plugins</b></em>' containment reference list.
	 * The list contents are of type {@link org.homeunix.drummer.prefs.Plugin}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Plugins</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Plugins</em>' containment reference list.
	 * @see org.homeunix.drummer.prefs.PrefsPackage#getLists_Plugins()
	 * @model type="org.homeunix.drummer.prefs.Plugin" containment="true"
	 * @generated
	 */
	EList getPlugins();

} // Lists