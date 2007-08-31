/*
 * Created on Jul 30, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.model;

import org.homeunix.thecave.buddi.model.exception.InvalidValueException;


public interface AccountType extends ModelObject {
	
	public String getName();
	
	public boolean isCredit();
	
	public boolean isExpanded();
	
	public void setCredit(boolean credit) throws InvalidValueException;
	
	public void setExpanded(boolean isExpanded);
	
	public void setName(String name) throws InvalidValueException;
}
