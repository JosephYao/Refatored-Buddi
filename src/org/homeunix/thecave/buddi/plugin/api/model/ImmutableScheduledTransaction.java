/*
 * Created on Aug 26, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.plugin.api.model;

import java.util.Date;

public interface ImmutableScheduledTransaction extends ImmutableTransaction {
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
