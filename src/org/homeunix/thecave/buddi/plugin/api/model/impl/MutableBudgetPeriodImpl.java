/*
 * Created on Aug 23, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.plugin.api.model.impl;

import java.util.Date;

import org.homeunix.thecave.buddi.i18n.keys.BudgetPeriodType;
import org.homeunix.thecave.buddi.model.BudgetPeriod;
import org.homeunix.thecave.buddi.plugin.api.model.ImmutableBudgetCategory;
import org.homeunix.thecave.buddi.plugin.api.model.MutableBudgetCategory;
import org.homeunix.thecave.buddi.plugin.api.model.MutableBudgetPeriod;

public class MutableBudgetPeriodImpl extends MutableModelObjectImpl implements MutableBudgetPeriod {

	public MutableBudgetPeriodImpl(BudgetPeriod period) {
		super(period);
	}

	public void setAmount(MutableBudgetCategory budgetCategory, long amount) {
		if (budgetCategory == null)
			return;
		getBudgetPeriod().setAmount(budgetCategory.getBudgetCategory(), amount);
	}

	public void setPeriodDate(BudgetPeriodType period, Date date) {
		if (date != null)
			getBudgetPeriod().setPeriodDate(period, date);
	}
	public BudgetPeriod getBudgetPeriod(){
		return (BudgetPeriod) getRaw();
	}
	
	public Date getPeriodDate(){
		return getBudgetPeriod().getPeriodDate();
	}
	
	public long getAmount(ImmutableBudgetCategory budgetCategory){
		if (budgetCategory != null)
			return getBudgetPeriod().getAmount(budgetCategory.getBudgetCategory());
		return 0;
	}
}
