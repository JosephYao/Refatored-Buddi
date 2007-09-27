/*
 * Created on Aug 9, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.model.swing;

import javax.swing.AbstractListModel;

import org.homeunix.thecave.buddi.i18n.keys.TransactionDateFilterKeys;
import org.homeunix.thecave.buddi.model.Document;
import org.homeunix.thecave.buddi.model.Source;
import org.homeunix.thecave.buddi.model.impl.FilteredLists;

public class TransactionListModel extends AbstractListModel {
	public static final long serialVersionUID = 0;
	
	private final FilteredLists.TransactionListFilteredBySearch transactions;
	
	public TransactionListModel(Document model, Source selectedSource) {
		if (selectedSource == null)
			this.transactions = new FilteredLists.TransactionListFilteredBySearch(model, model.getTransactions());
		else
			this.transactions = new FilteredLists.TransactionListFilteredBySearch(model, model.getTransactions(selectedSource));
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
