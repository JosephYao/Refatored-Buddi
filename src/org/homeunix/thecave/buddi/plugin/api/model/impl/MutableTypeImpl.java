/*
 * Created on Aug 23, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.plugin.api.model.impl;

import org.homeunix.thecave.buddi.model.AccountType;
import org.homeunix.thecave.buddi.model.api.exception.InvalidValueException;
import org.homeunix.thecave.buddi.plugin.api.model.MutableAccountType;

public class MutableTypeImpl extends MutableModelObjectImpl implements MutableAccountType {

	public MutableTypeImpl(AccountType type) {
		super(type);
	}
	
	public AccountType getType(){
		return (AccountType) getRaw();
	}
	
	public String getName(){
		return getType().getName();
	}
	
	public boolean isCredit(){
		return getType().isCredit();
	}

	public void setName(String name) throws InvalidValueException{
		getType().setName(name);
	}
}
