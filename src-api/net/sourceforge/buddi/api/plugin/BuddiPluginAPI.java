package net.sourceforge.buddi.api.plugin;

import org.homeunix.drummer.plugins.interfaces.BuddiPluginGeneric;
import org.homeunix.thecave.moss.util.Version;

import net.sourceforge.buddi.api.manager.UtilityManager;

public interface BuddiPluginAPI extends BuddiPluginGeneric {

    /**
     * Returns the description text, as seen on the main window 
     * under Reports tab, or in the menu
     * @return
     */
    public String getDescription(UtilityManager utilityManager);
    
    
    /**
     * Returns the minimum version of the API.  When loading, 
     * we should not load if the implemented API version is 
     * lower than the plugin's minimum API version.
     * @return
     */
    public Version getMinimumAPIVersion();
}
