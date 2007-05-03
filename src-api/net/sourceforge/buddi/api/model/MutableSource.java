package net.sourceforge.buddi.api.model;

import java.util.Date;

public interface MutableSource extends ImmutableSource {

    public void setName(String newName);
    public void setDeleted(boolean newDeleted);
    public void setCreationDate(Date newCreationDate);
}