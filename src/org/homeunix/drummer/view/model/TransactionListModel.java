/*
 * Created on Sep 30, 2006 by wyatt
 */
package org.homeunix.drummer.view.model;

import javax.swing.AbstractListModel;

import org.eclipse.emf.common.util.ECollections;
import org.eclipse.emf.common.util.EList;
import org.homeunix.drummer.model.Account;
import org.homeunix.drummer.model.DataInstance;
import org.homeunix.drummer.model.Transaction;

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
		this.fireContentsChanged(this, i, i);
	}
	
	public boolean remove(Transaction t){
		int i = transactions.indexOf(t);
		boolean r = transactions.remove(t);
		if (r)
			this.fireContentsChanged(this, i, i);
		return r;
	}
	
	public boolean update(Transaction t){
		ECollections.sort(this.transactions);
		int i = transactions.indexOf(t);
		if (i != -1)
			this.fireContentsChanged(this, 0, getSize() - 1);
		return (i != -1);
	}
	
	//*** Filtered List Model
	public FilteredDynamicListModel getFilteredListModel(final Account a){
		FilteredDynamicListModel fdlm = new BitSetFilteredDynamicListModel();
		fdlm.setSource(this);
		fdlm.setFilter(new ListElementFilter(){
			public static final long serialVersionUID = 0;
			
			public boolean accept(Object arg0) {
//				if (arg0 instanceof Transaction){
				Transaction t = (Transaction) arg0;
				if (t.getTo().equals(a) || t.getFrom().equals(a))
					return true;
//				}
				return false;
			}
		});
		return fdlm;
	}

}
