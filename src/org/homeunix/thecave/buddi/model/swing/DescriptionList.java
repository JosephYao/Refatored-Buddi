/*
 * Created on Aug 10, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.model.swing;

import java.util.AbstractList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.homeunix.thecave.buddi.model.Document;
import org.homeunix.thecave.buddi.model.Transaction;
import org.homeunix.thecave.moss.model.DocumentChangeEvent;
import org.homeunix.thecave.moss.model.DocumentChangeListener;

public class DescriptionList extends AbstractList<String> {
	private static final long serialVersionUID = 0; 

	private Document model;
	private final List<String> descriptionList;

	public DescriptionList(Document model) {
		this.model = model;
		this.descriptionList = new LinkedList<String>();
		updateAutoCompleteList();

		model.addDocumentChangeListener(new DocumentChangeListener(){
			public void documentChange(DocumentChangeEvent event) {
				updateAutoCompleteList();				
			}
		});
			
	}

	@Override
	public String get(int index) {
		return descriptionList.get(index);
	}

	@Override
	public int size() {
		return descriptionList.size();
	}

	private void updateAutoCompleteList(){
		Set<String> descriptions = new HashSet<String>();
		for (Transaction t : model.getTransactions()) {
			descriptions.add(t.getDescription());
		}

		descriptionList.clear();
		descriptionList.addAll(descriptions);
		Collections.sort(descriptionList);
	}
}
