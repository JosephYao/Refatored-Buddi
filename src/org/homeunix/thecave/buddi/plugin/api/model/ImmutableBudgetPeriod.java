/*
 * Created on Aug 12, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.plugin.api.model;

import java.util.Date;

import org.homeunix.thecave.buddi.model.BudgetPeriod;

public interface ImmutableBudgetPeriod extends ImmutableModelObject {
	
	/**
	 * Returns the wrapped object from the underlying data model.  By 
	 * accessing this method, you bypass all protection which the Buddi API
	 * gives you; it is not recommended to use this method unless you understand
	 * the risks associated with it. 
	 * @return
	 */
	public BudgetPeriod getBudgetPeriod();
	
	/**
	 * Returns the date associated with this period.  This is the starting date
	 * of the budget period
	 * @return
	 */
	public Date getPeriodDate();
	
	/**
	 * Returns the amount associated with this budget period and the 
	 * given budget category.
	 * @param budgetCategory
	 * @return
	 */
	public long getAmount(ImmutableBudgetCategory budgetCategory);
}
