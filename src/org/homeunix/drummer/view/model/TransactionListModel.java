/*
 * Created on Sep 30, 2006 by wyatt
 */
package org.homeunix.drummer.view.model;

import java.util.Date;

import javax.swing.AbstractListModel;

import org.eclipse.emf.common.util.ECollections;
import org.eclipse.emf.common.util.EList;
import org.homeunix.drummer.controller.TransactionsFrame;
import org.homeunix.drummer.controller.TranslateKeys;
import org.homeunix.drummer.model.Account;
import org.homeunix.drummer.model.DataInstance;
import org.homeunix.drummer.model.Transaction;
import org.homeunix.drummer.util.DateUtil;
import org.homeunix.drummer.util.Log;

import de.schlichtherle.swing.filter.BitSetFilteredDynamicListModel;
import de.schlichtherle.swing.filter.FilteredDynamicListModel;
import de.schlichtherle.swing.filter.ListElementFilter;

public class TransactionListModel extends AbstractListModel {
	public static final long serialVersionUID = 0;
	
	private final EList transactions;
	
	public TransactionListModel(EList transactions){
		this.transactions = transactions;
		ECollections.sort(this.transactions);
	}
	
	//*** Abstract List Model methods
	public Object getElementAt(int index) {
		return transactions.get(index);
	}

	public int getSize() {
		return transactions.size();
	}
	
	//*** Data Access methods
	public void add(Transaction t){
		DataInstance.getInstance().addTransaction(t);
		ECollections.sort(this.transactions);
		int i = transactions.indexOf(t);
		this.fireIntervalAdded(this, i, i);
//		this.fireContentsChanged(this, i, transactions.size() - 1);
//		this.fireIntervalAdded(this, 0, getSize() - 1);
		Log.info(transactions);
	}
	
	public boolean remove(Transaction t){
		int i = transactions.indexOf(t);
		boolean r = transactions.remove(t);
		if (r){
			this.fireIntervalRemoved(this, i, i);
//			this.fireIntervalRemoved(this, 0, getSize() - 1);
		}
		Log.info(transactions);
		return r;
	}
	
	public boolean update(Transaction t){
		int i = transactions.indexOf(t);
		ECollections.sort(this.transactions);
//		int j = transactions.indexOf(t);
//		if (i != -1 && j != -1){
			this.fireContentsChanged(this, 0, getSize() - 1);
//			this.fireContentsChanged(this, i, i);
//			this.fireContentsChanged(this, j, j);
//		}
		Log.info(transactions);
		return (i != -1);
	}
	
	//*** Filtered List Model
	public FilteredDynamicListModel getFilteredListModel(final Account a, final TransactionsFrame frame){
		FilteredDynamicListModel fdlm = new BitSetFilteredDynamicListModel();
		fdlm.setSource(this);
		fdlm.setFilter(new ListElementFilter(){
			public static final long serialVersionUID = 0;
			
			public boolean accept(Object arg0) {
//				if (arg0 instanceof Transaction){
				Transaction t = (Transaction) arg0;
				if (t.getTo().equals(a) || t.getFrom().equals(a)){
					return acceptText(t, frame.getFilterText()) && acceptDate(t, frame.getDateRangeFilter());
				}
				return false;
			}
			
			private boolean acceptDate(Transaction t, TranslateKeys filterDateRange) {
				if (null == filterDateRange || TranslateKeys.ALL == filterDateRange) {
					return true;
				}

				Date today = new Date();

				if (TranslateKeys.TODAY == filterDateRange) {
					return DateUtil.getEndOfDay(today).equals(DateUtil.getEndOfDay(t.getDate()));
				}
				else if (TranslateKeys.THIS_WEEK == filterDateRange) {
					return DateUtil.getStartOfDay(DateUtil.getNextNDay(today, -7)).before(t.getDate());
				}
				else if (TranslateKeys.THIS_MONTH == filterDateRange) {
					return DateUtil.getStartOfDay(DateUtil.getBeginOfMonth(today, 0)).before(t.getDate());
				}
				else if (TranslateKeys.THIS_QUARTER == filterDateRange) {
					return DateUtil.getStartOfDay(DateUtil.getBeginOfQuarter(today, 0)).before(t.getDate());
				} 
				else if (TranslateKeys.THIS_YEAR == filterDateRange) {
					return DateUtil.getStartOfDay(DateUtil.getBeginOfYear(today)).before(t.getDate());				
				}
				else if (TranslateKeys.LAST_YEAR == filterDateRange) {
					Date startOfLastYear = DateUtil.getStartOfDay(DateUtil.getStartOfYear(DateUtil.getNextNDay(today, -365)));
					Date endOfLastYear = DateUtil.getEndOfYear(startOfLastYear);
					return startOfLastYear.before(t.getDate()) && endOfLastYear.after(t.getDate()); 
				}
				else 
					Log.error("Unknown filter date range: " + filterDateRange);
					return false;
			}

			private boolean acceptText(Transaction t, String filterText) {
				if (filterText == null || filterText.length() == 0) {
					return true;
				}
				return (t.getDescription().toLowerCase().contains(filterText.toLowerCase())
						|| t.getNumber().toLowerCase().contains(filterText.toLowerCase())
						|| t.getMemo().toLowerCase().contains(filterText.toLowerCase())
						|| t.getFrom().getName().toLowerCase().contains(filterText.toLowerCase())
						|| t.getTo().getName().toLowerCase().contains(filterText.toLowerCase()));
			}	
		});
		return fdlm;
	}

}
