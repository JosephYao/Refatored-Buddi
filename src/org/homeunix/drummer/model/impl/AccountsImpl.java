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
import org.homeunix.drummer.model.Account;
import org.homeunix.drummer.model.Accounts;
import org.homeunix.drummer.model.DataModel;
import org.homeunix.drummer.model.ModelPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Accounts</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.homeunix.drummer.model.impl.AccountsImpl#getAccounts <em>Accounts</em>}</li>
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
		return ModelPackage.Literals.ACCOUNTS;
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
	public DataModel getAllAccounts() {
		if (eContainerFeatureID != ModelPackage.ACCOUNTS__ALL_ACCOUNTS) return null;
		return (DataModel)eContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetAllAccounts(DataModel newAllAccounts, NotificationChain msgs) {
		msgs = eBasicSetContainer((InternalEObject)newAllAccounts, ModelPackage.ACCOUNTS__ALL_ACCOUNTS, msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAllAccounts(DataModel newAllAccounts) {
		if (newAllAccounts != eInternalContainer() || (eContainerFeatureID != ModelPackage.ACCOUNTS__ALL_ACCOUNTS && newAllAccounts != null)) {
			if (EcoreUtil.isAncestor(this, newAllAccounts))
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
			NotificationChain msgs = null;
			if (eInternalContainer() != null)
				msgs = eBasicRemoveFromContainer(msgs);
			if (newAllAccounts != null)
				msgs = ((InternalEObject)newAllAccounts).eInverseAdd(this, ModelPackage.DATA_MODEL__ALL_ACCOUNTS, DataModel.class, msgs);
			msgs = basicSetAllAccounts(newAllAccounts, msgs);
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
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ModelPackage.ACCOUNTS__ALL_ACCOUNTS:
				if (eInternalContainer() != null)
					msgs = eBasicRemoveFromContainer(msgs);
				return basicSetAllAccounts((DataModel)otherEnd, msgs);
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
			case ModelPackage.ACCOUNTS__ACCOUNTS:
				return ((InternalEList)getAccounts()).basicRemove(otherEnd, msgs);
			case ModelPackage.ACCOUNTS__ALL_ACCOUNTS:
				return basicSetAllAccounts(null, msgs);
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
			case ModelPackage.ACCOUNTS__ALL_ACCOUNTS:
				return eInternalContainer().eInverseRemove(this, ModelPackage.DATA_MODEL__ALL_ACCOUNTS, DataModel.class, msgs);
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
			case ModelPackage.ACCOUNTS__ACCOUNTS:
				return getAccounts();
			case ModelPackage.ACCOUNTS__ALL_ACCOUNTS:
				return getAllAccounts();
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
			case ModelPackage.ACCOUNTS__ACCOUNTS:
				getAccounts().clear();
				getAccounts().addAll((Collection)newValue);
				return;
			case ModelPackage.ACCOUNTS__ALL_ACCOUNTS:
				setAllAccounts((DataModel)newValue);
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
			case ModelPackage.ACCOUNTS__ACCOUNTS:
				getAccounts().clear();
				return;
			case ModelPackage.ACCOUNTS__ALL_ACCOUNTS:
				setAllAccounts((DataModel)null);
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
			case ModelPackage.ACCOUNTS__ACCOUNTS:
				return accounts != null && !accounts.isEmpty();
			case ModelPackage.ACCOUNTS__ALL_ACCOUNTS:
				return getAllAccounts() != null;
		}
		return super.eIsSet(featureID);
	}

} //AccountsImpl
