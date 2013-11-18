package org.homeunix.thecave.buddi.model.impl;

import java.util.Date;

import org.homeunix.thecave.buddi.model.BudgetCategoryType;

public class BudgetPeriod {
	
	private final Period period;
	private final BudgetCategoryType type;

	public BudgetPeriod(BudgetCategoryType type, Date date) {
		this.type = type;
		period = new Period(type.getStartOfBudgetPeriod(date), type.getEndOfBudgetPeriod(date));
	}
	
	public boolean equals(Object obj) {
		BudgetPeriod another = (BudgetPeriod)obj;
		return another.period.equals(this.period);
	}
	
	public BudgetPeriod nextBudgetPeriod() {
		return new BudgetPeriod(type, type.getBudgetPeriodOffset(period.getStartDate(), 1));
	}
	
	public Date getStartDate() {
		return period.getStartDate();
	}

	public Date getEndDate() {
		return period.getEndDate();
	}

	public BudgetPeriod perviousBudgetPeriod() {
		return new BudgetPeriod(type, type.getBudgetPeriodOffset(period.getStartDate(), -1));
	}

}
