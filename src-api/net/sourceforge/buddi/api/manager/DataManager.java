package net.sourceforge.buddi.api.manager;

import java.util.Collection;

import net.sourceforge.buddi.api.model.ImmutableAccount;
import net.sourceforge.buddi.api.model.ImmutableCategory;
import net.sourceforge.buddi.api.model.ImmutableTransaction;

public interface DataManager extends UtilityManager {

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
    public Collection<ImmutableTransaction> getTransactionsForAccount(ImmutableAccount account);
}
