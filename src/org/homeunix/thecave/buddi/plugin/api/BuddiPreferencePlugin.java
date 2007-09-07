/*
 * Created on Sep 14, 2006 by wyatt
 * 
 * The interface which can be extended to create custom reports.
 */
package org.homeunix.thecave.buddi.plugin.api;

import javax.swing.JPanel;

import org.homeunix.thecave.buddi.model.prefs.PrefsModel;
import org.homeunix.thecave.buddi.plugin.api.exception.PluginException;
import org.homeunix.thecave.moss.plugin.MossPlugin;

/**
 * @author wyatt
 * 
 * A Buddi plugin which will be loaded into the Preferences screen.  You can 
 * use this plugin to store user preferences about the plugin.
 */
public abstract class BuddiPreferencePlugin implements MossPlugin {
	
	/**
	 * Loads the value which is associated with the given key.
	 * @param key The key to read.
	 * @return The value associated with the given key, or null if there was no 
	 * such value set.
	 */
	public String getPreference(String key){
		return PrefsModel.getInstance().getPluginPreference(key);
	}
	
	/**
	 * @param key The key to save.  In order to ensure that you do not
	 * overwrite the preferences for another plugin, you must use
	 * the key format as follows:
	 * 
	 * "package.Plugin.property"
	 * 
	 * For instance, use the key "com.example.buddi.ImportFoo.LAST_EXECUTE_DAY" 
	 * instead of the key "LAST_EXECUTE_DAY".
	 * @param value The value to store.  Can be any valid String.
	 */
	public void putPreference(String key, String value){
		PrefsModel.getInstance().putPluginPreference(key, value);
	}
	
	/**
	 * Saves the preferences which this panel is responsible for.
	 */
	public abstract void save() throws PluginException;
	
	/**
	 * Loads the preferences which this panel is responsible for.
	 */
	public abstract void load() throws PluginException;

	/**
	 * If true, we put a JPanel wrapper around this JPanel, so that it keeps 
	 * all the components at the top of the window, even if there is extra
	 * room.  This is correct behaviour for most preference panes, especially
	 * if they contain multiple small widgets (buttons, check boxes, etc).
	 * 
	 * If the panel is to contain a large widget which is to take up the entire
	 * frame (such as seen in the built in Plugins preference pane), override this
	 * and set to false.
	 * @return
	 */
	public boolean isUseWrapper(){
		return true;
	}
	
	/**
	 * Create the JPanel to show in the Preferences.
	 * @return A JPanel object which can be put into the Preferences panel
	 */
	public abstract JPanel getPreferencesPanel();
	
	/**
	 * Not used in PreferencePlugin
	 */
	public boolean isPluginActive() {
		return true;
	}
	
	/**
	 * Not used in PreferencePlugin
	 */
	public String getDescription() {
		return null;
	}
}
