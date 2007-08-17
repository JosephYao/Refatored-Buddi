/*
 * Created on Jul 29, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.model.beans;

import java.util.Date;

public class ScheduledTransactionBean extends TransactionBean {
	
	//Scheduling Information
	private Date startDate;
	private Date endDate;
	private Date lastDayCreated;

	//Frequency and timing
	private String frequencyType;
	private Integer scheduleDay;
	private Integer scheduleWeek;
	private Integer scheduleMonth;
	
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
	public Integer getScheduleDay() {
		return scheduleDay;
	}
	public void setScheduleDay(Integer scheduleDay) {
		this.scheduleDay = scheduleDay;
	}
	public Integer getScheduleMonth() {
		return scheduleMonth;
	}
	public void setScheduleMonth(Integer scheduleMonth) {
		this.scheduleMonth = scheduleMonth;
	}
	public String getScheduleName() {
		return scheduleName;
	}
	public void setScheduleName(String scheduleName) {
		this.scheduleName = scheduleName;
	}
	public Integer getScheduleWeek() {
		return scheduleWeek;
	}
	public void setScheduleWeek(Integer scheduleWeek) {
		this.scheduleWeek = scheduleWeek;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}	
}
