/*
 * Created on Jul 29, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.model.impl;

import java.beans.XMLEncoder;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.homeunix.thecave.buddi.model.Account;
import org.homeunix.thecave.buddi.model.AccountType;
import org.homeunix.thecave.buddi.model.BudgetCategory;
import org.homeunix.thecave.buddi.model.Document;
import org.homeunix.thecave.buddi.model.ModelObject;
import org.homeunix.thecave.buddi.model.ScheduledTransaction;
import org.homeunix.thecave.buddi.model.Source;
import org.homeunix.thecave.buddi.model.Transaction;
import org.homeunix.thecave.buddi.model.exception.ModelException;
import org.homeunix.thecave.buddi.model.prefs.PrefsModel;
import org.homeunix.thecave.buddi.util.BuddiCryptoFactory;
import org.homeunix.thecave.buddi.view.dialogs.BuddiPasswordDialog;
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

	
	//User data objects
	private List<Account> accounts = new LinkedList<Account>();
	private List<BudgetCategory> budgetCategories = new LinkedList<BudgetCategory>();
//	private Map<String, BudgetPeriodBean> budgetPeriods = new HashMap<String, BudgetPeriodBean>();
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
	}
	public Account getAccount(String name) {
		// TODO Auto-generated method stub
		return null;
	}
	public AccountType getAccountType(String name) {
		// TODO Auto-generated method stub
		return null;
	}
	public List<AccountType> getAccountTypes() {
		return Collections.unmodifiableList(types);
	}
	public BudgetCategory getBudgetCategory(String fullName) {
		// TODO Auto-generated method stub
		return null;
	}
	public ModelObject getObjectByUid(String uid) {
		// TODO Auto-generated method stub
		return null;
	}
	public List<Source> getSources() {
		// TODO Auto-generated method stub
		return null;
	}
	public List<Transaction> getTransactions(Date startDate, Date endDate) {
		// TODO Auto-generated method stub
		return null;
	}
	public List<Transaction> getTransactions(Source source, Date startDate, Date endDate) {
		// TODO Auto-generated method stub
		return null;
	}
	public List<Transaction> getTransactions(Source source) {
		// TODO Auto-generated method stub
		return null;
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
}
