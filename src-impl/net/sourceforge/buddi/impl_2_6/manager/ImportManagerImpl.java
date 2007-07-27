package net.sourceforge.buddi.impl_2_6.manager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import net.sourceforge.buddi.api.exception.ValidationException;
import net.sourceforge.buddi.api.manager.ImportManager;
import net.sourceforge.buddi.api.model.ImmutableAccount;
import net.sourceforge.buddi.api.model.ImmutableCategory;
import net.sourceforge.buddi.api.model.ImmutableSource;
import net.sourceforge.buddi.api.model.ImmutableTransaction;
import net.sourceforge.buddi.api.model.ImmutableType;
import net.sourceforge.buddi.api.model.MutableAccount;
import net.sourceforge.buddi.api.model.MutableCategory;
import net.sourceforge.buddi.api.model.MutableTransaction;
import net.sourceforge.buddi.api.model.MutableType;
import net.sourceforge.buddi.impl_2_4.model.ImmutableSourceImpl;
import net.sourceforge.buddi.impl_2_4.model.MutableAccountImpl;
import net.sourceforge.buddi.impl_2_4.model.MutableCategoryImpl;
import net.sourceforge.buddi.impl_2_4.model.MutableTransactionImpl;
import net.sourceforge.buddi.impl_2_4.model.MutableTypeImpl;

import org.homeunix.drummer.controller.SourceController;
import org.homeunix.drummer.controller.TransactionController;
import org.homeunix.drummer.controller.TypeController;
import org.homeunix.drummer.model.Account;
import org.homeunix.drummer.model.Category;
import org.homeunix.drummer.model.ModelFactory;
import org.homeunix.drummer.model.Transaction;
import org.homeunix.drummer.model.Type;
import org.homeunix.drummer.view.MainFrame;
import org.homeunix.drummer.view.TransactionsFrame;

public class ImportManagerImpl extends DataManagerImpl implements ImportManager {

    public ImportManagerImpl(Account selectedAccount, Category selectedCategory, Transaction selectedTransaction) {
        super(selectedAccount, selectedCategory, selectedTransaction);
    }

    private List<MutableTransactionImpl> temporaryAddTransactionList = new ArrayList<MutableTransactionImpl>();
    private List<MutableCategoryImpl> temporaryAddCategoryList = new ArrayList<MutableCategoryImpl>();
    private List<MutableAccountImpl> temporaryAddAccountList = new ArrayList<MutableAccountImpl>();
    private List<MutableTypeImpl> temporaryAddTypeList = new ArrayList<MutableTypeImpl>();

    private List<MutableTransactionImpl> temporaryRemoveTransactionList = new ArrayList<MutableTransactionImpl>();
    private List<MutableCategoryImpl> temporaryRemoveCategoryList = new ArrayList<MutableCategoryImpl>();
    private List<MutableAccountImpl> temporaryRemoveAccountList = new ArrayList<MutableAccountImpl>();
    private List<MutableTypeImpl> temporaryRemoveTypeList = new ArrayList<MutableTypeImpl>();

    
    
    public void removeAccount(MutableAccount a) {
    	temporaryRemoveAccountList.add((MutableAccountImpl) a);
	}

	public void removeCategory(MutableCategory c) {
		temporaryRemoveCategoryList.add((MutableCategoryImpl) c);		
	}

	public void removeTransaction(MutableTransaction t) {
		temporaryRemoveTransactionList.add((MutableTransactionImpl) t);
	}

	public void removeType(MutableType t) {
		temporaryRemoveTypeList.add((MutableTypeImpl) t);
	}

	public Collection<MutableAccount> getModelAccounts() {
    	Collection<MutableAccount> accounts = new LinkedList<MutableAccount>();
    	
    	for (Account account : SourceController.getAccounts()) {
			accounts.add(new MutableAccountImpl(account));
		}
    	
		return accounts;
	}

	public Collection<MutableCategory> getModelCategories() {
    	Collection<MutableCategory> categories = new LinkedList<MutableCategory>();
    	
    	for (Category category : SourceController.getCategories()) {
			categories.add(new MutableCategoryImpl(category));
		}
    	
		return categories;
	}

	public MutableTransaction getModelTransaction(String uid) {
		Transaction t = TransactionController.getTransaction(uid);
		
		if (t != null)
			return new MutableTransactionImpl(t);
		
		return null;
	}

	public Collection<MutableTransaction> getModelTransactions() {
		Collection<MutableTransaction> transactions = new LinkedList<MutableTransaction>();
		
		for (Transaction transaction : TransactionController.getTransactions()) {
			transactions.add(new MutableTransactionImpl(transaction));
		}
		
		return transactions;
	}

	public Collection<MutableTransaction> getModelTransactions(ImmutableSource s) {
		Collection<MutableTransaction> transactions = new LinkedList<MutableTransaction>();
		
		for (Transaction transaction : TransactionController.getTransactions(((ImmutableSourceImpl) s).getImpl())) {
			transactions.add(new MutableTransactionImpl(transaction));
		}
		
		return transactions;
	}

	public Collection<MutableType> getModelTypes() {
		Collection<MutableType> types = new LinkedList<MutableType>();
		
		for (Type type : TypeController.getTypes()) {
			types.add(new MutableTypeImpl(type));
		}
		
		return types;
	}

