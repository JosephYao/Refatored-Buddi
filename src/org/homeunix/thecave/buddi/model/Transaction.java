/*
 * Created on Jul 30, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.model;

import java.util.Date;

import org.homeunix.thecave.buddi.model.exception.InvalidValueException;

public interface Transaction extends ModelObject {	
	
	public long getAmount();
	
	public long getBalanceFrom();
	
	public long getBalanceTo();
	
	public Date getDate();
	
	public String getDescription();
	
	public Source getFrom();
	
	public String getMemo();
	
	public String getNumber();
	
	public Source getTo();
	
	public boolean isCleared();
	
	public boolean isInflow();
	
	public boolean isReconciled();
	
	public boolean isScheduled();
	
	public void setAmount(long amount);
	
	void setBalanceFrom(long balanceFrom);
	
	void setBalanceTo(long balanceTo);
	
	public void setCleared(boolean cleared);
	
	public void setDate(Date date) throws InvalidValueException;
	
	public void setDescription(String description) throws InvalidValueException;
	
	public void setFrom(Source from) throws InvalidValueException;
	
	public void setMemo(String memo) throws InvalidValueException;

	public void setNumber(String number);

	public void setReconciled(boolean reconciled);
	
	public void setScheduled(boolean scheduled);
	
	public void setTo(Source to) throws InvalidValueException;
}
