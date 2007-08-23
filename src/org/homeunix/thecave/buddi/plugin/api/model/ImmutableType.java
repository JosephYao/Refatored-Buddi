/*
 * Created on Aug 12, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.plugin.api.model;

import org.homeunix.thecave.buddi.model.Type;

public interface ImmutableType extends ImmutableModelObject {
	
	public Type getType();
	
	public String getName();
	
	public boolean isCredit();
}
