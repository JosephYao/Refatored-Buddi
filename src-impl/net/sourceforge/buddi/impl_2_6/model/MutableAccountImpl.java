package net.sourceforge.buddi.impl_2_6.model;

import java.util.Date;

import net.sourceforge.buddi.api.exception.ValidationException;
import net.sourceforge.buddi.api.model.ImmutableType;
import net.sourceforge.buddi.api.model.MutableAccount;
import net.sourceforge.buddi.impl_2_4.exception.UnimplementedException;

import org.homeunix.drummer.model.Account;

public class MutableAccountImpl extends MutableSourceImpl implements MutableAccount {

    private Account account;
    
    public Account getImpl() {
        return account;
    }

    public MutableAccountImpl(Account account) {
        super(account);
        this.account = account;
    }
    
    public long getCreditLimit() {
        return account.getCreditLimit();
    }
    public void setCreditLimit(long creditLimit) {
        account.setCreditLimit(creditLimit);
    }
    public Date getDueDate() {
        throw new UnimplementedException();
    }
    public void setDueDate(Date dueDate) {
        throw new UnimplementedException();
    }
    public long getInterestRate() {
        return account.getInterestRate();
    }
    public void setInterestRate(long interestRate) {
        account.setInterestRate(interestRate);
    }
    public long getStartingBalance() {
        return account.getStartingBalance();
    }
    public void setStartingBalance(long startingBalance) {
        account.setStartingBalance(startingBalance);
    }
    
    public long getBalance() {
        return account.getBalance();
    }
    public boolean isCredit() {
        return account.isCredit();
    }
    
    private ImmutableType immutableTypeAccountType = null;
    public ImmutableType getAccountType() {
        if (null == immutableTypeAccountType)
        {
            if (null == account.getAccountType())
            {
                immutableTypeAccountType = null;
            }
            else
            {
                immutableTypeAccountType = new ImmutableTypeImpl(account.getAccountType());
            }
        }
        return immutableTypeAccountType;
    }
    public void setAccountType(ImmutableType accountType) {
        this.immutableTypeAccountType = accountType;
        account.setAccountType(((ImmutableTypeImpl) accountType).getImpl());
    }


    public void validate() throws ValidationException {
        super.validate();

//        if (null == account.getDueDate())
//        {
//            throw new ValidationException("No DueDate specified for MutableAccount " + this);
//        }

        if (account.getAccountType() == null){
            throw new ValidationException("No AccountType specified for MutableAccount " + this);
        }
    }
    
    public boolean equals(Object obj) {
        if (false == obj instanceof MutableAccountImpl)
        {
            return false;
        }

        MutableAccountImpl mutableAccountImpl = (MutableAccountImpl)obj;
        
        return 0 == compareTo(mutableAccountImpl);
    }

}
