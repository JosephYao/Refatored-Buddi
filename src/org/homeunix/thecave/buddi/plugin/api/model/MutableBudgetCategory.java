/*
 * Created on Aug 22, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.plugin.api.model;

import java.util.Date;

import org.homeunix.thecave.buddi.model.BudgetCategoryType;
import org.homeunix.thecave.buddi.plugin.api.exception.InvalidValueException;

public interface MutableBudgetCategory extends ImmutableBudgetCategory, MutableSource {
	/**
	 * Sets the budget period type associated with this budget category.
	 * @return
	 */
	public void setBudgetPeriodType(BudgetCategoryType periodType) throws InvalidValueException;
	
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
	
//	public long getAmount(Date startDate, Date endDate);
//	
//	public long getAmount(Date date);
	
	public void setAmount(Date date, long amount) throws InvalidValueException;
}
