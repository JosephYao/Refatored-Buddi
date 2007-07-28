/*
 * Created on Sep 14, 2006 by wyatt
 */
package org.homeunix.drummer.plugins.reports;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Vector;

import net.sourceforge.buddi.api.manager.APICommonFormatter;
import net.sourceforge.buddi.api.manager.APICommonHTMLHelper;
import net.sourceforge.buddi.api.manager.DataManager;
import net.sourceforge.buddi.api.manager.APICommonHTMLHelper.HTMLWrapper;
import net.sourceforge.buddi.api.manager.DateRangeType;
import net.sourceforge.buddi.api.plugin.BuddiReportPlugin;
import net.sourceforge.buddi.impl_2_6.model.ImmutableCategoryImpl;
import net.sourceforge.buddi.impl_2_6.model.ImmutableTransactionImpl;

import org.homeunix.drummer.controller.SourceController;
import org.homeunix.drummer.controller.TransactionController;
import org.homeunix.drummer.controller.Translate;
import org.homeunix.drummer.controller.TranslateKeys;
import org.homeunix.drummer.model.Category;
import org.homeunix.drummer.model.Transaction;
import org.homeunix.drummer.prefs.PrefsInstance;
import org.homeunix.drummer.util.BudgetCalculator;
import org.homeunix.thecave.moss.util.Version;

/**
 * Built-in plugin.  Feel free to use this as an example on how to make
 * report plugins
 * 
 * @author wyatt
 *
 */
public class AverageIncomeExpenseByCategory extends BuddiReportPlugin {
	
	public static final long serialVersionUID = 0;
	
	public HTMLWrapper getReport(DataManager dataManager, Date startDate, Date endDate) {
		StringBuilder sb = APICommonHTMLHelper.getHtmlHeader(getTitle(), null, startDate, endDate);

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
		sb.append(Translate.getInstance().get(TranslateKeys.AVERAGE));
		sb.append(" / ");
		sb.append(Translate.getInstance().get(PrefsInstance.getInstance().getSelectedInterval().getName()));
		sb.append("</th></tr>\n");
		
		long totalActual = 0, totalAverage = 0;
		
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

			long average = BudgetCalculator.getAverageByInterval(actual, PrefsInstance.getInstance().getSelectedInterval(), startDate, endDate);
			if (c.isIncome()){
				totalAverage += average;
			}
			else {
				totalAverage -= average;
			}
			

			if (c.getBudgetedAmount() != 0 || transactions.size() > 0){				
				sb.append("<tr>");
				sb.append("<td>");
				sb.append(Translate.getInstance().get(c.toString()));
				sb.append("</td><td class='right" + (APICommonFormatter.isRed(new ImmutableCategoryImpl(c), actual) ? " red'" : "'") + ">");
				sb.append(APICommonFormatter.getFormattedCurrency(actual));
				sb.append("</td><td class='right" + (APICommonFormatter.isRed(new ImmutableCategoryImpl(c), average) ? " red'" : "'") + "'>");
				sb.append(APICommonFormatter.getFormattedCurrency(average));				
				sb.append("</td></tr>\n");
			}
		}
		
		sb.append("<tr><th>");
		sb.append(Translate.getInstance().get(TranslateKeys.TOTAL));
		sb.append("</th><th class='right" + (APICommonFormatter.isRed(totalActual) ? " red'" : "'") + "'>");
		sb.append(APICommonFormatter.getFormattedCurrency(totalActual));
		sb.append("</th><th class='right" + (APICommonFormatter.isRed(totalAverage) ? " red'" : "'") + "'>");
		sb.append(APICommonFormatter.getFormattedCurrency(totalAverage));
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

				sb.append(APICommonHTMLHelper.getHtmlTransactionHeader());


				for (Transaction t : transactions) {
					sb.append(APICommonHTMLHelper.getHtmlTransactionRow(new ImmutableTransactionImpl(t), new ImmutableCategoryImpl(c)));
				}

				sb.append(APICommonHTMLHelper.getHtmlTransactionFooter());
			}
		}
		
		sb.append(APICommonHTMLHelper.getHtmlFooter());
	
		return new HTMLWrapper(sb.toString(), null);
	}
	
	public DateRangeType getDateRangeType() {
		return DateRangeType.INTERVAL;
	}

	public String getTitle() {
		return Translate.getInstance().get(TranslateKeys.REPORT_TITLE_AVERAGE_INCOME_AND_EXPENSES_BY_CATEGORY);
	}

	public String getDescription() {
		return TranslateKeys.REPORT_DESCRIPTION_AVERAGE_INCOME_AND_EXPENSES_BY_CATEGORY.toString();
	}
	
	public boolean isPluginActive(DataManager dataManager) {
		return true;
	}
	public Version getAPIVersion() {
//		return new Version("2.3.4");
		return null;
	}
}
