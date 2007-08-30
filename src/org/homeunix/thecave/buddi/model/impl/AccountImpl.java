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
			return getDocument().getTransactions(this).get(0).getDate();
		return null;
	}
	public long getBalance() {
		return balance;
	}
	public void setBalance(long balance) {
		this.balance = balance;
	}
	public AccountType getType() {
		return type;
	}
	public void setType(AccountType type) {
		this.type = type;
	}
	public void updateBalance(){
		if (getDocument() == null)
			return;
		
		long balance = this.getStartingBalance();
		
		List<Transaction> transactions = getDocument().getTransactions(this);
		
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
		
		setBalance(balance);
	}
	public String getFullName() {
		return this.getName() + " (" + getType().getName() + ")";
	}
	@Override
	public int compareTo(ModelObject arg0) {
		if (arg0 instanceof AccountImpl){
			AccountImpl a = (AccountImpl) arg0;
			if (this.getType().isCredit() != a.getType().isCredit()){
				if (this.getType().isCredit())
					return -1;
				return 1;
			}
		}
		return super.compareTo(arg0);
	}
}
