/*
 * Created on Aug 28, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.model.impl;

import java.beans.XMLDecoder;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

import org.homeunix.thecave.buddi.i18n.keys.BudgetExpenseDefaultKeys;
import org.homeunix.thecave.buddi.i18n.keys.BudgetIncomeDefaultKeys;
import org.homeunix.thecave.buddi.i18n.keys.TypeCreditDefaultKeys;
import org.homeunix.thecave.buddi.i18n.keys.TypeDebitDefaultKeys;
import org.homeunix.thecave.buddi.model.Account;
import org.homeunix.thecave.buddi.model.AccountType;
import org.homeunix.thecave.buddi.model.BudgetCategory;
import org.homeunix.thecave.buddi.model.BudgetCategoryType;
import org.homeunix.thecave.buddi.model.Document;
import org.homeunix.thecave.buddi.model.ModelObject;
import org.homeunix.thecave.buddi.model.ScheduledTransaction;
import org.homeunix.thecave.buddi.model.Source;
import org.homeunix.thecave.buddi.model.Transaction;
import org.homeunix.thecave.buddi.model.exception.InvalidValueException;
import org.homeunix.thecave.buddi.model.exception.ModelException;
import org.homeunix.thecave.buddi.model.periods.BudgetPeriodMonthly;
import org.homeunix.thecave.buddi.util.BuddiCryptoFactory;
import org.homeunix.thecave.buddi.view.dialogs.BuddiPasswordDialog;
import org.homeunix.thecave.moss.exception.DocumentLoadException;
import org.homeunix.thecave.moss.exception.OperationCancelledException;
import org.homeunix.thecave.moss.util.Log;
import org.homeunix.thecave.moss.util.crypto.CipherException;
import org.homeunix.thecave.moss.util.crypto.IncorrectDocumentFormatException;
import org.homeunix.thecave.moss.util.crypto.IncorrectPasswordException;

public class ModelFactory {
	public static Account createAccount(String name, AccountType type) throws InvalidValueException {
		Account a = new AccountImpl();
		
		a.setName(name);
		a.setType(type);
		
		return a;
	}
	
	public static AccountType createAccountType(String name, boolean credit) throws InvalidValueException {
		AccountType at = new AccountTypeImpl();
		
		at.setName(name);
		at.setCredit(credit);
		
		return at;
	}
	
	public static BudgetCategory createBudgetCategory(String name, BudgetCategoryType type, boolean income) throws InvalidValueException {
		BudgetCategory bc = new BudgetCategoryImpl();
		
		bc.setName(name);
		bc.setPeriodType(type);
		bc.setIncome(income);
		
		return bc;
	}
		
	public static Transaction createTransaction(Date date, String description, long amount, Source from, Source to) throws InvalidValueException {
		Transaction t = new TransactionImpl();
		
		t.setDate(date);
		t.setDescription(description);
		t.setAmount(amount);
		t.setFrom(from);
		t.setTo(to);
		
		return t;
	}
	
	public static ScheduledTransaction createScheduledTransaction() throws InvalidValueException {
		ScheduledTransaction st = new ScheduledTransactionImpl();
		
		return st;
	}
	
	
	
	/**
	 * Attempts to load a data model from file.  Works with Buddi 3 format.  To load a
	 * legacy format, use ModelConverter to get a Bean object, and call the constructor which
	 * takes a DataModelBean.
	 * @param file File to load
	 * @throws DocumentLoadException
	 */
	public static Document createDocument(File file) throws DocumentLoadException, OperationCancelledException {
		Document document;
		
		if (file == null)
			throw new DocumentLoadException("Error loading model: specfied file is null.");

		if (!file.exists())
			throw new DocumentLoadException("File " + file + " does not exist.");

		if (!file.canRead())
			throw new DocumentLoadException("File " + file + " cannot be opened for reading.");

		Log.debug("Trying to load file " + file);

		try {
			InputStream is;
			BuddiCryptoFactory factory = new BuddiCryptoFactory();
			char[] password = null;

			//Loop until the user gets the password correct, hits cancel, 
			// or some other error occurs.
			while (true) {
				try {
					is = factory.getCipherInputStream(new FileInputStream(file), password);

					//Attempt to decode the XML within the (now hopefully unencrypted) data file. 
					XMLDecoder decoder = new XMLDecoder(is);
					Object o = decoder.readObject();
					if (o instanceof Document){
						document = (Document) o;
					}
					else {
						throw new IncorrectDocumentFormatException("Could not find a DataModelBean object in the data file!");
					}
					is.close();

					//Refresh the UID Map...
//					document.refreshUidMap();

					//This wil let us know where to save the file to.
					document.setFile(file);
					
					//Update all balances...
					document.updateAllBalances();

					//Return to calling code... the model is correctly loaded.
					return document;
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
			throw new DocumentLoadException(ce);
		}
		catch (IOException ioe){
			//If we get here, chances are good that the file is not valid.
			throw new DocumentLoadException(ioe);
		}
	}

	/**
	 * Creates a new data model, with some default types and categories.
	 */
	public static Document createDocument() throws ModelException {
		Document document = new DocumentImpl();
		document.setFile(null); //A null dataFile will prompt for location on first save.

		for (BudgetExpenseDefaultKeys s : BudgetExpenseDefaultKeys.values()){
			try {
				document.addBudgetCategory(ModelFactory.createBudgetCategory(s.toString(), new BudgetPeriodMonthly(), true));
			}
			catch (ModelException me){
				Log.error("Error creating budget category", me);
			}
		}
		for (BudgetIncomeDefaultKeys s : BudgetIncomeDefaultKeys.values()){
			try {
				document.addBudgetCategory(ModelFactory.createBudgetCategory(s.toString(), new BudgetPeriodMonthly(), true));
			}
			catch (ModelException me){
				Log.error("Error creating budget category", me);
			}
		}

		for (TypeDebitDefaultKeys s : TypeDebitDefaultKeys.values()){
			try {
				document.addAccountType(ModelFactory.createAccountType(s.toString(), false));
			}
			catch (ModelException me){
				Log.error("Error creating account type", me);
			}
		}

		for (TypeCreditDefaultKeys s : TypeCreditDefaultKeys.values()){
			try {
				document.addAccountType(ModelFactory.createAccountType(s.toString(), true));
			}
			catch (ModelException me){
				Log.error("Error creating account type", me);
			}
		}	

		document.updateAllBalances();
		document.setChanged();
		
		return document;
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
	public static String getGeneratedUid(ModelObject object){
		long time = System.currentTimeMillis();
		int random = (int) (Math.random() * 0xFFFF);
		int hash = object.hashCode() & 0xFFFF;

		String uid = object.getClass().getCanonicalName() + "-" + Long.toHexString(time) + "-" + Integer.toHexString(random) + "-" + Integer.toHexString(hash);

		return uid;
	}
}
