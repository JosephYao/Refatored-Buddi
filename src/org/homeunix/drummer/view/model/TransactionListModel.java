/*
 * Created on Sep 30, 2006 by wyatt
 */
package org.homeunix.drummer.view.model;

import java.util.Collection;
import java.util.Date;

import javax.swing.AbstractListModel;

import org.eclipse.emf.common.util.ECollections;
import org.eclipse.emf.common.util.EList;
import org.homeunix.drummer.controller.SourceController;
import org.homeunix.drummer.controller.TransactionController;
import org.homeunix.drummer.controller.TranslateKeys;
import org.homeunix.drummer.model.Account;
import org.homeunix.drummer.model.Transaction;
import org.homeunix.drummer.view.TransactionsFrame;
import org.homeunix.thecave.moss.util.DateUtil;
import org.homeunix.thecave.moss.util.Log;

import de.schlichtherle.swing.filter.BitSetFilteredDynamicListModel;
import de.schlichtherle.swing.filter.FilteredDynamicListModel;
import de.schlichtherle.swing.filter.ListElementFilter;

/**
 * Custom list model for use with TransactionFrames.  This is designed
 * to be the 'base model' - all transactions frames share one instance of
 * this object, and base their lists off of it.  This allows a change in 
 * one to automatically show in all.  To get the different views for each
 * window (showing only transactions which match a given account), we
 * use a DynamicFilteredListModel, which is based off of this one (via the
 * getFilteredListMode(Account, TransactionsFrame) method).
 *  
 * @author wyatt
 *
 */
public class TransactionListModel extends AbstractListModel {
	public static final long serialVersionUID = 0;
	
	private EList transactions;
	
	/**
	 * Create a new TransactionListModel object, using a given set of transactions for the data.
	 * @param transactions The list of transactions which this model is based off of
	 */
	public TransactionListModel(EList transactions){
		this.transactions = transactions;
		ECollections.sort(this.transactions);
	}
	
	/**
	 * Re-loads the model with new values.  Should only need to be 
	 * called after a new data model is loaded.
	 * @param transactions
	 */
	public void loadModel(EList transactions){
		this.transactions = transactions;
		ECollections.sort(this.transactions);
	}
	
	//*** Abstract List Model methods
	/* (non-Javadoc)
	 * @see javax.swing.ListModel#getElementAt(int)
	 */
	public Object getElementAt(int index) {
		return transactions.get(index);
	}

	/* (non-Javadoc)
	 * @see javax.swing.ListModel#getSize()
	 */
	public int getSize() {
		return transactions.size();
	}
	
	//*** Data Access methods
	/**
	 * Adds a collection of new transactions to the model.  Mostly used
	 * for importing transactions, as you won't need to update the 
	 * windows after each addition.
	 * @param transactions A collection of transactions
	 */
	public void addAll(Collection<Transaction> transactions){
		for (Transaction t : transactions) {
			TransactionController.addTransaction(t);	
		}
		ECollections.sort(this.transactions);
		this.fireIntervalAdded(this, 0, this.getSize() - 1);
	}
	
	/**
	 * Adds a new transaction to the model.  You should use this method
	 * instead of DataInstance to ensure that all transactions frames 
	 * get updated automatically.
	 * @param t The transaction to add to the list.
	 */
	public void add(Transaction t){
		TransactionController.addTransaction(t);
		ECollections.sort(this.transactions);
		int i = transactions.indexOf(t);
		this.fireIntervalAdded(this, i, i);
	}
	
	/**
	 * Removes the given transaction from the list.  You should use this 
	 * method instead of DataInstance to ensure that all transactions frames
	 * get updated automatically. 
	 * @param t The transaction to remove from the list.
	 * @param fdlm The filtered dynamic list model from which the removal originated. 
	 */
	public void remove(Transaction t, FilteredDynamicListModel fdlm){
		TransactionController.deleteTransaction(t);
		if (fdlm != null)
			fdlm.update();
	}
	
	/**
	 * Updates the given transaction in the list.  You should use this 
	 * method instead of DataInstance to ensure that all transactions frames
	 * get updated automatically. 
	 * @param t The transaction to update in the list.
	 * @param fdlm The filtered dynamic list model from which the update originated. 
	 */
	public void update(Transaction t, FilteredDynamicListModel fdlm){
		ECollections.sort(this.transactions);
		SourceController.calculateAllBalances();
		if (fdlm != null)
			fdlm.update();
	}
	
