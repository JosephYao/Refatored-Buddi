/*
 * Created on Aug 25, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.model.swing;

import java.util.Date;

import javax.swing.SpinnerDateModel;

import org.homeunix.thecave.buddi.util.BudgetPeriodUtil;

public class BudgetDateSpinnerModel extends SpinnerDateModel {
	private final BudgetTreeTableModel budgetModel;
	
	public BudgetDateSpinnerModel(BudgetTreeTableModel budgetModel) {
		super();
		this.setValue(new Date());
		this.budgetModel = budgetModel;
	}
	
	@Override
	public Object getNextValue() {
		return BudgetPeriodUtil.getNextBudgetPeriod(budgetModel.getSelectedBudgetPeriodType(), getDate());
	}
	
	@Override
	public Object getPreviousValue() {
		return BudgetPeriodUtil.getPreviousBudgetPeriod(budgetModel.getSelectedBudgetPeriodType(), getDate());
	}
}
