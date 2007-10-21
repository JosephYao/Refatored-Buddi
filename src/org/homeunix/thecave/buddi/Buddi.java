/*
 * Created on Jun 26, 2005 by wyatt
 */
package org.homeunix.thecave.buddi;

import java.beans.XMLDecoder;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.plaf.FontUIResource;

import net.java.dev.SwingWorker;

import org.homeunix.thecave.buddi.i18n.BuddiKeys;
import org.homeunix.thecave.buddi.i18n.keys.ButtonKeys;
import org.homeunix.thecave.buddi.model.Document;
import org.homeunix.thecave.buddi.model.impl.DocumentImpl;
import org.homeunix.thecave.buddi.model.impl.ModelFactory;
import org.homeunix.thecave.buddi.model.prefs.PrefsModel;
import org.homeunix.thecave.buddi.plugin.BuddiPluginFactory;
import org.homeunix.thecave.buddi.plugin.api.BuddiRunnablePlugin;
import org.homeunix.thecave.buddi.plugin.api.exception.DataModelProblemException;
import org.homeunix.thecave.buddi.plugin.api.exception.ModelException;
import org.homeunix.thecave.buddi.plugin.api.util.TextFormatter;
import org.homeunix.thecave.buddi.util.BuddiCryptoFactory;
import org.homeunix.thecave.buddi.view.MainFrame;
import org.homeunix.thecave.buddi.view.dialogs.BuddiPasswordDialog;
import org.homeunix.thecave.buddi.view.menu.bars.FramelessMenuBar;
import org.homeunix.thecave.buddi.view.menu.items.EditPreferences;
import org.homeunix.thecave.buddi.view.menu.items.FileOpen;
import org.homeunix.thecave.buddi.view.menu.items.FileQuit;
import org.homeunix.thecave.buddi.view.menu.items.HelpAbout;
import org.homeunix.thecave.moss.exception.DocumentLoadException;
import org.homeunix.thecave.moss.exception.DocumentSaveException;
import org.homeunix.thecave.moss.exception.OperationCancelledException;
import org.homeunix.thecave.moss.exception.WindowOpenException;
import org.homeunix.thecave.moss.swing.ApplicationModel;
import org.homeunix.thecave.moss.swing.MossFrame;
import org.homeunix.thecave.moss.swing.MossSplashScreen;
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
import org.homeunix.thecave.moss.util.crypto.IncorrectDocumentFormatException;
import org.homeunix.thecave.moss.util.crypto.IncorrectPasswordException;

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

