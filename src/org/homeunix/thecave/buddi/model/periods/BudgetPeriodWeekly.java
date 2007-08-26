/*
 * Created on Aug 26, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.model.periods;

import java.util.Date;

import org.homeunix.thecave.buddi.i18n.keys.BudgetPeriodType;
import org.homeunix.thecave.buddi.plugin.api.BuddiBudgetPeriodTypePlugin;
import org.homeunix.thecave.moss.util.DateFunctions;

public class BudgetPeriodWeekly extends BuddiBudgetPeriodTypePlugin {
	
	@Override
	public Date getStartOfBudgetPeriod(Date date) {
		return DateFunctions.getStartOfWeek(date);
	}
	
	@Override
	public Date getEndOfBudgetPeriod(Date date) {
		return DateFunctions.getEndOfWeek(date);
	}
	
	@Override
	public Date getBudgetPeriodOffset(Date date, int offset) {
		return getStartOfBudgetPeriod(DateFunctions.addDays(DateFunctions.getStartOfWeek(date), 7 * offset));
	}
	
	@Override
	public long getDaysInPeriod(Date date) {
		return 7;
	}
	
	@Override
	public String getDateFormat() {
		return "dd MMM yyyy";
	}
			
	public String getName() {
		return BudgetPeriodType.BUDGET_PERIOD_WEEK.toString();
	}
}