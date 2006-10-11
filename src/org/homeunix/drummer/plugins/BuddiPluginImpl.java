/*
 * Created on Oct 7, 2006 by wyatt
 */
package org.homeunix.drummer.plugins;

import java.io.File;

import javax.swing.filechooser.FileFilter;

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

		public void exportData(AbstractFrame frame, File file) {}

		public String getFileChooserTitle() {
			return null;
		}

		public FileFilter getFileFilter() {
			return null;
		}

		public boolean isPromptForFile() {
			return false;
		}
	}
	
	static class BuddiImportPluginImpl implements BuddiImportPlugin {

		public Class[] getCorrectWindows() {
			return null;
		}

		public String getDescription() {
			return null;
		}
		
		public String getFileChooserTitle() {
			return null;
		}

		public FileFilter getFileFilter() {
			return null;
		}

		public boolean isPromptForFile() {
			return false;
		}

		public void importData(AbstractFrame frame, File file) {}
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
