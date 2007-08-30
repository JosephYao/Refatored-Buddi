/*
 * Created on Jul 30, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.model;

import java.util.Date;

import org.homeunix.thecave.buddi.model.exception.InvalidValueException;

public interface BudgetCategory extends Source {	
	
	/**
	 * Returns the budgeted amount associated with the given budget category, for 
	 * the date in which the given period date exists.
	 * @param periodDate
	 * @return
	 */
	public long getAmount(Date periodDate);
	
	public long getAmount(Date startDate, Date endDate);
	
	/**
	 * Returns a list of BudgetPeriods, covering the entire range of periods
	 * occupied by startDate to endDate.
	 * @param startDate
	 * @param endDate
	 * @return
	 */
//	public List<String> getBudgetPeriods(Date startDate, Date endDate);
	
	/**
	 * Returns the Budget Period type.  One of the values in Enum BudgePeriodKeys.
	 * @return
	 */
	public BudgetCategoryType getBudgetPeriodType();
	
	public BudgetCategory getParent();
		
	public boolean isExpanded();
	
	public boolean isIncome();
	
	/**
	 * Sets the budgeted amount for the given time period.
	 * @param periodDate
	 * @param amount
	 */
	public void setAmount(Date periodDate, long amount) throws InvalidValueException;
	
	public void setExpanded(boolean expanded);
	
	public void setIncome(boolean income);
	
	public void setParent(BudgetCategory parent) throws InvalidValueException;
	
	/**
	 * Sets the Budget Period type. 
	 * @param periodType
	 */
	public void setPeriodType(BudgetCategoryType periodType) throws InvalidValueException;
}
