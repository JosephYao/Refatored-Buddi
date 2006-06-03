/*
 * Created on Jun 26, 2005 by wyatt
 */
package org.homeunix.drummer;

import java.util.HashMap;
import java.util.Map;

import javax.swing.UIManager;

import net.roydesign.mac.MRJAdapter;

import org.homeunix.drummer.controller.PrefsInstance;
import org.homeunix.drummer.util.Log;
import org.homeunix.drummer.util.ParseCommands;
import org.homeunix.drummer.util.ParseCommands.ParseException;
import org.homeunix.drummer.view.components.BudgetMenu;
import org.homeunix.drummer.view.logic.MainBudgetFrame;

/**
 * @author wyatt
 *
 */
public class Buddi {
	
	public static final String version = "0.95 Beta";
	
	private static Boolean isMac;
	private static final boolean UI_DEBUG = false; 
	
	public static boolean isMac(){
		if (isMac == null){
			isMac = !UI_DEBUG && System.getProperty("os.name").equals("Mac OS X");
		}
		
		return isMac;
	}
	
	private static void launchGUI(){
		MainBudgetFrame.getInstance().openWindow();
	}
	
	public static void main(String[] args) {
		String prefsLocation = "";
		Integer verbosity = 0;
		
		String help = "USAGE: java -jar Buddi.jar <options>, where options include:\n" 
			+ "-p\tFilename\tPath and name of Preference File\n"
			+ "-v\t0-7\tVerbosity Level (7 = Debug)";
		
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("-p", prefsLocation);
		map.put("-v", verbosity);
		try{
			map = ParseCommands.parse(args, map, help);
		}
		catch (ParseException pe){
			Log.critical(pe);
			System.exit(1);
		}
		prefsLocation = (String) map.get("-p");
		verbosity = (Integer) map.get("-v");
		
		if (prefsLocation != null){
			PrefsInstance.setLocation(prefsLocation);
		}
		
		if (verbosity != null){
			Log.setLogLevel(verbosity);
		}
		
		if (isMac()){
			// set system properties here that affect Quaqua
			// for example the default layout policy for tabbed
			// panes:
			System.setProperty("Quaqua.tabLayoutPolicy","scroll");
			System.setProperty("Quaqua.selectionStyle","bright");
			
			// set the Quaqua Look and Feel in the UIManager
			try {
				UIManager.setLookAndFeel("ch.randelshofer.quaqua.QuaquaLookAndFeel");
				
				// set UI manager properties here that affect Quaqua
			} catch (Exception e) {
				Log.error(e);
			}
		}
		
		Translate.inst().loadLanguage(
				PrefsInstance.getInstance().getPrefs().getLanguage()
		);
		
		MRJAdapter.setFramelessJMenuBar(new BudgetMenu(null));
		
		
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				launchGUI();
			}
		});		
	}
}
