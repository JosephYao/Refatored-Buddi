/*
 * Created on Aug 26, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.plugin.api.model.impl;

import java.util.Date;

import org.homeunix.thecave.buddi.model.ScheduledTransaction;
import org.homeunix.thecave.buddi.plugin.api.model.MutableScheduledTransaction;

public class MutableScheduledTransactionImpl extends MutableTransactionImpl implements MutableScheduledTransaction {

	public MutableScheduledTransactionImpl(ScheduledTransaction scheduledTransaction) {
		super(scheduledTransaction);
	}
	
	public ScheduledTransaction getScheduledTransaction(){
		return (ScheduledTransaction) getRaw();
	}
	
	public Date getEndDate() {
		return getScheduledTransaction().getEndDate();
	}

	public String getFrequencyType() {
		return getScheduledTransaction().getFrequencyType();
	}

	public Date getLastDayCreated() {
		return getScheduledTransaction().getLastDayCreated();
	}

	public String getMessage() {
		return getScheduledTransaction().getMessage();
	}

	public int getScheduleDay() {
		return getScheduledTransaction().getScheduleDay();
	}

	public int getScheduleMonth() {
		return getScheduledTransaction().getScheduleMonth();
	}

	public String getScheduleName() {
		return getScheduledTransaction().getScheduleName();
	}

	public int getScheduleWeek() {
		return getScheduledTransaction().getScheduleWeek();
	}

	public Date getStartDate() {
		return getScheduledTransaction().getStartDate();
	}

	public void setEndDate(Date endDate) {
		getScheduledTransaction().setEndDate(endDate);
	}

	public void setFrequencyType(String frequencyType) {
		getScheduledTransaction().setFrequencyType(frequencyType);		
	}

	public void setLastDayCreated(Date lastDayCreated) {
		getScheduledTransaction().setLastDayCreated(lastDayCreated);		
	}

	public void setMessage(String message) {
		getScheduledTransaction().setMessage(message);		
	}

	public void setScheduleDay(int scheduleDay) {
		getScheduledTransaction().setScheduleDay(scheduleDay);		
	}

	public void setScheduleMonth(int scheduleMonth) {
		getScheduledTransaction().setScheduleMonth(scheduleMonth);		
	}

	public void setScheduleName(String scheduleName) {
		getScheduledTransaction().setScheduleName(scheduleName);		
	}

	public void setScheduleWeek(int scheduleWeek) {
		getScheduledTransaction().setScheduleWeek(scheduleWeek);
	}

	public void setStartDate(Date startDate) {
		getScheduledTransaction().setStartDate(startDate);		
	}
}
