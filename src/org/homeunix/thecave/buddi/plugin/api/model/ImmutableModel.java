/*
 * Created on Aug 12, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.plugin.api.model;

import java.util.Date;
import java.util.List;

import org.homeunix.thecave.buddi.model.DataModel;

/**
 * The API version of DataModel.  This contains methods to access all other  
 * objects in the model.  This is the object passed to plugins which do not
 * require write access (such as reports and export plugins)
 * 
 * @author wyatt
 *
 */
public interface ImmutableModel extends ImmutableModelObject {
	
	/**
	 * Returns the wrapped object from the underlying data model.  By 
	 * accessing this method, you bypass all protection which the Buddi API
	 * gives you; it is not recommended to use this method unless you understand
	 * the risks associated with it. 
	 * @return
	 */
	public DataModel getModel();
	
	/**
	 * Returns a list of all immutable accounts in the model
	 * @return
	 */
	public List<ImmutableAccount> getAccounts();
	
	/**
	 * Returns a list of all immutable budget categories in the model
	 * @return
	 */
	public List<ImmutableBudgetCategory> getBudgetCategories();
	
	/**
	 * Returns a list of all immutable types in the model
	 * @return
	 */
	public List<ImmutableType> getTypes();
	
	/**
	 * Returns a list of all immutable transactions in the model
	 * @return
	 */
	public List<ImmutableTransaction> getTransactions();
	
	/**
	 * Returns a list of all immutable transactions in the model which are
	 * associatd with the given source
	 * @return
	 */
	public List<ImmutableTransaction> getTransactions(ImmutableSource source);
	
	/**
	 * Returns a list of all immutable transactions in the model which are
	 * between startDate and endDate
	 * @return
	 */
	public List<ImmutableTransaction> getTransactions(Date startDate, Date endDate);
	
	/**
	 * Returns a list of all immutable transactions in the model which are associated with
	 * source and between startDate and endDate
	 * @return
	 */
	public List<ImmutableTransaction> getTransactions(ImmutableSource source, Date startDate, Date endDate);
}
