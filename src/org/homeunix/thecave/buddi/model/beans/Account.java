/*
 * Created on Jul 29, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.model.beans;

import java.util.Date;

public class Account extends Source {
	private Long balance;
	private Long startingBalance;
//	private Long creditLimit;
//	private Long interestRate;
//	private Type type;
	
	
	
	
	public Long getBalance() {
		return balance;
	}
	public void setBalance(Long balance) {
		this.setModifiedDate(new Date());
		this.balance = balance;
	}
//	public Long getCreditLimit() {
//		return creditLimit;
//	}
//	public void setCreditLimit(Long creditLimit) {
//		this.setModifiedDate(new Date());
//		this.creditLimit = creditLimit;
//	}
//	public Long getInterestRate() {
//		return interestRate;
//	}
//	public void setInterestRate(Long interestRate) {
//		this.setModifiedDate(new Date());
//		this.interestRate = interestRate;
//	}
	public Long getStartingBalance() {
		return startingBalance;
	}
	public void setStartingBalance(Long startingBalance) {
		this.setModifiedDate(new Date());
		this.startingBalance = startingBalance;
	}
//	public Type getType() {
//		return type;
//	}
//	public void setType(Type type) {
//		this.setModifiedDate(new Date());
//		this.type = type;
//	}
}
