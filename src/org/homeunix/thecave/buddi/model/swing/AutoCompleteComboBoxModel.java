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
}
