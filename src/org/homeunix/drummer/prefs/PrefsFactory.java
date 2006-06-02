/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.homeunix.drummer.prefs;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see org.homeunix.drummer.prefs.PrefsPackage
 * @generated
 */
public interface PrefsFactory extends EFactory{
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	PrefsFactory eINSTANCE = new org.homeunix.drummer.prefs.impl.PrefsFactoryImpl();

	/**
	 * Returns a new object of class '<em>Dict Entry</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Dict Entry</em>'.
	 * @generated
	 */
	DictEntry createDictEntry();

	/**
	 * Returns a new object of class '<em>Prefs</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Prefs</em>'.
	 * @generated
	 */
	Prefs createPrefs();

	/**
	 * Returns a new object of class '<em>User Prefs</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>User Prefs</em>'.
	 * @generated
	 */
	UserPrefs createUserPrefs();

	/**
	 * Returns a new object of class '<em>Window Attributes</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Window Attributes</em>'.
	 * @generated
	 */
	WindowAttributes createWindowAttributes();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	PrefsPackage getPrefsPackage();

} //PrefsFactory
