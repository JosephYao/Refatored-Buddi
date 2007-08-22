/*
 * Created on Aug 12, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.plugin.api.model.immutable;

import org.homeunix.thecave.buddi.model.Type;

public class ImmutableType extends ImmutableModelObject {
	
	public ImmutableType(Type type) {
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
