/*
 * Created on May 12, 2006 by wyatt
 */
package org.homeunix.drummer.prefs;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMLResourceFactoryImpl;
import org.homeunix.drummer.Buddi;
import org.homeunix.drummer.Const;
import org.homeunix.drummer.controller.TranslateKeys;
import org.homeunix.drummer.prefs.impl.PrefsFactoryImpl;
import org.homeunix.drummer.util.FileFunctions;
import org.homeunix.drummer.util.Log;
import org.homeunix.drummer.view.components.autocomplete.DefaultDictionary;

@SuppressWarnings("unchecked")
public class PrefsInstance {
	
	//public static final String DATA_FILE_EXTENSION = ".xml";
	
	public static PrefsInstance getInstance() {
		return SingletonHolder.instance;
	}
	
	private static class SingletonHolder {
		private static PrefsInstance instance = new PrefsInstance();		
	}
	
	private UserPrefs userPrefs;
	private PrefsFactory prefsFactory = PrefsFactoryImpl.eINSTANCE;

	//Provide backing for the autocomplete text fields
	private final DefaultDictionary descDict;
	private final Map<String, DictData> defaultsMap;
	private final Map<String, ListAttributes> listAttributesMap;

	private ResourceSet resourceSet;
	private static String location;

	private PrefsInstance(){
		descDict = new DefaultDictionary();
		defaultsMap = new HashMap<String, DictData>();
		listAttributesMap = new HashMap<String, ListAttributes>();
		
		if (location == null){
			if (Buddi.isMac()){
				location = 
					System.getProperty("user.home") 
					+ File.separator 
					+ "Library" + File.separator 
					+ "Application Support" 
					+ File.separator 
					+ "Buddi" + File.separator 
					+ "prefs.xml";
			}
			else if (Buddi.isWindows()){

				location = 
					System.getProperty("user.home")
                                        + File.separator
                                        + "Application Data"
                                        + File.separator
                                        + "Buddi" + File.separator
                                        + "prefs.xml";
			}
			else{ //Probably Linux / Unix...
				location = 
					System.getProperty("user.home") 
					+ File.separator + ".buddi" + File.separator  
					+ "prefs.xml";

			}
		}
		File locationFile = new File(location).getAbsoluteFile();
		
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
			
			//Do some final sanity checks.  Probably not needed for everyday use, but
			// needed for some upgrades (i.e., from 1.1.5 to 1.1.6).
			if (userPrefs.getPrefs().getSelectedInterval() == null 
					|| userPrefs.getPrefs().getIntervals() == null
					|| userPrefs.getPrefs().getIntervals().getAllIntervals() == null)
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
			
			//Allow upgrading to rotating backups - default to 10
			if (userPrefs.getPrefs().getNumberOfBackups() == 0){
				userPrefs.getPrefs().setNumberOfBackups(10);
				savePrefs();
			}
			
			if (userPrefs.getPrefs().getCurrencySymbol() == null){
				userPrefs.getPrefs().setCurrencySymbol("$");
				savePrefs();
			}
		}
		//If there is a problem opening the file, we will prompt to make a new one
		catch (Exception e){
			//If the file does not exist, we make a new one w/o prompting.
			userPrefs = prefsFactory.createUserPrefs();
			Prefs prefs = prefsFactory.createPrefs();
			userPrefs.setPrefs(prefs);
			
			//Create sane defaults for all the properties
			//Ask the user where to put the data file.  This could possibly be on encrypted disk, etc.
			// We do not translate this part because we have not yet loaded the langauge files...
			JOptionPane.showMessageDialog(
					null,
					"You need to choose your data file.  You can\n"
					+ "either choose an existing Buddi data store, or a\n"
					+ "directory in which a new file will be created.",
					"Choose Datastore Location...",
					JOptionPane.INFORMATION_MESSAGE);
			
			final String dataFileName = chooseDataFile();
			
			if (dataFileName == null){
				Log.critical("Cannot use a null file");
				System.exit(1);
			}

			File languageLocation = new File(Const.LANGUAGE_FOLDER);
			if (languageLocation.exists() && languageLocation.isDirectory()){
				Vector<String> languages = new Vector<String>();
				for (File f: languageLocation.listFiles())
					if (f.getName().endsWith(Const.LANGUAGE_EXTENSION))
						languages.add(f.getName().replaceAll(Const.LANGUAGE_EXTENSION, ""));

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
					prefs.setLanguage((String) retValue);
				else{
					JOptionPane.showMessageDialog(null, "Invalid selection.  Defaulting to English - you can change this in Preferences.", "Invalid Selection", JOptionPane.ERROR_MESSAGE);
					prefs.setLanguage("English");	
				}
			}
			else{
				prefs.setLanguage("English");	
			}
			
			//Set meaningful defaults
			prefs.setDataFile(dataFileName);
			prefs.setShowDeletedAccounts(true);
			prefs.setShowDeletedCategories(true);
			prefs.setShowAutoComplete(true);
			prefs.setEnableUpdateNotifications(true);
			prefs.setDateFormat("yyyy/MMM/d");
			prefs.setCurrencySymbol(Const.CURRENCY_FORMATS[0]);
			
			WindowAttributes main = prefsFactory.createWindowAttributes();
			WindowAttributes transactions = prefsFactory.createWindowAttributes();
			WindowAttributes graphs = prefsFactory.createWindowAttributes();
			WindowAttributes reports = prefsFactory.createWindowAttributes();
			
			main.setHeight(400);
			main.setWidth(600);
			
			transactions.setHeight(500);
			transactions.setWidth(700);
			
			prefs.setMainWindow(main);
			prefs.setTransactionsWindow(transactions);
			prefs.setGraphsWindow(graphs);
			prefs.setReportsWindow(reports);
			
			Intervals intervals = prefsFactory.createIntervals();
			prefs.setIntervals(intervals);
			
			Interval month = prefsFactory.createInterval();
			month.setName(TranslateKeys.MONTH.toString());
			month.setLength(1);
			month.setDays(false);  //If days is false, then we assume the length is in months.
			prefs.getIntervals().getAllIntervals().add(month);

			Interval week = prefsFactory.createInterval();
			week.setName(TranslateKeys.WEEK.toString());
			week.setLength(7);
			week.setDays(true);
			prefs.getIntervals().getAllIntervals().add(week);
			
			Interval fortnight = prefsFactory.createInterval();
			fortnight.setName(TranslateKeys.FORTNIGHT.toString());
			fortnight.setLength(14);
			fortnight.setDays(true);
			prefs.getIntervals().getAllIntervals().add(fortnight);

			Interval quarter = prefsFactory.createInterval();
			quarter.setName(TranslateKeys.QUARTER.toString());
			quarter.setLength(3);
			quarter.setDays(false);
			prefs.getIntervals().getAllIntervals().add(quarter);

			Interval year = prefsFactory.createInterval();
			year.setName(TranslateKeys.YEAR.toString());
			year.setLength(12);
			year.setDays(false);
			prefs.getIntervals().getAllIntervals().add(year);

			
			
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
		
		//Load the auto complete entries into the dictionary
		// and create the autocomplete map, between 
		// description and other fields.
		for (Object o : userPrefs.getPrefs().getDescDict()) {
			if (o instanceof DictEntry) {
				DictEntry entry = (DictEntry) o;
				descDict.add(entry.getEntry());
				defaultsMap.put(entry.getEntry(), entry.getData());
			}
		}
		
		//Load the state for the list entries
		for (Object o : userPrefs.getPrefs().getListEntries()) {
			if (o instanceof ListEntry){
				ListEntry l = (ListEntry) o;
				listAttributesMap.put(l.getEntry(), l.getAttributes());
			}
		}
	}
	
