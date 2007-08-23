/*
 * Created on Sep 14, 2006 by wyatt
 * 
 * The interface which can be extended to create export files
 */
package org.homeunix.thecave.buddi.plugin.api;

import java.io.File;

import org.homeunix.thecave.buddi.plugin.api.model.impl.ImmutableModelImpl;
import org.homeunix.thecave.moss.plugin.MossActionPlugin;

public abstract class BuddiExportPlugin implements MossActionPlugin {

	/**
	 * Exports data as required.  The implementor chooses where to send
	 * the file (it is reccomended to open a JFileChooser, but
	 * this is up to the implementor to decide).
	 * 
	 * @param frame The frame from which the command was called.  Can be 
	 * used to determine what type of export to do.
	 * @param file the file to export to.  Has just been chosen by the user.
	 * If you specify isPromptForFile() == false, this
	 * string will be null.
	 */
	public abstract void exportData(ImmutableModelImpl model, File file);
	
	/**
	 * Gets the extension for the data file, e.g., .csv, .xml, .qif, etc.
	 * If the value is null (default), there is no extension appended.
	 * Override to enable this.
	 * 
	 * Note: You should include the dot (.) in front of the extension;
	 * for instance, return ".csv", not "csv".
	 * 
	 * @return The extension of the data file, including the period.
	 */
	public String getExtension(){
		return null;
	}
}
