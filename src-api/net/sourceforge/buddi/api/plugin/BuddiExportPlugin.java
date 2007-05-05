/*
 * Created on Sep 14, 2006 by wyatt
 * 
 * The interface which can be extended to create export files
 */
package net.sourceforge.buddi.api.plugin;

import java.io.File;

import net.sourceforge.buddi.api.manager.DataManager;

public abstract class BuddiExportPlugin extends BuddiMenuPlugin {

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
	public abstract void exportData(DataManager dataManager, File file);
	
}
