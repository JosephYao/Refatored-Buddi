/*
 * Created on Aug 16, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.plugin.builtin.report;

import java.util.Date;

import org.homeunix.thecave.buddi.i18n.keys.PluginReportDateRangeChoices;
import org.homeunix.thecave.buddi.plugin.api.BuddiReportPlugin;
import org.homeunix.thecave.buddi.plugin.api.model.impl.ImmutableModelImpl;
import org.homeunix.thecave.buddi.plugin.api.util.HtmlHelper;
import org.homeunix.thecave.buddi.plugin.api.util.HtmlHelper.HtmlPage;
import org.homeunix.thecave.moss.util.Version;

public class NullReport extends BuddiReportPlugin {

	@Override
	public HtmlPage getReport(ImmutableModelImpl model, Date startDate, Date endDate) {
		return new HtmlHelper.HtmlPage("<html><body>Empty Plugin Report</body></html>", null);
	}

	public String getDescription() {
		return "Empty report plugin for test purposes";
	}

	public Version getMaximumVersion() {
		return null;
	}

	public Version getMinimumVersion() {
		return null;
	}

	public String getName() {
		return "Null Report";
	}

	public boolean isPluginActive() {
		return true;
	}
	@Override
	public PluginReportDateRangeChoices getDateRangeChoice() {
		return PluginReportDateRangeChoices.START_ONLY;
	}
}
