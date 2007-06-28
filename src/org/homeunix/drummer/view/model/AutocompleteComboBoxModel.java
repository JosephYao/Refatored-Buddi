/*
 * Created on Jun 27, 2007 by wyatt
 */
package org.homeunix.drummer.view.model;

import javax.swing.DefaultComboBoxModel;

import de.schlichtherle.swing.filter.BitSetFilteredDynamicListModel;
import de.schlichtherle.swing.filter.ListElementFilter;

public class AutocompleteComboBoxModel extends DefaultComboBoxModel {
	public static final long serialVersionUID = 0;
	
	private final BitSetFilteredDynamicListModel listModel = new BitSetFilteredDynamicListModel();
	private final DefaultListElementFilter filter = new DefaultListElementFilter(); 

	public AutocompleteComboBoxModel() {
		listModel.setSource(this);
		listModel.setFilter(filter);
	}
	
	@Override
	public Object getElementAt(int index) {
		if (getSize() > index)
			return listModel.getElementAt(index);
		return null;
	}

	@Override
	public int getIndexOf(Object anObject) {
		for (int i = 0; i < getSize(); i++){
			if (getElementAt(i).equals(anObject))
				return i;
		}
		return 0;
	}

	@Override
	public int getSize() {
		return listModel.getSize();
	}
	
	public class DefaultListElementFilter implements ListElementFilter {
		public static final long serialVersionUID = 0;
		private String filter = "";
		
		public void setFilter(String filter){
			this.filter = filter;
		}
		
		public boolean accept(Object arg0) {
			if (arg0 != null)
				return arg0.toString().startsWith(filter);
			return false;
		}
	}
}
