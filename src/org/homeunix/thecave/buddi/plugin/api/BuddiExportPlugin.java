/*
 * Created on Sep 14, 2006 by wyatt
 * 
 * The interface which can be extended to create export files
 */
package org.homeunix.thecave.buddi.plugin.api;

import java.io.File;

import org.homeunix.thecave.buddi.i18n.BuddiKeys;
import org.homeunix.thecave.buddi.plugin.api.model.ImmutableModel;
import org.homeunix.thecave.moss.plugin.MossPlugin;

public abstract class BuddiExportPlugin implements MossPlugin {

	/**
	 * Exports data as required.  The plugin launch code will prompt for a file
	 * and pass it in (unless you override the isPromptForFile() method to return false).
	 */
	public abstract void exportData(ImmutableModel model, File file);
	

	/**
	 * Return the description which shows up in the file chooser.  By default, this 
	 * is set to "Buddi Export Files".  This value is filtered through the translator
	 * before being displayed.
	 */
	public String getDescription() {
		return BuddiKeys.FILE_DESCRIPTION_BUDDI_EXPORT_FILES.toString();
	}
	
	public boolean isPluginActive() {
		return true;
	}
	
	/**
	 * Should Buddi prompt for a file to export to?  Defaults to true.  If you know what
	 * the file name is (or you are exporting from a different source, such as the 
	 * network), you should override this method and return false. 
	 * @return
	 */
	public boolean isPromptForFile(){
		return true;
	}
	
	/**
	 * Override to specify that Buddi should only include certain file types in 
	 * the file chooser.  This only has an effect if isPromptForFile() is true.  If
	 * you want to do this, return a String array of the file extensions which you wish
	 * to match.  The plugin loader will create a FileFilter for this.
	 * @return
	 */
	public String[] getFileExtensions(){
		return null;
	}
}
