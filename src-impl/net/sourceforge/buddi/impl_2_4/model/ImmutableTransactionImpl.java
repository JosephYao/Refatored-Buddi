package net.sourceforge.buddi.impl_2_4.model;

import java.util.Date;

import net.sourceforge.buddi.api.model.ImmutableSource;
import net.sourceforge.buddi.api.model.ImmutableTransaction;

import org.homeunix.drummer.model.Transaction;

public class ImmutableTransactionImpl implements ImmutableTransaction {

    private Transaction transaction;
    
    public Transaction getImpl() {
        return transaction;
    }

    public ImmutableTransactionImpl(Transaction transaction) {
        this.transaction = transaction;
    }
    
    public ImmutableTransactionImpl() {
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

    private ImmutableSource immutableSourceFrom = null;
    public ImmutableSource getFrom() {
        if (null == immutableSourceFrom)
        {
            immutableSourceFrom = new ImmutableSourceImpl(transaction.getFrom());
        }
        return immutableSourceFrom;
    }

    private ImmutableSource immutableSourceTo = null;
    public ImmutableSource getTo() {
        if (null == immutableSourceTo)
        {
            immutableSourceTo = new ImmutableSourceImpl(transaction.getTo());
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

}
