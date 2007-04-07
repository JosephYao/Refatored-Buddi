/*
 * Created on May 6, 2006 by wyatt
 */
package org.homeunix.drummer.controller;

import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Vector;

import org.homeunix.drummer.model.Account;
import org.homeunix.drummer.model.Category;
import org.homeunix.drummer.model.DataInstance;
import org.homeunix.drummer.model.Source;
import org.homeunix.drummer.model.Transaction;
import org.homeunix.thecave.moss.util.Log;


public class TransactionController {
	
	public static boolean isRecordValid(
			String description, 
			Date date, 
			long amount, 
			Source to,
			Source from,
			Account thisAccount){
		return (!(
				description.length() == 0 
				|| date == null
				|| amount < 0
				|| to == null
				|| from == null
				|| (from != thisAccount
						&& to != thisAccount)
		));
	}
	
	/**
	 * Do not call this method unless you know what you are doing!  If you
	 * are writing plugin code, the method you probably want is 
	 * TransactionsFrame.removeFromTransactionListModel().  This will 
	 * delete the transaction, and automatically update all open windows.
	 * @param t
	 */
	public static void deleteTransaction(Transaction t){
		if (DataInstance.getInstance().getDataModel().getAllTransactions().getTransactions().remove(t)){
			t.calculateBalance();
		}
		else{
			Log.warning("Could not delete transaction: could not find orignal in data store");
		}
	}
	
	/**
	 * Do not call this method unless you know what you are doing!  If you
	 * are writing plugin code, the method you probably want is 
	 * TransactionsFrame.addToTransactionListModel().  This will add the
	 * transaction, and automatically update all open windows.
	 * @param t
	 */
	@SuppressWarnings("unchecked")
	public static void addTransaction(Transaction t){
		DataInstance.getInstance().getDataModel().getAllTransactions().getTransactions().add(t);
		t.calculateBalance();		
	}

	/**
	 * Returns all transactions in the model, sorted according to the Transaction comparator
	 * @return All transactions in the currently loaded data model
	 */
	@SuppressWarnings("unchecked")
	public static Vector<Transaction> getTransactions(){
		Vector<Transaction> transactions = new Vector<Transaction>(DataInstance.getInstance().getDataModel().getAllTransactions().getTransactions());
		Collections.sort(transactions);
		return transactions;
	}

	/**
	 * Returns all transactions which go to or from a given source.
	 * @param source
	 * @return
	 */
	public static Vector<Transaction> getTransactions(Source source){
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
	public static Vector<Transaction> getTransactions(Source source, Integer year, Integer month, Integer dayOfMonth){
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
	public static Vector<Transaction> getTransactions(String description, Date startDate, Date endDate){
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
	public static Vector<Transaction> getTransactions(Boolean isIncome, Date startDate, Date endDate){
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
	public static Vector<Transaction> getTransactions(Source source, Date startDate, Date endDate){
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
	public static Vector<Transaction> getTransactions(Date startDate, Date endDate){
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
}
