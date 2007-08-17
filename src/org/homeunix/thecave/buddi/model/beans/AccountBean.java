/*
 * Created on Jul 29, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.model.beans;

public class AccountBean extends SourceBean {
	private long startingBalance;
	private long balance;
	private TypeBean type;
	
	public long getStartingBalance() {
		return startingBalance;
	}
	public void setStartingBalance(long startingBalance) {
		this.startingBalance = startingBalance;
	}
	public long getBalance() {
		return balance;
	}
	public void setBalance(long balance) {
		this.balance = balance;
	}
	public TypeBean getType() {
		return type;
	}
	public void setType(TypeBean type) {
		this.type = type;
	}	
}
