/*
 * Created on Aug 26, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.plugin.api.model;

import java.util.Date;

import org.homeunix.thecave.buddi.i18n.keys.BudgetPeriodType;
import org.homeunix.thecave.buddi.model.Account;
import org.homeunix.thecave.buddi.model.BudgetCategory;
import org.homeunix.thecave.buddi.model.Transaction;
import org.homeunix.thecave.buddi.model.Type;
import org.homeunix.thecave.buddi.plugin.api.model.impl.MutableAccountImpl;
import org.homeunix.thecave.buddi.plugin.api.model.impl.MutableBudgetCategoryImpl;
import org.homeunix.thecave.buddi.plugin.api.model.impl.MutableTransactionImpl;
import org.homeunix.thecave.buddi.plugin.api.model.impl.MutableTypeImpl;

public class ModelFactory {
	public static MutableAccount createMutableAccount(MutableModel model, String name, long startingBalance, MutableType type){
		Account a = new Account(model.getModel(), name, startingBalance, type.getType());

		return new MutableAccountImpl(a);
	}
	
	public static MutableBudgetCategory createMutableBudgetCategory(MutableModel model, String name, BudgetPeriodType periodType, boolean isIncome){
		BudgetCategory bc = new BudgetCategory(model.getModel(), name, periodType, isIncome);
		
		return new MutableBudgetCategoryImpl(bc);
	}
	
	public static MutableTransaction createMutableTransaction(MutableModel model, Date date, String description, long amount, MutableSource from, MutableSource to){
		Transaction t = new Transaction(model.getModel(), date, description, amount, from.getSource(), to.getSource());
		
		return new MutableTransactionImpl(t);
	}
	
	public static MutableType createMutableType(MutableModel model, String name, boolean credit){
		Type t = new Type(model.getModel(), name, credit);
		
		return new MutableTypeImpl(t);
	}
}
