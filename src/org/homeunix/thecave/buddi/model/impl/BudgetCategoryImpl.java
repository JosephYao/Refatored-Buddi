/*
 * Created on Jul 30, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.model.impl;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.homeunix.thecave.buddi.model.BudgetCategory;
import org.homeunix.thecave.buddi.model.BudgetCategoryType;
import org.homeunix.thecave.buddi.model.ModelObject;
import org.homeunix.thecave.buddi.model.beans.BudgetCategoryBean;
import org.homeunix.thecave.buddi.model.exception.DataModelProblemException;
import org.homeunix.thecave.buddi.model.exception.InvalidValueException;
import org.homeunix.thecave.buddi.model.exception.ModelException;
import org.homeunix.thecave.buddi.model.periods.BudgetPeriodMonthly;
import org.homeunix.thecave.moss.util.DateFunctions;

public class BudgetCategoryImpl extends SourceImpl implements BudgetCategory {	
	BudgetCategoryImpl(BudgetCategoryBean budgetCategory) throws ModelException {
		super(budgetCategory);
	}
	
	public boolean isIncome() {
		return getBudgetCategory().isIncome();
	}
	public void setIncome(boolean income) {
		getBudgetCategory().setIncome(income);
	}
	public BudgetCategory getParent() {
		if (getBudgetCategory().getParent() == null)
			return null;
		try {
			return new BudgetCategoryImpl(getBudgetCategory().getParent());
		}
		catch (ModelException me){
			return null;
		}
	}
	public void setParent(BudgetCategory parent) throws InvalidValueException{
		if (parent == null){
			getBudgetCategory().setParent(null);
		}
		else {
			if (getBudgetCategory().equals(parent.getBudgetCategoryBean()))
				throw new InvalidValueException("Cannot set a Budget Category's parent to itself");
			getBudgetCategory().setParent(parent.getBudgetCategoryBean());
		}
	}
	public boolean isExpanded() {
		return getBudgetCategoryBean().isExpanded();
	}
	public void setExpanded(boolean expanded) {
		getBudgetCategoryBean().setExpanded(expanded);
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
		if (getBudgetCategory().getPeriodType() == null)
			setPeriodType(new BudgetPeriodMonthly());
		return getBudgetCategory().getPeriodType();
	}
	
	/**
	 * Sets the Budget Period type. 
	 * @param periodType
	 */
	public void setPeriodType(BudgetCategoryType periodType) {
		getBudgetCategory().setPeriodType(periodType);
	}
	
	/**
	 * Returns the budgeted amount associated with the given budget category, for 
	 * the date in which the given period date exists.
	 * @param periodDate
	 * @return
	 */
	public long getAmount(Date periodDate){
		Long l = getBudgetCategory().getAmounts().get(getPeriodKey(periodDate));
		if (l == null)
			return 0;
		return l;
	}
	
	/**
	 * Sets the budgeted amount for the given time period.
	 * @param periodDate
	 * @param amount
	 */
	public void setAmount(Date periodDate, long amount){
		getBudgetCategory().getAmounts().put(getPeriodKey(periodDate), amount);
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
	
//	/**
//	 * Parses a periodKey to get the period type
//	 * @param periodKey
//	 * @return
//	 */
//	private BudgetPeriodType getPeriodType(String periodKey){
//		String[] splitKey = periodKey.split(":");
//		if (splitKey.length > 0){
//			return BudgetPeriodType.valueOf(splitKey[0]);
//		}
//
//		throw new DataModelProblemException("Cannot parse BudgetPeriodType from key " + periodKey, this.getModel());		
//	}
	
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

	
	@Override
	public int compareTo(ModelObject arg0) {
		if (arg0 instanceof BudgetCategoryImpl){
			BudgetCategoryImpl c = (BudgetCategoryImpl) arg0;
			if (this.isIncome() != c.isIncome())
				return -1 * new Boolean(this.isIncome()).compareTo(new Boolean(c.isIncome()));
		}
		return super.compareTo(arg0);
	}
	
	public BudgetCategoryBean getBudgetCategoryBean(){
		return getBudgetCategory();
	}
		
	private BudgetCategoryBean getBudgetCategory(){
		return (BudgetCategoryBean) getSourceBean();
	}
}
