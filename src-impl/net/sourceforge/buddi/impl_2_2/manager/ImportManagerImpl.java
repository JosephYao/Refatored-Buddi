package net.sourceforge.buddi.impl_2_2.manager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import net.sourceforge.buddi.api.exception.ValidationException;
import net.sourceforge.buddi.api.manager.ImportManager;
import net.sourceforge.buddi.api.model.ImmutableAccount;
import net.sourceforge.buddi.api.model.ImmutableCategory;
import net.sourceforge.buddi.api.model.ImmutableTransaction;
import net.sourceforge.buddi.api.model.MutableAccount;
import net.sourceforge.buddi.api.model.MutableCategory;
import net.sourceforge.buddi.api.model.MutableTransaction;
import net.sourceforge.buddi.impl_2_2.model.MutableAccountImpl;
import net.sourceforge.buddi.impl_2_2.model.MutableCategoryImpl;
import net.sourceforge.buddi.impl_2_2.model.MutableTransactionImpl;

import org.homeunix.drummer.controller.SourceController;
import org.homeunix.drummer.controller.TransactionController;
import org.homeunix.drummer.model.Account;
import org.homeunix.drummer.model.Category;
import org.homeunix.drummer.model.ModelFactory;
import org.homeunix.drummer.model.Transaction;

public class ImportManagerImpl extends DataManagerImpl implements ImportManager {

    public ImportManagerImpl(Account selectedAccount, Category selectedCategory, Transaction selectedTransaction) {
        super(selectedAccount, selectedCategory, selectedTransaction);
    }

    private List<MutableTransactionImpl> temporaryTransactionList = new ArrayList<MutableTransactionImpl>();
    private List<MutableCategoryImpl> temporaryCategoryList = new ArrayList<MutableCategoryImpl>();
    private List<MutableAccountImpl> temporaryAccountList = new ArrayList<MutableAccountImpl>();

    public MutableTransaction createTransaction() {
        MutableTransactionImpl mutableTransactionImpl = new MutableTransactionImpl(ModelFactory.eINSTANCE.createTransaction());
        temporaryTransactionList.add(mutableTransactionImpl);
        return mutableTransactionImpl;
    }

    public MutableCategory createCategory() {
        MutableCategoryImpl mutableCategoryImpl = new MutableCategoryImpl(ModelFactory.eINSTANCE.createCategory());
        temporaryCategoryList.add(mutableCategoryImpl);
        return mutableCategoryImpl;
    }

    public MutableAccount createAccount() {
        MutableAccountImpl mutableAccountImpl = new MutableAccountImpl(ModelFactory.eINSTANCE.createAccount());
        temporaryAccountList.add(mutableAccountImpl);
        return mutableAccountImpl;
    }

    public void saveChanges() throws ValidationException {
        for(MutableAccountImpl mutableAccountImpl: temporaryAccountList) {
            mutableAccountImpl.validate();
            SourceController.addAccount(mutableAccountImpl.getImpl());
        }
        
        for(MutableCategoryImpl mutableCategoryImpl: temporaryCategoryList) {
            mutableCategoryImpl.validate();
            SourceController.addCategory(mutableCategoryImpl.getImpl());
        }
        
        for(MutableTransactionImpl mutableTransactionImpl: temporaryTransactionList) {
            mutableTransactionImpl.validate();
            TransactionController.addTransaction(mutableTransactionImpl.getImpl());
        }
    }

    public void rollbackChanges() {
        temporaryTransactionList.clear();
        temporaryCategoryList.clear();
        temporaryAccountList.clear();
    }

    public ImmutableCategory findCategoryByName(String name) {
        if (null == name)
        {
            return null;
        }

        for(ImmutableCategory immutableCategory: temporaryCategoryList) {
            if(name.compareToIgnoreCase(immutableCategory.getName()) == 0)
                return immutableCategory;
        }

        return super.findCategoryByName(name);
    }

    public ImmutableAccount findAccountByName(String name) {
        if (null == name)
        {
            return null;
        }

        for(ImmutableAccount immutableAccount: temporaryAccountList) {
            if(name.compareToIgnoreCase(immutableAccount.getName()) == 0)
                return immutableAccount;
        }

        return super.findAccountByName(name);
    }

    public Collection<ImmutableCategory> getCategories() {
        Collection<ImmutableCategory> categories = super.getCategories();
        categories.addAll(temporaryCategoryList);
        return categories;
    }

    public Collection<ImmutableAccount> getAccounts() {
        Collection<ImmutableAccount> accounts = super.getAccounts();
        accounts.addAll(temporaryAccountList);
        return accounts;
    }

    public Collection<ImmutableTransaction> getTransactions() {
        Collection<ImmutableTransaction> transactions = super.getTransactions();
        transactions.addAll(temporaryTransactionList);
        return transactions;
    }

    public Collection<ImmutableTransaction> getTransactionsForAccount(ImmutableAccount account) {
        Collection<ImmutableTransaction> matchingTransactions = super.getTransactionsForAccount(account);
        
        for (ImmutableTransaction transaction : temporaryTransactionList) {
            if (transaction.getFrom() != null && transaction.getTo() != null && 
                    (transaction.getFrom().equals(account) || transaction.getTo().equals(account)))
                matchingTransactions.add(transaction);
        }
        
        return matchingTransactions;
    }
}
