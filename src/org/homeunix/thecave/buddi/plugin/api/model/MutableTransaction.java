/*
 * Created on Aug 22, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.plugin.api.model;

import java.util.Date;

public interface MutableTransaction extends ImmutableTransaction {
	
	/**
	 * Sets the amount associated with this transaction
	 * @param amount
	 */
	public void setAmount(long amount);
	
	/**
	 * Marks this transaction as cleared
	 * @param cleared
	 */
	public void setCleared(boolean cleared);
	
	/**
	 * Sets the date associated with this account
	 * @param date
	 */
	public void setDate(Date date);
	
	/**
	 * Sets the description associated with this account
	 * @param description
	 */
	public void setDescription(String description);
	
	/**
	 * Sets the given source as the From field
	 * @param from
	 */
	public void setFrom(MutableSource from);
	
	/**
	 * Sets the given memo for this transaction
	 * @param memo
	 */
	public void setMemo(String memo);
	
	/**
	 * Sets the cheque number for this transaction
	 * @param number
	 */
	public void setNumber(String number);
	
	/**
	 * Marks this transactin as reconciled
	 * @param reconciled
	 */
	public void setReconciled(boolean reconciled);
	
	/**
	 * Sets the given source as the To field
	 * @param to
	 */
	public void setTo(MutableSource to);
}
