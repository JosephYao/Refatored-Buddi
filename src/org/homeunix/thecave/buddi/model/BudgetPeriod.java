/*
 * Created on Jul 30, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.model;

import java.util.Date;

import org.homeunix.thecave.buddi.i18n.keys.BudgetPeriodType;
import org.homeunix.thecave.buddi.model.beans.BudgetPeriodBean;
import org.homeunix.thecave.buddi.util.BudgetPeriodUtil;

public class BudgetPeriod extends ModelObjectImpl {
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

	public void setPeriodDate(BudgetPeriodType period, Date periodDate){
		getBudgetPeriodBean().setPeriodDate(BudgetPeriodUtil.getStartOfBudgetPeriod(period, periodDate));
	}

	BudgetPeriodBean getBudgetPeriodBean(){
		return (BudgetPeriodBean) getBean();
	}

	public int compareTo(ModelObjectImpl arg0) {
		if (arg0 instanceof BudgetPeriod){
			BudgetPeriod bp = (BudgetPeriod) arg0;
			if (bp == null)
				return -1;
			if (bp.getPeriodDate() == null && this.getPeriodDate() == null)
				return 0;
			if (bp.getPeriodDate() == null)
				return -1;
			if (this.getPeriodDate() == null)
				return 1;
			return this.getPeriodDate().compareTo(bp.getPeriodDate());
		}
		return super.compareTo(arg0);
	}
}
