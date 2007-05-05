package net.sourceforge.buddi.api.plugin;

import net.sourceforge.buddi.api.manager.DataManager;

import org.homeunix.drummer.plugins.interfaces.BuddiExportPlugin;

public abstract class BuddiExportPluginAPI implements BuddiExportPlugin, BuddiPluginAPI {

	protected DataManager dataManager;

	public void setDataManager(DataManager dataManager){
		this.dataManager = dataManager;
	}
	
	public DataManager getDataManager(){
		return dataManager;
	}
}
