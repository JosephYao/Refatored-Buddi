/*
 * Created on Oct 7, 2006 by wyatt
 */
package org.homeunix.drummer.plugins;

import java.io.File;

import javax.swing.JFrame;
import javax.swing.filechooser.FileFilter;

import org.homeunix.thecave.moss.util.Version;

import net.sourceforge.buddi.api.manager.DataManager;
import net.sourceforge.buddi.api.manager.ImportManager;
import net.sourceforge.buddi.api.manager.UtilityManager;
import net.sourceforge.buddi.api.plugin.BuddiExportPluginAPI;
import net.sourceforge.buddi.api.plugin.BuddiImportPluginAPI;

/**
 * @author Mike Kienenberger
 * Null implementations of the three low level plugin types.  Used
 * to compare instances in the PluginFactory.  Do not instantiate outside
 * of that circumstance.
 */
class BuddiPluginAPIImpl {

	static class BuddiExportPluginAPIImpl implements BuddiExportPluginAPI {

        public void exportData(DataManager dataManager, JFrame frame, File file) {}

        public Version getMinimumAPIVersion() {
        	return null;
        }
        
        public boolean isMenuActive(DataManager dataManager) {
            return false;
        }

        public boolean isPromptForFile(UtilityManager utilityManager) {
            return false;
        }

        public String getFileChooserTitle(UtilityManager utilityManager) {
            return null;
        }

        public FileFilter getFileFilter(UtilityManager utilityManager) {
            return null;
        }

        public String getDescription(UtilityManager utilityManager) {
            return null;
        }
	}
	
	static class BuddiImportPluginAPIImpl implements BuddiImportPluginAPI {

        public void importData(ImportManager importManager, JFrame frame, File file) {}

        public Version getMinimumAPIVersion() {
        	return null;
        }
        
        public boolean isMenuActive(DataManager dataManager) {
            return false;
        }

        public boolean isPromptForFile(UtilityManager utilityManager) {
            return false;
        }

        public String getFileChooserTitle(UtilityManager utilityManager) {
            return null;
        }

        public FileFilter getFileFilter(UtilityManager utilityManager) {
            return null;
        }

        public String getDescription(UtilityManager utilityManager) {
            return null;
        }
	}
	
//	static class BuddiPanelPluginImplAPI implements BuddiPanelPluginAPI {
//
//		public DateRangeType getDateRangeType() {
//			return null;
//		}
//
//		public String getTitle() {
//			return null;
//		}
//
//		public String getDescription() {
//			return null;
//		}
//	}
}
