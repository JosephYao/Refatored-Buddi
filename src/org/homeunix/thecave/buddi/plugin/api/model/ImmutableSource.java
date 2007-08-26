/*
 * Created on Aug 12, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.plugin.api.model;

import java.util.Date;

import org.homeunix.thecave.buddi.model.Source;

public interface ImmutableSource extends ImmutableModelObject {
	
	/**
	 * Returns the wrapped object from the underlying data model.  By 
	 * accessing this method, you bypass all protection which the Buddi API
	 * gives you; it is not recommended to use this method unless you understand
	 * the risks associated with it. 
	 * @return
	 */
	public Source getSource();
	
	/**
	 * Returns the earliest date assoicated with this source.  This is obtained 
	 * by looking at the associated transactions / period dates.
	 * @return
	 */
	public Date getEarliestDate();
	
	/**
	 * Is this source deleted?
	 * @return
	 */
	public boolean isDeleted();
	
	/**
	 * Returns the name of this source 
	 * @return
	 */
	public String getName();
	
	/**
	 * Returns the full name of this source.  The full name is the name, plus
	 * the path to get there (parent categories, etc).
	 * @return
	 */
	public String getFullName();
	
	/**
	 * Return any notes associated with this source
	 * @return
	 */
	public String getNotes();
}
