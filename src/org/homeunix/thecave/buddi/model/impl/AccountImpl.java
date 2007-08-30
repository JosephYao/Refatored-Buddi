/*
 * Created on Jul 30, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.model.impl;

import java.util.List;

import org.homeunix.thecave.buddi.model.Account;
import org.homeunix.thecave.buddi.model.AccountType;
import org.homeunix.thecave.buddi.model.ModelObject;
import org.homeunix.thecave.buddi.model.Transaction;
import org.homeunix.thecave.buddi.model.beans.AccountBean;
import org.homeunix.thecave.buddi.model.exception.InvalidValueException;
import org.homeunix.thecave.buddi.model.exception.ModelException;

public class AccountImpl extends SourceImpl implements Account {
	
	AccountImpl(AccountBean account) throws InvalidValueException {
		super(account);
	}
	
	public long getBalance(){
		return getAccount().getBalance();
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
		
		getAccount().setBalance(balance);
	}
	public long getStartingBalance() {
		return getAccount().getStartingBalance();
	}
	public void setStartingBalance(long startingBalance) {
		getAccount().setStartingBalance(startingBalance);
	}
	public AccountType getType() {
		try {
			return new AccountTypeImpl(getAccount().getType());
		}
		catch (ModelException me){
			return null;
		}
	}
	public void setType(AccountType type) {
		getAccount().setType(type.getTypeBean());
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
	
	
	public AccountBean getAccountBean(){
		return getAccount();
	}
	
	private AccountBean getAccount(){
		return (AccountBean) getSourceBean(); 
	}
	
	@Override
	public String getFullName() {
		return this.getName() + " (" + getType().getName() + ")";
	}
}
