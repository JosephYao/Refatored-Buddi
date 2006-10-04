/*
 * Created on May 6, 2006 by wyatt
 */
package org.homeunix.drummer.controller;

import javax.swing.JPanel;

import org.homeunix.drummer.Const;
import org.homeunix.drummer.plugins.BuddiPluginFactory;
import org.homeunix.drummer.prefs.PluginEntry;
import org.homeunix.drummer.prefs.PrefsInstance;
import org.homeunix.drummer.view.ReportPanelLayout;

public class ReportPanel extends ReportPanelLayout {
	public static final long serialVersionUID = 0;
		
	public ReportPanel(){
		super();
		
		for (String pluginClassName : Const.BUILT_IN_PANEL_PLUGINS) {
			JPanel pluginPanel = BuddiPluginFactory.getPanelPluginLauncher(pluginClassName);
			if (pluginPanel != null)
				pluginsPanel.add(pluginPanel);
		}

		if (PrefsInstance.getInstance().getPrefs().getCustomPlugins() != null) {
			for (Object entry : PrefsInstance.getInstance().getPrefs().getCustomPlugins().getPanelPlugins()) {
				if (entry instanceof PluginEntry){
					JPanel pluginPanel = BuddiPluginFactory.getPanelPluginLauncher(((PluginEntry) entry).getClassName());
					if (pluginPanel != null)
						pluginsPanel.add(pluginPanel);
				}
			}
		}
	}
}