	public void savePrefs(){		
		
		File saveLocation = new File(location);
		File backupLocation = new File(saveLocation.getAbsolutePath() + "~");
		
		//Translate between Set<String> (the dictionary) and the persistence model
		userPrefs.getPrefs().getDescDict().retainAll(new Vector());
		for (String s : descDict) {
			DictEntry d = prefsFactory.createDictEntry();
			d.setEntry(s);
			d.setData(defaultsMap.get(s));
			userPrefs.getPrefs().getDescDict().add(d);
		}
		
		//Translate between Map<String, ListAttribute> (the list attributes) and the persistance model
		userPrefs.getPrefs().getListEntries().retainAll(new Vector());
		for (String s : listAttributesMap.keySet()){
			//Create an entry in the listEntry structure
			ListEntry l = prefsFactory.createListEntry();
			l.setEntry(s);
			l.setAttributes(listAttributesMap.get(s));
			
			userPrefs.getPrefs().getListEntries().add(l);
		}
		
		try{
			FileFunctions.copyFile(saveLocation, backupLocation);
			
			URI fileURI = URI.createFileURI(saveLocation.getAbsolutePath());
			
			if (Const.DEVEL) Log.debug("Data saved to " + location);
			
			Resource resource = resourceSet.createResource(fileURI);
			
			resource.getContents().add(userPrefs);
			
			resource.save(Collections.EMPTY_MAP);
		}
		catch (IOException ioe){
			Log.critical("Error when saving file: " + ioe);
		}
	}
	
	public Prefs getPrefs(){
		return userPrefs.getPrefs();
	}
		
	public static void setLocation(String location){
		PrefsInstance.location = location;
	}
	
	public void addDescEntry(String entry){
		descDict.add(entry);
		savePrefs();
	}

	public DefaultDictionary getDescDict() {
		return descDict;
	}
	
