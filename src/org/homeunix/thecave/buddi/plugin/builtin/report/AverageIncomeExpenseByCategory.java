/*
 * Created on Sep 14, 2006 by wyatt
 */
package org.homeunix.thecave.buddi.plugin.builtin.report;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import org.homeunix.thecave.buddi.i18n.BuddiKeys;
import org.homeunix.thecave.buddi.model.Transaction;
import org.homeunix.thecave.buddi.model.prefs.PrefsModel;
import org.homeunix.thecave.buddi.plugin.api.BuddiReportPlugin;
import org.homeunix.thecave.buddi.plugin.api.model.immutable.ImmutableBudgetCategory;
import org.homeunix.thecave.buddi.plugin.api.model.immutable.ImmutableModel;
import org.homeunix.thecave.buddi.plugin.api.util.APICommonFormatter;
import org.homeunix.thecave.buddi.plugin.api.util.APICommonHTMLHelper;
import org.homeunix.thecave.buddi.plugin.api.util.APICommonHTMLHelper.HTMLWrapper;
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
	
	public Version getMaximumVersion() {
		return null; //This is integrated, so there is no need to pick this.
	}

	public Version getMinimumVersion() {
		return null;
	}

	public boolean isPluginActive() {
		return true;
	}

	@Override
	public HTMLWrapper getReport(ImmutableModel model, Date startDate, Date endDate) {
		StringBuilder sb = APICommonHTMLHelper.getHtmlHeader(getName(), null, startDate, endDate);

		List<ImmutableBudgetCategory> categories = model.getBudgetCategories();
		Collections.sort(categories, new Comparator<ImmutableBudgetCategory>(){
			public int compare(ImmutableBudgetCategory o1, ImmutableBudgetCategory o2) {
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
		
		sb.append("<h1>").append(PrefsModel.getInstance().getTranslator().get(BuddiKeys.REPORT_SUMMARY)).append("</h1>\n");
		sb.append("<table class='main'>\n");
		
		sb.append("<tr><th>");
		sb.append(PrefsModel.getInstance().getTranslator().get(BuddiKeys.NAME));
		sb.append("</th><th>");
		sb.append(PrefsModel.getInstance().getTranslator().get(BuddiKeys.ACTUAL));
		sb.append("</th><th>");
		sb.append(PrefsModel.getInstance().getTranslator().get(BuddiKeys.AVERAGE));
		sb.append(" / ");
		sb.append(PrefsModel.getInstance().getTranslator().get(model.getPeriodType()));
		sb.append("</th></tr>\n");
		
		long totalActual = 0, totalAverage = 0;
		
		for (ImmutableBudgetCategory c : categories){
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
				sb.append(PrefsModel.getInstance().getTranslator().get(c.toString()));
				sb.append("</td><td class='right" + (APICommonFormatter.isRed(new ImmutableCategoryImpl(c), actual) ? " red'" : "'") + ">");
				sb.append(APICommonFormatter.getFormattedCurrency(actual));
				sb.append("</td><td class='right" + (APICommonFormatter.isRed(new ImmutableCategoryImpl(c), average) ? " red'" : "'") + "'>");
				sb.append(APICommonFormatter.getFormattedCurrency(average));				
				sb.append("</td></tr>\n");
			}
		}
		
		sb.append("<tr><th>");
		sb.append(PrefsModel.getInstance().getTranslator().get(BuddiKeys.TOTAL));
		sb.append("</th><th class='right" + (APICommonFormatter.isRed(totalActual) ? " red'" : "'") + "'>");
		sb.append(APICommonFormatter.getFormattedCurrency(totalActual));
		sb.append("</th><th class='right" + (APICommonFormatter.isRed(totalAverage) ? " red'" : "'") + "'>");
		sb.append(APICommonFormatter.getFormattedCurrency(totalAverage));
		sb.append("</th></tr>\n");

		sb.append("</table>\n\n");
		
		sb.append("<hr>\n");
		
		sb.append("<h1>").append(PrefsModel.getInstance().getTranslator().get(BuddiKeys.REPORT_DETAILS)).append("</h1>\n");
		
		for (Category c : categories){
			Vector<Transaction> transactions = TransactionController.getTransactions(c, startDate, endDate);
			
			
			if (transactions.size() > 0){
				sb.append(c.isIncome() ? "<h2>" : "<h2 class='red'>");
				sb.append(PrefsModel.getInstance().getTranslator().get(c.toString()));
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

	public String getName() {
		return PrefsModel.getInstance().getTranslator().get(BuddiKeys.REPORT_TITLE_AVERAGE_INCOME_AND_EXPENSES_BY_CATEGORY);
	}

	public String getDescription() {
		return BuddiKeys.REPORT_DESCRIPTION_AVERAGE_INCOME_AND_EXPENSES_BY_CATEGORY.toString();
	}
}
