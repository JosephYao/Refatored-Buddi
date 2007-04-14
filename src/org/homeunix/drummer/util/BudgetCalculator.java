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
	public static long getEquivalentByInterval(long value, Interval interval, Date startDate, Date endDate){
		//Find out how many days are in this interval.
		double daysInInterval = 
			interval.getLength() * (interval.isDays() ? 1 : 30);

		double numberOfBudgetPeriods = 
			(DateUtil.daysBetween(startDate, endDate) + 1) / daysInInterval;
		return (long) (value * numberOfBudgetPeriods);
	}
	
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
	public static long getAverageByInterval(long value, Interval interval, Date startDate, Date endDate){
		long daysInInterval = 
			interval.getLength() * (interval.isDays() ? 1 : 30);
		long daysBetweenDates =
			DateUtil.daysBetween(startDate, endDate);
		
		
		double average = (value * daysInInterval) / (double) daysBetweenDates;
		
		return (long) average;
	}
}
