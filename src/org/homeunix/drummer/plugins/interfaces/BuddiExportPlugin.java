/*
 * Created on Sep 14, 2006 by wyatt
 * 
 * The interface which can be extended to create export files
 */
package org.homeunix.drummer.plugins.interfaces;

import java.io.File;

import org.homeunix.thecave.moss.gui.abstractwindows.AbstractFrame;


public interface BuddiExportPlugin extends BuddiMenuPlugin {
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
	public void exportData(AbstractFrame frame, File file);
}
