/*
 * Created on Aug 22, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.plugin.api.model;

import java.util.Date;

public interface MutableBudgetPeriod extends ImmutableBudgetPeriod {

	public void setAmount(MutableBudgetCategory budgetCategory, long amount);
	
	public void setPeriodDate(Date date);
}
