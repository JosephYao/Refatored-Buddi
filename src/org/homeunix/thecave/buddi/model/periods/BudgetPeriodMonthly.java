/*
 * Created on Aug 26, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.model.periods;

import java.util.Date;

import org.homeunix.thecave.buddi.i18n.keys.BudgetPeriodType;
import org.homeunix.thecave.buddi.plugin.api.BuddiBudgetPeriodTypePlugin;
import org.homeunix.thecave.moss.util.DateFunctions;

public class BudgetPeriodMonthly extends BuddiBudgetPeriodTypePlugin {
	
	@Override
	public Date getStartOfBudgetPeriod(Date date) {
		return DateFunctions.getStartOfMonth(date);
	}
	
	@Override
	public Date getEndOfBudgetPeriod(Date date) {
		return DateFunctions.getEndOfMonth(date);
	}
	
	@Override
	public Date getBudgetPeriodOffset(Date date, int offset) {
		return getStartOfBudgetPeriod(DateFunctions.addMonths(DateFunctions.getStartOfMonth(date), 1 * offset));
	}
	
	@Override
	public long getDaysInPeriod(Date date) {
		return DateFunctions.getDaysInMonth(date);
	}
	
	@Override
	public String getDateFormat() {
		return "MMM yyyy";
	}
			
	public String getName() {
		return BudgetPeriodType.BUDGET_PERIOD_MONTH.toString();
	}
}