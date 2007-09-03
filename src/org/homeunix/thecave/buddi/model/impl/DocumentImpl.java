/*
 * Created on Jul 29, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.model.impl;

import java.beans.Encoder;
import java.beans.Expression;
import java.beans.PersistenceDelegate;
import java.beans.XMLEncoder;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;

import org.homeunix.thecave.buddi.Const;
import org.homeunix.thecave.buddi.i18n.BuddiKeys;
import org.homeunix.thecave.buddi.i18n.keys.ButtonKeys;
import org.homeunix.thecave.buddi.i18n.keys.ScheduleFrequency;
import org.homeunix.thecave.buddi.model.Account;
import org.homeunix.thecave.buddi.model.AccountType;
import org.homeunix.thecave.buddi.model.BudgetCategory;
import org.homeunix.thecave.buddi.model.Document;
import org.homeunix.thecave.buddi.model.ModelObject;
import org.homeunix.thecave.buddi.model.ScheduledTransaction;
import org.homeunix.thecave.buddi.model.Source;
import org.homeunix.thecave.buddi.model.Transaction;
import org.homeunix.thecave.buddi.model.impl.FilteredLists.AccountListFilteredByType;
import org.homeunix.thecave.buddi.model.impl.FilteredLists.BudgetCategoryListFilteredByParent;
import org.homeunix.thecave.buddi.model.impl.FilteredLists.ScheduledTransactionListFilteredByBeforeToday;
import org.homeunix.thecave.buddi.model.impl.FilteredLists.TransactionListFilteredByDate;
import org.homeunix.thecave.buddi.model.impl.FilteredLists.TransactionListFilteredBySource;
import org.homeunix.thecave.buddi.model.prefs.PrefsModel;
import org.homeunix.thecave.buddi.plugin.api.exception.DataModelProblemException;
import org.homeunix.thecave.buddi.plugin.api.exception.ModelException;
import org.homeunix.thecave.buddi.plugin.api.util.TextFormatter;
import org.homeunix.thecave.buddi.util.BuddiCryptoFactory;
import org.homeunix.thecave.buddi.view.dialogs.BuddiPasswordDialog;
import org.homeunix.thecave.moss.data.list.CompositeList;
import org.homeunix.thecave.moss.exception.DocumentSaveException;
import org.homeunix.thecave.moss.model.AbstractDocument;
import org.homeunix.thecave.moss.util.DateFunctions;
import org.homeunix.thecave.moss.util.FileFunctions;
import org.homeunix.thecave.moss.util.Log;
import org.homeunix.thecave.moss.util.crypto.CipherException;


/**
 * The main container class for the new data model, to be implemented in Buddi version 3.0.
 * This contains all the data, most of it in list form.  This object is the root of the XML
 * file as serialized by XMLEncoder.
 * 
 * You should *not* create this class by calling its constructor - you must create it using
 * one of the ModelFactory.createDocument methods.  The factory will correctly initialize
 * the default types and budget categories.  The only reason we did not make the default
 * constructor for this class to be non-public was because the XMLDecoder needs public
 * consructors to create objects at load time.  
 * 
 * @author wyatt
 */
public class DocumentImpl extends AbstractDocument implements ModelObject, Document {
	public static final int ENCRYPT_DATA_FILE = 1;

	//Store the password when loaded, and use the same one for save().
	// This is obviously not the best practice to use 
	// from a security point of view.  However, if a malicious
	// third party has good enough access to the machine to be able
	// to read private Java objects, they will just read it when
	// we call MossCryptoFactory anyways - the password is handed 
	// there in plain text as well.  The window is already there - this
	// just increases the time it is available for.
	private char[] password; 	

	//Convenience class for checking if objects are already entered.
	private final Map<String, ModelObject> uidMap = new HashMap<String, ModelObject>();

	//User data objects
	private List<Account> accounts = new LinkedList<Account>();
	private List<BudgetCategory> budgetCategories = new LinkedList<BudgetCategory>();
	private List<AccountType> accountTypes = new LinkedList<AccountType>();
	private List<Transaction> transactions = new LinkedList<Transaction>();
	private List<ScheduledTransaction> scheduledTransactions = new LinkedList<ScheduledTransaction>();

	//Model object data
	private Date modifiedDate;
	private String uid;

