/*
 * Created on Jul 30, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.model;

import java.util.Date;
import java.util.List;

import org.homeunix.thecave.buddi.plugin.api.exception.InvalidValueException;

public interface Transaction extends ModelObject {	
	
	public long getAmount();
	
	public long getBalanceFrom();
	
	public long getBalanceTo();
	
	public Date getDate();
	
	public String getDescription();
	
	public Source getFrom();
	
	public String getMemo();
	
	public String getNumber();
	
	public List<TransactionSplit> getSplits();
	
	public Source getTo();
	
	public boolean isClearedFrom();
	
	public boolean isClearedTo();
	
	public boolean isInflow();
	
	public boolean isReconciledFrom();
	
	public boolean isReconciledTo();
	
	public boolean isScheduled();
	
	public boolean isDeleted();
	
	public void setAmount(long amount);
	
	void setBalanceFrom(long balanceFrom) throws InvalidValueException;
	
	void setBalanceTo(long balanceTo) throws InvalidValueException;
	
	public void setClearedFrom(boolean cleared) throws InvalidValueException;
	
	public void setClearedTo(boolean cleared) throws InvalidValueException;
	
	public void setDate(Date date) throws InvalidValueException;
	
	public void setDescription(String description) throws InvalidValueException;
	
	public void setFrom(Source from) throws InvalidValueException;
	
	public void setMemo(String memo) throws InvalidValueException;

	public void setNumber(String number) throws InvalidValueException;

	public void setReconciledFrom(boolean reconciled) throws InvalidValueException;
	
	public void setReconciledTo(boolean reconciled) throws InvalidValueException;
	
	public void setScheduled(boolean scheduled) throws InvalidValueException;
	
	public void setSplits(List<TransactionSplit> splits) throws InvalidValueException;
	
	public void setTo(Source to) throws InvalidValueException;
	
	public void setDeleted(boolean deleted) throws InvalidValueException;
}
