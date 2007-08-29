/*
 * Created on Jul 29, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.model.beans;

import java.util.HashMap;
import java.util.Map;

import org.homeunix.thecave.buddi.model.BudgetCategoryType;

public class BudgetCategoryBean extends SourceBean {
	private boolean income;
	private boolean expanded;
	private BudgetCategoryType periodType;
	private BudgetCategoryBean parent;
	private Map<String, Long> amounts;
	
	
	public Map<String, Long> getAmounts() {
		if (amounts == null)
			amounts = new HashMap<String, Long>();
		return amounts;
	}
	public void setAmounts(Map<String, Long> amounts) {
		this.amounts = amounts;
	}
	public BudgetCategoryType getPeriodType() {
		return periodType;
	}
	public void setPeriodType(BudgetCategoryType periodType) {
		this.periodType = periodType;
	}
	public boolean isIncome() {
		return income;
	}
	public void setIncome(boolean income) {
		this.income = income;
	}
	public BudgetCategoryBean getParent() {
		return parent;
	}
	public void setParent(BudgetCategoryBean parent) {
		this.parent = parent;
	}
	public boolean isExpanded() {
		return expanded;
	}
	public void setExpanded(boolean expanded) {
		this.expanded = expanded;
	}	
}
