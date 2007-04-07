/*
 * Created on May 12, 2006 by wyatt
 */
package org.homeunix.drummer.model;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JOptionPane;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.URIConverter;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMLResourceFactoryImpl;
import org.homeunix.drummer.Buddi;
import org.homeunix.drummer.Const;
import org.homeunix.drummer.controller.ScheduleController;
import org.homeunix.drummer.controller.SourceController;
import org.homeunix.drummer.controller.Translate;
import org.homeunix.drummer.controller.TranslateKeys;
import org.homeunix.drummer.controller.TypeController;
import org.homeunix.drummer.model.util.AESCryptoCipher;
import org.homeunix.drummer.prefs.PrefsInstance;
import org.homeunix.thecave.moss.util.FileFunctions;
import org.homeunix.thecave.moss.util.Log;

/**
 * TODO: allow the user to specify if they'd like to use the maximum
 * strength encryption supported by their system. Make sure to warn
 * the user that this may make their encrypted data file non-portable. 
 */
@SuppressWarnings("unchecked")
public class DataInstance {
	public static DataInstance getInstance() {
		return SingletonHolder.instance;
	}

	private static class SingletonHolder {
		private static DataInstance instance = new DataInstance();		
	}

	private DataModel dataModel;

	private ResourceSet resourceSet;
	private URIConverter.Cipher cipher;

	/**
	 * Creates a new cipher, with the encryption set as defined
	 * @param encrypted
	 */
	public void createNewCipher(boolean encrypted){
		this.cipher = new AESCryptoCipher(128);
		((AESCryptoCipher) cipher).setEncrypted(encrypted);
	}

	/**
	 * Creates a new data instance.  To prevent initialization
	 * errors (if we access other components here which have
	 * not yet been initialized fully), we do not do anything
	 * here.  Instead, to initialize the data model, we call
	 * loadDataFile or newDataFile.
	 */
	private DataInstance(){
	}

	/**
	 * Creates a new data file with all the default data, and saves
	 * it in the given location.  This method does NOT check if the 
	 * given file already exists; you MUST check this before passing
	 * a file in, or else you may overwrite something else.
	 * 
	 * This method does NOT write the file to the Preferences.  The 
	 * calling code should do that, and save the new location, if
	 * desired.
	 * 
	 * @param locationFile The file to save to.  SHOULD not exist yet, 
	 * although we do not check that here.
	 */
	public void newDataFile(File locationFile){
		//If we are making a new file, we want to ask for encryption options again.
		this.cipher = new AESCryptoCipher(128);

		Accounts accounts = ModelFactory.eINSTANCE.createAccounts();
		Transactions transactions = ModelFactory.eINSTANCE.createTransactions();
		Categories categories = ModelFactory.eINSTANCE.createCategories();
		Types types = ModelFactory.eINSTANCE.createTypes();

		dataModel = ModelFactory.eINSTANCE.createDataModel();

		dataModel.setAllAccounts(accounts);
		dataModel.setAllTransactions(transactions);
		dataModel.setAllCategories(categories);
		dataModel.setAllTypes(types);

		//Default starting categories
		TranslateKeys[] expenseNames = {
				TranslateKeys.AUTO, 
				TranslateKeys.ENTERTAINMENT, 
				TranslateKeys.HOUSEHOLD, 
				TranslateKeys.GROCERIES, 
				TranslateKeys.INVESTMENT_EXPENSES, 
				TranslateKeys.MISC_EXPENSES, 
				TranslateKeys.UTILITIES
		};
		TranslateKeys[] incomeNames = {
				TranslateKeys.BONUS, 
				TranslateKeys.SALARY, 
				TranslateKeys.INVESTMENT_INCOME
		};

		for (TranslateKeys s : expenseNames){
			Category c = ModelFactory.eINSTANCE.createCategory();
			c.setName(s.toString());
			c.setBudgetedAmount(0);
			c.setIncome(false);
			categories.getCategories().add(c);
		}
		for (TranslateKeys s : incomeNames){
			Category c = ModelFactory.eINSTANCE.createCategory();
			c.setName(s.toString());
			c.setBudgetedAmount(0);
			c.setIncome(true);
			categories.getCategories().add(c);
		}

		//Default starting types - debit
		TranslateKeys[] debitNames = {
				TranslateKeys.CASH, 
				TranslateKeys.SAVINGS,
				TranslateKeys.CHEQUING,
				TranslateKeys.INVESTMENT				
		};

		for (TranslateKeys s : debitNames){
			TypeController.addType(s.toString(), false);
		}				

		//Default starting types - credit
		TranslateKeys[] creditNames = {
				TranslateKeys.LIABILITY,
				TranslateKeys.CREDIT_CARD,
				TranslateKeys.PREPAID_ACCOUNT,
				TranslateKeys.LINE_OF_CREDIT,
				TranslateKeys.LOAN
		};

		for (TranslateKeys s : creditNames){
			TypeController.addType(s.toString(), true);
		}				

		ResourceSet resourceSet = new ResourceSetImpl();			
		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put(
				Resource.Factory.Registry.DEFAULT_EXTENSION, new XMLResourceFactoryImpl());

		URI fileURI = URI.createFileURI(locationFile.toString());
		if (Const.DEVEL) Log.debug("Saving new file to " + locationFile.toString());
		Resource resource = resourceSet.createResource(fileURI);			
		resource.getContents().add(dataModel);

		Map options = new HashMap(1);
		options.put(Resource.OPTION_CIPHER, this.cipher);

		try{
			resource.save(options);
		}
		catch (IOException ioe){
			Log.warning(ioe);
		}
	}