	/**
	 * Updates the given transaction in the list, with no change in order 
	 * (i.e., the date for the updated transaction did not change).  You 
	 * should use this method instead of DataInstance to ensure that all 
	 * transactions frames get updated automatically. 
	 * @param t The transaction to update in the list.
	 * @param fdlm The filtered dynamic list model from which the update originated. 
	 */
	public void updateNoOrderChange(Transaction t){
		t.calculateBalance();
		int i = transactions.indexOf(t);
		this.fireContentsChanged(this, i, i);
	}
	
	//*** Filtered List Model
	/**
	 * Returns a filtered dynamic list model, associated with a given
	 * account and frame.  The transactionFrame is needed to allow
	 * filtering on Search text and date range.
	 * @param a Account to filter on
	 * @param frame The frame which will be asociated with this model.
	 * @return
	 */
	public FilteredDynamicListModel getFilteredListModel(final Account a, final TransactionsFrame frame){
		final FilteredDynamicListModel fdlm = new BitSetFilteredDynamicListModel();  
		fdlm.setSource(this);
		fdlm.setFilter(new ListElementFilter(){
			public static final long serialVersionUID = 0;
			
			public boolean accept(Object arg0) {
				Transaction t = (Transaction) arg0;
				
				if (t.getTo().equals(a) || t.getFrom().equals(a)){
					return acceptDate(t, frame.getFilterComboBox()) && acceptText(t, frame.getFilterText());
				}
				
				return false;
			}
			
			private boolean acceptDate(Transaction t, TranslateKeys filterPulldown) {
				if (null == filterPulldown || TranslateKeys.TRANSACTION_FILTER_ALL == filterPulldown) {
					return true;
				}

				Date today = new Date();

				if (TranslateKeys.TRANSACTION_FILTER_TODAY == filterPulldown) {
					return DateUtil.getEndOfDay(today).equals(DateUtil.getEndOfDay(t.getDate()));
				}
				else if (TranslateKeys.TRANSACTION_FILTER_THIS_WEEK == filterPulldown) {
					return DateUtil.getStartOfDay(DateUtil.getNextNDay(today, -7)).before(t.getDate());
				}
				else if (TranslateKeys.TRANSACTION_FILTER_THIS_MONTH == filterPulldown) {
					return DateUtil.getStartOfDay(DateUtil.getBeginOfMonth(today, 0)).before(t.getDate());
				}
				else if (TranslateKeys.TRANSACTION_FILTER_THIS_QUARTER == filterPulldown) {
					return DateUtil.getStartOfDay(DateUtil.getBeginOfQuarter(today, 0)).before(t.getDate());
				} 
				else if (TranslateKeys.TRANSACTION_FILTER_THIS_YEAR == filterPulldown) {
					return DateUtil.getStartOfDay(DateUtil.getBeginOfYear(today)).before(t.getDate());				
				}
				else if (TranslateKeys.TRANSACTION_FILTER_LAST_YEAR == filterPulldown) {
					Date startOfLastYear = DateUtil.getStartOfDay(DateUtil.getStartOfYear(DateUtil.getNextNDay(today, -365)));
					Date endOfLastYear = DateUtil.getEndOfYear(startOfLastYear);
					return startOfLastYear.before(t.getDate()) && endOfLastYear.after(t.getDate()); 
				}
				else if (TranslateKeys.TRANSACTION_FILTER_NOT_RECONCILED == filterPulldown) {
					return !t.isReconciled();
				}
				else if (TranslateKeys.TRANSACTION_FILTER_NOT_CLEARED == filterPulldown) {
					return !t.isCleared();
				}
				else {
					Log.error("Unknown filter pulldown: " + filterPulldown);
					return false;
				}
			}

			private boolean acceptText(Transaction t, String filterText) {
				if (filterText == null || filterText.length() == 0) {
					return true;
				}
				return (t.getDescription().toLowerCase().contains(filterText.toLowerCase())
						|| t.getNumber().toLowerCase().contains(filterText.toLowerCase())
						|| t.getMemo().toLowerCase().contains(filterText.toLowerCase())
						|| t.getFrom().getName().toLowerCase().contains(filterText.toLowerCase())
						|| t.getTo().getName().toLowerCase().contains(filterText.toLowerCase())
						|| Long.toString(t.getAmount()).contains(filterText.toLowerCase()));
			}	
		});
		return fdlm;
	}
}
