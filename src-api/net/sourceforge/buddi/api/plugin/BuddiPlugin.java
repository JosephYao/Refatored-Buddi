/*
 * Created on Sep 14, 2006 by wyatt
 */
package net.sourceforge.buddi.api.plugin;

import net.sourceforge.buddi.api.manager.DataManager;

import org.homeunix.thecave.moss.util.Version;

public interface BuddiPlugin {
	/**
	 * Returns the description text, as seen on the main window 
	 * under Reports tab, or in the menu
	 * @return
	 */
	public String getDescription();
	
	/**
	 * Returns the API version required to run this plugin, 
	 * or null if there is no defined version (not recommended
	 * unless the plugin uses no API calls which have been changed 
	 * since plugins were introduced, or if it does not use the
	 * API at all).
	 * 
	 * @return The org.homeunix.thecave.moss.util.Version object 
	 * containing the API version number. 
	 */
	public Version getAPIVersion();
	
	/**
	 * Should this plugin be activated?  Most people can just
	 * put true here; if there is some logic which determines if this
	 * is to be shown or not, though, you can add it here.
	 * @param dataManager The data manager associated with this plugin
	 * @return
	 */
	public boolean isPluginActive(DataManager dataManager);	
}
