/*
 * Created on Aug 12, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.plugin.api.model.mutable;

import org.homeunix.thecave.buddi.model.Type;

public class MutableType extends MutableModelObject {
	
	public MutableType(Type type) {
		super(type);
	}
	
	public Type getType(){
		return (Type) getRaw();
	}
}
