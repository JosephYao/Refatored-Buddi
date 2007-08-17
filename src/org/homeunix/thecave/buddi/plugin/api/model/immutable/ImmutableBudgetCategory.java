/*
 * Created on Aug 12, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.plugin.api.model.immutable;

import org.homeunix.thecave.buddi.model.BudgetCategory;

public class ImmutableBudgetCategory extends ImmutableSource {
	
	public ImmutableBudgetCategory(BudgetCategory budgetCategory) {
		super(budgetCategory);
	}
	
	public boolean isIncome() {
		return getBudgetCategory().isIncome();
	}
	public ImmutableBudgetCategory getParent() {
		if (getBudgetCategory().getParent() != null)
			return new ImmutableBudgetCategory(getBudgetCategory().getParent());
		return null;
	}
	
	public BudgetCategory getBudgetCategory(){
		return (BudgetCategory) getRaw();
	}
}
