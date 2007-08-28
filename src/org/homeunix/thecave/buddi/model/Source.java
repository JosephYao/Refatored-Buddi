/*
 * Created on Jul 30, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.model;

import java.util.Date;

import org.homeunix.thecave.buddi.model.beans.SourceBean;
import org.homeunix.thecave.buddi.model.exception.DataModelProblemException;
import org.homeunix.thecave.buddi.model.prefs.PrefsModel;

public abstract class Source extends ModelObjectImpl {

	Source(DataModel model, SourceBean source) {
		super(model, source);
	}

	public boolean isDeleted() {
		return getSourceBean().isDeleted();
	}
	public void setDeleted(Boolean deleted) {
		getSourceBean().setDeleted(deleted);
		getModel().setChanged();
	}
	public String getName() {
		return PrefsModel.getInstance().getTranslator().get(getSourceBean().getName());
	}
	public void setName(String name) {
		if (name == null || name.length() == 0)
			throw new DataModelProblemException("Source name cannot be null or empty", getModel());
		if (this instanceof Account){
			for (Account a : getModel().getAccounts()) {
				if (!this.equals(a) && a.getName().equals(name))
					throw new DataModelProblemException("Account name must be unique within the model", getModel());
			}
		}
		else if (this instanceof BudgetCategory){
			for (BudgetCategory bc : getModel().getBudgetCategories()) {
				if (!this.equals(bc) && bc.getName().equals(name) && bc.getParent().equals(((BudgetCategory) this).getParent()))
					throw new DataModelProblemException("Category name must be unique within the model", getModel());
			}
		}

		getSourceBean().setName(name);
		getModel().setChanged();
	}
	public String getNotes() {
		return getSourceBean().getNotes();
	}
	public void setNotes(String notes) {
		getSourceBean().setNotes(notes);
		getModel().setChanged();
	}


	@Override
	public int compareTo(ModelObject arg0) {
		if (arg0 instanceof Source)
			return this.getFullName().compareTo(((Source) arg0).getFullName());
		return super.compareTo(arg0);
	}

	/**
	 * Returns the earliest date associated with this source.  This is generated
	 * on the fly by iterating through all transactions and scheduled transactions.
	 * Thus, this is an O(n) operation based on transactions.  Try to avoid
	 * using this repeatedly for time-sensitive algorithms (or run it once and 
	 * cache the result).  
	 * @return
	 */
	public Date getEarliestDate() {
		Date earliestDate = new Date();
		for (Transaction t : getModel().getTransactions(this)) {
			if (t.getDate().before(earliestDate))
				earliestDate = t.getDate();
		}
		for (ScheduledTransaction s : getModel().getScheduledTransactions()) {
			if (s.getStartDate().before(earliestDate))
				earliestDate = s.getStartDate();
		}
		return earliestDate;
	}

	SourceBean getSourceBean(){
		return (SourceBean) getBean();
	}

	@Override
	public String toString() {
		return getFullName();
	}

	public abstract String getFullName();
}