//	private static String userDir;
	private static String pluginsFolder;
	private static String languagesFolder;
	private static List<File> filesToLoad;
	private static Boolean debian = false;
	private static Boolean windowsInstaller = false;
	private static Boolean genericUnix = false;
	private static Boolean slackware = false;
	private static Boolean redhat = false;
	private static Boolean simpleFont = false;
	private static File logFile = null;
	private static Version version = null;

	public static Version getVersion() {
		if (version == null)
			version = Version.getVersionResource("version.txt");
		return version;
	}

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
	 * @return True if running on generic Unix, false otherwise.  This is 
	 * obtained through the startup flag --unix, so it can be 
	 * faked if desired.  This is used to determine which download
	 * to use on Linux - if the flag is set, we will download
	 * a .deb when a new version is released, otherwise we will
	 * download a .jar.
	 */
	public static boolean isUnix(){
		if (genericUnix == null)
			genericUnix = false;
		return genericUnix;
	}
	
	/** 
	 * @return True if running on Slackware, false otherwise.  This is 
	 * obtained through the startup flag --slackware, so it can be 
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
	 * @return True if running from a Windows Installer installed .exe, false
	 * otherwise.  This is obtained through the startup flag --windows-installer, so it can be 
	 * faked if desired.  This is used to determine which download
	 * to use on Windows - if the flag is set, we will download
	 * the installer version when a new version is released, otherwise we will
	 * download the standalone .exe.
	 */
	public static boolean isWindowsInstaller(){
		if (windowsInstaller == null)
			windowsInstaller = false;
		return windowsInstaller;
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

	public static File getPluginsFolder(){
		if (pluginsFolder == null)
			pluginsFolder = OperatingSystemUtil.getUserFolder("Buddi") + File.separator + Const.PLUGIN_FOLDER;
		return new File(pluginsFolder);
	}

	public static File getLanguagesFolder(){
		if (languagesFolder == null)
			languagesFolder = OperatingSystemUtil.getUserFile("Buddi", Const.LANGUAGE_FOLDER).getAbsolutePath();
		Log.debug(languagesFolder);
		return new File(languagesFolder);
	}

	/**
	 * Method to start the GUI.  Should be run from the AWT Dispatch thread.
	 */
	private static void launchGUI(){
		//If we are on a Mac, open a new Frameless menu bar.
		if (OperatingSystemUtil.isMac()){
			Application.getApplication().setFramessMenuBar(new FramelessMenuBar());
		}

		//Handle opening files from command line.
		if (filesToLoad.size() > 0){
			for (File f : filesToLoad) {
				openFile(f);
			}
		}

		//If we have specified that we want to prompt for a data file at startup,
		// do so (assuming that we have not already opened one from the command
		// line, or Mac Application listeners.
		if (PrefsModel.getInstance().isShowPromptAtStartup()
				&& ApplicationModel.getInstance().getOpenFrames().size() == 0){
			new FileOpen(null).doClick();
		}
		
		//Handle opening the last user file, if available.
		if (PrefsModel.getInstance().getLastDataFiles() != null
				&& ApplicationModel.getInstance().getOpenFrames().size() == 0) {
			for (File f : PrefsModel.getInstance().getLastDataFiles()) {
				openFile(f);				
			}
		}

		//If no files are available, just create a new one.
		if (ApplicationModel.getInstance().getOpenFrames().size() == 0) {
			try {
				Document model = ModelFactory.createDocument();
				MainFrame mainWndow = new MainFrame(model);
				try{
					mainWndow.openWindow(
							PrefsModel.getInstance().getWindowSize(model.getFile() + ""), 
							PrefsModel.getInstance().getWindowLocation(model.getFile() + ""));
				}
				catch (WindowOpenException woe){
					Log.error("Error opening window: ", woe);
				}
			}
			catch (ModelException me){
				me.printStackTrace(Log.getPrintStream());
			}
		}

		//Check if we have opened at least one window...
		MainFrame mainFrame = null;
		for (MossFrame mossFrame : ApplicationModel.getInstance().getOpenFrames()) {
			if (mossFrame instanceof MainFrame){
				mainFrame = (MainFrame) mossFrame;
				break;
			}
		}
		if (mainFrame == null)
			Log.emergency("No Buddi main windows were able to open!");

		//Start the background startup tasks... 
		startVersionCheck(mainFrame);
		startUpdateCheck(mainFrame, false);


		//Start the auto save timer
		Timer t = new Timer();
		t.schedule(new TimerTask(){
			@Override
			public void run() {
				SwingUtilities.invokeLater(new Runnable(){
					public void run() {	
						for (MossFrame frame : ApplicationModel.getInstance().getOpenFrames()) {
							if (frame instanceof MainFrame){
								MainFrame mainFrame = (MainFrame) frame;
								if (mainFrame.getDocument().isChanged()){
									File autoSaveLocation = ModelFactory.getAutoSaveLocation(mainFrame.getDocument().getFile());

									try {
										((DocumentImpl) mainFrame.getDocument()).saveAuto(autoSaveLocation);
										Log.debug("Auto saved file to " + autoSaveLocation);
									}
									catch (DocumentSaveException dse){
										Log.emergency("Error saving autosave file:");
										dse.printStackTrace(Log.getPrintStream());
									}
								}
								else {
									Log.debug("Did not autosave, as there are no changes to the data file " + mainFrame.getDocument().getFile());
								}		
							}
						}
					};
				});
			}
		}, 
		10 * 1000, //Save the first one after 10 seconds 
		PrefsModel.getInstance().getAutosaveDelay() * 1000); //Use preferences to decide period
		
		//If it has not already been done, disable the splash screen now.
		MossSplashScreen.hideSplash();
	}


	/**
	 * Opens the given file.  Must be run in the event dispatch thread. 
	 * @param f
	 */
	private static void openFile(File f){
		//Handle opening files from command line.
		if (f.getName().endsWith(Const.DATA_FILE_EXTENSION)){
			try {
				MossSplashScreen.hideSplash();
				
				Document model;
				model = ModelFactory.createDocument(f);

				MainFrame mainWndow = new MainFrame(model);
				try {
					mainWndow.openWindow(
							PrefsModel.getInstance().getWindowSize(model.getFile().getAbsolutePath()), 
							PrefsModel.getInstance().getWindowLocation(model.getFile().getAbsolutePath()));
				}
				catch (WindowOpenException woe){
					Log.error("Error opening window: ", woe);
				}
			}
			catch (DocumentLoadException lme){
				lme.printStackTrace(Log.getPrintStream());
			}
			catch (OperationCancelledException oce){}  //Do nothing
		}
		else if (f.getName().endsWith(Const.PLUGIN_EXTENSION)){
			Log.info("Trying to copy " + f.getAbsolutePath() + " to " + Buddi.getPluginsFolder() + File.separator + f.getName());
			if (!Buddi.getPluginsFolder().exists()){
				if (!Buddi.getPluginsFolder().mkdirs()){
					Log.error("Error creating Plugins directory!");
				}
			}
			try {
				File dest = new File(Buddi.getPluginsFolder().getAbsolutePath() + File.separator + f.getName());
				if (f.getAbsolutePath().equals(dest.getAbsolutePath()))
					throw new IOException("Cannot copy to the same file.");
				FileFunctions.copyFile(f, dest);

				String[] options = new String[1];
				options[0] = TextFormatter.getTranslation(ButtonKeys.BUTTON_OK);

				JOptionPane.showOptionDialog(
						null, 
						TextFormatter.getTranslation(BuddiKeys.MESSAGE_PLUGIN_ADDED_RESTART_NEEDED),
						TextFormatter.getTranslation(BuddiKeys.MESSAGE_PLUGIN_ADDED_RESTART_NEEDED_TITLE),
						JOptionPane.OK_CANCEL_OPTION,
						JOptionPane.INFORMATION_MESSAGE,
						null,
						options,
						options[0]
				);
			}
			catch (IOException ioe){
				Log.error("Error copying plugin to Plugins directory", ioe);
			}
		}
		else if (f.getName().endsWith(Const.LANGUAGE_EXTENSION)){
			Log.info("Trying to copy " + f.getAbsolutePath() + " to " + Buddi.getLanguagesFolder() + File.separator + f.getName());
			if (!Buddi.getLanguagesFolder().exists()){
				if (!Buddi.getLanguagesFolder().mkdirs()){
					Log.error("Error creating Languages directory!");
				}
			}
			try {
				File dest = new File(Buddi.getLanguagesFolder().getAbsolutePath() + File.separator + f.getName());
				if (f.getAbsolutePath().equals(dest.getAbsolutePath()))
					throw new IOException("Cannot copy to the same file.");
				FileFunctions.copyFile(f, dest);

				String[] options = new String[1];
				options[0] = TextFormatter.getTranslation(ButtonKeys.BUTTON_OK);

				JOptionPane.showOptionDialog(
						null, 
						TextFormatter.getTranslation(BuddiKeys.MESSAGE_LANGUAGE_ADDED_RESTART_NEEDED),
						TextFormatter.getTranslation(BuddiKeys.MESSAGE_LANGUAGE_ADDED_RESTART_NEEDED_TITLE),
						JOptionPane.OK_CANCEL_OPTION,
						JOptionPane.INFORMATION_MESSAGE,
						null,
						options,
						options[0]
				);

				PrefsModel.getInstance().setLanguage(f.getName().replaceAll(Const.LANGUAGE_EXTENSION, ""));
				PrefsModel.getInstance().save();
			}
			catch (IOException ioe){
				Log.error("Error copying translation to Languages directory", ioe);
			}
		}
	}


	/**
	 * Main method for Buddi.  Can pass certain command line options
	 * in.  Use --help flag to see complete list.
	 * @param args
	 */
	public static void main(String[] args) {
		//Set Buddi-specific LnF options.
		//This one removes the width limitation for dialogs.  Since we already will
		// wrap for other OS's, there is no need to have Quaqua do this for you.
		UIManager.put("OptionPane.maxCharactersPerLineCount", Integer.MAX_VALUE);
		//Set the max row count.  Quaqua overrides the value set in MossScrollingComboBox, 
		// so we must set it here manually. 
		UIManager.put("ComboBox.maximumRowCount", Integer.valueOf(10));
		
		System.setProperty("com.apple.mrj.application.apple.menu.about.name", Const.PROJECT_NAME);

		//Load the correct Look and Feel.  Includes OS specific options, such as Quaqua constants.
		LookAndFeelUtil.setLookAndFeel();
		
		splash: if (!OperatingSystemUtil.isMac()){
			for (String string : args) {
				if (string.equals("--nosplash"))
					break splash;
			}
			MossSplashScreen.showSplash("img/BuddiSplashScreen.jpg");
		}
		
		//Catch runtime exceptions
		Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler(){
			public void uncaughtException(Thread arg0, Throwable arg1) {
				arg1.printStackTrace(Log.getPrintStream());
				if (!Log.getPrintStream().equals(System.err)
						&& !Log.getPrintStream().equals(System.out)){
					if (arg1 instanceof DataModelProblemException)
						sendBugReport(((DataModelProblemException) arg1).getDataModel());
					else
						sendBugReport();
				}
			}
		});

		//Early on, we need to catch any open requests from Apple Launchd.
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
				public void handleOpenFile(final ApplicationEvent arg0) {
					arg0.setHandled(true);
					SwingUtilities.invokeLater(new Runnable(){
						public void run() {
							openFile(new File(arg0.getFilename()));
						}
					});
				}

				@Override
				public void handleReOpenApplication(ApplicationEvent arg0) {
					arg0.setHandled(true);

					if (ApplicationModel.getInstance().getOpenFrames().size() == 0){
						try {
							Document model = ModelFactory.createDocument();
							MainFrame mainWndow = new MainFrame(model);
							mainWndow.openWindow(
									PrefsModel.getInstance().getWindowSize(model.getFile().getAbsolutePath()), 
									PrefsModel.getInstance().getWindowLocation(model.getFile().getAbsolutePath()));
						}
						catch (ModelException me){
							me.printStackTrace(Log.getPrintStream());
						}
						catch (WindowOpenException woe){
							woe.printStackTrace(Log.getPrintStream());
						}
					}
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
			+ "--usb\t\tRun on a USB key: put preferences, languages, and plugins in working dir.\n"
			+ "--prefs\tFilename\tPath and name of Preference File (Default varies by platform)\n"
			+ "--verbosity\t0-7\tVerbosity Level (0 = Emergency, 7 = Debug)\n"
			+ "--languages\tFolder\tFolder to store custom languages (should be writable)\n"
			+ "--plugins\tFolder\tFolder to store plugins (should be writable)\n"
			+ "--nosplash\t\tDon't show splash screen on startup\n"
			+ "--simpleFont\t\tDon't use bold or italic fonts (better support for special characters on Windows)\n"
			+ "--log\tlogFile\tLocation to store logs, or 'stdout' / 'stderr' (default varies by platform)\n";
		// Undocumented flag --extract <filename> will extract the given file to stdout, and exit.  Useful for debugging.
		// Undocumented flag --font	<fontName> will specify a font to use by default
		// Undocumented flag --windows-installer will specify a -Installer.exe download for new versions.
		// Undocumented flag --debian will specify a .deb download for new versions.
		// Undocumented flag --redhat will specify a .rpm download for new versions.
		// Undocumented flag --slackware will specify a -Slackware.tgz download for new versions.
		// Undocumented flag --unix will specify a .tgz download for new versions.

		List<ParseVariable> variables = new LinkedList<ParseVariable>();
		variables.add(new ParseVariable("--usb", Boolean.class, false));
		variables.add(new ParseVariable("--prefs", String.class, false));
		variables.add(new ParseVariable("--verbosity", Integer.class, false));
		variables.add(new ParseVariable("--plugins", String.class, false));
		variables.add(new ParseVariable("--languages", String.class, false));
		variables.add(new ParseVariable("--font", String.class, false));
		variables.add(new ParseVariable("--simpleFont", Boolean.class, false));
		variables.add(new ParseVariable("--log", String.class, false));
		variables.add(new ParseVariable("--nosplash", Boolean.class, false));
		variables.add(new ParseVariable("--extract", String.class, false));
		
		variables.add(new ParseVariable("--debian", Boolean.class, false));
		variables.add(new ParseVariable("--redhat", Boolean.class, false));
		variables.add(new ParseVariable("--slackware", Boolean.class, false));
		variables.add(new ParseVariable("--unix", Boolean.class, false));
		variables.add(new ParseVariable("--windows-installer", Boolean.class, false));

		ParseResults results = ParseCommands.parse(args, help, variables);

		//Extract file to stdout, and exit.
		if (results.getString("--extract") != null){
			extractFile(new File(results.getString("--extract")));
			System.exit(0);
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
			else
				logFile = OperatingSystemUtil.getLogFile("Buddi", Const.LOG_FILE);

			if (logFile != null){
				if (!logFile.getParentFile().exists())
					logFile.getParentFile().mkdirs();
				if (logFile.getParentFile().exists())
					logStream = new PrintStream(logFile);
			}

			//Set the log stream
			Log.setPrintStream(logStream);
		}
		catch (FileNotFoundException fnfe){
			Log.setPrintStream(System.err);
			Log.error(fnfe);
		}

		//Set up the log levels
		Integer verbosity = results.getInteger("--verbosity");
		if (verbosity != null){
			Log.setLogLevel(verbosity);
			Log.debug("Setting log level to " + verbosity);
		}
		else {
			if (Const.DEVEL)
				Log.setLogLevel(Log.DEBUG);
			else
				Log.setLogLevel(Log.INFO);
		}

		//Prints the version of Buddi to the logs
		Log.info("Buddi version: " + getVersion());
		Log.info("Buddi command line arguments: " + Arrays.toString(args));
		Log.info("Operating System: " + System.getProperty("os.name") + ", " + System.getProperty("os.arch"));
		Log.info("Java VM version: " + System.getProperty("java.version"));

		//Parse all the remaining options
		Boolean usbKey = results.getBoolean("--usb");
		String prefsLocation = results.getString("--prefs");
		String font = results.getString("--font");
		languagesFolder = results.getString("--languages");
		pluginsFolder = results.getString("--plugins");
		simpleFont = results.getBoolean("--simpleFont");
		
		windowsInstaller = results.getBoolean("--windows-installer");
		slackware = results.getBoolean("--slackware");
		debian = results.getBoolean("--debian");
		redhat = results.getBoolean("--redhat");
		genericUnix = results.getBoolean("--unix");

		filesToLoad = new LinkedList<File>();
		for (String s : results.getCommands()) {
			filesToLoad.add(new File(s));
		}

		Log.debug("Files to load: " + filesToLoad);

		String currentWorkingDir = System.getProperty("user.dir") + File.separator;
		
		//Set some directories if USB mode is enabled.
		if (usbKey != null){
			if (languagesFolder == null)
				languagesFolder = currentWorkingDir + Const.LANGUAGE_FOLDER;
			if (pluginsFolder == null)
				pluginsFolder = currentWorkingDir + Const.PLUGIN_FOLDER;
			if (prefsLocation == null)
				prefsLocation = currentWorkingDir + Const.PREFERENCE_FILE_NAME;
		}

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

		//Let the user know where the working directory is, after
		// we have set up logging properly.
		Log.info("Working directory: " + currentWorkingDir);

		//Run any RunnablePlugins which we may have here.
		for (BuddiRunnablePlugin plugin : BuddiPluginFactory.getRunnablePlugins()) {
			plugin.run();
		}
		
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
				|| !PrefsModel.getInstance().getLastVersion().equals(getVersion())){
			PrefsModel.getInstance().updateVersion();

			String[] buttons = new String[2];
			buttons[0] = TextFormatter.getTranslation(ButtonKeys.BUTTON_DONATE);
			buttons[1] = TextFormatter.getTranslation(ButtonKeys.BUTTON_NOT_NOW);

			int reply = JOptionPane.showOptionDialog(
					frame, 
					TextFormatter.getTranslation(BuddiKeys.MESSAGE_ASK_FOR_DONATION),
					TextFormatter.getTranslation(BuddiKeys.MESSAGE_ASK_FOR_DONATION_TITLE),
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


						Proxy p;
						if (PrefsModel.getInstance().isShowProxySettings()){
							InetAddress proxyAddress = InetAddress.getByName(PrefsModel.getInstance().getProxyServer());
							InetSocketAddress socketAddress = new InetSocketAddress(proxyAddress, PrefsModel.getInstance().getPort()); 
							p = new Proxy(Proxy.Type.DIRECT, socketAddress);
						}
						else {
							p = Proxy.NO_PROXY;
						}

						URL mostRecentVersion = new URL(Const.PROJECT_URL + Const.VERSION_FILE);
						URLConnection connection = mostRecentVersion.openConnection(p);

						InputStream is = connection.getInputStream();


						Properties versions = new Properties();

						versions.load(is);

						Version availableVersion = new Version(versions.get(Const.BRANCH).toString());
						Version thisVersion = getVersion();

						Log.debug("This version: " + thisVersion);
						Log.debug("Available version: " + availableVersion);

						if (thisVersion.compareTo(availableVersion) < 0)
							return availableVersion;
					}
					catch (IOException ioe){
						Log.error(ioe);

						String[] options = new String[1];
						options[0] = TextFormatter.getTranslation(ButtonKeys.BUTTON_OK);

						JOptionPane.showOptionDialog(
								frame, 
								TextFormatter.getTranslation(BuddiKeys.MESSAGE_ERROR_CHECKING_FOR_UPDATES), 
								TextFormatter.getTranslation(BuddiKeys.ERROR), 
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
						buttons[0] = TextFormatter.getTranslation(ButtonKeys.BUTTON_DOWNLOAD);
						buttons[1] = TextFormatter.getTranslation(ButtonKeys.BUTTON_CANCEL);

						int reply = JOptionPane.showOptionDialog(
								frame, 
								TextFormatter.getTranslation(BuddiKeys.NEW_VERSION_MESSAGE)
								+ " " + get() + "\n"
								+ TextFormatter.getTranslation(BuddiKeys.NEW_VERSION_MESSAGE_2),
								TextFormatter.getTranslation(BuddiKeys.NEW_VERSION),
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
							else if (OperatingSystemUtil.isWindows()){
								if (isWindowsInstaller())
									fileLocation += Const.DOWNLOAD_TYPE_WINDOWS_INSTALLER;
								else
									fileLocation += Const.DOWNLOAD_TYPE_WINDOWS;
							}
							else {
								//Check for any specific distributions here
								if (Buddi.isDebian())
									fileLocation += Const.DOWNLOAD_TYPE_DEBIAN;
								else if (Buddi.isSlackware())
									fileLocation += Const.DOWNLOAD_TYPE_SLACKWARE;
								else if (Buddi.isRedhat())
									fileLocation += Const.DOWNLOAD_TYPE_REDHAT;
								else if (Buddi.isUnix())
									fileLocation += Const.DOWNLOAD_TYPE_UNIX;
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
						options[0] = TextFormatter.getTranslation(ButtonKeys.BUTTON_OK);

						JOptionPane.showOptionDialog(
								frame, 
								TextFormatter.getTranslation(BuddiKeys.MESSAGE_NO_NEW_VERSION), 
								TextFormatter.getTranslation(BuddiKeys.MESSAGE_NO_NEW_VERSION_TITLE), 
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

	public static void sendBugReport(Document... models){
		StringBuilder crashLog = new StringBuilder();
		crashLog.append("\n---Starting Preferences---\n");
		crashLog.append(PrefsModel.getInstance().saveToString());
		crashLog.append("---Finished Preferences---\n\n");

		if (models != null){
			for (Document m : models) {
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				try {
					m.saveToStream(baos);

					crashLog.append("---Starting Data File---\n");
					crashLog.append(baos.toString());
					crashLog.append("---Finished Data File---\n\n");
				}
				catch (DocumentSaveException dse){
					crashLog.append("---Starting Data File---\n");
					crashLog.append("Error: Could not save to stream:");
					crashLog.append(dse.getMessage());
					crashLog.append("---Finished Data File---\n\n");					
				}
			}
		}

		if (logFile != null){
			try{
				BufferedReader logReader = new BufferedReader(new FileReader(logFile));
				crashLog.append("---Starting Log File (" + logFile.getAbsolutePath() + ")---\n");
				String temp;
				while ((temp = logReader.readLine()) != null)
					crashLog.append(temp).append("\n");
				crashLog.append("---Finished Log File---\n");
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
								"There has been a serious problem encountered in Buddi, " +
								"and Buddi has closed.  It is recommended that you send a " +
								"crash report to the program author (Wyatt Olson <wyatt.olson@gmail.com>) " +
								"to help him troubleshoot the problem.  The following components may " +
								"be included in the crash report:\n\t-Contents of your Preferences " +
								"file,\n\t-Contents of all open Data files\n\t-Contents of the Buddi " +
								"log file\n\nThese components included as text in the body below, " +
								"clearly marked between tags such as '---Starting Preferences---' " +
								"and '---Finished Preferences---'.  If for privacy or other reasons you" +
								" wish to remove one or more of these components, feel free to do so." +
								"\n\nIf you use a different mail program (such as a Web based email " +
								"program), plese copy the entire contents of this email to that program, " +
								"and send it to Wyatt.\n\nIf you do not wish to send this crash report at " +
								"all, simply close this window.\n\n\n"
								+ crashLog.toString()

								, 
						"UTF-8").replaceAll("\\+", "%20"));
			}
			catch (Exception e){
				Log.emergency("Unable to send crash email.");
			}
		}

//		Log.critical(crashLog.toString());
	}
	
	private static void extractFile(File fileToLoad){
		try {
			InputStream is;
			BuddiCryptoFactory factory = new BuddiCryptoFactory();
			char[] password = null;

			//Loop until the user gets the password correct, hits cancel, 
			// or some other error occurs.
			while (true) {
				try {
					is = factory.getDecryptedStream(new FileInputStream(fileToLoad), password);

					//Attempt to decode the XML within the (now hopefully unencrypted) data file. 
					XMLDecoder decoder = new XMLDecoder(is);
					Object o = decoder.readObject();
					if (o instanceof DocumentImpl){
						DocumentImpl document = (DocumentImpl) o;
						FileOutputStream fos = new FileOutputStream(new File(fileToLoad.getAbsolutePath() + ".raw_output"));
						document.saveToStream(fos);
						fos.close();
					}
					else {
						throw new IncorrectDocumentFormatException("Could not find a DataModelBean object in the data file!");
					}
					is.close();
					System.exit(0);
				}
				catch (IncorrectPasswordException ipe){
					//The password was not correct.  Prompt for a new one.
					BuddiPasswordDialog passwordDialog = new BuddiPasswordDialog();
					password = passwordDialog.askForPassword(false, true);

					//User hit cancel.  Exit.
					if (password == null)
						System.exit(0);
				}
				catch (Exception e){
					e.printStackTrace();
					System.exit(1);
				}
			}
		}
		catch (Exception e){
			e.printStackTrace();
			System.exit(1);
		}
		
		System.exit(0);
	}
}