	public String getLastVersionRun(){
		Version version = userPrefs.getPrefs().getLastVersionRun();
		if (version == null){
			version = prefsFactory.createVersion();
			version.setVersion("");
			userPrefs.getPrefs().setLastVersionRun(version);
		}
		savePrefs();
		return version.getVersion();
	}
	
	public void updateVersion(){
		Version version = userPrefs.getPrefs().getLastVersionRun();
		if (version == null){
			version = prefsFactory.createVersion();
			userPrefs.getPrefs().setLastVersionRun(version);
		}
		
		version.setVersion(Const.VERSION);
		savePrefs();
	}
	
	public void setAutoCompleteEntry(String description, 
			String number, long amount, String from, String to, String memo){
		DictData dd = prefsFactory.createDictData();
		dd.setNumber(number);
		dd.setAmount(amount);
		dd.setFrom(from);
		dd.setTo(to);
		dd.setMemo(memo);
		
		defaultsMap.put(description, dd);
	}
	
	public DictData getAutoCompleteEntry(String description){
		return defaultsMap.get(description);
	}
	
	public void setListAttributes(String entryName, boolean unrolled){
		ListAttributes l;
		if (listAttributesMap.get(entryName) != null)
			l = listAttributesMap.get(entryName);
		else
			l = prefsFactory.createListAttributes();
		
		l.setUnrolled(unrolled);
		
		listAttributesMap.put(entryName, l);
	}
	
	public ListAttributes getListAttributes(String entryName){
		return listAttributesMap.get(entryName);
	}
	
	public Vector<Interval> getIntervals(){
		Vector<Interval> intervals = new Vector<Interval>();
		
		for (Object o : userPrefs.getPrefs().getIntervals().getAllIntervals()) {
			if (o instanceof Interval){
				Interval interval = (Interval) o;
				intervals.add(interval);
				if (Const.DEVEL) Log.debug("Added interval: " + interval);
			}
		}
		
		return intervals;
	}
	
	public Interval getSelectedInterval(){
		for (Object o : userPrefs.getPrefs().getIntervals().getAllIntervals()) {
			if (o instanceof Interval){
				Interval interval = (Interval) o;
				if (interval.getName().equals(userPrefs.getPrefs().getSelectedInterval()))
					return interval;
			}
		}
		
		if (Const.DEVEL) Log.debug("Can't find Interval: " + userPrefs.getPrefs().getSelectedInterval());
		return null;
	}
	
	public static String chooseDataFile(){
		final String dataFileName;
		
		final JFileChooser jfc = new JFileChooser();
		jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		jfc.setDialogTitle("Choose Datastore Location...");
		if (jfc.showSaveDialog(null) == JFileChooser.APPROVE_OPTION){
			if (jfc.getSelectedFile() != null){
				String chosenString = jfc.getSelectedFile().getAbsolutePath();
				if (jfc.getSelectedFile().isDirectory()){
					dataFileName = 
						chosenString 
						+ File.separator + Const.DATA_DEFAULT_FILENAME 
						+ Const.DATA_FILE_EXTENSION;
				}
				else{
					if (chosenString.endsWith(Const.DATA_FILE_EXTENSION))
						dataFileName = chosenString;
					else
						dataFileName = chosenString + Const.DATA_FILE_EXTENSION;
				}
			}
			else{
				JOptionPane.showMessageDialog(
						null,
						"Error choosing file.",
						"Exiting.",
						JOptionPane.ERROR_MESSAGE
				);
				return null;					
			}
		}
		else{
			JOptionPane.showMessageDialog(
					null,
					"Did not choose data directory.",
					"Exiting",
					JOptionPane.ERROR_MESSAGE
			);
			return null;
		}
		
		if (Const.DEVEL) Log.debug("Chosen data file: " + dataFileName);
		
		return dataFileName;
	}

	public void checkWindowSanity(){
		Prefs prefs = getPrefs();
		if (prefs.getMainWindow() == null)
			prefs.setMainWindow(prefsFactory.createWindowAttributes());
		if (prefs.getTransactionsWindow() == null)
			prefs.setTransactionsWindow(prefsFactory.createWindowAttributes());
		if (prefs.getGraphsWindow() == null)
			prefs.setGraphsWindow(prefsFactory.createWindowAttributes());
		if (prefs.getReportsWindow() == null)
			prefs.setReportsWindow(prefsFactory.createWindowAttributes());

		if (prefs.getMainWindow().getHeight() < 300)
			prefs.getMainWindow().setHeight(300);
		if (prefs.getTransactionsWindow().getHeight() < 300)
			prefs.getTransactionsWindow().setHeight(300);
		if (prefs.getMainWindow().getWidth() < 400)
			prefs.getMainWindow().setWidth(400);
		if (prefs.getTransactionsWindow().getWidth() < 500)
			prefs.getTransactionsWindow().setWidth(500);
	}
}
