/*
 * Created on Aug 22, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.plugin.api.model;

public interface MutableModel extends ImmutableModel {

	/**
	 * Adds an account to the model
	 * @param account
	 */
	public void addAccount(MutableAccount account);
	
	/**
	 * Adds a budget category to the model
	 * @param budgetCategory
	 */
	public void addBudgetCategory(MutableBudgetCategory budgetCategory);
	
	/**
	 * Adds a transaction to the model
	 * @param transaction
	 */
	public void addTransaction(MutableTransaction transaction);
	
	/**
	 * Adds a type to the model
	 * @param type
	 */
	public void addType(MutableType type);
	
	/**
	 * Removes the given account from the model
	 * @param account
	 */
	public void removeAccount(MutableAccount account);
	
	/**
	 * Removes the given budget category from the model
	 * @param budgetCategory
	 */
	public void removeBudgetCategory(MutableBudgetCategory budgetCategory);
	
	/**
	 * Removes the given transaction from the model
	 * @param transaction
	 */
	public void removeTransaction(MutableTransaction transaction);
	
	/**
	 * Removes the given type from the model
	 * @param type
	 */
	public void removeType(MutableType type);
}
