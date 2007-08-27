/*
 * Created on Aug 26, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.model.periods;

import java.util.Date;

import org.homeunix.thecave.buddi.i18n.BuddiKeys;
import org.homeunix.thecave.buddi.model.BudgetPeriodType;
import org.homeunix.thecave.moss.util.DateFunctions;

public class BudgetPeriodWeekly extends BudgetPeriodType {
	
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
		return BuddiKeys.BUDGET_PERIOD_WEEK.toString();
	}
}