/*
 * Created on Aug 12, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.plugin.api.model.impl;

import java.util.Date;

import org.homeunix.thecave.buddi.model.BudgetCategory;
import org.homeunix.thecave.buddi.model.BudgetPeriod;
import org.homeunix.thecave.buddi.model.DataModel;
import org.homeunix.thecave.buddi.plugin.api.model.ImmutableBudgetCategory;
import org.homeunix.thecave.buddi.util.BudgetPeriodUtil;
import org.homeunix.thecave.moss.util.DateFunctions;

public class ImmutableBudgetCategoryImpl extends ImmutableSourceImpl implements ImmutableBudgetCategory {
	
	public ImmutableBudgetCategoryImpl(BudgetCategory budgetCategory) {
		super(budgetCategory);
	}
	
	public boolean isIncome() {
		return getBudgetCategory().isIncome();
	}
	public ImmutableBudgetCategory getParent() {
		if (getBudgetCategory().getParent() != null)
			return new ImmutableBudgetCategoryImpl(getBudgetCategory().getParent());
		return null;
	}
	
	public BudgetCategory getBudgetCategory(){
		return (BudgetCategory) getRaw();
	}
	
	public long getBudgetedAmount(Date startDate, Date endDate){
		if (startDate.after(endDate))
			throw new RuntimeException("Start date cannot be before End Date!");
		
		DataModel model = getBudgetCategory().getModel(); 
		
		//If Start and End are in the same budget period
		if (BudgetPeriodUtil.getStartOfBudgetPeriod(model.getPeriodType(), startDate).equals(
				BudgetPeriodUtil.getStartOfBudgetPeriod(model.getPeriodType(), endDate))){
			long amount = model.getBudgetedAmount(getBudgetCategory(), startDate);
			long daysInPeriod = BudgetPeriodUtil.getDaysInPeriod(model.getPeriodType(), startDate); 
			long daysBetween = DateFunctions.getDaysBetween(startDate, endDate, true);
		
			return (long) (((double) amount / (double) daysInPeriod) * daysBetween);
		}
		 
		if (BudgetPeriodUtil.getNextBudgetPeriod(model.getPeriodType(), startDate).equals(
				BudgetPeriodUtil.getStartOfBudgetPeriod(model.getPeriodType(), endDate))
				|| BudgetPeriodUtil.getNextBudgetPeriod(model.getPeriodType(), startDate).before(
						BudgetPeriodUtil.getStartOfBudgetPeriod(model.getPeriodType(), endDate))){
			long amountStartPeriod = model.getBudgetedAmount(getBudgetCategory(), startDate);
			long daysInStartPeriod = BudgetPeriodUtil.getDaysInPeriod(model.getPeriodType(), startDate); 
			long daysAfterStartDateInStartPeriod = DateFunctions.getDaysBetween(startDate, BudgetPeriodUtil.getEndOfBudgetPeriod(model.getPeriodType(), startDate), true);
			long totalStartPeriod = (long) (((double) amountStartPeriod / (double) daysInStartPeriod) * daysAfterStartDateInStartPeriod);
			
			long totalInMiddle = 0;
			for (BudgetPeriod bp : model.getBudgetPeriodsInRange(
					BudgetPeriodUtil.getNextBudgetPeriod(model.getPeriodType(), startDate),
					BudgetPeriodUtil.getPreviousBudgetPeriod(model.getPeriodType(), endDate))) {
				totalInMiddle += bp.getAmount(getBudgetCategory());
			}
			
			long amountEndPeriod = model.getBudgetedAmount(getBudgetCategory(), endDate);
			long daysInEndPeriod = BudgetPeriodUtil.getDaysInPeriod(model.getPeriodType(), startDate); 
			long daysBeforeEndDateInEndPeriod = DateFunctions.getDaysBetween(startDate, BudgetPeriodUtil.getEndOfBudgetPeriod(model.getPeriodType(), startDate), true);
			long totalEndPeriod = (long) (((double) amountEndPeriod / (double) daysInEndPeriod) * daysBeforeEndDateInEndPeriod); 

			return totalStartPeriod + totalInMiddle + totalEndPeriod;
		}

		throw new RuntimeException("You should not be here.  We need to debug this bad boy...");
	}
}
