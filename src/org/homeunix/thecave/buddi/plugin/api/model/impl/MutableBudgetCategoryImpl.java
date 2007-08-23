/*
 * Created on Aug 23, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.plugin.api.model.impl;

import org.homeunix.thecave.buddi.model.BudgetCategory;
import org.homeunix.thecave.buddi.plugin.api.model.MutableBudgetCategory;

public abstract class MutableBudgetCategoryImpl extends MutableSourceImpl implements MutableBudgetCategory {

	public MutableBudgetCategoryImpl(BudgetCategory budgetCategory) {
		super(budgetCategory);
	}
}
