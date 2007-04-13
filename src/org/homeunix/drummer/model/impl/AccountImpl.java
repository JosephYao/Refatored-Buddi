/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.homeunix.drummer.model.impl;

import java.util.Date;
import java.util.Vector;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.homeunix.drummer.controller.TransactionController;
import org.homeunix.drummer.model.Account;
import org.homeunix.drummer.model.ModelPackage;
import org.homeunix.drummer.model.Source;
import org.homeunix.drummer.model.Transaction;
import org.homeunix.drummer.model.Type;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Account</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.homeunix.drummer.model.impl.AccountImpl#getBalance <em>Balance</em>}</li>
 *   <li>{@link org.homeunix.drummer.model.impl.AccountImpl#getStartingBalance <em>Starting Balance</em>}</li>
 *   <li>{@link org.homeunix.drummer.model.impl.AccountImpl#getCreditLimit <em>Credit Limit</em>}</li>
 *   <li>{@link org.homeunix.drummer.model.impl.AccountImpl#getInterestRate <em>Interest Rate</em>}</li>
 *   <li>{@link org.homeunix.drummer.model.impl.AccountImpl#getDueDate <em>Due Date</em>}</li>
 *   <li>{@link org.homeunix.drummer.model.impl.AccountImpl#getAccountType <em>Account Type</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class AccountImpl extends SourceImpl implements Account {
	/**
	 * The default value of the '{@link #getBalance() <em>Balance</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBalance()
	 * @generated
	 * @ordered
	 */
	protected static final long BALANCE_EDEFAULT = 0L;

	/**
	 * The cached value of the '{@link #getBalance() <em>Balance</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBalance()
	 * @generated
	 * @ordered
	 */
	protected long balance = BALANCE_EDEFAULT;

	/**
	 * The default value of the '{@link #getStartingBalance() <em>Starting Balance</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStartingBalance()
	 * @generated
	 * @ordered
	 */
	protected static final long STARTING_BALANCE_EDEFAULT = 0L;

	/**
	 * The cached value of the '{@link #getStartingBalance() <em>Starting Balance</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStartingBalance()
	 * @generated
	 * @ordered
	 */
	protected long startingBalance = STARTING_BALANCE_EDEFAULT;

	/**
	 * The default value of the '{@link #getCreditLimit() <em>Credit Limit</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCreditLimit()
	 * @generated
	 * @ordered
	 */
	protected static final long CREDIT_LIMIT_EDEFAULT = 0L;

	/**
	 * The cached value of the '{@link #getCreditLimit() <em>Credit Limit</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCreditLimit()
	 * @generated
	 * @ordered
	 */
	protected long creditLimit = CREDIT_LIMIT_EDEFAULT;

	/**
	 * The default value of the '{@link #getInterestRate() <em>Interest Rate</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInterestRate()
	 * @generated
	 * @ordered
	 */
	protected static final long INTEREST_RATE_EDEFAULT = 0L;

	/**
	 * The cached value of the '{@link #getInterestRate() <em>Interest Rate</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInterestRate()
	 * @generated
	 * @ordered
	 */
	protected long interestRate = INTEREST_RATE_EDEFAULT;

	/**
	 * The default value of the '{@link #getDueDate() <em>Due Date</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDueDate()
	 * @generated
	 * @ordered
	 */
	protected static final Date DUE_DATE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getDueDate() <em>Due Date</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDueDate()
	 * @generated
	 * @ordered
	 */
	protected Date dueDate = DUE_DATE_EDEFAULT;

	/**
	 * The cached value of the '{@link #getAccountType() <em>Account Type</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAccountType()
	 * @generated
	 * @ordered
	 */
	protected Type accountType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected AccountImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return ModelPackage.Literals.ACCOUNT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public long getBalance() {
		return balance;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setBalance(long newBalance) {
		long oldBalance = balance;
		balance = newBalance;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.ACCOUNT__BALANCE, oldBalance, balance));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public long getStartingBalance() {
		return startingBalance;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setStartingBalance(long newStartingBalance) {
		long oldStartingBalance = startingBalance;
		startingBalance = newStartingBalance;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.ACCOUNT__STARTING_BALANCE, oldStartingBalance, startingBalance));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public long getCreditLimit() {
		return creditLimit;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCreditLimit(long newCreditLimit) {
		long oldCreditLimit = creditLimit;
		creditLimit = newCreditLimit;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.ACCOUNT__CREDIT_LIMIT, oldCreditLimit, creditLimit));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public long getInterestRate() {
		return interestRate;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setInterestRate(long newInterestRate) {
		long oldInterestRate = interestRate;
		interestRate = newInterestRate;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.ACCOUNT__INTEREST_RATE, oldInterestRate, interestRate));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Date getDueDate() {
		return dueDate;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDueDate(Date newDueDate) {
		Date oldDueDate = dueDate;
		dueDate = newDueDate;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.ACCOUNT__DUE_DATE, oldDueDate, dueDate));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Type getAccountType() {
		if (accountType != null && accountType.eIsProxy()) {
			InternalEObject oldAccountType = (InternalEObject)accountType;
			accountType = (Type)eResolveProxy(oldAccountType);
			if (accountType != oldAccountType) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, ModelPackage.ACCOUNT__ACCOUNT_TYPE, oldAccountType, accountType));
			}
		}
		return accountType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Type basicGetAccountType() {
		return accountType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAccountType(Type newAccountType) {
		Type oldAccountType = accountType;
		accountType = newAccountType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.ACCOUNT__ACCOUNT_TYPE, oldAccountType, accountType));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ModelPackage.ACCOUNT__BALANCE:
				return new Long(getBalance());
			case ModelPackage.ACCOUNT__STARTING_BALANCE:
				return new Long(getStartingBalance());
			case ModelPackage.ACCOUNT__CREDIT_LIMIT:
				return new Long(getCreditLimit());
			case ModelPackage.ACCOUNT__INTEREST_RATE:
				return new Long(getInterestRate());
			case ModelPackage.ACCOUNT__DUE_DATE:
				return getDueDate();
			case ModelPackage.ACCOUNT__ACCOUNT_TYPE:
				if (resolve) return getAccountType();
				return basicGetAccountType();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case ModelPackage.ACCOUNT__BALANCE:
				setBalance(((Long)newValue).longValue());
				return;
			case ModelPackage.ACCOUNT__STARTING_BALANCE:
				setStartingBalance(((Long)newValue).longValue());
				return;
			case ModelPackage.ACCOUNT__CREDIT_LIMIT:
				setCreditLimit(((Long)newValue).longValue());
				return;
			case ModelPackage.ACCOUNT__INTEREST_RATE:
				setInterestRate(((Long)newValue).longValue());
				return;
			case ModelPackage.ACCOUNT__DUE_DATE:
				setDueDate((Date)newValue);
				return;
			case ModelPackage.ACCOUNT__ACCOUNT_TYPE:
				setAccountType((Type)newValue);
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
			case ModelPackage.ACCOUNT__BALANCE:
				setBalance(BALANCE_EDEFAULT);
				return;
			case ModelPackage.ACCOUNT__STARTING_BALANCE:
				setStartingBalance(STARTING_BALANCE_EDEFAULT);
				return;
			case ModelPackage.ACCOUNT__CREDIT_LIMIT:
				setCreditLimit(CREDIT_LIMIT_EDEFAULT);
				return;
			case ModelPackage.ACCOUNT__INTEREST_RATE:
				setInterestRate(INTEREST_RATE_EDEFAULT);
				return;
			case ModelPackage.ACCOUNT__DUE_DATE:
				setDueDate(DUE_DATE_EDEFAULT);
				return;
			case ModelPackage.ACCOUNT__ACCOUNT_TYPE:
				setAccountType((Type)null);
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
			case ModelPackage.ACCOUNT__BALANCE:
				return balance != BALANCE_EDEFAULT;
			case ModelPackage.ACCOUNT__STARTING_BALANCE:
				return startingBalance != STARTING_BALANCE_EDEFAULT;
			case ModelPackage.ACCOUNT__CREDIT_LIMIT:
				return creditLimit != CREDIT_LIMIT_EDEFAULT;
			case ModelPackage.ACCOUNT__INTEREST_RATE:
				return interestRate != INTEREST_RATE_EDEFAULT;
			case ModelPackage.ACCOUNT__DUE_DATE:
				return DUE_DATE_EDEFAULT == null ? dueDate != null : !DUE_DATE_EDEFAULT.equals(dueDate);
			case ModelPackage.ACCOUNT__ACCOUNT_TYPE:
				return accountType != null;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 */
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer();
//		result.append("[ ");
		result.append(name);
		result.append(" (");
		result.append(accountType);
		result.append(")");
//		result.append(") ]");

		return result.toString();
	}
	
//	public String toStringLong() {
//		if (eIsProxy()) return super.toString();
//
//		StringBuffer result = new StringBuffer(name);
//		result.append(" (");
//		result.append(accountType);
//		result.append("): ");
//		if ((isCredit() ^ balance <= 0) && balance != 0)
//			result.append("-");
//		result.append(Translate.getFormattedCurrency(balance, getAccountType().isCredit()));
//		return result.toString();
//	}

	
	public void calculateBalance(){
		long balance = this.getStartingBalance();
//		long balance = 0;
		
		Vector<Transaction> transactions = TransactionController.getTransactions(this);
		
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
	
	public boolean isCredit(){
		if (getAccountType() != null)
			return getAccountType().isCredit();
		else
//			Log.critical("null type for account " + this.toStringLong());
			return false;
	}
	
	public int compareTo(Source arg0) {
		if (arg0 instanceof Account){
			Account a = (Account) arg0;
			if (this.isCredit() != a.isCredit()){
				return new Boolean(this.isCredit()).compareTo(new Boolean(a.isCredit()));
			}
		}
		return super.compareTo(arg0);
	}

	
} //AccountImpl
