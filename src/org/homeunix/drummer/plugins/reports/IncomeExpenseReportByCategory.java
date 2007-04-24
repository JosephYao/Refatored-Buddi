/*
 * Created on Sep 14, 2006 by wyatt
 */
package org.homeunix.drummer.plugins.reports;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Vector;

import org.homeunix.drummer.controller.SourceController;
import org.homeunix.drummer.controller.TransactionController;
import org.homeunix.drummer.controller.Translate;
import org.homeunix.drummer.controller.TranslateKeys;
import org.homeunix.drummer.model.Category;
import org.homeunix.drummer.model.Transaction;
import org.homeunix.drummer.plugins.BuddiPluginHelper.DateRangeType;
import org.homeunix.drummer.plugins.interfaces.BuddiReportPlugin;
import org.homeunix.drummer.prefs.PrefsInstance;
import org.homeunix.drummer.util.BudgetCalculator;
import org.homeunix.drummer.util.FormatterWrapper;
import org.homeunix.drummer.view.HTMLExportHelper;
import org.homeunix.drummer.view.HTMLExportHelper.HTMLWrapper;
import org.homeunix.thecave.moss.util.Version;

/**
 * Built-in plugin.  Feel free to use this as an example on how to make
 * report plugins (although this one is kind of ugly, so you may not 
 * want to use it..)
 * 
 * @author wyatt
 *
 */
public class IncomeExpenseReportByCategory implements BuddiReportPlugin {
	
	public HTMLWrapper getReport(Date startDate, Date endDate) {
		StringBuilder sb = HTMLExportHelper.getHtmlHeader(getTitle(), null, startDate, endDate);

		Vector<Category> categories = SourceController.getCategories();
		Collections.sort(categories, new Comparator<Category>(){
			public int compare(Category o1, Category o2) {
				//First we sort by income
				if (o1.isIncome() != o2.isIncome()){
					if (o1.isIncome()){
						return -1;
					}
					else {
						return 1;
					}
				}
								
				//Finally, we sort by Category Name
				return o1.toString().compareTo(o2.toString());
			}
		});
		
		sb.append("<h1>").append(Translate.getInstance().get(TranslateKeys.REPORT_SUMMARY)).append("</h1>\n");
		sb.append("<table class='main'>\n");
		
		sb.append("<tr><th>");
		sb.append(Translate.getInstance().get(TranslateKeys.NAME));
		sb.append("</th><th>");
		sb.append(Translate.getInstance().get(TranslateKeys.ACTUAL));
		sb.append("</th><th>");
		sb.append(Translate.getInstance().get(TranslateKeys.BUDGETED));
		sb.append("</th><th>");
		sb.append(Translate.getInstance().get(TranslateKeys.DIFFERENCE));
		sb.append("</th></tr>\n");
		
		long totalActual = 0, totalBudgeted = 0;
		
		for (Category c : categories){
			Vector<Transaction> transactions = TransactionController.getTransactions(c, startDate, endDate);
			long actual = 0;
			for (Transaction transaction : transactions) {
				actual += transaction.getAmount();
				
				if (transaction.getTo() instanceof Category){
					totalActual -= transaction.getAmount();
				}
				else if (transaction.getFrom() instanceof Category){
					totalActual += transaction.getAmount();
				}
			}

			long budgeted = BudgetCalculator.getEquivalentByInterval(c.getBudgetedAmount(), PrefsInstance.getInstance().getSelectedInterval(), startDate, endDate);
			if (c.isIncome()){
				totalBudgeted += budgeted;
			}
			else {
				totalBudgeted -= budgeted;
			}
			

			if (c.getBudgetedAmount() != 0 || transactions.size() > 0){				
				sb.append("<tr>");
				sb.append("<td>");
				sb.append(Translate.getInstance().get(c.toString()));
				sb.append("</td><td class='right" + (FormatterWrapper.isRed(c, actual) ? " red'>" : "'>"));
				sb.append(FormatterWrapper.getFormattedCurrency(actual));
				sb.append("</td><td class='right" + (FormatterWrapper.isRed(c, budgeted) ? " red'>" : "'>"));
				sb.append(FormatterWrapper.getFormattedCurrency(budgeted));				
				long difference = actual - budgeted;
				sb.append("</td><td class='right" + (difference > 0 ^ c.isIncome() ? " red'>" : "'>"));
				sb.append(FormatterWrapper.getFormattedCurrency(difference, difference < 0));				
				sb.append("</td></tr>\n");
			}
		}
		
		sb.append("<tr><th>");
		sb.append(Translate.getInstance().get(TranslateKeys.TOTAL));
		sb.append("</th><th class='right" + (totalActual < 0 ? " red'>" : "'>"));
		sb.append(FormatterWrapper.getFormattedCurrency(totalActual));
		sb.append("</th><th class='right" + (totalBudgeted < 0 ? " red'>" : "'>"));
		sb.append(FormatterWrapper.getFormattedCurrency(totalBudgeted));
		long totalDifference = totalActual - totalBudgeted; 
		sb.append("</th><th class='right" + (totalDifference < 0 ? " red'>" : "'>"));
		sb.append(FormatterWrapper.getFormattedCurrency(totalDifference));				
		sb.append("</th></tr>\n");

		sb.append("</table>\n\n");
		
		sb.append("<hr>\n");
		
		sb.append("<h1>").append(Translate.getInstance().get(TranslateKeys.REPORT_DETAILS)).append("</h1>\n");
		
		for (Category c : categories){
			Vector<Transaction> transactions = TransactionController.getTransactions(c, startDate, endDate);
			
			
			if (transactions.size() > 0){
				sb.append(c.isIncome() ? "<h2>" : "<h2 class='red'>");
				sb.append(Translate.getInstance().get(c.toString()));
				sb.append("</h2>\n");

				sb.append(HTMLExportHelper.getHtmlTransactionHeader());

				for (Transaction t : transactions) {
					sb.append(HTMLExportHelper.getHtmlTransactionRow(t, c));
				}

				sb.append(HTMLExportHelper.getHtmlTransactionFooter());
			}
		}
		
		sb.append(HTMLExportHelper.getHtmlFooter());
	
		return new HTMLWrapper(sb.toString(), null);
	}
	
	public DateRangeType getDateRangeType() {
		return DateRangeType.INTERVAL;
	}

	public String getTitle() {
		return Translate.getInstance().get(TranslateKeys.REPORT_TITLE_INCOME_AND_EXPENSES_BY_CATEGORY);
	}

	public String getDescription() {
		return TranslateKeys.REPORT_DESCRIPTION_INCOME_EXPENSES_BY_CATEGORY.toString();
	}
	
	public boolean isEnabled() {
		return true;
	}
	public Version getMinimumVersion() {
		return new Version("2.3.4");
	}
}
