/*
 * Created on Sep 14, 2006 by wyatt
 * 
 * The interface which can be extended to create export files
 */
package org.homeunix.thecave.buddi.plugin.api;

import java.io.File;

import org.homeunix.thecave.buddi.plugin.api.model.ImmutableModel;
import org.homeunix.thecave.moss.plugin.MossPlugin;

public abstract class BuddiExportPlugin implements MossPlugin {

	/**
	 * Exports data as required.  The implementor chooses where to send
	 * the file (it is reccomended to open a JFileChooser, but
	 * this is up to the implementor to decide).
	 */
	public abstract void exportData(ImmutableModel model, File file);
	
	/**
	 * This is not used in BuddiExportPlugin.
	 * @see org.homeunix.thecave.moss.plugin.MossPlugin#getDescription()
	 */
	public String getDescription() {
		return null;
	}
	
	public boolean isPluginActive() {
		return true;
	}	
}
