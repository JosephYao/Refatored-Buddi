/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.homeunix.drummer.prefs.impl;

import java.io.File;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.homeunix.drummer.prefs.Plugin;
import org.homeunix.drummer.prefs.PrefsPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Plugin</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.homeunix.drummer.prefs.impl.PluginImpl#getJarFile <em>Jar File</em>}</li>
 *   <li>{@link org.homeunix.drummer.prefs.impl.PluginImpl#getClassName <em>Class Name</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class PluginImpl extends EObjectImpl implements Plugin {
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
	 * The default value of the '{@link #getClassName() <em>Class Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getClassName()
	 * @generated
	 * @ordered
	 */
	protected static final String CLASS_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getClassName() <em>Class Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getClassName()
	 * @generated
	 * @ordered
	 */
	protected String className = CLASS_NAME_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected PluginImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return PrefsPackage.Literals.PLUGIN;
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
			eNotify(new ENotificationImpl(this, Notification.SET, PrefsPackage.PLUGIN__JAR_FILE, oldJarFile, jarFile));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getClassName() {
		return className;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setClassName(String newClassName) {
		String oldClassName = className;
		className = newClassName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PrefsPackage.PLUGIN__CLASS_NAME, oldClassName, className));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case PrefsPackage.PLUGIN__JAR_FILE:
				return getJarFile();
			case PrefsPackage.PLUGIN__CLASS_NAME:
				return getClassName();
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
			case PrefsPackage.PLUGIN__JAR_FILE:
				setJarFile((String)newValue);
				return;
			case PrefsPackage.PLUGIN__CLASS_NAME:
				setClassName((String)newValue);
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
			case PrefsPackage.PLUGIN__JAR_FILE:
				setJarFile(JAR_FILE_EDEFAULT);
				return;
			case PrefsPackage.PLUGIN__CLASS_NAME:
				setClassName(CLASS_NAME_EDEFAULT);
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
			case PrefsPackage.PLUGIN__JAR_FILE:
				return JAR_FILE_EDEFAULT == null ? jarFile != null : !JAR_FILE_EDEFAULT.equals(jarFile);
			case PrefsPackage.PLUGIN__CLASS_NAME:
				return CLASS_NAME_EDEFAULT == null ? className != null : !CLASS_NAME_EDEFAULT.equals(className);
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 */
	public String toString() {
		StringBuffer result = new StringBuffer();
		result.append(className.replaceAll(".class$", "").replaceAll("^.*" + File.separator, ""));
		result.append(" (");
		result.append(jarFile.replaceAll("^.*" + File.separator, ""));
		result.append(')');
		return result.toString();
	}

} //PluginImpl