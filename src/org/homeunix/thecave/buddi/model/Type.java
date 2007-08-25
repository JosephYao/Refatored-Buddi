/*
 * Created on Jul 30, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.model;

import org.homeunix.thecave.buddi.model.beans.TypeBean;
import org.homeunix.thecave.buddi.model.exception.DataModelProblemException;
import org.homeunix.thecave.buddi.model.prefs.PrefsModel;

public class Type extends ModelObjectImpl {
	Type(DataModel model, TypeBean type) {
		super(model, type);
	}
	
	public Type(DataModel model, String name, boolean credit) {
		this(model, new TypeBean());
		
		setName(name);
		setCredit(credit);
	}
	
	public boolean isCredit() {
		return getTypeBean().isCredit();
	}
	public void setCredit(boolean credit) {
		getTypeBean().setCredit(credit);
		getModel().setChanged();
	}
	public String getName() {
		return PrefsModel.getInstance().getTranslator().get(getTypeBean().getName());
	}
	public void setName(String name) {
		if (name == null || name.length() == 0)
			throw new DataModelProblemException("Type name cannot be null or empty", getModel());
		for (Type t : getModel().getTypes()) {
			if (!this.equals(t) && t.getName().equals(name))
				throw new DataModelProblemException("Type name must be unique within the model", getModel());
		}
		getTypeBean().setName(name);
		getModel().setChanged();
	}
	public boolean isExpanded() {
		return getTypeBean().isExpanded();
	}
	public void setExpanded(boolean isExpanded) {
		getTypeBean().setExpanded(isExpanded);
	}
	
	TypeBean getTypeBean(){
		return (TypeBean) getBean();
	}
	
	@Override
	public int compareTo(ModelObject o) {
		if (o instanceof Type){
			Type t = (Type) o;
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
		return getName() + " (0x" + getTypeBean().getUid() + ")";
	}
}
