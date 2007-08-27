/*
 * Created on Aug 26, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.plugin.api.model;

import java.util.Date;

public interface MutableScheduledTransaction extends MutableTransaction, ImmutableScheduledTransaction {
	public void setEndDate(Date endDate);

	public void setFrequencyType(String frequencyType);

	public void setLastDayCreated(Date lastDayCreated);

	public void setMessage(String message);
	
	public void setScheduleDay(int scheduleDay);
	
	public void setScheduleMonth(int scheduleMonth);

	public void setScheduleName(String scheduleName);

	public void setScheduleWeek(int scheduleWeek);
	
	public void setStartDate(Date startDate);
}
