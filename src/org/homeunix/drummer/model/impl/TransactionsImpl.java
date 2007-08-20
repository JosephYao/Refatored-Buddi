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
import org.homeunix.drummer.model.DataModel;
import org.homeunix.drummer.model.ModelPackage;
import org.homeunix.drummer.model.Schedule;
import org.homeunix.drummer.model.Transaction;
import org.homeunix.drummer.model.Transactions;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Transactions</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.homeunix.drummer.model.impl.TransactionsImpl#getScheduledTransactions <em>Scheduled Transactions</em>}</li>
 *   <li>{@link org.homeunix.drummer.model.impl.TransactionsImpl#getTransactions <em>Transactions</em>}</li>
 *   <li>{@link org.homeunix.drummer.model.impl.TransactionsImpl#getAllTransactions <em>All Transactions</em>}</li>
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
	 * The cached value of the '{@link #getScheduledTransactions() <em>Scheduled Transactions</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getScheduledTransactions()
	 * @generated
	 * @ordered
	 */
	protected EList scheduledTransactions = null;

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
		return ModelPackage.Literals.TRANSACTIONS;
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
		return (DataModel)eContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetAllTransactions(DataModel newAllTransactions, NotificationChain msgs) {
		msgs = eBasicSetContainer((InternalEObject)newAllTransactions, ModelPackage.TRANSACTIONS__ALL_TRANSACTIONS, msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAllTransactions(DataModel newAllTransactions) {
		if (newAllTransactions != eInternalContainer() || (eContainerFeatureID != ModelPackage.TRANSACTIONS__ALL_TRANSACTIONS && newAllTransactions != null)) {
			if (EcoreUtil.isAncestor(this, newAllTransactions))
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
			NotificationChain msgs = null;
			if (eInternalContainer() != null)
				msgs = eBasicRemoveFromContainer(msgs);
			if (newAllTransactions != null)
				msgs = ((InternalEObject)newAllTransactions).eInverseAdd(this, ModelPackage.DATA_MODEL__ALL_TRANSACTIONS, DataModel.class, msgs);
			msgs = basicSetAllTransactions(newAllTransactions, msgs);
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
	public EList getScheduledTransactions() {
		if (scheduledTransactions == null) {
			scheduledTransactions = new EObjectContainmentEList(Schedule.class, this, ModelPackage.TRANSACTIONS__SCHEDULED_TRANSACTIONS);
		}
		return scheduledTransactions;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ModelPackage.TRANSACTIONS__ALL_TRANSACTIONS:
				if (eInternalContainer() != null)
					msgs = eBasicRemoveFromContainer(msgs);
				return basicSetAllTransactions((DataModel)otherEnd, msgs);
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
			case ModelPackage.TRANSACTIONS__SCHEDULED_TRANSACTIONS:
				return ((InternalEList)getScheduledTransactions()).basicRemove(otherEnd, msgs);
			case ModelPackage.TRANSACTIONS__TRANSACTIONS:
				return ((InternalEList)getTransactions()).basicRemove(otherEnd, msgs);
			case ModelPackage.TRANSACTIONS__ALL_TRANSACTIONS:
				return basicSetAllTransactions(null, msgs);
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
			case ModelPackage.TRANSACTIONS__ALL_TRANSACTIONS:
				return eInternalContainer().eInverseRemove(this, ModelPackage.DATA_MODEL__ALL_TRANSACTIONS, DataModel.class, msgs);
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
			case ModelPackage.TRANSACTIONS__SCHEDULED_TRANSACTIONS:
				return getScheduledTransactions();
			case ModelPackage.TRANSACTIONS__TRANSACTIONS:
				return getTransactions();
			case ModelPackage.TRANSACTIONS__ALL_TRANSACTIONS:
				return getAllTransactions();
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
			case ModelPackage.TRANSACTIONS__SCHEDULED_TRANSACTIONS:
				getScheduledTransactions().clear();
				getScheduledTransactions().addAll((Collection)newValue);
				return;
			case ModelPackage.TRANSACTIONS__TRANSACTIONS:
				getTransactions().clear();
				getTransactions().addAll((Collection)newValue);
				return;
			case ModelPackage.TRANSACTIONS__ALL_TRANSACTIONS:
				setAllTransactions((DataModel)newValue);
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
			case ModelPackage.TRANSACTIONS__SCHEDULED_TRANSACTIONS:
				getScheduledTransactions().clear();
				return;
			case ModelPackage.TRANSACTIONS__TRANSACTIONS:
				getTransactions().clear();
				return;
			case ModelPackage.TRANSACTIONS__ALL_TRANSACTIONS:
				setAllTransactions((DataModel)null);
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
			case ModelPackage.TRANSACTIONS__SCHEDULED_TRANSACTIONS:
				return scheduledTransactions != null && !scheduledTransactions.isEmpty();
			case ModelPackage.TRANSACTIONS__TRANSACTIONS:
				return transactions != null && !transactions.isEmpty();
			case ModelPackage.TRANSACTIONS__ALL_TRANSACTIONS:
				return getAllTransactions() != null;
		}
		return super.eIsSet(featureID);
	}

} //TransactionsImpl
