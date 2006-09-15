/*
 * Created on Sep 14, 2006 by wyatt
 * 
 * The interface which can be extended to create custom reports.
 */
package org.homeunix.drummer.plugins;

import java.util.Date;

import javax.swing.JPanel;

public interface BuddiGraphPlugin extends BuddiPlugin {
	
	/**
	 * Returns a JPanel with the graph in it.
	 * @param startDate Start of report period
	 * @param endDate End of report period
	 * @return
	 */
	public JPanel getGraphPanel(Date startDate, Date endDate);

}
