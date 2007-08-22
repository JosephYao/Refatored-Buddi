/*
 * Created on Aug 12, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.plugin.api.model.mutable;

import org.homeunix.thecave.buddi.model.BudgetPeriod;

public class MutableBudgetPeriod extends MutableModelObject {
	
	public MutableBudgetPeriod(BudgetPeriod budgetPeriod) {
		super(budgetPeriod);
	}
	
	public BudgetPeriod getBudgetPeriod(){
		return (BudgetPeriod) getRaw();
	}
}
