package org.homeunix.thecave.buddi.plugin.builtin.report;

import java.util.Date;
import java.util.List;

import org.homeunix.thecave.buddi.i18n.BuddiKeys;
import org.homeunix.thecave.buddi.i18n.keys.PluginReportDateRangeChoices;
import org.homeunix.thecave.buddi.plugin.api.BuddiReportPlugin;
import org.homeunix.thecave.buddi.plugin.api.model.ImmutableDocument;
import org.homeunix.thecave.buddi.plugin.api.model.ImmutableTransaction;
import org.homeunix.thecave.buddi.plugin.api.util.HtmlHelper;
import org.homeunix.thecave.buddi.plugin.api.util.HtmlPage;
import org.homeunix.thecave.buddi.plugin.api.util.TextFormatter;

import ca.digitalcave.moss.swing.MossDocumentFrame;

public class Transactions extends BuddiReportPlugin {
	public static final long serialVersionUID = 0;
	
	
	@Override
	public HtmlPage getReport(ImmutableDocument model, MossDocumentFrame frame, Date startDate, Date endDate) {
		StringBuilder sb = HtmlHelper.getHtmlHeader(getName(), null, startDate, endDate);

		List<ImmutableTransaction> transactions = model.getImmutableTransactions(startDate, endDate);
		
		sb.append("<h1>").append(TextFormatter.getTranslation(BuddiKeys.REPORT_TITLE_TRANSACTIONS)).append("</h1>\n");
		sb.append("<table class='main'>\n");
		
		sb.append("<tr><th>");
		sb.append(TextFormatter.getTranslation(BuddiKeys.REPORT_KEYS_TRANSACTIONS_DATE));
		sb.append("</th><th>");
		sb.append(TextFormatter.getTranslation(BuddiKeys.REPORT_KEYS_TRANSACTIONS_DESCRIPTION));
		sb.append("</th><th>");
		sb.append(TextFormatter.getTranslation(BuddiKeys.REPORT_KEYS_TRANSACTIONS_NUMBER));
		sb.append("</th><th>");
		sb.append(TextFormatter.getTranslation(BuddiKeys.REPORT_KEYS_TRANSACTIONS_AMOUNT));
		sb.append("</th><th>");
		sb.append(TextFormatter.getTranslation(BuddiKeys.REPORT_KEYS_TRANSACTIONS_FROM));
		sb.append("</th><th>");
		sb.append(TextFormatter.getTranslation(BuddiKeys.REPORT_KEYS_TRANSACTIONS_TO));
		sb.append("</th><th>");
		sb.append(TextFormatter.getTranslation(BuddiKeys.REPORT_KEYS_TRANSACTIONS_MEMO));
		sb.append("</th></tr>\n");
		
		for (ImmutableTransaction t : transactions){
			sb.append("<tr>");
			sb.append("<td>");
			sb.append(TextFormatter.getFormattedDate(t.getDate()));
			sb.append("</td><td>");
			sb.append(t.getDescription());
			sb.append("</td><td>");
			sb.append(t.getNumber());
			sb.append("</td><td class='right" + (TextFormatter.isRed(t) ? " red'>" : "'>"));
			sb.append(TextFormatter.getFormattedCurrency(t.getAmount()));
			sb.append("</td><td>");
			sb.append(TextFormatter.getFormattedNameGeneric(t.getFrom().getName(), false));
			sb.append("</td><td>");
			sb.append(TextFormatter.getFormattedNameGeneric(t.getTo().getName(), false));
			sb.append("</td><td>");
			sb.append(t.getMemo());
			sb.append("</td></tr>\n");
		}
		sb.append("</table>\n\n");
		
		sb.append(HtmlHelper.getHtmlFooter());
	
		return new HtmlPage(sb.toString(), null);
	}

	public String getName() {
		return TextFormatter.getTranslation(BuddiKeys.REPORT_TITLE_TRANSACTIONS);
	}

	public String getDescription() {
		return BuddiKeys.REPORT_DESCRIPTION_TRANSACTIONS.toString();
	}
	@Override
	public PluginReportDateRangeChoices getDateRangeChoice() {
		return PluginReportDateRangeChoices.INTERVAL;
	}
	
	public boolean isPluginActive() {
		return true;
	}

}
