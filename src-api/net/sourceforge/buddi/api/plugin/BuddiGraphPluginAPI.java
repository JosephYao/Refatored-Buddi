/*
 * Created on Sep 14, 2006 by wyatt
 * 
 * The interface which can be extended to create custom reports.
 */
package net.sourceforge.buddi.api.plugin;

import java.util.Date;

import org.homeunix.drummer.view.HTMLExportHelper.HTMLWrapper;

public interface BuddiGraphPluginAPI extends BuddiPanelPluginAPI {
	
	/**
	 * Returns an in-memory version of the printed page, as an HTML
	 * file.  An HTMLWrapper is just a small class containing a string
	 * with the HTML text in it, and a map of String to BufferedImage
	 * containing all the images referenced in the HTML, by name.
	 * 
	 * See HTMLWrapper for more information on what is needed. 
	 * @param startDate Start of report period
	 * @param endDate End of report period
	 * @return
	 */
	public HTMLWrapper getGraph(Date startDate, Date endDate);

}
