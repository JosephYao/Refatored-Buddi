package net.sourceforge.buddi.api.model;

import java.util.Date;

public interface MutableAccount extends MutableSource, ImmutableAccount {

    public void setStartingBalance(long newStartingBalance);
    public void setCreditLimit(long newCreditLimit);
    public void setInterestRate(long newInterestRate);
    public void setDueDate(Date newDueDate);
    public void setAccountType(ImmutableType newAccountType);
}