/*
 * Created on Jul 11, 2006 by wyatt
 */
package org.homeunix.drummer.view.components;

import javax.swing.DefaultListModel;
import javax.swing.ListModel;
import javax.swing.event.ListDataListener;

import org.homeunix.drummer.util.Log;

import de.schlichtherle.swing.filter.FilteredDynamicListModel;
import de.schlichtherle.swing.filter.ListElementFilter;

public class FilteredDynamicListModelImpl implements FilteredDynamicListModel {

	private ListElementFilter filter;
	private ListModel source;
	private final DefaultListModel filteredModel;
	
	public FilteredDynamicListModelImpl(){
		filteredModel = new DefaultListModel();
	}
	
	public ListElementFilter getFilter() {
		return filter;
	}

	public ListModel getSource() {
		return source;
	}

	public void setFilter(ListElementFilter filter) {
		this.filter = filter;
	}

	public void setSource(ListModel source) {
		this.source = source;
		
		this.update();
	}

	public void update() {
		Log.debug("FilteredDynamicListModelImpl.update()");
		filteredModel.removeAllElements();
		
		if (source != null){
			for (int i = 0; i < source.getSize(); i++) {
				Object o = source.getElementAt(i);
				
				if (filter == null || filter.accept(o)){
					Log.debug("Accepted value " + o);
					filteredModel.addElement(o);
				}
				else
					Log.debug("Rejected value " + o);
			}
		}
		else
			Log.debug("Source is null");
	}

	//The ListModel methods
	public void addListDataListener(ListDataListener arg0) {
		filteredModel.addListDataListener(arg0);
	}

	public Object getElementAt(int arg0) {
		return filteredModel.getElementAt(arg0);
	}

	public int getSize() {
		return filteredModel.size();
	}

	public void removeListDataListener(ListDataListener arg0) {
		filteredModel.removeListDataListener(arg0);
	}
}