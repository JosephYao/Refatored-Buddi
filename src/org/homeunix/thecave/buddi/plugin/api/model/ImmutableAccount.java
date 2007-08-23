/*
 * Created on Aug 12, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.plugin.api.model;

import org.homeunix.thecave.buddi.model.Account;

public interface ImmutableAccount extends ImmutableSource {
		
	public long getStartingBalance();
	
	public long getBalance();
	
	public ImmutableType getType();
	
	public Account getAccount();
}
