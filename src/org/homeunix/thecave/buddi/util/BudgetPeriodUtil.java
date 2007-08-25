/*
 * Created on Aug 16, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.util;

import java.util.Date;

import org.homeunix.thecave.buddi.i18n.keys.BudgetPeriodKeys;
import org.homeunix.thecave.buddi.model.exception.DataModelProblemException;
import org.homeunix.thecave.moss.util.DateFunctions;

/**
 * The class where most (hopefully most, if at all possible) of the BudgetPeriod
 * logic code lives.  This is the abstraction layer between the model and API
 * helpers, which are BudgetPeriod specific.
 * 
 * @author wyatt
 *
 */
public class BudgetPeriodUtil {
	
	public static Date getStartOfBudgetPeriod(BudgetPeriodKeys period, Date date){
		//TODO
		if (period.equals(BudgetPeriodKeys.BUDGET_PERIOD_WEEK)){
			throw new DataModelProblemException("Weekly period not implemented!");
		}
		if (period.equals(BudgetPeriodKeys.BUDGET_PERIOD_FORTNIGHT)){
			throw new DataModelProblemException("Weekly period not implemented!");
		}
		if (period.equals(BudgetPeriodKeys.BUDGET_PERIOD_MONTH)){
			return DateFunctions.getStartOfMonth(date);
		}
		if (period.equals(BudgetPeriodKeys.BUDGET_PERIOD_QUARTER)){
			throw new DataModelProblemException("Quarterly period not implemented!");
		}

		if (period.equals(BudgetPeriodKeys.BUDGET_PERIOD_YEAR)){
			throw new DataModelProblemException("Yearly period not implemented!");
		}

		throw new DataModelProblemException("Period " + period + " period not implemented!");
	}
	
	public static Date getEndOfBudgetPeriod(BudgetPeriodKeys period, Date date){
		//TODO
		if (period.equals(BudgetPeriodKeys.BUDGET_PERIOD_WEEK)){
			throw new DataModelProblemException("Weekly period not implemented!");
		}
		if (period.equals(BudgetPeriodKeys.BUDGET_PERIOD_FORTNIGHT)){
			throw new DataModelProblemException("Weekly period not implemented!");
		}
		if (period.equals(BudgetPeriodKeys.BUDGET_PERIOD_MONTH)){
			return DateFunctions.getEndOfMonth(date);
		}
		if (period.equals(BudgetPeriodKeys.BUDGET_PERIOD_QUARTER)){
			throw new DataModelProblemException("Quarterly period not implemented!");
		}

		if (period.equals(BudgetPeriodKeys.BUDGET_PERIOD_YEAR)){
			throw new DataModelProblemException("Yearly period not implemented!");
		}

		throw new DataModelProblemException("Period " + period + " period not implemented!");
	}
	
	public static Date getNextBudgetPeriod(BudgetPeriodKeys period, Date date){
		date = getStartOfBudgetPeriod(period, date);
		
		//TODO
		if (period.equals(BudgetPeriodKeys.BUDGET_PERIOD_WEEK)){
			throw new DataModelProblemException("Weekly period not implemented!");
		}
		if (period.equals(BudgetPeriodKeys.BUDGET_PERIOD_FORTNIGHT)){
			throw new DataModelProblemException("Weekly period not implemented!");
		}
		if (period.equals(BudgetPeriodKeys.BUDGET_PERIOD_MONTH)){
			return getStartOfBudgetPeriod(period, DateFunctions.addMonths(DateFunctions.getStartOfMonth(date), 1));
		}
		if (period.equals(BudgetPeriodKeys.BUDGET_PERIOD_QUARTER)){
			throw new DataModelProblemException("Quarterly period not implemented!");
		}

		if (period.equals(BudgetPeriodKeys.BUDGET_PERIOD_YEAR)){
			throw new DataModelProblemException("Yearly period not implemented!");
		}

		throw new DataModelProblemException("Period " + period + " period not implemented!");
	}
	
	public static Date getPreviousBudgetPeriod(BudgetPeriodKeys period, Date date){
		//TODO
		if (period.equals(BudgetPeriodKeys.BUDGET_PERIOD_WEEK)){
			throw new DataModelProblemException("Weekly period not implemented!");
		}
		if (period.equals(BudgetPeriodKeys.BUDGET_PERIOD_FORTNIGHT)){
			throw new DataModelProblemException("Weekly period not implemented!");
		}
		if (period.equals(BudgetPeriodKeys.BUDGET_PERIOD_MONTH)){
			return getStartOfBudgetPeriod(period, DateFunctions.addMonths(DateFunctions.getStartOfMonth(date), -1));
		}
		if (period.equals(BudgetPeriodKeys.BUDGET_PERIOD_QUARTER)){
			throw new DataModelProblemException("Quarterly period not implemented!");
		}

		if (period.equals(BudgetPeriodKeys.BUDGET_PERIOD_YEAR)){
			throw new DataModelProblemException("Yearly period not implemented!");
		}

		throw new DataModelProblemException("Period " + period + " period not implemented!");
	}
	
	/**
	 * Returns the number of days in the given period.  Depending on the period, this may
	 * be a constant number (BUDGET_PERIOD_WEEK == 7), or a variable number (BUDGET_PERIOD_MONTH
	 * depends on which month).
	 * @param period The butget period type to use
	 * @param startOfBudgetPeriod The date to get the budget period from.
	 * @return
	 */
	public static long getDaysInPeriod(BudgetPeriodKeys period, Date startOfBudgetPeriod){
		startOfBudgetPeriod = getStartOfBudgetPeriod(period, startOfBudgetPeriod);
		
		//TODO
		if (period.equals(BudgetPeriodKeys.BUDGET_PERIOD_WEEK)){
			throw new DataModelProblemException("Weekly period not implemented!");
		}
		if (period.equals(BudgetPeriodKeys.BUDGET_PERIOD_FORTNIGHT)){
			throw new DataModelProblemException("Weekly period not implemented!");
		}
		if (period.equals(BudgetPeriodKeys.BUDGET_PERIOD_MONTH)){
			return DateFunctions.getDaysInMonth(startOfBudgetPeriod);
		}
		if (period.equals(BudgetPeriodKeys.BUDGET_PERIOD_QUARTER)){
			throw new DataModelProblemException("Quarterly period not implemented!");
		}

		if (period.equals(BudgetPeriodKeys.BUDGET_PERIOD_YEAR)){
			throw new DataModelProblemException("Yearly period not implemented!");
		}

		throw new DataModelProblemException("Period " + period + " period not implemented!");	
	}
}
