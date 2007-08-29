/*
 * Created on Aug 23, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.plugin.api.model.impl;

import java.util.Date;

import org.homeunix.thecave.buddi.model.BudgetCategory;
import org.homeunix.thecave.buddi.model.BudgetCategoryType;
import org.homeunix.thecave.buddi.plugin.api.model.ImmutableBudgetCategory;
import org.homeunix.thecave.buddi.plugin.api.model.MutableBudgetCategory;

public class MutableBudgetCategoryImpl extends MutableSourceImpl implements MutableBudgetCategory {

	public MutableBudgetCategoryImpl(BudgetCategory budgetCategory) {
		super(budgetCategory);
	}

	public void setIncome(boolean income) {
		getBudgetCategory().setIncome(income);
	}

	public void setParent(MutableBudgetCategory budgetCategory) {
		System.out.println(budgetCategory);
		if (budgetCategory == null)
			getBudgetCategory().setParent(null);
		else
			getBudgetCategory().setParent(budgetCategory.getBudgetCategory());
	}

	public BudgetCategory getBudgetCategory() {
		return (BudgetCategory) getSource();
	}

	public long getAmount(Date startDate, Date endDate) {
		return getBudgetCategory().getAmount(startDate, endDate);
	}
	
	public void setAmount(Date date, long amount) {
		getBudgetCategory().setAmount(date, amount);
	}
	
	public long getAmount(Date date) {
		return getBudgetCategory().getAmount(date);
	}
	
	public BudgetCategoryType getBudgetPeriodType() {
		return getBudgetCategory().getBudgetPeriodType();
	}

	public void setBudgetPeriodType(BudgetCategoryType periodType) {
		if (periodType != null)
			getBudgetCategory().setPeriodType(periodType);
	}
	
	public ImmutableBudgetCategory getParent() {
		if (getBudgetCategory().getParent() != null)
			return new MutableBudgetCategoryImpl(getBudgetCategory().getParent());
		return null;
	}

	public boolean isIncome() {
		return getBudgetCategory().isIncome();
	}
}
