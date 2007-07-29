/*
 * Created on Jul 3, 2007 by wyatt
 */
package org.homeunix.drummer.view.model;

import java.util.Comparator;

import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;

import org.eclipse.emf.common.util.ECollections;
import org.eclipse.emf.common.util.EList;
import org.homeunix.drummer.model.Source;

import de.schlichtherle.swing.filter.BitSetFilteredDynamicListModel;

/**
 * @author wyatt
 *
 * Default model for To/From Combo Boxes in EditableTransaction.  Non mutable - it
 * updates itself when something changes.  Keeps the returned list sorted at all 
 * times (thus overriding insertElementAt() with addElement).  The sort order is 
 * Accounts (by name), then Categories (by name).
 */
public class ToFromComboBoxModel extends AbstractListModel implements ComboBoxModel {
	public static final long serialVersionUID = 0;

	private Source selectedItem;
	private final EList accountList, categoryList;
	private int accountListLength, categoryListLength;  //Used to detect changes
	
//	protected EventListenerList eventListenerList;
	
	public ToFromComboBoxModel(EList accountList, EList categoryList) {
		this.accountList = accountList;
		this.categoryList = categoryList;

		accountListLength = accountList.size();
		categoryListLength = categoryList.size();
//		this.eventListenerList = new EventListenerList();
		
		selectedItem = null;
	}

	
	public Object getSelectedItem() {
		return selectedItem;
	}



	public void setSelectedItem(Object anItem) {
		if (anItem == null 
				|| (anItem instanceof Source 
						&& (accountList.contains(anItem) || categoryList.contains(anItem)))){
			selectedItem = (Source) anItem;
			fireContentsChanged(this, -1, -1);  
		}
	}


//	public void addListDataListener(ListDataListener l) {
//		eventListenerList.add(ListDataListener.class, l);
//	}

	

	public Object getElementAt(int index) {
		//EList does not seem to have any change listeners.  We need to check
		// if the lists have changed at all here, first.
		if (accountListLength != accountList.size() || categoryListLength != categoryList.size()){
			accountListLength = accountList.size();
			categoryListLength = categoryList.size();

			ECollections.sort(accountList, new Comparator<Source>(){
				public int compare(Source o1, Source o2) {
					return o1.getName().compareToIgnoreCase(o2.getName());
				}
			});
			ECollections.sort(categoryList, new Comparator<Source>(){
				public int compare(Source o1, Source o2) {
					return o1.getName().compareToIgnoreCase(o2.getName());
				}
			});
			
			fireContentsChanged(this, -1, -1);  
		}
		
		
		//We need to make sure we get the order right here.
		// Externally, we show the structure of this model as 
		// Account1, Account2, ..., null, Category1, Category2, ...

		if (index == 0)
			return null;
		
		//Decrement index to make the rest of the calculations easier.
		index--;
		
		//Check for all the accounts...
		if (index < accountList.size())
			return accountList.get(index);
		
		//Allow for the null in the center of the list.
		if (index == accountList.size())
			return null;
		
		//Check for all the categories...
		if (index > accountList.size() && (index - accountList.size() < categoryList.size()))
			return categoryList.get(index - accountList.size());
		
		return null;
	}


	public int getSize() {
		return accountList.size() + categoryList.size() + 1;
	}
	
	public static class FilteredComboBoxModel extends BitSetFilteredDynamicListModel implements ComboBoxModel {

		public Object getSelectedItem() {
			// TODO Auto-generated method stub
			return null;
		}

		public void setSelectedItem(Object arg0) {
			// TODO Auto-generated method stub
			
		}
		
	}
}
