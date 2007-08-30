/*
 * Created on Jul 30, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.model;

import java.util.Date;

import org.homeunix.thecave.buddi.model.exception.InvalidValueException;

/**
 * @author wyatt
 *
 */
public interface ScheduledTransaction extends Transaction {

	public Date getEndDate();
	
	public String getFrequencyType();
	
	public Date getLastDayCreated();
	
	public String getMessage();
	
	public int getScheduleDay();
	
	public int getScheduleMonth();
	
	public String getScheduleName();
	
	public int getScheduleWeek();
	
	public Date getStartDate();
	
	public void setEndDate(Date endDate) throws InvalidValueException;
	
	public void setFrequencyType(String frequencyType) throws InvalidValueException;
	
	public void setLastDayCreated(Date lastDayCreated);
	
	public void setMessage(String message) throws InvalidValueException;
	
	public void setScheduleDay(int scheduleDay);
	
	public void setScheduleMonth(int scheduleMonth);
	
	public void setScheduleName(String scheduleName);
	
	public void setScheduleWeek(int scheduleWeek);
	
	public void setStartDate(Date startDate);
}
