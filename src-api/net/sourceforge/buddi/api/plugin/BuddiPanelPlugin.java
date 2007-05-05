/*
 * Created on Oct 3, 2006 by wyatt
 */
package net.sourceforge.buddi.api.plugin;

import javax.swing.JPanel;

import org.homeunix.drummer.plugins.BuddiPluginHelper.DateRangeType;

public abstract class BuddiPanelPlugin extends JPanel implements BuddiPlugin {
	
	public static final long serialVersionUID = 0;
	
	/**
	 * Returns the type of date range to use in the combo box.
	 * Only used for plugin types Report or Graph; not used in Import
	 * or Export.
	 * @return
	 */
	public abstract DateRangeType getDateRangeType();
	
	/**
	 * @return The title to appear in report.
	 */
	public abstract String getTitle();	
}
