/*
 * Created on Jul 29, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.model.impl;

import java.util.Date;
import java.util.List;

import org.homeunix.thecave.buddi.model.Account;
import org.homeunix.thecave.buddi.model.AccountType;
import org.homeunix.thecave.buddi.model.ModelObject;
import org.homeunix.thecave.buddi.model.Transaction;
import org.homeunix.thecave.buddi.plugin.api.exception.InvalidValueException;
import org.homeunix.thecave.moss.util.Log;

/**
 * Default implementation of an Account.  You should not create this object directly; 
 * instead, please use the ModelFactory to create it, as this will ensure that all
 * required fields are correctly set.
 * @author wyatt
 *
 */
public class AccountImpl extends SourceImpl implements Account {
	private long startingBalance;
	private long balance;
	private AccountType type;
	
	public long getStartingBalance() {
		return startingBalance;
	}
	public void setStartingBalance(long startingBalance) {
		this.startingBalance = startingBalance;
	}
	public Date getStartDate() {
		if (getDocument() != null)
//			return getDocument().getTransactions(this).get(0).getDate();
			return new Date();
		return null;
	}
	public long getBalance() {
		return balance;
	}
	public void setBalance(long balance) {
		this.balance = balance;
	}
	public AccountType getAccountType() {
		return type;
	}
	public void setAccountType(AccountType type) {
		this.type = type;
	}
	public void updateBalance(){
		if (getDocument() == null)
			return;

		long balance = this.getStartingBalance();

		List<Transaction> transactions = getDocument().getTransactions(this);

		for (Transaction transaction : transactions) {
			try {
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
			catch (InvalidValueException ive){
				Log.error(ive);
			}
		}

		setBalance(balance);
	}
	public String getFullName() {
		return this.getName() + " (" + getAccountType().getName() + ")";
	}
	@Override
	public int compareTo(ModelObject arg0) {
		if (arg0 instanceof AccountImpl){
			AccountImpl a = (AccountImpl) arg0;
			if (this.getAccountType().isCredit() != a.getAccountType().isCredit()){
				if (this.getAccountType().isCredit())
					return -1;
				return 1;
			}
		}
		return super.compareTo(arg0);
	}
}