	/**
	 * Loads the given datafile.
	 * @param locationFile Data file to load
	 * @param forceNewFile If true, create a new data file at the given location (overwriting
	 * the file currently there, if any).  If false, load the data file. 
	 */
	public void loadDataFile(File locationFile){
		// throw away the old cipher (if any) when we load a new data file
		this.cipher = new AESCryptoCipher(128);

		//Check that the file is not null.  Assuming the File Chooser
		// is working properly, this should never be null, but it's good 
		// to check it...
		if (locationFile == null 
				|| !locationFile.exists()){
			JOptionPane.showMessageDialog(
					null,
					Translate.getInstance().get(TranslateKeys.PROBLEM_READING_DATA_FILE_INTRO)
					+ ( locationFile == null ? locationFile : locationFile.getAbsolutePath() ) 
					+ Translate.getInstance().get(TranslateKeys.PROBLEM_READING_DATA_FILE_DIR_NOT_EXIST),
					Translate.getInstance().get(TranslateKeys.MISSING_DATA_FILE),
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		//Backup the file...
		try{
			//Use a rotating backup file, of form 'Data.X.buddi'  
			// The one with the smallest number X is the most recent.
			String fileBase = locationFile.getAbsolutePath().replaceAll(Const.DATA_FILE_EXTENSION + "$", "");
			for (int i = PrefsInstance.getInstance().getPrefs().getNumberOfBackups() - 2; i >= 0; i--){
				File tempBackupDest = new File(fileBase + "." + (i + 1) + Const.DATA_FILE_EXTENSION);
				File tempBackupSource = new File(fileBase + "." + i + Const.DATA_FILE_EXTENSION);
				if (tempBackupSource.exists()){
					FileFunctions.copyFile(tempBackupSource, tempBackupDest);
					if (Const.DEVEL) Log.debug("Moving " + tempBackupSource + " to " + tempBackupDest);
				}
			}
			File tempBackupDest = new File(fileBase + ".0" + Const.DATA_FILE_EXTENSION);
			FileFunctions.copyFile(locationFile, tempBackupDest);
			if (Const.DEVEL) Log.debug("Backing up file to " + tempBackupDest);
		}
		catch(IOException ioe){
			Log.emergency("Problem backing up data files when starting program: " + ioe);
		}

		try{
			// Create a resource set.
			resourceSet = new ResourceSetImpl();

			// Register the default resource factory
			resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put(
					Resource.Factory.Registry.DEFAULT_EXTENSION, new XMLResourceFactoryImpl());
			resourceSet.getLoadOptions().put(Resource.OPTION_CIPHER, this.cipher);

			// Register the package
			@SuppressWarnings("unused") 
			ModelPackage modelPackage = ModelPackage.eINSTANCE;

			// Get the URI of the model file.
			URI fileURI = URI.createFileURI(locationFile.getAbsolutePath());

			Resource resource = resourceSet.getResource(fileURI, true);

			// Print the contents of the resource to System.out.
			EList contents = resource.getContents();
			if (contents.size() > 0){
				this.dataModel = (DataModel) contents.get(0);
			}

			// Once we have this loaded, we need to do some sanity checks.
			// After a few versions, these can be phased out.

			// Updates the scheduled transaction frequency type to new (2.2) version.
			// Added December 6 2006, in version 2.1.3; we can probably
			// remove it after version 2.4 or so...
			for (Schedule s : ScheduleController.getScheduledTransactions()) {
				if (s.getFrequencyType().equals("WEEK")){
					s.setFrequencyType(TranslateKeys.WEEKLY.toString());
					Log.notice("Changing schedule frequency from WEEK to " + TranslateKeys.WEEKLY + " for " + s.getScheduleName());
				}
				if (s.getFrequencyType().equals("MONTH")){
					s.setFrequencyType(TranslateKeys.MONTHLY_BY_DATE.toString());
					s.setScheduleDay(s.getScheduleDay() + 1); //Before, we had the first of the month represented by 0.  Now it is 1.
					Log.notice("Changing schedule frequency from MONTH to " + TranslateKeys.MONTHLY_BY_DATE + " for " + s.getScheduleName());
				}
				//This check is for those who have been following the Dev
				// branch.  When you ran 2.1.3, it upgraded from MONTH
				// to MONTHLY_BY_DATE, but it did not convert the date
				// to the new format.  This means that if you had the date
				// set to 1, it would try to set it to 0, which would not
				// work.  This check will fix it.
				if (s.getFrequencyType().equals(TranslateKeys.MONTHLY_BY_DATE.toString())){
					if (s.getScheduleDay() == 0)
						s.setScheduleDay(1);
				}
			}

			//Check that there are no scheduled transactions which should be happening...
			ScheduleController.checkForScheduledActions();		

			//Make sure that this is updated when we load...
			calculateAllBalances();
			
			saveDataFile();

		}
		//If there is a problem opening the file, we will pass control to 
		// the New / Open dialog to choose a new one.
		catch (Exception e){
			JOptionPane.showMessageDialog(
					null,
					Translate.getInstance().get(TranslateKeys.PROBLEM_READING_DATA_FILE_INTRO)
					+ e.getMessage(),
					Translate.getInstance().get(TranslateKeys.ERROR),
					JOptionPane.ERROR_MESSAGE);

			Buddi.chooseDataFileSource();
		}
	}

	/**
	 * Saves the data model in XML format to the default location currently stored in the preferences.
	 */
	public void saveDataFile(){
		try {
			saveDataFile(PrefsInstance.getInstance().getPrefs().getDataFile());
		}
		catch (IOException ioe){
			Log.critical("Error while saving data file: " + ioe);
		}
	}

	/**
	 * Saves the data model in XML format to the given location.
	 * @param location Location to save the date model to.
	 */
	synchronized public void saveDataFile(final String location) throws IOException {
		File saveLocation = new File(location);
		File backupLocation = new File(saveLocation.getAbsolutePath() + "~");

		if (saveLocation.exists())
			FileFunctions.copyFile(saveLocation, backupLocation);

		URI fileURI = URI.createFileURI(saveLocation.getAbsolutePath());

		if (Const.DEVEL) Log.debug("Data saved to " + location);

		Resource resource = resourceSet.createResource(fileURI);

		resource.getContents().add(getDataModel());

		Map options = new HashMap(1);
		options.put(Resource.OPTION_CIPHER, this.cipher);

		resource.save(options);
	}


	/**
	 * Returns the date model instance.  Useful for doing low level operations
	 * for which there is no helper function in this class.  Note that when 
	 * modifying the data model directly, there are generally no safeguards.
	 * Be sure to verify your input, at the risk of corrupting the model
	 * and losing all data!
	 * @return
	 */
	public DataModel getDataModel(){
		return dataModel;
	}

//	/**
//	* Returns an instance of the data model factory, for use in 
//	* creating different model objects.
//	* @return
//	*/
//	public ModelFactory getDataModelFactory(){
//	return dataModelFactory;
//	}

	/**
	 * Forces an update of all the balances.  This O(n) operation can
	 * take a while with large models; use sparingly if possible
	 */
	public void calculateAllBalances(){
		for (Account a : SourceController.getAccounts()){
			a.calculateBalance();
		}
	}
}