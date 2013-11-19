package org.homeunix.thecave.buddi.model.impl;

import java.util.Date;

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
}
