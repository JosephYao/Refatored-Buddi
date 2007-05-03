package net.sourceforge.buddi.impl_2_2.model;

import java.util.Date;

import net.sourceforge.buddi.api.exception.ValidationException;
import net.sourceforge.buddi.api.model.ImmutableSource;
import net.sourceforge.buddi.api.model.MutableSource;
import net.sourceforge.buddi.impl_2_2.exception.UnimplementedException;

import org.homeunix.drummer.model.Source;

public class MutableSourceImpl implements MutableSource {

    private Source source;
    
    public Source getImpl() {
        return source;
    }

    public MutableSourceImpl(Source source) {
        this.source = source;
    }

    public Date getCreationDate() {
        return source.getCreationDate();
    }

    public void setCreationDate(Date creationDate) {
        source.setCreationDate(creationDate);
    }

    public boolean isDeleted() {
        return source.isDeleted();
    }

    public void setDeleted(boolean deleted) {
        source.setDeleted(deleted);
    }

    public String getName() {
        return source.getName();
    }

    public void setName(String name) {
        source.setName(name);
    }

    public void validate() throws ValidationException {
        if (null == source.getCreationDate())
        {
            throw new ValidationException("No CreationDate specified for MutableSource " + this);
        }
        if (null == source.getName())
        {
            throw new ValidationException("No Name specified for MutableSource " + this);
        }
    }
    
    public int compareTo(ImmutableSource source) {
        throw new UnimplementedException();
    }

    public boolean equals(Object obj) {
        if (false == obj instanceof MutableSourceImpl)
        {
            return false;
        }

        MutableSourceImpl mutableSourceImpl = (MutableSourceImpl)obj;
        
        return source.equals(mutableSourceImpl.getImpl());
    }
}
