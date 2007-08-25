/*
 * Created on Jul 30, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.model;

import java.util.List;

import org.homeunix.thecave.buddi.model.beans.AccountBean;
import org.homeunix.thecave.buddi.model.exception.DataModelProblemException;

public class Account extends Source{
	
	Account(DataModel model, AccountBean account) throws DataModelProblemException {
		super(model, account);
	}
	
	public Account(DataModel model, String name, long startingBalance, Type type) throws DataModelProblemException {
		this(model, new AccountBean());

		this.setStartingBalance(startingBalance);
		this.setName(name);
		this.setType(type);
	}
	
	public long getBalance(){
		return getAccount().getBalance();
	}
	public void updateBalance(){
		getModel().startBatchChange();
		
		
		long balance = this.getStartingBalance();
		
		List<Transaction> transactions = getModel().getTransactions(this);
		
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
		getModel().finishBatchChange();
	}
	public long getStartingBalance() {
		return getAccount().getStartingBalance();
	}
	public void setStartingBalance(Long startingBalance) {
		getAccount().setStartingBalance(startingBalance);
		getModel().setChanged();
	}
	public Type getType() {
		return new Type(getModel(), getAccount().getType());
	}
	public void setType(Type type) {
		if (type == null)
			throw new DataModelProblemException("Type cannot be null", getModel());
		if (!getModel().getTypes().contains(type))
			throw new DataModelProblemException("Type must be entered in model", getModel());

		getAccount().setType(type.getTypeBean());
//		getModel().setChanged();
	}
	
	@Override
	public int compareTo(ModelObject arg0) {
		if (arg0 instanceof Account){
			Account a = (Account) arg0;
			if (this.getType().isCredit() != a.getType().isCredit()){
				if (this.getType().isCredit())
					return -1;
				return 1;
			}
		}
		return super.compareTo(arg0);
	}
	
	
	AccountBean getAccountBean(){
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
