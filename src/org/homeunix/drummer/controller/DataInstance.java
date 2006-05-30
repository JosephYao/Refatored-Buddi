/*
 * Created on May 12, 2006 by wyatt
 */
package org.homeunix.drummer.controller;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Vector;

import javax.swing.JOptionPane;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMLResourceFactoryImpl;
import org.homeunix.drummer.Const;
import org.homeunix.drummer.Strings;
import org.homeunix.drummer.model.Account;
import org.homeunix.drummer.model.Accounts;
import org.homeunix.drummer.model.Categories;
import org.homeunix.drummer.model.Category;
import org.homeunix.drummer.model.DataModel;
import org.homeunix.drummer.model.ModelFactory;
import org.homeunix.drummer.model.ModelPackage;
import org.homeunix.drummer.model.Source;
import org.homeunix.drummer.model.Transaction;
import org.homeunix.drummer.model.Transactions;
import org.homeunix.drummer.model.Type;
import org.homeunix.drummer.model.Types;
import org.homeunix.drummer.model.impl.ModelFactoryImpl;
import org.homeunix.drummer.util.FileFunctions;
import org.homeunix.drummer.util.Formatter;
import org.homeunix.drummer.util.Log;

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
	
	private DataInstance(){
		File dataFile;
		
		if (PrefsInstance.getInstance().getPrefs().getDataFile() != null){
			dataFile = new File(PrefsInstance.getInstance().getPrefs().getDataFile());
			try{
				String backupFileLocation = 
					PrefsInstance.getInstance().getPrefs().getDataFile()
					.replaceAll(Const.DATA_FILE_EXTENSION + "$", "") 
					+ " " + Formatter.getInstance().getFilenameDateFormat().format(new Date())
					+ Const.DATA_FILE_EXTENSION;
				File backupFile = new File(backupFileLocation);
				if (!backupFile.exists()){
					FileFunctions.copyFile(dataFile, backupFile);
					Log.debug("Backing up file to " + backupFile);
				}
			}
			catch(IOException ioe){
				Log.emergency("Failure when attempting to backup when starting program: " + ioe);
			}
		}
		else
			dataFile = null;
		
		loadDataModel(dataFile, false);
	}
	
	public void loadDataModel(File locationFile, boolean forceNewFile){
		
		if (!forceNewFile){
			while (locationFile == null || !locationFile.getParentFile().exists()){
				JOptionPane.showMessageDialog(
						null,
						"There was a problem reading the data file:\n"
						+ locationFile.getAbsolutePath() + "\n"
						+ "The requested directory does not exist.\n"
						+ "Please choose an existing data file, or a\n"
						+ "folder in which to create a new data file.",
						"Missing Data File",
						JOptionPane.ERROR_MESSAGE);
				
				String file = PrefsInstance.chooseDataFile();
				if (file != null)
					locationFile = new File(file);
				else
					locationFile = null;
			}
			
			if (!locationFile.exists() && locationFile.getParentFile().exists()){
				locationFile = new File(locationFile.getParent() + File.separator + Const.DATA_DEFAULT_FILENAME + Const.DATA_FILE_EXTENSION);
			}
		}
		
		try{
			if (forceNewFile){
				throw new Exception();
			}
			
			// Create a resource set.
			resourceSet = new ResourceSetImpl();
			
			// Register the default resource factory -- only needed for stand-alone!
			resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put(
					Resource.Factory.Registry.DEFAULT_EXTENSION, new XMLResourceFactoryImpl());
			
			// Register the package
			@SuppressWarnings("unused") 
			ModelPackage modelPackage = ModelPackage.eINSTANCE;
			
			// Get the URI of the model file.
			URI fileURI = URI.createFileURI(locationFile.getAbsolutePath());
			
			// Demand load the resource for this file.
			Resource resource = resourceSet.getResource(fileURI, true);
			
			// Print the contents of the resource to System.out.
			EList contents = resource.getContents();
			if (contents.size() > 0){
				dataModel = (DataModel) contents.get(0);
			}
		}
		//If there is a problem opening the file, we will prompt to make a new one
		catch (Exception e){
			if (locationFile == null)
				locationFile = new File(Const.DATA_DEFAULT_FILENAME + Const.DATA_FILE_EXTENSION);
			
			PrefsInstance.getInstance().getPrefs().setDataFile(locationFile.getAbsolutePath());
			PrefsInstance.getInstance().savePrefs();
			
			if (forceNewFile || !locationFile.exists() || JOptionPane.showConfirmDialog(
					null,
					"There was a problem reading the data file:\n" 
					+ locationFile.getAbsolutePath() + ".\n"
					+ "The data file exists, but it appears to be corrupted.  " 
					+ "Do you want to overwrite it, and create a new file?\n" 
					+ "All information currently in the file will be lost.",
					"Create new Datastore?",
					JOptionPane.YES_NO_OPTION,
					JOptionPane.WARNING_MESSAGE) == JOptionPane.YES_OPTION){
				
				if (!locationFile.exists() && !forceNewFile){
					JOptionPane.showMessageDialog(
							null,
							"Created a new data file:\n"
							+ locationFile.getAbsolutePath(),
							"Created new Data File",
							JOptionPane.INFORMATION_MESSAGE);
				}
				
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
				String[] expenseNames = {
						Strings.AUTO, 
						Strings.ENTERTAINMENT, 
						Strings.HOUSEHOLD, 
						Strings.GROCERIES, 
						Strings.INVESTMENT_EXPENSES, 
						Strings.MISC_EXPENSES, 
						Strings.UTILITIES
				};
				String[] incomeNames = {
						Strings.BONUS, 
						Strings.SALARY, 
						Strings.INVESTMENT_INCOME
				};
				
				for (String s : expenseNames){
					Category c = getDataModelFactory().createCategory();
					c.setName(s);
					c.setBudgetedAmount(0);
					c.setIncome(false);
					categories.getCategories().add(c);
				}
				for (String s : incomeNames){
					Category c = getDataModelFactory().createCategory();
					c.setName(s);
					c.setBudgetedAmount(0);
					c.setIncome(true);
					categories.getCategories().add(c);
				}
				
				//Default starting types - debit
				String[] debitNames = {
						Strings.CASH, 
						Strings.SAVINGS,
						Strings.CHEQUING,
						Strings.INVESTMENT				
				};
				
				for (String s : debitNames){
					addType(s, false);
				}				

				//Default starting types - credit
				String[] creditNames = {
						Strings.LIABILITY,
						Strings.CREDIT_CARD, 
						Strings.LINE_OF_CREDIT
				};
				
				for (String s : creditNames){
					addType(s, true);
				}				
				
				ResourceSet resourceSet = new ResourceSetImpl();			
				resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put(
						Resource.Factory.Registry.DEFAULT_EXTENSION, new XMLResourceFactoryImpl());
				
				URI fileURI = URI.createFileURI(locationFile.toString());
				Log.debug("Saving new file to " + locationFile.toString());
				Resource resource = resourceSet.createResource(fileURI);			
				resource.getContents().add(dataModel);
				
				try{
					resource.save(Collections.EMPTY_MAP);
				}
				catch (IOException ioe){}
			}
			else{
				JOptionPane.showMessageDialog(
						null, 
						"I cannot read file, and you requested that I do not overwrite the old one.  Exiting.",
						"Exiting Program",
						JOptionPane.ERROR_MESSAGE);
				System.exit(0);
			}
		}
	}
	
	public void saveDataModel(){
		saveDataModel(PrefsInstance.getInstance().getPrefs().getDataFile());
	}
	
	public void saveDataModel(String location){		
		File saveLocation = new File(location);
		File backupLocation = new File(saveLocation.getAbsolutePath() + "~");

		try{
			if (saveLocation.exists())
				FileFunctions.copyFile(saveLocation, backupLocation);
			
			URI fileURI = URI.createFileURI(saveLocation.getAbsolutePath());
			
			Log.debug("Data saved to " + location);
			
			Resource resource = resourceSet.createResource(fileURI);
			
			resource.getContents().add(getDataModel());
			
			resource.save(Collections.EMPTY_MAP);
		}
		catch (IOException ioe){
			Log.critical("Error when saving file: " + ioe);
		}
	}
	
	public DataModel getDataModel(){
		return dataModel;
	}
	
	public ModelFactory getDataModelFactory(){
		return dataModelFactory;
	}
	
	public void calculateAllBalances(){
		for (Account a : getAccounts()){
			a.calculateBalance();
		}
	}
	
	public void addAccount(Account a){
		getDataModel().getAllAccounts().getAccounts().add(a);
		saveDataModel();
	}

	public void addCategory(Category c){
		getDataModel().getAllCategories().getCategories().add(c);
		saveDataModel();
	}
	
	public void addType(String name, boolean credit){
		Type t = getDataModelFactory().createType();
		t.setName(name);
		t.setCredit(credit);
		getDataModel().getAllTypes().getTypes().add(t);
		saveDataModel();
	}

	public void addTransaction(Transaction t){
		getDataModel().getAllTransactions().getTransactions().add(t);
		t.calculateBalance();		
		saveDataModel();
	}
	
	
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

	public void deleteSourcePermanent(Source s){
		if (s instanceof Category) {
			Category c = (Category) s;
			getDataModel().getAllCategories().getCategories().remove(c);
		}
		else if (s instanceof Account) {
			Account a = (Account) s;
			getDataModel().getAllAccounts().getAccounts().remove(a);
		}
		saveDataModel();
	}
	
	public void deleteTransaction(Transaction t){
		if (getDataModel().getAllTransactions().getTransactions().remove(t)){
			t.calculateBalance();
			saveDataModel();
		}
		else{
			Log.warning("Could not delete transaction: could not find orignal in data store");
		}
	}

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
		
	public Vector<Account> getAccounts(){
		Vector<Account> v = new Vector<Account>(getDataModel().getAllAccounts().getAccounts());
		Collections.sort(v);
		return v;
	}

	public Vector<Category> getCategories(){
		Vector<Category> v = new Vector<Category>(getDataModel().getAllCategories().getCategories());
		Collections.sort(v);
		return v;
	}
	
	public Vector<Type> getTypes(){
		Vector<Type> v = new Vector<Type>(getDataModel().getAllTypes().getTypes());
		Collections.sort(v);
		return v;
	}

	public Vector<Transaction> getTransactions(){
		Vector<Transaction> v = new Vector<Transaction>(getDataModel().getAllTransactions().getTransactions());
		Collections.sort(v);
		return v;
	}
	
	public Vector<Transaction> getTransactions(Source source){
		Vector<Transaction> transactions = new Vector<Transaction>();
		
		for (Transaction transaction : getTransactions()) {
			if (transaction.getFrom() != null && transaction.getTo() != null && 
					(transaction.getFrom().equals(source) || transaction.getTo().equals(source)))
				transactions.add(transaction);
		}
		
		return transactions;
	}
	
	/**
	 * Returns all transactions within a given time frame.  Must match all
	 * time arguments (excluding null arguments)
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
		
		return v;
	}

	//[TODO] Test boundary conditions: does this overlap dates or not?
	public Vector<Transaction> getTransactions(Source source, Date startDate, Date endDate){
		Vector<Transaction> transactions = getTransactions(source);
		Vector<Transaction> v = new Vector<Transaction>();
		
		for (Transaction t : transactions) {
			if (t.getDate().after(startDate) && 
					t.getDate().before(endDate)){
				v.add(t);
			}
		}
		
		return v;
	}
	
	//[TODO] Test boundary conditions: does this overlap dates or not?
	public Vector<Transaction> getTransactions(Date startDate, Date endDate){
		Vector<Transaction> transactions = getTransactions();
		Vector<Transaction> v = new Vector<Transaction>();
		
		for (Transaction t : transactions) {
			if (t.getDate().after(startDate) && 
					t.getDate().before(endDate)){
				v.add(t);
			}
		}
		
		return v;
	}
}
