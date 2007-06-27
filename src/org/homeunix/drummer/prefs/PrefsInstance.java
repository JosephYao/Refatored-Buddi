/*
 * Created on May 12, 2006 by wyatt
 */
package org.homeunix.drummer.prefs;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import javax.swing.JOptionPane;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMLResourceFactoryImpl;
import org.homeunix.drummer.Buddi;
import org.homeunix.drummer.Const;
import org.homeunix.drummer.controller.Translate;
import org.homeunix.drummer.controller.TranslateKeys;
import org.homeunix.thecave.moss.util.FileFunctions;
import org.homeunix.thecave.moss.util.Log;
import org.homeunix.thecave.moss.util.OperatingSystemUtil;

@SuppressWarnings("unchecked")
public class PrefsInstance {

	public static PrefsInstance getInstance() {
		return SingletonHolder.instance;
	}

	private static class SingletonHolder {
		private static PrefsInstance instance = new PrefsInstance();		
	}

	private UserPrefs userPrefs;

	private final Map<String, ListAttributes> listAttributesMap;

	private ResourceSet resourceSet;
	private static String location;

	private PrefsInstance(){

		listAttributesMap = new HashMap<String, ListAttributes>();

		if (location == null){
			//We use OperatingSystemUtil to define the correct location for preferences files.
			location = OperatingSystemUtil.getPreferencesFile("Buddi", "prefs.xml").getAbsolutePath();
		}

		File locationFile = new File(location).getAbsoluteFile();
		if (!locationFile.canWrite() && locationFile.exists()){
			String[] options = new String[1];
			options[0] = Translate.getInstance().get(TranslateKeys.BUTTON_OK);

			JOptionPane.showOptionDialog(
					null,
					Translate.getInstance().get(TranslateKeys.MESSAGE_ERROR_CANNOT_WRITE_PREFS_FILE),
					Translate.getInstance().get(TranslateKeys.ERROR),
					JOptionPane.DEFAULT_OPTION,
					JOptionPane.ERROR_MESSAGE,
					null,
					options,
					options[0]);
			System.exit(1);
		}
		if (!locationFile.canRead() && locationFile.exists()){
			String[] options = new String[1];
			options[0] = Translate.getInstance().get(TranslateKeys.BUTTON_OK);

			JOptionPane.showOptionDialog(
					null,
					Translate.getInstance().get(TranslateKeys.PROBLEM_READING_PREFS_FILE),
					Translate.getInstance().get(TranslateKeys.ERROR),
					JOptionPane.DEFAULT_OPTION,
					JOptionPane.ERROR_MESSAGE,
					null,
					options,
					options[0]);
			System.exit(1);
		}

		try{
			// Create a resource set.
			resourceSet = new ResourceSetImpl();

			// Register the default resource factory -- only needed for stand-alone!
			resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put(
					Resource.Factory.Registry.DEFAULT_EXTENSION, new XMLResourceFactoryImpl());

			// Register the package
			@SuppressWarnings("unused") 
			PrefsPackage prefsPackage = PrefsPackage.eINSTANCE;

			// Get the URI of the model file.
			URI fileURI = URI.createFileURI(locationFile.getAbsolutePath());

			// Demand load the resource for this file.
			Resource resource = resourceSet.getResource(fileURI, true);

			// Print the contents of the resource to System.out.
			EList contents = resource.getContents();
			if (contents.size() > 0){
				userPrefs = (UserPrefs) contents.get(0);
			}

			//Load the state for the list entries
			for (Object o : userPrefs.getPrefs().getLists().getListEntries()) {
				if (o instanceof ListEntry){
					ListEntry l = (ListEntry) o;
					listAttributesMap.put(l.getEntry(), l.getAttributes());
				}
			}

			//Do some final sanity checks.  Probably not needed for 
			// everyday use, but needed for some 
			// upgrades (i.e., from 1.1.5 to 1.1.6).
			if (userPrefs.getPrefs().getSelectedInterval() == null 
					|| userPrefs.getPrefs().getIntervals() == null
					|| userPrefs.getPrefs().getIntervals().getIntervals() == null)
				throw new Exception();

			//Make a smooth crossover from 1.6 to 1.8
			if (userPrefs.getPrefs().getLanguage() != null){
				if (userPrefs.getPrefs().getLanguage().equals("en"))
					userPrefs.getPrefs().setLanguage("English");
				else if (userPrefs.getPrefs().getLanguage().equals("en-US"))
					userPrefs.getPrefs().setLanguage("English_(US)");
				else if (userPrefs.getPrefs().getLanguage().equals("es"))
					userPrefs.getPrefs().setLanguage("Espanol");
				else if (userPrefs.getPrefs().getLanguage().equals("de"))
					userPrefs.getPrefs().setLanguage("Deutsch");
				else if (userPrefs.getPrefs().getLanguage().equals("no"))
					userPrefs.getPrefs().setLanguage("Norsk");
				savePrefs();
			}

			//Change Interval names to reflect new TranslationKeys in 
			// and beyond version 2.3.4.  Can probably remove this
			// after 2.6.x.
			for (Object o : userPrefs.getPrefs().getIntervals().getIntervals()) {
				if (o instanceof Interval){
					Interval interval = (Interval) o;

					if (interval.getName().equals("WEEK"))
						interval.setName(TranslateKeys.INTERVAL_WEEK.toString());
					else if (interval.getName().equals("FORTNIGHT"))
						interval.setName(TranslateKeys.INTERVAL_FORTNIGHT.toString());
					else if (interval.getName().equals("MONTH"))
						interval.setName(TranslateKeys.INTERVAL_MONTH.toString());
					else if (interval.getName().equals("QUARTER"))
						interval.setName(TranslateKeys.INTERVAL_QUARTER.toString());
					else if (interval.getName().equals("YEAR"))
						interval.setName(TranslateKeys.INTERVAL_YEAR.toString());
					savePrefs();
				}
			}

			//Finish off the above checks by updating the selected interval too.
			String interval = userPrefs.getPrefs().getSelectedInterval();
			if (interval.equals("WEEK"))
				userPrefs.getPrefs().setSelectedInterval(TranslateKeys.INTERVAL_WEEK.toString());
			else if (interval.equals("FORTNIGHT"))
				userPrefs.getPrefs().setSelectedInterval(TranslateKeys.INTERVAL_FORTNIGHT.toString());
			else if (interval.equals("MONTH"))
				userPrefs.getPrefs().setSelectedInterval(TranslateKeys.INTERVAL_MONTH.toString());
			else if (interval.equals("QUARTER"))
				userPrefs.getPrefs().setSelectedInterval(TranslateKeys.INTERVAL_QUARTER.toString());
			else if (interval.equals("YEAR"))
				userPrefs.getPrefs().setSelectedInterval(TranslateKeys.INTERVAL_YEAR.toString());
			savePrefs();


			//Allow upgrading to rotating backups - default to 20
			if (userPrefs.getPrefs().getNumberOfBackups() == 0){
				userPrefs.getPrefs().setNumberOfBackups(20);
				savePrefs();
			}

			//Set a sane currency symbol if none is already set.
			if (userPrefs.getPrefs().getCurrencySymbol() == null){
				userPrefs.getPrefs().setCurrencySymbol("$");
				savePrefs();
			}
		}
		//If there is a problem opening the file, we will make a new one
		catch (Exception e){
			//If the file does not exist, we make a new one w/o prompting.
			// This may be annoying, as it loses all your preferences,
			// but there is not much you can do.  This only happens 
			// when the preference file changes format (i.e., version 
			// upgrade).
			userPrefs = PrefsFactory.eINSTANCE.createUserPrefs();
			Prefs prefs = PrefsFactory.eINSTANCE.createPrefs();
			userPrefs.setPrefs(prefs);

			//Create sane defaults for all the properties...

			//Prompt for language
			File languageLocation = new File(Buddi.getWorkingDir() + File.separator + Const.LANGUAGE_FOLDER);
			Set<String> languageSet = new HashSet<String>();
			if (languageLocation.exists() && languageLocation.isDirectory()){
				for (File f: languageLocation.listFiles()){
					if (f.getName().endsWith(Const.LANGUAGE_EXTENSION)){
						languageSet.add(f.getName().replaceAll(Const.LANGUAGE_EXTENSION, "").replaceAll("_", " "));
					}
				}
			}

			for (String s : Const.BUNDLED_LANGUAGES){
				languageSet.add(s.replaceAll("_", " "));
			}

			Vector<String> languages = new Vector<String>(languageSet);
			Collections.sort(languages);

			Object[] options = languages.toArray();
			Object defaultOption = options[languages.indexOf("English")];
			Object retValue = JOptionPane.showInputDialog(
					null, 
					"Please choose your language:", 
					"Language", 
					JOptionPane.PLAIN_MESSAGE, 
					null, 
					options, 
					defaultOption
			);

			if (retValue != null)
				prefs.setLanguage(((String) retValue).replaceAll(" ", "_"));
			else{
				Log.info("User hit cancel.  Exiting.");
				System.exit(0);
			}

			//Set meaningful defaults
			prefs.setShowDeletedAccounts(false);
			prefs.setShowDeletedCategories(false);
			prefs.setShowAccountTypes(true);
			prefs.setShowAutoComplete(true);
			prefs.setEnableUpdateNotifications(true);
			prefs.setDateFormat(Const.DATE_FORMATS[0]);
			prefs.setCurrencySymbol(Const.CURRENCY_FORMATS[0]);

			prefs.setLists(PrefsFactory.eINSTANCE.createLists());
			prefs.setWindows(PrefsFactory.eINSTANCE.createWindows());
			prefs.setIntervals(PrefsFactory.eINSTANCE.createIntervals());

			WindowAttributes main = PrefsFactory.eINSTANCE.createWindowAttributes();
			WindowAttributes transactions = PrefsFactory.eINSTANCE.createWindowAttributes();
			WindowAttributes graphs = PrefsFactory.eINSTANCE.createWindowAttributes();
			WindowAttributes reports = PrefsFactory.eINSTANCE.createWindowAttributes();

			main.setHeight(400);
			main.setWidth(600);

			transactions.setHeight(500);
			transactions.setWidth(700);

			prefs.getWindows().setMainWindow(main);
			prefs.getWindows().setTransactionsWindow(transactions);
			prefs.getWindows().setGraphsWindow(graphs);
			prefs.getWindows().setReportsWindow(reports);

			Interval month = PrefsFactory.eINSTANCE.createInterval();
			month.setName(TranslateKeys.INTERVAL_MONTH.toString());
			month.setLength(1);
			month.setDays(false);  //If days is false, then we assume the length is in months.
			prefs.getIntervals().getIntervals().add(month);

			Interval week = PrefsFactory.eINSTANCE.createInterval();
			week.setName(TranslateKeys.INTERVAL_WEEK.toString());
			week.setLength(7);
			week.setDays(true);
			prefs.getIntervals().getIntervals().add(week);

			Interval fortnight = PrefsFactory.eINSTANCE.createInterval();
			fortnight.setName(TranslateKeys.INTERVAL_FORTNIGHT.toString());
			fortnight.setLength(14);
			fortnight.setDays(true);
			prefs.getIntervals().getIntervals().add(fortnight);

			Interval quarter = PrefsFactory.eINSTANCE.createInterval();
			quarter.setName(TranslateKeys.INTERVAL_QUARTER.toString());
			quarter.setLength(3);
			quarter.setDays(false);
			prefs.getIntervals().getIntervals().add(quarter);

			Interval year = PrefsFactory.eINSTANCE.createInterval();
			year.setName(TranslateKeys.INTERVAL_YEAR.toString());
			year.setLength(12);
			year.setDays(false);
			prefs.getIntervals().getIntervals().add(year);

			prefs.setSelectedInterval(month.toString());

			ResourceSet resourceSet = new ResourceSetImpl();			
			resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put(
					Resource.Factory.Registry.DEFAULT_EXTENSION, new XMLResourceFactoryImpl());

			if (!locationFile.getParentFile().exists()){
				locationFile.getParentFile().mkdirs();
			}
			if (!locationFile.getParentFile().exists()){
				Log.critical("Cannot create directory for preferences file.  Please choose a location for which you have write privleges.  Exiting.");
				System.exit(1);
			}
			URI fileURI = URI.createFileURI(locationFile.getAbsolutePath());			
			Resource resource = resourceSet.createResource(fileURI);			
			resource.getContents().add(userPrefs);

			try{
				resource.save(Collections.EMPTY_MAP);
			}
			catch (IOException ioe){}
		}

		//Load language
		Translate.getInstance().loadLanguage(
				userPrefs.getPrefs().getLanguage());
	}

