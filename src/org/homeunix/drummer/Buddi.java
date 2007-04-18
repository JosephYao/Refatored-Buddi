/*
 * Created on Jun 26, 2005 by wyatt
 */
package org.homeunix.drummer;

import java.awt.Dimension;
import java.awt.Point;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import net.roydesign.mac.MRJAdapter;

import org.homeunix.drummer.controller.ReturnCodes;
import org.homeunix.drummer.controller.Translate;
import org.homeunix.drummer.controller.TranslateKeys;
import org.homeunix.drummer.prefs.PrefsInstance;
import org.homeunix.drummer.prefs.WindowAttributes;
import org.homeunix.drummer.util.LookAndFeelManager;
import org.homeunix.drummer.view.DocumentManager;
import org.homeunix.drummer.view.MainFrame;
import org.homeunix.drummer.view.menu.MainMenu;
import org.homeunix.thecave.moss.util.FileFunctions;
import org.homeunix.thecave.moss.util.Log;
import org.homeunix.thecave.moss.util.OperatingSystemUtil;
import org.homeunix.thecave.moss.util.ParseCommands;
import org.homeunix.thecave.moss.util.SwingWorker;
import org.homeunix.thecave.moss.util.ParseCommands.ParseException;

import edu.stanford.ejalbert.BrowserLauncher;

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
		//Ensure the Preferences are loaded first, so that everything can
		// be translated properly...
		PrefsInstance.getInstance();
		
		// TODO Remove this from stable versions after 2.x.0
		//Temporary notice stating the data format has changed.
		if (Const.BRANCH.equals(Const.UNSTABLE)
				&& PrefsInstance.getInstance().getLastVersionRun().length() > 0 
				&& !PrefsInstance.getInstance().getLastVersionRun().equals(Const.VERSION)){

			if (JOptionPane.showConfirmDialog(null, 
					Translate.getInstance().get(TranslateKeys.MESSAGE_UPGRADE_NOTICE),
					Translate.getInstance().get(TranslateKeys.MESSAGE_UPGRADE_NOTICE_TITLE),
					JOptionPane.OK_CANCEL_OPTION,
					JOptionPane.WARNING_MESSAGE
			) == JOptionPane.CANCEL_OPTION)
				System.exit(0);

			
			//Make a backup of the existing data file, just to be safe...
			File dataFile = new File(PrefsInstance.getInstance().getPrefs().getDataFile());
			File backupDataFile = new File(PrefsInstance.getInstance().getPrefs().getDataFile() + " backup before " + Const.VERSION + Const.BACKUP_FILE_EXTENSION);
			try {
				FileFunctions.copyFile(dataFile, backupDataFile);
			}
			catch (IOException ioe){
				Log.warning("Error backing up file: " + ioe);
			}			
		}

		//Create the frameless menu bar (for Mac)
		JMenuBar frameless = new MainMenu(null);
		MRJAdapter.setFramelessJMenuBar(frameless);
				
		//Open the main window at the saved location
		WindowAttributes wa = PrefsInstance.getInstance().getPrefs().getWindows().getMainWindow();
		Dimension dim = new Dimension(wa.getWidth(), wa.getHeight());
		Point point = new Point(wa.getX(), wa.getY());
		MainFrame.getInstance().openWindow(dim, point);
		
		//Ensure that we load a data file.
		ReturnCodes returnCode = DocumentManager.getInstance().selectDesiredDataFile();
		if (returnCode.equals(ReturnCodes.CANCEL)){
			System.exit(1);
		}

		//Start the initial checks
		startVersionCheck();
		startUpdateCheck();
	}

	/**
	 * A helper method to prompt the user with three choices: New data,
	 * Load data, or cancel (exit).
	 *
	 */
