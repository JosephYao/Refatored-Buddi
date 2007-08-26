/*
 * Created on Jul 29, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.model.beans;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;



/**
 * The main container class for the new data model, to be implemented in Buddi version 3.0.
 * This contains all the data, most of it in list form.  This object is the root of the XML
 * file as serialized by XMLEncoder.
 * 
 * @author wyatt
 */
public class DataModelBean extends ModelObjectBean {
	//User data objects
	private List<AccountBean> accounts = new LinkedList<AccountBean>();
	private List<BudgetCategoryBean> budgetCategories = new LinkedList<BudgetCategoryBean>();
	private Map<String, BudgetPeriodBean> budgetPeriods = new HashMap<String, BudgetPeriodBean>();
	private List<TypeBean> types = new LinkedList<TypeBean>();
	private List<TransactionBean> transactions = new LinkedList<TransactionBean>();
	private List<ScheduledTransactionBean> scheduledTransactions = new LinkedList<ScheduledTransactionBean>();

	public List<AccountBean> getAccounts() {
		checkLists();
		return accounts;
	}
	public void setAccounts(List<AccountBean> accounts) {
		this.accounts = accounts;
	}
	
	public List<BudgetCategoryBean> getBudgetCategories() {
		return budgetCategories;
	}
	public void setBudgetCategories(List<BudgetCategoryBean> budgetCategories) {
		this.budgetCategories = budgetCategories;
	}
	public Map<String, BudgetPeriodBean> getBudgetPeriods() {
		return budgetPeriods;
	}
	public void setBudgetPeriods(Map<String, BudgetPeriodBean> budgetPeriods) {
		this.budgetPeriods = budgetPeriods;
	}
	public List<ScheduledTransactionBean> getScheduledTransactions() {
		checkLists();
		return scheduledTransactions;
	}
	public void setScheduledTransactions(List<ScheduledTransactionBean> scheduledTransactions) {
		this.scheduledTransactions = scheduledTransactions;
	}
	public List<TransactionBean> getTransactions() {
		checkLists();
		return transactions;
	}
	public void setTransactions(List<TransactionBean> transactions) {
		this.transactions = transactions;
	}
	public List<TypeBean> getTypes() {
		checkLists();
		return types;
	}
	public void setTypes(List<TypeBean> types) {
		this.types = types;
	}
	
	private void checkLists(){
		if (this.accounts == null)
			this.accounts = new LinkedList<AccountBean>();
		if (this.budgetPeriods == null)
			this.budgetPeriods = new HashMap<String, BudgetPeriodBean>();
		if (this.transactions == null)
			this.transactions = new LinkedList<TransactionBean>();
		if (this.scheduledTransactions == null)
			this.scheduledTransactions = new LinkedList<ScheduledTransactionBean>();
		if (this.types == null)
			this.types = new LinkedList<TypeBean>();
	}
}
