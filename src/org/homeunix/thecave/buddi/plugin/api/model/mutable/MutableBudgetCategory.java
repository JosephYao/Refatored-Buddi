/*
 * Created on Aug 12, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.plugin.api.model.mutable;

import org.homeunix.thecave.buddi.model.BudgetCategory;

public class MutableBudgetCategory extends MutableSource {
	
	public MutableBudgetCategory(BudgetCategory budgetCategory) {
		super(budgetCategory);
	}
	
	public boolean isIncome() {
		return getBudgetCategory().isIncome();
	}
	public MutableBudgetCategory getParent() {
		if (getBudgetCategory().getParent() != null)
			return new MutableBudgetCategory(getBudgetCategory().getParent());
		return null;
	}
	
	public BudgetCategory getBudgetCategory(){
		return (BudgetCategory) getRaw();
	}
}
