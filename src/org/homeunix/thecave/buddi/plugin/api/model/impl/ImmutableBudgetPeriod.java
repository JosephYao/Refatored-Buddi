/*
 * Created on Aug 12, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.plugin.api.model.impl;

import java.util.Date;

import org.homeunix.thecave.buddi.model.BudgetPeriod;

public class ImmutableBudgetPeriod extends ImmutableModelObject {
	
	public ImmutableBudgetPeriod(BudgetPeriod budgetPeriod) {
		super(budgetPeriod);
	}
	
	public BudgetPeriod getBudgetPeriod(){
		return (BudgetPeriod) getRaw();
	}
	
	public Date getPeriodDate(){
		return getBudgetPeriod().getPeriodDate();
	}
	
	public long getAmount(ImmutableBudgetCategory budgetCategory){
		return getBudgetPeriod().getAmount(budgetCategory.getBudgetCategory());
	}
}
