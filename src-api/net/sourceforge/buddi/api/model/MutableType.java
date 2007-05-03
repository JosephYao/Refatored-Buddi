package net.sourceforge.buddi.api.model;

public interface MutableType extends ImmutableType {

    public void setName(String newName);
    public void setCredit(boolean newCredit);
}