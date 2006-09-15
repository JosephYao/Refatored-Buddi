/*
 * Created on Sep 14, 2006 by wyatt
 */
package org.homeunix.drummer.plugins;

public interface BuddiPlugin {
	/**
	 * Returns the type of date range to use in the combo box
	 * @return
	 */
	public BuddiPluginFactory.DateRangeType getDateRangeType();
	
	/**
	 * Returns the title to appear in the JFrame title bar
	 * @return
	 */
	public String getTitle();
	
	/**
	 * Returns the description text, as seen on the main window 
	 * under Reports tab
	 * @return
	 */
	public String getDescription();
}
