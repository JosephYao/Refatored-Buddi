/*
 * Created on May 6, 2006 by wyatt
 */
package org.homeunix.drummer.controller;

import java.io.File;

import javax.swing.JPanel;

import org.homeunix.drummer.Const;
import org.homeunix.drummer.plugins.BuddiPluginFactory;
import org.homeunix.drummer.prefs.PluginEntry;
import org.homeunix.drummer.prefs.PluginJar;
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
			for (Object o1 : PrefsInstance.getInstance().getPrefs().getCustomPlugins().getJars()){
				if (o1 instanceof PluginJar){
					PluginJar pluginJar = (PluginJar) o1;
					File jarFile = new File((pluginJar).getJarFile());
					for (Object o2 : pluginJar.getPanelPlugins()) {
						if (o2 instanceof PluginEntry){
							PluginEntry pluginEntry = (PluginEntry) o2;
							JPanel pluginPanel = BuddiPluginFactory.getPanelPluginLauncher((pluginEntry).getClassName());
							if (pluginPanel != null)
								pluginsPanel.add(pluginPanel);
						}
					}
				}
			}
		}
	}
}
