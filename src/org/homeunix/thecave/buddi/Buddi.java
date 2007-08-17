/*
 * Created on Jun 26, 2005 by wyatt
 */
package org.homeunix.thecave.buddi;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.plaf.FontUIResource;

import net.java.dev.SwingWorker;

import org.homeunix.thecave.buddi.i18n.BuddiKeys;
import org.homeunix.thecave.buddi.i18n.keys.ButtonKeys;
import org.homeunix.thecave.buddi.i18n.keys.MessageKeys;
import org.homeunix.thecave.buddi.model.DataModel;
import org.homeunix.thecave.buddi.model.exception.DataModelProblemException;
import org.homeunix.thecave.buddi.model.exception.DocumentLoadException;
import org.homeunix.thecave.buddi.model.prefs.PrefsModel;
import org.homeunix.thecave.buddi.view.AccountFrame;
import org.homeunix.thecave.buddi.view.menu.items.EditPreferences;
import org.homeunix.thecave.buddi.view.menu.items.FileQuit;
import org.homeunix.thecave.buddi.view.menu.items.HelpAbout;
import org.homeunix.thecave.moss.exception.WindowOpenException;
import org.homeunix.thecave.moss.swing.window.MossFrame;
import org.homeunix.thecave.moss.util.FileFunctions;
import org.homeunix.thecave.moss.util.Log;
import org.homeunix.thecave.moss.util.LookAndFeelUtil;
import org.homeunix.thecave.moss.util.OperatingSystemUtil;
import org.homeunix.thecave.moss.util.ParseCommands;
import org.homeunix.thecave.moss.util.Version;
import org.homeunix.thecave.moss.util.ParseCommands.ParseResults;
import org.homeunix.thecave.moss.util.ParseCommands.ParseVariable;
import org.homeunix.thecave.moss.util.apple.Application;
import org.homeunix.thecave.moss.util.apple.ApplicationAdapter;
import org.homeunix.thecave.moss.util.apple.ApplicationEvent;

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
	private static List<File> filesToLoad;
	private static Boolean debian = false;
	private static Boolean slackware = false;
	private static Boolean redhat = false;
	private static Boolean simpleFont = false;
	private static File logFile = null;

	/**
	 * Use simple fonts (i.e., no bold or italics) for transaction window.  This 
	 * allows for better Unicode support on some platforms which do not include
	 * good support for Unicode fonts, such as Windows.
	 * @return
	 */
	public static Boolean isSimpleFont(){
		if (simpleFont == null)
			simpleFont = false;
		return simpleFont;
	}

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
		
		if (PrefsModel.getInstance().getLastVersion() == null || Const.VERSION.isGreaterPatch(PrefsModel.getInstance().getLastVersion())){
			String[] options = new String[2];
			options[0] = PrefsModel.getInstance().getTranslator().get(ButtonKeys.BUTTON_OK);
			options[1] = PrefsModel.getInstance().getTranslator().get(ButtonKeys.BUTTON_CANCEL);

			if (JOptionPane.showOptionDialog(
					null, 
					PrefsModel.getInstance().getTranslator().get(MessageKeys.MESSAGE_UPGRADE_NOTICE),
					PrefsModel.getInstance().getTranslator().get(MessageKeys.MESSAGE_UPGRADE_NOTICE_TITLE),
					JOptionPane.OK_CANCEL_OPTION,
					JOptionPane.WARNING_MESSAGE,
					null,
					options,
					options[0]
			) == 1)  //The index of the Cancel button.
				System.exit(0);


			//Make a backup of the last data file, just to be safe...
			if (PrefsModel.getInstance().getLastDataFile() != null){
				File dataFile = PrefsModel.getInstance().getLastDataFile();
				File backupDataFile = new File(PrefsModel.getInstance().getLastDataFile().getAbsolutePath().replaceAll(Const.DATA_FILE_EXTENSION + "$", "") + "_" + Const.VERSION + "_" + Const.BACKUP_FILE_EXTENSION);
				try {
					FileFunctions.copyFile(dataFile, backupDataFile);
				}
				catch (IOException ioe){
					Log.warning("Error backing up file: " + ioe);
				}
			}
		}
		
		//Open the data file
		try {
			DataModel model;
			if (PrefsModel.getInstance().getLastDataFile() == null){
				Log.debug("Creating new data model");
				model = new DataModel();
			}
			else if (!PrefsModel.getInstance().getLastDataFile().exists()){
				//TODO Prompt for open other data file, or create new one.
				Log.debug("Cannot find old data file.  Creating new data model");
				model = new DataModel();				
			}
			else {
				Log.debug("Loading data model " + PrefsModel.getInstance().getLastDataFile());
				model = new DataModel(PrefsModel.getInstance().getLastDataFile());
			}

			AccountFrame accountFrame = new AccountFrame(model);

			accountFrame.openWindow(PrefsModel.getInstance().getAccountWindowSize(), PrefsModel.getInstance().getAccountWindowLocation());

			//Start the background startup tasks... 
			startVersionCheck(accountFrame);
			startUpdateCheck(accountFrame, false);

		}
		catch (WindowOpenException foe){
			//TODO Do something nicer here.  We throw a runtime exception during 
			// development to be sure that we catch problems with this.
			throw new RuntimeException(foe);
		}
		catch (DocumentLoadException lme){
			//TODO Do something nicer here.  We throw a runtime exception during 
			// development to be sure that we catch problems with this.
			throw new RuntimeException(lme);
		}

	}

	/**
	 * Main method for Buddi.  Can pass certain command line options
	 * in.  Use --help flag to see complete list.
	 * @param args
	 */
	public static void main(String[] args) {
		//Catch runtime exceptions
		Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler(){
			public void uncaughtException(Thread arg0, Throwable arg1) {
				arg1.printStackTrace(Log.getPrintStream());
				
				if (arg1 instanceof DataModelProblemException)
					sendBugReport(((DataModelProblemException) arg1).getDataModel());
				else
					sendBugReport();
			}
		});


		//First thing to do is to catch any open requests from Apple Launchd.
		if (OperatingSystemUtil.isMac()){
			Application application = Application.getApplication();
			application.addAboutMenuItem();
			application.addPreferencesMenuItem();
			application.setEnabledAboutMenu(true);
			application.setEnabledPreferencesMenu(true);
			application.addApplicationListener(new ApplicationAdapter(){
				@Override
				public void handleAbout(ApplicationEvent arg0) {
					new HelpAbout(null).doClick();
					arg0.setHandled(true);
				}

				@Override
				public void handleOpenFile(ApplicationEvent arg0) {
					// TODO Auto-generated method stub
					super.handleOpenFile(arg0);
				}

				@Override
				public void handlePreferences(ApplicationEvent arg0) {
					new EditPreferences(null).doClick();
					arg0.setHandled(true);
				}

				@Override
				public void handleQuit(ApplicationEvent arg0) {
					arg0.setHandled(false);  //You must set this to false if you want to be able to cancel it.
					new FileQuit(null).doClick();
				}
			});
		}

		String help = "USAGE: java -jar Buddi.jar <options> <data file>, where options include:\n" 
			+ "-p\tFilename\tPath and name of Preference File\n"
			+ "-v\t0-7\tVerbosity Level (7 = Debug)\n"
			+ "--lnf\tclassName\tJava Look and Feel to use\n"
			+ "--font\tfontName\tFont to use by default\n"
			+ "--simpleFont\t\tDon't use bold or italic fonts (better Unicode support)\n"
			+ "--log\tlogFile\tLocation to store logs, or 'stdout' / 'stderr' (default varies by platform)\n";
		// Undocumented flag --debian will specify a .deb download for new versions.
		// Undocumented flag --redhat will specify a .rpm download for new versions.
		// Undocumented flag --slackware will specify a .tgz download for new versions.

		List<ParseVariable> variables = new LinkedList<ParseVariable>();
		variables.add(new ParseVariable("-p", String.class, false));
		variables.add(new ParseVariable("-v", Integer.class, false));
		variables.add(new ParseVariable("--lnf", String.class, false));
		variables.add(new ParseVariable("--font", String.class, false));
		variables.add(new ParseVariable("--simpleFont", Boolean.class, false));
		variables.add(new ParseVariable("--log", String.class, false));
		variables.add(new ParseVariable("--debian", Boolean.class, false));
		variables.add(new ParseVariable("--redhat", Boolean.class, false));
		variables.add(new ParseVariable("--slackware", Boolean.class, false));

		ParseResults results = ParseCommands.parse(args, help, variables);

		//Set the working path.  If we save files (plugins, data files) 
		// within this path, we remove this parent path.  This allows
		// us to use relative paths for such things as running from
		// USB drives, from remote shares (which would not always have
		// the same path), etc.
		try {
			if (System.getProperty("user.dir").length() > 0)
				workingDir = new File(System.getProperty("user.dir")).getCanonicalPath() + File.separator;

			//If we did not set it via user.dir, we set it here.
			if (workingDir.length() == 0)
				workingDir = new File("").getCanonicalPath() + File.separator; // + (!OperatingSystemUtil.isWindows() ? File.separator : "");
		}
		catch (IOException ioe){
			//Fallback which does not throw IOException, but may get drive case incorrect on Windows.
			workingDir = new File("").getAbsolutePath() + File.separator; // + (!OperatingSystemUtil.isWindows() ? File.separator : "");
		}


		//Set up the logging system.  If we have specified --log, we first
		// try using that file.  If that is not specified, on the Mac
		// we just use stderr (since Console.app provides an easy way
		// to view that).  On all else, we default to buddi.log, in
		// the working directory.
		try {
			PrintStream logStream = System.err; //Default to stderr
			if (results.getString("--log") != null){
				if (results.getString("--log").equals("stdout"))
					logStream = System.out;
				else if (results.getString("--log").equals("stderr"))
					logStream = System.err;
				else {
					logFile = new File(results.getString("--log"));
				}
			}
			else if (!OperatingSystemUtil.isMac())
				logFile = new File(getWorkingDir() + Const.LOG_FILE);
			else if (OperatingSystemUtil.isMac() && System.getProperty("user.home").length() > 0)
				logFile = new File(System.getProperty("user.home") + "/Library/Logs/" + Const.LOG_FILE);

			if (logFile != null)
				logStream = new PrintStream(logFile);

			//Set the log stream
			Log.setPrintStream(logStream);
		}
		catch (FileNotFoundException fnfe){
			Log.setPrintStream(System.err);
			Log.error(fnfe);
		}

		//Set up the log levels
		Integer verbosity = results.getInteger("-v");
		if (verbosity != null){
			Log.setLogLevel(verbosity);
			Log.debug("Setting log level to " + verbosity);
		}
		else if (Const.DEVEL){
			Log.setLogLevel(Log.DEBUG);
		}



		String prefsLocation = results.getString("-p");
		String lnf = results.getString("--lnf");
		String font = results.getString("--font");
		simpleFont = results.getBoolean("--simpleFont");
		debian = results.getBoolean("--debian");
		redhat = results.getBoolean("--redhat");
		slackware = results.getBoolean("--slackware");

		filesToLoad = new LinkedList<File>();
		for (String s : results.getCommands()) {
			filesToLoad.add(new File(s));
		}

		Log.debug("File to Load == " + filesToLoad);


		//Override the location of the Prefs file, if the -p flag is set.
		// This MUST be called before the first PrefsMode.getInstance() 
		// is called.
		if (prefsLocation != null){
			PrefsModel.setPrefsFile(new File(prefsLocation));
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

		//The version of Buddi.  Useful for diagnostics
		Log.notice("Running Buddi version " + Const.VERSION);

		//Let the user know where the working directory is, after
		// we have set up logging properly.
		Log.notice("Set working directory to " + workingDir);

		//Set some Mac-specific GUI options
//		if (OperatingSystemUtil.isMac()){
//			System.setProperty("Quaqua.tabLayoutPolicy", "scroll");
//			System.setProperty("Quaqua.selectionStyle", "bright");
//			System.setProperty("apple.laf.useScreenMenuBar", "true");
//			System.setProperty("apple.awt.graphics.EnableQ2DX", "true");
//			System.setProperty("apple.awt.rendering", "VALUE_RENDER_SPEED"); // VALUE_RENDER_SPEED or VALUE_RENDER_QUALITY
//			System.setProperty("apple.awt.interpolation", "VALUE_INTERPOLATION_NEAREST_NEIGHBOR"); // VALUE_INTERPOLATION_NEAREST_NEIGHBOR, VALUE_INTERPOLATION_BILINEAR, or VALUE_INTERPOLATION_BICUBIC
//			System.setProperty("apple.awt.showGrowBox", "false");
//			System.setProperty("com.apple.mrj.application.growbox.intrudes","true");
//			System.setProperty("com.apple.mrj.application.apple.menu.about.name", PrefsModel.getInstance().getTranslator().get(BuddiKeys.BUDDI));
//		}

		//Load the correct Look and Feel
		LookAndFeelUtil.setLookAndFeel(lnf);

		//Start the GUI in the proper thread
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				launchGUI();
			}
		});
	}

	/**
	 * Display any information you need to see on first version, such as 
	 * donation requests, etc. 
	 */
	private static void startVersionCheck(final MossFrame frame){
		if (PrefsModel.getInstance().getLastVersion() == null 
				|| !PrefsModel.getInstance().getLastVersion().equals(Const.VERSION)){
			PrefsModel.getInstance().updateVersion();

			String[] buttons = new String[2];
			buttons[0] = PrefsModel.getInstance().getTranslator().get(ButtonKeys.BUTTON_DONATE);
			buttons[1] = PrefsModel.getInstance().getTranslator().get(ButtonKeys.BUTTON_NOT_NOW);

			int reply = JOptionPane.showOptionDialog(
					frame, 
					PrefsModel.getInstance().getTranslator().get(MessageKeys.MESSAGE_ASK_FOR_DONATION),
					PrefsModel.getInstance().getTranslator().get(MessageKeys.MESSAGE_ASK_FOR_DONATION_TITLE),
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
		}
	}

	/**
	 * Starts a thread which checks the Internet for any new versions.
	 */
	public static void startUpdateCheck(final MossFrame frame, final boolean confirm){
		if (confirm || PrefsModel.getInstance().isShowUpdateNotifications()){
			SwingWorker updateWorker = new SwingWorker(){
				@Override
				public Object construct() {
					try{
						Properties versions = new Properties();
						URL mostRecentVersion = new URL(Const.PROJECT_URL + Const.VERSION_FILE);

						versions.load(mostRecentVersion.openStream());

						Version availableVersion = new Version(versions.get(Const.BRANCH).toString());
						Version thisVersion = Const.VERSION;

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

						String[] options = new String[1];
						options[0] = PrefsModel.getInstance().getTranslator().get(ButtonKeys.BUTTON_OK);

						JOptionPane.showOptionDialog(
								frame, 
								PrefsModel.getInstance().getTranslator().get(MessageKeys.MESSAGE_ERROR_CHECKING_FOR_UPDATES), 
								PrefsModel.getInstance().getTranslator().get(BuddiKeys.ERROR), 
								JOptionPane.DEFAULT_OPTION,
								JOptionPane.ERROR_MESSAGE,
								null,
								options,
								options[0]);
					}

					return null;
				}

				@Override
				public void finished() {
					if (get() != null){
						String[] buttons = new String[2];
						buttons[0] = PrefsModel.getInstance().getTranslator().get(ButtonKeys.BUTTON_DOWNLOAD);
						buttons[1] = PrefsModel.getInstance().getTranslator().get(ButtonKeys.BUTTON_CANCEL);

						int reply = JOptionPane.showOptionDialog(
								frame, 
								PrefsModel.getInstance().getTranslator().get(BuddiKeys.NEW_VERSION_MESSAGE)
								+ " " + get() + "\n"
								+ PrefsModel.getInstance().getTranslator().get(BuddiKeys.NEW_VERSION_MESSAGE_2),
								PrefsModel.getInstance().getTranslator().get(BuddiKeys.NEW_VERSION),
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
						options[0] = PrefsModel.getInstance().getTranslator().get(ButtonKeys.BUTTON_OK);

						JOptionPane.showOptionDialog(
								frame, 
								PrefsModel.getInstance().getTranslator().get(MessageKeys.MESSAGE_NO_NEW_VERSION), 
								PrefsModel.getInstance().getTranslator().get(MessageKeys.MESSAGE_NO_NEW_VERSION_TITLE), 
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

	public static void sendBugReport(DataModel... models){
		StringBuilder crashLog = new StringBuilder();
		crashLog.append("\n---Starting Preferences---\n");
		crashLog.append(PrefsModel.getInstance().saveToString());
		crashLog.append("---Finished Preferences---\n\n");

		if (models != null){
			for (DataModel m : models) {
				crashLog.append("---Starting Data File---\n");
				crashLog.append(m.saveToString());
				crashLog.append("---Finished Data File---\n\n");
			}
		}

		if (logFile != null){
			try{
				BufferedReader logReader = new BufferedReader(new FileReader(logFile));
				crashLog.append("---Starting Log File (" + logFile.getAbsolutePath() + ")---");
				String temp;
				while ((temp = logReader.readLine()) != null)
					crashLog.append(temp);
				crashLog.append("---Finished Log File---");
			}
			catch (IOException ioe){}
		}


		if (PrefsModel.getInstance().isSendCrashReports()){
			try {
				BrowserLauncher bl = new BrowserLauncher();
				bl.openURLinBrowser("mailto:wyatt.olson@gmail.com?subject=" 
						+ URLEncoder.encode("Buddi Crash Report", "UTF-8").replaceAll("\\+", "%20") 
						+ "&body="
						+ URLEncoder.encode(
								"There has been a serious problem encountered in Buddi, and Buddi has closed.  It is recommended that you send a crash report to the program author (Wyatt Olson <wyatt.olson@gmail.com>) to help him troubleshoot the problem.  The following components may be included in the crash report:\n\t-Contents of your Preferences file,\n\t-Contents of all open Data files\n\t-Contents of the Buddi log file\n\n.These components included as text in the body below, clearly marked between tags such as '---Starting Preferences---' and '---Finished Preferences---'.  If for privacy or other reasons you wish to remove one or more of these components, feel free to do so.\n\nIf you use a different mail program (such as a Web based email program), plese copy the entire contents of this email to that program, and send it to Wyatt.\n\nIf you do not wish to send this crash report at all, simply close this window.\n\n\n"
								+ crashLog.toString()

								, 
						"UTF-8").replaceAll("\\+", "%20"));
			}
			catch (Exception e){
				Log.critical("Unable to send crash email.");
			}
		}

		Log.critical(crashLog.toString());
	}
}
