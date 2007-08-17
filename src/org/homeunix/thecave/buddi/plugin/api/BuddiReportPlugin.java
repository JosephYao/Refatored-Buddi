/*
 * Created on Sep 14, 2006 by wyatt
 * 
 * The interface which can be extended to create custom reports.
 */
package org.homeunix.thecave.buddi.plugin.api;

import java.util.Date;

import org.homeunix.thecave.buddi.plugin.api.model.immutable.ImmutableModel;
import org.homeunix.thecave.buddi.plugin.api.util.HtmlHelper.HtmlPage;
import org.homeunix.thecave.moss.plugin.MossPlugin;


public abstract class BuddiReportPlugin implements MossPlugin {
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
	public abstract HtmlPage getReport(ImmutableModel model, Date startDate, Date endDate);
}
