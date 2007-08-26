/*
 * Created on Aug 26, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.plugin.api;

import java.util.Date;

import org.homeunix.thecave.moss.plugin.MossPlugin;
import org.homeunix.thecave.moss.util.Version;

public abstract class BuddiBudgetPeriodTypePlugin implements MossPlugin {

	public String getDescription() {
		return "Budget Period Type definition";
	}

	public Version getMaximumVersion() {
		return null;
	}

	public Version getMinimumVersion() {
		return null;
	}

	public boolean isPluginActive() {
		return true;
	}

	/**
	 * Method to move forwards or backwards by the given number of periods.  This 
	 * must conform to the following statements:
	 * 
	 * If offset is 0, return the same date
	 * If offset is positive, add that many budget periods.  For instance, if 
	 * the value 3 is given, MONTHLY period is selected, and the current date
	 * is August 1, the return date will be November 1 (August + 3).
	 * If offset is negative, subtract that many budget periods.  The inverse
	 * of this must be the same as positive; for instance, if you are given 
	 * a date and you add '-3' to it, and then add '3' again, you must arrive 
	 * at the same date as you started with.
	 * 
	 * @param date
	 * @param offset
	 * @return
	 */
	public abstract Date getBudgetPeriodOffset(Date date, int offset);
	
	/**
	 * Returns the number of days in the period specified by date.  The given 
	 * date can be any date within the budget period.  For instance, if this
	 * class defines a Monthly type, passing in a date of August 4 2007 would
	 * return the value 31; passing in the date of September 20 2009 would 
	 * return the value 30.   
	 * @param date
	 * @return
	 */
	public abstract long getDaysInPeriod(Date date);
	
	/**
	 * Returns the date format associated with this budget period type.  This is
	 * used in the My Budgets window to format the date spinner and the column 
	 * names.  For information on how to display the format, please see the
	 * SimpleDateFormatter javadocs.  For instance, for Monthly type, the
	 * value would be "MMM yyyy" (to show Aug 2007).  For the Weekly type, since
	 * we want to show days as well, the format would be "dd MMM yyyy". 
	 * @return
	 */
	public abstract String getDateFormat();
	
	/**
	 * Returns the start of the budget period which contains the given date.
	 * @param date
	 * @return
	 */
	public abstract Date getStartOfBudgetPeriod(Date date);
	
	/**
	 * Returns the end of the budget period which contains the given date.
	 * @param date
	 * @return
	 */
	public abstract Date getEndOfBudgetPeriod(Date date);
}
