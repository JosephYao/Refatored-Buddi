package org.homeunix.thecave.buddi.model.impl;

import java.util.Date;

import ca.digitalcave.moss.common.DateUtil;

public class Period {
	
	private final Date start;
	private final Date end;

	public Period(Date start, Date end) {
		this.start = start;
		this.end = end;
	}

	public Date getStartDate() {
		return start;
	}

	public Date getEndDate() {
		return end;
	}
	
	public boolean equals(Object obj) {
		Period another = (Period) obj;
		return another.start.equals(this.start) &&
			   another.end.equals(this.end);
	}
	
	public long getDayCount() {
		return DateUtil.getDaysBetween(start, end, true);
	}
	
	public long getOverlappingDayCount(Period anotherPeriod) {
		Date laterStartDate = (start.after(anotherPeriod.start)) ? start : anotherPeriod.start;
		Date earlierEndDate = (end.before(anotherPeriod.end)) ? end : anotherPeriod.end;
		
		if (laterStartDate.after(earlierEndDate))
			return 0;
		
		return new Period(laterStartDate, earlierEndDate).getDayCount();
	}
}
