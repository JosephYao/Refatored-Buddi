package net.sourceforge.buddi.api.model;

public interface MutableCategory extends MutableSource, ImmutableCategory {

    public void setBudgetedAmount(long newBudgetedAmount);
    public void setIncome(boolean newIncome);
    public void setParent(ImmutableCategory newParent);
}