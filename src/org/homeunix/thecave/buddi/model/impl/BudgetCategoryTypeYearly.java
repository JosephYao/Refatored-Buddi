/*
 * Created on Aug 26, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.model.impl;

import java.util.Date;

import org.homeunix.thecave.buddi.i18n.keys.BudgetCategoryTypes;
import org.homeunix.thecave.buddi.model.BudgetCategoryType;
import org.homeunix.thecave.moss.util.DateFunctions;

/**
 * Definition of a Yearly BudgetCategoryType
 * 
 * @author wyatt
 *
 */
public class BudgetCategoryTypeYearly extends BudgetCategoryType {
	
	public Date getStartOfBudgetPeriod(Date date) {
		return DateFunctions.getStartOfYear(date);
	}
	
	public Date getEndOfBudgetPeriod(Date date) {
		return DateFunctions.getEndOfYear(date);
	}
	
	public Date getBudgetPeriodOffset(Date date, int offset) {
		return getStartOfBudgetPeriod(DateFunctions.addYears(date, offset));
	}
	
	public long getDaysInPeriod(Date date) {
		return DateFunctions.getDaysBetween(getStartOfBudgetPeriod(date), getEndOfBudgetPeriod(date), true);
	}
	
	public String getDateFormat() {
		return "yyyy";
	}
			
	public String getName() {
		return BudgetCategoryTypes.BUDGET_CATEGORY_TYPE_YEAR.toString();
	}
}