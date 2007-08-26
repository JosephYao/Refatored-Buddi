/*
 * Created on Aug 12, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.plugin.api.model;

import java.util.Date;

import org.homeunix.thecave.buddi.model.Transaction;

public interface ImmutableTransaction extends ImmutableModelObject {
	
	public Transaction getTransaction();
	
	public boolean isCleared();
	
	public Date getDate();
	
	public String getDescription();
	
	public String getMemo();
	
	public String getNumber();
	
	public boolean isReconciled();
	
	public boolean isScheduled();
	
	public ImmutableSource getFrom();
	
	public ImmutableSource getTo();
	
	public long getBalanceFrom();
	
	public long getBalanceTo();
	
	public boolean isInflow();
	
	public long getAmount();
}
