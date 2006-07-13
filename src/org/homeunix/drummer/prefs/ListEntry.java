/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.homeunix.drummer.prefs;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>List Entry</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.homeunix.drummer.prefs.ListEntry#getEntry <em>Entry</em>}</li>
 *   <li>{@link org.homeunix.drummer.prefs.ListEntry#getAttributes <em>Attributes</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.homeunix.drummer.prefs.PrefsPackage#getListEntry()
 * @model
 * @generated
 */
public interface ListEntry extends EObject {
	/**
	 * Returns the value of the '<em><b>Entry</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Entry</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Entry</em>' attribute.
	 * @see #setEntry(String)
	 * @see org.homeunix.drummer.prefs.PrefsPackage#getListEntry_Entry()
	 * @model required="true"
	 * @generated
	 */
	String getEntry();

	/**
	 * Sets the value of the '{@link org.homeunix.drummer.prefs.ListEntry#getEntry <em>Entry</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Entry</em>' attribute.
	 * @see #getEntry()
	 * @generated
	 */
	void setEntry(String value);

	/**
	 * Returns the value of the '<em><b>Attributes</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Attributes</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Attributes</em>' containment reference.
	 * @see #setAttributes(ListAttributes)
	 * @see org.homeunix.drummer.prefs.PrefsPackage#getListEntry_Attributes()
	 * @model containment="true" required="true"
	 * @generated
	 */
	ListAttributes getAttributes();

	/**
	 * Sets the value of the '{@link org.homeunix.drummer.prefs.ListEntry#getAttributes <em>Attributes</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Attributes</em>' containment reference.
	 * @see #getAttributes()
	 * @generated
	 */
	void setAttributes(ListAttributes value);

} // ListEntry
