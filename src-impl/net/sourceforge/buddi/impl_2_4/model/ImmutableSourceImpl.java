package net.sourceforge.buddi.impl_2_4.model;

import java.util.Date;

import net.sourceforge.buddi.api.model.ImmutableSource;

import org.homeunix.drummer.model.Source;

public class ImmutableSourceImpl implements ImmutableSource {

    private Source source;
    
    public Source getImpl()
    {
        return source;
    }
    
    public ImmutableSourceImpl(Source source)
    {
        this.source = source;
    }

    public String getName() {
        return source.getName();
    }

    public boolean isDeleted() {
        return source.isDeleted();
    }

    public Date getCreationDate() {
        return source.getCreationDate();
    }

    public int compareTo(ImmutableSource immutableSource) {
        return source.compareTo(((ImmutableSourceImpl) immutableSource).getImpl());
    }

    public boolean equals(Object obj) {
        if (false == obj instanceof ImmutableSourceImpl)
        {
            return false;
        }

        ImmutableSourceImpl immutableSourceImpl = (ImmutableSourceImpl)obj;
        
        return source.equals(immutableSourceImpl.getImpl());
    }
    
    @Override
    public String toString() {
    	return source.toString();
    }
}
