/*
 * Created on Jul 30, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.model.impl;

import org.homeunix.thecave.buddi.model.AccountType;
import org.homeunix.thecave.buddi.model.ModelObject;
import org.homeunix.thecave.buddi.model.beans.TypeBean;
import org.homeunix.thecave.buddi.model.exception.InvalidValueException;
import org.homeunix.thecave.buddi.model.prefs.PrefsModel;

public class AccountTypeImpl extends ModelObjectImpl implements AccountType {
	AccountTypeImpl(TypeBean type) throws InvalidValueException {
		super(type);
	}
	
	public boolean isCredit() {
		return getTypeBean().isCredit();
	}
	
	public void setCredit(boolean credit) {
		getTypeBean().setCredit(credit);
	}
	
	public String getName() {
		return PrefsModel.getInstance().getTranslator().get(getTypeBean().getName());
	}
	
	public void setName(String name) {
		getTypeBean().setName(name);
	}
	
	public boolean isExpanded() {
		return getTypeBean().isExpanded();
	}
	
	public void setExpanded(boolean isExpanded) {
		getTypeBean().setExpanded(isExpanded);
	}
	
	public TypeBean getTypeBean(){
		return (TypeBean) getBean();
	}
	
	@Override
	public int compareTo(ModelObject o) {
		if (o instanceof AccountTypeImpl){
			AccountTypeImpl t = (AccountTypeImpl) o;
			if (this.isCredit() != t.isCredit()){
				if (t.isCredit())
					return -1;
				return 1;
			}
		}
		return super.compareTo(o);
	}

	@Override
	public String toString() {
		return getName();
	}
}
