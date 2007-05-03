package net.sourceforge.buddi.api.model;

import java.util.Date;

public interface MutableTransaction extends ImmutableTransaction {

    public void setAmount(long newAmount);
    public void setDescription(String newDescription);
    public void setDate(Date newDate);
    public void setNumber(String newNumber);
    public void setMemo(String newMemo);
    public void setCleared(boolean newCleared);
    public void setReconciled(boolean newReconciled);
    public void setFrom(ImmutableSource newFrom);
    public void setTo(ImmutableSource newTo);
}