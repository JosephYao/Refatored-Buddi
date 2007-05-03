package net.sourceforge.buddi.impl_2_2.manager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

import net.sourceforge.buddi.api.manager.DataManager;
import net.sourceforge.buddi.api.model.ImmutableAccount;
import net.sourceforge.buddi.api.model.ImmutableCategory;
import net.sourceforge.buddi.api.model.ImmutableTransaction;
import net.sourceforge.buddi.impl_2_2.model.ImmutableAccountImpl;
import net.sourceforge.buddi.impl_2_2.model.ImmutableCategoryImpl;
import net.sourceforge.buddi.impl_2_2.model.ImmutableTransactionImpl;

import org.homeunix.drummer.controller.SourceController;
import org.homeunix.drummer.controller.TransactionController;
import org.homeunix.drummer.model.Account;
import org.homeunix.drummer.model.Category;
import org.homeunix.drummer.model.Transaction;

public class DataManagerImpl extends UtilityManagerImpl implements DataManager {

    private ImmutableAccount selectedImmutableAccount = null;
    private ImmutableCategory selectedImmutableCategory = null;
    private ImmutableTransaction selectedImmutableTransaction = null;
    
    private DataManagerImpl()
    {
        
    }
    
    public DataManagerImpl(Account selectedAccount, Category selectedCategory, Transaction selectedTransaction) {
        if (null != selectedAccount)
        {
            this.selectedImmutableAccount = new ImmutableAccountImpl(selectedAccount);
        }
        if (null != selectedCategory)
        {
            this.selectedImmutableCategory = new ImmutableCategoryImpl(selectedCategory);  
        }
        if (null != selectedTransaction)
        {
            this.selectedImmutableTransaction = new ImmutableTransactionImpl(selectedTransaction);
        }
    }

    public ImmutableAccount getSelectedAccount() {
        return selectedImmutableAccount;
    }

    public ImmutableCategory getSelectedCategory() {
        return selectedImmutableCategory;
    }

    public ImmutableTransaction getSelectedTransaction() {
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

    public Collection<ImmutableTransaction> getTransactionsForAccount(ImmutableAccount account) {
        Vector<Transaction> transactionVector = TransactionController.getTransactions(((ImmutableAccountImpl)account).getImpl());
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

}
