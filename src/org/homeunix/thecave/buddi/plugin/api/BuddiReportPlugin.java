/*
 * Created on Sep 14, 2006 by wyatt
 * 
 * The interface which can be extended to create custom reports.
 */
package org.homeunix.thecave.buddi.plugin.api;

import java.util.Date;

import org.homeunix.thecave.buddi.i18n.keys.PluginReportDateRangeChoices;
import org.homeunix.thecave.buddi.plugin.api.model.impl.ImmutableModelImpl;
import org.homeunix.thecave.buddi.plugin.api.util.HtmlHelper.HtmlPage;
import org.homeunix.thecave.moss.plugin.MossPlugin;


public abstract class BuddiReportPlugin implements MossPlugin {
	/**
	 * Returns an in-memory version of the printed page, as an HTML
	 * file.  An HtmlPage object is just a small class containing a string
	 * with the HTML text in it, and a map of String to BufferedImage
	 * containing all the images referenced in the HTML, by name.
	 * 
	 * See HtmlPage for more information on what is needed. 
	 * @param startDate Start of report period
	 * @param endDate End of report period
	 * @return
	 */
	public abstract HtmlPage getReport(ImmutableModelImpl model, Date startDate, Date endDate);
	
	/**
	 * The type of plugin this should be.  Can choose between one of the following enum
	 * values:
	 * 
	 * 	INTERVAL,
	 *  START_ONLY,
	 *  END_ONLY
	 *  
	 * @return A constant enum value which determines how the plugin loader should display this plugin.
	 */
	public abstract PluginReportDateRangeChoices getDateRangeChoice();
}
