/*
 * Created on Aug 22, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.plugin.api.model;

import java.util.Date;

public interface MutableTransaction extends ImmutableTransaction {
	public void setAmount(long amount);
	public void setCleared(boolean cleared);
	public void setDate(Date date);
	public void setDescription(String description);
	public void setFrom(MutableSource from);
	public void setMemo(String memo);
	public void setNumber(String number);
	public void setReconciled(boolean reconciled);
	public void setTo(MutableSource to);
}
