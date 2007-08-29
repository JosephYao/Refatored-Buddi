/*
 * Created on Aug 11, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.model;

import org.homeunix.thecave.buddi.model.beans.ModelObjectBean;
import org.homeunix.thecave.buddi.model.exception.DocumentAlreadySetException;

public interface ModelObject extends Comparable<ModelObject> {
	
	public int compareTo(ModelObject o);
	
	/**
	 * Returns the bean associated with this given model object.  This is not 
	 * meant for direct access, and doing so can easily break the data model.
	 * Don't use this unless you know exactly what you are doing!
	 * @return
	 */
	public ModelObjectBean getBean();
		
	/**
	 * Returns the UID string for this object.
	 * @return
	 */
	public String getUid();
	
	/**
	 * Returns the document associated with this model object, or null if
	 * there is no document. 
	 * @return
	 */
	public Document getDocument();
	
	/**
	 * Sets the document.  You can only do this once per object; once the document 
	 * is set, you cannot set another one.
	 * @param document
	 */
	public void setDocument(Document document) throws DocumentAlreadySetException;
}
