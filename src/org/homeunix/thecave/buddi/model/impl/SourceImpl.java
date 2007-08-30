/*
 * Created on Jul 30, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.model.impl;

import org.homeunix.thecave.buddi.model.ModelObject;
import org.homeunix.thecave.buddi.model.Source;
import org.homeunix.thecave.buddi.model.beans.SourceBean;
import org.homeunix.thecave.buddi.model.exception.InvalidValueException;

public abstract class SourceImpl extends ModelObjectImpl implements Source {

	SourceImpl(SourceBean source) throws InvalidValueException {
		super(source);
	}

	public boolean isDeleted() {
		return getSourceBean().isDeleted();
	}
	public void setDeleted(boolean deleted) {
		getSourceBean().setDeleted(deleted);
	}
	public String getName() {
		return getSourceBean().getName();
	}
	public void setName(String name) {
		getSourceBean().setName(name);
	}
	public String getNotes() {
		return getSourceBean().getNotes();
	}
	public void setNotes(String notes) {
		getSourceBean().setNotes(notes);
	}


	@Override
	public int compareTo(ModelObject arg0) {
		if (arg0 instanceof SourceImpl)
			return this.getFullName().compareTo(((SourceImpl) arg0).getFullName());
		return super.compareTo(arg0);
	}

	public SourceBean getSourceBean(){
		return (SourceBean) getBean();
	}

	@Override
	public String toString() {
		return getFullName();
	}

	public abstract String getFullName();
}
