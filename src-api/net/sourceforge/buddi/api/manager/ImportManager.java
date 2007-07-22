package net.sourceforge.buddi.api.manager;

import java.util.Collection;

import net.sourceforge.buddi.api.exception.ValidationException;
import net.sourceforge.buddi.api.model.ImmutableAccount;
import net.sourceforge.buddi.api.model.ImmutableCategory;
import net.sourceforge.buddi.api.model.ImmutableSource;
import net.sourceforge.buddi.api.model.ImmutableTransaction;
import net.sourceforge.buddi.api.model.ImmutableType;
import net.sourceforge.buddi.api.model.MutableAccount;
import net.sourceforge.buddi.api.model.MutableCategory;
import net.sourceforge.buddi.api.model.MutableTransaction;
import net.sourceforge.buddi.api.model.MutableType;

/**
 * @author wyatt
 * This implementation of this class allows plugin authors to modify data in the 
 * data model.  Perhaps ImportManager is not the best term; possibly 
 * "MutableDataManager" or something would have made more sense.
 */
public interface ImportManager extends DataManager {

	/**
	 * Returns a list of all transactions currently in the data model, excluding those
	 * pending a commit.
	 * @return
	 */
	public Collection<MutableTransaction> getModelTransactions();
	
	/**
	 * Returns a list of all transactions currently in the data model which are
	 * associated with the given source, excluding those pending a commit.
	 * @return
	 */
	public Collection<MutableTransaction> getModelTransactions(ImmutableSource s);
	
	/**
	 * Returns the single (first, if there are multiple) transaction which matches the 
	 * specified UID, or null if it does not exist. 
	 * @param uid
	 * @return
	 */
	public MutableTransaction getModelTransaction(String uid);
	
	/**
	 * Returns a list of all accounts currently in the data model, excluding those
	 * pending a commit.
	 * @return
	 */
	public Collection<MutableAccount> getModelAccounts();
	
	/**
	 * Returns a list of all categories currently in the data model, excluding those
	 * pending a commit.
	 * @return
	 */
	public Collection<MutableCategory> getModelCategories();
	
	/**
	 * Returns a list of all types currently in the data model, excluding those
	 * pending a commit.
	 * @return
	 */
	public Collection<MutableType> getModelTypes();
	
	/**
	 * Remove the given transaction.  Must call saveChanges() to commit changes before it will be applied.
	 * @param t
	 */
	public void removeTransaction(MutableTransaction t);
	
	/**
	 * Remove the given account.  Must call saveChanges() to commit changes before it will be applied.
	 * @param a
	 */	
	public void removeAccount(MutableAccount a);
	
	/**
	 * Remove the given category.  Must call saveChanges() to commit changes before it will be applied.
	 * @param c
	 */
	public void removeCategory(MutableCategory c);
	
	
	/**
	 * Remove the given type.  Must call saveChanges() to commit changes before it will be applied.
	 * @param t
	 */
	public void removeType(MutableType t);
	
	
    /**
     * Create and add a new MutableTransaction to the ImportManager.
     * The transaction will be made permanent if saveChanges() succeeds.
     * 
     * @return new MutableTransaction
     */
    public MutableTransaction createTransaction();
    
    /**
     * Create and add a new MutableType to the ImportManager.
     * The type will be made permanent if saveChanges() succeeds.
     * @return
     */
    public MutableType createType();
    
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
     * Remove the object specified, and avoid it from being imported on saveChanges().
     * @param t
     */
    public void rollbackTransaction(ImmutableTransaction t);

    /**
     * Remove the object specified, and avoid it from being imported on saveChanges().
     * @param a
     */
    public void rollbackAccount(ImmutableAccount a);
    
    /**
     * Remove the object specified, and avoid it from being imported on saveChanges().
     * @param c
     */
    public void rollbackCategory(ImmutableCategory c);
    
    /**
     * Remove the object specified, and avoid it from being imported on saveChanges().
     * @param t
     */
    public void rollbackType(ImmutableType t);

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
     * @return ImmutableType Collection of all types, including those waiting to be imported
     */
    public Collection<ImmutableType> getTypes();

    /**
     * @return ImmutableTransaction Collection of all transactions for specified account, including those waiting to be imported
     */
    public Collection<ImmutableTransaction> getTransactionsForAccount(ImmutableAccount account);
}
