/*
 * Created on Aug 12, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.plugin.api.model;

import java.util.Date;

import org.homeunix.thecave.buddi.model.BudgetPeriod;

public interface ImmutableBudgetPeriod extends ImmutableModelObject {
	
	public BudgetPeriod getBudgetPeriod();
	
	public Date getPeriodDate();
	
	public long getAmount(ImmutableBudgetCategory budgetCategory);
}
