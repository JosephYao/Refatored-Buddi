/*
 * Created on Aug 9, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.model.swing;

import javax.swing.AbstractListModel;

import org.homeunix.thecave.buddi.i18n.keys.TransactionDateFilterKeys;
import org.homeunix.thecave.buddi.model.Account;
import org.homeunix.thecave.buddi.model.DataModel;
import org.homeunix.thecave.buddi.model.FilteredLists;

public class TransactionListModel extends AbstractListModel {
	public static final long serialVersionUID = 0;
	
	private final FilteredLists.TransactionListFilteredBySearch transactions;
	
	public TransactionListModel(DataModel model, Account selectedAccount) {
		this.transactions = new FilteredLists.TransactionListFilteredBySearch(model, model.getTransactions(selectedAccount));
	}
	
	public Object getElementAt(int index) {
		return transactions.get(index);
	}

	public int getSize() {
		return transactions.size();
	}
	
	public void setSearchText(String text){
		transactions.setSearchText(text);
		transactions.updateFilteredList();
		update();
	}
	
	public void setDateFilter(TransactionDateFilterKeys key){
		transactions.setDateFilter(key);
		transactions.updateFilteredList();
		update();
	}
	
	public void update(){
		fireContentsChanged(this, -1, -1);
	}
}