	public void savePrefs(){
		if (location == null){
			return;
		}

		File saveLocation = new File(location);
		File backupLocation = new File(saveLocation.getAbsolutePath() + "~");

		//Translate between Map<String, ListAttribute> (the list attributes) and the persistance model
		userPrefs.getPrefs().getLists().getListEntries().retainAll(new Vector());
		for (String s : listAttributesMap.keySet()){
			//Create an entry in the listEntry structure
			ListEntry l = PrefsFactory.eINSTANCE.createListEntry();
			l.setEntry(s);
			l.setAttributes(listAttributesMap.get(s));

			userPrefs.getPrefs().getLists().getListEntries().add(l);
		}

		String workingDirectoryRegex = "";
		if (userPrefs.getPrefs().getDataFile() != null){
			//If we are using relative directories, replace with the relative dirs.
			//Java doesn't like non-escaped backslashes in regexes...
			workingDirectoryRegex = Buddi.getWorkingDir().replaceAll("\\\\", "\\\\\\\\");
			userPrefs.getPrefs().setDataFile(userPrefs.getPrefs().getDataFile().replaceAll("^" + workingDirectoryRegex, ""));

			for (Object o : userPrefs.getPrefs().getLists().getPlugins()) {
				if (o instanceof Plugin){
					Plugin plugin = (Plugin) o;
					plugin.setJarFile(plugin.getJarFile().replaceAll("^" + workingDirectoryRegex, ""));
				}
			}
		}

		try{
			FileFunctions.copyFile(saveLocation, backupLocation);

			URI fileURI = URI.createFileURI(saveLocation.getAbsolutePath());
			if (Const.DEVEL) Log.debug("Preferences saved to " + location);

			Resource resource = resourceSet.createResource(fileURI);
			resource.getContents().add(userPrefs);
			resource.save(Collections.EMPTY_MAP);
		}
		catch (IOException ioe){
			Log.critical("Error when saving preferences file: " + ioe);
		}
	}

