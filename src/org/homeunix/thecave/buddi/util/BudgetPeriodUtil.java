/*
 * Created on Aug 16, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.util;

import java.util.Date;

import org.homeunix.thecave.buddi.i18n.keys.BudgetPeriodType;
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
	
	public static Date getStartOfBudgetPeriod(BudgetPeriodType period, Date date){
		//TODO
		if (period.equals(BudgetPeriodType.BUDGET_PERIOD_WEEK)){
			return DateFunctions.getStartOfWeek(date);
		}
		if (period.equals(BudgetPeriodType.BUDGET_PERIOD_FORTNIGHT)){
			throw new DataModelProblemException("Weekly period not implemented!");
		}
		if (period.equals(BudgetPeriodType.BUDGET_PERIOD_MONTH)){
			return DateFunctions.getStartOfMonth(date);
		}
		if (period.equals(BudgetPeriodType.BUDGET_PERIOD_QUARTER)){
			throw new DataModelProblemException("Quarterly period not implemented!");
		}

		if (period.equals(BudgetPeriodType.BUDGET_PERIOD_YEAR)){
			throw new DataModelProblemException("Yearly period not implemented!");
		}

		throw new DataModelProblemException("Period " + period + " period not implemented!");
	}
	
	public static Date getEndOfBudgetPeriod(BudgetPeriodType period, Date date){
		//TODO
		if (period.equals(BudgetPeriodType.BUDGET_PERIOD_WEEK)){
			return DateFunctions.getEndOfWeek(date);
		}
		if (period.equals(BudgetPeriodType.BUDGET_PERIOD_FORTNIGHT)){
			throw new DataModelProblemException("Weekly period not implemented!");
		}
		if (period.equals(BudgetPeriodType.BUDGET_PERIOD_MONTH)){
			return DateFunctions.getEndOfMonth(date);
		}
		if (period.equals(BudgetPeriodType.BUDGET_PERIOD_QUARTER)){
			throw new DataModelProblemException("Quarterly period not implemented!");
		}

		if (period.equals(BudgetPeriodType.BUDGET_PERIOD_YEAR)){
			throw new DataModelProblemException("Yearly period not implemented!");
		}

		throw new DataModelProblemException("Period " + period + " period not implemented!");
	}
	
	
	public static String getDateFormat(BudgetPeriodType period){
		if (period.equals(BudgetPeriodType.BUDGET_PERIOD_WEEK)){
			return "dd MMM yyyy";
		}
		if (period.equals(BudgetPeriodType.BUDGET_PERIOD_FORTNIGHT)){
			throw new DataModelProblemException("Weekly period not implemented!");
		}
		if (period.equals(BudgetPeriodType.BUDGET_PERIOD_MONTH)){
			return "MMM yyyy";
		}
		if (period.equals(BudgetPeriodType.BUDGET_PERIOD_QUARTER)){
			throw new DataModelProblemException("Quarterly period not implemented!");
		}

		if (period.equals(BudgetPeriodType.BUDGET_PERIOD_YEAR)){
			throw new DataModelProblemException("Yearly period not implemented!");
		}

		throw new DataModelProblemException("Period " + period + " period not implemented!");
	}
	
	
//	public static Date getNextBudgetPeriod(BudgetPeriodType period, Date date){
//		return getNextBudgetPeriod(period, date, 1);
//	}
	
	/**
	 * Adds an offset of the given number of periods to the given period.  This 
	 * must conform to the following:
	 * 
	 * If offset is 0, return the same date
	 * If offset is positive, add that many budget periods.  For instance, if 
	 * the value 3 is given, MONTHLY period is selected, and the current date
	 * is August 1, the return date will be November 1 (August + 3).
	 * If offset is negative, subtract that many budget periods.  The inverse
	 * of this must be the same as positive; for instance, if you are given 
	 * a date and you add '-3' to it, and then add '3' again, you must arrive 
	 * at the same date as you started with. 
	 * @param period
	 * @param date
	 * @param offset
	 * @return
	 */
	public static Date addBudgetPeriod(BudgetPeriodType period, Date date, int offset){
		date = getStartOfBudgetPeriod(period, date);
		
		//TODO
		if (period.equals(BudgetPeriodType.BUDGET_PERIOD_WEEK)){
			return getStartOfBudgetPeriod(period, DateFunctions.addDays(DateFunctions.getStartOfWeek(date), 7 * offset));
		}
		if (period.equals(BudgetPeriodType.BUDGET_PERIOD_FORTNIGHT)){
			throw new DataModelProblemException("Weekly period not implemented!");
		}
		if (period.equals(BudgetPeriodType.BUDGET_PERIOD_MONTH)){
			return getStartOfBudgetPeriod(period, DateFunctions.addMonths(DateFunctions.getStartOfMonth(date), 1 * offset));
		}
		if (period.equals(BudgetPeriodType.BUDGET_PERIOD_QUARTER)){
			throw new DataModelProblemException("Quarterly period not implemented!");
		}

		if (period.equals(BudgetPeriodType.BUDGET_PERIOD_YEAR)){
			throw new DataModelProblemException("Yearly period not implemented!");
		}

		throw new DataModelProblemException("Period " + period + " period not implemented!");
	}
	
