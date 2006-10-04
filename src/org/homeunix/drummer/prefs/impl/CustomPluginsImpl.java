/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.homeunix.drummer.prefs.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.homeunix.drummer.prefs.CustomPlugins;
import org.homeunix.drummer.prefs.PluginEntry;
import org.homeunix.drummer.prefs.PrefsPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Custom Plugins</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.homeunix.drummer.prefs.impl.CustomPluginsImpl#getPanelPlugins <em>Panel Plugins</em>}</li>
 *   <li>{@link org.homeunix.drummer.prefs.impl.CustomPluginsImpl#getImportPlugins <em>Import Plugins</em>}</li>
 *   <li>{@link org.homeunix.drummer.prefs.impl.CustomPluginsImpl#getExportPlugins <em>Export Plugins</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class CustomPluginsImpl extends EObjectImpl implements CustomPlugins {
	/**
	 * The cached value of the '{@link #getPanelPlugins() <em>Panel Plugins</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPanelPlugins()
	 * @generated
	 * @ordered
	 */
	protected EList panelPlugins = null;

	/**
	 * The cached value of the '{@link #getImportPlugins() <em>Import Plugins</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getImportPlugins()
	 * @generated
	 * @ordered
	 */
	protected EList importPlugins = null;

	/**
	 * The cached value of the '{@link #getExportPlugins() <em>Export Plugins</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getExportPlugins()
	 * @generated
	 * @ordered
	 */
	protected EList exportPlugins = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected CustomPluginsImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return PrefsPackage.Literals.CUSTOM_PLUGINS;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getExportPlugins() {
		if (exportPlugins == null) {
			exportPlugins = new EObjectContainmentEList(PluginEntry.class, this, PrefsPackage.CUSTOM_PLUGINS__EXPORT_PLUGINS);
		}
		return exportPlugins;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getPanelPlugins() {
		if (panelPlugins == null) {
			panelPlugins = new EObjectContainmentEList(PluginEntry.class, this, PrefsPackage.CUSTOM_PLUGINS__PANEL_PLUGINS);
		}
		return panelPlugins;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getImportPlugins() {
		if (importPlugins == null) {
			importPlugins = new EObjectContainmentEList(PluginEntry.class, this, PrefsPackage.CUSTOM_PLUGINS__IMPORT_PLUGINS);
		}
		return importPlugins;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case PrefsPackage.CUSTOM_PLUGINS__PANEL_PLUGINS:
				return ((InternalEList)getPanelPlugins()).basicRemove(otherEnd, msgs);
			case PrefsPackage.CUSTOM_PLUGINS__IMPORT_PLUGINS:
				return ((InternalEList)getImportPlugins()).basicRemove(otherEnd, msgs);
			case PrefsPackage.CUSTOM_PLUGINS__EXPORT_PLUGINS:
				return ((InternalEList)getExportPlugins()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case PrefsPackage.CUSTOM_PLUGINS__PANEL_PLUGINS:
				return getPanelPlugins();
			case PrefsPackage.CUSTOM_PLUGINS__IMPORT_PLUGINS:
				return getImportPlugins();
			case PrefsPackage.CUSTOM_PLUGINS__EXPORT_PLUGINS:
				return getExportPlugins();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case PrefsPackage.CUSTOM_PLUGINS__PANEL_PLUGINS:
				getPanelPlugins().clear();
				getPanelPlugins().addAll((Collection)newValue);
				return;
			case PrefsPackage.CUSTOM_PLUGINS__IMPORT_PLUGINS:
				getImportPlugins().clear();
				getImportPlugins().addAll((Collection)newValue);
				return;
			case PrefsPackage.CUSTOM_PLUGINS__EXPORT_PLUGINS:
				getExportPlugins().clear();
				getExportPlugins().addAll((Collection)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void eUnset(int featureID) {
		switch (featureID) {
			case PrefsPackage.CUSTOM_PLUGINS__PANEL_PLUGINS:
				getPanelPlugins().clear();
				return;
			case PrefsPackage.CUSTOM_PLUGINS__IMPORT_PLUGINS:
				getImportPlugins().clear();
				return;
			case PrefsPackage.CUSTOM_PLUGINS__EXPORT_PLUGINS:
				getExportPlugins().clear();
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case PrefsPackage.CUSTOM_PLUGINS__PANEL_PLUGINS:
				return panelPlugins != null && !panelPlugins.isEmpty();
			case PrefsPackage.CUSTOM_PLUGINS__IMPORT_PLUGINS:
				return importPlugins != null && !importPlugins.isEmpty();
			case PrefsPackage.CUSTOM_PLUGINS__EXPORT_PLUGINS:
				return exportPlugins != null && !exportPlugins.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //CustomPluginsImpl