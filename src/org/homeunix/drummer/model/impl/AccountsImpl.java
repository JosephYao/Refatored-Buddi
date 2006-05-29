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
import org.homeunix.drummer.model.Account;
import org.homeunix.drummer.model.Accounts;
import org.homeunix.drummer.model.DataModel;
import org.homeunix.drummer.model.ModelPackage;

import org.homeunix.drummer.model.SubAccount;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Accounts</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.homeunix.drummer.model.impl.AccountsImpl#getAccounts <em>Accounts</em>}</li>
 *   <li>{@link org.homeunix.drummer.model.impl.AccountsImpl#getSubAccounts <em>Sub Accounts</em>}</li>
 *   <li>{@link org.homeunix.drummer.model.impl.AccountsImpl#getAllAccounts <em>All Accounts</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class AccountsImpl extends EObjectImpl implements Accounts {
	/**
	 * The cached value of the '{@link #getAccounts() <em>Accounts</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAccounts()
	 * @generated
	 * @ordered
	 */
	protected EList accounts = null;

	/**
	 * The cached value of the '{@link #getSubAccounts() <em>Sub Accounts</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSubAccounts()
	 * @generated
	 * @ordered
	 */
	protected EList subAccounts = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected AccountsImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return ModelPackage.eINSTANCE.getAccounts();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getAccounts() {
		if (accounts == null) {
			accounts = new EObjectContainmentEList(Account.class, this, ModelPackage.ACCOUNTS__ACCOUNTS);
		}
		return accounts;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getSubAccounts() {
		if (subAccounts == null) {
			subAccounts = new EObjectContainmentEList(SubAccount.class, this, ModelPackage.ACCOUNTS__SUB_ACCOUNTS);
		}
		return subAccounts;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DataModel getAllAccounts() {
		if (eContainerFeatureID != ModelPackage.ACCOUNTS__ALL_ACCOUNTS) return null;
		return (DataModel)eContainer;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAllAccounts(DataModel newAllAccounts) {
		if (newAllAccounts != eContainer || (eContainerFeatureID != ModelPackage.ACCOUNTS__ALL_ACCOUNTS && newAllAccounts != null)) {
			if (EcoreUtil.isAncestor(this, newAllAccounts))
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
			NotificationChain msgs = null;
			if (eContainer != null)
				msgs = eBasicRemoveFromContainer(msgs);
			if (newAllAccounts != null)
				msgs = ((InternalEObject)newAllAccounts).eInverseAdd(this, ModelPackage.DATA_MODEL__ALL_ACCOUNTS, DataModel.class, msgs);
			msgs = eBasicSetContainer((InternalEObject)newAllAccounts, ModelPackage.ACCOUNTS__ALL_ACCOUNTS, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.ACCOUNTS__ALL_ACCOUNTS, newAllAccounts, newAllAccounts));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, Class baseClass, NotificationChain msgs) {
		if (featureID >= 0) {
			switch (eDerivedStructuralFeatureID(featureID, baseClass)) {
				case ModelPackage.ACCOUNTS__ALL_ACCOUNTS:
					if (eContainer != null)
						msgs = eBasicRemoveFromContainer(msgs);
					return eBasicSetContainer(otherEnd, ModelPackage.ACCOUNTS__ALL_ACCOUNTS, msgs);
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
				case ModelPackage.ACCOUNTS__ACCOUNTS:
					return ((InternalEList)getAccounts()).basicRemove(otherEnd, msgs);
				case ModelPackage.ACCOUNTS__SUB_ACCOUNTS:
					return ((InternalEList)getSubAccounts()).basicRemove(otherEnd, msgs);
				case ModelPackage.ACCOUNTS__ALL_ACCOUNTS:
					return eBasicSetContainer(null, ModelPackage.ACCOUNTS__ALL_ACCOUNTS, msgs);
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
				case ModelPackage.ACCOUNTS__ALL_ACCOUNTS:
					return eContainer.eInverseRemove(this, ModelPackage.DATA_MODEL__ALL_ACCOUNTS, DataModel.class, msgs);
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
			case ModelPackage.ACCOUNTS__ACCOUNTS:
				return getAccounts();
			case ModelPackage.ACCOUNTS__SUB_ACCOUNTS:
				return getSubAccounts();
			case ModelPackage.ACCOUNTS__ALL_ACCOUNTS:
				return getAllAccounts();
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
			case ModelPackage.ACCOUNTS__ACCOUNTS:
				getAccounts().clear();
				getAccounts().addAll((Collection)newValue);
				return;
			case ModelPackage.ACCOUNTS__SUB_ACCOUNTS:
				getSubAccounts().clear();
				getSubAccounts().addAll((Collection)newValue);
				return;
			case ModelPackage.ACCOUNTS__ALL_ACCOUNTS:
				setAllAccounts((DataModel)newValue);
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
			case ModelPackage.ACCOUNTS__ACCOUNTS:
				getAccounts().clear();
				return;
			case ModelPackage.ACCOUNTS__SUB_ACCOUNTS:
				getSubAccounts().clear();
				return;
			case ModelPackage.ACCOUNTS__ALL_ACCOUNTS:
				setAllAccounts((DataModel)null);
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
			case ModelPackage.ACCOUNTS__ACCOUNTS:
				return accounts != null && !accounts.isEmpty();
			case ModelPackage.ACCOUNTS__SUB_ACCOUNTS:
				return subAccounts != null && !subAccounts.isEmpty();
			case ModelPackage.ACCOUNTS__ALL_ACCOUNTS:
				return getAllAccounts() != null;
		}
		return eDynamicIsSet(eFeature);
	}

} //AccountsImpl
