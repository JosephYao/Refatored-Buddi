/*
 * Created on Oct 7, 2006 by wyatt
 */
package org.homeunix.drummer.plugins;

import org.homeunix.drummer.plugins.BuddiPluginHelper.DateRangeType;
import org.homeunix.drummer.plugins.interfaces.BuddiExportPlugin;
import org.homeunix.drummer.plugins.interfaces.BuddiImportPlugin;
import org.homeunix.drummer.plugins.interfaces.BuddiPanelPlugin;
import org.homeunix.drummer.view.AbstractFrame;

/**
 * @author wyatt
 * Null implementations of the three low level plugin types.  Used
 * to compare instances in the PluginFactory.  Do not instantiate outside
 * of that circumstance.
 */
class BuddiPluginImpl {

	static class BuddiExportPluginImpl implements BuddiExportPlugin {

		public void exportData(AbstractFrame frame) {}

		public Class[] getCorrectWindows() {
			return null;
		}

		public String getDescription() {
			return null;
		}
	}
	
	static class BuddiImportPluginImpl implements BuddiImportPlugin {

		public Class[] getCorrectWindows() {
			return null;
		}

		public String getDescription() {
			return null;
		}

		public void importData(AbstractFrame frame) {}
	}
	
	static class BuddiPanelPluginImpl implements BuddiPanelPlugin {

		public DateRangeType getDateRangeType() {
			return null;
		}

		public String getTitle() {
			return null;
		}

		public String getDescription() {
			return null;
		}
	}
}
