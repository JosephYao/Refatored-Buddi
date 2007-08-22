/*
 * Created on Aug 12, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.plugin.api.model.mutable;

import org.homeunix.thecave.buddi.model.ModelObject;

public abstract class MutableModelObject implements Comparable<MutableModelObject> {
	private ModelObject raw;
	
	public MutableModelObject(ModelObject raw) {
		this.raw = raw;
	}
	
	public ModelObject getRaw(){
		return raw;
	}
	
	public int compareTo(MutableModelObject o) {
		return this.getRaw().compareTo(o.getRaw());
	}
}
