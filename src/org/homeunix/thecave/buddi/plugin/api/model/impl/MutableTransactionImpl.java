/*
 * Created on Aug 23, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.plugin.api.model.impl;

import org.homeunix.thecave.buddi.model.Transaction;
import org.homeunix.thecave.buddi.plugin.api.model.MutableTransaction;

public abstract class MutableTransactionImpl extends ImmutableTransactionImpl implements MutableTransaction {

	public MutableTransactionImpl(Transaction transaction) {
		super(transaction);
	}
}
