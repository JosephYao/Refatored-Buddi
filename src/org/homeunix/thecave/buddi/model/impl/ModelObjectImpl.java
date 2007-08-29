/*
 * Created on Aug 11, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.model.impl;

import org.homeunix.thecave.buddi.model.Document;
import org.homeunix.thecave.buddi.model.ModelObject;
import org.homeunix.thecave.buddi.model.beans.ModelObjectBean;
import org.homeunix.thecave.buddi.model.exception.DocumentAlreadySetException;
import org.homeunix.thecave.buddi.model.exception.ModelException;

public abstract class ModelObjectImpl implements ModelObject {
	private Document document;
	private final ModelObjectBean bean;
	
	ModelObjectImpl(ModelObjectBean bean) throws ModelException {
		if (bean == null)
			throw new ModelException("Model Bean cannot be null.");
		this.bean = bean;
	}
	
	public ModelObjectBean getBean(){
		return bean;
	}
	
	@Override
	public int hashCode() {
		return bean.getUid().hashCode();
	}
		
	public int compareTo(ModelObject o) {
		//We fall back to comparing strings.  Override this in subclasses
		// to provide more fine grained comparisons between similar objects.
		return this.toString().compareTo(o.toString());
	}
	
	public boolean equals(Object obj) {
		if (obj instanceof ModelObject)
			return getBean().equals(((ModelObject) obj).getBean());
		
		return false;
	}
	
	public String getUid(){
		return getBean().getUid();
	}
	
	public Document getDocument() {
		return null;
	}
	
	public void setDocument(Document document) throws DocumentAlreadySetException {
		if (this.document != null)
			throw new DocumentAlreadySetException("The object " + this.toString() + " already has a document set.");
		this.document = document;
	}
}
