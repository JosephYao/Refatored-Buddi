/*
 * Created on Aug 23, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.plugin.api.model.impl;

import org.homeunix.thecave.buddi.model.Account;
import org.homeunix.thecave.buddi.plugin.api.model.MutableAccount;

public abstract class MutableAccountImpl extends MutableSourceImpl implements MutableAccount {

	public MutableAccountImpl(Account account) {
		super(account);
	}
}
