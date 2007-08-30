/*
 * Created on Jul 29, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.model.beans;

import java.util.Date;

import org.homeunix.thecave.buddi.model.AccountType;

public class AccountTypeImpl extends ModelObjectImpl implements AccountType {
	private String name;
	private boolean credit;
	private boolean isExpanded;

//	public TypeBean() {
//		this.setUid(DataModel.getGeneratedUid(this));
//	}
	
	public boolean isCredit() {
		return credit;
	}
	public void setCredit(boolean credit) {
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
	public boolean isExpanded() {
		return isExpanded;
	}
	public void setExpanded(boolean isExpanded) {
		this.isExpanded = isExpanded;
	}
	@Override
	public String toString() {
		return getName() + ":0x" + getUid();
	}
}
