package net.sourceforge.buddi.impl_2_4.model;

import net.sourceforge.buddi.api.exception.ValidationException;
import net.sourceforge.buddi.api.model.ImmutableType;
import net.sourceforge.buddi.api.model.MutableType;

import org.homeunix.drummer.model.ModelFactory;
import org.homeunix.drummer.model.Type;

/**
 * @author wyatt
 * @deprecated
 */
public class MutableTypeImpl extends ImmutableTypeImpl implements MutableType {

	private Type type;

	public MutableTypeImpl() {
		this(ModelFactory.eINSTANCE.createType());
	}
	
	public MutableTypeImpl(Type type) {
		super(type);
		this.type = type;
	}
	
	public void setCredit(boolean newCredit) {
		type.setCredit(newCredit);
	}

	public void setName(String newName) {
		type.setName(newName);
	}

	public String getName() {
		return type.getName();
	}

	public boolean isCredit() {
		return type.isCredit();
	}

	public int compareTo(ImmutableType arg0) {
		return type.compareTo(((ImmutableTypeImpl)arg0).getImpl());
	}
	
    public void validate() throws ValidationException {
        if (type.getName() == null){
            throw new ValidationException("No Name specified for MutableType " + this);
        }
    }
}
