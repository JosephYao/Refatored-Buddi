/*
 * Created on Aug 26, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.plugin.api.model;

import java.util.Date;

import org.homeunix.thecave.buddi.model.api.exception.InvalidValueException;

public interface MutableScheduledTransaction extends MutableTransaction, ImmutableScheduledTransaction {
	public void setEndDate(Date endDate) throws InvalidValueException;

	public void setFrequencyType(String frequencyType) throws InvalidValueException;

	public void setLastDayCreated(Date lastDayCreated) throws InvalidValueException;

	public void setMessage(String message) throws InvalidValueException;
	
	public void setScheduleDay(int scheduleDay) throws InvalidValueException;
	
	public void setScheduleMonth(int scheduleMonth) throws InvalidValueException;

	public void setScheduleName(String scheduleName) throws InvalidValueException;

	public void setScheduleWeek(int scheduleWeek) throws InvalidValueException;
	
	public void setStartDate(Date startDate) throws InvalidValueException;
}
