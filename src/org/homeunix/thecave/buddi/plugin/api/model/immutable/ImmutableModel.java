/*
 * Created on Aug 12, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.plugin.api.model.immutable;

import java.util.List;

import org.homeunix.thecave.buddi.i18n.keys.BudgetPeriodKeys;
import org.homeunix.thecave.buddi.model.Account;
import org.homeunix.thecave.buddi.model.BudgetCategory;
import org.homeunix.thecave.buddi.model.DataModel;
import org.homeunix.thecave.buddi.model.Source;
import org.homeunix.thecave.buddi.model.Transaction;
import org.homeunix.thecave.buddi.model.Type;
import org.homeunix.thecave.buddi.model.WrapperLists;

public class ImmutableModel {
	private final DataModel model;
	
	public ImmutableModel(DataModel model) {
		this.model = model;
	}
	
	
	public BudgetPeriodKeys getPeriodType(){
		return model.getPeriodType();
	}
	
	
	public List<ImmutableAccount> getAccounts(){
		return new WrapperLists.ImmutableObjectWrapperList<ImmutableAccount, Account>(model, model.getAccounts());
	}
	
	public List<ImmutableBudgetCategory> getBudgetCategories(){
		return new WrapperLists.ImmutableObjectWrapperList<ImmutableBudgetCategory, BudgetCategory>(model, model.getBudgetCategories());
	}
	
	public List<ImmutableType> getTypes(){
		return new WrapperLists.ImmutableObjectWrapperList<ImmutableType, Type>(model, model.getTypes());		
	}
	
	public List<ImmutableTransaction> getTransactions(){
		return new WrapperLists.ImmutableObjectWrapperList<ImmutableTransaction, Transaction>(model, model.getTransactions());
	}
	
	public List<ImmutableTransaction> getTransactions(ImmutableSource associatedSource){
		return new WrapperLists.ImmutableObjectWrapperList<ImmutableTransaction, Transaction>(model, model.getTransactions((Source) associatedSource.getRaw()));
	}
}
