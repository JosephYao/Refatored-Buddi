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
public interface PrefsFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	PrefsFactory eINSTANCE = org.homeunix.drummer.prefs.impl.PrefsFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Dict Data</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Dict Data</em>'.
	 * @generated
	 */
	DictData createDictData();

	/**
	 * Returns a new object of class '<em>Dict Entry</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Dict Entry</em>'.
	 * @generated
	 */
	DictEntry createDictEntry();

	/**
	 * Returns a new object of class '<em>Interval</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Interval</em>'.
	 * @generated
	 */
	Interval createInterval();

	/**
	 * Returns a new object of class '<em>Intervals</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Intervals</em>'.
	 * @generated
	 */
	Intervals createIntervals();

	/**
	 * Returns a new object of class '<em>List Attributes</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>List Attributes</em>'.
	 * @generated
	 */
	ListAttributes createListAttributes();

	/**
	 * Returns a new object of class '<em>List Entry</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>List Entry</em>'.
	 * @generated
	 */
	ListEntry createListEntry();

	/**
	 * Returns a new object of class '<em>Lists</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Lists</em>'.
	 * @generated
	 */
	Lists createLists();

	/**
	 * Returns a new object of class '<em>Plugin</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Plugin</em>'.
	 * @generated
	 */
	Plugin createPlugin();

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
	 * Returns a new object of class '<em>Version</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Version</em>'.
	 * @generated
	 */
	Version createVersion();

	/**
	 * Returns a new object of class '<em>Window Attributes</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Window Attributes</em>'.
	 * @generated
	 */
	WindowAttributes createWindowAttributes();

	/**
	 * Returns a new object of class '<em>Windows</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Windows</em>'.
	 * @generated
	 */
	Windows createWindows();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	PrefsPackage getPrefsPackage();

} //PrefsFactory
