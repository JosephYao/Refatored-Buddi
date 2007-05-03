package net.sourceforge.buddi.api.model;

import java.util.Date;

public interface ImmutableSource {
    public String getName();
    public boolean isDeleted();
    public Date getCreationDate();
    public int compareTo(ImmutableSource source);
}