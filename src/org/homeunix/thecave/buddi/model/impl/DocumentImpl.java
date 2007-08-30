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
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.homeunix.thecave.buddi.model.Account;
import org.homeunix.thecave.buddi.model.AccountType;
import org.homeunix.thecave.buddi.model.BudgetCategory;
import org.homeunix.thecave.buddi.model.Document;
import org.homeunix.thecave.buddi.model.ModelObject;
import org.homeunix.thecave.buddi.model.ScheduledTransaction;
import org.homeunix.thecave.buddi.model.Source;
import org.homeunix.thecave.buddi.model.Transaction;
import org.homeunix.thecave.buddi.model.exception.DataModelProblemException;
import org.homeunix.thecave.buddi.model.exception.ModelException;
import org.homeunix.thecave.buddi.model.impl.FilteredLists.AccountListFilteredByType;
import org.homeunix.thecave.buddi.model.impl.FilteredLists.BudgetCategoryListFilteredByParent;
import org.homeunix.thecave.buddi.model.impl.FilteredLists.TransactionListFilteredByDate;
import org.homeunix.thecave.buddi.model.impl.FilteredLists.TransactionListFilteredBySource;
import org.homeunix.thecave.buddi.model.prefs.PrefsModel;
import org.homeunix.thecave.buddi.util.BuddiCryptoFactory;
import org.homeunix.thecave.buddi.view.dialogs.BuddiPasswordDialog;
import org.homeunix.thecave.moss.data.list.CompositeList;
import org.homeunix.thecave.moss.exception.DocumentSaveException;
import org.homeunix.thecave.moss.model.AbstractDocument;
import org.homeunix.thecave.moss.util.crypto.CipherException;



/**
 * The main container class for the new data model, to be implemented in Buddi version 3.0.
 * This contains all the data, most of it in list form.  This object is the root of the XML
 * file as serialized by XMLEncoder.
 * 
 * @author wyatt
 */
public class DocumentImpl extends AbstractDocument implements ModelObject, Document {
	public static final int ENCRYPT_DATA_FILE = 1;

	private char[] password; 	//Store the password when loaded, and use the same one for save().
								// This is obviously not the best practice to use 
								// from a security point of view.  However, if a malicious
								// third party has good enough access to the machine to be able
								// to read private Java objects, they will just read it when
								// we call MossCryptoFactory anyways - the password is handed 
								// there in plain text as well.  The window is already there - this
								// just increases the time it is available for.

	//Convenience class for checking if objects are already entered.
	private final Map<String, ModelObject> uidMap = new HashMap<String, ModelObject>();
	
	//User data objects
	private List<Account> accounts = new LinkedList<Account>();
	private List<BudgetCategory> budgetCategories = new LinkedList<BudgetCategory>();
	private List<AccountType> types = new LinkedList<AccountType>();
	private List<Transaction> transactions = new LinkedList<Transaction>();
	private List<ScheduledTransaction> scheduledTransactions = new LinkedList<ScheduledTransaction>();

	//Model object data
	private Date modifiedDate;
	private String uid;

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
		return Collections.unmodifiableList(transactions);
	}
	public void setTransactions(List<Transaction> transactions) {
		this.transactions = transactions;
		Collections.sort(transactions);
	}
	public List<AccountType> getTypes() {
		checkLists();
		return types;
	}
	public void setTypes(List<AccountType> types) {
		this.types = types;
	}




	public void addAccount(Account account) throws ModelException {
		accounts.add(account);
	}
	public void addAccountType(AccountType type) throws ModelException {
		types.add(type);
	}
	public void addBudgetCategory(BudgetCategory budgetCategory) throws ModelException {
		budgetCategories.add(budgetCategory);
	}
	public void addScheduledTransaction(ScheduledTransaction scheduledTransaction) throws ModelException {
		scheduledTransactions.add(scheduledTransaction);
	}
	public void addTransaction(Transaction transaction) throws ModelException {
		transactions.add(transaction);
		Collections.sort(transactions);
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
	public List<AccountType> getAccountTypes() {
		return Collections.unmodifiableList(types);
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
	public boolean removeAccount(Account account) throws ModelException {
		return accounts.remove(account);
	}
	public boolean removeAccountType(AccountType type) throws ModelException {
		return types.remove(type);
	}
	public boolean removeBudgetCategory(BudgetCategory budgetCategory) throws ModelException {
		return budgetCategories.remove(budgetCategory);
	}
	public boolean removeScheduledTransaction(ScheduledTransaction scheduledTransaction) throws ModelException {
		return scheduledTransactions.remove(scheduledTransaction);
	}
	public boolean removeTransaction(Transaction transaction) throws ModelException {
		return transactions.remove(transaction);
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
				setUid(ModelFactory.getGeneratedUid(this));

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

			//Save where we last saved this file.
			setFile(file);
			PrefsModel.getInstance().setLastOpenedDataFile(file);

			resetChanged();
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
		if (obj instanceof ModelObjectImpl)
			return this.getUid().equals(((ModelObjectImpl) obj).getUid());
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
	
	private void checkLists(){
		if (this.accounts == null)
			this.accounts = new LinkedList<Account>();
		if (this.transactions == null)
			this.transactions = new LinkedList<Transaction>();
		if (this.scheduledTransactions == null)
			this.scheduledTransactions = new LinkedList<ScheduledTransaction>();
		if (this.types == null)
			this.types = new LinkedList<AccountType>();
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

}
