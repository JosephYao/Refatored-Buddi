/*
 * Created on Aug 26, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.model.periods;

import java.util.Date;

import org.homeunix.thecave.buddi.i18n.BuddiKeys;
import org.homeunix.thecave.buddi.model.BudgetPeriodType;
import org.homeunix.thecave.moss.util.DateFunctions;

public class BudgetPeriodMonthly extends BudgetPeriodType {
	
	public Date getStartOfBudgetPeriod(Date date) {
		return DateFunctions.getStartOfMonth(date);
	}
	
	public Date getEndOfBudgetPeriod(Date date) {
		return DateFunctions.getEndOfMonth(date);
	}
	
	public Date getBudgetPeriodOffset(Date date, int offset) {
		return getStartOfBudgetPeriod(DateFunctions.addMonths(DateFunctions.getStartOfMonth(date), 1 * offset));
	}
	
	public long getDaysInPeriod(Date date) {
		return DateFunctions.getDaysInMonth(date);
	}
	
	public String getDateFormat() {
		return "MMM yyyy";
	}
			
	public String getName() {
		return BuddiKeys.BUDGET_PERIOD_MONTH.toString();
	}
}