/*
 * Created on Aug 23, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.plugin.api.model.impl;

import java.util.Date;

import org.homeunix.thecave.buddi.model.Account;
import org.homeunix.thecave.buddi.model.exception.InvalidValueException;
import org.homeunix.thecave.buddi.plugin.api.model.ImmutableAccountType;
import org.homeunix.thecave.buddi.plugin.api.model.MutableAccount;
import org.homeunix.thecave.buddi.plugin.api.model.MutableAccountType;

public class MutableAccountImpl extends MutableSourceImpl implements MutableAccount {

	public MutableAccountImpl(Account account) {
		super(account);
	}
//	
//	public MutableAccountImpl(String name, long startingBalance, MutableType type) {
//		super(new Account(type.getType().getModel(), name, startingBalance, type.getType()));
//	}

	public void setStartingBalance(long startingBalance) throws InvalidValueException {
		getAccount().setStartingBalance(startingBalance);
	}

	public void setType(MutableAccountType type) throws InvalidValueException {
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

	public ImmutableAccountType getType() {
		if (getAccount().getType() != null)
			return new ImmutableTypeImpl(getAccount().getType());
		return null;
	}
	public Date getStartDate() {
		return getAccount().getStartDate();
	}
}
