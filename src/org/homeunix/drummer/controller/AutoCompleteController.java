/*
 * Created on Jul 25, 2007 by wyatt
 */
package org.homeunix.drummer.controller;

import java.util.HashMap;
import java.util.Map;

import org.homeunix.drummer.model.Transaction;
import org.homeunix.drummer.view.model.AutoCompleteEntry;
import org.homeunix.thecave.moss.gui.model.DefaultSortedSetComboBoxModel;

public class AutoCompleteController {

	//Provide temporary backing for the autocomplete text fields
	private static DefaultSortedSetComboBoxModel autocompleteEntries;
	private static Map<String, AutoCompleteEntry> defaultsMap;	
	
	public static DefaultSortedSetComboBoxModel getAutoCompleteEntries() {
		initialize();
		return autocompleteEntries;
	}
	
	public static void setAutoCompleteEntry(Transaction t){
		initialize();
		
		AutoCompleteEntry ace = new AutoCompleteEntry(t);
		defaultsMap.put(t.getDescription(), ace);
		autocompleteEntries.addElement(t.getDescription());
	}

	public static AutoCompleteEntry getAutoCompleteEntry(String description){
		initialize();
		
		return defaultsMap.get(description);
	}
	
	/**
	 * Checks if the data structures are null, and initializes if so.
	 */
	private static void initialize(){
		if (autocompleteEntries == null || defaultsMap == null){
			autocompleteEntries = new DefaultSortedSetComboBoxModel();
			defaultsMap = new HashMap<String, AutoCompleteEntry>();
			
			//Dynamically load the auto complete entries into the dictionary
			// and create the autocomplete map, based on existing transactions.
			for (Transaction t : TransactionController.getTransactions()) {
				AutoCompleteEntry entry = new AutoCompleteEntry(t);
				autocompleteEntries.addElement(entry.getDescription());
				defaultsMap.put(entry.getDescription(), entry);
			}

		}
	}
}
