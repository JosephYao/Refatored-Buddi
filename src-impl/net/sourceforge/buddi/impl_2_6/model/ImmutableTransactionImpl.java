package net.sourceforge.buddi.impl_2_6.model;

import java.util.Date;

import net.sourceforge.buddi.api.model.ImmutableSource;
import net.sourceforge.buddi.api.model.ImmutableTransaction;

import org.homeunix.drummer.model.Account;
import org.homeunix.drummer.model.Category;
import org.homeunix.drummer.model.Transaction;
import org.homeunix.thecave.moss.util.Log;

public class ImmutableTransactionImpl implements ImmutableTransaction {

    private Transaction transaction;
    
    public Transaction getImpl() {
        return transaction;
    }

    public ImmutableTransactionImpl(Transaction transaction) {
        this.transaction = transaction;
    }
    
    public long getAmount() {
        return transaction.getAmount();
    }

    public String getDescription() {
        return transaction.getDescription();
    }

    public Date getDate() {
        return transaction.getDate();
    }

    public String getNumber() {
        return transaction.getNumber();
    }

    public String getMemo() {
        return transaction.getMemo();
    }

    public long getBalanceFrom() {
        return transaction.getBalanceFrom();
    }

    public long getBalanceTo() {
        return transaction.getBalanceTo();
    }

    public boolean isScheduled() {
        return transaction.isScheduled();
    }

    public boolean isCleared() {
        return transaction.isCleared();
    }

    public boolean isReconciled() {
        return transaction.isReconciled();
    }
    
    public String getUID(){
    	return transaction.getUID();
    }

    private ImmutableSource immutableSourceFrom = null;
    public ImmutableSource getFrom() {
        if (transaction != null && immutableSourceFrom == null){
        	if (transaction.getFrom() instanceof Category)
        		immutableSourceFrom = new ImmutableCategoryImpl((Category) transaction.getFrom());
        	else if (transaction.getFrom() instanceof Account)
        		immutableSourceFrom = new ImmutableAccountImpl((Account) transaction.getFrom());
        	else 
        		Log.critical("From not of type Account or Category!");
        }
        return immutableSourceFrom;
    }

    private ImmutableSource immutableSourceTo = null;
    public ImmutableSource getTo() {
        if (transaction != null && immutableSourceTo == null){
        	if (transaction.getTo() instanceof Category)
        		immutableSourceTo = new ImmutableCategoryImpl((Category) transaction.getTo());
        	else if (transaction.getTo() instanceof Account)
        		immutableSourceTo = new ImmutableAccountImpl((Account) transaction.getTo());
        	else
        		Log.critical("To not of type Account or Category!");
        }
        return immutableSourceTo;
    }

    public int compareTo(ImmutableTransaction immutableTransaction) {
        return transaction.compareTo(((ImmutableTransactionImpl)immutableTransaction).getImpl());
    }

    public boolean equals(Object obj) {
        if (false == obj instanceof ImmutableTransactionImpl)
        {
            return false;
        }

        ImmutableTransactionImpl immutableTransactionImpl = (ImmutableTransactionImpl)obj;
        
        return 0 == compareTo(immutableTransactionImpl);
    }

    @Override
    public String toString() {
    	return transaction.toString();
    }
}
