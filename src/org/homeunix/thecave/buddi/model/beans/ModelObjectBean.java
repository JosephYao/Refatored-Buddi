/*
 * Created on Jul 29, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.model.beans;

import java.util.Date;

import org.homeunix.thecave.buddi.model.DataModel;

public abstract class ModelObjectBean {
	
	private Date modifiedDate;
	private String uid;
	private String userUid;
	
	
	
	public Date getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	public String getUid() {
		if (uid == null || uid.length() == 0){
			setUid(DataModel.getGeneratedUid(this));
		}
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getUserUid() {
		return userUid;
	}
	public void setUserUid(String userUid) {
		this.userUid = userUid;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof ModelObjectBean)
			return this.getUid().equals(((ModelObjectBean) obj).getUid());
		return false;
	}

}
