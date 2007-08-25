/*
 * Created on Aug 12, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.plugin.api.model;

import java.util.Date;

import org.homeunix.thecave.buddi.model.BudgetCategory;

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
	 * Returns the wrapped BudgetCategory object associated with this ImmutableBudgetCategory.
	 * If you access this object, you will bypass all security associated with the Immutable API.
	 * It is highly recommended not to use this method unless you know what you are doing.  
	 * @return
	 */
	public BudgetCategory getBudgetCategory();
	
	/**
	 * Returns the budgeted amount spread across the date range.  For instance, given 
	 * a monthly budget period, and the date range Sept 15 - Sept 30, and September
	 * had a value of 100 for the given budget category, we would return 50.
	 * 
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public long getBudgetedAmount(Date startDate, Date endDate);
}
