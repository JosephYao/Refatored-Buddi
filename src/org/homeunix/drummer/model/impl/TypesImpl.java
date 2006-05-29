/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.homeunix.drummer.model.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.InternalEList;

import org.homeunix.drummer.model.DataModel;
import org.homeunix.drummer.model.ModelPackage;
import org.homeunix.drummer.model.Type;
import org.homeunix.drummer.model.Types;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Types</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.homeunix.drummer.model.impl.TypesImpl#getTypes <em>Types</em>}</li>
 *   <li>{@link org.homeunix.drummer.model.impl.TypesImpl#getAllTypes <em>All Types</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class TypesImpl extends EObjectImpl implements Types {
	/**
	 * The cached value of the '{@link #getTypes() <em>Types</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTypes()
	 * @generated
	 * @ordered
	 */
	protected EList types = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected TypesImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return ModelPackage.eINSTANCE.getTypes();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DataModel getAllTypes() {
		if (eContainerFeatureID != ModelPackage.TYPES__ALL_TYPES) return null;
		return (DataModel)eContainer;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAllTypes(DataModel newAllTypes) {
		if (newAllTypes != eContainer || (eContainerFeatureID != ModelPackage.TYPES__ALL_TYPES && newAllTypes != null)) {
			if (EcoreUtil.isAncestor(this, newAllTypes))
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
			NotificationChain msgs = null;
			if (eContainer != null)
				msgs = eBasicRemoveFromContainer(msgs);
			if (newAllTypes != null)
				msgs = ((InternalEObject)newAllTypes).eInverseAdd(this, ModelPackage.DATA_MODEL__ALL_TYPES, DataModel.class, msgs);
			msgs = eBasicSetContainer((InternalEObject)newAllTypes, ModelPackage.TYPES__ALL_TYPES, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.TYPES__ALL_TYPES, newAllTypes, newAllTypes));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getTypes() {
		if (types == null) {
			types = new EObjectContainmentEList(Type.class, this, ModelPackage.TYPES__TYPES);
		}
		return types;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, Class baseClass, NotificationChain msgs) {
		if (featureID >= 0) {
			switch (eDerivedStructuralFeatureID(featureID, baseClass)) {
				case ModelPackage.TYPES__ALL_TYPES:
					if (eContainer != null)
						msgs = eBasicRemoveFromContainer(msgs);
					return eBasicSetContainer(otherEnd, ModelPackage.TYPES__ALL_TYPES, msgs);
				default:
					return eDynamicInverseAdd(otherEnd, featureID, baseClass, msgs);
			}
		}
		if (eContainer != null)
			msgs = eBasicRemoveFromContainer(msgs);
		return eBasicSetContainer(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, Class baseClass, NotificationChain msgs) {
		if (featureID >= 0) {
			switch (eDerivedStructuralFeatureID(featureID, baseClass)) {
				case ModelPackage.TYPES__TYPES:
					return ((InternalEList)getTypes()).basicRemove(otherEnd, msgs);
				case ModelPackage.TYPES__ALL_TYPES:
					return eBasicSetContainer(null, ModelPackage.TYPES__ALL_TYPES, msgs);
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
	public NotificationChain eBasicRemoveFromContainer(NotificationChain msgs) {
		if (eContainerFeatureID >= 0) {
			switch (eContainerFeatureID) {
				case ModelPackage.TYPES__ALL_TYPES:
					return eContainer.eInverseRemove(this, ModelPackage.DATA_MODEL__ALL_TYPES, DataModel.class, msgs);
				default:
					return eDynamicBasicRemoveFromContainer(msgs);
			}
		}
		return eContainer.eInverseRemove(this, EOPPOSITE_FEATURE_BASE - eContainerFeatureID, null, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Object eGet(EStructuralFeature eFeature, boolean resolve) {
		switch (eDerivedStructuralFeatureID(eFeature)) {
			case ModelPackage.TYPES__TYPES:
				return getTypes();
			case ModelPackage.TYPES__ALL_TYPES:
				return getAllTypes();
		}
		return eDynamicGet(eFeature, resolve);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public void eSet(EStructuralFeature eFeature, Object newValue) {
		switch (eDerivedStructuralFeatureID(eFeature)) {
			case ModelPackage.TYPES__TYPES:
				getTypes().clear();
				getTypes().addAll((Collection)newValue);
				return;
			case ModelPackage.TYPES__ALL_TYPES:
				setAllTypes((DataModel)newValue);
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
			case ModelPackage.TYPES__TYPES:
				getTypes().clear();
				return;
			case ModelPackage.TYPES__ALL_TYPES:
				setAllTypes((DataModel)null);
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
			case ModelPackage.TYPES__TYPES:
				return types != null && !types.isEmpty();
			case ModelPackage.TYPES__ALL_TYPES:
				return getAllTypes() != null;
		}
		return eDynamicIsSet(eFeature);
	}

} //TypesImpl
