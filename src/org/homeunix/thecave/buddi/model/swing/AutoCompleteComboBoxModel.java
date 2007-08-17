/*
 * Created on Aug 10, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.model.swing;

import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.event.ListDataListener;

import org.homeunix.thecave.buddi.model.DataModel;
import org.homeunix.thecave.buddi.model.Transaction;
import org.homeunix.thecave.moss.model.DocumentChangeEvent;
import org.homeunix.thecave.moss.model.DocumentChangeListener;

public class AutoCompleteComboBoxModel implements ComboBoxModel {
	private static final long serialVersionUID = 0; 

	private DataModel model;
	private DefaultComboBoxModel comboBoxModel;

	public AutoCompleteComboBoxModel(DataModel model) {
		this.model = model;
		this.comboBoxModel = new DefaultComboBoxModel();
		updateAutoCompleteList();

		model.addDocumentChangeListener(new DocumentChangeListener(){
			public void documentChange(DocumentChangeEvent event) {
				updateAutoCompleteList();				
			}
		});
			
	}

	public Object getSelectedItem() {
		return comboBoxModel.getSelectedItem();
	}

	public void setSelectedItem(Object anItem) {
		comboBoxModel.setSelectedItem(anItem);
	}

	public void addListDataListener(ListDataListener l) {
		comboBoxModel.addListDataListener(l);
	}

	public Object getElementAt(int index) {
		return comboBoxModel.getElementAt(index);
	}

	public int getSize() {
		return comboBoxModel.getSize();
	}

	public void removeListDataListener(ListDataListener l) {
		comboBoxModel.removeListDataListener(l);
	}

	private void updateAutoCompleteList(){
		Set<String> descriptions = new HashSet<String>();
		for (Transaction t : model.getTransactions()) {
			descriptions.add(t.getDescription());
		}

		List<String> descList = new LinkedList<String>(descriptions);
		Collections.sort(descList);
		
		comboBoxModel.removeAllElements();
		comboBoxModel.addElement("");
		for (String string : descList) {
			comboBoxModel.addElement(string);	
		}
		
	}



//	private final DataModel model;
//	private int selectedIndex = 0;
//	private List<String> descriptions;

//	public AutoCompleteComboBoxModel(DataModel model) {
//	this.model = model;
//	model.addDataModelChangeListener(new DataModel.DataModelChangeListener() {
//	public void dataModelChange(DataModelChangeEvent event) {
//	updateAutoCompleteList();
//	}
//	});
//	}

//	public Object getSelectedItem() {
//	if (selectedIndex >= 0 && selectedIndex < getSize())
//	return getBackingList().get(selectedIndex);
//	return null;
//	}

//	public void setSelectedItem(Object arg0) {
//	selectedIndex = getBackingList().indexOf(arg0);
//	}

//	public Object getElementAt(int arg0) {
//	return getBackingList().get(arg0);
//	}

//	public int getSize() {
//	return getBackingList().size();
//	}

//	private List<String> getBackingList(){
//	if (descriptions == null)
//	updateAutoCompleteList();

//	return descriptions;
//	}

//	private void updateAutoCompleteList(){
//	Set<String> descriptions = new HashSet<String>();
//	for (Transaction t : model.getTransactions()) {
//	descriptions.add(t.getDescription());
//	}

//	this.descriptions = new LinkedList<String>();
//	this.descriptions.add("");
//	this.descriptions.addAll(descriptions);
//	Collections.sort(this.descriptions);
//	}
}
