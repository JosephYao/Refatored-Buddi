package org.homeunix.thecave.buddi.model.impl;

import java.util.Date;

import ca.digitalcave.moss.common.DateUtil;

public class Period {
	
	private final Date startDate;
	private final Date endDate;

	public Period(Date startDate, Date endDate) {
		this.startDate = startDate;
		this.endDate = endDate;
	}

	public Date getStartDate() {
		return startDate;
	}

	public Date getEndDate() {
		return endDate;
	}
	
	public boolean equals(Object object) {
		Period another = (Period) object;
		return another.startDate.equals(this.startDate) &&
			   another.endDate.equals(this.endDate);
	}

	public long getDayCount() {
		return DateUtil.getDaysBetween(startDate, endDate, true);
	}

	public long getOverlappingDayCount(Period another) {
		Date overlappingStartDate = (startDate.after(another.startDate)) ? startDate : another.startDate;
		Date overlappingEndDate = (endDate.before(another.endDate)) ? endDate : another.endDate;
		
		return new Period(overlappingStartDate, overlappingEndDate).getDayCount();
	}

}
