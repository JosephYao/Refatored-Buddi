/*
 * Created on Aug 12, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.plugin.api.model.immutable;

import java.util.Date;

import org.homeunix.thecave.buddi.model.Source;

public abstract class ImmutableSource extends ImmutableModelObject {
	
	public ImmutableSource(Source source) {
		super(source);
	}
	public Source getSource(){
		return (Source) getRaw(); 
	}
	public Date getCreatedDate() {
		return getSource().getCreatedDate();
	}
	public boolean isDeleted() {
		return getSource().isDeleted();
	}
	public String getName() {
		return getSource().getName();
	}
	public String getFullName() {
		return getSource().getFullName();
	}
	public String getNotes() {
		return getSource().getNotes();
	}
}
