/*
 * Created on Aug 23, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.plugin.api.model.impl;

import java.util.Date;

import org.homeunix.thecave.buddi.model.Account;
import org.homeunix.thecave.buddi.model.BudgetCategory;
import org.homeunix.thecave.buddi.model.Transaction;
import org.homeunix.thecave.buddi.plugin.api.exception.InvalidValueException;
import org.homeunix.thecave.buddi.plugin.api.model.ImmutableTransaction;
import org.homeunix.thecave.buddi.plugin.api.model.MutableSource;
import org.homeunix.thecave.buddi.plugin.api.model.MutableTransaction;

public class MutableTransactionImpl extends MutableModelObjectImpl implements MutableTransaction {

	public MutableTransactionImpl(Transaction transaction) {
		super(transaction);
	}
	
	public Transaction getTransaction(){
		return (Transaction) getRaw();
	}
	
	public boolean isClearedFrom() {
		return getTransaction().isClearedFrom();
	}
	public boolean isClearedTo() {
		return getTransaction().isClearedTo();
	}
	public boolean isReconciledFrom() {
		return getTransaction().isReconciledFrom();
	}
	public boolean isReconciledTo() {
		return getTransaction().isReconciledTo();
	}

	public Date getDate() {
		return getTransaction().getDate();
	}
	public String getDescription() {
		return getTransaction().getDescription();
	}
	public String getMemo() {
		return getTransaction().getMemo();
	}
	public String getNumber() {
		return getTransaction().getNumber();
	}
	public boolean isScheduled() {
		return getTransaction().isScheduled();
	}
	public MutableSourceImpl getFrom(){
		if (getTransaction().getFrom() instanceof Account)
			return new MutableAccountImpl((Account) getTransaction().getFrom());
		if (getTransaction().getFrom() instanceof BudgetCategory)
			return new MutableBudgetCategoryImpl((BudgetCategory) getTransaction().getFrom());
		return null;
	}
	public MutableSourceImpl getTo(){
		if (getTransaction().getTo() instanceof Account)
			return new MutableAccountImpl((Account) getTransaction().getTo());
		if (getTransaction().getTo() instanceof BudgetCategory)
			return new MutableBudgetCategoryImpl((BudgetCategory) getTransaction().getTo());
		return null;
	}
	public long getBalanceFrom() {
		return getTransaction().getBalanceFrom();
	}
	public long getBalanceTo() {
		return getTransaction().getBalanceTo();
	}
	public boolean isInflow(){
		return getTransaction().isInflow();
	}
	public long getAmount(){
		return getTransaction().getAmount();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof ImmutableTransaction)
			return getUid().equals(((ImmutableTransaction) obj).getUid());
		return false;
	}

	public void setAmount(long amount) {
		getTransaction().setAmount(amount);
	}

	public void setClearedFrom(boolean cleared) throws InvalidValueException{
		getTransaction().setClearedFrom(cleared);
	}

	public void setClearedTo(boolean cleared) throws InvalidValueException{
		getTransaction().setClearedTo(cleared);
	}

	public void setDate(Date date) throws InvalidValueException{
		getTransaction().setDate(date);
	}

	public void setDescription(String description) throws InvalidValueException{
		getTransaction().setDescription(description);
	}

	public void setFrom(MutableSource from) throws InvalidValueException{
		if (from == null)
			getTransaction().setFrom(null);
		else
			getTransaction().setFrom(from.getSource());
	}

	public void setMemo(String memo) throws InvalidValueException{
		getTransaction().setMemo(memo);
	}

	public void setNumber(String number) throws InvalidValueException{
		getTransaction().setNumber(number);
	}

	public void setReconciledFrom(boolean reconciled) throws InvalidValueException{
		getTransaction().setReconciledFrom(reconciled);
	}

	public void setReconciledTo(boolean reconciled) throws InvalidValueException{
		getTransaction().setReconciledTo(reconciled);
	}

	public void setTo(MutableSource to) throws InvalidValueException{
		if (to == null)
			getTransaction().setTo(null);
		else
			getTransaction().setTo(to.getSource());
	}
	
	public void setScheduled(boolean scheduled) throws InvalidValueException{
		getTransaction().setScheduled(scheduled);
	}
	
	public boolean isDeleted() {
		return getTransaction().isDeleted();
	}
	
	public void setDeleted(boolean deleted) throws InvalidValueException {
		getTransaction().setDeleted(deleted);
	}
}
