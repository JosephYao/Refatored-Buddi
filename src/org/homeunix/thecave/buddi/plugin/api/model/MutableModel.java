/*
 * Created on Aug 22, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.plugin.api.model;

import org.homeunix.thecave.buddi.plugin.api.exception.InvalidActionException;

public interface MutableModel extends ImmutableModel {

	/**
	 * Adds an account to the model
	 * @param account
	 */
	public void addAccount(MutableAccount account) throws InvalidActionException;
	
	/**
	 * Adds a budget category to the model
	 * @param budgetCategory
	 */
	public void addBudgetCategory(MutableBudgetCategory budgetCategory) throws InvalidActionException;
	
	/**
	 * Adds a scheduled transaction to the model
	 * @param scheduledTransaction
	 */
	public void addScheduledTransaction(MutableScheduledTransaction scheduledTransaction) throws InvalidActionException;
	
	/**
	 * Adds a transaction to the model
	 * @param transaction
	 */
	public void addTransaction(MutableTransaction transaction) throws InvalidActionException;
	
	/**
	 * Adds a type to the model
	 * @param type
	 */
	public void addType(MutableAccountType type) throws InvalidActionException;
	
	/**
	 * Removes the given account from the model
	 * @param account
	 */
	public void removeAccount(MutableAccount account) throws InvalidActionException;
	
	/**
	 * Removes the given budget category from the model
	 * @param budgetCategory
	 */
	public void removeBudgetCategory(MutableBudgetCategory budgetCategory) throws InvalidActionException;
	
	/**
	 * Removes the given scheduled transaction from the model
	 * @param scheduledTransaction
	 */
	public void removeScheduledTransaction(MutableScheduledTransaction scheduledTransaction) throws InvalidActionException;
	
	/**
	 * Removes the given transaction from the model
	 * @param transaction
	 */
	public void removeTransaction(MutableTransaction transaction) throws InvalidActionException;
	
	/**
	 * Removes the given type from the model
	 * @param type
	 */
	public void removeType(MutableAccountType type) throws InvalidActionException;
}
