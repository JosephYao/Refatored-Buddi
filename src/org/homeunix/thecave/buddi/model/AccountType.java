/*
 * Created on Jul 30, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.model;

import org.homeunix.thecave.buddi.model.api.exception.InvalidValueException;


public interface AccountType extends ModelObject, Expandable {
	
	public String getName();
	
	public boolean isCredit();
	
	public void setCredit(boolean credit) throws InvalidValueException;
	
	public void setName(String name) throws InvalidValueException;
}
