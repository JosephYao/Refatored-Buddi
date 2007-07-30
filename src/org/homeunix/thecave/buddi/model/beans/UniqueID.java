/*
 * Created on Jul 29, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.model.beans;

public class UniqueID {
	private Integer uid;
	private static Integer currentUID = 0;
		
	public static UniqueID getNextUID(){
		currentUID++;
		UniqueID uid = new UniqueID();
		uid.setUid(currentUID);
		return uid;
	}
	
	
	public static Integer getCurrentUID() {
		return currentUID;
	}
	public static void setCurrentUID(Integer nextUID) {
		UniqueID.currentUID = nextUID;
	}
	public Integer getUid() {
		return uid;
	}
	public void setUid(Integer uid) {
		this.uid = uid;
	}
}
