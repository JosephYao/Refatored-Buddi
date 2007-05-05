/*
 * Created on Sep 14, 2006 by wyatt
 * 
 * The interface which can be extended to create import filters
 */
package net.sourceforge.buddi.api.plugin;

import java.io.File;

import net.sourceforge.buddi.api.manager.ImportManager;

public abstract class BuddiImportPlugin extends BuddiMenuPlugin {
	/**
	 * Imports data as required.  The implementor chooses where to send
	 * the file (it is reccomended to open a JFileChooser, but
	 * this is up to the implementor to decide).
	 * 
	 * @param frame The frame from which the command was called.  Can be 
	 * used to determine what type of export to do.
	 */
	public abstract void importData(ImportManager importManager, File file);
}
