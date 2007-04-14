/*
 * Created on Oct 3, 2006 by wyatt
 */
package org.homeunix.drummer.plugins.interfaces;

import org.homeunix.drummer.plugins.BuddiPluginHelper.DateRangeType;

public interface BuddiPanelPlugin extends BuddiPlugin {
	/**
	 * Returns the type of date range to use in the combo box.
	 * Only used for plugin types Report or Graph; not used in Import
	 * or Export.
	 * @return
	 */
	public DateRangeType getDateRangeType();
	
	/**
	 * Returns the title to appear in the JFrame title bar.  Not
	 * used in plugins of type Export or Import.
	 * @return
	 */
	public String getTitle();
	
	/**
	 * Should this plugin be added to the panel?  Most people can just
	 * put true here; if there is some logic which determines if this
	 * is to be shown or not, though, you can add it here.
	 * @return
	 */
	public boolean isEnabled();
}
