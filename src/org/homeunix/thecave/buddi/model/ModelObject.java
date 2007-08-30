/*
 * Created on Aug 11, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.model;

import org.homeunix.thecave.buddi.model.exception.DocumentAlreadySetException;

public interface ModelObject extends Comparable<ModelObject> {
	
	public int compareTo(ModelObject o);
	
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
	Document getDocument();
	
	/**
	 * Sets the document.
	 * @param document
	 */
	void setDocument(Document document) throws DocumentAlreadySetException;
}
