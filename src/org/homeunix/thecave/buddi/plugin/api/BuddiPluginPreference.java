/*
 * Created on Sep 7, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.plugin.api;

import org.homeunix.thecave.buddi.model.prefs.PrefsModel;

/**
 * An abstract class which provides plugins the functionality of storing and
 * reading preferences in the main Buddi preferences file. 
 * @author wyatt
 *
 */
public abstract class BuddiPluginPreference {
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
}
