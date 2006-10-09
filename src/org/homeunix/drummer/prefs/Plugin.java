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
 * A representation of the model object '<em><b>Plugin</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.homeunix.drummer.prefs.Plugin#getJarFile <em>Jar File</em>}</li>
 *   <li>{@link org.homeunix.drummer.prefs.Plugin#getClassName <em>Class Name</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.homeunix.drummer.prefs.PrefsPackage#getPlugin()
 * @model
 * @generated
 */
public interface Plugin extends EObject {
	/**
	 * Returns the value of the '<em><b>Jar File</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Jar File</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Jar File</em>' attribute.
	 * @see #setJarFile(String)
	 * @see org.homeunix.drummer.prefs.PrefsPackage#getPlugin_JarFile()
	 * @model required="true"
	 * @generated
	 */
	String getJarFile();

	/**
	 * Sets the value of the '{@link org.homeunix.drummer.prefs.Plugin#getJarFile <em>Jar File</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Jar File</em>' attribute.
	 * @see #getJarFile()
	 * @generated
	 */
	void setJarFile(String value);

	/**
	 * Returns the value of the '<em><b>Class Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Class Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Class Name</em>' attribute.
	 * @see #setClassName(String)
	 * @see org.homeunix.drummer.prefs.PrefsPackage#getPlugin_ClassName()
	 * @model required="true"
	 * @generated
	 */
	String getClassName();

	/**
	 * Sets the value of the '{@link org.homeunix.drummer.prefs.Plugin#getClassName <em>Class Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Class Name</em>' attribute.
	 * @see #getClassName()
	 * @generated
	 */
	void setClassName(String value);

} // Plugin