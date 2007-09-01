/*
 * Created on Aug 26, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.model.impl;

import java.util.Date;

import org.homeunix.thecave.buddi.i18n.keys.BudgetCategoryTypes;
import org.homeunix.thecave.buddi.model.BudgetCategoryType;
import org.homeunix.thecave.moss.util.DateFunctions;

/**
 * Definition of a Quarterly BudgetCategoryType
 * @author wyatt
 *
 */
public class BudgetCategoryTypeQuarterly extends BudgetCategoryType {
	
	public Date getStartOfBudgetPeriod(Date date) {
		return DateFunctions.getStartOfQuarter(date);
	}
	
	public Date getEndOfBudgetPeriod(Date date) {
		return DateFunctions.getEndOfQuarter(date);
	}
	
	public Date getBudgetPeriodOffset(Date date, int offset) {
		return getStartOfBudgetPeriod(DateFunctions.addQuarters(date, offset));
	}
	
	public long getDaysInPeriod(Date date) {
		return DateFunctions.getDaysBetween(getStartOfBudgetPeriod(date), getEndOfBudgetPeriod(date), true);
	}
	
	public String getDateFormat() {
		return "MMM yyyy";
	}
			
	public String getName() {
		return BudgetCategoryTypes.BUDGET_CATEGORY_TYPE_QUARTER.toString();
	}
}