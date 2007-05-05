/*
 * Created on Sep 14, 2006 by wyatt
 * 
 * The interface which can be extended to create custom reports.
 */
package net.sourceforge.buddi.api.plugin;

import net.sourceforge.buddi.api.manager.DataManager;

import org.homeunix.drummer.plugins.interfaces.BuddiGraphPlugin;

public abstract class BuddiGraphPluginAPI implements BuddiGraphPlugin, BuddiPluginAPI {
	protected DataManager dataManager;

	public void setDataManager(DataManager dataManager){
		this.dataManager = dataManager;
	}
	
	public DataManager getDataManager(){
		return dataManager;
	}
}
