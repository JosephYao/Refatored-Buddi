/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.homeunix.drummer.prefs.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.homeunix.drummer.prefs.Prefs;
import org.homeunix.drummer.prefs.PrefsPackage;
import org.homeunix.drummer.prefs.UserPrefs;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>User Prefs</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.homeunix.drummer.prefs.impl.UserPrefsImpl#getPrefs <em>Prefs</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class UserPrefsImpl extends EObjectImpl implements UserPrefs {
	/**
	 * The cached value of the '{@link #getPrefs() <em>Prefs</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPrefs()
	 * @generated
	 * @ordered
	 */
	protected Prefs prefs = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected UserPrefsImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return PrefsPackage.eINSTANCE.getUserPrefs();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Prefs getPrefs() {
		return prefs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetPrefs(Prefs newPrefs, NotificationChain msgs) {
		Prefs oldPrefs = prefs;
		prefs = newPrefs;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, PrefsPackage.USER_PREFS__PREFS, oldPrefs, newPrefs);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPrefs(Prefs newPrefs) {
		if (newPrefs != prefs) {
			NotificationChain msgs = null;
			if (prefs != null)
				msgs = ((InternalEObject)prefs).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - PrefsPackage.USER_PREFS__PREFS, null, msgs);
			if (newPrefs != null)
				msgs = ((InternalEObject)newPrefs).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - PrefsPackage.USER_PREFS__PREFS, null, msgs);
			msgs = basicSetPrefs(newPrefs, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PrefsPackage.USER_PREFS__PREFS, newPrefs, newPrefs));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, Class baseClass, NotificationChain msgs) {
		if (featureID >= 0) {
			switch (eDerivedStructuralFeatureID(featureID, baseClass)) {
				case PrefsPackage.USER_PREFS__PREFS:
					return basicSetPrefs(null, msgs);
				default:
					return eDynamicInverseRemove(otherEnd, featureID, baseClass, msgs);
			}
		}
		return eBasicSetContainer(null, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Object eGet(EStructuralFeature eFeature, boolean resolve) {
		switch (eDerivedStructuralFeatureID(eFeature)) {
			case PrefsPackage.USER_PREFS__PREFS:
				return getPrefs();
		}
		return eDynamicGet(eFeature, resolve);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void eSet(EStructuralFeature eFeature, Object newValue) {
		switch (eDerivedStructuralFeatureID(eFeature)) {
			case PrefsPackage.USER_PREFS__PREFS:
				setPrefs((Prefs)newValue);
				return;
		}
		eDynamicSet(eFeature, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void eUnset(EStructuralFeature eFeature) {
		switch (eDerivedStructuralFeatureID(eFeature)) {
			case PrefsPackage.USER_PREFS__PREFS:
				setPrefs((Prefs)null);
				return;
		}
		eDynamicUnset(eFeature);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean eIsSet(EStructuralFeature eFeature) {
		switch (eDerivedStructuralFeatureID(eFeature)) {
			case PrefsPackage.USER_PREFS__PREFS:
				return prefs != null;
		}
		return eDynamicIsSet(eFeature);
	}

} //UserPrefsImpl
