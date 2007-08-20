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
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.InternalEList;

import org.homeunix.drummer.model.AutoSaveInfo;
import org.homeunix.drummer.model.DataModel;
import org.homeunix.drummer.model.Lists;
import org.homeunix.drummer.model.ModelPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Lists</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.homeunix.drummer.model.impl.ListsImpl#getAllLists <em>All Lists</em>}</li>
 *   <li>{@link org.homeunix.drummer.model.impl.ListsImpl#getAllAutoSave <em>All Auto Save</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ListsImpl extends EObjectImpl implements Lists {
	/**
	 * The cached value of the '{@link #getAllAutoSave() <em>All Auto Save</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAllAutoSave()
	 * @generated
	 * @ordered
	 */
	protected EList allAutoSave = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ListsImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return ModelPackage.Literals.LISTS;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DataModel getAllLists() {
		if (eContainerFeatureID != ModelPackage.LISTS__ALL_LISTS) return null;
		return (DataModel)eContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetAllLists(DataModel newAllLists, NotificationChain msgs) {
		msgs = eBasicSetContainer((InternalEObject)newAllLists, ModelPackage.LISTS__ALL_LISTS, msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAllLists(DataModel newAllLists) {
		if (newAllLists != eInternalContainer() || (eContainerFeatureID != ModelPackage.LISTS__ALL_LISTS && newAllLists != null)) {
			if (EcoreUtil.isAncestor(this, newAllLists))
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
			NotificationChain msgs = null;
			if (eInternalContainer() != null)
				msgs = eBasicRemoveFromContainer(msgs);
			if (newAllLists != null)
				msgs = ((InternalEObject)newAllLists).eInverseAdd(this, ModelPackage.DATA_MODEL__ALL_LISTS, DataModel.class, msgs);
			msgs = basicSetAllLists(newAllLists, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.LISTS__ALL_LISTS, newAllLists, newAllLists));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getAllAutoSave() {
		if (allAutoSave == null) {
			allAutoSave = new EObjectContainmentEList(AutoSaveInfo.class, this, ModelPackage.LISTS__ALL_AUTO_SAVE);
		}
		return allAutoSave;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ModelPackage.LISTS__ALL_LISTS:
				if (eInternalContainer() != null)
					msgs = eBasicRemoveFromContainer(msgs);
				return basicSetAllLists((DataModel)otherEnd, msgs);
		}
		return super.eInverseAdd(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ModelPackage.LISTS__ALL_LISTS:
				return basicSetAllLists(null, msgs);
			case ModelPackage.LISTS__ALL_AUTO_SAVE:
				return ((InternalEList)getAllAutoSave()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain eBasicRemoveFromContainerFeature(NotificationChain msgs) {
		switch (eContainerFeatureID) {
			case ModelPackage.LISTS__ALL_LISTS:
				return eInternalContainer().eInverseRemove(this, ModelPackage.DATA_MODEL__ALL_LISTS, DataModel.class, msgs);
		}
		return super.eBasicRemoveFromContainerFeature(msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ModelPackage.LISTS__ALL_LISTS:
				return getAllLists();
			case ModelPackage.LISTS__ALL_AUTO_SAVE:
				return getAllAutoSave();
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
			case ModelPackage.LISTS__ALL_LISTS:
				setAllLists((DataModel)newValue);
				return;
			case ModelPackage.LISTS__ALL_AUTO_SAVE:
				getAllAutoSave().clear();
				getAllAutoSave().addAll((Collection)newValue);
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
			case ModelPackage.LISTS__ALL_LISTS:
				setAllLists((DataModel)null);
				return;
			case ModelPackage.LISTS__ALL_AUTO_SAVE:
				getAllAutoSave().clear();
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
			case ModelPackage.LISTS__ALL_LISTS:
				return getAllLists() != null;
			case ModelPackage.LISTS__ALL_AUTO_SAVE:
				return allAutoSave != null && !allAutoSave.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //ListsImpl