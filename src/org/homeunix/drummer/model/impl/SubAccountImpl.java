/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.homeunix.drummer.model.impl;

import java.util.Collection;
import java.util.Date;
import java.util.Vector;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.homeunix.drummer.controller.DataInstance;
import org.homeunix.drummer.model.Account;
import org.homeunix.drummer.model.ModelPackage;
import org.homeunix.drummer.model.SubAccount;
import org.homeunix.drummer.model.Transaction;
import org.homeunix.drummer.model.Type;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Sub Account</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.homeunix.drummer.model.impl.SubAccountImpl#getParent <em>Parent</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class SubAccountImpl extends AccountImpl implements SubAccount {
	/**
	 * The cached value of the '{@link #getParent() <em>Parent</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getParent()
	 * @generated
	 * @ordered
	 */
	protected Account parent = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected SubAccountImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return ModelPackage.eINSTANCE.getSubAccount();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Account getParent() {
		if (parent != null && parent.eIsProxy()) {
			Account oldParent = parent;
			parent = (Account)eResolveProxy((InternalEObject)parent);
			if (parent != oldParent) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, ModelPackage.SUB_ACCOUNT__PARENT, oldParent, parent));
			}
		}
		return parent;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Account basicGetParent() {
		return parent;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setParent(Account newParent) {
		Account oldParent = parent;
		parent = newParent;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.SUB_ACCOUNT__PARENT, oldParent, parent));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Object eGet(EStructuralFeature eFeature, boolean resolve) {
		switch (eDerivedStructuralFeatureID(eFeature)) {
			case ModelPackage.SUB_ACCOUNT__NAME:
				return getName();
			case ModelPackage.SUB_ACCOUNT__DELETED:
				return isDeleted() ? Boolean.TRUE : Boolean.FALSE;
			case ModelPackage.SUB_ACCOUNT__CREATION_DATE:
				return getCreationDate();
			case ModelPackage.SUB_ACCOUNT__BALANCE:
				return new Long(getBalance());
			case ModelPackage.SUB_ACCOUNT__STARTING_BALANCE:
				return new Long(getStartingBalance());
			case ModelPackage.SUB_ACCOUNT__ACCOUNT_TYPE:
				if (resolve) return getAccountType();
				return basicGetAccountType();
			case ModelPackage.SUB_ACCOUNT__SUB:
				return getSub();
			case ModelPackage.SUB_ACCOUNT__PARENT:
				if (resolve) return getParent();
				return basicGetParent();
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
			case ModelPackage.SUB_ACCOUNT__NAME:
				setName((String)newValue);
				return;
			case ModelPackage.SUB_ACCOUNT__DELETED:
				setDeleted(((Boolean)newValue).booleanValue());
				return;
			case ModelPackage.SUB_ACCOUNT__CREATION_DATE:
				setCreationDate((Date)newValue);
				return;
			case ModelPackage.SUB_ACCOUNT__BALANCE:
				setBalance(((Long)newValue).longValue());
				return;
			case ModelPackage.SUB_ACCOUNT__STARTING_BALANCE:
				setStartingBalance(((Long)newValue).longValue());
				return;
			case ModelPackage.SUB_ACCOUNT__ACCOUNT_TYPE:
				setAccountType((Type)newValue);
				return;
			case ModelPackage.SUB_ACCOUNT__SUB:
				getSub().clear();
				getSub().addAll((Collection)newValue);
				return;
			case ModelPackage.SUB_ACCOUNT__PARENT:
				setParent((Account)newValue);
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
			case ModelPackage.SUB_ACCOUNT__NAME:
				setName(NAME_EDEFAULT);
				return;
			case ModelPackage.SUB_ACCOUNT__DELETED:
				setDeleted(DELETED_EDEFAULT);
				return;
			case ModelPackage.SUB_ACCOUNT__CREATION_DATE:
				setCreationDate(CREATION_DATE_EDEFAULT);
				return;
			case ModelPackage.SUB_ACCOUNT__BALANCE:
				setBalance(BALANCE_EDEFAULT);
				return;
			case ModelPackage.SUB_ACCOUNT__STARTING_BALANCE:
				setStartingBalance(STARTING_BALANCE_EDEFAULT);
				return;
			case ModelPackage.SUB_ACCOUNT__ACCOUNT_TYPE:
				setAccountType((Type)null);
				return;
			case ModelPackage.SUB_ACCOUNT__SUB:
				getSub().clear();
				return;
			case ModelPackage.SUB_ACCOUNT__PARENT:
				setParent((Account)null);
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
			case ModelPackage.SUB_ACCOUNT__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case ModelPackage.SUB_ACCOUNT__DELETED:
				return deleted != DELETED_EDEFAULT;
			case ModelPackage.SUB_ACCOUNT__CREATION_DATE:
				return CREATION_DATE_EDEFAULT == null ? creationDate != null : !CREATION_DATE_EDEFAULT.equals(creationDate);
			case ModelPackage.SUB_ACCOUNT__BALANCE:
				return balance != BALANCE_EDEFAULT;
			case ModelPackage.SUB_ACCOUNT__STARTING_BALANCE:
				return startingBalance != STARTING_BALANCE_EDEFAULT;
			case ModelPackage.SUB_ACCOUNT__ACCOUNT_TYPE:
				return accountType != null;
			case ModelPackage.SUB_ACCOUNT__SUB:
				return sub != null && !sub.isEmpty();
			case ModelPackage.SUB_ACCOUNT__PARENT:
				return parent != null;
		}
		return eDynamicIsSet(eFeature);
	}

	@Override
	public String toString() {
		StringBuffer result = new StringBuffer();
		result.append("[ ");
		result.append(name);
		result.append(" ]");

		return result.toString();
	}
	
	@Override
	public void calculateBalance(){
		long balance = 0;
		
		Vector<Transaction> transactions = DataInstance.getInstance().getTransactions(this);
		
		for (Transaction transaction : transactions) {
			//We are moving money *to* this account
			if (transaction.getTo().equals(this)){
				balance = balance + transaction.getAmount();
				transaction.setBalanceTo(balance);
			}
			
			//We are moving money *from* this account
			else{
				balance = balance - transaction.getAmount();
				transaction.setBalanceFrom(balance);
			}
		}
		
		this.setBalance(balance);
	}

} //SubAccountImpl
