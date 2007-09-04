/*
 * Created on Aug 12, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.plugin.api.model.impl;

import java.util.Date;
import java.util.List;

import org.homeunix.thecave.buddi.model.Account;
import org.homeunix.thecave.buddi.model.AccountType;
import org.homeunix.thecave.buddi.model.BudgetCategory;
import org.homeunix.thecave.buddi.model.Document;
import org.homeunix.thecave.buddi.model.Source;
import org.homeunix.thecave.buddi.model.Transaction;
import org.homeunix.thecave.buddi.model.impl.WrapperLists;
import org.homeunix.thecave.buddi.plugin.api.model.ImmutableAccount;
import org.homeunix.thecave.buddi.plugin.api.model.ImmutableAccountType;
import org.homeunix.thecave.buddi.plugin.api.model.ImmutableBudgetCategory;
import org.homeunix.thecave.buddi.plugin.api.model.ImmutableDocument;
import org.homeunix.thecave.buddi.plugin.api.model.ImmutableSource;
import org.homeunix.thecave.buddi.plugin.api.model.ImmutableTransaction;

public class ImmutableDocumentImpl extends ImmutableModelObjectImpl implements ImmutableDocument {
	private final Document model;
	
	public ImmutableDocumentImpl(Document model) {
		super(model);
		this.model = model;
	}
	
	
	public ImmutableAccount getAccount(String name) {
		if (getModel().getAccount(name) == null)
			return null;
		return new MutableAccountImpl(getModel().getAccount(name));
	}
	
	public List<ImmutableAccount> getAccounts(){
		return new WrapperLists.ImmutableObjectWrapperList<ImmutableAccount, Account>(getModel(), getModel().getAccounts());
	}
	
	public List<ImmutableBudgetCategory> getBudgetCategories(){
		return new WrapperLists.ImmutableObjectWrapperList<ImmutableBudgetCategory, BudgetCategory>(getModel(), getModel().getBudgetCategories());
	}
	
	public ImmutableBudgetCategory getBudgetCategory(String fullName) {
		if (getModel().getBudgetCategory(fullName) == null)
			return null;
		return new MutableBudgetCategoryImpl(getModel().getBudgetCategory(fullName));
	}
	
	public Document getModel(){
		return model;
	}
	
	public List<ImmutableTransaction> getTransactions(){
		return new WrapperLists.ImmutableObjectWrapperList<ImmutableTransaction, Transaction>(getModel(), getModel().getTransactions());
	}
	
	public List<ImmutableTransaction> getTransactions(Date startDate, Date endDate){
		return new WrapperLists.ImmutableObjectWrapperList<ImmutableTransaction, Transaction>(getModel(), getModel().getTransactions(startDate, endDate));
	}
	
	public List<ImmutableTransaction> getTransactions(ImmutableSource source){
		return new WrapperLists.ImmutableObjectWrapperList<ImmutableTransaction, Transaction>(getModel(), getModel().getTransactions((Source) source.getRaw()));
	}
	
	public List<ImmutableTransaction> getTransactions(ImmutableSource source, Date startDate, Date endDate){
		return new WrapperLists.ImmutableObjectWrapperList<ImmutableTransaction, Transaction>(getModel(), getModel().getTransactions((Source) source.getRaw(), startDate, endDate));
	}
	
	public ImmutableAccountType getType(String name) {
		if (getModel().getAccountType(name) == null)
			return null;
		return new MutableAccountTypeImpl(getModel().getAccountType(name));
	}
	
	public List<ImmutableAccountType> getAccountTypes(){
		return new WrapperLists.ImmutableObjectWrapperList<ImmutableAccountType, AccountType>(getModel(), getModel().getAccountTypes());		
	}
	
	@Override
	public String toString() {
		return getUid();
	}
}
