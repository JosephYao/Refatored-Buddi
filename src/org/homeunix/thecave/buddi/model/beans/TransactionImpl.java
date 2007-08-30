/*
 * Created on Jul 29, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.model.beans;

import java.util.Date;

import org.homeunix.thecave.buddi.model.BudgetCategory;
import org.homeunix.thecave.buddi.model.Source;
import org.homeunix.thecave.buddi.model.Transaction;

public class TransactionImpl extends ModelObjectImpl implements Transaction {
	private Date date;
	private String description;
	private String number;
	private long amount;
	private Source from;
	private Source to;
	private boolean cleared;
	private boolean reconciled; 
	private String memo;

	private boolean scheduled;

	private long balanceFrom;
	private long balanceTo;
	
	public boolean isCleared() {
		return cleared;
	}
	public void setCleared(boolean cleared) {
		this.cleared = cleared;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public boolean isReconciled() {
		return reconciled;
	}
	public void setReconciled(boolean reconciled) {
		this.reconciled = reconciled;
	}
	public Source getFrom() {
		return from;
	}
	public void setFrom(Source from) {
		this.from = from;
	}
	public Source getTo() {
		return to;
	}
	public void setTo(Source to) {
		this.to = to;
	}
	public boolean isScheduled() {
		return scheduled;
	}
	public void setScheduled(boolean scheduled) {
		this.scheduled = scheduled;
	}
	public long getAmount() {
		return amount;
	}
	public void setAmount(long amount) {
		this.amount = amount;
	}
	public long getBalanceTo() {
		return balanceTo;
	}
	public void setBalanceTo(long balanceTo) {
		this.balanceTo = balanceTo;
	}
	public long getBalanceFrom() {
		return balanceFrom;
	}
	public void setBalanceFrom(long balanceFrom) {
		this.balanceFrom = balanceFrom;
	}
	public boolean isInflow(){
		if (getFrom() instanceof BudgetCategory){
			return this.getAmount() >= 0;
		}
		if (getTo() instanceof BudgetCategory){
			return this.getAmount() < 0;
		}

		//If neither sources are BudgetCategory, this is not an inflow.
		return false;
	}
	

//	public void calculateBalance(){
//	//Update balance in affected accounts
//	for (TransactionSplit split : splits) {
//	if (split.getFrom() instanceof Account){
//	((Account) split.getFrom()).calculateBalance();
//	}
//	if (split.getTo() instanceof Account){
//	((Account) split.getTo()).calculateBalance();
//	}

//	}
//	}
}