	/**
	 * By default, we start with one batch change enabled.  This is because, otherwise,
	 * the XMLDecoder will cause many model change events to be fired, which will result in
	 * much longer load times.  You must call finisheBatchChange() before any change 
	 * events will be sent!  ModelFactory does this for you automatically; as such,
	 * you are much better off to use that to create Document objects.
	 * 
	 * (In fact, this constructor would be private, except for XMLDecoder's need for a public
	 * constructor.)
	 */
	public DocumentImpl() {
		startBatchChange();
	}

	private void doBackupDataFile() {
		//Make a backup of the file...
		//Backup the file, now that we know it is good...
		try{
			//Use a rotating backup file, of form 'Data.X.buddi'  
			// The one with the smallest number X is the most recent.
			String fileBase = getFile().getAbsolutePath().replaceAll(Const.DATA_FILE_EXTENSION + "$", "");
			for (int i = PrefsModel.getInstance().getNumberOfBackups() - 2; i >= 0; i--){
				File tempBackupDest = new File(fileBase + "_" + (i + 1) + Const.BACKUP_FILE_EXTENSION);
				File tempBackupSource = new File(fileBase + "_" + i + Const.BACKUP_FILE_EXTENSION);
				if (tempBackupSource.exists()){
					FileFunctions.copyFile(tempBackupSource, tempBackupDest);
					Log.debug("Moving " + tempBackupSource + " to " + tempBackupDest);
				}
			}
			File tempBackupDest = new File(fileBase + "_0" + Const.BACKUP_FILE_EXTENSION);
			FileFunctions.copyFile(getFile(), tempBackupDest);
			if (Const.DEVEL) Log.debug("Backing up file to " + tempBackupDest);
		}
		catch(IOException ioe){
			Log.emergency("Problem backing up data files when starting program: " + ioe);
		}
	}
	
	public List<Account> getAccounts() {
		checkLists();
		return accounts;
	}
	public void setAccounts(List<Account> accounts) {
		this.accounts = accounts;
	}

	public List<BudgetCategory> getBudgetCategories() {
		return budgetCategories;
	}
	public void setBudgetCategories(List<BudgetCategory> budgetCategories) {
		this.budgetCategories = budgetCategories;
	}
	public List<ScheduledTransaction> getScheduledTransactions() {
		checkLists();
		return scheduledTransactions;
	}
	public void setScheduledTransactions(List<ScheduledTransaction> scheduledTransactions) {
		this.scheduledTransactions = scheduledTransactions;
	}
	public List<Transaction> getTransactions() {
		checkLists();
		return transactions;
	}
	public void setTransactions(List<Transaction> transactions) {
		this.transactions = transactions;
		Collections.sort(transactions);
	}
	public List<AccountType> getAccountTypes() {
		checkLists();
		return accountTypes;
	}
	public void setAccountTypes(List<AccountType> types) {
		this.accountTypes = types;
	}




