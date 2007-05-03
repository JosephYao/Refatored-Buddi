package net.sourceforge.buddi.api.plugin;

import javax.swing.filechooser.FileFilter;

import net.sourceforge.buddi.api.manager.UtilityManager;

public interface BuddiFilePluginAPI extends BuddiPluginAPI {

    /**
     * Should Buddi prompt the user for a file when running 
     * the plugin?  If you select true, you must also
     * specify the FileChooserTitle and FileFilter get() methods.
     * @return
     */
    public boolean isPromptForFile(UtilityManager utilityManager);
    
    /**
     * Returns the title of the File Chooser
     * @return
     */
    public String getFileChooserTitle(UtilityManager utilityManager);
    
    /**
     * Returns the file filter to use.  Either create a new
     * class, or return null to see all files.
     * @return
     */
    public FileFilter getFileFilter(UtilityManager utilityManager);
}
