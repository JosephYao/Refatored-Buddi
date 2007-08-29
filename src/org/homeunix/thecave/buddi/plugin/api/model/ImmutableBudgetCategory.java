/*
 * Created on Aug 12, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.plugin.api.model;

import java.util.Date;

import org.homeunix.thecave.buddi.model.BudgetCategory;
import org.homeunix.thecave.buddi.model.BudgetCategoryType;

public interface ImmutableBudgetCategory extends ImmutableSource {
		
	/**
	 * Does this ImmutableBudgetCategory represent an income category?
	 * @return
	 */
	public boolean isIncome();
	
	/**
	 * Returns the parent of this ImmutableBudgetCategory, or null if there is no parent.
	 * @return
	 */
	public ImmutableBudgetCategory getParent();
	
	/**
	 * Returns the wrapped object from the underlying data model.  By 
	 * accessing this method, you bypass all protection which the Buddi API
	 * gives you; it is not recommended to use this method unless you understand
	 * the risks associated with it. 
	 * @return
	 */
	public BudgetCategory getBudgetCategory();
	
	/**
	 * Returns the budget period type associated with this budget category.
	 * @return
	 */
	public BudgetCategoryType getBudgetPeriodType();
	
	/**
	 * Returns the budgeted amount for the given date.
	 * 
	 * @param date
	 * @return
	 */
	public long getAmount(Date date);
	
	/**
	 * Returns the budgeted amount spread across the date range.  For instance, given 
	 * a monthly budget period, and the date range Sept 15 - Sept 30, and September
	 * had a value of 100 for the given budget category, we would return 50.
	 * 
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public long getAmount(Date startDate, Date endDate);
}
