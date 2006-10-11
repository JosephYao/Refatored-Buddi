/*
 * Created on Sep 14, 2006 by wyatt
 * 
 * The interface which can be extended to create custom reports.
 */
package org.homeunix.drummer.plugins.interfaces;

import javax.swing.filechooser.FileFilter;



public interface BuddiFilePlugin extends BuddiPlugin {

	/**
	 * Should Buddi prompt the user for a file when running 
	 * the plugin?  If you select true, you must also
	 * specify the FileChooserTitle and FileFilter get() methods.
	 * @return
	 */
	public boolean isPromptForFile();
	
	/**
	 * Returns the title of the File Chooser
	 * @return
	 */
	public String getFileChooserTitle();
	
	/**
	 * Returns the file filter to use.  Either create a new
	 * class, or return null to see all files.
	 * @return
	 */
	public FileFilter getFileFilter();
}
