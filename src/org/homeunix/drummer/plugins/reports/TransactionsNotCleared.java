/*
 * Created on Apr 14, 2007 by wyatt
 */
package org.homeunix.drummer.plugins.reports;

import java.util.Collections;
import java.util.Date;
import java.util.Vector;

import net.sourceforge.buddi.api.manager.APICommonHTMLHelper;
import net.sourceforge.buddi.api.manager.DataManager;
import net.sourceforge.buddi.api.manager.APICommonHTMLHelper.HTMLWrapper;
import net.sourceforge.buddi.api.manager.DateRangeType;
import net.sourceforge.buddi.api.plugin.BuddiReportPlugin;
import net.sourceforge.buddi.impl_2_4.model.ImmutableTransactionImpl;

import org.homeunix.drummer.controller.TransactionController;
import org.homeunix.drummer.controller.Translate;
import org.homeunix.drummer.controller.TranslateKeys;
import org.homeunix.drummer.model.Transaction;
import org.homeunix.drummer.prefs.PrefsInstance;
import org.homeunix.thecave.moss.util.Version;

public class TransactionsNotCleared extends BuddiReportPlugin {
	
	public static final long serialVersionUID = 0;
	
	public HTMLWrapper getReport(DataManager dataManager, Date startDate, Date endDate) {
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
		StringBuilder sb = APICommonHTMLHelper.getHtmlHeader(getTitle(), null, startDate, endDate);

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

			sb.append(APICommonHTMLHelper.getHtmlTransactionHeader());
			
			for (Transaction t : transactions) {
				sb.append(APICommonHTMLHelper.getHtmlTransactionRow(new ImmutableTransactionImpl(t), null));	
			}
		
			sb.append(APICommonHTMLHelper.getHtmlTransactionFooter());
		}
		
		sb.append(APICommonHTMLHelper.getHtmlFooter());
		
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
	
	public boolean isPluginActive(DataManager dataManager) {
		return PrefsInstance.getInstance().getPrefs().isShowAdvanced();
	}
	public Version getAPIVersion() {
//		return new Version("2.3.4");
		return null;
	}
}
