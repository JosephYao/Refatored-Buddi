/*
 * Created on Aug 23, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.plugin.api.model.impl;

import org.homeunix.thecave.buddi.model.BudgetPeriod;
import org.homeunix.thecave.buddi.plugin.api.model.MutableBudgetPeriod;

public abstract class MutableBudgetPeriodImpl extends ImmutableBudgetPeriodImpl implements MutableBudgetPeriod {

	public MutableBudgetPeriodImpl(BudgetPeriod period) {
		super(period);
	}
}
