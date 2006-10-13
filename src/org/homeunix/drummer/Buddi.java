/*
 * Created on Jun 26, 2005 by wyatt
 */
package org.homeunix.drummer;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.swing.SwingUtilities;

import net.roydesign.mac.MRJAdapter;

import org.homeunix.drummer.controller.MainBuddiFrame;
import org.homeunix.drummer.controller.Translate;
import org.homeunix.drummer.prefs.PrefsInstance;
import org.homeunix.drummer.util.LookAndFeelManager;
import org.homeunix.drummer.view.components.BuddiMenu;
import org.homeunix.thecave.moss.util.Formatter;
import org.homeunix.thecave.moss.util.Log;
import org.homeunix.thecave.moss.util.ParseCommands;
import org.homeunix.thecave.moss.util.ParseCommands.ParseException;

/**
 * @author wyatt
 * @author jdidion
 */
public class Buddi {

	private static Boolean isMac;
	private static Boolean isWindows;
	private static String workingDir;
	private static final boolean UI_DEBUG = false;

	public static boolean isMac(){
		if (isMac == null){
			isMac = !UI_DEBUG && System.getProperty("os.name").startsWith("Mac OS");
		}

		return isMac;
	}

	public static Boolean isWindows(){
		if (isWindows == null){
			isWindows = !UI_DEBUG && System.getProperty("os.name").startsWith("Windows");
		}

		return isWindows;
	}

	public static String getWorkingDir(){
		if (workingDir == null){
			workingDir = "";
		}

		return workingDir;
	}

	private static void launchGUI(){

		// TODO Remove this from stable versions after 1.x.0
		//Temporary notice stating the data format has changed.
		/*	
		if (!PrefsInstance.getInstance().getLastVersionRun().equals(Const.VERSION)){
			if (JOptionPane.showConfirmDialog(null, 
					Translate.getInstance().get(TranslateKeys.UPGRADE_NOTICE),
					Translate.getInstance().get(TranslateKeys.UPGRADE_NOTICE_TITLE),
					JOptionPane.OK_CANCEL_OPTION,
					JOptionPane.WARNING_MESSAGE
			) == JOptionPane.CANCEL_OPTION)
				System.exit(0);
		}
		 */
		/*
		if (!PrefsInstance.getInstance().getLastVersionRun().equals(Const.VERSION)){
			JOptionPane.showMessageDialog(null, 
					"Warning: This version of Buddi contains code for encrypting and\ndecrypting of data files.  While a few individuals have tested\nas much as possible, it is still likely that there are bugs which\ncould result in loss of data.\n\nMake sure you have backups of any critical files before proceeding!",
					"Warning: Encryption Added",
					JOptionPane.WARNING_MESSAGE
			);
		}
		 */

		MainBuddiFrame.getInstance().openWindow();
	}

	public static void main(String[] args) {
		String prefsLocation = "";
		Integer verbosity = new Integer(0);
		String lnf = "";

		String help = "USAGE: java -jar Buddi.jar <options>, where options include:\n" 
			+ "-p\tFilename\tPath and name of Preference File\n"
			+ "-v\t0-7\tVerbosity Level (7 = Debug)\n"
			+ "--lnf\tclassName\tJava Look and Feel to use\n";

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("-p", prefsLocation);
		map.put("-v", verbosity);
		map.put("--lnf", lnf);
		try{
			map = ParseCommands.parse(args, map, help);
		}
		catch (ParseException pe){
			Log.critical(pe);
			System.exit(1);
		}
		prefsLocation = (String) map.get("-p");
		verbosity = (Integer) map.get("-v");
		lnf = (String) map.get("--lnf");

		if (prefsLocation != null){
			PrefsInstance.setLocation(prefsLocation);
		}

		if (verbosity != null){
			Log.setLogLevel(verbosity);
		}

		//Set the working path.  If we save files (plugins, data files) 
		// within this path, we remove this parent path.  This allows
		// us to use relative paths for such things as running from
		// USB drives, from remote shares (which would not always have
		// the same path), etc.
		workingDir = new File("").getAbsolutePath() + File.separator;
		if (Const.DEVEL) Log.info("Set working directory to " + workingDir);

		//Load the correct Look and Feel
		LookAndFeelManager.getInstance().setLookAndFeel(lnf);

		//Load the language
		Translate.getInstance().loadLanguage(
				PrefsInstance.getInstance().getPrefs().getLanguage());

		//Load the correct formatter preferences
		Formatter.getInstance(PrefsInstance.getInstance().getPrefs().getDateFormat());

		//Create the frameless menu bar (for Mac)
		MRJAdapter.setFramelessJMenuBar(new BuddiMenu(null));

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				launchGUI();
			}
		});
	}
}
