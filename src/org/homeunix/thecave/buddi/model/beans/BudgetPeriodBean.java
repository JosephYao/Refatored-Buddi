/*
 * Created on Jul 29, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.model.beans;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class BudgetPeriodBean extends ModelObjectBean {	
	private Map<BudgetCategoryBean, Long> budgetCategories = new HashMap<BudgetCategoryBean, Long>();
	private Date periodDate; //Used in budget period comparisons

	public Date getPeriodDate() {
		return periodDate;
	}
	public void setPeriodDate(Date periodDate) {
		this.periodDate = periodDate;
	}
	public Map<BudgetCategoryBean, Long> getBudgetCategories() {
		checkLists();
		return budgetCategories;
	}
	public void setBudgetCategories(Map<BudgetCategoryBean, Long> budgetCategories) {
		this.budgetCategories = budgetCategories;
	}

	private void checkLists(){
		if (this.budgetCategories == null)
			this.budgetCategories = new HashMap<BudgetCategoryBean, Long>();
	}
	@Override
	public String toString() {
		return getPeriodDate() + ":0x" + getUid();
	}

}
