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
 * A representation of the model object '<em><b>Custom Plugins</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.homeunix.drummer.prefs.CustomPlugins#getJars <em>Jars</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.homeunix.drummer.prefs.PrefsPackage#getCustomPlugins()
 * @model
 * @generated
 */
public interface CustomPlugins extends EObject {
	/**
	 * Returns the value of the '<em><b>Jars</b></em>' containment reference list.
	 * The list contents are of type {@link org.homeunix.drummer.prefs.PluginJar}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Jars</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Jars</em>' containment reference list.
	 * @see org.homeunix.drummer.prefs.PrefsPackage#getCustomPlugins_Jars()
	 * @model type="org.homeunix.drummer.prefs.PluginJar" containment="true"
	 * @generated
	 */
	EList getJars();

} // CustomPlugins