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
import org.homeunix.drummer.view.HTMLExportHelper;
import org.homeunix.drummer.view.HTMLExportHelper.HTMLWrapper;
import org.homeunix.thecave.moss.util.Formatter;

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

		sb.append("<hr>\n");
		
		sb.append("<h1>").append(Translate.getInstance().get(TranslateKeys.SUMMARY)).append("</h1>\n");
		sb.append("<table class='main'>\n");
		
		sb.append("<tr><th>");
		sb.append(Translate.getInstance().get(TranslateKeys.NAME));
		sb.append("</th><th>");
		sb.append(Translate.getInstance().get(TranslateKeys.BUDGETED));
		sb.append("</th><th>");
		sb.append(Translate.getInstance().get(TranslateKeys.ACTUAL));
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

			long budgeted = BudgetCalculator.getBudgetEquivalentByInterval(c.getBudgetedAmount(), PrefsInstance.getInstance().getSelectedInterval(), startDate, endDate);
			if (c.isIncome()){
				totalBudgeted -= budgeted;
			}
			else {
				totalBudgeted += budgeted;
			}
			

			if (c.getBudgetedAmount() > 0 || transactions.size() > 0){				
				sb.append("<tr>");
				sb.append(c.isIncome() ? "<td>" : "<td class='red'>");
				sb.append(Translate.getInstance().get(c.toString()));
				sb.append(c.isIncome() ? "</td><td class='right'>" : "</td><td class='red right'>");
				sb.append(Translate.getFormattedCurrency(budgeted, false));
				sb.append(c.isIncome() ? "</td><td class='right'>" : "</td><td class='red right'>");
				sb.append(Translate.getFormattedCurrency(actual, false));				
				sb.append(!c.isIncome() ^ ((budgeted - actual) < 0) ? "</td><td class='right'>" : "</td><td class='red right'>");
				sb.append(Translate.getFormattedCurrency(budgeted - actual, c.isIncome()));				
				sb.append("</td></tr>\n");
			}
		}
		
		sb.append("<tr><th>");
		sb.append(Translate.getInstance().get(TranslateKeys.TOTAL));
		sb.append(totalBudgeted > 0 ? "</th><th>" : "</th><th class='red'>");
		sb.append(Translate.getFormattedCurrency(totalBudgeted, false));
		sb.append(totalActual > 0 ? "</th><th>" : "</th><th class='red'>");
		sb.append(Translate.getFormattedCurrency(totalActual, false));				
		sb.append((totalBudgeted - totalActual) > 0 ? "</th><th>" : "</th><th class='red'>");
		sb.append(Translate.getFormattedCurrency(totalBudgeted - totalActual, false));				
		sb.append("</th></tr>\n");

		sb.append("</table>\n\n");
		
		sb.append("<hr>\n");
		
		sb.append("<h1>").append(Translate.getInstance().get(TranslateKeys.DETAILS)).append("</h1>\n");
		
		for (Category c : categories){
			Vector<Transaction> transactions = TransactionController.getTransactions(c);
			
			
			if (transactions.size() > 0){
				sb.append(c.isIncome() ? "<h2>" : "<h2 class='red'>");
				sb.append(Translate.getInstance().get(c.toString()));
				sb.append("</h2>\n");

				sb.append("<table class='main'>\n");

				sb.append("<tr><th>");
				sb.append(Translate.getInstance().get(TranslateKeys.DATE));
				sb.append("</th><th>");
				sb.append(Translate.getInstance().get(TranslateKeys.DESCRIPTION));
				sb.append("</th><th>");
				sb.append(Translate.getInstance().get(TranslateKeys.SOURCE_TO_FROM));
				sb.append("</th><th>");
				sb.append(Translate.getInstance().get(TranslateKeys.AMOUNT));
				sb.append("</th></tr>\n");


				for (Transaction t : transactions) {
					sb.append("<tr><td>");
					sb.append(Formatter.getInstance().getDateFormat().format(t.getDate()));
					
					sb.append("</td><td>");
					sb.append(Translate.getInstance().get(t.getDescription()));
					
					sb.append("</td><td>");
					sb.append(Translate.getInstance().get(t.getFrom().toString()));
					sb.append(Translate.getInstance().get(TranslateKeys.TO_HTML_SAFE));
					sb.append(Translate.getInstance().get(t.getTo().toString()));
					
					sb.append(t.getFrom() instanceof Category ? "</td><td>" : "</td><td class='red'>");
					sb.append(Translate.getFormattedCurrency(t.getAmount(), false));

					sb.append("</td></tr>\n");
				}

				sb.append("</table>\n\n");
			}
		}
		
		
		return new HTMLWrapper(sb.toString(), null);
	}
	
	public DateRangeType getDateRangeType() {
		return DateRangeType.INTERVAL;
	}

	public String getTitle() {
		return Translate.getInstance().get(TranslateKeys.INCOME_AND_EXPENSES_BY_CATEGORY_TITLE);
	}

	public String getDescription() {
		return TranslateKeys.REPORT_INCOME_EXPENSES_BY_CATEGORY.toString();
	}
}
