/*
 * Created on Aug 12, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.plugin.api.model;

import org.homeunix.thecave.buddi.model.BudgetCategory;

public interface ImmutableBudgetCategory extends ImmutableSource {
		
	public boolean isIncome();
	
	public ImmutableBudgetCategory getParent();
	
	public BudgetCategory getBudgetCategory();
}
