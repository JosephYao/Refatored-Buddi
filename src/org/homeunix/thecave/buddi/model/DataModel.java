/*
 * Created on Jul 29, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.model;

import java.util.LinkedList;
import java.util.List;

import org.homeunix.thecave.buddi.model.beans.Account;
import org.homeunix.thecave.buddi.model.beans.BudgetPeriod;
import org.homeunix.thecave.buddi.model.beans.ScheduledTransaction;
import org.homeunix.thecave.buddi.model.beans.Transaction;
import org.homeunix.thecave.buddi.model.beans.Type;


/**
 * @author wyatt
 * The main container class for the new data model, to be implemented in Buddi version 3.0.
 * This contains all the  
 */
public class DataModel {
	//User's data objects
	private List<Account> accounts = new LinkedList<Account>(); 
	private List<BudgetPeriod> budgetPeriods = new LinkedList<BudgetPeriod>();
	private List<Type> types = new LinkedList<Type>();
	private List<Transaction> transactions = new LinkedList<Transaction>();
	private List<ScheduledTransaction> scheduledTransactions = new LinkedList<ScheduledTransaction>();

	//Configuration Data for budget period
	private Long length;
	private Boolean lengthInDays;

	
	
	public List<Account> getAccounts() {
		return accounts;
	}
	public void setAccounts(List<Account> accounts) {
		this.accounts = accounts;
	}
	public List<BudgetPeriod> getBudgetPeriods() {
		return budgetPeriods;
	}
	public void setBudgetPeriods(List<BudgetPeriod> budgetPeriods) {
		this.budgetPeriods = budgetPeriods;
	}
	public List<ScheduledTransaction> getScheduledTransactions() {
		return scheduledTransactions;
	}
	public void setScheduledTransactions(
			List<ScheduledTransaction> scheduledTransactions) {
		this.scheduledTransactions = scheduledTransactions;
	}
	public List<Transaction> getTransactions() {
		return transactions;
	}
	public void setTransactions(List<Transaction> transactions) {
		this.transactions = transactions;
	}
	public List<Type> getTypes() {
		return types;
	}
	public void setTypes(List<Type> types) {
		this.types = types;
	}
	public Long getLength() {
		return length;
	}
	public void setLength(Long length) {
		this.length = length;
	}
	public Boolean getLengthInDays() {
		return lengthInDays;
	}
	public void setLengthInDays(Boolean lengthInDays) {
		this.lengthInDays = lengthInDays;
	}
	
	
}
