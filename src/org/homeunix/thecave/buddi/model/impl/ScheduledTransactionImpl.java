/*
 * Created on Jul 29, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.model.impl;

import java.util.Date;

import org.homeunix.thecave.buddi.model.ModelObject;
import org.homeunix.thecave.buddi.model.ScheduledTransaction;
import org.homeunix.thecave.buddi.model.Transaction;

public class ScheduledTransactionImpl extends TransactionImpl implements ScheduledTransaction {
	
	//Scheduling Information
	private Date startDate;
	private Date endDate;
	private Date lastDayCreated;

	//Frequency and timing
	private String frequencyType;
	private int scheduleDay;
	private int scheduleWeek;
	private int scheduleMonth;
	
	//User information
	private String scheduleName;
	private String message;
	
	
	
	
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public String getFrequencyType() {
		return frequencyType;
	}
	public void setFrequencyType(String frequencyType) {
		this.frequencyType = frequencyType;
	}
	public Date getLastDayCreated() {
		return lastDayCreated;
	}
	public void setLastDayCreated(Date lastDayCreated) {
		this.lastDayCreated = lastDayCreated;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public int getScheduleDay() {
		return scheduleDay;
	}
	public void setScheduleDay(int scheduleDay) {
		this.scheduleDay = scheduleDay;
	}
	public int getScheduleMonth() {
		return scheduleMonth;
	}
	public void setScheduleMonth(int scheduleMonth) {
		this.scheduleMonth = scheduleMonth;
	}
	public String getScheduleName() {
		return scheduleName;
	}
	public void setScheduleName(String scheduleName) {
		this.scheduleName = scheduleName;
	}
	public int getScheduleWeek() {
		return scheduleWeek;
	}
	public void setScheduleWeek(int scheduleWeek) {
		this.scheduleWeek = scheduleWeek;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	@Override
	public int compareTo(ModelObject arg0) {
		if (arg0 instanceof Transaction){
			ScheduledTransaction st = (ScheduledTransaction) arg0;

			//We want to sort schedued transactions by name, for the list
			return this.getScheduleName().compareTo(st.getScheduleName());
		}
		return super.compareTo(arg0);
	}
}