	public void addAccount(Account account) throws ModelException {
		account.setDocument(this);
		checkValid(account, true, false);
		accounts.add(account);
		Collections.sort(accounts);
		setChanged();
	}
	public void addAccountType(AccountType type) throws ModelException {
		type.setDocument(this);
		checkValid(type, true, false);
		accountTypes.add(type);
		Collections.sort(budgetCategories);
		setChanged();
	}
	public void addBudgetCategory(BudgetCategory budgetCategory) throws ModelException {
		budgetCategory.setDocument(this);
		checkValid(budgetCategory, true, false);
		budgetCategories.add(budgetCategory);
		Collections.sort(budgetCategories);
		setChanged();
	}
	public void addScheduledTransaction(ScheduledTransaction scheduledTransaction) throws ModelException {
		scheduledTransaction.setDocument(this);
		checkValid(scheduledTransaction, true, false);
		scheduledTransactions.add(scheduledTransaction);
		Collections.sort(scheduledTransactions);
		setChanged();
	}
	public void addTransaction(Transaction transaction) throws ModelException {
		transaction.setDocument(this);
		checkValid(transaction, true, false);
		transactions.add(transaction);
		Collections.sort(transactions);
		setChanged();
	}
	public Account getAccount(String name) {
		for (Account a : getAccounts()) { //Try strict matching first
			if (a.getName().equals(name))
				return a;
		}
		for (Account a : getAccounts()) { //Next try to relax it, to ignore case
			if (a.getName().equalsIgnoreCase(name))
				return a;
		}
		for (Account a : getAccounts()) { //Finally try checking full name
			if (a.getFullName().equalsIgnoreCase(name))
				return a;
		}

		return null;
	}
	public AccountType getAccountType(String name) {
		for (AccountType at : getAccountTypes()) {
			if (at.getName().equals(name))
				return at;
		}
		for (AccountType at : getAccountTypes()) {
			if (at.getName().equalsIgnoreCase(name))
				return at;
		}
		return null;
	}
	public BudgetCategory getBudgetCategory(String fullName) {
		for (BudgetCategory bc : getBudgetCategories()) {
			if (bc.getFullName().equals(fullName))
				return bc;
		}
		for (BudgetCategory bc : getBudgetCategories()) {
			if (bc.getFullName().equalsIgnoreCase(fullName))
				return bc;
		}
		return null;
	}
	public ModelObject getObjectByUid(String uid) {
		// TODO Auto-generated method stub
		return null;
	}
	@SuppressWarnings("unchecked")
	public List<Source> getSources() {
		return new CompositeList<Source>(true, false, getAccounts(), getBudgetCategories());
	}
	public List<Transaction> getTransactions(Date startDate, Date endDate) {
		return new FilteredLists.TransactionListFilteredByDate(this, getTransactions(), startDate, endDate);
	}
	public List<Transaction> getTransactions(Source source, Date startDate, Date endDate) {
		return new TransactionListFilteredBySource(
				this,
				new TransactionListFilteredByDate(this, getTransactions(), startDate, endDate),
				source);
	}
	public List<Transaction> getTransactions(Source source) {
		return new TransactionListFilteredBySource(this, getTransactions(), source);
	}
	public void removeAccount(Account account) throws ModelException {
		if (getTransactions(account).size() > 0)
			throw new ModelException("Cannot remove account " + account + "; it contains transactions");
		for (ScheduledTransaction st : getScheduledTransactions())
			if (st.getFrom().equals(account)
					|| st.getTo().equals(account))
				throw new ModelException("Cannot remove account " + account + "; it contains scheduled transactions");		
		accounts.remove(account);
		setChanged();
	}
	public void removeAccountType(AccountType type) throws ModelException {
		for (Account a : getAccounts()) {
			if (a.getAccountType().equals(type))
				throw new ModelException("Cannot remove account type " + type + "; it is referred to by " + a);
		}
		accountTypes.remove(type);
		setChanged();
	}

	public void removeBudgetCategory(BudgetCategory budgetCategory) throws ModelException {
		if (getTransactions(budgetCategory).size() > 0)
			throw new ModelException("Cannot remove budget category " + budgetCategory + "; it is referenced by at least one transaction");
		for (ScheduledTransaction st : getScheduledTransactions())
			if (st.getFrom().equals(budgetCategory)
					|| st.getTo().equals(budgetCategory))
				throw new ModelException("Cannot remove budget category " + budgetCategory + "; it contains scheduled transactions");		

		budgetCategories.remove(budgetCategory);
		setChanged();
	}
	public void removeScheduledTransaction(ScheduledTransaction scheduledTransaction) throws ModelException {
		scheduledTransactions.remove(scheduledTransaction);
		setChanged();
	}
	public void removeTransaction(Transaction transaction) throws ModelException {
		transactions.remove(transaction);
		setChanged();
	}
	/**
	 * Saves the data file to the current file.  If the file has not yet been set,
	 * this method silently returns.
	 * @throws SaveModelException
	 */
	public void save() throws DocumentSaveException {
		saveWrapper(getFile(), 0, false);
	}

	/**
	 * Saves the data file to the specified file.  If the specified file is 
	 * null, silently return without error and without saving.
	 * @param file
	 * @param flags.  Flags to set for saving.  AND together for multiple flags.
	 * @throws SaveModelException
	 */
	public void saveAs(File file, int flags) throws DocumentSaveException {
		saveWrapper(file, flags, true);
	}

	public void saveAuto(File file) throws DocumentSaveException {
		saveInternal(file, 0);
	}

