/*
 * Created on Apr 7, 2007 by wyatt
 */
package org.homeunix.drummer.controller;

import java.util.Collections;
import java.util.Vector;

import org.homeunix.drummer.model.Account;
import org.homeunix.drummer.model.Category;
import org.homeunix.drummer.model.DataInstance;
import org.homeunix.drummer.model.Source;

public class SourceController {
	/**
	 * Adds the specified account to the model.  This should be already 
	 * created using the ModelFactory.
	 * @param a Account to add
	 */
	@SuppressWarnings("unchecked")
	public static void addAccount(Account a){
		DataInstance.getInstance().getDataModel().getAllAccounts().getAccounts().add(a);
	}

	/**
	 * Adds the specified category to the model.  This should be already 
	 * created using the ModelFactory.
	 * @param c Category to add
	 */
	@SuppressWarnings("unchecked")
	public static void addCategory(Category c){
		DataInstance.getInstance().getDataModel().getAllCategories().getCategories().add(c);
	}
	
	/**
	 * Deletes the given source by setting the deleted flag to true.  If 
	 * you are writing a plugin which must delete a source (why, I don't 
	 * know, but there may be some situation in which this is desired),
	 * you are probably better off using this method.  This method can
	 * be undone with the undeleteSource() method. 
	 * @param s
	 */
	public static void deleteSource(Source s){
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
	 * @param s The source to delete
	 * @return <code>true</code> if deleted, <code>false</code> if there
	 * was an error (including not being able to delete because of
	 * data model sanity).
	 */
	public static void deleteSourcePermanent(Source s){
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
			DataInstance.getInstance().getDataModel().getAllCategories().getCategories().remove(c);
		}
		else if (s instanceof Account) {
			Account a = (Account) s;
			DataInstance.getInstance().getDataModel().getAllAccounts().getAccounts().remove(a);
		}
	}
	
	/**
	 * Returns all accounts in the model, sorted according to the Account comparator
	 * @return All accounts in the currently loaded data model
	 */
	@SuppressWarnings("unchecked")
	public static Vector<Account> getAccounts(){
		Vector<Account> v = new Vector<Account>(DataInstance.getInstance().getDataModel().getAllAccounts().getAccounts());
		Collections.sort(v);
		return v;
	}

	/**
	 * Returns all categories in the model, sorted according to the Category comparator
	 * @return All categories in the currently loaded data model
	 */
	@SuppressWarnings("unchecked")
	public static Vector<Category> getCategories(){
		Vector<Category> v = new Vector<Category>(DataInstance.getInstance().getDataModel().getAllCategories().getCategories());
		Collections.sort(v);
		return v;
	}
	
	/**
	 * Sets the Deleted flag of the given source to false.
	 * @param s
	 */
	public static void undeleteSource(Source s){
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
	}
	
	/**
	 * Forces an update of all the balances.  This O(n) operation can
	 * take a while with large models; use sparingly if possible
	 */
	public static void calculateAllBalances(){
		for (Account a : getAccounts()){
			a.calculateBalance();
		}
	}
}
