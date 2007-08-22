/*
 * Created on Jul 29, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.model.beans;

import java.util.Date;

public class TransactionBean extends ModelObjectBean {
	private Date date;
	private String description;
	private String number;
	private long amount;
	private SourceBean from;
	private SourceBean to;
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
	public SourceBean getFrom() {
		return from;
	}
	public void setFrom(SourceBean from) {
		this.from = from;
	}
	public SourceBean getTo() {
		return to;
	}
	public void setTo(SourceBean to) {
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
	public long volatileGetBalanceTo() {
		return balanceTo;
	}
	public void volatileSetBalanceTo(long balanceTo) {
		this.balanceTo = balanceTo;
	}
	public long volatileGetBalanceFrom() {
		return balanceFrom;
	}
	public void volatileSetBalanceFrom(long balanceFrom) {
		this.balanceFrom = balanceFrom;
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
