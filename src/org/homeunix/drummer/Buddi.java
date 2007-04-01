/*
 * Created on Jun 26, 2005 by wyatt
 */
package org.homeunix.drummer;

import java.awt.Dimension;
import java.awt.Point;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JMenuBar;
import javax.swing.SwingUtilities;

import net.roydesign.mac.MRJAdapter;

import org.homeunix.drummer.controller.MainBuddiFrame;
import org.homeunix.drummer.controller.Translate;
import org.homeunix.drummer.controller.TranslateKeys;
import org.homeunix.drummer.prefs.PrefsInstance;
import org.homeunix.drummer.prefs.WindowAttributes;
import org.homeunix.drummer.util.LookAndFeelManager;
import org.homeunix.drummer.view.components.BuddiMenu;
import org.homeunix.thecave.moss.util.Formatter;
import org.homeunix.thecave.moss.util.Log;
import org.homeunix.thecave.moss.util.ParseCommands;
import org.homeunix.thecave.moss.util.ParseCommands.ParseException;

/**
 * The main class, containing the launch methods for Buddi.  This class 
 * is responsible for setting up the environment (including reading
 * command line options), loading the preferences and languages, 
 * warning the user (if using experimental code), etc.  It also initializes
 * and contains access methods for several environment-type methods,
 * such as what OS we are running on, and what the working directory is.
 * 
 * @author wyatt
 * @author jdidion - Split the LNF dialog into its own class for better maintainability
 */
public class Buddi {

	private static String workingDir;

	/**
	 * Gets the current working directory.  This should be the same directory
	 * that the Buddi.{exe|jar} is in or the Buddi.app/Contents/Resources/Java/ 
	 * folder on a Mac.  This will determine what a relative path is, which
	 * is useful for running on a thumb drive.
	 * @return
	 */
	public static String getWorkingDir(){
		if (workingDir == null){
			workingDir = "";
		}

		return workingDir;
	}

	/**
	 * Method to start the GUI.  Should be run from the AWT Dispatch thread.
	 */
	private static void launchGUI(){

		// TODO Remove this from stable versions after 1.x.0
		//Temporary notice stating the data format has changed.

//		if (PrefsInstance.getInstance().getLastVersionRun().length() > 0 
//		&& !PrefsInstance.getInstance().getLastVersionRun().equals(Const.VERSION)){
//		if (JOptionPane.showConfirmDialog(null, 
//		Translate.getInstance().get(TranslateKeys.UPGRADE_NOTICE),
//		Translate.getInstance().get(TranslateKeys.UPGRADE_NOTICE_TITLE),
//		JOptionPane.OK_CANCEL_OPTION,
//		JOptionPane.WARNING_MESSAGE
//		) == JOptionPane.CANCEL_OPTION)
//		System.exit(0);
//		}

		/*
		if (!PrefsInstance.getInstance().getLastVersionRun().equals(Const.VERSION)){
			JOptionPane.showMessageDialog(null, 
					"Warning: This version of Buddi contains code for encrypting and\ndecrypting of data files.  While a few individuals have tested\nas much as possible, it is still likely that there are bugs which\ncould result in loss of data.\n\nMake sure you have backups of any critical files before proceeding!",
					"Warning: Encryption Added",
					JOptionPane.WARNING_MESSAGE
			);
		}
		 */
		
		WindowAttributes wa = PrefsInstance.getInstance().getPrefs().getWindows().getMainWindow();
		Dimension dim = new Dimension(wa.getWidth(), wa.getHeight());
		Point point = new Point(wa.getX(), wa.getY());
		
		MainBuddiFrame.getInstance().openWindow(dim, point);
	}

	/**
	 * Main method for Buddi.  Can pass certain command line options
	 * in.  Use --help flag to see complete list.
	 * @param args
	 */
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
		Log.notice("Set working directory to " + workingDir);

		//Load the correct Look and Feel
		LookAndFeelManager.getInstance().setLookAndFeel(lnf);

		//Load the language
		Translate.getInstance().loadLanguage(
				PrefsInstance.getInstance().getPrefs().getLanguage());

		//Load the correct formatter preferences
		Formatter.getInstance(PrefsInstance.getInstance().getPrefs().getDateFormat());

		//Create the frameless menu bar (for Mac)
		JMenuBar frameless = new BuddiMenu(null);
		MRJAdapter.setFramelessJMenuBar(frameless);

		//Add any shutdown hoks you want to use.  Currently this
		// is only enabled in development versions.
//		if (Const.DEVEL){
//		Runtime.getRuntime().addShutdownHook(new Thread(){
//		@Override
//		public void run() {

//		super.run();
//		}
//		});
//		}
		
		System.setProperty("Quaqua.tabLayoutPolicy", "scroll");
		System.setProperty("Quaqua.selectionStyle", "bright");
		System.setProperty("apple.laf.useScreenMenuBar", "true");
		System.setProperty("apple.awt.graphics.EnableQ2DX", "true");
		System.setProperty("apple.awt.rendering", "VALUE_RENDER_SPEED"); // VALUE_RENDER_SPEED or VALUE_RENDER_QUALITY
		System.setProperty("apple.awt.interpolation", "VALUE_INTERPOLATION_NEAREST_NEIGHBOR"); // VALUE_INTERPOLATION_NEAREST_NEIGHBOR, VALUE_INTERPOLATION_BILINEAR, or VALUE_INTERPOLATION_BICUBIC
		System.setProperty("apple.awt.showGrowBox", "true");
		System.setProperty("com.apple.mrj.application.growbox.intrudes","true");
		System.setProperty("com.apple.mrj.application.apple.menu.about.name", Translate.getInstance().get(TranslateKeys.BUDDI));

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				launchGUI();
			}
		});
	}
}
