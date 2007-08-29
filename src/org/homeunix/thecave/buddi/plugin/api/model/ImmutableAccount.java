/*
 * Created on Aug 12, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.plugin.api.model;

import org.homeunix.thecave.buddi.model.Account;

public interface ImmutableAccount extends ImmutableSource {
		
	/**
	 * Returns the starting balance for the account.
	 * @return
	 */
	public long getStartingBalance();
	
	/**
	 * Returns the current balance of the account. 
	 * @return
	 */
	public long getBalance();
	
	/**
	 * Returns the Type object associated with the account.
	 * @return
	 */
	public ImmutableAccountType getType();
	
	/**
	 * Returns the wrapped object from the underlying data model.  By 
	 * accessing this method, you bypass all protection which the Buddi API
	 * gives you; it is not recommended to use this method unless you understand
	 * the risks associated with it. 
	 * @return
	 */
	public Account getAccount();
}
