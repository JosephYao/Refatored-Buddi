/*
 * Created on Aug 23, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.plugin.api.model.impl;

import org.homeunix.thecave.buddi.model.Document;
import org.homeunix.thecave.buddi.plugin.api.exception.ModelException;
import org.homeunix.thecave.buddi.plugin.api.model.MutableAccount;
import org.homeunix.thecave.buddi.plugin.api.model.MutableAccountType;
import org.homeunix.thecave.buddi.plugin.api.model.MutableBudgetCategory;
import org.homeunix.thecave.buddi.plugin.api.model.MutableModel;
import org.homeunix.thecave.buddi.plugin.api.model.MutableScheduledTransaction;
import org.homeunix.thecave.buddi.plugin.api.model.MutableTransaction;

public class MutableModelImpl extends ImmutableModelImpl implements MutableModel {

	public MutableModelImpl(Document model) {
		super(model);
	}

	public void addAccount(MutableAccount account) throws ModelException{
		getModel().addAccount(account.getAccount());
	}

	public void addBudgetCategory(MutableBudgetCategory budgetCategory) throws ModelException{
		getModel().addBudgetCategory(budgetCategory.getBudgetCategory());
	}

	public void addScheduledTransaction(MutableScheduledTransaction scheduledTransaction) throws ModelException{
		getModel().addScheduledTransaction(scheduledTransaction.getScheduledTransaction());		
	}

	public void addTransaction(MutableTransaction transaction) throws ModelException{
		getModel().addTransaction(transaction.getTransaction());
	}

	public void addAccountType(MutableAccountType type) throws ModelException{
		getModel().addAccountType(type.getType());
	}

	public void removeAccount(MutableAccount account) throws ModelException{
		getModel().removeAccount(account.getAccount());
	}

	public void removeBudgetCategory(MutableBudgetCategory budgetCategory) throws ModelException{
		getModel().removeBudgetCategory(budgetCategory.getBudgetCategory());
	}

	public void removeScheduledTransaction(MutableScheduledTransaction scheduledTransaction) throws ModelException{
		getModel().removeScheduledTransaction(scheduledTransaction.getScheduledTransaction());		
	}

	public void removeTransaction(MutableTransaction transaction) throws ModelException{
		getModel().removeTransaction(transaction.getTransaction());
	}

	public void removeType(MutableAccountType type) throws ModelException{
		getModel().removeAccountType(type.getType());
	}
}
