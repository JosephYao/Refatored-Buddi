/*
 * DateUtil - collection of date related functions.
 *  Downloaded from http://www.koders.com/
 */

package org.homeunix.drummer.util;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtil {
	static GregorianCalendar calendar = new GregorianCalendar();
	public static Date getStartOfDay(Date date){
		calendar.setTime(date);
		calendar.set(GregorianCalendar.HOUR_OF_DAY, calendar.getActualMinimum(GregorianCalendar.HOUR_OF_DAY));
		calendar.set(GregorianCalendar.MINUTE, calendar.getActualMinimum(GregorianCalendar.MINUTE));
		calendar.set(GregorianCalendar.SECOND, calendar.getActualMinimum(GregorianCalendar.SECOND));
		calendar.set(GregorianCalendar.MILLISECOND, calendar.getActualMinimum(GregorianCalendar.MILLISECOND));		
		return (Date) calendar.getTime().clone();
	}
	public static Date getEndOfDay(Date date){
		calendar.setTime(date);
		calendar.set(GregorianCalendar.HOUR_OF_DAY, calendar.getActualMaximum(GregorianCalendar.HOUR_OF_DAY));
		calendar.set(GregorianCalendar.MINUTE, calendar.getActualMaximum(GregorianCalendar.MINUTE));
		calendar.set(GregorianCalendar.SECOND, calendar.getActualMaximum(GregorianCalendar.SECOND));
		calendar.set(GregorianCalendar.MILLISECOND, calendar.getActualMaximum(GregorianCalendar.MILLISECOND));		
		return (Date) calendar.getTime().clone();
	}
	public static Date getNextDay(Date date){
		calendar.setTime(date);
		calendar.add(GregorianCalendar.DAY_OF_MONTH, 1);
		return (Date) calendar.getTime().clone();
	}
	public static Date getNextNDay(Date date, int n){
		calendar.setTime(date);
		calendar.add(GregorianCalendar.DAY_OF_MONTH, n);
		return (Date) calendar.getTime().clone();
	}
	public static Date getBeginOfMonth(Date date, int months){
		calendar.setTime(date);
		if(months!=0)
			calendar.add(GregorianCalendar.MONTH, months);
		calendar.set(GregorianCalendar.DAY_OF_MONTH, calendar.getActualMinimum(GregorianCalendar.DAY_OF_MONTH));
		return (Date) calendar.getTime().clone();
	}
	
	public static Date getEndOfMonth(Date date, int months){
		calendar.setTime(date);
		if(months!=0)
			calendar.add(GregorianCalendar.MONTH, months);
		calendar.set(GregorianCalendar.DAY_OF_MONTH, calendar
				.getActualMaximum(GregorianCalendar.DAY_OF_MONTH));
		return (Date) calendar.getTime().clone();
	}
	
	public static Date getBeginOfYear(int year){
		calendar.set(GregorianCalendar.YEAR, year);
		calendar.set(GregorianCalendar.MONTH, calendar
				.getActualMinimum(GregorianCalendar.MONTH));
		calendar.set(GregorianCalendar.DAY_OF_MONTH, calendar
				.getActualMinimum(GregorianCalendar.DAY_OF_MONTH));
		calendar.set(GregorianCalendar.HOUR_OF_DAY, calendar.getActualMinimum(GregorianCalendar.HOUR_OF_DAY));
		calendar.set(GregorianCalendar.MINUTE, calendar.getActualMinimum(GregorianCalendar.MINUTE));
		calendar.set(GregorianCalendar.SECOND, calendar.getActualMinimum(GregorianCalendar.SECOND));
		calendar.set(GregorianCalendar.MILLISECOND, calendar.getActualMinimum(GregorianCalendar.MILLISECOND));		
		return (Date) calendar.getTime().clone();
	}

	
	public static Date getBeginOfYear(Date date){
		calendar.setTime(date);
		calendar.set(GregorianCalendar.MONTH, calendar
				.getActualMinimum(GregorianCalendar.MONTH));
		calendar.set(GregorianCalendar.DAY_OF_MONTH, calendar
				.getActualMinimum(GregorianCalendar.DAY_OF_MONTH));
		calendar.set(GregorianCalendar.HOUR_OF_DAY, calendar.getActualMinimum(GregorianCalendar.HOUR_OF_DAY));
		calendar.set(GregorianCalendar.MINUTE, calendar.getActualMinimum(GregorianCalendar.MINUTE));
		calendar.set(GregorianCalendar.SECOND, calendar.getActualMinimum(GregorianCalendar.SECOND));
		calendar.set(GregorianCalendar.MILLISECOND, calendar.getActualMinimum(GregorianCalendar.MILLISECOND));		
		return (Date) calendar.getTime().clone();
	}
	
	public static Date getEndOfYear(Date date) {
		calendar.setTime(date);
		calendar.set(GregorianCalendar.MONTH, calendar
				.getActualMaximum(GregorianCalendar.MONTH));
		calendar.set(GregorianCalendar.DAY_OF_MONTH, calendar
				.getActualMaximum(GregorianCalendar.DAY_OF_MONTH));
		calendar.set(GregorianCalendar.HOUR_OF_DAY, calendar.getActualMaximum(GregorianCalendar.HOUR_OF_DAY));
		calendar.set(GregorianCalendar.MINUTE, calendar.getActualMaximum(GregorianCalendar.MINUTE));
		calendar.set(GregorianCalendar.SECOND, calendar.getActualMaximum(GregorianCalendar.SECOND));
		calendar.set(GregorianCalendar.MILLISECOND, calendar.getActualMaximum(GregorianCalendar.MILLISECOND));		
		return (Date) calendar.getTime().clone();
	}
	public static int getYearOfDate(Date date){
		return calendar.get(Calendar.YEAR);
	}
	
	public static Date getEndOfYear(int year) {
		calendar.set(GregorianCalendar.YEAR, year);
		calendar.set(GregorianCalendar.MONTH, calendar
				.getActualMaximum(GregorianCalendar.MONTH));
		calendar.set(GregorianCalendar.DAY_OF_MONTH, calendar
				.getActualMaximum(GregorianCalendar.DAY_OF_MONTH));
		calendar.set(GregorianCalendar.HOUR_OF_DAY, calendar.getActualMaximum(GregorianCalendar.HOUR_OF_DAY));
		calendar.set(GregorianCalendar.MINUTE, calendar.getActualMaximum(GregorianCalendar.MINUTE));
		calendar.set(GregorianCalendar.SECOND, calendar.getActualMaximum(GregorianCalendar.SECOND));
		calendar.set(GregorianCalendar.MILLISECOND, calendar.getActualMaximum(GregorianCalendar.MILLISECOND));		
		return (Date) calendar.getTime().clone();
	}
	
	public static int monthsBetween(Date startDate, Date endDate) {
		calendar.setTime(startDate);
		int startMonth = calendar.get(GregorianCalendar.MONTH);
		int startYear = calendar.get(GregorianCalendar.YEAR);
		calendar.setTime(endDate);
		int endMonth = calendar.get(GregorianCalendar.MONTH);
		int endYear = calendar.get(GregorianCalendar.YEAR);
		return (endYear-startYear)*12 + (endMonth-startMonth);
	}
	
	public static int daysBetween(Date startDate, Date endDate) {	
		Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        c1.setTime(startDate);
        c2.setTime(endDate);
        return daysBetween(c1, c2);	
	}
	
	public static int daysBetween(Calendar early, Calendar late) {
		return (int) (toJulian(late) - toJulian(early));
	}

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
