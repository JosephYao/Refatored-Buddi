/*
 * Created on Apr 14, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.plugin.builtin.report;

import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.homeunix.thecave.buddi.i18n.BuddiKeys;
import org.homeunix.thecave.buddi.model.prefs.PrefsModel;
import org.homeunix.thecave.buddi.plugin.api.BuddiReportPlugin;
import org.homeunix.thecave.buddi.plugin.api.model.immutable.ImmutableModel;
import org.homeunix.thecave.buddi.plugin.api.model.immutable.ImmutableTransaction;
import org.homeunix.thecave.buddi.plugin.api.util.HtmlHelper;
import org.homeunix.thecave.buddi.plugin.api.util.TextFormatter;
import org.homeunix.thecave.buddi.plugin.api.util.HtmlHelper.HtmlPage;
import org.homeunix.thecave.moss.util.Version;

public class TransactionsNotReconciled extends BuddiReportPlugin {

	public static final long serialVersionUID = 0;

	public HtmlPage getReport(ImmutableModel model, Date startDate, Date endDate) {
		//Find all transactions between given dates which have not been cleared
		List<ImmutableTransaction> temp = model.getTransactions(startDate, endDate);
		List<ImmutableTransaction> transactions = new LinkedList<ImmutableTransaction>();

		for (ImmutableTransaction transaction : temp) {
			if (!transaction.isReconciled()){
				transactions.add(transaction);
			}
		}

		Collections.sort(transactions);

		//Output transactions to HTML in StringBuilder
		StringBuilder sb = HtmlHelper.getHtmlHeader(getName(), null, startDate, endDate);

		if (!PrefsModel.getInstance().isShowReconciled()){
			sb.append("<h1>")
			.append(TextFormatter.getTranslation(BuddiKeys.ERROR))
			.append("</h1>");

			sb.append("<p>")
			.append(TextFormatter.getTranslation(BuddiKeys.REPORT_ERROR_ADVANCED_DISPLAY_NOT_ENABLED))
			.append("</p>");
		}
		else {
			sb.append("<h1>")
			.append(TextFormatter.getTranslation(BuddiKeys.REPORT_TRANSACTIONS_NOT_RECONCILED))
			.append("</h1>");

			sb.append(HtmlHelper.getHtmlTransactionHeader());

			for (ImmutableTransaction t : transactions) {
				sb.append(HtmlHelper.getHtmlTransactionRow(t, null));	
			}

			sb.append(HtmlHelper.getHtmlTransactionFooter());
		}

		sb.append(HtmlHelper.getHtmlFooter());

		//Wrap in HTMLWrapper for return
		return new HtmlPage(sb.toString(), null);
	}

	public String getDescription() {
		return TextFormatter.getTranslation(BuddiKeys.REPORT_DESCRIPTION_TRANSACTIONS_NOT_RECONCILED);
	}

	public String getName() {
		return TextFormatter.getTranslation(BuddiKeys.REPORT_TITLE_TRANSACTIONS_NOT_RECONCILED);
	}

	public boolean isPluginActive() {
		return PrefsModel.getInstance().isShowReconciled();
	}
	public Version getMaximumVersion() {
		return null;
	}
	public Version getMinimumVersion() {
		return null;
	}
}
