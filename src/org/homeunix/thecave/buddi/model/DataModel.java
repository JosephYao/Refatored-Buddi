/*
 * Created on Jul 29, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.model;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

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
//	private List<Account> accounts = new LinkedList<Account>(); 
	private List<BudgetPeriod> budgetPeriods = new LinkedList<BudgetPeriod>();
	private List<Type> types = new LinkedList<Type>();
	private List<Transaction> transactions = new LinkedList<Transaction>();
	private List<ScheduledTransaction> scheduledTransactions = new LinkedList<ScheduledTransaction>();

	//Configuration Data for budget period
	private Long length;
	private Boolean lengthInDays;

	//Other information
	private Date modifiedDate;
	
//	public List<Account> getAccounts() {
//		return accounts;
//	}
//	public void setAccounts(List<Account> accounts) {
//		this.setModifiedDate(new Date());
//		this.accounts = accounts;
//	}
	public List<BudgetPeriod> getBudgetPeriods() {
		return budgetPeriods;
	}
	public void setBudgetPeriods(List<BudgetPeriod> budgetPeriods) {
		this.setModifiedDate(new Date());
		this.budgetPeriods = budgetPeriods;
	}
	public List<ScheduledTransaction> getScheduledTransactions() {
		return scheduledTransactions;
	}
	public void setScheduledTransactions(List<ScheduledTransaction> scheduledTransactions) {
		this.setModifiedDate(new Date());
		this.scheduledTransactions = scheduledTransactions;
	}
	public List<Transaction> getTransactions() {
		return transactions;
	}
	public void setTransactions(List<Transaction> transactions) {
		this.setModifiedDate(new Date());
		this.transactions = transactions;
	}
	public List<Type> getTypes() {
		return types;
	}
	public void setTypes(List<Type> types) {
		this.setModifiedDate(new Date());
		this.types = types;
	}
	public Long getLength() {
		return length;
	}
	public void setLength(Long length) {
		this.setModifiedDate(new Date());
		this.length = length;
	}
	public Boolean getLengthInDays() {
		return lengthInDays;
	}
	public void setLengthInDays(Boolean lengthInDays) {
		this.setModifiedDate(new Date());
		this.lengthInDays = lengthInDays;
	}
	public Date getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	
//	public void addAccount(Account account){
//		this.checkLists();
//		this.setModifiedDate(new Date());
//		this.accounts.add(account);
//	}
	public void addBudgetPeriod(BudgetPeriod budgetPeriod){
		this.checkLists();
		this.setModifiedDate(new Date());
		this.budgetPeriods.add(budgetPeriod);
	}
	public void addScheduledTransaction(ScheduledTransaction scheduledTransaction){
		this.checkLists();
		this.setModifiedDate(new Date());
		this.scheduledTransactions.add(scheduledTransaction);
	}
	public void addTransaction(Transaction transaction){
		this.checkLists();
		this.setModifiedDate(new Date());
		this.transactions.add(transaction);
	}
	public void addType(Type type){
		this.checkLists();
		this.setModifiedDate(new Date());
		this.types.add(type);
	}
	
//	public boolean removeAccount(Account account){
//		this.checkLists();
//		this.setModifiedDate(new Date());
//		return this.accounts.remove(account);
//	}
	public boolean removeBudgetPeriod(BudgetPeriod budgetPeriod){
		this.checkLists();
		this.setModifiedDate(new Date());
		return this.budgetPeriods.remove(budgetPeriod);
	}
	public boolean removeScheduledTransaction(ScheduledTransaction scheduledTransaction){
		this.checkLists();
		this.setModifiedDate(new Date());
		return this.scheduledTransactions.remove(scheduledTransaction);
	}
	public boolean removeTransaction(Transaction transaction){
		this.checkLists();
		this.setModifiedDate(new Date());
		return this.transactions.remove(transaction);
	}
	public boolean removeType(Type type){
		this.checkLists();
		this.setModifiedDate(new Date());
		return this.types.remove(type);
	}
	
	private void checkLists(){
//		if (this.accounts == null)
//			this.accounts = new LinkedList<Account>();
		if (this.budgetPeriods == null)
			this.budgetPeriods = new LinkedList<BudgetPeriod>();
		if (this.transactions == null)
			this.transactions = new LinkedList<Transaction>();
		if (this.scheduledTransactions == null)
			this.scheduledTransactions = new LinkedList<ScheduledTransaction>();
		if (this.types == null)
			this.types = new LinkedList<Type>();
	}

}
