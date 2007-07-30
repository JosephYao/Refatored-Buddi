/*
 * Created on Jul 29, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.model.beans;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class Type implements StandardFields {
	private String name;
	private Boolean credit;
	
	private UniqueID systemUid;
	private String userUid;
	private Date modifiedDate;
	
	private List<Account> accounts = new LinkedList<Account>();
	
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
	public Boolean getCredit() {
		return credit;
	}
	public void setCredit(Boolean credit) {
		this.setModifiedDate(new Date());
		this.credit = credit;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.setModifiedDate(new Date());
		this.name = name;
	}
	public List<Account> getAccounts() {
		return accounts;
	}
	public void setAccounts(List<Account> accounts) {
		this.setModifiedDate(new Date());
		this.accounts = accounts;
	}

	public void addAccount(Account account){
		this.checkLists();
		this.setModifiedDate(new Date());
		this.accounts.add(account);
	}
	public boolean removeAccount(Account account){
		this.checkLists();
		this.setModifiedDate(new Date());
		return this.accounts.remove(account);
	}

	private void checkLists(){
		if (this.accounts == null)
			this.accounts = new LinkedList<Account>();
	}

}
