package net.sourceforge.buddi.impl_2_4.model;

import java.util.Date;

import net.sourceforge.buddi.api.exception.ValidationException;
import net.sourceforge.buddi.api.model.ImmutableSource;
import net.sourceforge.buddi.api.model.ImmutableTransaction;
import net.sourceforge.buddi.api.model.MutableTransaction;
import net.sourceforge.buddi.impl_2_4.exception.UnimplementedException;

import org.homeunix.drummer.model.Account;
import org.homeunix.drummer.model.Category;
import org.homeunix.drummer.model.ModelFactory;
import org.homeunix.drummer.model.Transaction;

public class MutableTransactionImpl extends ImmutableTransactionImpl implements MutableTransaction {

    private Transaction transaction;
    
    public Transaction getImpl() {
        return transaction;
    }

    public MutableTransactionImpl(){
    	this(ModelFactory.eINSTANCE.createTransaction());
    }
    
    public MutableTransactionImpl(Transaction transaction) {
    	super(transaction);
        this.transaction = transaction;
    }
    
    public void setAmount(long newAmount) {
        transaction.setAmount(newAmount);
    }

    public void setDescription(String newDescription) {
        transaction.setDescription(newDescription);
    }

    public void setDate(Date newDate) {
        transaction.setDate(newDate);
    }

    public void setNumber(String newNumber) {
        transaction.setNumber(newNumber);
    }

    public void setMemo(String newMemo) {
        transaction.setMemo(newMemo);
    }

    public void setCleared(boolean newCleared) {
        transaction.setCleared(newCleared);
    }

    public void setReconciled(boolean newReconciled) {
        transaction.setReconciled(newReconciled);
    }

    public void setFrom(ImmutableSource newFrom) {
        if (null == newFrom){
            transaction.setFrom(null);
        }
        else{
            if (newFrom instanceof MutableSourceImpl){
                transaction.setFrom(((MutableSourceImpl)newFrom).getImpl());
            }
            else if (newFrom instanceof ImmutableSourceImpl){
                transaction.setFrom(((ImmutableSourceImpl)newFrom).getImpl());
            }
            else{
                throw new UnimplementedException("ImmutableSource implementation of unknown type: " + newFrom.getClass().getName());
            }
        }
    }

    public void setTo(ImmutableSource newTo) {
        if (null == newTo){
            transaction.setTo(null);
        }
        else{
            if (newTo instanceof MutableSourceImpl){
                transaction.setTo(((MutableSourceImpl)newTo).getImpl());
            }
            else if (newTo instanceof ImmutableSourceImpl){
                transaction.setTo(((ImmutableSourceImpl)newTo).getImpl());
            }
            else{
                throw new UnimplementedException("ImmutableSource implementation of unknown type: " + newTo.getClass().getName());
            }
        }
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
        if (transaction != null && immutableSourceFrom == null)
        {
            if (transaction.getFrom() instanceof Category){
                immutableSourceFrom = new ImmutableCategoryImpl((Category)transaction.getFrom());
            }
            else if (transaction.getFrom() instanceof Account){
                immutableSourceFrom = new ImmutableAccountImpl((Account)transaction.getFrom());
            }
            else if (transaction.getFrom() == null){
            	immutableSourceFrom = null;
            }
            else {
                throw new UnimplementedException("transaction.getFrom() implementation of unknown type: " + transaction.getFrom().getClass().getName());
            }
        }
        return immutableSourceFrom;
    }

    private ImmutableSource immutableSourceTo = null;
    public ImmutableSource getTo() {
        if (transaction != null && immutableSourceTo == null){
            if (transaction.getTo() instanceof Category){
                immutableSourceTo = new ImmutableCategoryImpl((Category)transaction.getTo());
            }
            else if (transaction.getTo() instanceof Account){
                immutableSourceTo = new ImmutableAccountImpl((Account)transaction.getTo());
            }
            else if (transaction.getTo() == null){
            	immutableSourceTo = null;
            }
            else{
                throw new UnimplementedException("transaction.getTo() implementation of unknown type: " + transaction.getTo().getClass().getName());
            }
        }
        return immutableSourceTo;
    }

    public void validate() throws ValidationException {
        if (null == getDate())
        {
            throw new ValidationException("No Date specified for MutableTransaction " + this);
        }
        if (null == getDescription())
        {
            throw new ValidationException("No Description specified for MutableTransaction " + this);
        }
        if (null == getFrom())
        {
            throw new ValidationException("No From specified for MutableTransaction " + this);
        }
        if (null == getMemo())
        {
            throw new ValidationException("No Memo specified for MutableTransaction " + this);
        }
        if (null == getNumber())
        {
            throw new ValidationException("No Number specified for MutableTransaction " + this);
        }
        if (null == getTo())
        {
            throw new ValidationException("No To specified for MutableTransaction " + this);
        }
    }
    
    public int compareTo(ImmutableTransaction immutableTransaction) {
        return transaction.compareTo(((ImmutableTransactionImpl)immutableTransaction).getImpl());
    }

    public boolean equals(Object obj) {
        if (false == obj instanceof MutableTransactionImpl)
        {
            return false;
        }

        MutableTransactionImpl mutableTransactionImpl = (MutableTransactionImpl)obj;
        
        return 0 == compareTo(mutableTransactionImpl);
    }

}
