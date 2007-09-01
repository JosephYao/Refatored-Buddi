/*
 * Created on Jul 30, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.model;

import java.util.Date;

import org.homeunix.thecave.buddi.plugin.api.exception.InvalidValueException;

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
	
	public void setLastDayCreated(Date lastDayCreated) throws InvalidValueException;
	
	public void setMessage(String message) throws InvalidValueException;
	
	public void setScheduleDay(int scheduleDay) throws InvalidValueException;
	
	public void setScheduleMonth(int scheduleMonth) throws InvalidValueException;
	
	public void setScheduleName(String scheduleName) throws InvalidValueException;
	
	public void setScheduleWeek(int scheduleWeek) throws InvalidValueException;
	
	public void setStartDate(Date startDate) throws InvalidValueException;
}
