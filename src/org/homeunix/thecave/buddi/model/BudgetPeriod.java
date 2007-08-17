/*
 * Created on Jul 30, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.model;

import java.util.Date;

import org.homeunix.thecave.buddi.model.beans.BudgetPeriodBean;

public class BudgetPeriod extends ModelObject implements Comparable<BudgetPeriod>{
//	private BudgetPeriodBean getBudgetPeriodBean();
	
	BudgetPeriod(DataModel model, BudgetPeriodBean budgetPeriod) {
		super(model, budgetPeriod);
	}

	public long getAmount(BudgetCategory budgetCategory){
		return getBudgetPeriodBean().getBudgetCategories().get(budgetCategory.getBudgetCategoryBean()) == null ? 0 : getBudgetPeriodBean().getBudgetCategories().get(budgetCategory.getBudgetCategoryBean());
	}
	
	public void setAmount(BudgetCategory budgetCategory, long amount){
		if (budgetCategory == null)
			return;
		getBudgetPeriodBean().getBudgetCategories().put(budgetCategory.getBudgetCategoryBean(), amount);
		getModel().setChanged();
	}
	
	public Date getPeriodDate(){
		return getBudgetPeriodBean().getPeriodDate();
	}
	
	public void setPeriodDate(Date periodDate){
		getBudgetPeriodBean().setPeriodDate(getModel().getStartOfBudgetPeriod(periodDate));
	}
	
	BudgetPeriodBean getBudgetPeriodBean(){
		return (BudgetPeriodBean) getBean();
	}

	public int compareTo(BudgetPeriod arg0) {
		if (arg0 == null)
			return -1;
		if (arg0.getPeriodDate() == null && this.getPeriodDate() == null)
			return 0;
		if (arg0.getPeriodDate() == null)
			return -1;
		if (this.getPeriodDate() == null)
			return 1;
		return this.getPeriodDate().compareTo(arg0.getPeriodDate());
	}
}