	public MutableTransaction createTransaction() {
        MutableTransactionImpl mutableTransactionImpl = new MutableTransactionImpl(ModelFactory.eINSTANCE.createTransaction());
        temporaryAddTransactionList.add(mutableTransactionImpl);
        return mutableTransactionImpl;
    }

    public MutableCategory createCategory() {
        MutableCategoryImpl mutableCategoryImpl = new MutableCategoryImpl(ModelFactory.eINSTANCE.createCategory());
        temporaryAddCategoryList.add(mutableCategoryImpl);
        return mutableCategoryImpl;
    }

    public MutableAccount createAccount() {
        MutableAccountImpl mutableAccountImpl = new MutableAccountImpl(ModelFactory.eINSTANCE.createAccount());
        temporaryAddAccountList.add(mutableAccountImpl);
        return mutableAccountImpl;
    }
    
    public MutableType createType() {
    	MutableTypeImpl mutableTypeImpl = new MutableTypeImpl();
    	temporaryAddTypeList.add(mutableTypeImpl);
    	return mutableTypeImpl;
    }

    public void saveChanges() throws ValidationException {
    	
    	//First we add the new data
    	for (MutableTypeImpl mutableTypeImpl : temporaryAddTypeList) {
			mutableTypeImpl.validate();
			TypeController.addType(mutableTypeImpl.getImpl());
		}
    	
        for(MutableAccountImpl mutableAccountImpl: temporaryAddAccountList) {
            mutableAccountImpl.validate();
            SourceController.addAccount(mutableAccountImpl.getImpl());
        }
        
        for(MutableCategoryImpl mutableCategoryImpl: temporaryAddCategoryList) {
            mutableCategoryImpl.validate();
            SourceController.addCategory(mutableCategoryImpl.getImpl());
        }
        
        Collection<Transaction> transactionsToCommit = new HashSet<Transaction>();
        for(MutableTransactionImpl mutableTransactionImpl: temporaryAddTransactionList) {
            mutableTransactionImpl.validate();
//            TransactionController.addTransaction(mutableTransactionImpl.getImpl());O
            transactionsToCommit.add(mutableTransactionImpl.getImpl());
        }
        
        
        //Then we remove the ones we need to.
        for (MutableTransactionImpl t : temporaryRemoveTransactionList) {
			TransactionController.deleteTransaction(t.getImpl());
		}
        
        for (MutableAccountImpl a : temporaryRemoveAccountList) {
			SourceController.deleteAccount(a.getImpl());
		}

        for (MutableCategoryImpl c : temporaryRemoveCategoryList) {
			SourceController.deleteCategory(c.getImpl());
		}

        for (MutableTypeImpl t : temporaryRemoveTypeList) {
			TypeController.deleteType(t.getImpl());
		}

        
        //Clean up
        
        TransactionsFrame.addToTransactionListModel(transactionsToCommit);
        
        MainFrame.getInstance().getAccountListPanel().updateContent();
        MainFrame.getInstance().getCategoryListPanel().updateContent();
//        TransactionsFrame.updateAllTransactionWindows();
    }

    public void rollbackChanges() {
        temporaryAddTransactionList.clear();
        temporaryAddCategoryList.clear();
        temporaryAddAccountList.clear();
        temporaryAddTypeList.clear();
    }
    
    public void rollbackTransaction(ImmutableTransaction t){
    	temporaryAddTransactionList.remove(t);
    }
    
    public void rollbackAccount(ImmutableAccount a){
    	temporaryAddAccountList.remove(a);
    }
    
    public void rollbackCategory(ImmutableCategory c){
    	temporaryAddCategoryList.remove(c);
    }
    
    public void rollbackType(ImmutableType t){
    	temporaryAddTypeList.remove(t);
    }

    public ImmutableCategory findCategoryByName(String name) {
        if (null == name)
        {
            return null;
        }

        for(ImmutableCategory immutableCategory: temporaryAddCategoryList) {
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

        for(ImmutableAccount immutableAccount: temporaryAddAccountList) {
            if(name.compareToIgnoreCase(immutableAccount.getName()) == 0)
                return immutableAccount;
        }

        return super.findAccountByName(name);
    }

    public Collection<ImmutableCategory> getCategories() {
        Collection<ImmutableCategory> categories = super.getCategories();
        categories.addAll(temporaryAddCategoryList);
        return categories;
    }

    public Collection<ImmutableAccount> getAccounts() {
        Collection<ImmutableAccount> accounts = super.getAccounts();
        accounts.addAll(temporaryAddAccountList);
        return accounts;
    }

    public Collection<ImmutableTransaction> getTransactions() {
        Collection<ImmutableTransaction> transactions = super.getTransactions();
        transactions.addAll(temporaryAddTransactionList);
        return transactions;
    }
    
    public Collection<ImmutableType> getTypes() {
    	Collection<ImmutableType> types = super.getTypes();
    	types.addAll(temporaryAddTypeList);
    	return types;
    }

    public Collection<ImmutableTransaction> getTransactionsForAccount(ImmutableAccount account) {
        Collection<ImmutableTransaction> matchingTransactions = super.getTransactions(account);
        
        for (ImmutableTransaction transaction : temporaryAddTransactionList) {
            if (transaction.getFrom() != null && transaction.getTo() != null && 
                    (transaction.getFrom().equals(account) || transaction.getTo().equals(account)))
                matchingTransactions.add(transaction);
        }
        
        return matchingTransactions;
    }
}
