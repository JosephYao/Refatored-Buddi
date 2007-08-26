/*
 * Created on Aug 23, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.plugin.api.model.impl;

import org.homeunix.thecave.buddi.model.Type;
import org.homeunix.thecave.buddi.plugin.api.model.MutableType;

public class MutableTypeImpl extends MutableModelObjectImpl implements MutableType {

	public MutableTypeImpl(Type type) {
		super(type);
	}
	
	public Type getType(){
		return (Type) getRaw();
	}
	
	public String getName(){
		return getType().getName();
	}
	
	public boolean isCredit(){
		return getType().isCredit();
	}

	public void setName(String name) {
		getType().setName(name);
	}
}
