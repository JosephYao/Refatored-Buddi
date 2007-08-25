/*
 * Created on Apr 7, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.plugin.api.util;

import java.util.Date;

import org.homeunix.thecave.buddi.i18n.keys.BudgetPeriodKeys;
import org.homeunix.thecave.buddi.util.BudgetPeriodUtil;
import org.homeunix.thecave.moss.util.DateFunctions;

public class BudgetCalculator {
	
	/**
	 * Returns an adjusted value based on the given date, and the currently
	 * selected budget interval.
	 * 
	 * For instance, if you pass in a value of 100, with a budget interval
	 * of Month, and a start and end date 60 days apart, it will return
	 * approximately 200 (since 100 / interval * 2 intervals = 200).
	 *  
	 * @param value
	 * @param interval
	 * @param startDate
	 * @param endDate
	 * @return
	 */
//	//TODO I have no idea if this works properly, or even if this makes sense with the new model.
//	public static long getEquivalentByInterval(long value, BudgetPeriodKeys period, Date startDate, Date endDate){
//		//Find out how many days are in this interval.
//		long daysInInterval = BudgetPeriodUtil.getDaysInInterval(period, BudgetPeriodUtil.getStartOfBudgetPeriod(period, startDate));
//		
//		double numberOfBudgetPeriods = 
//			((double) DateFunctions.getDaysBetween(startDate, endDate, true)) / daysInInterval;
//		System.out.println(numberOfBudgetPeriods);
//		return (long) (value * numberOfBudgetPeriods);
//	}
	
	/**
	 * Returns the average amount spread across the given interval.  For
	 * instance, if you passed in a value of 1000 and an interval of
	 * Month, with start and end dates spanning 10 months, it will return
	 * 1000 * 30 days / 300 days = 100.
	 * 
	 * Note that we assume a month is 30 for simplicity; this should be 
	 * close enough for our purposes.  If it turns out it is not, we can 
	 * change this later and update everything which relies on it 
	 * automatically.
	 *  
	 * @param value
	 * @param interval
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	//TODO I have no idea if this works properly, or even if this makes sense with the new model.
	public static long getAverageByInterval(long value, BudgetPeriodKeys period, Date startDate, Date endDate){
		long daysInInterval = BudgetPeriodUtil.getDaysInPeriod(period, BudgetPeriodUtil.getStartOfBudgetPeriod(period, startDate));		
		
		long daysBetweenDates =
			DateFunctions.getDaysBetween(startDate, endDate, true);
		
		double average = ((double) (value * daysInInterval)) / (double) daysBetweenDates;
		
		return (long) average;
	}
	

}
