/*
 * Created on Aug 26, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.plugin.api.model;

import java.util.Date;

import org.homeunix.thecave.buddi.model.ScheduledTransaction;

public interface ImmutableScheduledTransaction extends ImmutableTransaction {
	
	/**
	 * Returns the wrapped object from the underlying data model.  By 
	 * accessing this method, you bypass all protection which the Buddi API
	 * gives you; it is not recommended to use this method unless you understand
	 * the risks associated with it. 
	 * @return
	 */
	public ScheduledTransaction getScheduledTransaction();
	
	public Date getEndDate();
	
	public String getFrequencyType();

	public Date getLastDayCreated();
	
	public String getMessage();

	public int getScheduleDay();

	public int getScheduleMonth();

	public String getScheduleName();

	public int getScheduleWeek();
	
	public Date getStartDate();
}
