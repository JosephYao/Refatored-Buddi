/*
 * Created on Aug 26, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.model.impl;

import java.util.Date;

import org.homeunix.thecave.buddi.i18n.keys.BudgetCategoryTypes;
import org.homeunix.thecave.buddi.model.BudgetCategoryType;
import org.homeunix.thecave.moss.util.DateFunctions;

/**
 * Definition of a Weekly BudgetCategoryType
 * @author wyatt
 *
 */
public class BudgetCategoryTypeWeekly extends BudgetCategoryType {
	
	public Date getStartOfBudgetPeriod(Date date) {
		return DateFunctions.getStartOfWeek(date);
	}
	
	public Date getEndOfBudgetPeriod(Date date) {
		return DateFunctions.getEndOfWeek(date);
	}
	
	public Date getBudgetPeriodOffset(Date date, int offset) {
		return getStartOfBudgetPeriod(DateFunctions.addDays(DateFunctions.getStartOfWeek(date), 7 * offset));
	}
	
	public long getDaysInPeriod(Date date) {
		return 7;
	}
	
	public String getDateFormat() {
		return "dd MMM yyyy";
	}
			
	public String getName() {
		return BudgetCategoryTypes.BUDGET_CATEGORY_TYPE_WEEK.toString();
	}
}