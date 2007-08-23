/*
 * Created on Aug 12, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.plugin.api.model.impl;

import org.homeunix.thecave.buddi.model.ModelObject;

public abstract class ImmutableModelObject implements Comparable<ImmutableModelObject> {
	private ModelObject raw;
	
	public ImmutableModelObject(ModelObject raw) {
		this.raw = raw;
	}
	
	public ModelObject getRaw(){
		return raw;
	}
	
	public int compareTo(ImmutableModelObject o) {
		return this.getRaw().compareTo(o.getRaw());
	}
	
	/**
	 * Returns the UID string for this object.
	 * @return
	 */
	public String getUid(){
		return getRaw().getUid();
	}
}
