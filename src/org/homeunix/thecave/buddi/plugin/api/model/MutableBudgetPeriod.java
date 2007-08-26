/*
 * Created on Aug 22, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.plugin.api.model;

import java.util.Date;

import org.homeunix.thecave.buddi.i18n.keys.BudgetPeriodType;

public interface MutableBudgetPeriod extends ImmutableBudgetPeriod {

	public void setAmount(MutableBudgetCategory budgetCategory, long amount);
	
	public void setPeriodDate(BudgetPeriodType type, Date date);
}