//	public static void chooseDataFileSource(){
//		DataFileWrapper dfw = DocumentManager.getInstance().chooseNewOrExistingDataFile();
//		if (dfw == null){
//			Log.info("User hit cancel.  Exiting.");
//			System.exit(0);
//		}
//		else if (dfw.isExisting()){
////			PrefsInstance.getInstance().getPrefs().setDataFile(null);
//			DocumentController.loadFile(dfw.getDataFile());
//		}
//		else {
////			PrefsInstance.getInstance().getPrefs().setDataFile(null);
//			DocumentController.newFile(dfw.getDataFile());
//		}
//	}

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

		//Load the correct formatter preferences


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

		if (OperatingSystemUtil.isMac()){
			System.setProperty("Quaqua.tabLayoutPolicy", "scroll");
			System.setProperty("Quaqua.selectionStyle", "bright");
			System.setProperty("apple.laf.useScreenMenuBar", "true");
			System.setProperty("apple.awt.graphics.EnableQ2DX", "true");
			System.setProperty("apple.awt.rendering", "VALUE_RENDER_SPEED"); // VALUE_RENDER_SPEED or VALUE_RENDER_QUALITY
			System.setProperty("apple.awt.interpolation", "VALUE_INTERPOLATION_NEAREST_NEIGHBOR"); // VALUE_INTERPOLATION_NEAREST_NEIGHBOR, VALUE_INTERPOLATION_BILINEAR, or VALUE_INTERPOLATION_BICUBIC
			System.setProperty("apple.awt.showGrowBox", "true");
			System.setProperty("com.apple.mrj.application.growbox.intrudes","true");
			System.setProperty("com.apple.mrj.application.apple.menu.about.name", Translate.getInstance().get(TranslateKeys.BUDDI));
		}

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				launchGUI();
			}
		});
	}

	/**
	 * Checks if getInstance() version has been run before, and if not, displays any messages that show on first run. 
	 */
	private static void startVersionCheck(){
		if (!PrefsInstance.getInstance().getLastVersionRun().equals(Const.VERSION)){
			PrefsInstance.getInstance().updateVersion();

			SwingWorker worker = new SwingWorker() {

				@Override
				public Object construct() {
					return null;
				}

				@Override
				public void finished() {
					String[] buttons = new String[2];
					buttons[0] = Translate.getInstance().get(TranslateKeys.BUTTON_DONATE);
					buttons[1] = Translate.getInstance().get(TranslateKeys.BUTTON_NOT_NOW);

					int reply = JOptionPane.showOptionDialog(
							MainFrame.getInstance(), 
							Translate.getInstance().get(TranslateKeys.MESSAGE_ASK_FOR_DONATION),
							Translate.getInstance().get(TranslateKeys.MESSAGE_ASK_FOR_DONATION_TITLE),
							JOptionPane.YES_NO_OPTION,
							JOptionPane.INFORMATION_MESSAGE,
							null,
							buttons,
							buttons[0]);

					if (reply == JOptionPane.YES_OPTION){
						try{
							BrowserLauncher bl = new BrowserLauncher(null);
							bl.openURLinBrowser(Const.DONATE_URL);
						}
						catch (Exception e){
							Log.error(e);
						}
					}

					super.finished();
				}
			};

			worker.start();
		}
	}

	/**
	 * Starts a thread which checks the Internet for any new versions.
	 */
	public static void startUpdateCheck(){
		if (PrefsInstance.getInstance().getPrefs().isEnableUpdateNotifications()){
			SwingWorker updateWorker = new SwingWorker(){

				@Override
				public Object construct() {
					try{
						Properties versions = new Properties();
						URL mostRecentVersion = new URL(Const.PROJECT_URL + Const.VERSION_FILE);

						versions.load(mostRecentVersion.openStream());

						int majorAvailable = 0, minorAvailable = 0, bugfixAvailable = 0;
						int majorThis = 0, minorThis = 0, bugfixThis = 0;

						String[] available = versions.get(Const.BRANCH).toString().split("\\.");
						String[] thisVersion = Const.VERSION.split("\\.");

						if (available.length > 2){
							majorAvailable = Integer.parseInt(available[0]);
							minorAvailable = Integer.parseInt(available[1]);
							bugfixAvailable = Integer.parseInt(available[2]);
						}

						if (thisVersion.length > 2){
							majorThis = Integer.parseInt(thisVersion[0]);
							minorThis = Integer.parseInt(thisVersion[1]);
							bugfixThis = Integer.parseInt(thisVersion[2]);
						}

						Log.debug("This version: " + majorThis + "." + minorThis + "." + bugfixThis);
						Log.debug("Available version: " + majorAvailable + "." + minorAvailable + "." + bugfixAvailable);

						if (majorAvailable > majorThis
								|| (majorAvailable == majorThis && minorAvailable > minorThis)
								|| (majorAvailable == majorThis && minorAvailable == minorThis && bugfixAvailable > bugfixThis)){
							return versions.get(Const.BRANCH);
						}
					}
					catch (MalformedURLException mue){
						Log.error(mue);
					}
					catch (IOException ioe){
						Log.error(ioe);
					}

					return null;
				}

				@Override
				public void finished() {
					if (get() != null){
						String[] buttons = new String[2];
						buttons[0] = Translate.getInstance().get(TranslateKeys.BUTTON_DOWNLOAD);
						buttons[1] = Translate.getInstance().get(TranslateKeys.BUTTON_CANCEL);

						int reply = JOptionPane.showOptionDialog(
								MainFrame.getInstance(), 
								Translate.getInstance().get(TranslateKeys.NEW_VERSION_MESSAGE)
								+ " " + get() + "\n"
								+ Translate.getInstance().get(TranslateKeys.NEW_VERSION_MESSAGE_2),
								Translate.getInstance().get(TranslateKeys.NEW_VERSION),
								JOptionPane.YES_NO_OPTION,
								JOptionPane.INFORMATION_MESSAGE,
								null,
								buttons,
								buttons[0]);

						if (reply == JOptionPane.YES_OPTION){
							String fileLocation;
							if (Const.BRANCH.equals(Const.STABLE))
								fileLocation = Const.DOWNLOAD_URL_STABLE;
							else
								fileLocation = Const.DOWNLOAD_URL_UNSTABLE;

							//Link to the correct download by default.
							if (OperatingSystemUtil.isMac())
								fileLocation += Const.DOWNLOAD_URL_DMG;
							else if (OperatingSystemUtil.isWindows())
								fileLocation += Const.DOWNLOAD_URL_ZIP;
							else
								fileLocation += Const.DOWNLOAD_URL_TGZ;

							try{
								BrowserLauncher bl = new BrowserLauncher(null);
								bl.openURLinBrowser(fileLocation);
							}
							catch (Exception e){
								Log.error(e);
							}
						}
					}

					super.finished();
				}
			};

			if (Const.DEVEL) Log.debug("Starting update checking...");
			updateWorker.start();
		}
	}
}
