/*
 * Created on Jul 29, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.model.impl;

import java.util.Date;

import org.homeunix.thecave.buddi.model.BudgetCategory;
import org.homeunix.thecave.buddi.model.ModelObject;
import org.homeunix.thecave.buddi.model.Source;
import org.homeunix.thecave.buddi.model.Transaction;
import org.homeunix.thecave.moss.util.DateFunctions;

/**
 * Default implementation of a Transaction.  You should not create this object directly; 
 * instead, please use the ModelFactory to create it, as this will ensure that all
 * required fields are correctly set.
 * @author wyatt
 *
 */
public class TransactionImpl extends ModelObjectImpl implements Transaction {
	private Date date;
	private String description;
	private String number;
	private long amount;
	private Source from;
	private Source to;
	private boolean clearedFrom;
	private boolean clearedTo;
	private boolean reconciledFrom;
	private boolean reconciledTo; 
	private String memo;

	private boolean scheduled;

	private long balanceFrom;
	private long balanceTo;
	
	
	@Override
	public int compareTo(ModelObject arg0) {
		if (arg0 instanceof Transaction){
			Transaction t = (Transaction) arg0;

			//For regular transactions, first we sort by date 
			if (!DateFunctions.isSameDay(this.getDate(), t.getDate()))
				return this.getDate().compareTo(t.getDate());

			//Next we sort by debit / credit.  This is a nebulous beast, because of negative 
			// amounts, credit accounts, splits, etc.
			// We check the transaction source, and use some logic to determine if this means it
			// is inflow or outflow. 
			if (this.isInflow() != t.isInflow()){
				if (this.isInflow())
					return -1;
				return 1;
			}

			//If everything else is the same, we sort on description.
			else 
				return this.getDescription().compareTo(t.getDescription());
		}
		return super.compareTo(arg0);
	}
	public long getAmount() {
		return amount;
	}
	public long getBalanceFrom() {
		return balanceFrom;
	}
	public long getBalanceTo() {
		return balanceTo;
	}
	public Date getDate() {
		return date;
	}
	public String getDescription() {
		return description;
	}
	public Source getFrom() {
		return from;
	}
	public String getMemo() {
		return memo;
	}
	public String getNumber() {
		return number;
	}
	public Source getTo() {
		return to;
	}
	public boolean isClearedFrom() {
		return clearedFrom;
	}
	public boolean isClearedTo() {
		return clearedTo;
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
	public boolean isReconciledFrom() {
		return reconciledFrom;
	}
	public boolean isReconciledTo() {
		return reconciledTo;
	}
	public boolean isScheduled() {
		return scheduled;
	}
	public void setAmount(long amount) {
		this.amount = amount;
		setChanged();
	}
	public void setBalanceFrom(long balanceFrom) {
		this.balanceFrom = balanceFrom;
	}
	public void setBalanceTo(long balanceTo) {
		this.balanceTo = balanceTo;
	}
	public void setClearedFrom(boolean cleared) {
		this.clearedFrom = cleared;
		setChanged();
	}
	public void setClearedTo(boolean cleared) {
		this.clearedTo = cleared;
		setChanged();
	}
	public void setDate(Date date) {
		this.date = date;
		setChanged();
	}
	public void setDescription(String description) {
		this.description = description;
		setChanged();
	}
	public void setFrom(Source from) {
		this.from = from;
		setChanged();
	}
	public void setMemo(String memo) {
		this.memo = memo;
		setChanged();
	}
	public void setNumber(String number) {
		this.number = number;
		setChanged();
	}
	public void setReconciledFrom(boolean reconciled) {
		this.reconciledFrom = reconciled;
		setChanged();
	}
	public void setReconciledTo(boolean reconciled) {
		this.reconciledTo = reconciled;
		setChanged();
	}
	public void setScheduled(boolean scheduled) {
		this.scheduled = scheduled;
	}
	public void setTo(Source to) {
		this.to = to;
		setChanged();
	}
}
