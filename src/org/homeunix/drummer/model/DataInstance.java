/*
 * Created on May 12, 2006 by wyatt
 */
package org.homeunix.drummer.model;

import java.io.File;

import javax.swing.JOptionPane;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.URIConverter;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMLResourceFactoryImpl;
import org.homeunix.drummer.controller.ReturnCodes;
import org.homeunix.drummer.controller.ScheduleController;
import org.homeunix.drummer.controller.SourceController;
import org.homeunix.drummer.controller.TransactionController;
import org.homeunix.drummer.model.util.AESCryptoCipher;
import org.homeunix.thecave.buddi.i18n.BuddiKeys;
import org.homeunix.thecave.buddi.i18n.keys.ButtonKeys;
import org.homeunix.thecave.buddi.i18n.keys.ScheduleFrequency;
import org.homeunix.thecave.buddi.plugin.api.util.TextFormatter;
import org.homeunix.thecave.moss.exception.DocumentLoadException;
import org.homeunix.thecave.moss.util.DateFunctions;
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
	 * Loads the given datafile.
	 * @param locationFile Data file to load
	 * @param forceNewFile If true, create a new data file at the given 
	 * location (overwriting the file currently there, if any).  
	 * If false, load the data file.
	 * @return True if the file is successfully loaded, false otherwise. 
	 */
	public ReturnCodes loadDataFile(File locationFile) throws DocumentLoadException {
		
		// throw away the old cipher (if any) when we load a new data file
		this.cipher = new AESCryptoCipher(128);

		//Check that the file is not null.  Assuming the File Chooser
		// is working properly, this should never be null, but it's good 
		// to check it...
		if (locationFile == null 
				|| !locationFile.exists()){
			String[] options = new String[1];
			options[0] = TextFormatter.getTranslation(ButtonKeys.BUTTON_OK);

			JOptionPane.showOptionDialog(
					null,
					TextFormatter.getTranslation(BuddiKeys.MESSAGE_ERROR_READING_FILE)
					+ "\n"
					+ ( locationFile == null ? locationFile : locationFile.getAbsolutePath() )
					+ "\n"
					+ TextFormatter.getTranslation(BuddiKeys.MESSAGE_ERROR_READING_FILE_NOT_EXIST),
					TextFormatter.getTranslation(BuddiKeys.MISSING_DATA_FILE),
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
			throw new DocumentLoadException(e);
		}


		// Once we have this loaded, we need to do some sanity checks.
		// After a few versions, these can be phased out.  The dates
		// and descriptions should be included in each group.
		for (Schedule s : ScheduleController.getScheduledTransactions()) {

			// Updates the scheduled transaction frequency type to new (2.2) version.
			// Added December 6 2006, in version 2.1.3; we can probably
			// remove it after version 2.4 or so...
			if (s.getFrequencyType().equals("WEEK")){
				s.setFrequencyType(ScheduleFrequency.SCHEDULE_FREQUENCY_WEEKLY.toString());
				Log.notice("Changing schedule frequency from WEEK to " + ScheduleFrequency.SCHEDULE_FREQUENCY_WEEKLY + " for " + s.getScheduleName());
			}
			if (s.getFrequencyType().equals("MONTH")){
				s.setFrequencyType(ScheduleFrequency.SCHEDULE_FREQUENCY_MONTHLY_BY_DATE.toString());
				s.setScheduleDay(s.getScheduleDay() + 1); //Before, we had the first of the month represented by 0.  Now it is 1.
				Log.notice("Changing schedule frequency from MONTH to " + ScheduleFrequency.SCHEDULE_FREQUENCY_MONTHLY_BY_DATE + " for " + s.getScheduleName());
			}
			
			
			//Updates to the new frequency names in 2.3.x.  Added May 2 2007,
			// we should probably leave it in until at least version
			// 2.8 or later.
			if (s.getFrequencyType().equals("MONTHLY_BY_DATE")) {
				s.setFrequencyType(ScheduleFrequency.SCHEDULE_FREQUENCY_MONTHLY_BY_DATE.toString());
				Log.notice("Changing schedule frequency from MONTHLY_BY_DATE to "
						+ ScheduleFrequency.SCHEDULE_FREQUENCY_MONTHLY_BY_DATE + " for "
						+ s.getScheduleName());
			}
			if (s.getFrequencyType().equals("BIWEEKLY")) {
				s.setFrequencyType(ScheduleFrequency.SCHEDULE_FREQUENCY_BIWEEKLY.toString());
				Log.notice("Changing schedule frequency from BIWEEKLY to "
						+ ScheduleFrequency.SCHEDULE_FREQUENCY_BIWEEKLY + " for "
						+ s.getScheduleName());
			}
			if (s.getFrequencyType().equals("EVERY_DAY")) {
				s.setFrequencyType(ScheduleFrequency.SCHEDULE_FREQUENCY_EVERY_DAY.toString());
				Log.notice("Changing schedule frequency from EVERY_DAY to "
						+ ScheduleFrequency.SCHEDULE_FREQUENCY_EVERY_DAY + " for "
						+ s.getScheduleName());
			}
			if (s.getFrequencyType().equals("EVERY_WEEKDAY")) {
				s.setFrequencyType(ScheduleFrequency.SCHEDULE_FREQUENCY_EVERY_WEEKDAY.toString());
				Log.notice("Changing schedule frequency from EVERY_WEEKDAY to "
						+ ScheduleFrequency.SCHEDULE_FREQUENCY_EVERY_WEEKDAY + " for "
						+ s.getScheduleName());
			}
			if (s.getFrequencyType().equals("MONTHLY_BY_DAY_OF_WEEK")) {
				s.setFrequencyType(ScheduleFrequency.SCHEDULE_FREQUENCY_MONTHLY_BY_DAY_OF_WEEK.toString());
				Log.notice("Changing schedule frequency from MONTHLY_BY_DAY_OF_WEEK to "
						+ ScheduleFrequency.SCHEDULE_FREQUENCY_MONTHLY_BY_DAY_OF_WEEK + " for "
						+ s.getScheduleName());
			}
			if (s.getFrequencyType().equals("MULTIPLE_MONTHS_EVERY_YEAR")) {
				s.setFrequencyType(ScheduleFrequency.SCHEDULE_FREQUENCY_MULTIPLE_MONTHS_EVERY_YEAR.toString());
				Log.notice("Changing schedule frequency from MULTIPLE_MONTHS_EVERY_YEAR to "
						+ ScheduleFrequency.SCHEDULE_FREQUENCY_MULTIPLE_MONTHS_EVERY_YEAR + " for "
						+ s.getScheduleName());
			}
			if (s.getFrequencyType().equals("MULTIPLE_WEEKS_EVERY_MONTH")) {
				s.setFrequencyType(ScheduleFrequency.SCHEDULE_FREQUENCY_MULTIPLE_WEEKS_EVERY_MONTH.toString());
				Log.notice("Changing schedule frequency from MULTIPLE_WEEKS_EVERY_MONTH to "
						+ ScheduleFrequency.SCHEDULE_FREQUENCY_MULTIPLE_WEEKS_EVERY_MONTH + " for "
						+ s.getScheduleName());
			}
			if (s.getFrequencyType().equals("WEEKLY")) {
				s.setFrequencyType(ScheduleFrequency.SCHEDULE_FREQUENCY_WEEKLY.toString());
				Log.notice("Changing schedule frequency from WEEKLY to "
						+ ScheduleFrequency.SCHEDULE_FREQUENCY_WEEKLY + " for "
						+ s.getScheduleName());
			}

			
			//This check is for those who have been following the Dev
			// branch.  When you ran 2.1.3, it upgraded from MONTH
			// to MONTHLY_BY_DATE, but it did not convert the date
			// to the new format.  This means that if you had the date
			// set to 1, it would try to set it to 0, which would not
			// work.  This check will fix it.
			// We should be able to remove this sometime around 2.6 or so.
			if (s.getFrequencyType().equals(ScheduleFrequency.SCHEDULE_FREQUENCY_MONTHLY_BY_DATE.toString())){
				if (s.getScheduleDay() == 0)
					s.setScheduleDay(1);
			}
		}

		//We added the auto complete entries here in a 2.5 Development release.  We then 
		// removed them, preferring to create the list on the fly by analyzing existing
		// transactions.  We thus need to set allLists to null, in preparation for 
		// removal from the data model.  We should remove this from the model and
		// from here some time around 2.7 or so.
		if (dataModel.getAllLists() != null)
			dataModel.setAllLists(null);
		
		//Modify each of the transactions to remove time information - you 
		// should only have year / month / day information saved.
		for (Transaction t : TransactionController.getTransactions()) {
			t.setDate(DateFunctions.getStartOfDay(t.getDate()));
		}
		
		//Check that there are no scheduled transactions which should be happening...
		ScheduleController.checkForScheduledActions();		

		//Make sure that this is updated when we load...
		SourceController.calculateAllBalances();

		return ReturnCodes.SUCCESS;
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
}