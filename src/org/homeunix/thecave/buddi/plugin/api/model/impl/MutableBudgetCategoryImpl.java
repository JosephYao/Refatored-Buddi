/*
 * Created on Aug 23, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.plugin.api.model.impl;

import java.util.Date;

import org.homeunix.thecave.buddi.model.BudgetCategory;
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
		if (budgetCategory == null)
			getBudgetCategory().setParent(null);
		else
			getBudgetCategory().setParent(budgetCategory.getBudgetCategory());
	}

	public BudgetCategory getBudgetCategory() {
		return (BudgetCategory) getSource();
	}

	public long getBudgetedAmount(Date startDate, Date endDate) {
		return getBudgetCategory().getBudgetedAmount(startDate, endDate);
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
