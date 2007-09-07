/*
 * Created on Sep 14, 2006 by wyatt
 */
package org.homeunix.thecave.buddi.plugin.builtin.report;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.homeunix.thecave.buddi.i18n.BuddiKeys;
import org.homeunix.thecave.buddi.i18n.keys.PluginReportDateRangeChoices;
import org.homeunix.thecave.buddi.plugin.api.BuddiReportPlugin;
import org.homeunix.thecave.buddi.plugin.api.model.ImmutableBudgetCategory;
import org.homeunix.thecave.buddi.plugin.api.model.ImmutableDocument;
import org.homeunix.thecave.buddi.plugin.api.model.ImmutableTransaction;
import org.homeunix.thecave.buddi.plugin.api.util.HtmlHelper;
import org.homeunix.thecave.buddi.plugin.api.util.HtmlPage;
import org.homeunix.thecave.buddi.plugin.api.util.TextFormatter;
import org.homeunix.thecave.moss.swing.MossDocumentFrame;
import org.homeunix.thecave.moss.util.Version;

/**
 * @author wyatt
 *
 */
public class IncomeExpenseReportByDescription extends BuddiReportPlugin {
	
	public static final long serialVersionUID = 0;
	
	@Override
	public HtmlPage getReport(ImmutableDocument model, MossDocumentFrame frame, Date startDate, Date endDate) {
		StringBuilder sb = HtmlHelper.getHtmlHeader(getName(), null, startDate, endDate);

		sb.append("<h1>").append(TextFormatter.getTranslation(BuddiKeys.REPORT_DETAILS)).append("</h1>\n");
		
		List<ImmutableTransaction> transactions = model.getImmutableTransactions(startDate, endDate);
		Map<String, List<ImmutableTransaction>> descriptions = new HashMap<String, List<ImmutableTransaction>>();
		
		for (ImmutableTransaction transaction : transactions) {
			if (descriptions.get(transaction.getDescription()) == null)
				descriptions.put(transaction.getDescription(), new LinkedList<ImmutableTransaction>());
			
			if (transaction.getTo() instanceof ImmutableBudgetCategory || transaction.getFrom() instanceof ImmutableBudgetCategory)
				descriptions.get(transaction.getDescription()).add(transaction);			
		}
		
		List<String> descriptionList = new LinkedList<String>(descriptions.keySet());
		Collections.sort(descriptionList);
		
		for (String s : descriptionList){			
			if (descriptions.get(s).size() > 0){
				long total = 0;
				for (ImmutableTransaction t : descriptions.get(s)) {
					if (t.getTo() instanceof ImmutableBudgetCategory){
						total -= t.getAmount();
					}
					else if (t.getFrom() instanceof ImmutableBudgetCategory){
						total += t.getAmount();
					}
				}
				
				
				sb.append(total < 0 ? "<h2 class='red'>" : "<h2>");
				sb.append(s).append(": ");
				sb.append(TextFormatter.getFormattedCurrency(total, total < 0));
				sb.append("</h2>\n");

				sb.append(HtmlHelper.getHtmlTransactionHeader());

				for (ImmutableTransaction t : descriptions.get(s)) {
					sb.append(HtmlHelper.getHtmlTransactionRow(t, null));
				}

				sb.append(HtmlHelper.getHtmlTransactionFooter());
			}
		}
		
		
		return new HtmlPage(sb.toString(), null);
	}

	@Override
	public PluginReportDateRangeChoices getDateRangeChoice() {
		return PluginReportDateRangeChoices.INTERVAL;
	}

	public String getName() {
		return BuddiKeys.REPORT_TITLE_INCOME_AND_EXPENSES_BY_DESCRIPTION.toString();
	}

	public String getDescription() {
		return BuddiKeys.REPORT_DESCRIPTION_INCOME_EXPENSES_BY_DESCRIPTION.toString();
	}
	
	public boolean isPluginActive() {
		return true;
	}
	
	public Version getMaximumVersion() {
		return null;
	}
	
	public Version getMinimumVersion() {
		return null;
	}
}
