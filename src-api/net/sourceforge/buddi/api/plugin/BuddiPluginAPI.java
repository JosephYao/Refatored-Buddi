package net.sourceforge.buddi.api.plugin;

import org.homeunix.drummer.plugins.interfaces.BuddiPluginGeneric;

import net.sourceforge.buddi.api.manager.UtilityManager;

public interface BuddiPluginAPI extends BuddiPluginGeneric {

    /**
     * Returns the description text, as seen on the main window 
     * under Reports tab, or in the menu
     * @return
     */
    public String getDescription(UtilityManager utilityManager);
}
