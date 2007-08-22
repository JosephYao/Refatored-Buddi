/*
 * Created on Jul 30, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.model;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.homeunix.thecave.buddi.Const;
import org.homeunix.thecave.buddi.i18n.keys.BudgetExpenseDefaultKeys;
import org.homeunix.thecave.buddi.i18n.keys.BudgetIncomeDefaultKeys;
import org.homeunix.thecave.buddi.i18n.keys.BudgetPeriodKeys;
import org.homeunix.thecave.buddi.i18n.keys.TypeCreditDefaultKeys;
import org.homeunix.thecave.buddi.i18n.keys.TypeDebitDefaultKeys;
import org.homeunix.thecave.buddi.model.FilteredLists.AccountListFilteredByType;
import org.homeunix.thecave.buddi.model.FilteredLists.BudgetCategoryListFilteredByParent;
import org.homeunix.thecave.buddi.model.WrapperLists.WrapperAccountList;
import org.homeunix.thecave.buddi.model.WrapperLists.WrapperBudgetCategoryList;
import org.homeunix.thecave.buddi.model.WrapperLists.WrapperScheduledTransactionList;
import org.homeunix.thecave.buddi.model.WrapperLists.WrapperTransactionList;
import org.homeunix.thecave.buddi.model.WrapperLists.WrapperTypeList;
import org.homeunix.thecave.buddi.model.beans.BudgetPeriodBean;
import org.homeunix.thecave.buddi.model.beans.DataModelBean;
import org.homeunix.thecave.buddi.model.beans.ModelObjectBean;
import org.homeunix.thecave.buddi.model.exception.DataModelProblemException;
import org.homeunix.thecave.buddi.model.exception.DocumentLoadException;
import org.homeunix.thecave.buddi.model.prefs.PrefsModel;
import org.homeunix.thecave.buddi.util.BuddiCipherStreamFactory;
import org.homeunix.thecave.buddi.util.BudgetPeriodUtil;
import org.homeunix.thecave.buddi.view.dialogs.BuddiPasswordDialog;
import org.homeunix.thecave.moss.exception.DocumentSaveException;
import org.homeunix.thecave.moss.model.AbstractDocument;
import org.homeunix.thecave.moss.util.Log;
import org.homeunix.thecave.moss.util.crypto.CipherException;
import org.homeunix.thecave.moss.util.crypto.IncorrectDocumentFormatException;
import org.homeunix.thecave.moss.util.crypto.IncorrectPasswordException;

public class DataModel extends AbstractDocument {

	/**
	 * Set this flag in the saveAs() method to specify that we should encrypt the data file.
	 */
	public static final int ENCRYPT_DATA_FILE = 1;
	
	private DataModelBean dataModel;
	private final Map<String, ModelObject> uidMap = new HashMap<String, ModelObject>();
	
	private char[] password; //Store the password on load.  This is not the best practice 
							 // from a security point of view, but I suppose it will work...
	
	/**
	 * Attempts to load a data model from file.  Works with Buddi 3 and legacy formats (although
	 * of course Buddi 3 is preferred). 
	 * @param file File to load
	 * @throws DocumentLoadException
	 */
	public DataModel(File file) throws DocumentLoadException {
		if (file == null)
			throw new DocumentLoadException("Error loading model: specfied file is null.");

		Log.debug("Trying to load file " + file);

		//This wil let us know where to save the file to.
		this.setFile(file);

		try {
			InputStream is;
			BuddiCipherStreamFactory factory = new BuddiCipherStreamFactory();
			password = null;
			
			//Loop until the user gets the password correct, hits cancel, 
			// or some other error occurs.
			while (true) {
				try {
					is = factory.getCipherInputStream(new FileInputStream(file), password);

					//Attempt to decode the XML within the (now hopefully unencrypted) data file. 
					XMLDecoder decoder = new XMLDecoder(is);
					Object o = decoder.readObject();
					if (o instanceof DataModelBean){
						dataModel = (DataModelBean) o;
					}
					else {
						throw new IncorrectDocumentFormatException("Could not find a DataModelBean object in the data file!");
					}
					is.close();
					
					//Refresh the UID Map...
					this.refreshUidMap();
					
					//Return to calling code... the model is correctly loaded.
					return;
				}
				catch (IncorrectPasswordException ipe){
					//The password was not correct.  Prompt for a new one.
					BuddiPasswordDialog passwordDialog = new BuddiPasswordDialog();
					password = passwordDialog.askForPassword(false, false);
					
					//User hit cancel (or entered an empty password).  Cancel loading, and 
					// possibly prompt for a new file.
					if (password == null)
						throw new DocumentLoadException("User cancelled file load.");
				}
				catch (IncorrectDocumentFormatException ife){
					//The document we are trying to load does not have the proper header.
					// This is not a valid Buddi3 data file.  Break from the password loop
					// and TODO either create a new file, or load a different one.
					break;
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
	public DataModel() {
		dataModel = new DataModelBean();
		setFile(null); //A null dataFile will prompt for location on first save.

		for (BudgetExpenseDefaultKeys s : BudgetExpenseDefaultKeys.values()){
			addBudgetCategory(new BudgetCategory(this, s.toString(), false));
		}
		for (BudgetIncomeDefaultKeys s : BudgetIncomeDefaultKeys.values()){
			addBudgetCategory(new BudgetCategory(this, s.toString(), true));
		}

		for (TypeDebitDefaultKeys s : TypeDebitDefaultKeys.values()){
			addType(new Type(this, s.toString(), false));
		}

		for (TypeCreditDefaultKeys s : TypeCreditDefaultKeys.values()){
			addType(new Type(this, s.toString(), true));
		}	

		refreshUidMap();

		setChanged();
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
			password = null;
			if ((flags & ENCRYPT_DATA_FILE) != 0){
				BuddiPasswordDialog passwordDialog = new BuddiPasswordDialog();
				password = passwordDialog.askForPassword(true, false);
			}
			
			BuddiCipherStreamFactory factory = new BuddiCipherStreamFactory();
			OutputStream os = factory.getCipherOutputStream(new FileOutputStream(file), password);
			

			//TODO Test to make sure this is reset at the right time.
			if (resetUid)
				dataModel.setUid(getGeneratedUid(dataModel));

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

	/**
	 * Returns a date that is the beginning of the budget period which contains
	 * the given date.  This depends on the value of getPeriodType.
	 * @param date
	 * @return
	 */
	public Date getStartOfBudgetPeriod(Date date){
		return BudgetPeriodUtil.getStartOfBudgetPeriod(getPeriodType(), date);
	}

	public BudgetPeriodKeys getPeriodType() {
		if (dataModel.getPeriodType() == null)
			setPeriodType(BudgetPeriodKeys.BUDGET_PERIOD_MONTH);
		return BudgetPeriodKeys.valueOf(dataModel.getPeriodType());
	}
	public void setPeriodType(BudgetPeriodKeys periodType) {
		dataModel.setPeriodType(periodType.toString());
	}

	public List<Account> getAccounts() {
		return new WrapperAccountList(this, dataModel.getAccounts());
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

	public String getPeriodKey(Date periodDate){
		return getPeriodType() + ":" + getStartOfBudgetPeriod(periodDate).getTime();
	}

	/**
	 * Reverses getPeriodKey
	 * @param periodKey
	 * @return
	 */
	public Date getPeriodDate(String periodKey){
		String[] splitKey = periodKey.split(":");
		if (splitKey.length > 1){
			long l = Long.parseLong(splitKey[1]);
			return getStartOfBudgetPeriod(new Date(l));
		}

		throw new DataModelProblemException("Cannot parse date from key " + periodKey, this);
	}

	public BudgetPeriod getBudgetPeriod(Date periodDate){
		return getBudgetPeriod(getPeriodKey(periodDate));
	}

	/**
	 * Returns a list of BudgetPeriods, covering the entire range of periods
	 * occupied by startDate to endDate.
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public List<BudgetPeriod> getBudgetPeriodsInRange(Date startDate, Date endDate){
		List<BudgetPeriod> budgetPeriods = new LinkedList<BudgetPeriod>();

		Date temp = BudgetPeriodUtil.getStartOfBudgetPeriod(getPeriodType(), startDate);

		while (temp.before(BudgetPeriodUtil.getEndOfBudgetPeriod(getPeriodType(), endDate))){
			budgetPeriods.add(getBudgetPeriod(temp));
			temp = BudgetPeriodUtil.getNextBudgetPeriod(getPeriodType(), temp);
		}

		return budgetPeriods;
	}

	public BudgetPeriod getBudgetPeriod(String periodKey){
		if (dataModel.getBudgetPeriods().get(periodKey) == null) {
			dataModel.getBudgetPeriods().put(periodKey, new BudgetPeriodBean());
			dataModel.getBudgetPeriods().get(periodKey).setPeriodDate(getPeriodDate(periodKey));
			if (Const.DEVEL) Log.debug("Added new budget period for date " + periodKey);

			setChanged();
		}
		return new BudgetPeriod(this, dataModel.getBudgetPeriods().get(periodKey));		
	}

	public List<BudgetCategory> getBudgetCategories(){
		return new WrapperBudgetCategoryList(this, dataModel.getBudgetCategories());
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

	public List<Type> getTypes() {
		return new WrapperTypeList(this, dataModel.getTypes());
	}

	/**
	 * Adds the given transaction to the data model.
	 * @param transaction
	 */
	public void addTransaction(Transaction transaction){
		checkValid(transaction, true, false);

		dataModel.getTransactions().add(transaction.getTransactionBean());
		setChanged();		
	}

	/**
	 * Adds the given scheduled transaction to the data model. 
	 * @param scheduledTransaction
	 */
	public void addScheduledTransaction(ScheduledTransaction scheduledTransaction){
		checkValid(scheduledTransaction, true, false);

		dataModel.getScheduledTransactions().add(scheduledTransaction.getScheduledTranasactionBean());
		setChanged();		
	}

	/**
	 * Removes the given transaction from the data model.
	 * @param transaction
	 */
	public boolean removeTransaction(Transaction transaction){
		checkValid(transaction, false, false);

		dataModel.getTransactions().remove(transaction.getTransactionBean());
		setChanged();	
		
		return true;
	}

	/**
	 * Removes the given scheduled transaction from the data model.
	 * @param scheduledTransaction
	 */
	public boolean removeScheduledTransaction(ScheduledTransaction scheduledTransaction){
		checkValid(scheduledTransaction, false, false);

		dataModel.getScheduledTransactions().remove(scheduledTransaction.getScheduledTranasactionBean());
		setChanged();
		
		return true;
	}

	/**
	 * Adds the given account to the data model.  
	 * @param account
	 */
	public void addAccount(Account account){
		checkValid(account, true, false);

		dataModel.getAccounts().add(account.getAccountBean());
		setChanged();
	}

	/**
	 * Deletes the given account.  If possible, it will be removed from the data
	 * model.  If there are references to the account, it will only be marked 
	 * as deleted. 
	 * @param account
	 * @return <code>true</code> if the account has been removed from 
	 * the data model, <code>false</code> otherwise.
	 */
	public boolean removeAccount(Account account){
		checkValid(account, false, false);

		//Check if this is being used somewhere.  If so, 
		// we do the 'delete flag' deletion.
		boolean permanent = true;

		if (this.getTransactions(account).size() > 0)
			permanent = false;
		for (ScheduledTransaction st : this.getScheduledTransactions()) {
			if (st.getFrom().equals(account) || st.getTo().equals(account))
				permanent = false;
		}


		//Actually remove or set the delete flag as needed.
		if (!permanent)
			account.setDeleted(true);
		else 
			dataModel.getAccounts().remove(account.getAccountBean());

		setChanged();
		
		return permanent;
	}

	/**
	 * If a previous call to removeAccount() returned false (i.e., the account 
	 * was not permanently deleted, but only flagged as such), it is possible
	 * to undelete it.
	 * @param account
	 */
	public void undeleteAccount(Account account){
		checkValid(account, false, false);

		account.setDeleted(false);

		setChanged();
	}

	/**
	 * Adds the given budget category to the data model.
	 * @param budgetCategory
	 */
	public void addBudgetCategory(BudgetCategory budgetCategory){
		checkValid(budgetCategory, true, false);

		dataModel.getBudgetCategories().add(budgetCategory.getBudgetCategoryBean());
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
	public boolean removeBudgetCategory(BudgetCategory budgetCategory){
		checkValid(budgetCategory, false, false);

		List<BudgetCategory> catsToDelete = new FilteredLists.BudgetCategoryListFilteredByChildren(this, budgetCategory);

		//We either delete all the categories permanently, or none.
		// We run through each one and test to see if we can delete
		// it permanently; if we find one which we cannot, we
		// flag the list as such with permanent = false.
		//
		// Check if this is being used somewhere.  If so, 
		// we do the 'delete flag' deletion.
		boolean permanent = true;

		for (BudgetCategory bc : catsToDelete) {
			if (this.getTransactions(bc).size() > 0)
				permanent = false;
			for (ScheduledTransaction st : this.getScheduledTransactions()) {
				if (st.getFrom().equals(bc) || st.getTo().equals(bc))
					permanent = false;
			}
		}

		//We now execute the deletion.
		for (BudgetCategory bc : catsToDelete) {
			if (!permanent){
				bc.setDeleted(true);
			}
			else {
				dataModel.getBudgetCategories().remove(bc.getBudgetCategoryBean());
			}
		}

		setChanged();
		
		return permanent;
	}

	/**
	 * If a previous call to removeBudgetCategory() returned false (i.e., the budgetCategory 
	 * was not permanently deleted, but only flagged as such), it is possible
	 * to undelete it.
	 * @param budgetCategory
	 */
	public void undeleteBudgetCategory(BudgetCategory budgetCategory){
		checkValid(budgetCategory, false, false);

		if (budgetCategory.getParent() != null && budgetCategory.getParent().isDeleted()){
			undeleteBudgetCategory(budgetCategory.getParent());
		}
	}

	/**
	 * Adds the given type to the data model
	 * @param type
	 */
	public void addType(Type type){
		checkValid(type, true, false);

		dataModel.getTypes().add(type.getTypeBean());
		setChanged();
	}

	/**
	 * Removes the given type from the data model if possible.  If it is referenced by 
	 * any account, we return 
	 * @param type
	 */
	public boolean removeType(Type type){
		checkValid(type, false, false);
		
		for (Account a : getAccounts()) {
			if (a.getType().equals(type)) {
				//We cannot delete this type, as it is referenced by an account.
				Log.debug("We cannot delete type " + type + ", as it is referenced by account " + a.getFullName());
				return false;
			}
		}

		dataModel.getTypes().remove(type.getTypeBean());
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
	private void checkValid(ModelObject object, boolean isAddOperation, boolean isUidRefresh){
		if (!object.getModel().equals(this))
			throw new DataModelProblemException("Cannot modify an object not in this model.", this);

		if (isAddOperation){
//			if (this.getUid(object.getBean()) != null)
//			throw new DataModelProblemException("Cannot have the same UID for multiple objects in the model.", this);

			if (object instanceof Account){
				for (Account a : getAccounts()) {
					if (a.getName().equalsIgnoreCase(((Account) object).getName()))
						throw new DataModelProblemException("Cannot have multiple accounts with the same name.", this);
				}
			}

			if (object instanceof BudgetCategory){
				for (BudgetCategory bc : getBudgetCategories()) {
					if (bc.getFullName().equalsIgnoreCase(((BudgetCategory) object).getFullName()))
						throw new DataModelProblemException("Cannot have multiple budget categories with the same name.", this);
				}
			}

			if (object instanceof ScheduledTransaction){
				for (ScheduledTransaction s : getScheduledTransactions()) {
					if (s.getScheduleName().equalsIgnoreCase(((ScheduledTransaction) object).getScheduleName()))
						throw new DataModelProblemException("Cannot have multiple scheduled transactions with the same name.", this);
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

	/**
	 * Generate a UID string for a particular object.  This is guaranteed to be unique
	 * for each call to this method, even if the object is the same.  It is generated 
	 * by concatinating the following information, separated by the dash (-):
	 * 
	 * 1) The canonical name of the object (e.g. org.homeunix.thecave.buddi.model3.Account).
	 * 2) A hexadecimal representation of the current system time in milliseconds
	 * 3) A hexadecimal representation of a 16 bit random number
	 * 4) A hexadecimal representation of the 16 least significant bits of this object's hash code (object.hashCode()).
	 * @param object
	 * @return
	 */
	public static String getGeneratedUid(ModelObjectBean object){
		long time = System.currentTimeMillis();
		int random = (int) (Math.random() * 0xFFFF);
		int hash = object.hashCode() & 0xFFFF;

		String uid = object.getClass().getCanonicalName() + "-" + Long.toHexString(time) + "-" + Integer.toHexString(random) + "-" + Integer.toHexString(hash);

		return uid;
	}

	public void registerObjectInUidMap(ModelObject object){
		uidMap.put(object.getBean().getUid(), object);
	}

	/**
	 * Updates the balances of all accounts.  Iterates through all accounts, and
	 * calls the updateBalance() method for each. 
	 */
	public void updateAllBalances(){
		for (Account a : getAccounts()) {
			a.updateBalance();
		}
	}

	/**
	 * Refreshes the UID map.  This is a relatively expensive operation, and as such is 
	 * generally only done at file load time.
	 * 
	 * We iterate through all objects for each data type, and add them to the UID map.
	 * This will associate the object with their UID as the key in the Map.
	 */
	public void refreshUidMap(){
		uidMap.clear();

		for (Account a : getAccounts()) {
			checkValid(a, false, true);
			registerObjectInUidMap(a);
		}

		for (BudgetCategory bc : getBudgetCategories()) {
			checkValid(bc, false, true);
			registerObjectInUidMap(bc);
		}

		for (BudgetPeriodBean bpb : dataModel.getBudgetPeriods().values()) {
			BudgetPeriod bp = new BudgetPeriod(this, bpb);
			checkValid(bp, false, true);
			registerObjectInUidMap(bp);			
		}

		for (Type t : getTypes()) {
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

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append("\n--Accounts and Types--\n");
		for (Type t : getTypes()) {
			sb.append(t).append("\n");

			for (Account a : new AccountListFilteredByType(this, t)) {
				sb.append("\t").append(a).append("\n");
			}
		}

		sb.append("\n--Budget Categories--\n");

		//We only show 3 levels here; if there are more, we figure that it is not needed to
		// output them in the toString method, as it is just for troubleshooting.
		for (BudgetCategory bc : new BudgetCategoryListFilteredByParent(this, null)) {
			sb.append(bc).append("\n");
			for (BudgetCategory child1 : new BudgetCategoryListFilteredByParent(this, bc)) {
				sb.append("\t").append(child1).append("\n");
				for (BudgetCategory child2 : new BudgetCategoryListFilteredByParent(this, child1)) {
					sb.append("\t\t").append(child2).append("\n");	
				}				
			}
		}

		sb.append("\n--Budget Periods--\n");
		List<String> periodDates = new LinkedList<String>(dataModel.getBudgetPeriods().keySet());
		Collections.sort(periodDates);
		for (String d : periodDates) {
			sb.append(d).append(getBudgetPeriod(d));
		}
		sb.append("\n--Transactions--\n");
		sb.append("Total transactions: ").append(getTransactions().size()).append("\n");
		for (Transaction t : getTransactions()) {
			sb.append(t.toString()).append("\n");
		}
		sb.append("--");

		return sb.toString();
	}
	
	/**
	 * Returns the UID string for this object.
	 * @return
	 */
	public String getUid(){
		return dataModel.getUid();
	}
}
