/*
 * Created on Jun 26, 2005 by wyatt
 */
package org.homeunix.thecave.buddi;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
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
import org.homeunix.thecave.buddi.i18n.keys.MessageKeys;
import org.homeunix.thecave.buddi.model.Document;
import org.homeunix.thecave.buddi.model.impl.DocumentImpl;
import org.homeunix.thecave.buddi.model.impl.ModelFactory;
import org.homeunix.thecave.buddi.model.prefs.PrefsModel;
import org.homeunix.thecave.buddi.plugin.api.exception.DataModelProblemException;
import org.homeunix.thecave.buddi.plugin.api.exception.ModelException;
import org.homeunix.thecave.buddi.plugin.api.util.TextFormatter;
import org.homeunix.thecave.buddi.view.MainFrame;
import org.homeunix.thecave.buddi.view.menu.bars.FramelessMenuBar;
import org.homeunix.thecave.buddi.view.menu.items.EditPreferences;
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
	private static Boolean genericUnix = false;
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
	 * Gets the current working directory.  This should be the same directory
	 * that the Buddi.{exe|jar} is in or the Buddi.app/Contents/Resources/Java/ 
	 * folder on a Mac.  This will determine what a relative path is, which
	 * is useful for running on a thumb drive.
	 * @return
	 */
//	public static String getUserDir(){
//	if (userDir == null){
//	userDir = "";
//	}

