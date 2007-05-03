package net.sourceforge.buddi.api.plugin;

import java.io.File;

import javax.swing.JFrame;

import net.sourceforge.buddi.api.manager.ImportManager;

public interface BuddiImportPluginAPI extends BuddiMenuPluginAPI {

    /**
     * Imports data as required.  The implementor chooses where to send
     * the file (it is reccomended to open a JFileChooser, but
     * this is up to the implementor to decide).
     * 
     * @param importManager ImportManager for creating and providing access to Buddi objects.
     * @param frame The frame from which the command was called.  Can be 
     * used to determine what type of export to do.
     */
    public void importData(ImportManager importManager, JFrame frame, File file);
}
