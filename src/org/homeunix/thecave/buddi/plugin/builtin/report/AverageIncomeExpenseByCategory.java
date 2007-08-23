/*
 * Created on Sep 14, 2006 by wyatt
 */
package org.homeunix.thecave.buddi.plugin.builtin.report;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.homeunix.thecave.buddi.i18n.BuddiKeys;
import org.homeunix.thecave.buddi.i18n.keys.PluginReportDateRangeChoices;
import org.homeunix.thecave.buddi.model.prefs.PrefsModel;
import org.homeunix.thecave.buddi.plugin.api.BuddiReportPlugin;
import org.homeunix.thecave.buddi.plugin.api.model.ImmutableBudgetCategory;
import org.homeunix.thecave.buddi.plugin.api.model.ImmutableModel;
import org.homeunix.thecave.buddi.plugin.api.model.ImmutableTransaction;
import org.homeunix.thecave.buddi.plugin.api.util.BudgetCalculator;
import org.homeunix.thecave.buddi.plugin.api.util.HtmlHelper;
import org.homeunix.thecave.buddi.plugin.api.util.TextFormatter;
import org.homeunix.thecave.buddi.plugin.api.util.HtmlHelper.HtmlPage;
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
	public HtmlPage getReport(ImmutableModel model, Date startDate, Date endDate) {
		StringBuilder sb = HtmlHelper.getHtmlHeader(getName(), null, startDate, endDate);

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
			
			
			List<ImmutableTransaction> transactions = model.getTransactions(c, startDate, endDate);
			long actual = 0;
			for (ImmutableTransaction transaction : transactions) {
				actual += transaction.getAmount();
				
				if (transaction.getTo() instanceof ImmutableBudgetCategory){
					totalActual -= transaction.getAmount();
				}
				else if (transaction.getFrom() instanceof ImmutableBudgetCategory){
					totalActual += transaction.getAmount();
				}
			}

			long average = BudgetCalculator.getAverageByInterval(actual, model.getPeriodType(), startDate, endDate);
			if (c.isIncome()){
				totalAverage += average;
			}
			else {
				totalAverage -= average;
			}
			

			if (transactions.size() > 0){				
				sb.append("<tr>");
				sb.append("<td>");
				sb.append(PrefsModel.getInstance().getTranslator().get(c.toString()));
				sb.append("</td><td class='right" + (TextFormatter.isRed(c, actual) ? " red'" : "'") + ">");
				sb.append(TextFormatter.getFormattedCurrency(actual));
				sb.append("</td><td class='right" + (TextFormatter.isRed(c, average) ? " red'" : "'") + "'>");
				sb.append(TextFormatter.getFormattedCurrency(average));				
				sb.append("</td></tr>\n");
			}
		}
		
		sb.append("<tr><th>");
		sb.append(PrefsModel.getInstance().getTranslator().get(BuddiKeys.TOTAL));
		sb.append("</th><th class='right" + (TextFormatter.isRed(totalActual) ? " red'" : "'") + "'>");
		sb.append(TextFormatter.getFormattedCurrency(totalActual));
		sb.append("</th><th class='right" + (TextFormatter.isRed(totalAverage) ? " red'" : "'") + "'>");
		sb.append(TextFormatter.getFormattedCurrency(totalAverage));
		sb.append("</th></tr>\n");

		sb.append("</table>\n\n");
		
		sb.append("<hr>\n");
		
		sb.append("<h1>").append(PrefsModel.getInstance().getTranslator().get(BuddiKeys.REPORT_DETAILS)).append("</h1>\n");
		
		for (ImmutableBudgetCategory bc : categories){
			List<ImmutableTransaction> transactions = model.getTransactions(bc, startDate, endDate);
			
			
			if (transactions.size() > 0){
				sb.append(bc.isIncome() ? "<h2>" : "<h2 class='red'>");
				sb.append(PrefsModel.getInstance().getTranslator().get(bc.toString()));
				sb.append("</h2>\n");

				sb.append(HtmlHelper.getHtmlTransactionHeader());


				for (ImmutableTransaction t : transactions) {
					sb.append(HtmlHelper.getHtmlTransactionRow(t, bc));
				}

				sb.append(HtmlHelper.getHtmlTransactionFooter());
			}
		}
		
		sb.append(HtmlHelper.getHtmlFooter());
	
		return new HtmlPage(sb.toString(), null);
	}
	
	public String getName() {
		return PrefsModel.getInstance().getTranslator().get(BuddiKeys.REPORT_TITLE_AVERAGE_INCOME_AND_EXPENSES_BY_CATEGORY);
	}

	public String getDescription() {
		return BuddiKeys.REPORT_DESCRIPTION_AVERAGE_INCOME_AND_EXPENSES_BY_CATEGORY.toString();
	}
	
	@Override
	public PluginReportDateRangeChoices getDateRangeChoice() {
		return PluginReportDateRangeChoices.INTERVAL;
	}
}
