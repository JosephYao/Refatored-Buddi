/*
 * Created on Jul 29, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.model.beans;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class Transaction implements StandardFields {
	private Date date;
	private String description;
	private String number;
	private String memo;
	private Boolean cleared;
	private Boolean reconciled;
	private List<TransactionSplit> splits = new LinkedList<TransactionSplit>();
	
	private Boolean scheduled;
	
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
	public Boolean getCleared() {
		return cleared;
	}
	public void setCleared(Boolean cleared) {
		this.setModifiedDate(new Date());
		this.cleared = cleared;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.setModifiedDate(new Date());
		this.date = date;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.setModifiedDate(new Date());
		this.description = description;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.setModifiedDate(new Date());
		this.memo = memo;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.setModifiedDate(new Date());
		this.number = number;
	}
	public Boolean getReconciled() {
		return reconciled;
	}
	public void setReconciled(Boolean reconciled) {
		this.setModifiedDate(new Date());
		this.reconciled = reconciled;
	}
	public Boolean getScheduled() {
		return scheduled;
	}
	public void setScheduled(Boolean scheduled) {
		this.setModifiedDate(new Date());
		this.scheduled = scheduled;
	}
	public List<TransactionSplit> getSplits() {
		return splits;
	}
	public void setSplits(List<TransactionSplit> splits) {
		this.setModifiedDate(new Date());
		this.splits = splits;
	}
	
	public void addSplit(TransactionSplit split){
		this.checkLists();
		this.setModifiedDate(new Date());
		this.splits.add(split);
	}
	public boolean removeSplit(TransactionSplit split){
		this.checkLists();
		this.setModifiedDate(new Date());
		return this.splits.remove(split);
	}
	
	private void checkLists(){
		if (this.splits == null)
			this.splits = new LinkedList<TransactionSplit>();
	}

}
