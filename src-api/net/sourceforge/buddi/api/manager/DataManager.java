package net.sourceforge.buddi.api.manager;

import java.util.Collection;
import java.util.Date;

import net.sourceforge.buddi.api.model.ImmutableAccount;
import net.sourceforge.buddi.api.model.ImmutableCategory;
import net.sourceforge.buddi.api.model.ImmutableSource;
import net.sourceforge.buddi.api.model.ImmutableTransaction;

import org.homeunix.thecave.moss.util.Version;

public interface DataManager {

    /**
     * @return ImmutableAccount currently selected in interface or null if no account selected.
     */
    public ImmutableAccount getSelectedAccount();
    
    /**
     * @return ImmutableCategory currently selected in interface or null if no category selected.
     */
    public ImmutableCategory getSelectedCategory();

    /**
     * @return ImmutableTransaction currently selected in interface or null if no transaction selected.
     */
    public ImmutableTransaction getSelectedTransaction();

    /**
     * @return ImmutableCategory with specified name
     */
    public ImmutableCategory findCategoryByName(String name); 

    /**
     * @return ImmutableAccount with specified name
     */
    public ImmutableAccount findAccountByName(String name); 
     
    /**
     * @return ImmutableCategory Collection of all categories
     */
    public Collection<ImmutableCategory> getCategories(); 
    
    /**
     * @return ImmutableAccount Collection of all accounts
     */
    public Collection<ImmutableAccount> getAccounts();
    
    /**
     * @return ImmutableTransaction Collection of all transactions
     */
    public Collection<ImmutableTransaction> getTransactions(); 

    /**
     * @return ImmutableTransaction Collection of all transactions for specified account
     */
    public Collection<ImmutableTransaction> getTransactions(ImmutableSource source);

	/**
	 * Returns all transactions within a given time frame.  Must match all
	 * given time arguments; set an argument to null to ignore
	 * @param source
	 * @param year
	 * @param month
	 * @param day
	 * @return
	 */
	public Collection<ImmutableTransaction> getTransactions(ImmutableSource source, Integer year, Integer month, Integer dayOfMonth);
	
	/**
	 * Returns all transactions which meet the given criteria
	 * @param description Only return transactions matching this description
	 * @param startDate Only return transactions happening after this
	 * @param endDate Only return transactions happening before this
	 * @return
	 */
	public Collection<ImmutableTransaction> getTransactions(String description, Date startDate, Date endDate);

	/**
	 * Returns all transactions which meet the given criteria
	 * @param isIncome Does the transaction represent income?
	 * @param startDate Only return transactions happening after this
	 * @param endDate Only return transactions happening before this
	 * @return
	 */
	public Collection<ImmutableTransaction> getTransactions(Boolean isIncome, Date startDate, Date endDate);

	/**
	 * Returns all transactions between start and end
	 * @param startDate Only return transactions happening after this
	 * @param endDate Only return transactions happening before this
	 * @return
	 */
	public Collection<ImmutableTransaction> getTransactions(Date startDate, Date endDate);

	/**
	 * Returns all transactions between start and end
	 * @param startDate Only return transactions happening after this
	 * @param endDate Only return transactions happening before this
	 * @return
	 */
	public Collection<ImmutableTransaction> getTransactions(ImmutableSource source, Date startDate, Date endDate);
	
    /**
     * @return Version The version of the API (should only be Major.Minor)
     */
    public Version getAPIVersion();
}
