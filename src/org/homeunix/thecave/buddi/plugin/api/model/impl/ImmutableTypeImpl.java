/*
 * Created on Aug 12, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.plugin.api.model.impl;

import org.homeunix.thecave.buddi.model.Type;
import org.homeunix.thecave.buddi.plugin.api.model.ImmutableType;

public class ImmutableTypeImpl extends ImmutableModelObjectImpl implements ImmutableType {
	
	public ImmutableTypeImpl(Type type) {
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
}
