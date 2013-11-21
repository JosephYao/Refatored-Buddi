package org.homeunix.thecave.buddi.model.impl;

import java.util.Date;

import org.homeunix.thecave.buddi.model.BudgetCategoryType;

public class BudgetPeriod {
	
	private final Period period;
	private final BudgetCategoryType type;

	public BudgetPeriod(BudgetCategoryType type, Date date) {
		this.type = type;
		this.period = new Period(type.getStartOfBudgetPeriod(date), type.getEndOfBudgetPeriod(date));
	}
	
	public boolean equals(Object object) {
		BudgetPeriod another = (BudgetPeriod) object;
		return another.period.equals(period);
	}

	public BudgetPeriod nextBudgetPeriod() {
		return new BudgetPeriod(type, type.getStartOfNextBudgetPeriod(period.getStartDate()));
	}

	public Date getStartDate() {
		return period.getStartDate();
	}

	public Date getEndDate() {
		return period.getEndDate();
	}

	public BudgetPeriod previousBudgetPeriod() {
		return new BudgetPeriod(type, type.getStartOfPreviousBudgetPeriod(period.getStartDate()));
	}

}
