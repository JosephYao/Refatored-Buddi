/*
 * Created on May 12, 2006 by wyatt
 */
package org.homeunix.drummer.controller;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
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
import org.homeunix.drummer.prefs.DictEntry;
import org.homeunix.drummer.prefs.Prefs;
import org.homeunix.drummer.prefs.PrefsFactory;
import org.homeunix.drummer.prefs.PrefsPackage;
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
	
	private Prefs prefs;
	private PrefsFactory prefsFactory = PrefsFactoryImpl.eINSTANCE;

	//Provide backing for the autocomplete text fields
	private final DefaultDictionary descDict;
	private final DefaultDictionary memoDict;

	private ResourceSet resourceSet;
	
	private static String location;

	private PrefsInstance(){
		descDict = new DefaultDictionary();
		memoDict = new DefaultDictionary();
		
		if (location == null){
			if (Buddi.isMac()){
				location = 
					System.getProperty("user.home") 
					+ File.separator + "Library" + File.separator 
					+ "Application Support" + File.separator 
					+ "Buddi" + File.separator + "prefs.xml";
			}
			else{
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
				prefs = (Prefs) contents.get(0);
			}
		}
		//If there is a problem opening the file, we will prompt to make a new one
		catch (Exception e){
			//If the file does not exist, we make a new one w/o prompting.
			prefs = prefsFactory.createPrefs();
			
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
			
			//Set meaningful defaults
			prefs.setDataFile(dataFileName);
			prefs.setLanguage("en-US");
			prefs.setShowDeletedAccounts(true);
			prefs.setShowDeletedCategories(true);
			prefs.setDateFormat("yyyy/MMM/d");
			
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
			resource.getContents().add(prefs);
			
			try{
				resource.save(Collections.EMPTY_MAP);
			}
			catch (IOException ioe){}
		}
		
		//Load the auto complete entries into the dictionary
		for (Object o : prefs.getDescDict()) {
			if (o instanceof DictEntry) {
				descDict.add(((DictEntry) o).getEntry());
			}
		}

		for (Object o : prefs.getMemoDict()) {
			if (o instanceof DictEntry) {
				memoDict.add(((DictEntry) o).getEntry());
			}
		}

	}
	
	public void savePrefs(){		
		
		File saveLocation = new File(location);
		File backupLocation = new File(saveLocation.getAbsolutePath() + "~");
		
		//Translate between Set<String> (the dictionary) and the persistence model
		prefs.getDescDict().retainAll(new Vector());
		for (String s : descDict) {
			DictEntry d = prefsFactory.createDictEntry();
			d.setEntry(s);
			prefs.getDescDict().add(d);
		}
		prefs.getMemoDict().retainAll(new Vector());
		for (String s : memoDict) {
			DictEntry d = prefsFactory.createDictEntry();
			d.setEntry(s);
			prefs.getMemoDict().add(d);
		}
		
		try{
			FileFunctions.copyFile(saveLocation, backupLocation);
			
			URI fileURI = URI.createFileURI(saveLocation.getAbsolutePath());
			
			Log.debug("Data saved to " + location);
			
			Resource resource = resourceSet.createResource(fileURI);
			
			resource.getContents().add(getPrefs());
			
			resource.save(Collections.EMPTY_MAP);
		}
		catch (IOException ioe){
			Log.critical("Error when saving file: " + ioe);
		}
	}
	
	public Prefs getPrefs(){
		return prefs;
	}
		
	public static void setLocation(String location){
		PrefsInstance.location = location;
	}
	
	public void addDescEntry(String entry){
		descDict.add(entry);
		savePrefs();
	}

	public void addMemoEntry(String entry){
		memoDict.add(entry);
		savePrefs();
	}

	public DefaultDictionary getDescDict() {
		return descDict;
	}

	public DefaultDictionary getMemoDict() {
		return memoDict;
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
				JOptionPane.showMessageDialog(null, "Error choosing file.  Exiting.");
				return null;					
			}
		}
		else{
			JOptionPane.showMessageDialog(null, "Did not choose data directory.");
			return null;
		}
		
		Log.debug("Chosen data file: " + dataFileName);
		
		return dataFileName;
	}

}
