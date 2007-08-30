/*
 * Created on Jul 30, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.model;

import org.homeunix.thecave.buddi.model.exception.InvalidValueException;

public interface Account extends Source{
	
	public long getBalance();
	public long getStartingBalance();
	public AccountType getType();
	public void setStartingBalance(long startingBalance) throws InvalidValueException;
	public void setType(AccountType type) throws InvalidValueException;
	public void updateBalance();
}
