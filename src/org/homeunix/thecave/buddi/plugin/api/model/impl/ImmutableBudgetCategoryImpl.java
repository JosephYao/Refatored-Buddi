/*
 * Created on Aug 12, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.plugin.api.model.impl;

import java.util.Date;

import org.homeunix.thecave.buddi.i18n.keys.BudgetPeriodType;
import org.homeunix.thecave.buddi.model.BudgetCategory;
import org.homeunix.thecave.buddi.plugin.api.model.ImmutableBudgetCategory;

public class ImmutableBudgetCategoryImpl extends ImmutableSourceImpl implements ImmutableBudgetCategory {
	
	public ImmutableBudgetCategoryImpl(BudgetCategory budgetCategory) {
		super(budgetCategory);
	}
	
	public boolean isIncome() {
		return getBudgetCategory().isIncome();
	}
	public ImmutableBudgetCategory getParent() {
		if (getBudgetCategory().getParent() != null)
			return new MutableBudgetCategoryImpl(getBudgetCategory().getParent());
		return null;
	}
	
	public BudgetCategory getBudgetCategory(){
		return (BudgetCategory) getRaw();
	}
	
	public long getAmount(Date startDate, Date endDate) {
		return getBudgetCategory().getAmount(startDate, endDate);
	}
	
	public long getAmount(Date date) {
		return getBudgetCategory().getAmount(date);
	}
	
	public BudgetPeriodType getBudgetPeriodType() {
		return getBudgetCategory().getBudgetPeriodType();
	}
}
