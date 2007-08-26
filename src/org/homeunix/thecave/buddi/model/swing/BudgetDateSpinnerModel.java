/*
 * Created on Aug 25, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.model.swing;

import java.util.Date;

import javax.swing.SpinnerDateModel;

import org.homeunix.thecave.buddi.util.BudgetPeriodUtil;

public class BudgetDateSpinnerModel extends SpinnerDateModel {
	public static final long serialVersionUID = 0;
	
	private final BudgetTreeTableModel budgetModel;
	
	public BudgetDateSpinnerModel(BudgetTreeTableModel budgetModel) {
		super();
		this.setValue(new Date());
		this.budgetModel = budgetModel;
	}
		
	public Object getNextValue() {
//		System.out.println(getDate());
		return BudgetPeriodUtil.getBudgetPeriodOffset(budgetModel.getSelectedBudgetPeriodType(), getDate(), 1);
	}
	
	public Object getPreviousValue() {
//		System.out.println(getDate());
		return BudgetPeriodUtil.getBudgetPeriodOffset(budgetModel.getSelectedBudgetPeriodType(), getDate(), -1);
	}
	
	@Override
	public void setCalendarField(int calendarField) {
		// TODO Auto-generated method stub
//		super.setCalendarField(calendarField);
	}
}
