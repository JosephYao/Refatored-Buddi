/*
 * Created on Aug 22, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.plugin.api.model;

import org.homeunix.thecave.buddi.model.api.exception.InvalidValueException;

public interface MutableAccount extends ImmutableAccount, MutableSource {
		
	/**
	 * Sets the starting balance for this account
	 * @param startingBalance
	 */
	public void setStartingBalance(long startingBalance) throws InvalidValueException;
	
	/**
	 * Sets the type for this account
	 * @param type
	 */
	public void setType(MutableAccountType type) throws InvalidValueException;
}
