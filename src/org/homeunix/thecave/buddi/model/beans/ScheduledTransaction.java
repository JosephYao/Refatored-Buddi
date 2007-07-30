/*
 * Created on Jul 29, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.model.beans;

import java.util.Date;

public class ScheduledTransaction extends Transaction {
	
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
		this.setModifiedDate(new Date());
		this.endDate = endDate;
	}
	public String getFrequencyType() {
		return frequencyType;
	}
	public void setFrequencyType(String frequencyType) {
		this.setModifiedDate(new Date());
		this.frequencyType = frequencyType;
	}
	public Date getLastDayCreated() {
		return lastDayCreated;
	}
	public void setLastDayCreated(Date lastDayCreated) {
		this.setModifiedDate(new Date());
		this.lastDayCreated = lastDayCreated;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.setModifiedDate(new Date());
		this.message = message;
	}
	public Integer getScheduleDay() {
		return scheduleDay;
	}
	public void setScheduleDay(Integer scheduleDay) {
		this.setModifiedDate(new Date());
		this.scheduleDay = scheduleDay;
	}
	public Integer getScheduleMonth() {
		return scheduleMonth;
	}
	public void setScheduleMonth(Integer scheduleMonth) {
		this.setModifiedDate(new Date());
		this.scheduleMonth = scheduleMonth;
	}
	public String getScheduleName() {
		return scheduleName;
	}
	public void setScheduleName(String scheduleName) {
		this.setModifiedDate(new Date());
		this.scheduleName = scheduleName;
	}
	public Integer getScheduleWeek() {
		return scheduleWeek;
	}
	public void setScheduleWeek(Integer scheduleWeek) {
		this.setModifiedDate(new Date());
		this.scheduleWeek = scheduleWeek;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.setModifiedDate(new Date());
		this.startDate = startDate;
	}
	
	
	
	
}
