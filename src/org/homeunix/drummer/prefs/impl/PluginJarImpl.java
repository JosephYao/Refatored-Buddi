/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.homeunix.drummer.prefs.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.homeunix.drummer.prefs.PluginEntry;
import org.homeunix.drummer.prefs.PluginJar;
import org.homeunix.drummer.prefs.PrefsPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Plugin Jar</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.homeunix.drummer.prefs.impl.PluginJarImpl#getJarFile <em>Jar File</em>}</li>
 *   <li>{@link org.homeunix.drummer.prefs.impl.PluginJarImpl#getExportPlugins <em>Export Plugins</em>}</li>
 *   <li>{@link org.homeunix.drummer.prefs.impl.PluginJarImpl#getPanelPlugins <em>Panel Plugins</em>}</li>
 *   <li>{@link org.homeunix.drummer.prefs.impl.PluginJarImpl#getImportPlugins <em>Import Plugins</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class PluginJarImpl extends EObjectImpl implements PluginJar {
	/**
	 * The default value of the '{@link #getJarFile() <em>Jar File</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getJarFile()
	 * @generated
	 * @ordered
	 */
	protected static final String JAR_FILE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getJarFile() <em>Jar File</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getJarFile()
	 * @generated
	 * @ordered
	 */
	protected String jarFile = JAR_FILE_EDEFAULT;

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
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected PluginJarImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return PrefsPackage.Literals.PLUGIN_JAR;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getJarFile() {
		return jarFile;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setJarFile(String newJarFile) {
		String oldJarFile = jarFile;
		jarFile = newJarFile;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PrefsPackage.PLUGIN_JAR__JAR_FILE, oldJarFile, jarFile));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getExportPlugins() {
		if (exportPlugins == null) {
			exportPlugins = new EObjectContainmentEList(PluginEntry.class, this, PrefsPackage.PLUGIN_JAR__EXPORT_PLUGINS);
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
			panelPlugins = new EObjectContainmentEList(PluginEntry.class, this, PrefsPackage.PLUGIN_JAR__PANEL_PLUGINS);
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
			importPlugins = new EObjectContainmentEList(PluginEntry.class, this, PrefsPackage.PLUGIN_JAR__IMPORT_PLUGINS);
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
			case PrefsPackage.PLUGIN_JAR__EXPORT_PLUGINS:
				return ((InternalEList)getExportPlugins()).basicRemove(otherEnd, msgs);
			case PrefsPackage.PLUGIN_JAR__PANEL_PLUGINS:
				return ((InternalEList)getPanelPlugins()).basicRemove(otherEnd, msgs);
			case PrefsPackage.PLUGIN_JAR__IMPORT_PLUGINS:
				return ((InternalEList)getImportPlugins()).basicRemove(otherEnd, msgs);
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
			case PrefsPackage.PLUGIN_JAR__JAR_FILE:
				return getJarFile();
			case PrefsPackage.PLUGIN_JAR__EXPORT_PLUGINS:
				return getExportPlugins();
			case PrefsPackage.PLUGIN_JAR__PANEL_PLUGINS:
				return getPanelPlugins();
			case PrefsPackage.PLUGIN_JAR__IMPORT_PLUGINS:
				return getImportPlugins();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case PrefsPackage.PLUGIN_JAR__JAR_FILE:
				setJarFile((String)newValue);
				return;
			case PrefsPackage.PLUGIN_JAR__EXPORT_PLUGINS:
				getExportPlugins().clear();
				getExportPlugins().addAll((Collection)newValue);
				return;
			case PrefsPackage.PLUGIN_JAR__PANEL_PLUGINS:
				getPanelPlugins().clear();
				getPanelPlugins().addAll((Collection)newValue);
				return;
			case PrefsPackage.PLUGIN_JAR__IMPORT_PLUGINS:
				getImportPlugins().clear();
				getImportPlugins().addAll((Collection)newValue);
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
			case PrefsPackage.PLUGIN_JAR__JAR_FILE:
				setJarFile(JAR_FILE_EDEFAULT);
				return;
			case PrefsPackage.PLUGIN_JAR__EXPORT_PLUGINS:
				getExportPlugins().clear();
				return;
			case PrefsPackage.PLUGIN_JAR__PANEL_PLUGINS:
				getPanelPlugins().clear();
				return;
			case PrefsPackage.PLUGIN_JAR__IMPORT_PLUGINS:
				getImportPlugins().clear();
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
			case PrefsPackage.PLUGIN_JAR__JAR_FILE:
				return JAR_FILE_EDEFAULT == null ? jarFile != null : !JAR_FILE_EDEFAULT.equals(jarFile);
			case PrefsPackage.PLUGIN_JAR__EXPORT_PLUGINS:
				return exportPlugins != null && !exportPlugins.isEmpty();
			case PrefsPackage.PLUGIN_JAR__PANEL_PLUGINS:
				return panelPlugins != null && !panelPlugins.isEmpty();
			case PrefsPackage.PLUGIN_JAR__IMPORT_PLUGINS:
				return importPlugins != null && !importPlugins.isEmpty();
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (jarFile: ");
		result.append(jarFile);
		result.append(')');
		return result.toString();
	}

} //PluginJarImpl