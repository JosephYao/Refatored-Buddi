/*
 * Created on Apr 7, 2007 by wyatt
 */
package org.homeunix.drummer.util;

import java.util.Date;

import org.homeunix.drummer.prefs.Interval;
import org.homeunix.thecave.moss.util.DateUtil;

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
	public static long getBudgetEquivalentByInterval(long value, Interval interval, Date startDate, Date endDate){
		//Find out how many days are in this interval.
		double daysInInterval = interval.getLength() * (interval.isDays() ? 1 : 30);

		double numberOfBudgetPeriods = (DateUtil.daysBetween(startDate, endDate) + 1) / daysInInterval;
		return (long) (value * numberOfBudgetPeriods);
	}
}
