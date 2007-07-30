/*
 * Created on Jul 29, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.model.beans;

import java.util.Date;

public class Type implements ModelFields {
	private String name;
	private Boolean credit;
	
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
		this.systemUid = systemUid;
	}
	public String getUserUid() {
		return userUid;
	}
	public void setUserUid(String userUid) {
		this.userUid = userUid;
	}	
	public Boolean getCredit() {
		return credit;
	}
	public void setCredit(Boolean credit) {
		this.credit = credit;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