	/**
	 * This is a simple wrapper around the saveInternal method, which adds some key functionality.
	 * This is especially related to handling autoSave files, and will remove the autosave
	 * file associated with this file if it exists. 
	 * @param file
	 * @param flags
	 * @param resetUid
	 * @throws DocumentSaveException
	 */
	private void saveWrapper(File file, int flags, boolean resetUid) throws DocumentSaveException {
		//Do a rotating backup before saving
		doBackupDataFile();
		
		//Reset the document's UID.  This needs to be done when saving to a different file,
		// or else you cannot have multiple files open at the same time.
		if (resetUid)
			setUid(ModelFactory.getGeneratedUid(this));
		
		//Save the file
		saveInternal(file, flags);

		//Save where we last saved this file.
		setFile(file);
		PrefsModel.getInstance().setLastOpenedDataFile(file);

		//Reset the changed flag.
		resetChanged();

		//Remove any auto save files which are associated with this document
		File autosave = ModelFactory.getAutoSaveLocation(file);
		if (autosave.exists() && autosave.isFile()){
			if (!autosave.delete())
				Log.error("Unable to delete file " + autosave + "; you may be prompted to load this file next time you load.");
		}
	}

	/**
	 * Very simple save method.  Will do the following:
	 * 
	 * 1) Prompt for password if the encryption flag is set.
	 * 2) Stream the document to XML using the XMLEncoder, optionally using an encrypted 
	 * output stream if the password is set.
	 * 
	 * @param file
	 * @param flags
	 * @throws DocumentSaveException
	 */
	private void saveInternal(File file, int flags) throws DocumentSaveException {
		if (file == null)
			throw new DocumentSaveException("Error saving data file: specified file is null!");

		try {
			if ((flags & ENCRYPT_DATA_FILE) != 0){
				BuddiPasswordDialog passwordDialog = new BuddiPasswordDialog();
				password = passwordDialog.askForPassword(true, false);
			}

			BuddiCryptoFactory factory = new BuddiCryptoFactory();
			OutputStream os = factory.getCipherOutputStream(new FileOutputStream(file), password);

			//We don't want to be firing change events in the middle of a save
			startBatchChange();

			XMLEncoder encoder = new XMLEncoder(os);
			encoder.setPersistenceDelegate(File.class, new PersistenceDelegate(){
				protected Expression instantiate(Object oldInstance, Encoder out ){
					File file = (File) oldInstance;
					String filePath = file.getAbsolutePath();
					return new Expression(file, file.getClass(), "new", new Object[]{filePath} );
				}
			});

			encoder.writeObject(this);
			encoder.flush();
			encoder.close();

			finishBatchChange();
		}
		catch (CipherException ce){
			//This means that there is something seriously wrong with the encryption methods.
			// Perhaps the user's platform does not support the given methods.
			// Notify the user, and cancel the save.
			throw new DocumentSaveException(ce);
		}
		catch (IOException ioe){
			//This means taht there was something wrong with the given file, or writing to
			// it.  Perhaps the user does not have write access, the folder does not exist,
			// or something similar.  Notify the user, and cancel the save.
			throw new DocumentSaveException(ioe);
		}

	}
	/**
	 * Streams the current Data Model object as an XML encoded string.  This is primarily meant
	 * for troubleshooting crashes. 
	 * @return
	 */
	public String saveToString(){
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		XMLEncoder encoder = new XMLEncoder(baos);
		encoder.writeObject(this);
		encoder.flush();
		encoder.close();

		return baos.toString();
	}
	/**
	 * Updates the balances of all accounts.  Iterates through all accounts, and
	 * calls the updateBalance() method for each. 
	 */
	public void updateAllBalances(){
		this.startBatchChange();
		for (Account a : getAccounts()) {
			a.updateBalance();
		}
		this.finishBatchChange();
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	public void setChanged(){
		setModifiedDate(new Date());
		super.setChanged();
	}
	public String getUid() {
		if (uid == null || uid.length() == 0){
			setUid(ModelFactory.getGeneratedUid(this));
		}
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public Document getDocument() {
		return this;
	}
	public void setDocument(Document document) {} //Null implementation - this makes no sense

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof DocumentImpl)
			return this.getUid().equals(((DocumentImpl) obj).getUid());
		return false;
	}

	public int compareTo(ModelObject o) {
		return (getUid().compareTo(o.getUid()));
	}

