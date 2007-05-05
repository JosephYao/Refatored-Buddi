package net.sourceforge.buddi.api.plugin;

import net.sourceforge.buddi.api.manager.DataManager;

import org.homeunix.drummer.plugins.interfaces.BuddiImportPlugin;

public abstract class BuddiImportPluginAPI implements BuddiImportPlugin, BuddiPluginAPI {
	protected DataManager dataManager;

	public void setDataManager(DataManager dataManager){
		this.dataManager = dataManager;
	}
	
	public DataManager getDataManager(){
		return dataManager;
	}
}
