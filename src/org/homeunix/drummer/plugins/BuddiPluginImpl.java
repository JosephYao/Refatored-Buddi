/*
 * Created on Oct 7, 2006 by wyatt
 */
package org.homeunix.drummer.plugins;

import java.io.File;
import java.util.Date;

import javax.swing.filechooser.FileFilter;

import net.sourceforge.buddi.api.plugin.BuddiExportPlugin;
import net.sourceforge.buddi.api.plugin.BuddiGraphPlugin;
import net.sourceforge.buddi.api.plugin.BuddiImportPlugin;
import net.sourceforge.buddi.api.plugin.BuddiReportPlugin;
import net.sourceforge.buddi.api.plugin.BuddiRunnablePlugin;

import org.homeunix.drummer.plugins.BuddiPluginHelper.DateRangeType;
import org.homeunix.drummer.view.DocumentManager;
import org.homeunix.drummer.view.HTMLExportHelper.HTMLWrapper;
import org.homeunix.thecave.moss.gui.abstractwindows.AbstractFrame;
import org.homeunix.thecave.moss.util.Version;

/**
 * @author wyatt
 * Null implementations of four low level plugin types.  Used
 * to compare instances in the PluginFactory.  Do not instantiate outside
 * of that circumstance.  Don't try to use these implementation, or you
 * will get null pointer exceptions all over the place!
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
		public Version getAPIVersion() {
			return null;
		}
		public boolean isPluginActive(DocumentManager documentManager) {
			return true;
		}
	}
	
	static class BuddiImportPluginImpl implements BuddiImportPlugin {

		public Class[] getCorrectWindows() {
			return null;
		}
		public Version getAPIVersion() {
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
		public boolean isPluginActive(DocumentManager documentManager) {
			return true;
		}
		public void importData(AbstractFrame frame, File file) {}
	}
	
	static class BuddiGraphPluginImpl implements BuddiGraphPlugin {
		public boolean isPluginActive(DocumentManager documentManager) {
			return false;
		}
		public Version getAPIVersion() {
			return null;
		}
		public DateRangeType getDateRangeType() {
			return null;
		}
		public String getDescription() {
			return null;
		}
		public HTMLWrapper getGraph(Date startDate, Date endDate) {
			return null;
		}
		public String getTitle() {
			return null;
		}
	}
	
	static class BuddiReportPluginImpl implements BuddiReportPlugin {
		public boolean isPluginActive(DocumentManager documentManager) {
			return false;
		}
		public DateRangeType getDateRangeType() {
			return null;
		}
		public String getTitle() {
			return null;
		}
		public Version getAPIVersion() {
			return null;
		}
		public String getDescription() {
			return null;
		}
		public HTMLWrapper getReport(Date startDate, Date endDate) {
			return null;
		}
	}
	
	static class BuddiRunnablePluginImpl implements BuddiRunnablePlugin {
		public String getDescription() {
			return null;
		}
		public Version getAPIVersion() {
			return null;
		}
		public void run() {
			
		}
		public boolean isPluginActive(DocumentManager documentManager) {
			return true;
		}
	}
}
