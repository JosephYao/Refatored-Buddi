/*
 * Created on Jul 29, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.model.beans;

import java.util.Date;

public interface StandardFields {
	
	public Date getModifiedDate();
	public void setModifiedDate(Date lastModified);
	public UniqueID getSystemUid();
	public void setSystemUid(UniqueID systemUid);
	public String getUserUid();
	public void setUserUid(String userUid);
}
