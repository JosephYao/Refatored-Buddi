/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.homeunix.drummer.prefs.impl;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.homeunix.drummer.prefs.ListAttributes;
import org.homeunix.drummer.prefs.PrefsPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>List Attributes</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.homeunix.drummer.prefs.impl.ListAttributesImpl#isUnrolled <em>Unrolled</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ListAttributesImpl extends EObjectImpl implements ListAttributes {
	/**
	 * The default value of the '{@link #isUnrolled() <em>Unrolled</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isUnrolled()
	 * @generated
	 * @ordered
	 */
	protected static final boolean UNROLLED_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isUnrolled() <em>Unrolled</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isUnrolled()
	 * @generated
	 * @ordered
	 */
	protected boolean unrolled = UNROLLED_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ListAttributesImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return PrefsPackage.eINSTANCE.getListAttributes();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isUnrolled() {
		return unrolled;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setUnrolled(boolean newUnrolled) {
		boolean oldUnrolled = unrolled;
		unrolled = newUnrolled;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PrefsPackage.LIST_ATTRIBUTES__UNROLLED, oldUnrolled, unrolled));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Object eGet(EStructuralFeature eFeature, boolean resolve) {
		switch (eDerivedStructuralFeatureID(eFeature)) {
			case PrefsPackage.LIST_ATTRIBUTES__UNROLLED:
				return isUnrolled() ? Boolean.TRUE : Boolean.FALSE;
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
			case PrefsPackage.LIST_ATTRIBUTES__UNROLLED:
				setUnrolled(((Boolean)newValue).booleanValue());
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
			case PrefsPackage.LIST_ATTRIBUTES__UNROLLED:
				setUnrolled(UNROLLED_EDEFAULT);
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
			case PrefsPackage.LIST_ATTRIBUTES__UNROLLED:
				return unrolled != UNROLLED_EDEFAULT;
		}
		return eDynamicIsSet(eFeature);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (unrolled: ");
		result.append(unrolled);
		result.append(')');
		return result.toString();
	}

} //ListAttributesImpl
