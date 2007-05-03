package net.sourceforge.buddi.api.plugin;

import java.io.File;

import javax.swing.JFrame;

import net.sourceforge.buddi.api.manager.DataManager;

public interface BuddiExportPluginAPI extends BuddiMenuPluginAPI {
    /**
     * Exports data as required.  The implementor chooses where to send
     * the file (it is reccomended to open a JFileChooser, but
     * this is up to the implementor to decide).
     * 
     * @param dataManager DataManager for providing access to Buddi objects.
     * @param frame The frame from which the command was called.  Can be 
     * used to determine what type of export to do.
     * @param file the file to export to.  Has just been chosen by the user.
     * If you specify isPromptForFile() == false, this
     * string will be null.
     */
    public void exportData(DataManager dataManager, JFrame frame, File file);
}
