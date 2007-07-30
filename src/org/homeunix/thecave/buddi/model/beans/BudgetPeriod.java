/*
 * Created on Jul 29, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.model.beans;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class BudgetPeriod implements ModelFields {	
	private List<BudgetCategory> budgetCategories = new LinkedList<BudgetCategory>();
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
	public List<BudgetCategory> getBudgetCategories() {
		return budgetCategories;
	}
	public void setBudgetCategories(List<BudgetCategory> budgetCategories) {
		this.budgetCategories = budgetCategories;
	}
}
