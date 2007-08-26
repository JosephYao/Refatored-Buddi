/*
 * Created on Aug 12, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.plugin.api.model;

import java.util.Date;
import java.util.List;

import org.homeunix.thecave.buddi.i18n.keys.BudgetPeriodType;
import org.homeunix.thecave.buddi.model.DataModel;

public interface ImmutableModel extends ImmutableModelObject {
	
	
	/**
	 * Returns the wrapped object from the underlying data model.  By 
	 * accessing this method, you bypass all protection which the Buddi API
	 * gives you; it is not recommended to use this method unless you understand
	 * the risks associated with it. 
	 * @return
	 */
	public DataModel getModel();
	
	public List<ImmutableAccount> getAccounts();
	
	public List<ImmutableBudgetCategory> getBudgetCategories();
	
	public List<ImmutableType> getTypes();
	
	public List<ImmutableTransaction> getTransactions();
	
	public List<ImmutableTransaction> getTransactions(ImmutableSource source);
	
	public List<ImmutableTransaction> getTransactions(Date startDate, Date endDate);
	
	public List<ImmutableTransaction> getTransactions(ImmutableSource source, Date startDate, Date endDate);
	
	public List<ImmutableBudgetPeriod> getBudgetPeriodsInRange(BudgetPeriodType period, Date startDate, Date endDate);
}
