/*
 * Created on Jul 30, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.model.impl;

import java.util.Date;

import org.homeunix.thecave.buddi.model.ScheduledTransaction;
import org.homeunix.thecave.buddi.model.beans.ScheduledTransactionBean;
import org.homeunix.thecave.buddi.model.exception.InvalidValueException;

/**
 * @author wyatt
 *
 */
public class ScheduledTransactionImpl extends TransactionImpl implements ScheduledTransaction {
	
	ScheduledTransactionImpl(ScheduledTransactionBean scheduledTransaction) throws InvalidValueException {
		super(scheduledTransaction);
	}
	
	public Date getEndDate() {
		return getScheduledTransactionBean().getEndDate();
	}
	public void setEndDate(Date endDate) {
		getScheduledTransactionBean().setEndDate(endDate);
	}
	public String getFrequencyType() {
		return getScheduledTransactionBean().getFrequencyType();
	}
	public void setFrequencyType(String frequencyType) {
		getScheduledTransactionBean().setFrequencyType(frequencyType);
	}
	public Date getLastDayCreated() {
		return getScheduledTransactionBean().getLastDayCreated();
	}
	public void setLastDayCreated(Date lastDayCreated) {
		getScheduledTransactionBean().setLastDayCreated(lastDayCreated);
	}
	public String getMessage() {
		return getScheduledTransactionBean().getMessage();
	}
	public void setMessage(String message) {
		getScheduledTransactionBean().setMessage(message);
	}
	public int getScheduleDay() {
		return getScheduledTransactionBean().getScheduleDay();
	}
	public void setScheduleDay(int scheduleDay) {
		getScheduledTransactionBean().setScheduleDay(scheduleDay);
	}
	public int getScheduleMonth() {
		return getScheduledTransactionBean().getScheduleMonth();
	}
	public void setScheduleMonth(int scheduleMonth) {
		getScheduledTransactionBean().setScheduleMonth(scheduleMonth);
	}
	public String getScheduleName() {
		return getScheduledTransactionBean().getScheduleName();
	}
	public void setScheduleName(String scheduleName) {
		getScheduledTransactionBean().setScheduleName(scheduleName);
	}
	public int getScheduleWeek() {
		return getScheduledTransactionBean().getScheduleWeek();
	}
	public void setScheduleWeek(int scheduleWeek) {
		getScheduledTransactionBean().setScheduleWeek(scheduleWeek);
	}
	public Date getStartDate() {
		return getScheduledTransactionBean().getStartDate();
	}
	public void setStartDate(Date startDate) {
		getScheduledTransactionBean().setStartDate(startDate);
	}
	public ScheduledTransactionBean getScheduledTransactionBean(){
		return (ScheduledTransactionBean) getBean();
	}
}
