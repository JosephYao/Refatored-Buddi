/*
 * Created on Oct 3, 2006 by wyatt
 */
package org.homeunix.drummer.plugins;

public interface BuddiPanelPlugin extends BuddiPlugin {
	/**
	 * Returns the type of date range to use in the combo box.
	 * Only used for plugin types Report or Graph; not used in Import
	 * or Export.
	 * @return
	 */
	public BuddiPluginFactory.DateRangeType getDateRangeType();
	
	/**
	 * Returns the title to appear in the JFrame title bar.  Not
	 * used in plugins of type Export or Import.
	 * @return
	 */
	public String getTitle();
}
