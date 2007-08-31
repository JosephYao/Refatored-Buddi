/*
 * Created on Aug 26, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.plugin.api.model;

import java.util.Date;

import org.homeunix.thecave.buddi.model.Account;
import org.homeunix.thecave.buddi.model.AccountType;
import org.homeunix.thecave.buddi.model.BudgetCategory;
import org.homeunix.thecave.buddi.model.BudgetCategoryType;
import org.homeunix.thecave.buddi.model.ScheduledTransaction;
import org.homeunix.thecave.buddi.model.Transaction;
import org.homeunix.thecave.buddi.model.api.exception.ModelException;
import org.homeunix.thecave.buddi.model.impl.ModelFactory;
import org.homeunix.thecave.buddi.plugin.api.model.impl.MutableAccountImpl;
import org.homeunix.thecave.buddi.plugin.api.model.impl.MutableBudgetCategoryImpl;
import org.homeunix.thecave.buddi.plugin.api.model.impl.MutableScheduledTransactionImpl;
import org.homeunix.thecave.buddi.plugin.api.model.impl.MutableTransactionImpl;
import org.homeunix.thecave.buddi.plugin.api.model.impl.MutableTypeImpl;

public class MutableModelFactory {
	public static MutableAccount createMutableAccount(MutableModel model, String name, long startingBalance, MutableAccountType type)  throws ModelException {
		Account a = ModelFactory.createAccount(name, type.getType());

		return new MutableAccountImpl(a);
	}
	
	public static MutableBudgetCategory createMutableBudgetCategory(MutableModel model, String name, BudgetCategoryType periodType, boolean isIncome) throws ModelException {
		BudgetCategory bc = ModelFactory.createBudgetCategory(name, periodType, isIncome);
		
		return new MutableBudgetCategoryImpl(bc);
	}
	
	public static MutableTransaction createMutableTransaction(MutableModel model, Date date, String description, long amount, MutableSource from, MutableSource to) throws ModelException {
		Transaction t = ModelFactory.createTransaction(date, description, amount, from.getSource(), to.getSource());
		
		return new MutableTransactionImpl(t);
	}
	
	public static MutableAccountType createMutableType(MutableModel model, String name, boolean credit) throws ModelException {
		AccountType t = ModelFactory.createAccountType(name, credit);
		
		return new MutableTypeImpl(t);
	}
	
	public static MutableScheduledTransaction createMutableScheduledTransaction(MutableModel model) throws ModelException{
		ScheduledTransaction st = ModelFactory.createScheduledTransaction();
		
		return new MutableScheduledTransactionImpl(st);
	}
}
