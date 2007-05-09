/*
 * Created on Sep 14, 2006 by wyatt
 * 
 * The interface which can be extended to create custom reports.
 */
package net.sourceforge.buddi.api.plugin;

import net.roydesign.ui.JScreenMenuItem;

public abstract class BuddiMenuPlugin extends JScreenMenuItem implements BuddiFilePlugin, AutoRefreshPlugin {

	/* (non-Javadoc)
	 * @see net.sourceforge.buddi.api.plugin.AutoRefreshPlugin#getRefreshInterval()
	 */
	public int getRefreshInterval() {
		return 2;
	}
}
