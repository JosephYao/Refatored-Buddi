/*
 * Created on Jul 30, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.model.impl;

import java.util.Date;

import org.homeunix.thecave.buddi.model.ModelObject;
import org.homeunix.thecave.buddi.model.Source;
import org.homeunix.thecave.buddi.model.Transaction;
import org.homeunix.thecave.buddi.model.beans.AccountBean;
import org.homeunix.thecave.buddi.model.beans.BudgetCategoryBean;
import org.homeunix.thecave.buddi.model.beans.ScheduledTransactionBean;
import org.homeunix.thecave.buddi.model.beans.TransactionBean;
import org.homeunix.thecave.buddi.model.exception.InvalidValueException;
import org.homeunix.thecave.buddi.model.exception.ModelException;
import org.homeunix.thecave.moss.util.DateFunctions;

public class TransactionImpl extends ModelObjectImpl implements Transaction {	
	TransactionImpl(TransactionBean transaction) throws InvalidValueException {
		super(transaction);
	}


	@Override
	public int compareTo(ModelObject arg0) {
		if (arg0 instanceof Transaction){
			Transaction t = (Transaction) arg0;

			//We want to sort schedued transactions by name, for the list
			if (getTransactionBean() instanceof ScheduledTransactionBean && t.getTransactionBean() instanceof ScheduledTransactionBean)
				return ((ScheduledTransactionBean) getTransactionBean()).getScheduleName().compareTo(((ScheduledTransactionBean) t.getTransactionBean()).getScheduleName());

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
	public long getAmount(){
		return getTransactionBean().getAmount();
	}
	public long getBalanceFrom() {
		return getTransactionBean().volatileGetBalanceFrom();
	}
	public long getBalanceTo() {
		return getTransactionBean().volatileGetBalanceTo();
	}
	public Date getDate() {
		return getTransactionBean().getDate();
	}
	public String getDescription() {
		return getTransactionBean().getDescription();
	}
	public SourceImpl getFrom(){
		if (getTransactionBean().getFrom() == null)
			return null;
		try {
			if (getTransactionBean().getFrom() instanceof AccountBean)
				return new AccountImpl((AccountBean) getTransactionBean().getFrom());
			else
				return new BudgetCategoryImpl((BudgetCategoryBean) getTransactionBean().getFrom());
		}
		catch (ModelException me){
			return null;
		}
	}
	public String getMemo() {
		return getTransactionBean().getMemo();
	}
	public String getNumber() {
		return getTransactionBean().getNumber();
	}
	public Source getTo(){
		if (getTransactionBean().getTo() == null)
			return null;
		try {
			if (getTransactionBean().getTo() instanceof AccountBean)
				return new AccountImpl((AccountBean) getTransactionBean().getTo());
			else
				return new BudgetCategoryImpl((BudgetCategoryBean) getTransactionBean().getTo());
		}
		catch (ModelException me){
			return null;
		}
	}
	public TransactionBean getTransactionBean(){
		return (TransactionBean) getBean();
	}
	public boolean isCleared() {
		return getTransactionBean().isCleared();
	}
	public boolean isInflow(){
		if (getTransactionBean().getFrom() instanceof BudgetCategoryBean){
			return this.getAmount() >= 0;
		}
		if (getTransactionBean().getTo() instanceof BudgetCategoryBean){
			return this.getAmount() < 0;
		}

		//If neither sources are BudgetCategory, this is not an inflow.
		return false;
	}
	public boolean isReconciled() {
		return getTransactionBean().isReconciled();
	}
	public boolean isScheduled() {
		return getTransactionBean().isScheduled();
	}
	public void setAmount(long amount){
		getTransactionBean().setAmount(amount);
	}
	public void setBalanceFrom(long balanceFrom) {
		getTransactionBean().volatileSetBalanceFrom(balanceFrom);
	}
	public void setBalanceTo(long balanceTo) {
		getTransactionBean().volatileSetBalanceTo(balanceTo);
	}


	public void setCleared(boolean cleared) {
		getTransactionBean().setCleared(cleared);
	}
	public void setDate(Date date) {
		getTransactionBean().setDate(date);
	}
	public void setDescription(String description) {
		getTransactionBean().setDescription(description);
	}
	public void setFrom(Source from){
		getTransactionBean().setFrom(from.getSourceBean());
	}

	public void setMemo(String memo) {
		getTransactionBean().setMemo(memo);
	}

	public void setNumber(String number) {
		getTransactionBean().getNumber();
	}

	public void setReconciled(boolean reconciled) {
		getTransactionBean().setReconciled(reconciled);
	}
	public void setScheduled(boolean scheduled) {
		getTransactionBean().setScheduled(scheduled);
	}

	public void setTo(Source to){
		getTransactionBean().setTo(to.getSourceBean());
	}

	@Override
	public String toString() {
		return getDescription() + " (" + getDate() + "): 0x" + getBean().getUid() + ": Amount = " + getAmount() + ", From = " + getFrom() + ", To = " + getTo();
	}
}
