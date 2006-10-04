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
 *   <li>{@link org.homeunix.drummer.prefs.CustomPlugins#getPanelPlugins <em>Panel Plugins</em>}</li>
 *   <li>{@link org.homeunix.drummer.prefs.CustomPlugins#getImportPlugins <em>Import Plugins</em>}</li>
 *   <li>{@link org.homeunix.drummer.prefs.CustomPlugins#getExportPlugins <em>Export Plugins</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.homeunix.drummer.prefs.PrefsPackage#getCustomPlugins()
 * @model
 * @generated
 */
public interface CustomPlugins extends EObject {
	/**
	 * Returns the value of the '<em><b>Export Plugins</b></em>' containment reference list.
	 * The list contents are of type {@link org.homeunix.drummer.prefs.PluginEntry}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Export Plugins</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Export Plugins</em>' containment reference list.
	 * @see org.homeunix.drummer.prefs.PrefsPackage#getCustomPlugins_ExportPlugins()
	 * @model type="org.homeunix.drummer.prefs.PluginEntry" containment="true"
	 * @generated
	 */
	EList getExportPlugins();

	/**
	 * Returns the value of the '<em><b>Panel Plugins</b></em>' containment reference list.
	 * The list contents are of type {@link org.homeunix.drummer.prefs.PluginEntry}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Panel Plugins</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Panel Plugins</em>' containment reference list.
	 * @see org.homeunix.drummer.prefs.PrefsPackage#getCustomPlugins_PanelPlugins()
	 * @model type="org.homeunix.drummer.prefs.PluginEntry" containment="true"
	 * @generated
	 */
	EList getPanelPlugins();

	/**
	 * Returns the value of the '<em><b>Import Plugins</b></em>' containment reference list.
	 * The list contents are of type {@link org.homeunix.drummer.prefs.PluginEntry}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Import Plugins</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Import Plugins</em>' containment reference list.
	 * @see org.homeunix.drummer.prefs.PrefsPackage#getCustomPlugins_ImportPlugins()
	 * @model type="org.homeunix.drummer.prefs.PluginEntry" containment="true"
	 * @generated
	 */
	EList getImportPlugins();

} // CustomPlugins