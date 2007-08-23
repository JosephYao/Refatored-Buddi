/*
 * Created on Aug 12, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.plugin.api.model;

import java.util.Date;

import org.homeunix.thecave.buddi.model.Source;

public interface ImmutableSource extends ImmutableModelObject {
	
	public Source getSource();
	
	public Date getCreatedDate();
	
	public boolean isDeleted();
	
	public String getName();
	
	public String getFullName();
	
	public String getNotes();
}
