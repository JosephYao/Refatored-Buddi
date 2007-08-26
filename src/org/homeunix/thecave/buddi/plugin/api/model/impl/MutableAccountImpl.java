/*
 * Created on Aug 23, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.plugin.api.model.impl;

import org.homeunix.thecave.buddi.model.Account;
import org.homeunix.thecave.buddi.plugin.api.model.ImmutableType;
import org.homeunix.thecave.buddi.plugin.api.model.MutableAccount;
import org.homeunix.thecave.buddi.plugin.api.model.MutableType;

public class MutableAccountImpl extends MutableSourceImpl implements MutableAccount {

	public MutableAccountImpl(Account account) {
		super(account);
	}
	
	public MutableAccountImpl() {
		super(new Account(getmodel, name, startingBalance, type));
	}

	public void setStartingBalance(long startingBalance) {
		getAccount().setStartingBalance(startingBalance);
	}

	public void setType(MutableType type) {
		getAccount().setType(type.getType());
	}

	public Account getAccount() {
		return (Account) getSource();
	}

	public long getBalance() {
		return getAccount().getBalance();
	}

	public long getStartingBalance() {
		return getAccount().getStartingBalance();
	}

	public ImmutableType getType() {
		if (getAccount().getType() != null)
			return new ImmutableTypeImpl(getAccount().getType());
		return null;
	}
}