	/**
	 * Refreshes the UID map.  This is a relatively expensive operation, and as such is 
	 * generally only done at file load time.
	 * 
	 * We iterate through all objects for each data type, and add them to the UID map.
	 * This will associate the object with their UID as the key in the Map.
	 */
	public void refreshUidMap() throws ModelException {
		uidMap.clear();

		for (Account a : getAccounts()) {
			checkValid(a, false, true);
			registerObjectInUidMap(a);
		}

		for (BudgetCategory bc : getBudgetCategories()) {
			checkValid(bc, false, true);
			registerObjectInUidMap(bc);
		}

//		for (BudgetPeriodBean bpb : dataModel.getBudgetPeriods().values()) {
//		BudgetPeriod bp = new BudgetPeriod(this, bpb);
//		checkValid(bp, false, true);
//		registerObjectInUidMap(bp);			
//		}

		for (AccountType t : getAccountTypes()) {
			checkValid(t, false, true);
			registerObjectInUidMap(t);
		}

		for (Transaction t : getTransactions()) {
			checkValid(t, false, true);
			registerObjectInUidMap(t);	
		}

		for (ScheduledTransaction s : getScheduledTransactions()) {
			checkValid(s, false, true);
			registerObjectInUidMap(s);	
		}
	}

	private void registerObjectInUidMap(ModelObject object){
		uidMap.put(object.getUid(), object);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append("\n--Accounts and Types--\n");
		for (AccountType t : getAccountTypes()) {
			sb.append(t).append("\n");

			for (Account a : new AccountListFilteredByType(this, this.getAccounts(), t)) {
				sb.append("\t").append(a).append("\n");
			}
		}

		sb.append("\n--Budget Categories--\n");

		//We only show 3 levels here; if there are more, we figure that it is not needed to
		// output them in the toString method, as it is just for troubleshooting.
		for (BudgetCategory bc : new BudgetCategoryListFilteredByParent(this, this.getBudgetCategories(), null)) {
			sb.append(bc).append("\n");
			for (BudgetCategory child1 : new BudgetCategoryListFilteredByParent(this, this.getBudgetCategories(), bc)) {
				sb.append("\t").append(child1).append("\n");
				for (BudgetCategory child2 : new BudgetCategoryListFilteredByParent(this, this.getBudgetCategories(), child1)) {
					sb.append("\t\t").append(child2).append("\n");	
				}				
			}
		}

//		sb.append("\n--Budget Periods--\n");
//		List<String> periodDates = new LinkedList<String>(dataModel.getBudgetPeriods().keySet());
//		Collections.sort(periodDates);
//		for (String d : periodDates) {
//		sb.append(d).append(getBudgetPeriod(d));
//		}
		sb.append("\n--Transactions--\n");
		sb.append("Total transactions: ").append(getTransactions().size()).append("\n");
		for (Transaction t : getTransactions()) {
			sb.append(t.toString()).append("\n");
		}
		sb.append("--");

		return sb.toString();
	}

	private void checkLists(){
		if (this.accounts == null)
			this.accounts = new LinkedList<Account>();
		if (this.transactions == null)
			this.transactions = new LinkedList<Transaction>();
		if (this.scheduledTransactions == null)
			this.scheduledTransactions = new LinkedList<ScheduledTransaction>();
		if (this.accountTypes == null)
			this.accountTypes = new LinkedList<AccountType>();
	}

	/**
	 * Perform all the sanity checks here, for code simplicity
	 * @param object The object to be modified
	 * @param isAddOperation Is this an add operation? (This affects some of the checks,
	 * such as if the UID is already entered into the model).
	 * @param isUidRefresh Is this being called from the refreshUid method?
	 */
	private void checkValid(ModelObject object, boolean isAddOperation, boolean isUidRefresh) throws ModelException {
		if (object.getDocument() == null)
			throw new ModelException("Document has not yet been set for this object");

		if(!object.getDocument().equals(this))
			throw new ModelException("Cannot modify an object not in this model");

		if (isAddOperation){
//			if (this.getUid(object.getBean()) != null)
//			throw new DataModelProblemException("Cannot have the same UID for multiple objects in the model.", this);

			if (object instanceof Account){
				for (Account a : getAccounts()) {
					if (a.getName().equalsIgnoreCase(((Account) object).getName()))
						throw new ModelException("Cannot have multiple accounts with the same name");
				}
			}

			if (object instanceof BudgetCategory){
				for (BudgetCategory bc : getBudgetCategories()) {
					if (bc.getFullName().equalsIgnoreCase(((BudgetCategory) object).getFullName()))
						throw new ModelException("Cannot have multiple budget categories with the same name");
				}
			}

			if (object instanceof ScheduledTransaction){
				for (ScheduledTransaction s : getScheduledTransactions()) {
					if (s.getScheduleName().equalsIgnoreCase(((ScheduledTransaction) object).getScheduleName()))
						throw new ModelException("Cannot have multiple scheduled transactions with the same name");
				}				
			}
		}

		if (isAddOperation || isUidRefresh){
			if (uidMap.get(object.getUid()) != null)
				//TODO Do something drastic here... perhaps save an emergency data file, prompt, and quit.
				throw new DataModelProblemException("Identical UID already in model!  Model is probably corrupt!", this); 
		}

	}

