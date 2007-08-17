/*
 * Created on Aug 12, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.plugin.api.model.immutable;

import java.util.Date;

import org.homeunix.thecave.buddi.model.Account;
import org.homeunix.thecave.buddi.model.BudgetCategory;
import org.homeunix.thecave.buddi.model.Transaction;

public class ImmutableTransaction extends ImmutableModelObject {
	
	public ImmutableTransaction(Transaction transaction) {
		super(transaction);
	}
	
	public Transaction getTransaction(){
		return (Transaction) getRaw();
	}
	
	public Boolean isCleared() {
		return getTransaction().isCleared();
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
	public Boolean isReconciled() {
		return getTransaction().isReconciled();
	}
	public Boolean getScheduled() {
		return getTransaction().getScheduled();
	}
	public ImmutableSource getFrom(){
		if (getTransaction().getFrom() instanceof Account)
			return new ImmutableAccount((Account) getTransaction().getFrom());
		if (getTransaction().getFrom() instanceof BudgetCategory)
			return new ImmutableBudgetCategory((BudgetCategory) getTransaction().getFrom());
		return null;
	}
	public ImmutableSource getTo(){
		if (getTransaction().getTo() instanceof Account)
			return new ImmutableAccount((Account) getTransaction().getTo());
		if (getTransaction().getTo() instanceof BudgetCategory)
			return new ImmutableBudgetCategory((BudgetCategory) getTransaction().getTo());
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
		if (obj instanceof ImmutableTransaction);
		
		return false;
	}
}
