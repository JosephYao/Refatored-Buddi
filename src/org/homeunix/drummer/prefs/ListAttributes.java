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
 * A representation of the model object '<em><b>List Attributes</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.homeunix.drummer.prefs.ListAttributes#isUnrolled <em>Unrolled</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.homeunix.drummer.prefs.PrefsPackage#getListAttributes()
 * @model
 * @generated
 */
public interface ListAttributes extends EObject {
	/**
	 * Returns the value of the '<em><b>Unrolled</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Unrolled</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Unrolled</em>' attribute.
	 * @see #setUnrolled(boolean)
	 * @see org.homeunix.drummer.prefs.PrefsPackage#getListAttributes_Unrolled()
	 * @model required="true"
	 * @generated
	 */
	boolean isUnrolled();

	/**
	 * Sets the value of the '{@link org.homeunix.drummer.prefs.ListAttributes#isUnrolled <em>Unrolled</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Unrolled</em>' attribute.
	 * @see #isUnrolled()
	 * @generated
	 */
	void setUnrolled(boolean value);

} // ListAttributes
