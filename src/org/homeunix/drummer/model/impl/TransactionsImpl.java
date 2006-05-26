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
import org.homeunix.drummer.model.Transaction;
import org.homeunix.drummer.model.Transactions;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Transactions</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.homeunix.drummer.model.impl.TransactionsImpl#getAllTransactions <em>All Transactions</em>}</li>
 *   <li>{@link org.homeunix.drummer.model.impl.TransactionsImpl#getTransactions <em>Transactions</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class TransactionsImpl extends EObjectImpl implements Transactions {
	/**
	 * The cached value of the '{@link #getTransactions() <em>Transactions</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTransactions()
	 * @generated
	 * @ordered
	 */
	protected EList transactions = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected TransactionsImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return ModelPackage.eINSTANCE.getTransactions();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getTransactions() {
		if (transactions == null) {
			transactions = new EObjectContainmentEList(Transaction.class, this, ModelPackage.TRANSACTIONS__TRANSACTIONS);
		}
		return transactions;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DataModel getAllTransactions() {
		if (eContainerFeatureID != ModelPackage.TRANSACTIONS__ALL_TRANSACTIONS) return null;
		return (DataModel)eContainer;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAllTransactions(DataModel newAllTransactions) {
		if (newAllTransactions != eContainer || (eContainerFeatureID != ModelPackage.TRANSACTIONS__ALL_TRANSACTIONS && newAllTransactions != null)) {
			if (EcoreUtil.isAncestor(this, newAllTransactions))
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
			NotificationChain msgs = null;
			if (eContainer != null)
				msgs = eBasicRemoveFromContainer(msgs);
			if (newAllTransactions != null)
				msgs = ((InternalEObject)newAllTransactions).eInverseAdd(this, ModelPackage.DATA_MODEL__ALL_TRANSACTIONS, DataModel.class, msgs);
			msgs = eBasicSetContainer((InternalEObject)newAllTransactions, ModelPackage.TRANSACTIONS__ALL_TRANSACTIONS, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.TRANSACTIONS__ALL_TRANSACTIONS, newAllTransactions, newAllTransactions));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, Class baseClass, NotificationChain msgs) {
		if (featureID >= 0) {
			switch (eDerivedStructuralFeatureID(featureID, baseClass)) {
				case ModelPackage.TRANSACTIONS__ALL_TRANSACTIONS:
					if (eContainer != null)
						msgs = eBasicRemoveFromContainer(msgs);
					return eBasicSetContainer(otherEnd, ModelPackage.TRANSACTIONS__ALL_TRANSACTIONS, msgs);
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
				case ModelPackage.TRANSACTIONS__ALL_TRANSACTIONS:
					return eBasicSetContainer(null, ModelPackage.TRANSACTIONS__ALL_TRANSACTIONS, msgs);
				case ModelPackage.TRANSACTIONS__TRANSACTIONS:
					return ((InternalEList)getTransactions()).basicRemove(otherEnd, msgs);
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
				case ModelPackage.TRANSACTIONS__ALL_TRANSACTIONS:
					return eContainer.eInverseRemove(this, ModelPackage.DATA_MODEL__ALL_TRANSACTIONS, DataModel.class, msgs);
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
			case ModelPackage.TRANSACTIONS__ALL_TRANSACTIONS:
				return getAllTransactions();
			case ModelPackage.TRANSACTIONS__TRANSACTIONS:
				return getTransactions();
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
			case ModelPackage.TRANSACTIONS__ALL_TRANSACTIONS:
				setAllTransactions((DataModel)newValue);
				return;
			case ModelPackage.TRANSACTIONS__TRANSACTIONS:
				getTransactions().clear();
				getTransactions().addAll((Collection)newValue);
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
			case ModelPackage.TRANSACTIONS__ALL_TRANSACTIONS:
				setAllTransactions((DataModel)null);
				return;
			case ModelPackage.TRANSACTIONS__TRANSACTIONS:
				getTransactions().clear();
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
			case ModelPackage.TRANSACTIONS__ALL_TRANSACTIONS:
				return getAllTransactions() != null;
			case ModelPackage.TRANSACTIONS__TRANSACTIONS:
				return transactions != null && !transactions.isEmpty();
		}
		return eDynamicIsSet(eFeature);
	}

} //TransactionsImpl