	public Prefs getPrefs(){
		return userPrefs.getPrefs();
	}

	public static void setLocation(String location){
		PrefsInstance.location = location;
	}

//	public void addDescEntry(String entry){
//	descDict.add(entry);
//	savePrefs();
//	}


	public String getLastVersionRun(){
		Version version = userPrefs.getPrefs().getLastVersionRun();
		if (version == null){
			version = PrefsFactory.eINSTANCE.createVersion();
			version.setVersion("");
			userPrefs.getPrefs().setLastVersionRun(version);
		}
		savePrefs();
		return version.getVersion();
	}

	public void updateVersion(){
		Version version = userPrefs.getPrefs().getLastVersionRun();
		if (version == null){
			version = PrefsFactory.eINSTANCE.createVersion();
			userPrefs.getPrefs().setLastVersionRun(version);
		}

		version.setVersion(Const.VERSION);
		savePrefs();
	}

	public void setListAttributes(String entryName, boolean unrolled){
		ListAttributes l;
		if (listAttributesMap.get(entryName) != null)
			l = listAttributesMap.get(entryName);
		else
			l = PrefsFactory.eINSTANCE.createListAttributes();

		l.setUnrolled(unrolled);

		listAttributesMap.put(entryName, l);
	}

	public ListAttributes getListAttributes(String entryName){
		return listAttributesMap.get(entryName);
	}

