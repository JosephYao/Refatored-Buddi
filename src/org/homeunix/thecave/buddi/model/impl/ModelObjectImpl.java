/*
 * Created on Jul 29, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.model.impl;

import java.util.Date;

import org.homeunix.thecave.buddi.model.Document;
import org.homeunix.thecave.buddi.model.ModelObject;

/**
 * The class from which most other model objects descend from.  You should not 
 * instantiate this class directly.
 * 
 * @author wyatt
 *
 */
public abstract class ModelObjectImpl implements ModelObject {
	
	private Date modifiedDate;
	private String uid;
	private Document document;
		
	public void setChanged(){
		setModifiedDate(new Date());
		if (document != null)
			document.setChanged();
	}
	public Date getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	public String getUid() {
		if (uid == null || uid.length() == 0){
			setUid(ModelFactory.getGeneratedUid(this));
		}
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public Document getDocument() {
		return document;
	}
	public void setDocument(Document document) {
		this.document = document;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof ModelObjectImpl)
			return this.getUid().equals(((ModelObjectImpl) obj).getUid());
		return false;
	}
	
	public int compareTo(ModelObject o) {
		return (getUid().compareTo(o.getUid()));
	}

}
