/*
 * Created on Jul 29, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.model.beans;

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
		this.amount = amount;
	}
	public Boolean getIncome() {
		return income;
	}
	public void setIncome(Boolean income) {
		this.income = income;
	}
	public List<BudgetCategory> getChildren() {
		return children;
	}
	public void setChildren(List<BudgetCategory> children) {
		this.children = children;
	}
}
