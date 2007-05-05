package net.sourceforge.buddi.api.plugin;

import org.homeunix.drummer.plugins.interfaces.BuddiGenericAPIPlugin;
import org.homeunix.thecave.moss.util.Version;

public interface BuddiPluginAPI extends BuddiGenericAPIPlugin {    
    /**
     * Returns the minimum version of the API.  When loading, 
     * we should not load if the implemented API version is 
     * lower than the plugin's minimum API version.
     * @return
     */
    public Version getMinimumAPIVersion();
    
//    /**
//     * Returns menu should currently be active.
//     */
//    public boolean isPluginActive(DataManager dataManager);
}
