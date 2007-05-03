package net.sourceforge.buddi.api.manager;

import java.util.Collection;

import net.sourceforge.buddi.api.exception.ValidationException;
import net.sourceforge.buddi.api.model.ImmutableAccount;
import net.sourceforge.buddi.api.model.ImmutableCategory;
import net.sourceforge.buddi.api.model.ImmutableTransaction;
import net.sourceforge.buddi.api.model.MutableAccount;
import net.sourceforge.buddi.api.model.MutableCategory;
import net.sourceforge.buddi.api.model.MutableTransaction;

public interface ImportManager extends DataManager {

    /**
     * Create and add a new MutableTransaction to the ImportManager.
     * The transaction will be made permanent if saveChanges() succeeds.
     * 
     * @return new MutableTransaction
     */
    public MutableTransaction createTransaction();
    
    /**
     * Create and add a new MutableCategory to the ImportManager.
     * The category will be made permanent if saveChanges() succeeds.
     * 
     * @return new MutableCategory
     */
    public MutableCategory createCategory(); 
    
    /**
     * Create and add a new MutableAccount to the ImportManager.
     * The account will be made permanent if saveChanges() succeeds.
     * 
     * @return new MutableAccount
     */
    public MutableAccount createAccount();
    
    
    /**
     * Validate created objects and import objects into Buddi.
     * 
     * @throws ValidationException if objects are in an invalid state.
     */
    public void saveChanges() throws ValidationException;
    
    /**
     * Destroy all new objects created in this importManager without importing them to Buddi.
     */
    public void rollbackChanges();


    /**
     * @return ImmutableCategory with specified name, including those waiting to be imported
     */
    public ImmutableCategory findCategoryByName(String name); 

    /**
     * @return ImmutableAccount with specified name, including those waiting to be imported
     */
    public ImmutableAccount findAccountByName(String name); 
     
    /**
     * @return ImmutableCategory Collection of all categories, including those waiting to be imported
     */
    public Collection<ImmutableCategory> getCategories(); 
    
    /**
     * @return ImmutableAccount Collection of all accounts, including those waiting to be imported
     */
    public Collection<ImmutableAccount> getAccounts();
    
    /**
     * @return ImmutableTransaction Collection of all transactions, including those waiting to be imported
     */
    public Collection<ImmutableTransaction> getTransactions(); 

    /**
     * @return ImmutableTransaction Collection of all transactions for specified account, including those waiting to be imported
     */
    public Collection<ImmutableTransaction> getTransactionsForAccount(ImmutableAccount account);
}
