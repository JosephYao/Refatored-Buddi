package net.sourceforge.buddi.api.model;

public interface ImmutableType {

    public String getName();
    public boolean isCredit();

    public int compareTo(ImmutableType type);

}