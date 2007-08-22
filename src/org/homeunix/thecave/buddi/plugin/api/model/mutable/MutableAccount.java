/*
 * Created on Aug 12, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.plugin.api.model.mutable;

import org.homeunix.thecave.buddi.model.Account;

public class MutableAccount extends MutableSource {
	
	public MutableAccount(Account account) {
		super(account);
	}
	
	public long getStartingBalance() {
		return getAccount().getStartingBalance();
	}
	public long getBalance() {
		return getAccount().getBalance();
	}
	public MutableType getType() {
		return new MutableType(getAccount().getType());
	}
	public Account getAccount(){
		return (Account) getRaw(); 
	}
}
