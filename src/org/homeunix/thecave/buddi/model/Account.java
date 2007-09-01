/*
 * Created on Jul 30, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.model;

import java.util.Date;

import org.homeunix.thecave.buddi.plugin.api.exception.InvalidValueException;

public interface Account extends Source{
	
	public AccountType getAccountType();
	public long getBalance();
	public Date getStartDate();
	public long getStartingBalance();
	public void setAccountType(AccountType accountType) throws InvalidValueException;
	public void setStartingBalance(long startingBalance) throws InvalidValueException;
	public void updateBalance();
}
