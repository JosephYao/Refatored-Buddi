/*
 * Created on Aug 12, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.view.dialogs.prefs;

import javax.swing.JPanel;

import org.homeunix.thecave.buddi.i18n.BuddiKeys;
import org.homeunix.thecave.buddi.plugin.api.BuddiPreferencePlugin;
import org.homeunix.thecave.moss.util.Version;

public class NetworkPreferences extends BuddiPreferencePlugin {
	public static final long serialVersionUID = 0;
	
	public void load() {
		
	}
	
	public void save() {
		
	}
	public Version getMaximumVersion() {
		return null;
	}
	
	public Version getMinimumVersion() {
		return null;
	}
	
	@Override
	public JPanel getPreferencesPanel() {
		return new JPanel();
	}
	
	public String getName() {
		return BuddiKeys.NETWORK.toString();
	}
}
