/*
 * Created on Jul 30, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.model;

import org.homeunix.thecave.buddi.model.beans.BudgetCategoryBean;
import org.homeunix.thecave.buddi.model.exception.DataModelProblemException;

public class BudgetCategory extends Source {	
	BudgetCategory(DataModel model, BudgetCategoryBean budgetCategory) {
		super(model, budgetCategory);
	}
	
	public BudgetCategory(DataModel model, String name, boolean isIncome) {
		this(model, new BudgetCategoryBean());
		
		this.setName(name);
		this.setIncome(isIncome);
	}
	
	public boolean isIncome() {
		return getBudgetCategory().isIncome();
	}
	public void setIncome(boolean income) {
		getBudgetCategory().setIncome(income);
	}
	public BudgetCategory getParent() {
		if (getBudgetCategory().getParent() == null)
			return null;
		return new BudgetCategory(getModel(), getBudgetCategory().getParent());
	}
	public void setParent(BudgetCategory parent) {
		if (parent == null){
			getBudgetCategory().setParent(null);
		}
		else {
			if (getBudgetCategory().equals(parent.getBudgetCategoryBean()))
				throw new DataModelProblemException("Cannot set a Budget Category's parent to itself", getModel());
			getBudgetCategory().setParent(parent.getBudgetCategoryBean());
		}
		getModel().setChanged();
	}
	public boolean isExpanded() {
		return getBudgetCategoryBean().isExpanded();
	}
	public void setExpanded(boolean expanded) {
		getBudgetCategoryBean().setExpanded(expanded);
	}
	public String getFullName(){
		if (this.getParent() != null)
			return this.getParent().getFullName() + " " + this.getName();
		
		return this.getName();
	}
	
	@Override
	public int compareTo(ModelObject arg0) {
		if (arg0 instanceof BudgetCategory){
			BudgetCategory c = (BudgetCategory) arg0;
			if (this.isIncome() != c.isIncome())
				return -1 * new Boolean(this.isIncome()).compareTo(new Boolean(c.isIncome()));
		}
		return super.compareTo(arg0);
	}
	
	BudgetCategoryBean getBudgetCategoryBean(){
		return getBudgetCategory();
	}
		
	private BudgetCategoryBean getBudgetCategory(){
		return (BudgetCategoryBean) getSourceBean();
	}
}
