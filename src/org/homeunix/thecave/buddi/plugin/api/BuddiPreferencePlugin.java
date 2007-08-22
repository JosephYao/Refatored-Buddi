/*
 * Created on Sep 14, 2006 by wyatt
 * 
 * The interface which can be extended to create custom reports.
 */
package org.homeunix.thecave.buddi.plugin.api;

import org.homeunix.thecave.moss.plugin.MossPlugin;
import org.homeunix.thecave.moss.swing.window.MossPanel;

/**
 * @author wyatt
 * 
 * A Buddi plugin which will be loaded into the Preferences screen.  You can 
 * use this plugin to store user preferences about the plugin.
 * 
 * This plugin extends MossPanel.  Please see the current Javadocs for MossPanel
 * for a more in depth idea of how to use it.  At a high level, you want to do
 * the following, in order:
 * 
 * 1) Instantiate any final variables in the constructor.  Try to avoid
 * doing anything more than that in the constructor.
 * 2) Set up the GUI, create non-final objects, etc in the init() method.
 * This method is called automatically at open() time.
 * 3) Anything which relates to window state (status labels, etc) you can
 * put in the updateContent() method.  The contract for this method specifies
 * that it is to avoid any time consuming actions.
 * 
 * If you need to reference a final variable in the init() method, you may need to call
 * the super constructor super(true).  This will make the pael not automatically 
 * call open().  However, you should call this at the end of the constructor; otherwise,
 * there will be no content shown in the panel. 
 */
public abstract class BuddiPreferencePlugin extends MossPanel implements MossPlugin {
	
	/**
	 * Saves the preferences which this panel is responsible for.
	 */
	public abstract void save();
	
	/**
	 * Loads the preferences which this panel is responsible for.
	 */
	public abstract void load();
}