	/**
	 * Runs through the list of scheduled transactions, and adds any which
	 * show be executed to the apropriate transacactions list.
+ 	 * Checks for the frequency type and based on it finds if a transaction is scheduled for a date
+ 	 * that has gone past.
	 */
	public void updateScheduledTransactions(){
		startBatchChange();

		boolean changed = false;  //Set to true if we do anything.  If we do, we will set the model to changed when done.

		//Update any scheduled transactions
		final Date today = DateFunctions.getEndOfDay(new Date());
		//We specify a GregorianCalendar because we make some assumptions
		// about numbering of months, etc that may break if we 
		// use the default calendar for the locale.  It's not the
		// prettiest code, but it works.  Perhaps we can change
		// it to be cleaner later on...
		final GregorianCalendar tempCal = new GregorianCalendar();

		for (ScheduledTransaction s : new ScheduledTransactionListFilteredByBeforeToday(this, getScheduledTransactions())) {
			if (Const.DEVEL) Log.info("Looking at scheduled transaction " + s.getScheduleName());

			Date tempDate = s.getLastDayCreated();
			//#1779286 Bug BiWeekly Scheduled Transactions -Check if this transcation has never been created. 
			boolean isNewTransaction=false;
			//The lastDayCreated need to date as such without rolling forward by a day and the start fo the day 
			//so calculations of difference of days are on the smae keel as tempDate.
			Date lastDayCreated = null;
			//Temp date is where we will start looping from.
			if (tempDate == null){
				//If it is null, we need to init it to a sane value.
				tempDate = s.getStartDate();
				isNewTransaction=true;
				//The below is just to avoid NPE's ideally changing the order of the checking below will solve the problem, but better safe than sorry.
				lastDayCreated=DateFunctions.getStartOfDay(tempDate);
			}
			else {
				lastDayCreated = DateFunctions.getStartOfDay(tempDate);
				//We start one day after the last day, to avoid repeats.  
				// See bug #1641937 for more details.
				tempDate = DateFunctions.addDays(tempDate, 1);

			}



			tempDate = DateFunctions.getStartOfDay(tempDate);

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
				if (s.getFrequencyType().equals(ScheduleFrequency.SCHEDULE_FREQUENCY_MONTHLY_BY_DATE.toString())
						&& s.getScheduleDay() == tempCal.get(Calendar.DAY_OF_MONTH)){
					todayIsTheDay = true;
				}
				//If we are using the Monthly by Day of Week,
				// we check if the given day (Sunday, Monday, etc) is equal to the
				// scheduleDay, and if the given day is within the first week.
				// FYI, we store Sunday == 0, even though Calendar.SUNDAY == 1.  Thus,
				// we add 1 to our stored day before comparing it.
				else if (s.getFrequencyType().equals(ScheduleFrequency.SCHEDULE_FREQUENCY_MONTHLY_BY_DAY_OF_WEEK.toString())
						&& s.getScheduleDay() + 1 == tempCal.get(Calendar.DAY_OF_WEEK)
						&& tempCal.get(Calendar.DAY_OF_MONTH) <= 7){
					todayIsTheDay = true;
				}
				//If we are using Weekly frequency, we only need to compare
				// the number of the day.
				// FYI, we store Sunday == 0, even though Calendar.SUNDAY == 1.  Thus,
				// we add 1 to our stored day before comparing it.
				else if (s.getFrequencyType().equals(ScheduleFrequency.SCHEDULE_FREQUENCY_WEEKLY.toString())
						&& s.getScheduleDay() + 1 == tempCal.get(Calendar.DAY_OF_WEEK)){
					todayIsTheDay = true;
				}
				//If we are using BiWeekly frequency, we need to compare
				// the number of the day as well as ensure that there is one
				// week between each scheduled transaction.
				// FYI, we store Sunday == 0, even though Calendar.SUNDAY == 1.  Thus,
				// we add 1 to our stored day before comparing it.
				else if (s.getFrequencyType().equals(ScheduleFrequency.SCHEDULE_FREQUENCY_BIWEEKLY.toString())
						&& s.getScheduleDay() + 1 == tempCal.get(Calendar.DAY_OF_WEEK)
						//As tempdate has been moved forward by one day we need to check if it is >= 13 instead of >13
						&& ((DateFunctions.getDaysBetween(lastDayCreated, tempDate, false) >= 13)
								|| isNewTransaction)){
					todayIsTheDay = true;
					lastDayCreated = (Date) tempDate.clone();
					if(isNewTransaction){
						isNewTransaction=false;
					}
				}
				//Every day - it's obvious enough even for a monkey!
				else if (s.getFrequencyType().equals(ScheduleFrequency.SCHEDULE_FREQUENCY_EVERY_DAY.toString())){
					todayIsTheDay = true;
				}
				//Every weekday - all days but Saturday and Sunday.
				else if (s.getFrequencyType().equals(ScheduleFrequency.SCHEDULE_FREQUENCY_EVERY_WEEKDAY.toString())
						&& (tempCal.get(Calendar.DAY_OF_WEEK) < Calendar.SATURDAY)
						&& (tempCal.get(Calendar.DAY_OF_WEEK) > Calendar.SUNDAY)){
					todayIsTheDay = true;
				}
				//To make this one clearer, we do it in two passes.
				// First, we check the frequency type and the day.
				// If these match, we do our bit bashing to determine
				// if the week is correct.
				else if (s.getFrequencyType().equals(ScheduleFrequency.SCHEDULE_FREQUENCY_MULTIPLE_WEEKS_EVERY_MONTH.toString())
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
				// If these match, we do our bit bashing to determine
				// if the month is correct.
				else if (s.getFrequencyType().equals(ScheduleFrequency.SCHEDULE_FREQUENCY_MULTIPLE_MONTHS_EVERY_YEAR.toString())
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

				try {
					//If one of the above rules matches, we will copy the
					// scheduled transaction into the transactions list
					// at the given day.
					if (todayIsTheDay){
						if (Const.DEVEL) Log.debug("Setting last created date to " + tempDate);
						s.setLastDayCreated(DateFunctions.getEndOfDay(tempDate));
						if (Const.DEVEL) Log.debug("Last created date to " + s.getLastDayCreated());

						if (s.getMessage() != null && s.getMessage().trim().length() > 0){
							String[] options = new String[1];
							options[0] = TextFormatter.getTranslation(ButtonKeys.BUTTON_OK);

							JOptionPane.showOptionDialog(
									null, 
									s.getMessage(), 
									TextFormatter.getTranslation(BuddiKeys.SCHEDULED_MESSAGE), 
									JOptionPane.DEFAULT_OPTION,
									JOptionPane.INFORMATION_MESSAGE,
									null,
									options,
									options[0]
							);
						}

						if (tempDate != null
								&& s.getDescription() != null) {
							Transaction t = ModelFactory.createTransaction(
									tempDate, 
									s.getDescription(), 
									s.getAmount(), 
									s.getFrom(), 
									s.getTo());

							t.setDate(tempDate);
							t.setDescription(s.getDescription());
							t.setAmount(s.getAmount());
							t.setTo(s.getTo());
							t.setFrom(s.getFrom());
							t.setMemo(s.getMemo());
							t.setNumber(s.getNumber());
//							t.setCleared(s.isCleared());
//							t.setReconciled(s.isReconciled());
							t.setScheduled(true);

							this.addTransaction(t);
							if (Const.DEVEL) Log.info("Added scheduled transaction " + t + " to transaction list on date " + t.getDate());
							changed = true;
						}
					}
					else {
						Log.debug("Today was not the day to add the scheduled transaction...\n\tDate = " 
								+ tempDate
								+ "\n\tDescription = " 
								+ s.getDescription());
					}

				}
				catch (ModelException me){
					Log.error("Error adding scheduled tranaction: " + me);
				}

				tempDate = DateFunctions.addDays(tempDate, 1);
			}
		}

		finishBatchChange();

		updateAllBalances();

		if (changed)
			setChanged();
	}

	public void setPassword(char[] password) {
		this.password = password;
	}
}
