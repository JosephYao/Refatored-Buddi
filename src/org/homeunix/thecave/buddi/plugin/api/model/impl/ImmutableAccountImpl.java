/*
 * Created on Aug 12, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.plugin.api.model.impl;

import org.homeunix.thecave.buddi.model.Account;
import org.homeunix.thecave.buddi.plugin.api.model.ImmutableAccount;
import org.homeunix.thecave.buddi.plugin.api.model.ImmutableType;

public class ImmutableAccountImpl extends ImmutableSourceImpl implements ImmutableAccount {
	
	public ImmutableAccountImpl(Account account) {
		super(account);
	}
	
	public long getStartingBalance() {
		return getAccount().getStartingBalance();
	}
	public long getBalance() {
		return getAccount().getBalance();
	}
	public ImmutableType getType() {
		return new MutableTypeImpl(getAccount().getType());
	}
	public Account getAccount(){
		return (Account) getRaw(); 
	}
}
