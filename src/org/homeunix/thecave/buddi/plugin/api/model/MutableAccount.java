/*
 * Created on Aug 22, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.plugin.api.model;

public interface MutableAccount extends ImmutableAccount, MutableSource {
	
	public void addAccount(MutableAccount account);
	public void addBudgetCategory(MutableBudgetCategory budgetCategory);
	public void addTransaction(MutableTransaction transaction);
	public void addType(MutableType type);
	public void removeAccount(MutableAccount account);
	public void removeBudgetCategory(MutableBudgetCategory budgetCategory);
	public void removeTransaction(MutableTransaction transaction);
	public void removeType(MutableType type);
}
