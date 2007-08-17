/*
 * Created on Aug 12, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.plugin.api.model.immutable;

import org.homeunix.thecave.buddi.model.BudgetPeriod;

public class ImmutableBudgetPeriod extends ImmutableModelObject {
	
	public ImmutableBudgetPeriod(BudgetPeriod budgetPeriod) {
		super(budgetPeriod);
	}
	
	public BudgetPeriod getBudgetPeriod(){
		return (BudgetPeriod) getRaw();
	}
}
