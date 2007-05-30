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
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.plaf.FontUIResource;

import net.roydesign.mac.MRJAdapter;

import org.homeunix.drummer.controller.ReturnCodes;
import org.homeunix.drummer.controller.Translate;
import org.homeunix.drummer.controller.TranslateKeys;
import org.homeunix.drummer.prefs.PrefsInstance;
import org.homeunix.drummer.prefs.WindowAttributes;
import org.homeunix.drummer.util.AppleApplicationWrapper;
import org.homeunix.drummer.util.LookAndFeelManager;
import org.homeunix.drummer.view.DocumentManager;
import org.homeunix.drummer.view.MainFrame;
import org.homeunix.drummer.view.menu.MainMenu;
import org.homeunix.thecave.moss.util.FileFunctions;
import org.homeunix.thecave.moss.util.Log;
import org.homeunix.thecave.moss.util.OperatingSystemUtil;
import org.homeunix.thecave.moss.util.ParseCommands;
import org.homeunix.thecave.moss.util.SwingWorker;
import org.homeunix.thecave.moss.util.Version;
import org.homeunix.thecave.moss.util.ParseCommands.ParseResults;
import org.homeunix.thecave.moss.util.ParseCommands.ParseVariable;

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
	private static String fileToLoad;
	private static Boolean debian = false;
	private static Boolean slackware = false;
	private static Boolean redhat = false;

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
	 * @return True if running on Slackware, false otherwise.  This is 
	 * obtained through the startup flag --debian, so it can be 
	 * faked if desired.  This is used to determine which download
	 * to use on Linux - if the flag is set, we will download
	 * a .deb when a new version is released, otherwise we will
	 * download a .jar.
	 */
	public static boolean isSlackware(){
		if (slackware == null)
			slackware = false;
		return slackware;
	}
	
	/** 
	 * @return True if running on Redhat, false otherwise.  This is 
	 * obtained through the startup flag --debian, so it can be 
	 * faked if desired.  This is used to determine which download
	 * to use on Linux - if the flag is set, we will download
	 * a .deb when a new version is released, otherwise we will
	 * download a .jar.
	 */
	public static boolean isRedhat(){
		if (redhat == null)
			redhat = false;
		return redhat;
	}
	
	/** 
	 * @return True if running on Debian, false otherwise.  This is 
	 * obtained through the startup flag --debian, so it can be 
	 * faked if desired.  This is used to determine which download
	 * to use on Linux - if the flag is set, we will download
	 * a .deb when a new version is released, otherwise we will
	 * download a .jar.
	 */
	public static boolean isDebian(){
		if (debian == null)
			debian = false;
		return debian;
	}
	
	/**
	 * Method to start the GUI.  Should be run from the AWT Dispatch thread.
	 */
	private static void launchGUI(){
		//Ensure the Preferences are loaded first, so that everything can
		// be translated properly...
		PrefsInstance.getInstance();

		//Temporary notice stating the data format has changed.
		if (Const.BRANCH.equals(Const.UNSTABLE)
				&& PrefsInstance.getInstance().getLastVersionRun().length() > 0 
				&& !PrefsInstance.getInstance().getLastVersionRun().equals(Const.VERSION)){

			String[] options = new String[2];
			options[0] = Translate.getInstance().get(TranslateKeys.BUTTON_OK);
			options[1] = Translate.getInstance().get(TranslateKeys.BUTTON_CANCEL);

			if (JOptionPane.showOptionDialog(null, 
					Translate.getInstance().get(TranslateKeys.MESSAGE_UPGRADE_NOTICE),
					Translate.getInstance().get(TranslateKeys.MESSAGE_UPGRADE_NOTICE_TITLE),
					JOptionPane.OK_CANCEL_OPTION,
					JOptionPane.WARNING_MESSAGE,
					null,
					options,
					options[0]
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
		ReturnCodes returnCode = DocumentManager.getInstance().selectDesiredDataFile(fileToLoad);
		if (returnCode.equals(ReturnCodes.CANCEL)){
			System.exit(1);
		}
		
		//Start the initial checks
		startVersionCheck();
		startUpdateCheck(false);
	}

	/**
	 * Main method for Buddi.  Can pass certain command line options
	 * in.  Use --help flag to see complete list.
	 * @param args
	 */
	public static void main(String[] args) {
		//Absolute first thing to do is to catch any open requests from Apple Launchd.
		if (OperatingSystemUtil.isMac()){
			AppleApplicationWrapper.addApplicationListener();
		}
		
		String help = "USAGE: java -jar Buddi.jar <options> <data file>, where options include:\n" 
			+ "-p\tFilename\tPath and name of Preference File\n"
			+ "-v\t0-7\tVerbosity Level (7 = Debug)\n"
			+ "--lnf\tclassName\tJava Look and Feel to use\n"
			+ "--font\tfontName\tFont to use by default\n";
		// Undocumented flag --debian will specify a .deb download for new versions.
		// Undocumented flag --redhat will specify a .rpm download for new versions.
		// Undocumented flag --slackware will specify a .tgz download for new versions.

		List<ParseVariable> variables = new LinkedList<ParseVariable>();
		variables.add(new ParseVariable("-p", String.class, false));
		variables.add(new ParseVariable("-v", Integer.class, false));
		variables.add(new ParseVariable("--lnf", String.class, false));
		variables.add(new ParseVariable("--font", String.class, false));
		variables.add(new ParseVariable("--debian", Boolean.class, false));
		variables.add(new ParseVariable("--redhat", Boolean.class, false));
		variables.add(new ParseVariable("--slackware", Boolean.class, false));
				
		ParseResults results = ParseCommands.parse(args, help, variables);
		String prefsLocation = results.getString("-p");
		Integer verbosity = results.getInteger("-v");
		String lnf = results.getString("--lnf");
		String font = results.getString("--font");
		debian = results.getBoolean("--debian");
		redhat = results.getBoolean("--redhat");
		slackware = results.getBoolean("--slackware");
		if (fileToLoad == null
				&& results.getCommands().size() > 0)
			fileToLoad = results.getCommands().get(0);

		Log.debug("File to Load == " + fileToLoad);
		
		if (prefsLocation != null){
			PrefsInstance.setLocation(prefsLocation);
		}

		if (verbosity != null){
			Log.setLogLevel(verbosity);
		}

		//If we specify a different font, replace all font instances with it.
		// This is useful for some locales, in which the default font
		// won't work properly.
		if (font != null){
			Enumeration<Object> keys = UIManager.getDefaults().keys();
			while (keys.hasMoreElements()) {
				Object key = keys.nextElement();
				Object value = UIManager.get(key);
				if (value instanceof FontUIResource){
					FontUIResource f = (FontUIResource) value;
					UIManager.put(key, new FontUIResource(font, f.getStyle(), f.getSize()));
				}
			}  
		}

		//Set the working path.  If we save files (plugins, data files) 
		// within this path, we remove this parent path.  This allows
		// us to use relative paths for such things as running from
		// USB drives, from remote shares (which would not always have
		// the same path), etc.
		try {
			if (System.getProperty("user.dir").length() > 0)
				workingDir = new File(System.getProperty("user.dir")).getCanonicalPath() + File.separator; //  + (!OperatingSystemUtil.isWindows() ? File.separator : "");

			//If we did not set it via user.dir, we set it here.
			if (workingDir.length() == 0)
				workingDir = new File("").getCanonicalPath() + File.separator; // + (!OperatingSystemUtil.isWindows() ? File.separator : "");
		}
		catch (IOException ioe){
			//Fallback which does not throw IOException, but may get drive case incorrect on Windows.
			workingDir = new File("").getAbsolutePath() + File.separator; // + (!OperatingSystemUtil.isWindows() ? File.separator : "");
		}
		
		Log.notice("Set working directory to " + workingDir);

		//Load the correct Look and Feel
		LookAndFeelManager.getInstance().setLookAndFeel(lnf);

		//Set some Mac-specific GUI options
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

		//Start the GUI in the proper thread
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
	public static void startUpdateCheck(final boolean confirm){
		if (confirm || PrefsInstance.getInstance().getPrefs().isEnableUpdateNotifications()){
			SwingWorker updateWorker = new SwingWorker(){

				@Override
				public Object construct() {
					try{
						Properties versions = new Properties();
						URL mostRecentVersion = new URL(Const.PROJECT_URL + Const.VERSION_FILE);

						versions.load(mostRecentVersion.openStream());

//						int majorAvailable = 0, minorAvailable = 0, bugfixAvailable = 0;
//						int majorThis = 0, minorThis = 0, bugfixThis = 0;

						Version availableVersion = new Version(versions.get(Const.BRANCH).toString());
						Version thisVersion = new Version(Const.VERSION);
//						String[] available = versions.get(Const.BRANCH).toString().split("\\.");
//						String[] thisVersion = Const.VERSION.split("\\.");
//
//						if (available.length > 2){
//							majorAvailable = Integer.parseInt(available[0]);
//							minorAvailable = Integer.parseInt(available[1]);
//							bugfixAvailable = Integer.parseInt(available[2]);
//						}
//
//						if (thisVersion.length > 2){
//							majorThis = Integer.parseInt(thisVersion[0]);
//							minorThis = Integer.parseInt(thisVersion[1]);
//							bugfixThis = Integer.parseInt(thisVersion[2]);
//						}

						Log.debug("This version: " + thisVersion);
						Log.debug("Available version: " + availableVersion);

						if (thisVersion.compareTo(availableVersion) < 0)
							return availableVersion;
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
								fileLocation += Const.DOWNLOAD_TYPE_OSX;
							else if (OperatingSystemUtil.isWindows())
								fileLocation += Const.DOWNLOAD_TYPE_WINDOWS;
							else {
								//Check for any specific distributions here
								if (Buddi.isDebian())
									fileLocation += Const.DOWNLOAD_TYPE_DEBIAN;
								else if (Buddi.isSlackware())
									fileLocation += Const.DOWNLOAD_TYPE_SLACKWARE;
								else if (Buddi.isRedhat())
									fileLocation += Const.DOWNLOAD_TYPE_REDHAT;
								else
									fileLocation += Const.DOWNLOAD_TYPE_GENERIC;
							}

							try{
								BrowserLauncher bl = new BrowserLauncher(null);
								bl.openURLinBrowser(fileLocation);
							}
							catch (Exception e){
								Log.error(e);
							}
						}
					}
					//There was no updates - if we want a confirmation, show it
					else if (confirm){
						String[] options = new String[1];
						options[0] = Translate.getInstance().get(TranslateKeys.BUTTON_OK);

						JOptionPane.showOptionDialog(
								MainFrame.getInstance(), 
								Translate.getInstance().get(TranslateKeys.MESSAGE_NO_NEW_VERSION), 
								Translate.getInstance().get(TranslateKeys.MESSAGE_NO_NEW_VERSION_TITLE), 
								JOptionPane.DEFAULT_OPTION,
								JOptionPane.INFORMATION_MESSAGE,
								null,
								options,
								options[0]);
					}

					super.finished();
				}
			};

			if (Const.DEVEL) Log.debug("Starting update checking...");
			updateWorker.start();
		}
	}
	
	public static void setFileToLoad(String fileToLoad){
		Buddi.fileToLoad = fileToLoad;
	}
}
