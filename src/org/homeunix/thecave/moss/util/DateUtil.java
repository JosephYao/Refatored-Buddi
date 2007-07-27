/*
 * Created on Jul 26, 2007 by wyatt
 */
package org.homeunix.thecave.moss.util;

import java.util.Calendar;
import java.util.Date;

/**
 * @author wyatt
 * Compatibility class to ease transition to new Moss version for old plugins.  Will be 
 * removed in the future - if you are developing new plugins, or upgrading old ones,
 * you should use the class DateFunctions in Moss instead of this - it has been 
 * completely re-written by me, and I have found and fixed a number of bugs which were
 * present in this DateUtil implementation.
 * 
 *  @deprecated
 */
public class DateUtil {
	
	/**
	 * Returns the beginning of the given day (i.e., 0:00)
	 * @param date
	 * @return
	 */
	public static Date getStartOfDay(Date date){
		return DateFunctions.getStartOfDay(date);
	}
	
	/**
	 * Returns the end of the given day (i.e., 23:59)
	 * @param date
	 * @return
	 */
	public static Date getEndOfDay(Date date){
		return DateFunctions.getEndOfDay(date);
	}
	
	/**
	 * Returns the next day (i.e., today + 24 hours)
	 * @param date
	 * @return
	 */
	public static Date getNextDay(Date date){
		return DateFunctions.addDays(date, 1);
	}
	
	/**
	 * Returns the next N days (i.e., today + (24 hours * N)).  N can be negative.
	 * @param date
	 * @param n
	 * @return
	 */
	public static Date getNextNDay(Date date, int n){
		return DateFunctions.addDays(date, n);
	}
	
	/**
	 * Returns the first day of the month in which the given day is found.  
	 * If months parameter is not 0, then that number is also added to the months.  
	 * @param date
	 * @param months
	 * @return
	 */
	public static Date getBeginOfMonth(Date date, int months){
		return DateFunctions.getStartOfMonth(DateFunctions.addMonths(date, months));
	}
	
	/**
	 * Returns the last day of the month in which the given day is found.  
	 * If months parameter is not 0, then that number is also added to the months.  
	 * @param date
	 * @param months
	 * @return
	 */
	public static Date getEndOfMonth(Date date, int months){
		return DateFunctions.getEndOfMonth(DateFunctions.addMonths(date, months));
	}
	
	/**
	 * Returns the date at the beginning of the quarter, modified by quarterOffset.
	 * @param date Date to use
	 * @param quarterOffset How many quarters before (negative) / after (positive) to use.
	 * @return
	 */
	public static Date getBeginOfQuarter(Date date, int quarterOffset){
		return DateFunctions.getStartOfQuarter(date, quarterOffset);
	}
	
	/**
	 * Returns the date at the end of the quarter, modified by quarterOffset.
	 * @param date Date to use
	 * @param quarterOffset How many quarters before (negative) / after (positive) to use.
	 * @return
	 */
	public static Date getEndOfQuarter(Date date, int quarterOffset){
		return DateFunctions.getEndOfQuarter(date, quarterOffset);
	}
	
	/**
	 * Returns the day at the beginning of the given year.
	 * @param year
	 * @return
	 */
	public static Date getBeginOfYear(int year){
		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, year);
		return DateFunctions.getStartOfYear(c.getTime());
	}

	
	/**
	 * Get the day at the beginning of the year in which the given day resides
	 * @param date
	 * @return
	 */
	public static Date getBeginOfYear(Date date){
		return DateFunctions.getStartOfYear(date);
	}
	
	/**
	 * Returns the day at the end of the year in which the given day resides
	 * @param date
	 * @return
	 */
	public static Date getEndOfYear(Date date) {
		return DateFunctions.getEndOfYear(date);
	}
	
	public static Date getStartOfYear(Date date){
		return DateFunctions.getStartOfYear(date);
	}
	
	public static int getYearOfDate(Date date){
		return DateFunctions.getYear(date);
	}
	
	public static Date getEndOfYear(int year) {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, year);
		return DateFunctions.getEndOfYear(c.getTime());
	}
	
	/**
	 * Returns the number of months in between the given dates
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static int monthsBetween(Date startDate, Date endDate) {
		return DateFunctions.getMonthsBetween(startDate, endDate, false);
	}
	
	/**
	 * Returns the number of days in between the given dates
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static int daysBetween(Date startDate, Date endDate) {	
		return DateFunctions.getDaysBetween(startDate, endDate, false);
	}
	
	public static int daysBetweenInclusive(Date startDate, Date endDate){
		return DateFunctions.getDaysBetween(startDate, endDate, true);
	}
	
	/**
	 * Returns the number of days in between the given days
	 * @param early
	 * @param late
	 * @return
	 */
	public static int daysBetween(Calendar start, Calendar end) {
		return DateFunctions.getDaysBetween(start.getTime(), end.getTime(), false);
	}
	
	public static int daysBetweenInclusive(Calendar start, Calendar end){
		return DateFunctions.getDaysBetween(start.getTime(), end.getTime(), true);
	}

	/**
	 * Converts the given calendar to Julian
	 * @param c
	 * @return
	 */
	public static final float toJulian(Calendar c) {
        int Y = c.get(Calendar.YEAR);
        int M = c.get(Calendar.MONTH);
        int D = c.get(Calendar.DATE);
        int A = Y / 100;
        int B = A / 4;
        int C = 2 - A + B;
        float E = (int) (365.25f * (Y + 4716));
        float F = (int) (30.6001f * (M + 1));
        float JD = (C + D + E + F) - 1524.5f;
        return JD;
    }
}
