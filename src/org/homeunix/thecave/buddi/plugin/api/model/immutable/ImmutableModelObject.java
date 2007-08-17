/*
 * Created on Aug 12, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.plugin.api.model.immutable;

import org.homeunix.thecave.buddi.model.ModelObject;

public class ImmutableModelObject {
	private ModelObject raw;
	
	public ImmutableModelObject(ModelObject raw) {
		this.raw = raw;
	}
	
	public ModelObject getRaw(){
		return raw;
	}
}
