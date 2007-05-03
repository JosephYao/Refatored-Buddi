package net.sourceforge.buddi.api.model;

import java.util.Collection;

public interface ImmutableCategory extends ImmutableSource {
    public long getBudgetedAmount();
    public boolean isIncome();
    public ImmutableCategory getParent();
    public Collection<ImmutableSource> getChildren();
    
    public int compareTo(ImmutableSource source);
}