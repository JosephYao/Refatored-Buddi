/*
 * Created on Sep 14, 2006 by wyatt
 * 
 * The interface which can be extended to create import filters
 */
package org.homeunix.thecave.buddi.plugin.api;

import java.io.File;

import org.homeunix.thecave.buddi.i18n.BuddiKeys;
import org.homeunix.thecave.buddi.plugin.api.exception.PluginException;
import org.homeunix.thecave.buddi.plugin.api.model.MutableDocument;
import org.homeunix.thecave.buddi.plugin.api.util.TextFormatter;
import org.homeunix.thecave.moss.plugin.MossPlugin;
import org.homeunix.thecave.moss.swing.MossDocumentFrame;

/**
 * The abstract class to extend when creating an import plugin.  The method importData() 
 * is the one which is called by Buddi when executing the plugin.  In this method,
 * you have access to the main document object, the frame from which the plugin was
 * called, and the file to import from.
 * 
 * @author wyatt
 *
 */
public abstract class BuddiImportPlugin extends PreferenceAccess implements MossPlugin, FileAccess {
	
	/**
	 * Imports data as required.  The plugin launch code will prompt for a file
	 * and pass it in (unless you override the isPromptForFile() method to return false).
	 */
	public abstract void importData(MutableDocument model, MossDocumentFrame callingFrame, File file) throws PluginException;
	
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
	
	public boolean isPromptForFile(){
		return true;
	}
	
	public String[] getFileExtensions(){
		return null;
	}
	
	public String getProcessingMessage() {
		return TextFormatter.getTranslation(BuddiKeys.MESSAGE_PROCESSING_FILE);
	}
}
