/*
 * Created on Jul 30, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.model;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.homeunix.thecave.buddi.i18n.keys.BudgetPeriodType;
import org.homeunix.thecave.buddi.model.beans.BudgetCategoryBean;
import org.homeunix.thecave.buddi.model.exception.DataModelProblemException;
import org.homeunix.thecave.buddi.util.BudgetPeriodUtil;
import org.homeunix.thecave.moss.util.DateFunctions;

public class BudgetCategory extends Source {	
	BudgetCategory(DataModel model, BudgetCategoryBean budgetCategory) {
		super(model, budgetCategory);
	}
	
	public BudgetCategory(DataModel model, String name, boolean isIncome) {
		this(model, new BudgetCategoryBean());
		
		this.setName(name);
		this.setIncome(isIncome);
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
		return new BudgetCategory(getModel(), getBudgetCategory().getParent());
	}
	public void setParent(BudgetCategory parent) {
		if (parent == null){
			getBudgetCategory().setParent(null);
		}
		else {
			if (getBudgetCategory().equals(parent.getBudgetCategoryBean()))
				throw new DataModelProblemException("Cannot set a Budget Category's parent to itself", getModel());
			getBudgetCategory().setParent(parent.getBudgetCategoryBean());
		}
		getModel().setChanged();
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
	public BudgetPeriodType getBudgetPeriodType() {
		if (getBudgetCategory().getPeriodType() == null)
			setPeriodType(BudgetPeriodType.BUDGET_PERIOD_MONTH);
		return BudgetPeriodType.valueOf(getBudgetCategory().getPeriodType());
	}
	
	/**
	 * Sets the Budget Period type. 
	 * @param periodType
	 */
	public void setPeriodType(BudgetPeriodType periodType) {
		getBudgetCategory().setPeriodType(periodType.toString());
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
		getModel().setChanged();
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
		return getBudgetPeriodType() + ":" + BudgetPeriodUtil.getStartOfBudgetPeriod(getBudgetPeriodType(), periodDate).getTime();
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
			return BudgetPeriodUtil.getStartOfBudgetPeriod(getPeriodType(periodKey), new Date(l));
		}

		throw new DataModelProblemException("Cannot parse date from key " + periodKey, this.getModel());
	}
	
	/**
	 * Parses a periodKey to get the period type
	 * @param periodKey
	 * @return
	 */
	private BudgetPeriodType getPeriodType(String periodKey){
		String[] splitKey = periodKey.split(":");
		if (splitKey.length > 0){
			return BudgetPeriodType.valueOf(splitKey[0]);
		}

		throw new DataModelProblemException("Cannot parse BudgetPeriodType from key " + periodKey, this.getModel());		
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

		Date temp = BudgetPeriodUtil.getStartOfBudgetPeriod(getBudgetPeriodType(), startDate);

		while (temp.before(BudgetPeriodUtil.getEndOfBudgetPeriod(getBudgetPeriodType(), endDate))){
			budgetPeriodKeys.add(getPeriodKey(temp));
			temp = BudgetPeriodUtil.addBudgetPeriod(getBudgetPeriodType(), temp, 1);
		}

		return budgetPeriodKeys;
	}
	
	public long getAmount(Date startDate, Date endDate){
		if (startDate.after(endDate))
			throw new RuntimeException("Start date cannot be before End Date!");
		
//		DataModel model = getBudgetCategory().getModel(); 
		
		//If Start and End are in the same budget period
		if (BudgetPeriodUtil.getStartOfBudgetPeriod(getBudgetPeriodType(), startDate).equals(
				BudgetPeriodUtil.getStartOfBudgetPeriod(getBudgetPeriodType(), endDate))){
			long amount = getAmount(startDate);
			long daysInPeriod = BudgetPeriodUtil.getDaysInPeriod(getBudgetPeriodType(), startDate); 
			long daysBetween = DateFunctions.getDaysBetween(startDate, endDate, true);
		
			return (long) (((double) amount / (double) daysInPeriod) * daysBetween);
		}
		 
		if (BudgetPeriodUtil.addBudgetPeriod(getBudgetPeriodType(), startDate, 1).equals(
				BudgetPeriodUtil.getStartOfBudgetPeriod(getBudgetPeriodType(), endDate))
				|| BudgetPeriodUtil.addBudgetPeriod(getBudgetPeriodType(), startDate, 1).before(
						BudgetPeriodUtil.getStartOfBudgetPeriod(getBudgetPeriodType(), endDate))){
			long amountStartPeriod = getAmount(startDate);
			long daysInStartPeriod = BudgetPeriodUtil.getDaysInPeriod(getBudgetPeriodType(), startDate); 
			long daysAfterStartDateInStartPeriod = DateFunctions.getDaysBetween(startDate, BudgetPeriodUtil.getEndOfBudgetPeriod(getBudgetPeriodType(), startDate), true);
			long totalStartPeriod = (long) (((double) amountStartPeriod / (double) daysInStartPeriod) * daysAfterStartDateInStartPeriod);
			
			long totalInMiddle = 0;
			for (String periodKey : getBudgetPeriods(
					BudgetPeriodUtil.addBudgetPeriod(getBudgetPeriodType(), startDate, 1),
					BudgetPeriodUtil.addBudgetPeriod(getBudgetPeriodType(), endDate, -1))) {
				totalInMiddle += getAmount(getPeriodDate(periodKey));
			}
			
			long amountEndPeriod = getAmount(endDate);
			long daysInEndPeriod = BudgetPeriodUtil.getDaysInPeriod(getBudgetPeriodType(), startDate); 
			long daysBeforeEndDateInEndPeriod = DateFunctions.getDaysBetween(startDate, BudgetPeriodUtil.getEndOfBudgetPeriod(getBudgetPeriodType(), startDate), true);
			long totalEndPeriod = (long) (((double) amountEndPeriod / (double) daysInEndPeriod) * daysBeforeEndDateInEndPeriod); 

			return totalStartPeriod + totalInMiddle + totalEndPeriod;
		}

		throw new RuntimeException("You should not be here.  We need to debug this bad boy...");
	}

	
	@Override
	public int compareTo(ModelObject arg0) {
		if (arg0 instanceof BudgetCategory){
			BudgetCategory c = (BudgetCategory) arg0;
			if (this.isIncome() != c.isIncome())
				return -1 * new Boolean(this.isIncome()).compareTo(new Boolean(c.isIncome()));
		}
		return super.compareTo(arg0);
	}
	
	BudgetCategoryBean getBudgetCategoryBean(){
		return getBudgetCategory();
	}
		
	private BudgetCategoryBean getBudgetCategory(){
		return (BudgetCategoryBean) getSourceBean();
	}
}
