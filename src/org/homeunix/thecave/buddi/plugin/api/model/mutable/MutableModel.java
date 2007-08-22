/*
 * Created on Aug 12, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.plugin.api.model.mutable;

import org.homeunix.thecave.buddi.model.DataModel;
import org.homeunix.thecave.buddi.plugin.api.model.immutable.ImmutableModel;

public class MutableModel extends ImmutableModel {
	
	public MutableModel(DataModel model) {
		super(model);
	}

	public void addAccount(MutableAccount account){
		getModel().addAccount(account.getAccount());
	}
	
	public void addBudgetCategory(MutableBudgetCategory budgetCategory){
		getModel().addBudgetCategory(budgetCategory.getBudgetCategory());
	}
	
	public void addTransaction(MutableTransaction transaction){
		getModel().addTransaction(transaction.getTransaction());
	}
	
	public void addType(MutableType type){
		getModel().addType(type.getType());
	}
	
	
	
	
	public void removeAccount(MutableAccount account){
		getModel().removeAccount(account.getAccount());
	}
	
	public void removeBudgetCategory(MutableBudgetCategory budgetCategory){
		getModel().removeBudgetCategory(budgetCategory.getBudgetCategory());
	}
	
	public void removeTransaction(MutableTransaction transaction){
		getModel().removeTransaction(transaction.getTransaction());
	}
	
	public void removeType(MutableType type){
		getModel().removeType(type.getType());
	}
	
	
	
	
	public void undeleteAccount(MutableAccount account){
		getModel().undeleteAccount(account.getAccount());
	}
	
	public void undeleteBudgetCategory(MutableBudgetCategory budgetCategory){
		getModel().undeleteBudgetCategory(budgetCategory.getBudgetCategory());
	}

}
