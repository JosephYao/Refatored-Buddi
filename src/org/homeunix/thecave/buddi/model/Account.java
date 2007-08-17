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
		List<Transaction> transactions = new FilteredLists.FilteredTransactionList(getModel(), this);
		
		long balance = getStartingBalance();

		for (Transaction transaction : transactions) {
			if (transaction.isInflow())
				balance += transaction.getAmount();
			else
				balance -= transaction.getAmount();
			
			if (transaction.getFrom().equals(this))
				transaction.setBalanceFrom(balance);
			else if (transaction.getTo().equals(this))
				transaction.setBalanceTo(balance);
			else {
				//TODO Get better checks here...
				System.out.println(getModel());
				throw new RuntimeException("Neither To nor From are this account.  Something is wrong!");
			}
		}

		getAccount().setBalance(balance);
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
		getModel().setChanged();
	}
	
	
	public int compareTo(Source arg0) {
		if (arg0 instanceof Account){
			Account a = (Account) arg0;
			if (this.getType().isCredit() != a.getType().isCredit()){
				return new Boolean(this.getType().isCredit()).compareTo(new Boolean(a.getType().isCredit()));
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