	public Vector<Interval> getIntervals(){
		Vector<Interval> intervals = new Vector<Interval>();

		for (Object o : userPrefs.getPrefs().getIntervals().getIntervals()) {
			if (o instanceof Interval){
				Interval interval = (Interval) o;
				intervals.add(interval);
				if (Const.DEVEL) Log.debug("Added interval: " + interval);
			}
		}

		return intervals;
	}

	public Interval getSelectedInterval(){
		for (Object o : userPrefs.getPrefs().getIntervals().getIntervals()) {
			if (o instanceof Interval){
				Interval interval = (Interval) o;
				if (interval.getName().equals(userPrefs.getPrefs().getSelectedInterval()))
					return interval;
			}
		}

		Log.error("Can't find Interval: " + userPrefs.getPrefs().getSelectedInterval());
		return null;
	}

	public void checkWindowSanity(){
		Prefs prefs = getPrefs();
		if (prefs.getWindows().getMainWindow() == null)
			prefs.getWindows().setMainWindow(PrefsFactory.eINSTANCE.createWindowAttributes());
		if (prefs.getWindows().getTransactionsWindow() == null)
			prefs.getWindows().setTransactionsWindow(PrefsFactory.eINSTANCE.createWindowAttributes());
		if (prefs.getWindows().getGraphsWindow() == null)
			prefs.getWindows().setGraphsWindow(PrefsFactory.eINSTANCE.createWindowAttributes());
		if (prefs.getWindows().getReportsWindow() == null)
			prefs.getWindows().setReportsWindow(PrefsFactory.eINSTANCE.createWindowAttributes());

		if (prefs.getWindows().getMainWindow().getHeight() < 300)
			prefs.getWindows().getMainWindow().setHeight(300);
		if (prefs.getWindows().getTransactionsWindow().getHeight() < 300)
			prefs.getWindows().getTransactionsWindow().setHeight(300);
		if (prefs.getWindows().getMainWindow().getWidth() < 400)
			prefs.getWindows().getMainWindow().setWidth(400);
		if (prefs.getWindows().getTransactionsWindow().getWidth() < 500)
			prefs.getWindows().getTransactionsWindow().setWidth(500);
	}

	/**
	 * Returns the folder in which the preferences file exists.  Can be
	 * used for plugins to store preferences in a consistent location.
	 * @return
	 */
	public File getPreferencesFolder(){
		if (location != null)
			return new File(location).getParentFile();
		else
			return OperatingSystemUtil.getPreferencesFile("Buddi", "test").getParentFile();
	}
}
