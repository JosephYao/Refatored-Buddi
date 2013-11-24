package org.homeunix.thecave.buddi.model.impl;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

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
		return another.period.equals(this.period);
	}

	private BudgetPeriod nextBudgetPeriod() {
		return new BudgetPeriod(type, type.getBudgetPeriodOffset(period.getStartDate(), 1));
	}

	public Date getStartDate() {
		return period.getStartDate();
	}

	private Date getEndDate() {
		return period.getEndDate();
	}

	public long getDayCount() {
		return period.getDayCount();
	}

	public List<BudgetPeriod> createBudgetPeriodsTill(BudgetPeriod lastBudgetPeriod){
		List<BudgetPeriod> budgetPeriods = new LinkedList<BudgetPeriod>();
	
		BudgetPeriod current = this;
	
		while (current.getStartDate().before(lastBudgetPeriod.getEndDate())){
			budgetPeriods.add(current);
			current = current.nextBudgetPeriod();
		}
	
		return budgetPeriods;
	}

	public Period getPeriod() {
		return period;
	}

}
