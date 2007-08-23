/*
 * Created on Aug 12, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.plugin.api.model;

import java.util.Date;
import java.util.List;

import org.homeunix.thecave.buddi.i18n.keys.BudgetPeriodKeys;
import org.homeunix.thecave.buddi.model.DataModel;

public interface ImmutableModel extends ImmutableModelObject {
	
	public BudgetPeriodKeys getPeriodType();
	
	public DataModel getModel();
	
	public List<ImmutableAccount> getAccounts();
	
	public List<ImmutableBudgetCategory> getBudgetCategories();
	
	public List<ImmutableType> getTypes();
	
	public List<ImmutableTransaction> getTransactions();
	
	public List<ImmutableTransaction> getTransactions(ImmutableSource source);
	
	public List<ImmutableTransaction> getTransactions(Date startDate, Date endDate);
	
	public List<ImmutableTransaction> getTransactions(ImmutableSource source, Date startDate, Date endDate);
	
	public List<ImmutableBudgetPeriod> getBudgetPeriodsInRange(Date startDate, Date endDate);
}
