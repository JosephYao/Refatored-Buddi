/*
 * Created on Jul 29, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.model.beans;

public class BudgetCategoryBean extends SourceBean {
	private boolean income;
	private BudgetCategoryBean parent;
	private boolean expanded;

	//Configuration Data for budget period
	private String periodType;
	
	public String getPeriodType() {
		return periodType;
	}
	public void setPeriodType(String periodType) {
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
