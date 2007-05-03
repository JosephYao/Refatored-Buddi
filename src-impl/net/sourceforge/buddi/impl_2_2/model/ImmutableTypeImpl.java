package net.sourceforge.buddi.impl_2_2.model;

import net.sourceforge.buddi.api.model.ImmutableType;

import org.homeunix.drummer.model.Type;

public class ImmutableTypeImpl implements ImmutableType {

    private Type type;
    
    public Type getImpl()
    {
        return type;
    }
    
    public ImmutableTypeImpl(Type type)
    {
        this.type = type;
    }

    public String getName() {
        return type.getName();
    }

    public boolean isCredit() {
        return type.isCredit();
    }

    public int compareTo(ImmutableType immutableType) {
        return type.compareTo(((ImmutableTypeImpl)immutableType).getImpl());
    }

    public boolean equals(Object obj) {
        if (false == obj instanceof ImmutableTypeImpl)
        {
            return false;
        }

        ImmutableTypeImpl immutableTypeImpl = (ImmutableTypeImpl)obj;
        
        return 0 == compareTo(immutableTypeImpl);
    }

}
