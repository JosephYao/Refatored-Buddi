/*
 * Created on Aug 26, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.model.periods;

import java.util.Date;

import org.homeunix.thecave.buddi.i18n.BuddiKeys;
import org.homeunix.thecave.buddi.model.BudgetPeriodType;
import org.homeunix.thecave.moss.util.DateFunctions;

public class BudgetPeriodYearly extends BudgetPeriodType {
	
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
		return BuddiKeys.BUDGET_PERIOD_YEAR.toString();
	}
}