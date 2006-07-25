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
 * A representation of the model object '<em><b>Intervals</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.homeunix.drummer.prefs.Intervals#getAllIntervals <em>All Intervals</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.homeunix.drummer.prefs.PrefsPackage#getIntervals()
 * @model
 * @generated
 */
public interface Intervals extends EObject {
	/**
	 * Returns the value of the '<em><b>All Intervals</b></em>' containment reference list.
	 * The list contents are of type {@link org.homeunix.drummer.prefs.Interval}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>All Intervals</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>All Intervals</em>' containment reference list.
	 * @see org.homeunix.drummer.prefs.PrefsPackage#getIntervals_AllIntervals()
	 * @model type="org.homeunix.drummer.prefs.Interval" containment="true"
	 * @generated
	 */
	EList getAllIntervals();

} // Intervals
