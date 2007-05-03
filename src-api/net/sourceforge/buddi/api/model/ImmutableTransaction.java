package net.sourceforge.buddi.api.model;

import java.util.Date;

public interface ImmutableTransaction {
    public long getAmount();
    public String getDescription();
    public Date getDate();
    public String getNumber();
    public String getMemo();
    public long getBalanceFrom();
    public long getBalanceTo();
    public boolean isScheduled();
    public boolean isCleared();
    public boolean isReconciled();
    public ImmutableSource getFrom();
    public ImmutableSource getTo();

    public int compareTo(ImmutableTransaction transaction);
}