/*
 * Created on Jul 30, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.model;

import org.homeunix.thecave.buddi.model.api.exception.InvalidValueException;

public interface Source extends ModelObject {

	public String getFullName();
	
	public String getName();
	
	public String getNotes();
	
	public boolean isDeleted();

	public void setDeleted(boolean deleted) throws InvalidValueException;

	public void setName(String name) throws InvalidValueException;
	
	public void setNotes(String notes) throws InvalidValueException;
}
