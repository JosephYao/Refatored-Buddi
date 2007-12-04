/*
 * Created on Jul 29, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.model.impl;

import java.util.Date;

import org.homeunix.thecave.buddi.i18n.BuddiKeys;
import org.homeunix.thecave.buddi.model.Account;
import org.homeunix.thecave.buddi.model.BudgetCategory;
import org.homeunix.thecave.buddi.model.ModelObject;
import org.homeunix.thecave.buddi.model.Source;
import org.homeunix.thecave.buddi.model.Transaction;
import org.homeunix.thecave.buddi.plugin.api.util.TextFormatter;
import org.homeunix.thecave.moss.util.DateFunctions;

/**
 * Default implementation of a Transaction.  You should not create this object directly; 
 * instead, please use the ModelFactory to create it, as this will ensure that all
 * required fields are correctly set.
 * @author wyatt
 *
 */
public class TransactionImpl extends ModelObjectImpl implements Transaction {
	private Day date;
//	private String dateString;
	private String description;
	private String number;
	private long amount;
	private Source from;
	private Source to;
	private boolean deleted;
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
//	public Date getDate() {
//		if (getDateString() == null){
//			setDate(date);
//		}
//		
//		return stringToDate(getDateString());
//	}
	public Date getDate(){
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
		if (this.amount != amount)
			setChanged();
		this.amount = amount;
	}
	public void setBalanceFrom(long balanceFrom) {
		this.balanceFrom = balanceFrom;
	}
	public void setBalanceTo(long balanceTo) {
		this.balanceTo = balanceTo;
	}
	public void setClearedFrom(boolean cleared) {
		if (this.clearedFrom != cleared)
			setChanged();
		this.clearedFrom = cleared;
		//If one of the To / From source is either
		// a budget category or a Prepaid account,
		// we set both of the flags to the same value.
		if (this.getTo() != null
				&& this.getFrom() != null
				&& (this.getTo() instanceof BudgetCategory
						|| this.getFrom() instanceof BudgetCategory
						|| ((Account) this.getTo()).getAccountType().getName().equals(TextFormatter.getTranslation(BuddiKeys.PREPAID_ACCOUNT))
						|| ((Account) this.getFrom()).getAccountType().getName().equals(TextFormatter.getTranslation(BuddiKeys.PREPAID_ACCOUNT)))){
			this.clearedTo = cleared;
		}
	}
	public void setClearedTo(boolean cleared) {
		if (this.clearedTo != cleared)
			setChanged();
		this.clearedTo = cleared;
		//If one of the To / From source is either
		// a budget category or a Prepaid account,
		// we set both of the flags to the same value.
		if (this.getTo() != null
				&& this.getFrom() != null
				&& (this.getTo() instanceof BudgetCategory
						|| this.getFrom() instanceof BudgetCategory
						|| ((Account) this.getTo()).getAccountType().getName().equals(TextFormatter.getTranslation(BuddiKeys.PREPAID_ACCOUNT))
						|| ((Account) this.getFrom()).getAccountType().getName().equals(TextFormatter.getTranslation(BuddiKeys.PREPAID_ACCOUNT)))){
			this.clearedFrom = cleared;
		}
	}
	public void setDate(Date date) {
		if (this.date != null && this.date.equals(date))
			setChanged();
		this.date = new Day(date);
	}
	public void setDescription(String description) {
		if (this.description != null && !this.description.equals(description))
			setChanged();
		this.description = description;
	}
	public void setFrom(Source from) {
		setChanged();
		this.from = from;
	}
	public void setMemo(String memo) {
		if (this.memo != null && !this.memo.equals(memo))
			setChanged();
		this.memo = memo;
	}
	public void setNumber(String number) {
		if (this.number != null && !this.number.equals(number))
			setChanged();
		this.number = number;
	}
	public void setReconciledFrom(boolean reconciled) {
		if (this.reconciledFrom != reconciled)
			setChanged();
		this.reconciledFrom = reconciled;
		//If one of the To / From source is either
		// a budget category or a Prepaid account,
		// we set both of the flags to the same value.
		if (this.getTo() != null
				&& this.getFrom() != null
				&& (this.getTo() instanceof BudgetCategory
						|| this.getFrom() instanceof BudgetCategory
						|| ((Account) this.getTo()).getAccountType().getName().equals(TextFormatter.getTranslation(BuddiKeys.PREPAID_ACCOUNT))
						|| ((Account) this.getFrom()).getAccountType().getName().equals(TextFormatter.getTranslation(BuddiKeys.PREPAID_ACCOUNT)))){
			this.reconciledTo = reconciled;
		}
	}
	public void setReconciledTo(boolean reconciled) {
		if (this.reconciledTo != reconciled)
			setChanged();
		this.reconciledTo = reconciled;
		//If one of the To / From source is either
		// a budget category or a Prepaid account,
		// we set both of the flags to the same value.
		if (this.getTo() != null
				&& this.getFrom() != null
				&& (this.getTo() instanceof BudgetCategory
						|| this.getFrom() instanceof BudgetCategory
						|| ((Account) this.getTo()).getAccountType().getName().equals(TextFormatter.getTranslation(BuddiKeys.PREPAID_ACCOUNT))
						|| ((Account) this.getFrom()).getAccountType().getName().equals(TextFormatter.getTranslation(BuddiKeys.PREPAID_ACCOUNT)))){
			this.reconciledFrom = reconciled;
		}
	}
	public void setScheduled(boolean scheduled) {
		this.scheduled = scheduled;
	}
	public void setTo(Source to) {
		setChanged();
		this.to = to;
	}
	public boolean isDeleted() {
		return deleted;
	}
	
	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
}
