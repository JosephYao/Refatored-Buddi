/*
 * Created on Aug 12, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.view.dialogs.prefs;

public interface PrefsPanel {

	/**
	 * Saves the preferences which this panel is responsible for.
	 */
	public void save();
	
	/**
	 * Loads the preferences which this panel is responsible for.
	 */
	public void load();
}
