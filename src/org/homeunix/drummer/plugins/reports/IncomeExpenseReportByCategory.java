/*
 * Created on Sep 14, 2006 by wyatt
 */
package org.homeunix.drummer.plugins.reports;

import java.util.Date;

import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreeModel;

import org.homeunix.drummer.controller.TranslateKeys;
import org.homeunix.drummer.plugins.BuddiPluginFactory;
import org.homeunix.drummer.plugins.BuddiReportPlugin;
import org.homeunix.drummer.plugins.BuddiPluginFactory.DateRangeType;

public class IncomeExpenseReportByCategory implements BuddiReportPlugin {
	public String getReportHTML(Date startDate, Date endDate) {
		// TODO Auto-generated method stub
		return null;
	}

	public TreeModel getReportTreeModel(Date startDate, Date endDate) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public TreeCellRenderer getTreeCellRenderer() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public DateRangeType getDateRangeType() {
		return BuddiPluginFactory.DateRangeType.INTERVAL;
	}
	
	public String getTitle() {
		return "My Report";
	}
	
	public String getDescription() {
		return TranslateKeys.REPORT_INCOME_EXPENSES_BY_CATEGORY.toString();
	}
}
