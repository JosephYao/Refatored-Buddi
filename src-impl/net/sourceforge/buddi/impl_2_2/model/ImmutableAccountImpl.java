package net.sourceforge.buddi.impl_2_2.model;

import java.util.Date;

import net.sourceforge.buddi.api.model.ImmutableAccount;
import net.sourceforge.buddi.api.model.ImmutableType;
import net.sourceforge.buddi.impl_2_2.exception.UnimplementedException;

import org.homeunix.drummer.model.Account;

public class ImmutableAccountImpl extends ImmutableSourceImpl implements ImmutableAccount {

    private Account account;
    
    public Account getImpl()
    {
        return account;
    }
    
    public ImmutableAccountImpl(Account account)
    {
        super(account);

        this.account = account;
    }

    public long getBalance() {
        return account.getBalance();
    }

    public long getStartingBalance() {
        return account.getStartingBalance();
    }

    public long getCreditLimit() {
        return account.getCreditLimit();
    }

    public long getInterestRate() {
        return account.getInterestRate();
    }

    public Date getDueDate() {
        throw new UnimplementedException();
    }

    public boolean isCredit() {
        return account.isCredit();
    }
    
    private ImmutableType immutableTypeAccountType = null;
    public ImmutableType getAccountType() {
        if (null == immutableTypeAccountType)
        {
            immutableTypeAccountType = new ImmutableTypeImpl(account.getAccountType());
        }
        return immutableTypeAccountType;
    }

    public int compareTo(ImmutableAccount immutableAccount) {
        return account.compareTo(((ImmutableAccountImpl)immutableAccount).getImpl());
    }

    public boolean equals(Object obj) {
        if (false == obj instanceof ImmutableAccountImpl)
        {
            return false;
        }

        ImmutableAccountImpl immutableAccountImpl = (ImmutableAccountImpl)obj;
        
        return 0 == compareTo(immutableAccountImpl);
    }

}
