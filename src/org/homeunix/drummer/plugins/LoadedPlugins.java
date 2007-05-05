/*
 * Created on May 5, 2007 by wyatt
 */
package org.homeunix.drummer.plugins;

import java.util.Collection;
import java.util.LinkedList;

import net.sourceforge.buddi.api.manager.DataManager;
import net.sourceforge.buddi.api.plugin.BuddiMenuPlugin;
import net.sourceforge.buddi.api.plugin.BuddiPanelPlugin;
import net.sourceforge.buddi.api.plugin.BuddiPlugin;

/**
 * @author wyatt
 * Keeps track of all plugins currently loaded.  This allows us
 * to update them on command.
 */
public class LoadedPlugins {
	
	Collection<BuddiPlugin> plugins = new LinkedList<BuddiPlugin>();
	
	public void addPlugin(BuddiPlugin plugin){
		plugins.add(plugin);
	}
	
	public void removePlugin(BuddiPlugin plugin){
		plugins.remove(plugin);
	}
	
	public void updateAllPlugins(DataManager dataManager){
		for (BuddiPlugin plugin : plugins) {
			if (plugin instanceof BuddiPanelPlugin){
				BuddiPanelPlugin panel = (BuddiPanelPlugin) plugin;
				panel.setVisible(panel.isPluginActive(dataManager));
			}
			else if (plugin instanceof BuddiMenuPlugin){
				BuddiMenuPlugin menu = (BuddiMenuPlugin) plugin;
				menu.setEnabled(menu.isPluginActive(dataManager));				
			}
		}
	}
}
