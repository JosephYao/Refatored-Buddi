/*
 * Created on Jul 30, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.model.impl;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.homeunix.thecave.buddi.i18n.keys.BudgetExpenseDefaultKeys;
import org.homeunix.thecave.buddi.i18n.keys.BudgetIncomeDefaultKeys;
import org.homeunix.thecave.buddi.i18n.keys.TypeCreditDefaultKeys;
import org.homeunix.thecave.buddi.i18n.keys.TypeDebitDefaultKeys;
import org.homeunix.thecave.buddi.model.Account;
import org.homeunix.thecave.buddi.model.AccountType;
import org.homeunix.thecave.buddi.model.BudgetCategory;
import org.homeunix.thecave.buddi.model.Document;
import org.homeunix.thecave.buddi.model.ModelObject;
import org.homeunix.thecave.buddi.model.ScheduledTransaction;
import org.homeunix.thecave.buddi.model.Source;
import org.homeunix.thecave.buddi.model.Transaction;
import org.homeunix.thecave.buddi.model.beans.DocumentBean;
import org.homeunix.thecave.buddi.model.beans.ModelObjectBean;
import org.homeunix.thecave.buddi.model.exception.DataModelProblemException;
import org.homeunix.thecave.buddi.model.exception.DocumentAlreadySetException;
import org.homeunix.thecave.buddi.model.exception.ModelException;
import org.homeunix.thecave.buddi.model.impl.FilteredLists.AccountListFilteredByType;
import org.homeunix.thecave.buddi.model.impl.FilteredLists.BudgetCategoryListFilteredByParent;
import org.homeunix.thecave.buddi.model.impl.WrapperLists.WrapperAccountList;
import org.homeunix.thecave.buddi.model.impl.WrapperLists.WrapperBudgetCategoryList;
import org.homeunix.thecave.buddi.model.impl.WrapperLists.WrapperScheduledTransactionList;
import org.homeunix.thecave.buddi.model.impl.WrapperLists.WrapperTransactionList;
import org.homeunix.thecave.buddi.model.impl.WrapperLists.WrapperTypeList;
import org.homeunix.thecave.buddi.model.periods.BudgetPeriodMonthly;
import org.homeunix.thecave.buddi.model.prefs.PrefsModel;
import org.homeunix.thecave.buddi.plugin.api.util.TextFormatter;
import org.homeunix.thecave.buddi.util.BuddiCryptoFactory;
import org.homeunix.thecave.buddi.view.dialogs.BuddiPasswordDialog;
import org.homeunix.thecave.moss.exception.DocumentLoadException;
import org.homeunix.thecave.moss.exception.DocumentSaveException;
import org.homeunix.thecave.moss.exception.OperationCancelledException;
import org.homeunix.thecave.moss.model.AbstractDocument;
import org.homeunix.thecave.moss.util.Log;
import org.homeunix.thecave.moss.util.crypto.CipherException;
import org.homeunix.thecave.moss.util.crypto.IncorrectDocumentFormatException;
import org.homeunix.thecave.moss.util.crypto.IncorrectPasswordException;

public class DocumentImpl extends AbstractDocument implements ModelObject, Document {

	/**
	 * Set this flag in the saveAs() method to specify that we should encrypt the data file.
	 */
	public static final int ENCRYPT_DATA_FILE = 1;

	private DocumentBean dataModel;
	private final Map<String, ModelObject> uidMap = new HashMap<String, ModelObject>();

	//These are maps of model objects to their names.  This serves two purposes: 1) we can easily
	// verify if there is already another object of this name, and 2) we can retrieve it easily,
	// which can be useful for some tasks.
	private final Map<String, Account> accountMap = new HashMap<String, Account>();
	private final Map<String, BudgetCategory> budgetCategoryMap = new HashMap<String, BudgetCategory>();
	private final Map<String, AccountType> typeMap = new HashMap<String, AccountType>();

	private char[] password; 	//Store the password when loaded, and use the same one for save().
								// This is obviously not the best practice to use 
								// from a security point of view.  However, if a malicious
								// third party has good enough access to the machine to be able
								// to read private Java objects, they will just read it when
								// we call MossCryptoFactory anyways - the password is handed 
								// there in plain text as well.  The window is already there - this
								// just increases the time it is available for.


	/**
	 * Creates a new DataModel, given the backing bean.
	 * @param bean
	 * @throws DocumentLoadException
	 */
	public DocumentImpl(DocumentBean bean) throws DocumentLoadException {
		if (bean == null)
			throw new DocumentLoadException("DataModelBean cannot be null!");

		this.dataModel = bean;
		try {
			this.refreshUidMap();
		}
		catch (ModelException me){
			throw new DocumentLoadException(me);
		}
		this.updateAllBalances();
		this.setChanged();
	}

	/**
	 * Attempts to load a data model from file.  Works with Buddi 3 format.  To load a
	 * legacy format, use ModelConverter to get a Bean object, and call the constructor which
	 * takes a DataModelBean.
	 * @param file File to load
	 * @throws DocumentLoadException
	 */
	public DocumentImpl(File file) throws DocumentLoadException, OperationCancelledException {
		if (file == null)
			throw new DocumentLoadException("Error loading model: specfied file is null.");

		if (!file.exists())
			throw new DocumentLoadException("File " + file + " does not exist.");

		if (!file.canRead())
			throw new DocumentLoadException("File " + file + " cannot be opened for reading.");

		Log.debug("Trying to load file " + file);

		//This wil let us know where to save the file to.
		this.setFile(file);

		try {
			InputStream is;
			BuddiCryptoFactory factory = new BuddiCryptoFactory();
			password = null;

			//Loop until the user gets the password correct, hits cancel, 
			// or some other error occurs.
			while (true) {
				try {
					is = factory.getCipherInputStream(new FileInputStream(file), password);

					//Attempt to decode the XML within the (now hopefully unencrypted) data file. 
					XMLDecoder decoder = new XMLDecoder(is);
					Object o = decoder.readObject();
					if (o instanceof DocumentBean){
						dataModel = (DocumentBean) o;
					}
					else {
						throw new IncorrectDocumentFormatException("Could not find a DataModelBean object in the data file!");
					}
					is.close();

					//Refresh the UID Map...
					this.refreshUidMap();

					//Update all balances...
					this.updateAllBalances();

					//Return to calling code... the model is correctly loaded.
					return;
				}
				catch (ModelException me){
					throw new DocumentLoadException("Model exception", me);
				}
				catch (IncorrectPasswordException ipe){
					//The password was not correct.  Prompt for a new one.
					BuddiPasswordDialog passwordDialog = new BuddiPasswordDialog();
					password = passwordDialog.askForPassword(false, true);

					//User hit cancel.  Cancel loading, and pass control to calling code. 
					// Calling code will possibly prompt for a new file.
					if (password == null)
						throw new OperationCancelledException("User cancelled file load.");
				}
				catch (IncorrectDocumentFormatException ife){
					//The document we are trying to load does not have the proper header.
					// This is not a valid Buddi3 data file.
					throw new DocumentLoadException("Incorrect document format", ife);
				}
			}
		}
		catch (CipherException ce){
			//If we get here, something is very wrong.  Perhaps the platform does not support
			// the encryption we have chosen, or something else is wrong.
		}
		catch (IOException ioe){
			//If we get here, chances are good that the file is not valid.  We will display
			// an error message, and open a new file by default.
		}
	}

	/**
	 * Creates a new data model, with some default types and categories.
	 */
	public DocumentImpl() throws ModelException {
		dataModel = new DocumentBean();
		try {
			setDocument(this);
		}
		catch (DocumentAlreadySetException dase){
			throw new ModelException(dase);
		}
		setFile(null); //A null dataFile will prompt for location on first save.

		for (BudgetExpenseDefaultKeys s : BudgetExpenseDefaultKeys.values()){
			try {
				this.addBudgetCategory(ModelFactory.createBudgetCategory(s.toString(), new BudgetPeriodMonthly(), true));
			}
			catch (ModelException me){
				Log.error("Error creating budget category", me);
			}
		}
		for (BudgetIncomeDefaultKeys s : BudgetIncomeDefaultKeys.values()){
			try {
				this.addBudgetCategory(ModelFactory.createBudgetCategory(s.toString(), new BudgetPeriodMonthly(), true));
			}
			catch (ModelException me){
				Log.error("Error creating budget category", me);
			}
		}

		for (TypeDebitDefaultKeys s : TypeDebitDefaultKeys.values()){
			try {
				this.addAccountType(ModelFactory.createAccountType(s.toString(), false));
			}
			catch (ModelException me){
				Log.error("Error creating account type", me);
			}
		}

		for (TypeCreditDefaultKeys s : TypeCreditDefaultKeys.values()){
			try {
				this.addAccountType(ModelFactory.createAccountType(s.toString(), true));
			}
			catch (ModelException me){
				Log.error("Error creating account type", me);
			}
		}	

		this.refreshUidMap();
		this.updateAllBalances();

		this.setChanged();
	}

	/**
	 * Saves the data file to the current file.  If the file has not yet been set,
	 * this method silently returns.
	 * @throws SaveModelException
	 */
	public void save() throws DocumentSaveException {
		saveInternal(getFile(), 0, false);
	}

	/**
	 * Saves the data file to the specified file.  If the specified file is 
	 * null, silently return without error and without saving.
	 * @param file
	 * @param flags.  Flags to set for saving.  AND together for multiple flags.
	 * @throws SaveModelException
	 */
	public void saveAs(File file, int flags) throws DocumentSaveException {
		saveInternal(file, flags, true);
	}

	private void saveInternal(File file, int flags, boolean resetUid) throws DocumentSaveException {
		if (file == null)
			throw new DocumentSaveException("Error saving data file: specified file is null!");
		try {
			if ((flags & ENCRYPT_DATA_FILE) != 0){
				BuddiPasswordDialog passwordDialog = new BuddiPasswordDialog();
				password = passwordDialog.askForPassword(true, false);
			}

			BuddiCryptoFactory factory = new BuddiCryptoFactory();
			OutputStream os = factory.getCipherOutputStream(new FileOutputStream(file), password);


			//TODO Test to make sure this is reset at the right time.
			if (resetUid)
				dataModel.setUid(ModelFactory.getGeneratedUid(dataModel));

			XMLEncoder encoder = new XMLEncoder(os);
			encoder.writeObject(dataModel);
			encoder.flush();
			encoder.close();

			//Save where we last saved this file.
			setFile(file);
			PrefsModel.getInstance().setLastOpenedDataFile(file);

			resetChanged();
		}
		catch (CipherException ce){
			//This means that there is something seriously wrong with the encryption methods.
			// Perhaps the user's platform does not support the given methods.
			// Notify the user, and cancel the save.
			//TODO Notify user

			throw new DocumentSaveException(ce);
		}
		catch (IOException ioe){
			//This means taht there was something wrong with the given file, or writing to
			// it.  Perhaps the user does not have write access, the folder does not exist,
			// or something similar.  Notify the user, and cancel the save.
			//TODO Notify user

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
		encoder.writeObject(dataModel);
		encoder.flush();
		encoder.close();

		return baos.toString();
	}

//	/**
//	* Returns a date that is the beginning of the budget period which contains
//	* the given date.  This depends on the value of getPeriodType.
//	* @param date
//	* @return
//	*/
//	public Date getStartOfBudgetPeriod(Date date){
//	return BudgetPeriodUtil.getStartOfBudgetPeriod(getPeriodType(), date);
//	}

//	/**
//	* Returns a date that is the end of the budget period which contains
//	* the given date.  This depends on the value of getPeriodType.
//	* @param date
//	* @return
//	*/
//	public Date getEndOfBudgetPeriod(Date date){
//	return BudgetPeriodUtil.getEndOfBudgetPeriod(getPeriodType(), date);
//	}



//	public BudgetPeriod getBudgetPeriod(String periodKey){
//	if (dataModel.getBudgetPeriods().get(periodKey) == null) {
//	dataModel.getBudgetPeriods().put(periodKey, new BudgetPeriodBean());
//	dataModel.getBudgetPeriods().get(periodKey).setPeriodDate(getPeriodDate(periodKey));
//	if (Const.DEVEL) Log.debug("Added new budget period for date " + periodKey);

//	getModel().setChanged();
//	}
//	return new BudgetPeriod(this, dataModel.getBudgetPeriods().get(periodKey));		
//	}

	public List<BudgetCategory> getBudgetCategories(){
		return new WrapperBudgetCategoryList(this, dataModel.getBudgetCategories());
	}

	/**
	 * Returns the budget category referenced by Full Name.
	 * @param fullName
	 * @return
	 */
	public BudgetCategory getBudgetCategory(String fullName){
		return budgetCategoryMap.get(fullName);
	}

//	public BudgetPeriodType getBudgetPeriodByName(String name){
//	return budgetPeriodMap.get(name);
//	}

	/**
	 * Returns a list of all accounts in the model.
	 * @return
	 */
	public List<Account> getAccounts() {
		return new WrapperAccountList(this, dataModel.getAccounts());
	}

	/**
	 * Returns the account of the given name.
	 * @param name
	 * @return
	 */
	public Account getAccount(String name){
		return accountMap.get(name);
	}

	/**
	 * Convenience class, to return all sources (Accounts and BudgetCategories).
	 * This method generates a list on the fly.  It is not automatically
	 * updated, so don't rely on it to report model updates.
	 * @return
	 */
	public List<Source> getSources() {
		List<Source> sources = new LinkedList<Source>();
		for (Account a : getAccounts()) {
			sources.add(a);
		}
		for (BudgetCategory bc : getBudgetCategories()) {
			sources.add(bc);
		}

		return sources;
	}

	public List<ScheduledTransaction> getScheduledTransactions() {
		return new WrapperScheduledTransactionList(this, dataModel.getScheduledTransactions());
	}

	public List<Transaction> getTransactions() {
		return new WrapperTransactionList(this, dataModel.getTransactions());
	}

	/**
	 * Returns transactions which are associated with the given source.  This list will update
	 * with changes to the data model.
	 * @param source
	 * @return
	 */
	public List<Transaction> getTransactions(Source source){
		return new FilteredLists.TransactionListFilteredBySource(this, this.getTransactions(), source);
	}

	public List<Transaction> getTransactions(Date startDate, Date endDate){
		return new FilteredLists.TransactionListFilteredByDate(this,
				this.getTransactions(),
				startDate,
				endDate);
	}

	public List<Transaction> getTransactions(Source source, Date startDate, Date endDate){
		return new FilteredLists.TransactionListFilteredByDate(this,
				this.getTransactions(source),
				startDate,
				endDate);
	}

	public List<AccountType> getAccountTypes() {
		return new WrapperTypeList(this, dataModel.getTypes());
	}

	public AccountType getAccountType(String name){
		return typeMap.get(name);
	}

	/**
	 * Adds the given transaction to the data model.
	 * @param transaction
	 */
	public void addTransaction(Transaction transaction) throws ModelException {
		transaction.setDocument(this);
		checkValid(transaction, true, false);

		dataModel.getTransactions().add(transaction.getTransactionBean());
		setChanged();		
	}

	/**
	 * Adds the given scheduled transaction to the data model. 
	 * @param scheduledTransaction
	 */
	public void addScheduledTransaction(ScheduledTransaction scheduledTransaction) throws ModelException {
		scheduledTransaction.setDocument(this);
		checkValid(scheduledTransaction, true, false);

		dataModel.getScheduledTransactions().add(scheduledTransaction.getScheduledTransactionBean());
		setChanged();		
	}

	/**
	 * Removes the given transaction from the data model.
	 * @param transaction
	 */
	public boolean removeTransaction(Transaction transaction) throws ModelException {
		checkValid(transaction, false, false);

		dataModel.getTransactions().remove(transaction.getTransactionBean());
		transaction.setDocument(null);
		setChanged();	

		return true;
	}

	/**
	 * Removes the given scheduled transaction from the data model.
	 * @param scheduledTransaction
	 */
	public boolean removeScheduledTransaction(ScheduledTransaction scheduledTransaction) throws ModelException {
		checkValid(scheduledTransaction, false, false);

		dataModel.getScheduledTransactions().remove(scheduledTransaction.getScheduledTransactionBean());
		scheduledTransaction.setDocument(null);
		setChanged();

		return true;
	}

	/**
	 * Adds the given account to the data model.  
	 * @param account
	 */
	public void addAccount(Account account) throws ModelException {
		account.setDocument(this);
		checkValid(account, true, false);

		dataModel.getAccounts().add(account.getAccountBean());
		accountMap.put(TextFormatter.getTranslation(account.getName()), account);
		setChanged();
	}

	/**
	 * Deletes the given account if possible.
	 * @param account
	 * @return <code>true</code> if the account has been removed from 
	 * the data model, <code>false</code> otherwise.
	 */
	public boolean removeAccount(Account account) throws ModelException {
		checkValid(account, false, false);

		//Check if this is being used somewhere.  If so, 
		// we do the 'delete flag' deletion.
		boolean canDelete = true;

		if (this.getTransactions(account).size() > 0)
			canDelete = false;
		for (ScheduledTransaction st : this.getScheduledTransactions()) {
			if (st.getFrom().equals(account) || st.getTo().equals(account))
				canDelete = false;
		}


		//Actually remove or set the delete flag as needed.
		if (canDelete) {
			dataModel.getAccounts().remove(account.getAccountBean());
			accountMap.remove(account.getName());
			account.setDocument(null);
			setChanged();
		}
		
		return canDelete;
	}

//	/**
//	 * If a previous call to removeAccount() returned false (i.e., the account 
//	 * was not permanently deleted, but only flagged as such), it is possible
//	 * to undelete it.
//	 * @param account
//	 */
//	public void undeleteAccount(Account account) throws ModelException {
//		checkValid(account, false, false);
//
//		account.setDeleted(false);
//
//		setChanged();
//	}

	/**
	 * Adds the given budget category to the data model.
	 * @param budgetCategory
	 */
	public void addBudgetCategory(BudgetCategory budgetCategory) throws ModelException {
		budgetCategory.setDocument(this);
		checkValid(budgetCategory, true, false);

		dataModel.getBudgetCategories().add(budgetCategory.getBudgetCategoryBean());
		budgetCategoryMap.put(TextFormatter.getTranslation(budgetCategory.getFullName()), budgetCategory);
		setChanged();
	}

	/**
	 * Deletes the given budget category.  If possible, it will be removed from the data
	 * model.  If there are references to the budget category, it will only be marked 
	 * as deleted. 
	 * @param budgetCategory
	 * @return <code>true</code> if the account has been removed from 
	 * the data model, <code>false</code> otherwise.
	 */
	public boolean removeBudgetCategory(BudgetCategory budgetCategory) throws ModelException {
		checkValid(budgetCategory, false, false);

		List<BudgetCategory> catsToDelete = new FilteredLists.BudgetCategoryListFilteredByChildren(this, this.getBudgetCategories(), budgetCategory);

		//We either delete all the categories permanently, or none.
		// We run through each one and test to see if we can delete
		// it permanently; if we find one which we cannot, we
		// flag the list as such with permanent = false.
		//
		// Check if this is being used somewhere.  If so, 
		// we do the 'delete flag' deletion.
		boolean canDelete = true;

		for (BudgetCategory bc : catsToDelete) {
			if (this.getTransactions(bc).size() > 0)
				canDelete = false;
			for (ScheduledTransaction st : this.getScheduledTransactions()) {
				if (st.getFrom().equals(bc) || st.getTo().equals(bc))
					canDelete = false;
			}
		}

		//We now execute the deletion.
		for (BudgetCategory bc : catsToDelete) {
			if (canDelete) {
				dataModel.getBudgetCategories().remove(bc.getBudgetCategoryBean());
				budgetCategoryMap.remove(budgetCategory.getFullName());
				budgetCategory.setDocument(null);
			}
		}

		setChanged();

		return canDelete;
	}

//	/**
//	 * If a previous call to removeBudgetCategory() returned false (i.e., the budgetCategory 
//	 * was not permanently deleted, but only flagged as such), it is possible
//	 * to undelete it.
//	 * @param budgetCategory
//	 */
//	public void undeleteBudgetCategory(BudgetCategory budgetCategory) throws ModelException {
//		checkValid(budgetCategory, false, false);
//
//		if (budgetCategory.getParent() != null && budgetCategory.getParent().isDeleted()){
//			undeleteBudgetCategory(budgetCategory.getParent());
//		}
//	}

	/**
	 * Adds the given type to the data model
	 * @param type
	 */
	public void addAccountType(AccountType type) throws ModelException {
		type.setDocument(this);
		checkValid(type, true, false);

		dataModel.getTypes().add(type.getTypeBean());
		typeMap.put(TextFormatter.getTranslation(type.getName()), type);
		setChanged();
	}

	/**
	 * Removes the given type from the data model if possible.  If it is referenced by 
	 * any account, we return 
	 * @param type
	 */
	public boolean removeAccountType(AccountType type) throws ModelException {
		checkValid(type, false, false);

		for (Account a : getAccounts()) {
			if (a.getType().equals(type)) {
				//We cannot delete this type, as it is referenced by an account.
				Log.debug("We cannot delete type " + type + ", as it is referenced by account " + a.getFullName());
				return false;
			}
		}

		dataModel.getTypes().remove(type.getTypeBean());
		typeMap.remove(type.getName());
		type.setDocument(null);
		setChanged();

		return true;
	}

	/**
	 * Returns the UID associated with this object.  Equivalent to
	 * calling object.getUid().
	 * @param object
	 * @return
	 */
	public String getUid(ModelObjectBean object){
		return object.getUid();
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
			if (uidMap.get(object.getBean().getUid()) != null)
				//TODO Do something drastic here... perhaps save an emergency data file, prompt, and quit.
				throw new DataModelProblemException("Identical UID already in model!  Model is probably corrupt!", this); 
		}

	}

	/**
	 * Returns the object with the given UID, or null if it does not exist.
	 * @param uid Unique ID string for the desired object.
	 * @return
	 */
	public ModelObject getObjectByUid(String uid){
		return uidMap.get(uid);
	}

	public void registerObjectInUidMap(ModelObject object){
		uidMap.put(object.getBean().getUid(), object);
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
			accountMap.put(a.getName(), a);
		}

		for (BudgetCategory bc : getBudgetCategories()) {
			checkValid(bc, false, true);
			registerObjectInUidMap(bc);
			budgetCategoryMap.put(bc.getFullName(), bc);
		}

//		for (BudgetPeriodBean bpb : dataModel.getBudgetPeriods().values()) {
//		BudgetPeriod bp = new BudgetPeriod(this, bpb);
//		checkValid(bp, false, true);
//		registerObjectInUidMap(bp);			
//		}

		for (AccountType t : getAccountTypes()) {
			checkValid(t, false, true);
			registerObjectInUidMap(t);	
			typeMap.put(t.getName(), t);
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

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append("\n--Accounts and Types--\n");
		for (AccountType t : getAccountTypes()) {
			sb.append(t).append("\n");

			for (Account a : new AccountListFilteredByType(this, t)) {
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

	public String getUid(){
		return dataModel.getUid();
	}

	public int compareTo(ModelObject o) {
		return getUid().compareTo(o.getUid());
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof DocumentImpl)
			return getUid().equals(((DocumentImpl) obj).getUid());
		return false;
	}

	@Override
	public int hashCode() {
		return getUid().hashCode();
	}

	public void modify() {
		dataModel.setModifiedDate(new Date());
	}

	public ModelObjectBean getBean() {
		return dataModel;
	}

	public Document getDocument() {
		return this;
	}

	public void setDocument(Document document) throws DocumentAlreadySetException {
		return;
	}
}
