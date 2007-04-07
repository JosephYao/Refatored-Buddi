/*
 * Created on May 12, 2006 by wyatt
 */
package org.homeunix.drummer.model;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import javax.swing.JOptionPane;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.URIConverter;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMLResourceFactoryImpl;
import org.homeunix.drummer.Const;
import org.homeunix.drummer.controller.Translate;
import org.homeunix.drummer.controller.TranslateKeys;
import org.homeunix.drummer.model.impl.ModelFactoryImpl;
import org.homeunix.drummer.model.util.AESCryptoCipher;
import org.homeunix.drummer.prefs.PrefsInstance;
import org.homeunix.thecave.moss.util.DateUtil;
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
	private ModelFactory dataModelFactory = ModelFactoryImpl.eINSTANCE;

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
	 * Creates a new data instance.
	 * 
	 * We follow the following steps when deciding if we create a new
	 * file or open an existing one:
	 * 
	 *  1) If the user wants to be prompted, prompt them.  If they hit 
	 *  cancel here, exit.
	 *  2) If there is a file in Preferences, try loading that one.
	 *  3) If there is any problem with the above, prompt the user.
	 *  4) If the user hits cancel from here, quit.
	 */
	private DataInstance(){
//		File dataFile = null;
//
//		//This option allows us to prompt for a new data file at startup every time
//		if (PrefsInstance.getInstance().getPrefs().isPromptForFileAtStartup()){
//			int i = PrefsInstance.getInstance().chooseNewOrExistingDataFile();
//			if (i == 0){
//				Log.info("User hit cancel when loading file.  Exiting.");
//				System.exit(0);
//			}
//			else if (i == 1){
//				new
//			}
//		}
//		//If the data file is not null, we try to use it.
//		else if (PrefsInstance.getInstance().getPrefs().getDataFile() != null){
//			dataFile = new File(PrefsInstance.getInstance().getPrefs().getDataFile());
//
//			//Does the file exist?
//			if (!dataFile.exists()){
//				PrefsInstance.getInstance().chooseNewOrExistingDataFile();
//			}
//			//Before we open the file, we check that we have read / write 
//			// permission to it.  This is in response to bug #1626996. 
//			else if (!dataFile.canWrite()){
//				JOptionPane.showMessageDialog(
//						null,
//						Translate.getInstance().get(TranslateKeys.CANNOT_WRITE_DATA_FILE),
//						Translate.getInstance().get(TranslateKeys.ERROR),
//						JOptionPane.ERROR_MESSAGE);
//				PrefsInstance.getInstance().chooseNewOrExistingDataFile();
//			}
//			else if (!dataFile.canRead()){
//				JOptionPane.showMessageDialog(
//						null,
//						Translate.getInstance().get(TranslateKeys.CANNOT_READ_DATA_FILE),
//						Translate.getInstance().get(TranslateKeys.ERROR),
//						JOptionPane.ERROR_MESSAGE);
//				PrefsInstance.getInstance().chooseNewOrExistingDataFile();
//			}
//			//If all looks well, we try to load the file.
//			else {
//				loadDataFile(dataFile);
//			}
//		}
//		//If the data file was null, we need to have the user pick a different one.
//		else {
//			if (!PrefsInstance.getInstance().chooseNewOrExistingDataFile()){
//				Log.info("User hit cancel when loading file.  Exiting.");
//				System.exit(0);
//			}
//		}
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

		Accounts accounts = getDataModelFactory().createAccounts();
		Transactions transactions = getDataModelFactory().createTransactions();
		Categories categories = getDataModelFactory().createCategories();
		Types types = getDataModelFactory().createTypes();

		dataModel = getDataModelFactory().createDataModel();

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
			Category c = getDataModelFactory().createCategory();
			c.setName(s.toString());
			c.setBudgetedAmount(0);
			c.setIncome(false);
			categories.getCategories().add(c);
		}
		for (TranslateKeys s : incomeNames){
			Category c = getDataModelFactory().createCategory();
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
			Type t = dataModelFactory.createType();
			t.setName(s.toString());
			t.setCredit(false);
			dataModel.getAllTypes().getTypes().add(t);
//			addType(s.toString(), false);
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
			Type t = dataModelFactory.createType();
			t.setName(s.toString());
			t.setCredit(true);
			dataModel.getAllTypes().getTypes().add(t);
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
			Log.emergency("Failure when attempting to backup when starting program: " + ioe);
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
			for (Schedule s : getScheduledTransactions()) {
				if (s.getFrequencyType().equals("WEEK")){
					s.setFrequencyType(TranslateKeys.WEEKLY.toString());
					Log.notice("Changing schedule frequency from WEEK to " + TranslateKeys.WEEKLY + " for " + s.getScheduleName());
					saveDataModel();
				}
				if (s.getFrequencyType().equals("MONTH")){
					s.setFrequencyType(TranslateKeys.MONTHLY_BY_DATE.toString());
					s.setScheduleDay(s.getScheduleDay() + 1); //Before, we had the first of the month represented by 0.  Now it is 1.
					Log.notice("Changing schedule frequency from MONTH to " + TranslateKeys.MONTHLY_BY_DATE + " for " + s.getScheduleName());
					saveDataModel();
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
					saveDataModel();
				}
			}
			
			//Check that there are no scheduled transactions which should be happening...
			checkForScheduledActions();		

			//Make sure that this is updated when we load...
			calculateAllBalances();

		}
		//If there is a problem opening the file, we will pass control to 
		// the New / Open dialog in PrefsInstance
		catch (Exception e){
			JOptionPane.showMessageDialog(
					null,
					Translate.getInstance().get(TranslateKeys.PROBLEM_READING_DATA_FILE_INTRO)
					+ e.getMessage(),
					Translate.getInstance().get(TranslateKeys.ERROR),
					JOptionPane.ERROR_MESSAGE);

//			PrefsInstance.getInstance().chooseNewOrExistingDataFile();
		}
	}

	/**
	 * Saves the data model in XML format to the default location currently stored in the preferences.
	 */
	public void saveDataModel(){
		saveDataModel(PrefsInstance.getInstance().getPrefs().getDataFile());
	}

	/**
	 * Saves the data model in XML format to the given location.
	 * @param location Location to save the date model to.
	 */
	synchronized public void saveDataModel(final String location){
//		new SwingWorker(){
//		@Override
//		public Object construct() {
		File saveLocation = new File(location);
		File backupLocation = new File(saveLocation.getAbsolutePath() + "~");

		try{
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
		catch (IOException ioe){
			Log.critical("Error when saving file: " + ioe);
		}
//		return null;
//		}			
//		}.start();
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

	/**
	 * Returns an instance of the data model factory, for use in 
	 * creating different model objects.
	 * @return
	 */
	public ModelFactory getDataModelFactory(){
		return dataModelFactory;
	}

	/**
	 * Forces an update of all the balances.  This O(n) operation can
	 * take a while with large models; use sparingly if possible
	 */
	public void calculateAllBalances(){
		for (Account a : getAccounts()){
			a.calculateBalance();
		}
	}

	/**
	 * Adds the specified account to the model.  This should be already 
	 * created using the ModelFactory.
	 * @param a Account to add
	 */
	public void addAccount(Account a){
		getDataModel().getAllAccounts().getAccounts().add(a);
		saveDataModel();
	}

	/**
	 * Adds the specified category to the model.  This should be already 
	 * created using the ModelFactory.
	 * @param c Category to add
	 */
	public void addCategory(Category c){
		getDataModel().getAllCategories().getCategories().add(c);
		saveDataModel();
	}

	/**
	 * Adds a new type to the data model.  This type is what will show
	 * up in the New Account window.
	 * @param name The user-readable name (or the translation key, if it is a translated term)
	 * @param credit Is this a Credit type? 
	 */
//	public void addType(String name, boolean credit){
//		Type t = getDataModelFactory().createType();
//		t.setName(name);
//		t.setCredit(credit);
//		getDataModel().getAllTypes().getTypes().add(t);
//		saveDataModel();
//	}

	/**
	 * Do not call this method unless you know what you are doing!  If you
	 * are writing plugin code, the method you probably want is 
	 * TransactionsFrame.addToTransactionListModel().  This will add the
	 * transaction, and automatically update all open windows.
	 * @param t
	 */
	public void addTransaction(Transaction t){
		getDataModel().getAllTransactions().getTransactions().add(t);
		t.calculateBalance();		
		saveDataModel();
	}


	/**
	 * Deletes the given source by setting the deleted flag to true.  If 
	 * you are writing a plugin which must delete a source (why, I don't 
	 * know, but there may be some situation in which this is desired),
	 * you are probably better off using this method.  This method can
	 * be undone with the undeleteSource() method. 
	 * @param s
	 */
	public void deleteSource(Source s){
		s.setDeleted(true);
		if (s instanceof Category) {
			Category c = (Category) s;
			for (Object o : c.getChildren()) {
				if (o instanceof Category) {
					Category child = (Category) o;
					deleteSource(child);
				}
			}
		}
		saveDataModel();
	}

	/**
	 * Permanently removes the source from the model.  If the source has
	 * children, recursively remove them too.  THIS IS A POTENTIALLY
	 * DANGEROUS OPERATION!  This method does not check for sanity of the
	 * data model after removing the sources.  If there are transactions
	 * or other objects which reference this source, it WILL CORRUPT THE
	 * DATA MODEL.  
	 * 
	 * Plugin writers: it is very unlikely that this is the method that you
	 * want to call.  A much safer method is deleteSource(), which only
	 * marks the sources as deleted, and does not remove them from the 
	 * model completely.
	 * @param s
	 */
	public void deleteSourcePermanent(Source s){
		if (s instanceof Category) {
			Category c = (Category) s;
			if (c.getParent() != null){
				c.getParent().getChildren().remove(c);
			}
			for (Object o: c.getChildren()) {
				if (o instanceof Category){
					Category child = (Category) o;
					deleteSourcePermanent(child);
				}
			}
			getDataModel().getAllCategories().getCategories().remove(c);
		}
		else if (s instanceof Account) {
			Account a = (Account) s;
			getDataModel().getAllAccounts().getAccounts().remove(a);
		}
		saveDataModel();
	}

	/**
	 * Do not call this method unless you know what you are doing!  If you
	 * are writing plugin code, the method you probably want is 
	 * TransactionsFrame.removeFromTransactionListModel().  This will 
	 * delete the transaction, and automatically update all open windows.
	 * @param t
	 */
	public void deleteTransaction(Transaction t){
		if (getDataModel().getAllTransactions().getTransactions().remove(t)){
			t.calculateBalance();
			saveDataModel();
		}
		else{
			Log.warning("Could not delete transaction: could not find orignal in data store");
		}
	}

	/**
	 * Sets the Deleted flag of the given source to false.
	 * @param s
	 */
	public void undeleteSource(Source s){
		s.setDeleted(false);
		if (s instanceof Category) {
			Category c = (Category) s;
			for (Object o : c.getChildren()) {
				if (o instanceof Category) {
					Category child = (Category) o;
					undeleteSource(child);
				}
			}
		}
		saveDataModel();
	}

	/**
	 * Returns all accounts in the model, sorted according to the Account comparator
	 * @return All accounts in the currently loaded data model
	 */
	public Vector<Account> getAccounts(){
		Vector<Account> v = new Vector<Account>(getDataModel().getAllAccounts().getAccounts());
		Collections.sort(v);
		return v;
	}

	/**
	 * Returns all categories in the model, sorted according to the Category comparator
	 * @return All categories in the currently loaded data model
	 */
	public Vector<Category> getCategories(){
		Vector<Category> v = new Vector<Category>(getDataModel().getAllCategories().getCategories());
		Collections.sort(v);
		return v;
	}

	/**
	 * Returns all types in the model, sorted according to the Type comparator
	 * @return All types in the currently loaded data model
	 */
	public Vector<Type> getTypes(){
		Vector<Type> v = new Vector<Type>(getDataModel().getAllTypes().getTypes());
		Collections.sort(v);
		return v;
	}

	/**
	 * Returns all transactions in the model, sorted according to the Transaction comparator
	 * @return All transactions in the currently loaded data model
	 */
	public Vector<Transaction> getTransactions(){
		Vector<Transaction> transactions = new Vector<Transaction>(getDataModel().getAllTransactions().getTransactions());
		Collections.sort(transactions);
		return transactions;
	}

	/**
	 * Returns all transactions which go to or from a given source.
	 * @param source
	 * @return
	 */
	public Vector<Transaction> getTransactions(Source source){
		Vector<Transaction> v = new Vector<Transaction>();

		for (Transaction transaction : getTransactions()) {
			if (transaction.getFrom() != null && transaction.getTo() != null && 
					(transaction.getFrom().equals(source) || transaction.getTo().equals(source)))
				v.add(transaction);
		}

		Collections.sort(v);		
		return v;
	}

	/**
	 * Returns all transactions within a given time frame.  Must match all
	 * given time arguments; set an argument to null to ignore
	 * @param source
	 * @param year
	 * @param month
	 * @param day
	 * @return
	 */
	public Vector<Transaction> getTransactions(Source source, Integer year, Integer month, Integer dayOfMonth){
		Vector<Transaction> transactions = getTransactions(source);
		Vector<Transaction> v = new Vector<Transaction>();

		for (Transaction t : transactions) {
			Calendar c = Calendar.getInstance();
			c.setTime(t.getDate());
			if ((year == null || year == c.get(Calendar.YEAR))
					&& (month == null || month == c.get(Calendar.MONTH))
					&& (dayOfMonth == null || dayOfMonth == c.get(Calendar.DAY_OF_MONTH))){
				v.add(t);
			}
		}

		Collections.sort(v);
		return v;
	}

	/**
	 * Returns all transactions which meet the given criteria
	 * @param description Only return transactions matching this description
	 * @param startDate Only return transactions happening after this
	 * @param endDate Only return transactions happening before this
	 * @return
	 */
	public Vector<Transaction> getTransactions(String description, Date startDate, Date endDate){
		Vector<Transaction> allTransactions = getTransactions(startDate, endDate);
		Vector<Transaction> transactions = new Vector<Transaction>();

		for (Transaction transaction : allTransactions) {
			if (transaction.getDescription().equals(description))
				transactions.add(transaction);
		}

		return transactions;
	}

	/**
	 * Returns all transactions which meet the given criteria
	 * @param isIncome Does the transaction represent income?
	 * @param startDate Only return transactions happening after this
	 * @param endDate Only return transactions happening before this
	 * @return
	 */
	public Vector<Transaction> getTransactions(Boolean isIncome, Date startDate, Date endDate){
		Vector<Transaction> allTransactions = getTransactions(startDate, endDate);
		Vector<Transaction> transactions = new Vector<Transaction>();

		for (Transaction transaction : allTransactions) {
			Category c = null;
			if (transaction.getFrom() instanceof Category)
				c = (Category) transaction.getFrom();
			else if (transaction.getTo() instanceof Category)
				c = (Category) transaction.getTo();

			if (c != null && c.isIncome() == isIncome){
				transactions.add(transaction);
			}
		}

		return transactions;
	}

	//TODO Test boundary conditions: does this overlap dates or not?
	/**
	 * Return all transactions related to the given source which are 
	 * between startDate and endDate
	 * @param source Returned transactions must be either to or from this source
	 * @param startDate Only return transactions happening after this
	 * @param endDate Only return transactions happening before this
	 * @return
	 */
	public Vector<Transaction> getTransactions(Source source, Date startDate, Date endDate){
		Vector<Transaction> transactions = getTransactions(source);
		Vector<Transaction> v = new Vector<Transaction>();

		for (Transaction t : transactions) {
			if ((t.getDate().after(startDate) || t.getDate().equals(startDate)) 
					&& (t.getDate().before(endDate) || t.getDate().equals(endDate))){
				v.add(t);
			}
		}

		Collections.sort(v);
		return v;
	}

	//TODO Test boundary conditions: does this overlap dates or not?
	// Update - I think that is should be working....
	/**
	 * Returns all transactions between start and end
	 * @param startDate Only return transactions happening after this
	 * @param endDate Only return transactions happening before this
	 * @return
	 */
	public Vector<Transaction> getTransactions(Date startDate, Date endDate){
		Vector<Transaction> transactions = getTransactions();
		Vector<Transaction> v = new Vector<Transaction>();

		for (Transaction t : transactions) {
			if ((t.getDate().after(startDate) || t.getDate().equals(startDate)) 
					&& (t.getDate().before(endDate) || t.getDate().equals(endDate))){
				v.add(t);
			}
		}

		Collections.sort(v);
		return v;
	}

	/**
	 * Returns all scheduled transactions
	 * @return A vector of all scheduled transactions
	 */
	public Vector<Schedule> getScheduledTransactions(){
		Vector<Schedule> v = new Vector<Schedule>(dataModel.getAllTransactions().getScheduledTransactions());
		Collections.sort(v);
		//System.out.println(v);
		return v;
	}


	/**
	 * Adds a new schedule to the model.  Since there are a number of fields
	 * which MUST be set, we prompt for all of them, instead of relying on the
	 * calling code to set them all.
	 * 
	 * The required objects for this are not obvious at first, and may
	 * require some interpretation.  Plugin developers: if you need to
	 * use this method, check the offical Buddi code which calls it
	 * to see the correct methods and values. 
	 * 
	 * @param name Human readable name (which will show up in the list)
	 * @param startDate Date which the schedule is effective
	 * @param endDate Date which the schedule expires (not currently used)
	 * @param frequencyType The type of frequency.  The options for this are 
	 * in TranslateKeys; unfortunately, there is no good way of checking which is 
	 * used for FrequencyType and which is not.  You can look in MainBuddiFrame.updateScheduledTransactions()
	 * for a list of which ones we check for.
	 * @param scheduleDay The day the schedule is one.  The meanings of the values 
	 * for this will differ depending on frequencyType.
	 * @param scheduleWeek Used for multiple weeks in a month option
	 * @param scheduleMonth Used for multiple months in a yeah option
	 * @param transaction The transaction to base new scheduled transactions off of
	 */
	public void addSchedule(String name, Date startDate, Date endDate, String frequencyType, Integer scheduleDay, Integer scheduleWeek, Integer scheduleMonth, String message, Transaction transaction){
		Schedule s = dataModelFactory.createSchedule();
		s.setScheduleName(name);
		s.setStartDate(startDate);
		s.setEndDate(endDate);
		s.setFrequencyType(frequencyType);
		s.setScheduleDay(scheduleDay);
		s.setScheduleWeek(scheduleWeek);
		s.setScheduleMonth(scheduleMonth);
		s.setMessage(message);
		if (transaction != null){
			s.setAmount(transaction.getAmount());
			s.setDescription(transaction.getDescription());
			s.setNumber(transaction.getNumber());
			s.setMemo(transaction.getMemo());
			s.setTo(transaction.getTo());
			s.setFrom(transaction.getFrom());
		}

		dataModel.getAllTransactions().getScheduledTransactions().add(s);
		saveDataModel();
	}

	/**
	 * Removes a schedule from the data model.
	 * @param s
	 */
	public void removeSchedule(Schedule s){
		dataModel.getAllTransactions().getScheduledTransactions().remove(s);
	}

	/**
	 * Returns all scheduled transactions who meet the following criteria: 
	 * 
	 * 1) Start date is before today
	 * 2) Last date created is before today (or is not yet set).
	 * 
	 * This is used at startup when determining which scheduled transactions
	 * we need to check and possibly add.
	 * @return
	 */
	public Vector<Schedule> getScheduledTransactionsBeforeToday(){
		Vector<Schedule> v = getScheduledTransactions();
		Vector<Schedule> newV = new Vector<Schedule>();

		for (Schedule schedule : v) {
			if (schedule.getStartDate().before(DateUtil.getStartOfDay(new Date()))
					&& (schedule.getLastDateCreated() == null 
							|| schedule.getLastDateCreated().before(DateUtil.getStartOfDay(new Date()))))
				newV.add(schedule);
		}

		return newV;
	}

//	/**
//	* Prompts the user for a new data file.  Should probably not be in 
//	* this class (fits more in the view package), but since we can
//	* only call it from here, and we want it to be private, it will
//	* have to do. 
//	*/
//	private void promptForNewDataFile(){
//	final JFileChooser jfc = new JFileChooser();
//	jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
//	jfc.setCurrentDirectory(new File(PrefsInstance.getInstance().getPrefs().getDataFile()));
//	jfc.setFileHidingEnabled(true);
//	jfc.setFileFilter(new FileFilter(){
//	public boolean accept(File arg0) {
//	if (arg0.isDirectory() 
//	|| arg0.getName().endsWith(Const.DATA_FILE_EXTENSION))
//	return true;
//	else
//	return false;
//	}

//	public String getDescription() {
//	return Translate.getInstance().get(TranslateKeys.BUDDI_FILE_DESC);
//	}
//	});
//	jfc.setDialogTitle(Translate.getInstance().get(TranslateKeys.OPEN_DATA_FILE_TITLE));
//	if (jfc.showOpenDialog(MainBuddiFrame.getInstance()) == JFileChooser.APPROVE_OPTION){
//	if (jfc.getSelectedFile().isDirectory()){
//	if (Const.DEVEL) Log.debug(Translate.getInstance().get(TranslateKeys.MUST_SELECT_BUDDI_FILE));
//	}
//	else{
//	if (Const.DEVEL) Log.debug("Loading file " + jfc.getSelectedFile().getAbsolutePath());
//	PrefsInstance.getInstance().getPrefs().setDataFile(jfc.getSelectedFile().getAbsolutePath());
//	PrefsInstance.getInstance().savePrefs();
//	}
//	}
//	else {
//	Log.warning("You clicked cancel.  Exiting.");
//	System.exit(1);
//	}
//	}
	
	/**
	 * Runs through the list of scheduled transactions, and adds any which
	 * show be executed to the apropriate transacactions list.
+ 	 * Checks for the frequency type and based on it finds if a transaction is scheduled for a date
+ 	 * that has gone past.
	 */
	public void checkForScheduledActions(){
		//Update any scheduled transactions
		final Date today = DateUtil.getEndOfDay(new Date());
		//We specify a GregorianCalendar because we make some assumptions
		// about numbering of months, etc that may break if we 
		// use the default calendar for the locale.  It's not the
		// prettiest code, but it works.  Perhaps we can change
		// it to be cleaner later on...
		final GregorianCalendar tempCal = new GregorianCalendar();

		for (Schedule s : DataInstance.getInstance().getScheduledTransactionsBeforeToday()) {
			if (Const.DEVEL) Log.info("Looking at scheduled transaction " + s.getScheduleName());

			Date tempDate = s.getLastDateCreated();

			//Temp date is where we will start looping from.  If it is
			// null, we need to init it to a sane value.
			if (tempDate == null)
				tempDate = s.getStartDate();

			//We start one day after the last day, to avoid repeats.  See 
			// bug #1641937 for more details.
			tempDate = DateUtil.getNextDay(tempDate);
			Date lastDayCreated = (Date) tempDate.clone();

			tempDate = DateUtil.getStartOfDay(tempDate);

			if (Const.DEVEL){
				Log.debug("tempDate = " + tempDate);
				Log.debug("startDate = " + s.getStartDate());
			}

			//The transaction is scheduled for a date before today and before the EndDate 
			while (tempDate.before(today)) {
				if (Const.DEVEL) Log.debug("Trying date " + tempDate);

				//We use a Calendar instead of a Date object for comparisons
				// because the Calendar interface is much nicer.
				tempCal.setTime(tempDate);

				boolean todayIsTheDay = false;

				//We check each type of schedule, and if it matches,
				// we set todayIsTheDay to true.  We could do it 
				// all in one huge if statement, but that is very
				// hard to read and maintain.

				//If we are using the Monthly by Date frequency, 
				// we only check if the given day is equal to the
				// scheduled day.
				if (s.getFrequencyType().equals(TranslateKeys.MONTHLY_BY_DATE.toString())
						&& s.getScheduleDay() == tempCal.get(Calendar.DAY_OF_MONTH)){
					todayIsTheDay = true;
				}
				//If we are using the Monthly by Day of Week,
				// we check if the given day (Sunday, Monday, etc) is equal to the
				// scheduleDay, and if the given day is within the first week.
				// FYI, we store Sunday == 0, even though Calendar.SUNDAY == 1.  Thus,
				// we add 1 to our stored day before comparing it.
				else if (s.getFrequencyType().equals(TranslateKeys.MONTHLY_BY_DAY_OF_WEEK.toString())
						&& s.getScheduleDay() + 1 == tempCal.get(Calendar.DAY_OF_WEEK)
						&& tempCal.get(Calendar.DAY_OF_MONTH) <= 7){
					todayIsTheDay = true;
				}
				//If we are using Weekly frequency, we only need to compare
				// the number of the day.
				// FYI, we store Sunday == 0, even though Calendar.SUNDAY == 1.  Thus,
				// we add 1 to our stored day before comparing it.
				else if (s.getFrequencyType().equals(TranslateKeys.WEEKLY.toString())
						&& s.getScheduleDay() + 1 == tempCal.get(Calendar.DAY_OF_WEEK)){
					todayIsTheDay = true;
				}
				//If we are using BiWeekly frequency, we need to compare
				// the number of the day as well as ensure that there is one
				// week between each scheduled transaction.
				// FYI, we store Sunday == 0, even though Calendar.SUNDAY == 1.  Thus,
				// we add 1 to our stored day before comparing it.
				else if (s.getFrequencyType().equals(TranslateKeys.BIWEEKLY.toString())
						&& s.getScheduleDay() + 1 == tempCal.get(Calendar.DAY_OF_WEEK)
						&& (DateUtil.daysBetween(lastDayCreated, tempDate) > 13)){
					todayIsTheDay = true;
					lastDayCreated = (Date) tempDate.clone();
				}
				//Every day - it's obvious enough even for a monkey!
				else if (s.getFrequencyType().equals(TranslateKeys.EVERY_DAY.toString())){
					todayIsTheDay = true;
				}
				//Every weekday - all days but Saturday and Sunday.
				else if (s.getFrequencyType().equals(TranslateKeys.EVERY_WEEKDAY.toString())
						&& (tempCal.get(Calendar.DAY_OF_WEEK) < Calendar.SATURDAY)
						&& (tempCal.get(Calendar.DAY_OF_WEEK) > Calendar.SUNDAY)){
					todayIsTheDay = true;
				}
				//To make this one clearer, we do it in two passes.
				// First, we check the frequency type and the day.
				// If these match, we do out bit bashing to determine
				// if the week is correct.
				else if (s.getFrequencyType().equals(TranslateKeys.MULTIPLE_WEEKS_EVERY_MONTH.toString())
						&& s.getScheduleDay() + 1 == tempCal.get(Calendar.DAY_OF_WEEK)){
					if (Const.DEVEL) {
						Log.debug("We are looking at day " + tempCal.get(Calendar.DAY_OF_WEEK) + ", which matches s.getScheduleDay() which == " + s.getScheduleDay());
						Log.debug("s.getScheduleWeek() == " + s.getScheduleWeek());
					}
					int week = s.getScheduleWeek();
					//The week mask should return 1 for the first week (day 1 - 7), 
					// 2 for the second week (day 8 - 14), 4 for the third week (day 15 - 21),
					// and 8 for the fourth week (day 22 - 28).  We then AND it with 
					// the scheduleWeek to determine if this week matches the criteria
					// or not.
					int weekNumber = tempCal.get(Calendar.DAY_OF_WEEK_IN_MONTH) - 1;
					int weekMask = (int) Math.pow(2, weekNumber);
					if (Const.DEVEL){
						Log.debug("The week number is " + weekNumber + ", the week mask is " + weekMask + ", and the day of week in month is " + tempCal.get(Calendar.DAY_OF_WEEK_IN_MONTH));
					}
					if ((week & weekMask) != 0){
						if (Const.DEVEL) Log.info("The date " + tempCal.getTime() + " matches the requirements.");
						todayIsTheDay = true;
					}
				}
				//To make this one clearer, we do it in two passes.
				// First, we check the frequency type and the day.
				// If these match, we do out bit bashing to determine
				// if the month is correct.
				else if (s.getFrequencyType().equals(TranslateKeys.MULTIPLE_MONTHS_EVERY_YEAR.toString())
						&& s.getScheduleDay() == tempCal.get(Calendar.DAY_OF_MONTH)){
					int months = s.getScheduleMonth();
					//The month mask should be 2 ^ MONTH NUMBER,
					// where January == 0.
					// i.e. 1 for January, 4 for March, 2048 for December.
					int monthMask = (int) Math.pow(2, tempCal.get(Calendar.MONTH));
					if ((months & monthMask) != 0){
						if (Const.DEVEL) Log.info("The date " + tempCal.getTime() + " matches the requirements.");
						todayIsTheDay = true;
					}
				}

				//If one of the above rules matches, we will copy the
				// scheduled transaction into the transactions list
				// at the given day.
				if (todayIsTheDay){
					if (Const.DEVEL) Log.debug("Setting last created date to " + tempDate);
					s.setLastDateCreated(DateUtil.getEndOfDay(tempDate));
					if (Const.DEVEL) Log.debug("Last created date to " + s.getLastDateCreated());

					if (s.getMessage() != null && s.getMessage().length() > 0){
						JOptionPane.showMessageDialog(
								null, 
								s.getMessage(), 
								Translate.getInstance().get(TranslateKeys.SCHEDULED_MESSAGE), 
								JOptionPane.INFORMATION_MESSAGE);
					}

					if (s.getDate() != null
							&& s.getDescription() != null) {
						Transaction t = DataInstance.getInstance().getDataModelFactory().createTransaction();

						t.setDate(tempDate);
						t.setDescription(s.getDescription());
						t.setAmount(s.getAmount());
						t.setTo(s.getTo());
						t.setFrom(s.getFrom());
						t.setMemo(s.getMemo());
						t.setNumber(s.getNumber());
						t.setScheduled(true);

						DataInstance.getInstance().addTransaction(t);
						if (Const.DEVEL) Log.info("Added scheduled transaction " + t + " to transaction list on date " + t.getDate());
					}
					//We need to save to store the lastCreatedDate; however,
					// if we did not create a new transaction, we must
					// manually trigger the save.
					else {
						DataInstance.getInstance().saveDataModel();
					}
				}

				tempDate = DateUtil.getNextDay(tempDate);
			}
		}
	}
}