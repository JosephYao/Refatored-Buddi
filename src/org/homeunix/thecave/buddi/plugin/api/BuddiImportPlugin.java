/*
 * Created on Sep 14, 2006 by wyatt
 * 
 * The interface which can be extended to create import filters
 */
package org.homeunix.thecave.buddi.plugin.api;

import java.io.File;

import org.homeunix.thecave.buddi.i18n.BuddiKeys;
import org.homeunix.thecave.buddi.plugin.api.model.MutableDocument;
import org.homeunix.thecave.moss.plugin.MossPlugin;
import org.homeunix.thecave.moss.swing.MossDocumentFrame;

public abstract class BuddiImportPlugin implements MossPlugin {
	
	/**
	 * Imports data as required.  The plugin launch code will prompt for a file
	 * and pass it in (unless you override the isPromptForFile() method to return false).
	 */
	public abstract void importData(MutableDocument model, MossDocumentFrame callingFrame, File file);
	
	/**
	 * Return the description which shows up in the file chooser.  By default, this 
	 * is set to "Buddi Import Files".  This value is filtered through the translator
	 * before being displayed.
	 */
	public String getDescription() {
		return BuddiKeys.FILE_DESCRIPTION_BUDDI_IMPORT_FILES.toString();
	}
	
	public boolean isPluginActive() {
		return true;
	}
	
	/**
	 * Should Buddi prompt for a file to import?  Defaults to true.  If you know what
	 * the file name is (or you are importing from a different source, such as the 
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
