package net.sourceforge.buddi.impl_2_4.manager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import net.sourceforge.buddi.api.manager.DataManager;
import net.sourceforge.buddi.api.model.ImmutableAccount;
import net.sourceforge.buddi.api.model.ImmutableCategory;
import net.sourceforge.buddi.api.model.ImmutableSource;
import net.sourceforge.buddi.api.model.ImmutableTransaction;
import net.sourceforge.buddi.api.model.ImmutableType;
import net.sourceforge.buddi.impl_2_4.model.ImmutableAccountImpl;
import net.sourceforge.buddi.impl_2_4.model.ImmutableCategoryImpl;
import net.sourceforge.buddi.impl_2_4.model.ImmutableSourceImpl;
import net.sourceforge.buddi.impl_2_4.model.ImmutableTransactionImpl;
import net.sourceforge.buddi.impl_2_4.model.ImmutableTypeImpl;

import org.homeunix.drummer.controller.SourceController;
import org.homeunix.drummer.controller.TransactionController;
import org.homeunix.drummer.controller.TypeController;
import org.homeunix.drummer.model.Account;
import org.homeunix.drummer.model.Category;
import org.homeunix.drummer.model.Transaction;
import org.homeunix.drummer.model.Type;
import org.homeunix.thecave.moss.util.Version;

/**
 * @author wyatt
 * @deprecated
 */
public class DataManagerImpl implements DataManager {

    private final ImmutableAccount selectedImmutableAccount;
    private final ImmutableCategory selectedImmutableCategory;
    private final ImmutableTransaction selectedImmutableTransaction;
    
//    private DataManagerImpl(){
//        selectedImmutableAccount = null;
//        selectedImmutableCategory = null;
//        selectedImmutableTransaction = null;
//    }
    
    public DataManagerImpl(Account selectedAccount, Category selectedCategory, Transaction selectedTransaction) {
    	if (selectedAccount != null)
    		this.selectedImmutableAccount = new ImmutableAccountImpl(selectedAccount);
    	else 
    		this.selectedImmutableAccount = null;
    	
    	if (selectedCategory != null)
    		this.selectedImmutableCategory = new ImmutableCategoryImpl(selectedCategory);
    	else
    		this.selectedImmutableCategory = null;
    	
    	if (selectedTransaction != null)
    		this.selectedImmutableTransaction = new ImmutableTransactionImpl(selectedTransaction);
    	else
    		this.selectedImmutableTransaction = null;
    }

    public ImmutableAccount getAccount() {
        return selectedImmutableAccount;
    }

    public ImmutableCategory getCategory() {
        return selectedImmutableCategory;
    }

    public ImmutableTransaction getTransaction() {
        return selectedImmutableTransaction;
    }

    public ImmutableCategory findCategoryByName(String name) {
        for(Category cat: SourceController.getCategories())
            if(cat.getName().compareToIgnoreCase(name) == 0)
                return new ImmutableCategoryImpl(cat);
        
        return null;
    }

    public ImmutableAccount findAccountByName(String name) {
        for(Account account: SourceController.getAccounts())
            if(account.getName().compareToIgnoreCase(name) == 0)
                return new ImmutableAccountImpl(account);
        
        return null;
    }

    public Collection<ImmutableCategory> getCategories() {
        Vector<Category> categoryVector = SourceController.getCategories();
        if (null == categoryVector)
        {
            return Collections.emptyList();
        }

        List<ImmutableCategory> immutableCategoryList = new ArrayList<ImmutableCategory>();
        
        for (Category category: categoryVector) {
            ImmutableCategoryImpl immutableCategory = new ImmutableCategoryImpl(category);
            immutableCategoryList.add(immutableCategory);
        }
        
        return immutableCategoryList;
    }
    
    public Collection<ImmutableType> getTypes() {
        Vector<Type> typeVector = TypeController.getTypes();
        if (null == typeVector)
        {
            return Collections.emptyList();
        }

        List<ImmutableType> immutableTypeList = new ArrayList<ImmutableType>();
        
        for (Type type: typeVector) {
            ImmutableTypeImpl immutableType = new ImmutableTypeImpl(type);
            immutableTypeList.add(immutableType);
        }
        
        return immutableTypeList;
    }


    public Collection<ImmutableAccount> getAccounts() {
        Vector<Account> accountVector = SourceController.getAccounts();
        if (null == accountVector)
        {
            return Collections.emptyList();
        }

        List<ImmutableAccount> immutableAccountList = new ArrayList<ImmutableAccount>();
        
        for (Account account: accountVector) {
            ImmutableAccountImpl immutableAccount = new ImmutableAccountImpl(account);
            immutableAccountList.add(immutableAccount);
        }
        
        return immutableAccountList;
    }

    public Collection<ImmutableTransaction> getTransactions() {
        Vector<Transaction> transactionVector = TransactionController.getTransactions();
        return getTransactionsForList(transactionVector);
    }

    public Collection<ImmutableTransaction> getTransactions(ImmutableSource source) {
        Vector<Transaction> transactionVector = TransactionController.getTransactions(((ImmutableSourceImpl) source).getImpl());
        return getTransactionsForList(transactionVector);
    }

    public Collection<ImmutableTransaction> getTransactions(Boolean isIncome, Date startDate, Date endDate) {
        Vector<Transaction> transactionVector = TransactionController.getTransactions(isIncome, startDate, endDate);
        return getTransactionsForList(transactionVector);
    }
    
    public Collection<ImmutableTransaction> getTransactions(Date startDate, Date endDate) {
        Vector<Transaction> transactionVector = TransactionController.getTransactions(startDate, endDate);
        return getTransactionsForList(transactionVector);
    }

    public Collection<ImmutableTransaction> getTransactions(ImmutableSource source, Date startDate, Date endDate) {
        Vector<Transaction> transactionVector = TransactionController.getTransactions(((ImmutableSourceImpl) source).getImpl(), startDate, endDate);
        return getTransactionsForList(transactionVector);
    }
    
    public Collection<ImmutableTransaction> getTransactions(ImmutableSource source, Integer year, Integer month, Integer dayOfMonth) {
        Vector<Transaction> transactionVector = TransactionController.getTransactions(((ImmutableSourceImpl) source).getImpl(), year, month, dayOfMonth);
        return getTransactionsForList(transactionVector);
    }
    
    public Collection<ImmutableTransaction> getTransactions(String description, Date startDate, Date endDate) {
        Vector<Transaction> transactionVector = TransactionController.getTransactions(description, startDate, endDate);
        return getTransactionsForList(transactionVector);
    }
    
    private Collection<ImmutableTransaction> getTransactionsForList(Vector<Transaction> transactionVector) {
        if (null == transactionVector)
        {
            return Collections.emptyList();
        }

        List<ImmutableTransaction> immutableTransactionList = new ArrayList<ImmutableTransaction>();
        
        for (Transaction transaction: transactionVector) {
            ImmutableTransactionImpl immutableTransaction = new ImmutableTransactionImpl(transaction);
            immutableTransactionList.add(immutableTransaction);
        }
        
        return immutableTransactionList;
    }

    public Version getAPIVersion() {
    	return new Version("2.4");
    }    
}
