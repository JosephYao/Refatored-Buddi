/*
 * Created on Apr 7, 2007 by wyatt
 */
package org.homeunix.drummer.controller;

import java.io.File;
import java.io.IOException;

import org.homeunix.drummer.model.DataInstance;
import org.homeunix.drummer.prefs.PrefsInstance;
import org.homeunix.drummer.view.MainFrame;
import org.homeunix.thecave.moss.util.Log;

public class DocumentController {
	
	public static void loadFile(File f){
		PrefsInstance.getInstance().getPrefs().setDataFile(f.getAbsolutePath());
		DataInstance.getInstance().loadDataFile(f);
		MainFrame.getInstance().updateContent();
		DataInstance.getInstance().saveDataFile();
	}
	
	public static void newFile(File f){
		PrefsInstance.getInstance().getPrefs().setDataFile(f.getAbsolutePath());
		DataInstance.getInstance().newDataFile(f);
		MainFrame.getInstance().updateContent();
		DataInstance.getInstance().saveDataFile();
	}
	
	public static void saveFile(){
		DataInstance.getInstance().saveDataFile();
	}
	
	/**
	 * Saves the data file in a separate thread.  This can be used
	 * to schedule a save, but not take up GUI time.
	 */
	public static void saveFileSoon(){
		Thread thread = new Thread(new Runnable() {
			public void run() {
				saveFile();
			}
		});
		
		thread.run();
	}

	
	public static boolean saveFile(File f){
		try {
			DataInstance.getInstance().saveDataFile(f.getAbsolutePath());
			return true;
		}
		catch (IOException ioe){
			Log.error("Error saving data file: " + ioe);
			return false;
		}
	}	
}
