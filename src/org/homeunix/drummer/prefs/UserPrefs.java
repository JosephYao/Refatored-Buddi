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
 * A representation of the model object '<em><b>User Prefs</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.homeunix.drummer.prefs.UserPrefs#getPrefs <em>Prefs</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.homeunix.drummer.prefs.PrefsPackage#getUserPrefs()
 * @model
 * @generated
 */
public interface UserPrefs extends EObject{
	/**
	 * Returns the value of the '<em><b>Prefs</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Prefs</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Prefs</em>' containment reference.
	 * @see #setPrefs(Prefs)
	 * @see org.homeunix.drummer.prefs.PrefsPackage#getUserPrefs_Prefs()
	 * @model containment="true" required="true"
	 * @generated
	 */
	Prefs getPrefs();

	/**
	 * Sets the value of the '{@link org.homeunix.drummer.prefs.UserPrefs#getPrefs <em>Prefs</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Prefs</em>' containment reference.
	 * @see #getPrefs()
	 * @generated
	 */
	void setPrefs(Prefs value);

} // UserPrefs
