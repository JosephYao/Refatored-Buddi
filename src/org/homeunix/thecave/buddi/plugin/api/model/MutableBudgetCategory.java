/*
 * Created on Aug 22, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.plugin.api.model;

import java.util.Date;

import org.homeunix.thecave.buddi.plugin.api.exception.InvalidValueException;

public interface MutableBudgetCategory extends ImmutableBudgetCategory, MutableSource {
	/**
	 * Sets the amount for the budget period which contains the given date.  This is 
	 * defined by the currently selected BudgetCategoryType object associated with
	 * this category.  
	 * @param date
	 * @param amount
	 * @throws InvalidValueException
	 */
	public void setAmount(Date date, long amount) throws InvalidValueException;
	
	/**
	 * Sets the budget period type associated with this budget category.
	 * @return
	 */
	public void setBudgetCategoryType(ImmutableBudgetCategoryType budgetCategoryType) throws InvalidValueException;
	
	/**
	 * Sets whether this budget category represents income or not.
	 * @param income
	 */
	public void setIncome(boolean income) throws InvalidValueException;
	
	/**
	 * Sets the parent of this budget category.  Set to null for no parent.
	 * @param parent
	 */
	public void setParent(MutableBudgetCategory parent) throws InvalidValueException;	
	/**
	 * Returns the parent of this ImmutableBudgetCategory, or null if there is no parent.
	 * @return
	 */
	public MutableBudgetCategory getParent();
}
