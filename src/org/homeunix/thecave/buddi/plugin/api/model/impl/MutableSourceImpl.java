/*
 * Created on Aug 23, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.plugin.api.model.impl;

import java.util.Date;

import org.homeunix.thecave.buddi.model.Source;
import org.homeunix.thecave.buddi.plugin.api.model.MutableSource;

public abstract class MutableSourceImpl extends MutableModelObjectImpl implements MutableSource {

	public MutableSourceImpl(Source source) {
		super(source);
	}

	public void setDeleted(boolean deleted) {
		getSource().setDeleted(deleted);
		
	}

	public void setName(String name) {
		getSource().setName(name);
	}

	public void setNotes(String notes) {
		getSource().setNotes(notes);
	}

	public Source getSource(){
		return (Source) getRaw(); 
	}
	public Date getEarliestDate() {
		return getSource().getEarliestDate();
	}
	public boolean isDeleted() {
		return getSource().isDeleted();
	}
	public String getName() {
		return getSource().getName();
	}
	public String getFullName() {
		return getSource().getFullName();
	}
	public String getNotes() {
		return getSource().getNotes();
	}
}
