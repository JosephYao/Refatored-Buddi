/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.homeunix.drummer.model.impl;

import java.util.Date;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.homeunix.drummer.model.Account;
import org.homeunix.drummer.model.ModelPackage;
import org.homeunix.drummer.model.Source;
import org.homeunix.drummer.model.Transaction;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Transaction</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.homeunix.drummer.model.impl.TransactionImpl#getAmount <em>Amount</em>}</li>
 *   <li>{@link org.homeunix.drummer.model.impl.TransactionImpl#getDescription <em>Description</em>}</li>
 *   <li>{@link org.homeunix.drummer.model.impl.TransactionImpl#getDate <em>Date</em>}</li>
 *   <li>{@link org.homeunix.drummer.model.impl.TransactionImpl#getFrom <em>From</em>}</li>
 *   <li>{@link org.homeunix.drummer.model.impl.TransactionImpl#getTo <em>To</em>}</li>
 * </ul>
 * </p>
 *
 */
public class TransactionImpl extends EObjectImpl implements Transaction, Comparable<Transaction> {
	/**
	 * The default value of the '{@link #getAmount() <em>Amount</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAmount()
	 * @generated
	 * @ordered
	 */
	protected static final long AMOUNT_EDEFAULT = 0L;

	/**
	 * The cached value of the '{@link #getAmount() <em>Amount</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAmount()
	 * @generated
	 * @ordered
	 */
	protected long amount = AMOUNT_EDEFAULT;

	/**
	 * The default value of the '{@link #getDescription() <em>Description</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDescription()
	 * @generated
	 * @ordered
	 */
	protected static final String DESCRIPTION_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getDescription() <em>Description</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDescription()
	 * @generated
	 * @ordered
	 */
	protected String description = DESCRIPTION_EDEFAULT;

	/**
	 * The default value of the '{@link #getDate() <em>Date</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDate()
	 * @generated
	 * @ordered
	 */
	protected static final Date DATE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getDate() <em>Date</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDate()
	 * @generated
	 * @ordered
	 */
	protected Date date = DATE_EDEFAULT;

	/**
	 * The default value of the '{@link #getNumber() <em>Number</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNumber()
	 * @generated
	 * @ordered
	 */
	protected static final String NUMBER_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getNumber() <em>Number</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNumber()
	 * @generated
	 * @ordered
	 */
	protected String number = NUMBER_EDEFAULT;

	/**
	 * The default value of the '{@link #getMemo() <em>Memo</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMemo()
	 * @generated
	 * @ordered
	 */
	protected static final String MEMO_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getMemo() <em>Memo</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMemo()
	 * @generated
	 * @ordered
	 */
	protected String memo = MEMO_EDEFAULT;

	/**
	 * The default value of the '{@link #getBalanceFrom() <em>Balance From</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBalanceFrom()
	 * @generated
	 * @ordered
	 */
	protected static final long BALANCE_FROM_EDEFAULT = 0L;

	/**
	 * The cached value of the '{@link #getBalanceFrom() <em>Balance From</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBalanceFrom()
	 * @generated
	 * @ordered
	 */
	protected long balanceFrom = BALANCE_FROM_EDEFAULT;

	/**
	 * The default value of the '{@link #getBalanceTo() <em>Balance To</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBalanceTo()
	 * @generated
	 * @ordered
	 */
	protected static final long BALANCE_TO_EDEFAULT = 0L;

	/**
	 * The cached value of the '{@link #getBalanceTo() <em>Balance To</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBalanceTo()
	 * @generated
	 * @ordered
	 */
	protected long balanceTo = BALANCE_TO_EDEFAULT;

	/**
	 * The cached value of the '{@link #getTo() <em>To</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTo()
	 * @generated
	 * @ordered
	 */
	protected Source to = null;

	/**
	 * The cached value of the '{@link #getFrom() <em>From</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFrom()
	 * @generated
	 * @ordered
	 */
	protected Source from = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected TransactionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return ModelPackage.eINSTANCE.getTransaction();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public long getAmount() {
		return amount;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAmount(long newAmount) {
		long oldAmount = amount;
		amount = newAmount;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.TRANSACTION__AMOUNT, oldAmount, amount));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDescription(String newDescription) {
		String oldDescription = description;
		description = newDescription;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.TRANSACTION__DESCRIPTION, oldDescription, description));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDate(Date newDate) {
		Date oldDate = date;
		date = newDate;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.TRANSACTION__DATE, oldDate, date));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getNumber() {
		return number;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setNumber(String newNumber) {
		String oldNumber = number;
		number = newNumber;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.TRANSACTION__NUMBER, oldNumber, number));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getMemo() {
		return memo;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMemo(String newMemo) {
		String oldMemo = memo;
		memo = newMemo;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.TRANSACTION__MEMO, oldMemo, memo));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public long getBalanceFrom() {
		return balanceFrom;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setBalanceFrom(long newBalanceFrom) {
		long oldBalanceFrom = balanceFrom;
		balanceFrom = newBalanceFrom;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.TRANSACTION__BALANCE_FROM, oldBalanceFrom, balanceFrom));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public long getBalanceTo() {
		return balanceTo;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setBalanceTo(long newBalanceTo) {
		long oldBalanceTo = balanceTo;
		balanceTo = newBalanceTo;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.TRANSACTION__BALANCE_TO, oldBalanceTo, balanceTo));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Source getFrom() {
		if (from != null && from.eIsProxy()) {
			Source oldFrom = from;
			from = (Source)eResolveProxy((InternalEObject)from);
			if (from != oldFrom) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, ModelPackage.TRANSACTION__FROM, oldFrom, from));
			}
		}
		return from;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Source basicGetFrom() {
		return from;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setFrom(Source newFrom) {
		Source oldFrom = from;
		from = newFrom;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.TRANSACTION__FROM, oldFrom, from));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Source getTo() {
		if (to != null && to.eIsProxy()) {
			Source oldTo = to;
			to = (Source)eResolveProxy((InternalEObject)to);
			if (to != oldTo) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, ModelPackage.TRANSACTION__TO, oldTo, to));
			}
		}
		return to;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Source basicGetTo() {
		return to;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTo(Source newTo) {
		Source oldTo = to;
		to = newTo;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.TRANSACTION__TO, oldTo, to));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Object eGet(EStructuralFeature eFeature, boolean resolve) {
		switch (eDerivedStructuralFeatureID(eFeature)) {
			case ModelPackage.TRANSACTION__AMOUNT:
				return new Long(getAmount());
			case ModelPackage.TRANSACTION__DESCRIPTION:
				return getDescription();
			case ModelPackage.TRANSACTION__DATE:
				return getDate();
			case ModelPackage.TRANSACTION__NUMBER:
				return getNumber();
			case ModelPackage.TRANSACTION__MEMO:
				return getMemo();
			case ModelPackage.TRANSACTION__BALANCE_FROM:
				return new Long(getBalanceFrom());
			case ModelPackage.TRANSACTION__BALANCE_TO:
				return new Long(getBalanceTo());
			case ModelPackage.TRANSACTION__TO:
				if (resolve) return getTo();
				return basicGetTo();
			case ModelPackage.TRANSACTION__FROM:
				if (resolve) return getFrom();
				return basicGetFrom();
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
			case ModelPackage.TRANSACTION__AMOUNT:
				setAmount(((Long)newValue).longValue());
				return;
			case ModelPackage.TRANSACTION__DESCRIPTION:
				setDescription((String)newValue);
				return;
			case ModelPackage.TRANSACTION__DATE:
				setDate((Date)newValue);
				return;
			case ModelPackage.TRANSACTION__NUMBER:
				setNumber((String)newValue);
				return;
			case ModelPackage.TRANSACTION__MEMO:
				setMemo((String)newValue);
				return;
			case ModelPackage.TRANSACTION__BALANCE_FROM:
				setBalanceFrom(((Long)newValue).longValue());
				return;
			case ModelPackage.TRANSACTION__BALANCE_TO:
				setBalanceTo(((Long)newValue).longValue());
				return;
			case ModelPackage.TRANSACTION__TO:
				setTo((Source)newValue);
				return;
			case ModelPackage.TRANSACTION__FROM:
				setFrom((Source)newValue);
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
			case ModelPackage.TRANSACTION__AMOUNT:
				setAmount(AMOUNT_EDEFAULT);
				return;
			case ModelPackage.TRANSACTION__DESCRIPTION:
				setDescription(DESCRIPTION_EDEFAULT);
				return;
			case ModelPackage.TRANSACTION__DATE:
				setDate(DATE_EDEFAULT);
				return;
			case ModelPackage.TRANSACTION__NUMBER:
				setNumber(NUMBER_EDEFAULT);
				return;
			case ModelPackage.TRANSACTION__MEMO:
				setMemo(MEMO_EDEFAULT);
				return;
			case ModelPackage.TRANSACTION__BALANCE_FROM:
				setBalanceFrom(BALANCE_FROM_EDEFAULT);
				return;
			case ModelPackage.TRANSACTION__BALANCE_TO:
				setBalanceTo(BALANCE_TO_EDEFAULT);
				return;
			case ModelPackage.TRANSACTION__TO:
				setTo((Source)null);
				return;
			case ModelPackage.TRANSACTION__FROM:
				setFrom((Source)null);
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
			case ModelPackage.TRANSACTION__AMOUNT:
				return amount != AMOUNT_EDEFAULT;
			case ModelPackage.TRANSACTION__DESCRIPTION:
				return DESCRIPTION_EDEFAULT == null ? description != null : !DESCRIPTION_EDEFAULT.equals(description);
			case ModelPackage.TRANSACTION__DATE:
				return DATE_EDEFAULT == null ? date != null : !DATE_EDEFAULT.equals(date);
			case ModelPackage.TRANSACTION__NUMBER:
				return NUMBER_EDEFAULT == null ? number != null : !NUMBER_EDEFAULT.equals(number);
			case ModelPackage.TRANSACTION__MEMO:
				return MEMO_EDEFAULT == null ? memo != null : !MEMO_EDEFAULT.equals(memo);
			case ModelPackage.TRANSACTION__BALANCE_FROM:
				return balanceFrom != BALANCE_FROM_EDEFAULT;
			case ModelPackage.TRANSACTION__BALANCE_TO:
				return balanceTo != BALANCE_TO_EDEFAULT;
			case ModelPackage.TRANSACTION__TO:
				return to != null;
			case ModelPackage.TRANSACTION__FROM:
				return from != null;
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
		result.append(" (amount: ");
		result.append(amount);
		result.append(", description: ");
		result.append(description);
		result.append(", date: ");
		result.append(date);
		result.append(", number: ");
		result.append(number);
		result.append(", memo: ");
		result.append(memo);
		result.append(", balanceFrom: ");
		result.append(balanceFrom);
		result.append(", balanceTo: ");
		result.append(balanceTo);
		result.append(')');
		return result.toString();
	}

	public int compareTo(Transaction arg0) {
		return this.getDate().compareTo(arg0.getDate());
	}
	
	public void calculateBalance(){
		//Update balance in affected accounts
		if (this.getFrom() instanceof Account){
			((Account) this.getFrom()).calculateBalance();
		}
		if (this.getTo() instanceof Account){
			((Account) this.getTo()).calculateBalance();
		}
	}

} //TransactionImpl
