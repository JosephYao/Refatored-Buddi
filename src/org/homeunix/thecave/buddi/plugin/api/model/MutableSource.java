/*
 * Created on Aug 22, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.plugin.api.model;

public interface MutableSource extends ImmutableSource {
	public void setDeleted(boolean deleted);
	public void setName(String name);
	public void setNotes(String notes);

}
