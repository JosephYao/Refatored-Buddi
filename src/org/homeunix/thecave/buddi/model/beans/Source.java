/*
 * Created on Jul 29, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.model.beans;

import java.util.Date;

public abstract class Source implements StandardFields {
	private String name;
	private Boolean deleted;
	private Date createdDate;
	private String notes;
	
	//User Interface stuff
	private Boolean expanded;
	
	//Required Model stuff
	private UniqueID systemUid;
	private String userUid;
	private Date modifiedDate;
	
	
	
	public Date getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Date lastModified) {
		this.modifiedDate = lastModified;
	}
	public UniqueID getSystemUid() {
		return systemUid;
	}
	public void setSystemUid(UniqueID systemUid) {
		this.setModifiedDate(new Date());
		this.systemUid = systemUid;
	}
	public String getUserUid() {
		return userUid;
	}
	public void setUserUid(String userUid) {
		this.setModifiedDate(new Date());
		this.userUid = userUid;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.setModifiedDate(new Date());
		this.createdDate = createdDate;
	}
	public Boolean getDeleted() {
		return deleted;
	}
	public void setDeleted(Boolean deleted) {
		this.setModifiedDate(new Date());
		this.deleted = deleted;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.setModifiedDate(new Date());
		this.name = name;
	}
	public Boolean getExpanded() {
		return expanded;
	}
	public void setExpanded(Boolean expanded) {
		this.setModifiedDate(new Date());
		this.expanded = expanded;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.setModifiedDate(new Date());
		this.notes = notes;
	}
}
