/*
 * Created on Aug 22, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.plugin.api.model;

import org.homeunix.thecave.buddi.plugin.api.exception.ModelException;

public interface MutableModel extends ImmutableModel {

	/**
	 * Adds an account to the model
	 * @param account
	 */
	public void addAccount(MutableAccount account) throws ModelException;
	
	/**
	 * Adds a type to the model
	 * @param type
	 */
	public void addAccountType(MutableAccountType type) throws ModelException;
	
	/**
	 * Adds a budget category to the model
	 * @param budgetCategory
	 */
	public void addBudgetCategory(MutableBudgetCategory budgetCategory) throws ModelException;
	
	/**
	 * Adds a scheduled transaction to the model
	 * @param scheduledTransaction
	 */
	public void addScheduledTransaction(MutableScheduledTransaction scheduledTransaction) throws ModelException;
	
	/**
	 * Adds a transaction to the model
	 * @param transaction
	 */
	public void addTransaction(MutableTransaction transaction) throws ModelException;
	
	/**
	 * Removes the given account from the model
	 * @param account
	 */
	public void removeAccount(MutableAccount account) throws ModelException;
	
	/**
	 * Removes the given budget category from the model
	 * @param budgetCategory
	 */
	public void removeBudgetCategory(MutableBudgetCategory budgetCategory) throws ModelException;
	
	/**
	 * Removes the given scheduled transaction from the model
	 * @param scheduledTransaction
	 */
	public void removeScheduledTransaction(MutableScheduledTransaction scheduledTransaction) throws ModelException;
	
	/**
	 * Removes the given transaction from the model
	 * @param transaction
	 */
	public void removeTransaction(MutableTransaction transaction) throws ModelException;
	
	/**
	 * Removes the given type from the model
	 * @param type
	 */
	public void removeType(MutableAccountType type) throws ModelException;
}
