/*
 * Created on Jul 29, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.model.beans;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class BudgetCategory extends Source {
	private Long amount;
	private Boolean income;
	private List<BudgetCategory> children = new LinkedList<BudgetCategory>();
		
	public Long getAmount() {
		return amount;
	}
	public void setAmount(Long amount) {
		this.setModifiedDate(new Date());
		this.amount = amount;
	}
	public Boolean getIncome() {
		return income;
	}
	public void setIncome(Boolean income) {
		this.setModifiedDate(new Date());
		this.income = income;
	}
	public List<BudgetCategory> getChildren() {
		return children;
	}
	public void setChildren(List<BudgetCategory> children) {
		this.setModifiedDate(new Date());
		this.children = children;
	}
	public void addChild(BudgetCategory child){
		this.checkLists();		
		this.setModifiedDate(new Date());
		this.children.add(child);
	}
	public boolean removeChild(BudgetCategory child){
		checkLists();
		this.setModifiedDate(new Date());
		return this.children.remove(child);
	}
	
	
	private void checkLists(){
		if (this.children == null)
			this.children = new LinkedList<BudgetCategory>();
	}
}
