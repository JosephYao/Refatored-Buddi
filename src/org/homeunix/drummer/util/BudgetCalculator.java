/*
 * Created on Apr 7, 2007 by wyatt
 */
package org.homeunix.drummer.util;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.homeunix.drummer.Const;
import org.homeunix.drummer.prefs.Interval;
import org.homeunix.thecave.moss.util.DateFunctions;
import org.homeunix.thecave.moss.util.Log;

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
		long daysInInterval = getDaysInInterval(interval, startDate, endDate);
		System.out.println(daysInInterval + ", " + DateFunctions.getDaysBetween(startDate, endDate, true));
		
		double numberOfBudgetPeriods = 
			((double) DateFunctions.getDaysBetween(startDate, endDate, true)) / daysInInterval;
		System.out.println(numberOfBudgetPeriods);
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
		long daysInInterval = getDaysInInterval(interval, startDate, endDate);		
		
		long daysBetweenDates =
			DateFunctions.getDaysBetween(startDate, endDate, true);
		
		double average = ((double) (value * daysInInterval)) / (double) daysBetweenDates;
		
		return (long) average;
	}
	
	private static long getDaysInInterval(Interval interval, Date startDate, Date endDate){
		long daysInInterval;
		
		Calendar start = new GregorianCalendar();
		Calendar end = new GregorianCalendar();
		start.setTime(startDate);
		end.setTime(endDate);
		
		//If the interval is measured in days, we just pass that in.
		if (interval.isDays()){
			daysInInterval = interval.getLength();
		}
		//The start and the end are not in the same month.  For now,
		// we just return the length * 30, as otherwise would
		// require all sorts of special case code.  The longer
		// range the forecase, the more inaccurate it will be 
		// anyways, so this should not be too much of a loss.
		else if (start.get(Calendar.YEAR) != end.get(Calendar.YEAR)
				|| start.get(Calendar.MONTH) != end.get(Calendar.MONTH)){
			if (Const.DEVEL) Log.debug("Start != End month; assuming 30 days for average budget");
			daysInInterval = interval.getLength() * 30;
		}
		//The start and end are in the same month; return the number 
		// of days in that month.
		else {			
			daysInInterval = start.getActualMaximum(Calendar.DAY_OF_MONTH);
			if (Const.DEVEL) Log.debug("Start == End month; " + daysInInterval + " accurate for month.");
		}
		
		return daysInInterval;
	}
}
