package net.sourceforge.buddi.api.model;

import java.util.Date;

public interface ImmutableSource extends Comparable<ImmutableSource> {
    public String getName();
    public boolean isDeleted();
    public Date getCreationDate();
}