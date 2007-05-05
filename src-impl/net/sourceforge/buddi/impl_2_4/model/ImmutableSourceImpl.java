package net.sourceforge.buddi.impl_2_4.model;

import java.util.Date;

import net.sourceforge.buddi.api.model.ImmutableSource;
import net.sourceforge.buddi.impl_2_2.exception.UnimplementedException;

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
        throw new UnimplementedException();
    }

    public boolean equals(Object obj) {
        if (false == obj instanceof ImmutableSourceImpl)
        {
            return false;
        }

        ImmutableSourceImpl immutableSourceImpl = (ImmutableSourceImpl)obj;
        
        return source.equals(immutableSourceImpl.getImpl());
    }
}
