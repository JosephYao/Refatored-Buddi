/*
 * Created on Sep 14, 2006 by wyatt
 */
package org.homeunix.drummer.plugins.reports;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import org.homeunix.drummer.controller.TransactionController;
import org.homeunix.drummer.controller.Translate;
import org.homeunix.drummer.controller.TranslateKeys;
import org.homeunix.drummer.model.Account;
import org.homeunix.drummer.model.Category;
import org.homeunix.drummer.model.Transaction;
import org.homeunix.drummer.plugins.BuddiPluginHelper.DateRangeType;
import org.homeunix.drummer.plugins.interfaces.BuddiReportPlugin;
import org.homeunix.drummer.util.FormatterWrapper;
import org.homeunix.drummer.view.HTMLExportHelper;
import org.homeunix.drummer.view.HTMLExportHelper.HTMLWrapper;

/**
 * Built-in plugin.  Feel free to use this as an example on how to make
 * report plugins (although this one is kind of ugly, so you may not 
 * want to use it..)
 * 
 * @author wyatt
 *
 */
public class IncomeExpenseReportByDescription implements BuddiReportPlugin {
	
	public HTMLWrapper getReport(Date startDate, Date endDate) {
		StringBuilder sb = HTMLExportHelper.getHtmlHeader(getTitle(), null, startDate, endDate);

		sb.append("<h1>").append(Translate.getInstance().get(TranslateKeys.DETAILS)).append("</h1>\n");
		
		Vector<Transaction> transactions = TransactionController.getTransactions(startDate, endDate);
		Map<String, Vector<Transaction>> descriptions = new HashMap<String, Vector<Transaction>>();
		
		for (Transaction transaction : transactions) {
			if (descriptions.get(transaction.getDescription()) == null)
				descriptions.put(transaction.getDescription(), new Vector<Transaction>());
			
			if (transaction.getTo() instanceof Category || transaction.getFrom() instanceof Category)
				descriptions.get(transaction.getDescription()).add(transaction);			
		}
		
		for (String s : descriptions.keySet()){			
			if (descriptions.get(s).size() > 0){
				long total = 0;
				for (Transaction t : descriptions.get(s)) {
					if (t.getTo() instanceof Category){
						total -= t.getAmount();
					}
					else if (t.getFrom() instanceof Category){
						total += t.getAmount();
					}
				}
				
				
				sb.append(total < 0 ? "<h2 class='red'>" : "<h2>");
				sb.append(s).append(": ");
				sb.append(FormatterWrapper.getFormattedCurrencyGeneric(total, total < 0, total < 0));
				sb.append("</h2>\n");

				sb.append("<table class='main'>\n");

				sb.append("<tr><th>");
				sb.append(Translate.getInstance().get(TranslateKeys.DATE));
				sb.append("</th><th>");
				sb.append(Translate.getInstance().get(TranslateKeys.SOURCE_TO_FROM));
				sb.append("</th><th>");
				sb.append(Translate.getInstance().get(TranslateKeys.AMOUNT));
				sb.append("</th></tr>\n");


				for (Transaction t : descriptions.get(s)) {
					sb.append("<tr><td>");
					sb.append(FormatterWrapper.getDateFormat().format(t.getDate()));
					
					sb.append("</td><td>");
					sb.append(Translate.getInstance().get(t.getFrom().toString()));
					sb.append(Translate.getInstance().get(TranslateKeys.TO_HTML_SAFE));
					sb.append(Translate.getInstance().get(t.getTo().toString()));
					
					sb.append("</td><td>");
					sb.append(FormatterWrapper.getFormattedCurrencyForTransaction(t.getAmount(), t.getTo() instanceof Account));

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
		return Translate.getInstance().get(TranslateKeys.INCOME_AND_EXPENSES_BY_DESCRIPTION_TITLE);
	}

	public String getDescription() {
		return Translate.getInstance().get(TranslateKeys.REPORT_INCOME_EXPENSES_BY_DESCRIPTION);
	}
}