//	return userDir;
//	}

	/** 
	 * @return True if running on Slackware, false otherwise.  This is 
	 * obtained through the startup flag --debian, so it can be 
	 * faked if desired.  This is used to determine which download
	 * to use on Linux - if the flag is set, we will download
	 * a .deb when a new version is released, otherwise we will
	 * download a .jar.
	 */
	public static boolean isSlackware(){
		if (genericUnix == null)
			genericUnix = false;
		return genericUnix;
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

//		if (JOptionPane.showConfirmDialog(
//		null, 
//		"WARNING:\n\nThis version of Buddi is currently under active development,\nand sould be considered Alpha quality code.  There are many\nfeatures which are not completely implemented, and many\nothers which contain bugs.\n\nFurthermore, the data model currently in place will likely\nchange before this is publicly released.\n\nYou should *NOT* use this version of software for any data\nthat matters - it is only a development preview of\nthe upcoming version.\n\nIf you are looking for stable budgeting software,\nplease use Buddi 2.6, available for free at http://buddi.sourceforge.net.\n\nIf you understand the risk, and still want to run this\nversion, hit OK.  Hit Cancel to exit.",
//		"Warning",
//		JOptionPane.OK_CANCEL_OPTION,
//		JOptionPane.WARNING_MESSAGE
//		) != JOptionPane.OK_OPTION)  //The index of the Cancel button.
//		System.exit(0);


//		if (PrefsModel.getInstance().getLastVersion() != null 
//		&& getVersion().isGreaterMinor(PrefsModel.getInstance().getLastVersion())){
//		String[] options = new String[2];
//		options[0] = TextFormatter.getTranslation(ButtonKeys.BUTTON_OK);
//		options[1] = TextFormatter.getTranslation(ButtonKeys.BUTTON_CANCEL);

//		if (JOptionPane.showOptionDialog(
//		null, 
//		TextFormatter.getTranslation(MessageKeys.MESSAGE_UPGRADE_NOTICE),
//		TextFormatter.getTranslation(MessageKeys.MESSAGE_UPGRADE_NOTICE_TITLE),
//		JOptionPane.OK_CANCEL_OPTION,
//		JOptionPane.WARNING_MESSAGE,
//		null,
//		options,
//		options[0]
//		) == 1)  //The index of the Cancel button.
//		System.exit(0);


//		//Make a backup of the last data file, just to be safe...
//		File file = PrefsModel.getInstance().getLastDataFile();
//		File backupDataFile = new File(
//		file.getAbsolutePath().replaceAll(Const.DATA_FILE_EXTENSION + "$", "") 
//		+ "_" + getVersion() + "_" + Const.BACKUP_FILE_EXTENSION);
//		try {
//		FileFunctions.copyFile(file, backupDataFile);
//		}
//		catch (IOException ioe){
//		Log.warning("Error backing up file: " + ioe);
//		}
//		}


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

		//Handle opening the last user file, if available.
		if (PrefsModel.getInstance().getLastDataFile() != null
				&& ApplicationModel.getInstance().getOpenFrames().size() == 0) {
			openFile(PrefsModel.getInstance().getLastDataFile());
		}

		//If no files are available, just create a new one.
		if (ApplicationModel.getInstance().getOpenFrames().size() == 0) {
			try {
				Document model = ModelFactory.createDocument();
				MainFrame mainWndow = new MainFrame(model);
				try{
					mainWndow.openWindow(PrefsModel.getInstance().getMainWindowSize(), PrefsModel.getInstance().getMainWindowLocation());
				}
				catch (WindowOpenException woe){
					Log.error("Error opening window: ", woe);
				}
			}
			catch (ModelException me){
				me.printStackTrace(Log.getPrintStream());
			}
		}

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
								Log.critical("Error saving autosave file:");
								dse.printStackTrace(Log.getPrintStream());
							}
						}
						else {
							Log.debug("Did not autosave, as there are no changes to the data file " + mainFrame.getDocument().getFile());
						}		
					}
				}
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
					mainWndow.openWindow(PrefsModel.getInstance().getMainWindowSize(), PrefsModel.getInstance().getMainWindowLocation());
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
						TextFormatter.getTranslation(MessageKeys.MESSAGE_PLUGIN_ADDED_RESTART_NEEDED),
						TextFormatter.getTranslation(MessageKeys.MESSAGE_PLUGIN_ADDED_RESTART_NEEDED_TITLE),
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
						TextFormatter.getTranslation(MessageKeys.MESSAGE_LANGUAGE_ADDED_RESTART_NEEDED),
						TextFormatter.getTranslation(MessageKeys.MESSAGE_LANGUAGE_ADDED_RESTART_NEEDED_TITLE),
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
		
		if (!OperatingSystemUtil.isMac())
			MossSplashScreen.showSplash("img/BuddiSplashScreen.jpg");
		
		//Catch runtime exceptions
		Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler(){
			public void uncaughtException(Thread arg0, Throwable arg1) {
				arg1.printStackTrace(Log.getPrintStream());
				if (arg1 instanceof DataModelProblemException)
					sendBugReport(((DataModelProblemException) arg1).getDataModel());
				else
					sendBugReport();

				arg1.printStackTrace(Log.getPrintStream());
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
							mainWndow.openWindow(PrefsModel.getInstance().getMainWindowSize(), PrefsModel.getInstance().getMainWindowLocation());
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
			+ "--prefs\tFilename\tPath and name of Preference File (Default varies by platform)\n"
			+ "--verbosity\t0-7\tVerbosity Level (0 = Emergency, 7 = Debug)\n"
			+ "--languages\tFolder\tFolder to store custom languages (should be writable)"
			+ "--plugins\tFolder\tFolder to store plugins (should be writable)"
			+ "--lnf\tclassName\tJava Look and Feel to use\n"
			+ "--simpleFont\t\tDon't use bold or italic fonts (better support for special characters on Windows)\n"
			+ "--log\tlogFile\tLocation to store logs, or 'stdout' / 'stderr' (default varies by platform)\n";
		// Undocumented flag --font	<fontName> will specifya font to use by default
		// Undocumented flag --debian will specify a .deb download for new versions.
		// Undocumented flag --redhat will specify a .rpm download for new versions.
		// Undocumented flag --unix will specify a .tgz download for new versions.

		List<ParseVariable> variables = new LinkedList<ParseVariable>();
		variables.add(new ParseVariable("--prefs", String.class, false));
		variables.add(new ParseVariable("--verbosity", Integer.class, false));
		variables.add(new ParseVariable("--plugins", String.class, false));
		variables.add(new ParseVariable("--languages", String.class, false));
		variables.add(new ParseVariable("--lnf", String.class, false));
		variables.add(new ParseVariable("--font", String.class, false));
		variables.add(new ParseVariable("--simpleFont", Boolean.class, false));
		variables.add(new ParseVariable("--log", String.class, false));
		variables.add(new ParseVariable("--debian", Boolean.class, false));
		variables.add(new ParseVariable("--redhat", Boolean.class, false));
		variables.add(new ParseVariable("--unix", Boolean.class, false));

		ParseResults results = ParseCommands.parse(args, help, variables);

		//Set the working path.  If we save files (plugins, data files) 
		// within this path, we remove this parent path.  This allows
		// us to use relative paths for such things as running from
		// USB drives, from remote shares (which would not always have
		// the same path), etc.
		//Removed in 3.0, as there are stricter definitions for file locations than before.
//		try {
//		if (System.getProperty("user.dir").length() > 0)
//		userDir = new File(System.getProperty("user.dir")).getCanonicalPath() + File.separator;

//		//If we did not set it via user.dir, we set it here.
//		if (userDir.length() == 0)
//		userDir = new File("").getCanonicalPath() + File.separator; // + (!OperatingSystemUtil.isWindows() ? File.separator : "");
//		}
//		catch (IOException ioe){
//		//Fallback which does not throw IOException, but may get drive case incorrect on Windows.
//		userDir = new File("").getAbsolutePath() + File.separator; // + (!OperatingSystemUtil.isWindows() ? File.separator : "");
//		}


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
		Integer verbosity = results.getInteger("--verbosity");
		if (verbosity != null){
			Log.setLogLevel(verbosity);
			Log.debug("Setting log level to " + verbosity);
		}
		else if (Const.DEVEL){
			Log.setLogLevel(Log.DEBUG);
		}



		String prefsLocation = results.getString("--prefs");
		String lnf = results.getString("--lnf");
		String font = results.getString("--font");
		languagesFolder = results.getString("--languages");
		pluginsFolder = results.getString("--plugins");
		simpleFont = results.getBoolean("--simpleFont");
		debian = results.getBoolean("--debian");
		redhat = results.getBoolean("--redhat");
		genericUnix = results.getBoolean("--unix");

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
		Log.notice("Running Buddi version " + getVersion());

		//Let the user know where the working directory is, after
		// we have set up logging properly.
//		Log.notice("Set working directory to " + userDir);

		//Set Buddi-specific LnF options.
		//This one removes the width limitation for dialogs.  Since we already will
		// wrap for other OS's, there is no need to have Quaqua do this for you.
		UIManager.put("OptionPane.maxCharactersPerLineCount", Integer.MAX_VALUE);
		//Set the max row count.  Quaqua overrides the value set in MossScrollingComboBox, 
		// so we must set it here manually. 
		UIManager.put("ComboBox.maximumRowCount", Integer.valueOf(10));
		
		System.setProperty("com.apple.mrj.application.apple.menu.about.name", "Buddi");

		//Load the correct Look and Feel.  Includes OS specific options, such as Quaqua constants.
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
				|| !PrefsModel.getInstance().getLastVersion().equals(getVersion())){
			PrefsModel.getInstance().updateVersion();

			String[] buttons = new String[2];
			buttons[0] = TextFormatter.getTranslation(ButtonKeys.BUTTON_DONATE);
			buttons[1] = TextFormatter.getTranslation(ButtonKeys.BUTTON_NOT_NOW);

			int reply = JOptionPane.showOptionDialog(
					frame, 
					TextFormatter.getTranslation(MessageKeys.MESSAGE_ASK_FOR_DONATION),
					TextFormatter.getTranslation(MessageKeys.MESSAGE_ASK_FOR_DONATION_TITLE),
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
					catch (ConnectException ce){
						//TODO Show warning dialog.
						Log.error(ce);
					}
					catch (IllegalArgumentException iae){
						//TODO Show warning dialog.
						Log.error(iae);
					}
					catch (MalformedURLException mue){
						Log.error(mue);
					}
					catch (IOException ioe){
						Log.error(ioe);

						String[] options = new String[1];
						options[0] = TextFormatter.getTranslation(ButtonKeys.BUTTON_OK);

						JOptionPane.showOptionDialog(
								frame, 
								TextFormatter.getTranslation(MessageKeys.MESSAGE_ERROR_CHECKING_FOR_UPDATES), 
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
						options[0] = TextFormatter.getTranslation(ButtonKeys.BUTTON_OK);

						JOptionPane.showOptionDialog(
								frame, 
								TextFormatter.getTranslation(MessageKeys.MESSAGE_NO_NEW_VERSION), 
								TextFormatter.getTranslation(MessageKeys.MESSAGE_NO_NEW_VERSION_TITLE), 
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
				Log.critical("Unable to send crash email.");
			}
		}

		Log.critical(crashLog.toString());
	}
}
