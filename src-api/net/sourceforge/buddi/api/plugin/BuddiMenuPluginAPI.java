package net.sourceforge.buddi.api.plugin;

import net.sourceforge.buddi.api.manager.DataManager;

public interface BuddiMenuPluginAPI extends BuddiFilePluginAPI {

    /**
     * Returns menu should currently be active.
     */
    public boolean isPluginActive(DataManager dataManager);
}
