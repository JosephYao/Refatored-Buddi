/*
 * Created on Jul 11, 2006 by wyatt
 */
package org.homeunix.drummer.view.components;

import org.homeunix.drummer.model.Transaction;

import de.schlichtherle.swing.filter.ListElementFilter;

public class TransactionListElementFilter implements ListElementFilter {
	static final long serialVersionUID = 0;
	
	private String filterText = "";
	
	public boolean accept(Object arg0) {
		if (arg0 instanceof Transaction){
			Transaction t = (Transaction) arg0;
			if (t.getDescription().toLowerCase().contains(filterText.toLowerCase()) 
					|| t.getNumber().toLowerCase().contains(filterText.toLowerCase())
					|| t.getMemo().toLowerCase().contains(filterText.toLowerCase())
					|| t.getFrom().getName().toLowerCase().contains(filterText.toLowerCase())
					|| t.getTo().getName().toLowerCase().contains(filterText.toLowerCase()))
				return true;
			else
				return false;
		}
		else if (arg0 == null) {
			return true;
		}
		else {
			return false;
		}
	}

	public String getFilterText() {
		return filterText;
	}

	public void setFilterText(String filterText) {
		this.filterText = filterText;
	}
}