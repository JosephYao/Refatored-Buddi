/*
 * Created on Aug 9, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.model.swing;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.event.ListDataListener;

import org.homeunix.thecave.buddi.i18n.BuddiKeys;
import org.homeunix.thecave.buddi.model.Account;
import org.homeunix.thecave.buddi.model.BudgetCategory;
import org.homeunix.thecave.buddi.model.Document;
import org.homeunix.thecave.buddi.plugin.api.util.TextFormatter;
import org.homeunix.thecave.moss.model.DocumentChangeEvent;
import org.homeunix.thecave.moss.model.DocumentChangeListener;

public class SourceComboBoxModel implements ComboBoxModel {
	private static final long serialVersionUID = 0; 

	private final Document model;
	private final boolean includeIncome;
	
	private final DefaultComboBoxModel comboBoxModel;
	
	private final DocumentChangeListener listener;

	public SourceComboBoxModel(Document model, boolean includeIncome) {
		this.model = model;
		this.includeIncome = includeIncome;
		this.comboBoxModel = new DefaultComboBoxModel();
		updateComboBoxModel();
		listener = new DocumentChangeListener(){
			public void documentChange(DocumentChangeEvent event) {
				updateComboBoxModel();	
			}
		};
		model.addDocumentChangeListener(listener);
	}

	public Object getSelectedItem() {
		return getComboBoxModel().getSelectedItem();
	}

	public void setSelectedItem(Object anItem) {
		getComboBoxModel().setSelectedItem(anItem);
	}

	public void addListDataListener(ListDataListener l) {
		getComboBoxModel().addListDataListener(l);
	}

	public Object getElementAt(int index) {
		return getComboBoxModel().getElementAt(index);
	}

	public int getSize() {
		return getComboBoxModel().getSize();
	}

	public void removeListDataListener(ListDataListener l) {
		getComboBoxModel().removeListDataListener(l);
	}
	
	private DefaultComboBoxModel getComboBoxModel(){
		return comboBoxModel;
	}

	private void updateComboBoxModel(){
		Object selected = getSelectedItem(); 
		
		List<Account> accounts = new LinkedList<Account>(model.getAccounts());
		List<BudgetCategory> budgetCategories = new LinkedList<BudgetCategory>(model.getBudgetCategories());
		
		Collections.sort(accounts);
		Collections.sort(budgetCategories);		
		
		getComboBoxModel().removeAllElements();
		getComboBoxModel().addElement(TextFormatter.getTranslation(BuddiKeys.ACCOUNTS_COMBOBOX_HEADER));
		for (Account a : accounts) {
			getComboBoxModel().addElement(a);
		}
		getComboBoxModel().addElement("&nbsp;");
		getComboBoxModel().addElement(TextFormatter.getTranslation(BuddiKeys.BUDGET_CATEGORIES_COMBOBOX_HEADER));
		for (BudgetCategory bc : budgetCategories) {
			if (bc.isIncome() == includeIncome){
				getComboBoxModel().addElement(bc);
			}
		}
		
		setSelectedItem(selected);
	}
}