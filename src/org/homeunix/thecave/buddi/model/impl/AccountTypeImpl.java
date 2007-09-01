/*
 * Created on Jul 29, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.model.impl;

import org.homeunix.thecave.buddi.model.AccountType;
import org.homeunix.thecave.buddi.model.ModelObject;
import org.homeunix.thecave.buddi.plugin.api.util.TextFormatter;

/**
 * Default implementation of an AccountType.  You should not create this object directly; 
 * instead, please use the ModelFactory to create it, as this will ensure that all
 * required fields are correctly set.
 * @author wyatt
 *
 */
public class AccountTypeImpl extends ModelObjectImpl implements AccountType {
	private String name;
	private boolean credit;
	private boolean isExpanded;

	public boolean isCredit() {
		return credit;
	}
	public void setCredit(boolean credit) {
		this.credit = credit;
		setChanged();
	}
	public String getName() {
		return TextFormatter.getTranslation(name);
	}
	public void setName(String name) {
		this.name = name;
		setChanged();
	}
	public boolean isExpanded() {
		return isExpanded;
	}
	public void setExpanded(boolean isExpanded) {
		this.isExpanded = isExpanded;
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
}
