/*
 * Created on Aug 12, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.plugin.api.model.impl;

import org.homeunix.thecave.buddi.model.Account;

public class ImmutableAccount extends ImmutableSource {
	
	public ImmutableAccount(Account account) {
		super(account);
	}
	
	public long getStartingBalance() {
		return getAccount().getStartingBalance();
	}
	public long getBalance() {
		return getAccount().getBalance();
	}
	public ImmutableType getType() {
		return new ImmutableType(getAccount().getType());
	}
	public Account getAccount(){
		return (Account) getRaw(); 
	}
}
