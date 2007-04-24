/*
 * Created on Apr 14, 2007 by wyatt
 */
package org.homeunix.drummer.plugins.reports;

import java.util.Collections;
import java.util.Date;
import java.util.Vector;

import org.homeunix.drummer.controller.TransactionController;
import org.homeunix.drummer.controller.Translate;
import org.homeunix.drummer.controller.TranslateKeys;
import org.homeunix.drummer.model.Transaction;
import org.homeunix.drummer.plugins.BuddiPluginHelper.DateRangeType;
import org.homeunix.drummer.plugins.interfaces.BuddiReportPlugin;
import org.homeunix.drummer.prefs.PrefsInstance;
import org.homeunix.drummer.view.HTMLExportHelper;
import org.homeunix.drummer.view.HTMLExportHelper.HTMLWrapper;
import org.homeunix.thecave.moss.util.Version;

public class TransactionsNotCleared implements BuddiReportPlugin {
	public HTMLWrapper getReport(Date startDate, Date endDate) {
		//Find all transactions between given dates which have not been cleared
		Vector<Transaction> temp = TransactionController.getTransactions(startDate, endDate);
		Vector<Transaction> transactions = new Vector<Transaction>();
		
		for (Transaction transaction : temp) {
			if (!transaction.isCleared()){
				transactions.add(transaction);
			}
		}

		Collections.sort(transactions);

		//Output transactions to HTML in StringBuilder
		StringBuilder sb = HTMLExportHelper.getHtmlHeader(getTitle(), null, startDate, endDate);

		if (!PrefsInstance.getInstance().getPrefs().isShowAdvanced()){
			sb.append("<h1>")
			.append(Translate.getInstance().get(TranslateKeys.ERROR))
			.append("</h1>");
			
			sb.append("<p>")
			.append(Translate.getInstance().get(TranslateKeys.REPORT_ERROR_ADVANCED_DISPLAY_NOT_ENABLED))
			.append("</p>");
		}
		else {
			sb.append("<h1>")
			.append(Translate.getInstance().get(TranslateKeys.REPORT_TRANSACTIONS_NOT_CLEARED))
			.append("</h1>");

			sb.append(HTMLExportHelper.getHtmlTransactionHeader());
			
			for (Transaction t : transactions) {
				sb.append(HTMLExportHelper.getHtmlTransactionRow(t, null));	
			}
		
			sb.append(HTMLExportHelper.getHtmlTransactionFooter());
		}
		
		sb.append(HTMLExportHelper.getHtmlFooter());
		
		//Wrap in HTMLWrapper for return
		return new HTMLWrapper(sb.toString(), null);
	}
	
	public DateRangeType getDateRangeType() {
		return DateRangeType.INTERVAL;
	}
	
	public String getDescription() {
		return TranslateKeys.REPORT_DESCRIPTION_TRANSACTIONS_NOT_CLEARED.toString();
	}
	
	public String getTitle() {
		return TranslateKeys.REPORT_TITLE_TRANSACTIONS_NOT_CLEARED.toString();
	}
	
	public boolean isEnabled() {
		return PrefsInstance.getInstance().getPrefs().isShowAdvanced();
	}
	public Version getMinimumVersion() {
		return new Version("2.3.4");
	}
}
