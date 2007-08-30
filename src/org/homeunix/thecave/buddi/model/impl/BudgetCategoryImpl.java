/*
 * Created on Jul 29, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.model.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.homeunix.thecave.buddi.model.BudgetCategory;
import org.homeunix.thecave.buddi.model.BudgetCategoryType;
import org.homeunix.thecave.buddi.model.exception.DataModelProblemException;
import org.homeunix.thecave.buddi.model.periods.BudgetPeriodMonthly;
import org.homeunix.thecave.moss.util.DateFunctions;

public class BudgetCategoryImpl extends SourceImpl implements BudgetCategory {
	private boolean income;
	private boolean expanded;
	private BudgetCategoryType periodType;
	private BudgetCategory parent;
	private Map<String, Long> amounts;
	
	
	public Map<String, Long> getAmounts() {
		if (amounts == null)
			amounts = new HashMap<String, Long>();
		return amounts;
	}
	public void setAmounts(Map<String, Long> amounts) {
		this.amounts = amounts;
	}
	/**
	 * Returns the budgeted amount associated with the given budget category, for 
	 * the date in which the given period date exists.
	 * @param periodDate
	 * @return
	 */
	public long getAmount(Date periodDate){
		Long l = getAmounts().get(getPeriodKey(periodDate));
		if (l == null)
			return 0;
		return l;
	}
	
	public long getAmount(Date startDate, Date endDate){
		if (startDate.after(endDate))
			throw new RuntimeException("Start date cannot be before End Date!");
		
//		DataModel model = getBudgetCategory().getModel(); 
		
		//If Start and End are in the same budget period
		if (getBudgetPeriodType().getStartOfBudgetPeriod(startDate).equals(
				getBudgetPeriodType().getStartOfBudgetPeriod(endDate))){
			long amount = getAmount(startDate);
			long daysInPeriod = getBudgetPeriodType().getDaysInPeriod(startDate); 
			long daysBetween = DateFunctions.getDaysBetween(startDate, endDate, true);
		
			return (long) (((double) amount / (double) daysInPeriod) * daysBetween);
		}
		 
		if (getBudgetPeriodType().getBudgetPeriodOffset(startDate, 1).equals(
				getBudgetPeriodType().getStartOfBudgetPeriod(endDate))
				|| getBudgetPeriodType().getBudgetPeriodOffset(startDate, 1).before(
						getBudgetPeriodType().getStartOfBudgetPeriod(endDate))){
			long amountStartPeriod = getAmount(startDate);
			long daysInStartPeriod = getBudgetPeriodType().getDaysInPeriod(startDate); 
			long daysAfterStartDateInStartPeriod = DateFunctions.getDaysBetween(startDate, getBudgetPeriodType().getEndOfBudgetPeriod(startDate), true);
			long totalStartPeriod = (long) (((double) amountStartPeriod / (double) daysInStartPeriod) * daysAfterStartDateInStartPeriod);
			
			long totalInMiddle = 0;
			for (String periodKey : getBudgetPeriods(
					getBudgetPeriodType().getBudgetPeriodOffset(startDate, 1),
					getBudgetPeriodType().getBudgetPeriodOffset(endDate, -1))) {
				totalInMiddle += getAmount(getPeriodDate(periodKey));
			}
			
			long amountEndPeriod = getAmount(endDate);
			long daysInEndPeriod = getBudgetPeriodType().getDaysInPeriod(startDate); 
			long daysBeforeEndDateInEndPeriod = DateFunctions.getDaysBetween(startDate, getBudgetPeriodType().getEndOfBudgetPeriod(startDate), true);
			long totalEndPeriod = (long) (((double) amountEndPeriod / (double) daysInEndPeriod) * daysBeforeEndDateInEndPeriod); 

			return totalStartPeriod + totalInMiddle + totalEndPeriod;
		}

		throw new RuntimeException("You should not be here.  We need to debug this bad boy...");
	}
	
	/**
	 * Returns a list of BudgetPeriods, covering the entire range of periods
	 * occupied by startDate to endDate.
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public List<String> getBudgetPeriods(Date startDate, Date endDate){
		List<String> budgetPeriodKeys = new LinkedList<String>();

		Date temp = getBudgetPeriodType().getStartOfBudgetPeriod(startDate);

		while (temp.before(getBudgetPeriodType().getEndOfBudgetPeriod(endDate))){
			budgetPeriodKeys.add(getPeriodKey(temp));
			temp = getBudgetPeriodType().getBudgetPeriodOffset(temp, 1);
		}

		return budgetPeriodKeys;
	}
	
	/**
	 * Sets the budgeted amount for the given time period.
	 * @param periodDate
	 * @param amount
	 */
	public void setAmount(Date periodDate, long amount){
		getAmounts().put(getPeriodKey(periodDate), amount);
	}
	public BudgetCategoryType getPeriodType() {
		return periodType;
	}
	public void setPeriodType(BudgetCategoryType periodType) {
		this.periodType = periodType;
	}
	public boolean isIncome() {
		return income;
	}
	public void setIncome(boolean income) {
		this.income = income;
	}
	public BudgetCategory getParent() {
		return parent;
	}
	public void setParent(BudgetCategory parent) {
		this.parent = parent;
	}
	public boolean isExpanded() {
		return expanded;
	}
	public void setExpanded(boolean expanded) {
		this.expanded = expanded;
	}
	/**
	 * Returns the key which is associated with the date contained within the
	 * current budget period.  The string is constructed as follows:
	 * 
	 * <code>String periodKey = getPeriodType() + ":" + getStartOfBudgetPeriod(periodDate).getTime();</code>
	 * 
	 * @param periodDate
	 * @return
	 */
	private String getPeriodKey(Date periodDate){
		return getBudgetPeriodType().getName() + ":" + getBudgetPeriodType().getStartOfBudgetPeriod(periodDate).getTime();
	}
	/**
	 * Parses a periodKey to get the date 
	 * @param periodKey
	 * @return
	 */
	private Date getPeriodDate(String periodKey){
		String[] splitKey = periodKey.split(":");
		if (splitKey.length > 1){
			long l = Long.parseLong(splitKey[1]);
			return getBudgetPeriodType().getStartOfBudgetPeriod(new Date(l));
		}

		throw new DataModelProblemException("Cannot parse date from key " + periodKey);
	}
	public String getFullName(){
		if (this.getParent() != null)
			return this.getParent().getFullName() + " " + this.getName();
		
		return this.getName();
	}
	/**
	 * Returns the Budget Period type.  One of the values in Enum BudgePeriodKeys.
	 * @return
	 */
	public BudgetCategoryType getBudgetPeriodType() {
		if (getPeriodType() == null)
			setPeriodType(new BudgetPeriodMonthly());
		return getPeriodType();
	}
}
