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
import org.homeunix.thecave.buddi.plugin.api.BuddiReportPlugin;
import org.homeunix.thecave.buddi.plugin.api.model.ImmutableAccount;
import org.homeunix.thecave.buddi.plugin.api.model.ImmutableDocument;
import org.homeunix.thecave.buddi.plugin.api.util.HtmlHelper;
import org.homeunix.thecave.buddi.plugin.api.util.HtmlPage;
import org.homeunix.thecave.buddi.plugin.api.util.TextFormatter;

import ca.digitalcave.moss.common.Version;
import ca.digitalcave.moss.swing.MossDocumentFrame;

/**
 * Built-in plugin.  Feel free to use this as an example on how to make
 * report plugins
 * 
 * @author wyatt
 *
 */
public class AccountBalance extends BuddiReportPlugin {
	
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
	public HtmlPage getReport(ImmutableDocument model, MossDocumentFrame callingFrame, Date startDate, Date endDate) {
		StringBuilder sb = HtmlHelper.getHtmlHeader(getName(), null, startDate, endDate);

		List<ImmutableAccount> accounts = model.getImmutableAccounts();
		Collections.sort(accounts, new Comparator<ImmutableAccount>(){
			public int compare(ImmutableAccount o1, ImmutableAccount o2) {
				//First we sort by income
				if (o1.getAccountType().isCredit() != o2.getAccountType().isCredit()){
					if (o1.getAccountType().isCredit()){
						return 1;
					}
					else {
						return -1;
					}
				}
								
				//Finally, we sort by Category Name
				return o1.toString().compareTo(o2.toString());
			}
		});
		
		sb.append("<h1>").append(TextFormatter.getTranslation(BuddiKeys.REPORT_TITLE_ACCOUNT_BALANCE)).append("</h1>\n");
		sb.append("<table class='main'>\n");
		
		sb.append("<tr><th>");
		sb.append(TextFormatter.getTranslation(BuddiKeys.NAME));
		sb.append("</th><th>");
		sb.append(TextFormatter.getTranslation(BuddiKeys.BALANCE_OF_ACCOUNT));
		sb.append("</th></tr>\n");
		
		for (ImmutableAccount a : accounts){
			if (!a.isDeleted()){
				sb.append("<tr>");
				sb.append("<td>");
				sb.append(TextFormatter.getTranslation(a.getFullName()));
				sb.append("</td><td class='right" + (TextFormatter.isRed(a, a.getBalance(endDate)) ? " red'" : "'") + ">");
				sb.append(TextFormatter.getFormattedCurrency(a.getBalance(endDate)));
				sb.append("</td></tr>\n");
			}
		}

		sb.append("</table>\n\n");
		
		sb.append(HtmlHelper.getHtmlFooter());
	
		return new HtmlPage(sb.toString(), null);
	}
	
	public String getName() {
		return TextFormatter.getTranslation(BuddiKeys.REPORT_TITLE_ACCOUNT_BALANCE);
	}

	public String getDescription() {
		return BuddiKeys.REPORT_DESCRIPTION_ACCOUNT_BALANCE.toString();
	}
	
	@Override
	public PluginReportDateRangeChoices getDateRangeChoice() {
		return PluginReportDateRangeChoices.END_ONLY;
	}
}
