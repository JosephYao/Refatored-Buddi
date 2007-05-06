package net.sourceforge.buddi.api.model;

import java.util.Date;

public interface ImmutableAccount extends ImmutableSource, Comparable<ImmutableSource> {
    public long getBalance();
    public long getStartingBalance();
    public long getCreditLimit();
    public long getInterestRate();
    public Date getDueDate();
    public ImmutableType getAccountType();
    public boolean isCredit();
}