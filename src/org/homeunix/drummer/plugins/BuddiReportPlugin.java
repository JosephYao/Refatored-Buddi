/*
 * Created on Sep 14, 2006 by wyatt
 * 
 * The interface which can be extended to create custom reports.
 */
package org.homeunix.drummer.plugins;

import java.util.Date;

import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreeModel;

public interface BuddiReportPlugin extends BuddiPlugin {
	
	/**
	 * Returns a treemodel used to display the report.
	 * @param startDate Start of report period
	 * @param endDate End of report period
	 * @return
	 */
	public TreeModel getReportTreeModel(Date startDate, Date endDate);
	
	/**
	 * Returns the tree cell renderer used to draw the ReportTreeModel
	 * @return
	 */
	public TreeCellRenderer getTreeCellRenderer();
	
	/**
	 * Returns a string containing HTML code (with CSS if desired).
	 * This will be saved to a temporary file and opened in a browser.
	 * @param startDate Start of 
	 * @param endDate
	 * @return
	 */
	public String getReportHTML(Date startDate, Date endDate);
}
