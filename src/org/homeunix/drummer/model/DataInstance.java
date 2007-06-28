/*
 * Created on May 12, 2006 by wyatt
 */
package org.homeunix.drummer.model;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.URIConverter;
import org.eclipse.emf.ecore.resource.URIConverter.Cipher;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMLResourceFactoryImpl;
import org.homeunix.drummer.Const;
import org.homeunix.drummer.controller.ReturnCodes;
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
	
	//Provide temporary backing for the autocomplete text fields
	private final DefaultComboBoxModel autocompleteEntries;
	private final Map<String, AutoSaveInfo> defaultsMap;
	
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
		autocompleteEntries = new DefaultComboBoxModel();
		defaultsMap = new HashMap<String, AutoSaveInfo>();
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
	public ReturnCodes newDataFile(File locationFile){
		//Keep backups of existing data
		Cipher oldCipher = this.cipher;
		DataModel oldDataModel = this.dataModel;
		ResourceSet oldResourceSet = this.resourceSet;
		
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
		dataModel.setAllLists(ModelFactory.eINSTANCE.createLists());


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

		initResourceSet();

//		ResourceSet resourceSet = new ResourceSetImpl();			
//		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put(
//		Resource.Factory.Registry.DEFAULT_EXTENSION, new XMLResourceFactoryImpl());

		URI fileURI = URI.createFileURI(locationFile.toString());
		if (Const.DEVEL) Log.debug("Saving new file to " + locationFile.toString());
		Resource resource = resourceSet.createResource(fileURI);			
		resource.getContents().add(dataModel);

		Map options = new HashMap(1);
		options.put(Resource.OPTION_CIPHER, this.cipher);

		try{
			resource.save(options);
			return ReturnCodes.SUCCESS;
		}
		catch (IOException ioe){
			Log.warning(ioe);
		}
		
		//In the event of an error, we will restore the data 
		// to its previous state.
		this.dataModel = oldDataModel;
		this.cipher = oldCipher;
		this.resourceSet = oldResourceSet;
		
		return ReturnCodes.ERROR;
	}

	/**
	 * Loads the given datafile.
	 * @param locationFile Data file to load
	 * @param forceNewFile If true, create a new data file at the given 
	 * location (overwriting the file currently there, if any).  
	 * If false, load the data file.
	 * @return True if the file is successfully loaded, false otherwise. 
	 */
	public ReturnCodes loadDataFile(File locationFile){
		//Backup existing data
		Cipher oldCipher = this.cipher;
		DataModel oldDataModel = this.dataModel;
		ResourceSet oldResourceSet = this.resourceSet;
		
		// throw away the old cipher (if any) when we load a new data file
		this.cipher = new AESCryptoCipher(128);

		//Check that the file is not null.  Assuming the File Chooser
		// is working properly, this should never be null, but it's good 
		// to check it...
		if (locationFile == null 
				|| !locationFile.exists()){
			String[] options = new String[1];
			options[0] = Translate.getInstance().get(TranslateKeys.BUTTON_OK);

			JOptionPane.showOptionDialog(
					null,
					Translate.getInstance().get(TranslateKeys.MESSAGE_ERROR_READING_FILE)
					+ "\n"
					+ ( locationFile == null ? locationFile : locationFile.getAbsolutePath() )
					+ "\n"
					+ Translate.getInstance().get(TranslateKeys.MESSAGE_ERROR_READING_FILE_NOT_EXIST),
					Translate.getInstance().get(TranslateKeys.MISSING_DATA_FILE),
					JOptionPane.DEFAULT_OPTION,
					JOptionPane.ERROR_MESSAGE,
					null,
					options,
					options[0]);
			return ReturnCodes.ERROR;
		}


		try{
			initResourceSet();

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
		}
		catch (Exception e){
			//This generally means that the password was incorrect,
			// although it could also be used for other errors.
			// In any case, we cannot open the file.
			if (Const.DEVEL) Log.debug("DataInstance.loadDataFile() failure");
			
			//We also have to return the data instance to the old
			// state.
			this.cipher = oldCipher;
			this.dataModel = oldDataModel;
			this.resourceSet = oldResourceSet;
			
			//TODO Is there ever a time when we get to this stage
			// without the user hitting cancel on the password
			// box?  If so, we should check for it, and return
			// an error code instead of a cancel code,
			// so that we can display the correct error message.
			return ReturnCodes.CANCEL;
		}

		//Backup the file, now that we know it is good...
		try{
			//Use a rotating backup file, of form 'Data.X.buddi'  
			// The one with the smallest number X is the most recent.
			String fileBase = locationFile.getAbsolutePath().replaceAll(Const.DATA_FILE_EXTENSION + "$", "");
			for (int i = PrefsInstance.getInstance().getPrefs().getNumberOfBackups() - 2; i >= 0; i--){
				File tempBackupDest = new File(fileBase + "." + (i + 1) + Const.BACKUP_FILE_EXTENSION);
				File tempBackupSource = new File(fileBase + "." + i + Const.BACKUP_FILE_EXTENSION);
				if (tempBackupSource.exists()){
					FileFunctions.copyFile(tempBackupSource, tempBackupDest);
					if (Const.DEVEL) Log.debug("Moving " + tempBackupSource + " to " + tempBackupDest);
				}
			}
			File tempBackupDest = new File(fileBase + ".0" + Const.BACKUP_FILE_EXTENSION);
			FileFunctions.copyFile(locationFile, tempBackupDest);
			if (Const.DEVEL) Log.debug("Backing up file to " + tempBackupDest);
		}
		catch(IOException ioe){
			Log.emergency("Problem backing up data files when starting program: " + ioe);
		}


		// Once we have this loaded, we need to do some sanity checks.
		// After a few versions, these can be phased out.  The dates
		// and descriptions should be included in each group.
		for (Schedule s : ScheduleController.getScheduledTransactions()) {

			// Updates the scheduled transaction frequency type to new (2.2) version.
			// Added December 6 2006, in version 2.1.3; we can probably
			// remove it after version 2.4 or so...
			if (s.getFrequencyType().equals("WEEK")){
				s.setFrequencyType(TranslateKeys.SCHEDULE_FREQUENCY_WEEKLY.toString());
				Log.notice("Changing schedule frequency from WEEK to " + TranslateKeys.SCHEDULE_FREQUENCY_WEEKLY + " for " + s.getScheduleName());
			}
			if (s.getFrequencyType().equals("MONTH")){
				s.setFrequencyType(TranslateKeys.SCHEDULE_FREQUENCY_MONTHLY_BY_DATE.toString());
				s.setScheduleDay(s.getScheduleDay() + 1); //Before, we had the first of the month represented by 0.  Now it is 1.
				Log.notice("Changing schedule frequency from MONTH to " + TranslateKeys.SCHEDULE_FREQUENCY_MONTHLY_BY_DATE + " for " + s.getScheduleName());
			}
			
			
			//Updates to the new frequency names in 2.3.x.  Added May 2 2007,
			// we should probably leave it in until at least version
			// 2.8 or later.
			if (s.getFrequencyType().equals("MONTHLY_BY_DATE")) {
				s.setFrequencyType(TranslateKeys.SCHEDULE_FREQUENCY_MONTHLY_BY_DATE.toString());
				Log.notice("Changing schedule frequency from MONTHLY_BY_DATE to "
						+ TranslateKeys.SCHEDULE_FREQUENCY_MONTHLY_BY_DATE + " for "
						+ s.getScheduleName());
			}
			if (s.getFrequencyType().equals("BIWEEKLY")) {
				s.setFrequencyType(TranslateKeys.SCHEDULE_FREQUENCY_BIWEEKLY.toString());
				Log.notice("Changing schedule frequency from BIWEEKLY to "
						+ TranslateKeys.SCHEDULE_FREQUENCY_BIWEEKLY + " for "
						+ s.getScheduleName());
			}
			if (s.getFrequencyType().equals("EVERY_DAY")) {
				s.setFrequencyType(TranslateKeys.SCHEDULE_FREQUENCY_EVERY_DAY.toString());
				Log.notice("Changing schedule frequency from EVERY_DAY to "
						+ TranslateKeys.SCHEDULE_FREQUENCY_EVERY_DAY + " for "
						+ s.getScheduleName());
			}
			if (s.getFrequencyType().equals("EVERY_WEEKDAY")) {
				s.setFrequencyType(TranslateKeys.SCHEDULE_FREQUENCY_EVERY_WEEKDAY.toString());
				Log.notice("Changing schedule frequency from EVERY_WEEKDAY to "
						+ TranslateKeys.SCHEDULE_FREQUENCY_EVERY_WEEKDAY + " for "
						+ s.getScheduleName());
			}
			if (s.getFrequencyType().equals("MONTHLY_BY_DAY_OF_WEEK")) {
				s.setFrequencyType(TranslateKeys.SCHEDULE_FREQUENCY_MONTHLY_BY_DAY_OF_WEEK.toString());
				Log.notice("Changing schedule frequency from MONTHLY_BY_DAY_OF_WEEK to "
						+ TranslateKeys.SCHEDULE_FREQUENCY_MONTHLY_BY_DAY_OF_WEEK + " for "
						+ s.getScheduleName());
			}
			if (s.getFrequencyType().equals("MULTIPLE_MONTHS_EVERY_YEAR")) {
				s.setFrequencyType(TranslateKeys.SCHEDULE_FREQUENCY_MULTIPLE_MONTHS_EVERY_YEAR.toString());
				Log.notice("Changing schedule frequency from MULTIPLE_MONTHS_EVERY_YEAR to "
						+ TranslateKeys.SCHEDULE_FREQUENCY_MULTIPLE_MONTHS_EVERY_YEAR + " for "
						+ s.getScheduleName());
			}
			if (s.getFrequencyType().equals("MULTIPLE_WEEKS_EVERY_MONTH")) {
				s.setFrequencyType(TranslateKeys.SCHEDULE_FREQUENCY_MULTIPLE_WEEKS_EVERY_MONTH.toString());
				Log.notice("Changing schedule frequency from MULTIPLE_WEEKS_EVERY_MONTH to "
						+ TranslateKeys.SCHEDULE_FREQUENCY_MULTIPLE_WEEKS_EVERY_MONTH + " for "
						+ s.getScheduleName());
			}
			if (s.getFrequencyType().equals("WEEKLY")) {
				s.setFrequencyType(TranslateKeys.SCHEDULE_FREQUENCY_WEEKLY.toString());
				Log.notice("Changing schedule frequency from WEEKLY to "
						+ TranslateKeys.SCHEDULE_FREQUENCY_WEEKLY + " for "
						+ s.getScheduleName());
			}

			
			//This check is for those who have been following the Dev
			// branch.  When you ran 2.1.3, it upgraded from MONTH
			// to MONTHLY_BY_DATE, but it did not convert the date
			// to the new format.  This means that if you had the date
			// set to 1, it would try to set it to 0, which would not
			// work.  This check will fix it.
			// We should be able to remove this sometime around 2.6 or so.
			if (s.getFrequencyType().equals(TranslateKeys.SCHEDULE_FREQUENCY_MONTHLY_BY_DATE.toString())){
				if (s.getScheduleDay() == 0)
					s.setScheduleDay(1);
			}
		}

		//Check for the auto complete entries - if they don't exist yet, we create it.
		if (dataModel.getAllLists() == null)
			dataModel.setAllLists(ModelFactory.eINSTANCE.createLists());
				
		//Load the auto complete entries into the dictionary
		// and create the autocomplete map, between 
		// description and other fields.
//		autocompleteEntries.addElement(null);
		for (Object o : dataModel.getAllLists().getAllAutoSave()) {
			if (o instanceof AutoSaveInfo) {
				AutoSaveInfo entry = (AutoSaveInfo) o;
				autocompleteEntries.addElement(entry.getDescription());
				defaultsMap.put(entry.getDescription(), entry);
			}
		}
		
		//Check that there are no scheduled transactions which should be happening...
		ScheduleController.checkForScheduledActions();		

		//Make sure that this is updated when we load...
		SourceController.calculateAllBalances();

		return ReturnCodes.SUCCESS;
	}

	/**
	 * Saves the data model in XML format to the default location currently stored in the preferences.
	 */
	public ReturnCodes saveDataFile(){
		ReturnCodes returnCode = ReturnCodes.INITIAL;
		
		try {
			returnCode = saveDataFile(PrefsInstance.getInstance().getPrefs().getDataFile());
		}
		catch (IOException ioe){
			Log.critical("Error while saving data file: " + ioe);
			returnCode = ReturnCodes.ERROR;
		}
		
		return returnCode;
	}

	/**
	 * Saves the data model in XML format to the given location.
	 * @param location Location to save the date model to.
	 */
	synchronized public ReturnCodes saveDataFile(final String location) throws IOException {
		if (getDataModel() != null){
			
//			//Translate between Set<String> (the dictionary) and the persistence model
//			getDataModel().getAllLists().getAllAutoSave().retainAll(new Vector());
//			for (int i = 0; i < autocompleteEntries.getSize(); i++) {
//				if (autocompleteEntries.getElementAt(i) != null){
//					String s = autocompleteEntries.getElementAt(i).toString();
//					AutoSaveInfo asi = ModelFactory.eINSTANCE.createAutoSaveInfo();
//					asi.setDescription(s);
//					asi.setAmount(defaultsMap.get(s).getAmount());
//					asi.setFrom(defaultsMap.get(s).getFrom());
//					asi.setTo(defaultsMap.get(s).getTo());
//					asi.setMemo(defaultsMap.get(s).getMemo());
//					asi.setNumber(defaultsMap.get(s).getNumber());
//					getDataModel().getAllLists().getAllAutoSave().add(asi);
//				}
//			}

			
			File saveLocation = new File(location);
			File backupLocation = new File(saveLocation.getAbsolutePath() + "~");

			if (saveLocation.exists())
				FileFunctions.copyFile(saveLocation, backupLocation);

			URI fileURI = URI.createFileURI(saveLocation.getAbsolutePath());

			if (Const.DEVEL) Log.debug("Data saved to " + fileURI);
			if (resourceSet == null)
				initResourceSet();

			Resource resource = resourceSet.createResource(fileURI);

			resource.getContents().add(getDataModel());

			Map options = new HashMap(1);
			options.put(Resource.OPTION_CIPHER, this.cipher);

			resource.save(options);
			
			return ReturnCodes.SUCCESS;
		}
		
		return ReturnCodes.ERROR;
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

	private void initResourceSet(){
		// Create a resource set.
		resourceSet = new ResourceSetImpl();

		// Register the default resource factory
		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put(
				Resource.Factory.Registry.DEFAULT_EXTENSION, new XMLResourceFactoryImpl());
		resourceSet.getLoadOptions().put(Resource.OPTION_CIPHER, this.cipher);
	}
	
	public DefaultComboBoxModel getAutoCompleteEntries() {
		return autocompleteEntries;
	}
	
	public void setAutoCompleteEntry(String description, String number, long amount, String from, String to, String memo){
		AutoSaveInfo asi;
		if (defaultsMap.get(description) == null){
			asi = ModelFactory.eINSTANCE.createAutoSaveInfo();
			asi.setDescription(description);
			asi.setNumber(number);
			asi.setAmount(amount);
			asi.setFrom(from);
			asi.setTo(to);
			asi.setMemo(memo);
			getDataModel().getAllLists().getAllAutoSave().add(asi);
			defaultsMap.put(description, asi);
			autocompleteEntries.addElement(description);
			
			//Sort the autocomplete entries
			List<String> entries = new LinkedList<String>(defaultsMap.keySet());
			Collections.sort(entries);
			autocompleteEntries.removeAllElements();
			for (String string : entries) {
				autocompleteEntries.addElement(string);	
			}
		}
		else {
			asi = defaultsMap.get(description);
			asi.setDescription(description);
			asi.setNumber(number);
			asi.setAmount(amount);
			asi.setFrom(from);
			asi.setTo(to);
			asi.setMemo(memo);			
		}
	}

	public AutoSaveInfo getAutoCompleteEntry(String description){
		return defaultsMap.get(description);
	}
}