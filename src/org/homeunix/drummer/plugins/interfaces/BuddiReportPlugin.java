/*
 * Created on Sep 14, 2006 by wyatt
 * 
 * The interface which can be extended to create custom reports.
 */
package org.homeunix.drummer.plugins.interfaces;

import java.util.Date;
import java.util.List;

import javax.swing.table.TableCellRenderer;
import javax.swing.tree.TreeCellRenderer;

import org.homeunix.drummer.view.HTMLExportHelper.HTMLWrapper;
import org.jdesktop.swingx.treetable.TreeTableModel;

public interface BuddiReportPlugin extends BuddiPanelPlugin {
	
//	public AbstractTableModel getReportData(Date startDate, Date endDate);
	
	/**
	 * Returns the data model used to display the report.
	 * @param startDate Start of report period
	 * @param endDate End of report period
	 * @return
	 */
	public TreeTableModel getTreeTableModel(Date startDate, Date endDate);
			
	public List<TableCellRenderer> getTableCellRenderers();
	
	public TreeCellRenderer getTreeCellRenderer();
	
	/**
	 * Returns a string containing HTML code (with CSS if desired).
	 * This will be saved to a temporary file and opened in a browser.
	 * @param startDate Start of 
	 * @param endDate
	 * @return
	 */
	public HTMLWrapper getHTML(Date startDate, Date endDate);
}
