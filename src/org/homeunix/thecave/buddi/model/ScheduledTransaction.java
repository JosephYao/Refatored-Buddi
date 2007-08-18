/*
 * Created on Jul 30, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.model;

import java.util.Date;

import org.homeunix.thecave.buddi.model.beans.ScheduledTransactionBean;

public class ScheduledTransaction extends Transaction {
	
	ScheduledTransaction(DataModel model, ScheduledTransactionBean scheduledTransaction) {
		super(model, scheduledTransaction);
	}
	
	public ScheduledTransaction(DataModel model, String scheduleName) {
		this(model, new ScheduledTransactionBean());

		getScheduledTranasactionBean().setScheduleName(scheduleName);
	}
	
	public Date getEndDate() {
		return getScheduledTransaction().getEndDate();
	}
	public void setEndDate(Date endDate) {
		getScheduledTransaction().setEndDate(endDate);
		getModel().setChanged();
	}
	public String getFrequencyType() {
		return getScheduledTransaction().getFrequencyType();
	}
	public void setFrequencyType(String frequencyType) {
		getScheduledTransaction().setFrequencyType(frequencyType);
		getModel().setChanged();
	}
	public Date getLastDayCreated() {
		return getScheduledTransaction().getLastDayCreated();
	}
	public void setLastDayCreated(Date lastDayCreated) {
		getScheduledTransaction().setLastDayCreated(lastDayCreated);
		getModel().setChanged();
	}
	public String getMessage() {
		return getScheduledTransaction().getMessage();
	}
	public void setMessage(String message) {
		getScheduledTransaction().setMessage(message);
		getModel().setChanged();
	}
	public Integer getScheduleDay() {
		return getScheduledTransaction().getScheduleDay();
	}
	public void setScheduleDay(Integer scheduleDay) {
		getScheduledTransaction().setScheduleDay(scheduleDay);
		getModel().setChanged();
	}
	public Integer getScheduleMonth() {
		return getScheduledTransaction().getScheduleMonth();
	}
	public void setScheduleMonth(Integer scheduleMonth) {
		getScheduledTransaction().setScheduleMonth(scheduleMonth);
		getModel().setChanged();
	}
	public String getScheduleName() {
		return getScheduledTransaction().getScheduleName();
	}
	public void setScheduleName(String scheduleName) {
		getScheduledTransaction().setScheduleName(scheduleName);
		getModel().setChanged();
	}
	public Integer getScheduleWeek() {
		return getScheduledTransaction().getScheduleWeek();
	}
	public void setScheduleWeek(Integer scheduleWeek) {
		getScheduledTransaction().setScheduleWeek(scheduleWeek);
		getModel().setChanged();
	}
	public Date getStartDate() {
		return getScheduledTransaction().getStartDate();
	}
	public void setStartDate(Date startDate) {
		getScheduledTransaction().setStartDate(startDate);
		getModel().setChanged();
	}
	ScheduledTransactionBean getScheduledTranasactionBean(){
		return getScheduledTransaction();
	}
	private ScheduledTransactionBean getScheduledTransaction(){
		return (ScheduledTransactionBean) getTransactionBean();
	}
}