//	public static Date getPreviousBudgetPeriod(BudgetPeriodType period, Date date){
//		return getPreviousBudgetPeriod(period, date, 1);
//	}
//	
//	public static Date getPreviousBudgetPeriod(BudgetPeriodType period, Date date, int offset){
//		offset = Math.abs(offset);
//		
//		//TODO
//		if (period.equals(BudgetPeriodType.BUDGET_PERIOD_WEEK)){
//			return getStartOfBudgetPeriod(period, DateFunctions.addDays(DateFunctions.getStartOfMonth(date), -7 * offset));
//		}
//		if (period.equals(BudgetPeriodType.BUDGET_PERIOD_FORTNIGHT)){
//			throw new DataModelProblemException("Weekly period not implemented!");
//		}
//		if (period.equals(BudgetPeriodType.BUDGET_PERIOD_MONTH)){
//			return getStartOfBudgetPeriod(period, DateFunctions.addMonths(DateFunctions.getStartOfMonth(date), -1 * offset));
//		}
//		if (period.equals(BudgetPeriodType.BUDGET_PERIOD_QUARTER)){
//			throw new DataModelProblemException("Quarterly period not implemented!");
//		}
//
//		if (period.equals(BudgetPeriodType.BUDGET_PERIOD_YEAR)){
//			throw new DataModelProblemException("Yearly period not implemented!");
//		}
//
//		throw new DataModelProblemException("Period " + period + " period not implemented!");
//	}
	
	/**
	 * Returns the number of days in the given period.  Depending on the period, this may
	 * be a constant number (BUDGET_PERIOD_WEEK == 7), or a variable number (BUDGET_PERIOD_MONTH
	 * depends on which month).
	 * @param period The butget period type to use
	 * @param startOfBudgetPeriod The date to get the budget period from.
	 * @return
	 */
	public static long getDaysInPeriod(BudgetPeriodType period, Date startOfBudgetPeriod){
		startOfBudgetPeriod = getStartOfBudgetPeriod(period, startOfBudgetPeriod);
		
		//TODO
		if (period.equals(BudgetPeriodType.BUDGET_PERIOD_WEEK)){
			return 7;
		}
		if (period.equals(BudgetPeriodType.BUDGET_PERIOD_FORTNIGHT)){
			throw new DataModelProblemException("Weekly period not implemented!");
		}
		if (period.equals(BudgetPeriodType.BUDGET_PERIOD_MONTH)){
			return DateFunctions.getDaysInMonth(startOfBudgetPeriod);
		}
		if (period.equals(BudgetPeriodType.BUDGET_PERIOD_QUARTER)){
			throw new DataModelProblemException("Quarterly period not implemented!");
		}

		if (period.equals(BudgetPeriodType.BUDGET_PERIOD_YEAR)){
			throw new DataModelProblemException("Yearly period not implemented!");
		}

		throw new DataModelProblemException("Period " + period + " period not implemented!");	
	}
	
//	/**
//	 * Returns a date that is the beginning of the budget period which contains
//	 * the given date.  This depends on the value of getPeriodType.
//	 * @param date
//	 * @return
//	 */
//	public Date getStartOfBudgetPeriod(Date date){
//		return BudgetPeriodUtil.getStartOfBudgetPeriod(getPeriodType(), date);
//	}
//	
//	/**
//	 * Returns a date that is the end of the budget period which contains
//	 * the given date.  This depends on the value of getPeriodType.
//	 * @param date
//	 * @return
//	 */
//	public Date getEndOfBudgetPeriod(Date date){
//		return BudgetPeriodUtil.getEndOfBudgetPeriod(getPeriodType(), date);
//	}
	

	



//	/**
//	 * Returns the budget period object which contains the given date.
//	 * @param periodDate
//	 * @return
//	 */
//	public BudgetPeriod getBudgetPeriod(Date periodDate){
//		return getBudgetPeriod(getPeriodKey(periodDate));
//	}


}
