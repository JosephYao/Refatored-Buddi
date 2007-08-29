/*
 * Created on Aug 4, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.model.impl;

import java.util.List;

import org.homeunix.thecave.buddi.model.Account;
import org.homeunix.thecave.buddi.model.AccountType;
import org.homeunix.thecave.buddi.model.BudgetCategory;
import org.homeunix.thecave.buddi.model.Document;
import org.homeunix.thecave.buddi.model.ScheduledTransaction;
import org.homeunix.thecave.buddi.model.Transaction;
import org.homeunix.thecave.buddi.model.beans.AccountBean;
import org.homeunix.thecave.buddi.model.beans.BudgetCategoryBean;
import org.homeunix.thecave.buddi.model.beans.ScheduledTransactionBean;
import org.homeunix.thecave.buddi.model.beans.TransactionBean;
import org.homeunix.thecave.buddi.model.beans.TypeBean;
import org.homeunix.thecave.buddi.model.exception.ModelException;
import org.homeunix.thecave.buddi.plugin.api.model.ImmutableAccount;
import org.homeunix.thecave.buddi.plugin.api.model.ImmutableModelObject;
import org.homeunix.thecave.buddi.plugin.api.model.impl.ImmutableAccountImpl;
import org.homeunix.thecave.buddi.plugin.api.model.impl.MutableAccountImpl;
import org.homeunix.thecave.buddi.plugin.api.model.impl.MutableBudgetCategoryImpl;
import org.homeunix.thecave.buddi.plugin.api.model.impl.MutableTransactionImpl;
import org.homeunix.thecave.buddi.plugin.api.model.impl.MutableTypeImpl;
import org.homeunix.thecave.moss.data.list.WrapperList;
import org.homeunix.thecave.moss.model.DocumentChangeEvent;
import org.homeunix.thecave.moss.model.DocumentChangeListener;

public class WrapperLists {

	private WrapperLists() {}

	private abstract static class BuddiWrapperList<T, W> extends WrapperList<T, W> {
		private final Document model;

		public BuddiWrapperList(Document model, List<W> wrappedList, boolean sorted) {
			super(wrappedList, sorted);
			this.model = model;

			model.addDocumentChangeListener(new DocumentChangeListener(){
				public void documentChange(DocumentChangeEvent event) {
					updateWrapperList();
				}
			});
		}

		public Document getDataModel(){
			return model;
		}
	}

	public static class WrapperAccountList extends WrapperList<Account, AccountBean>{
		public WrapperAccountList(Document model, List<AccountBean> wrappedList) {
			super(wrappedList, true);
		}

		@Override
		public AccountBean getWrappedObject(Account object) {
			return object.getAccountBean();
		}

		@Override
		public Account getWrapperObject(AccountBean object) {
			try {
				return new AccountImpl(object);
			}
			catch (ModelException me){
				return null;
			}
		}
	}

	public static class WrapperTypeList extends BuddiWrapperList<AccountType, TypeBean>{
		public WrapperTypeList(Document model, List<TypeBean> wrappedList) {
			super(model, wrappedList, true);
		}

		@Override
		public TypeBean getWrappedObject(AccountType object) {
			return object.getTypeBean();
		}

		@Override
		public AccountType getWrapperObject(TypeBean object) {
			try {
				return new AccountTypeImpl(object);
			}
			catch (ModelException me){
				return null;
			}
		}
	}

	public static class WrapperBudgetCategoryList extends BuddiWrapperList<BudgetCategory, BudgetCategoryBean>{
		public WrapperBudgetCategoryList(Document model, List<BudgetCategoryBean> wrappedList) {
			super(model, wrappedList, true);
		}

		@Override
		public BudgetCategoryBean getWrappedObject(BudgetCategory object) {
			return object.getBudgetCategoryBean();
		}

		@Override
		public BudgetCategory getWrapperObject(BudgetCategoryBean object) {
			try {
				return new BudgetCategoryImpl(object);
			}
			catch (ModelException me){
				return null;
			}
		}
	}

//	public static class WrapperBudgetPeriodList extends BuddiWrapperList<BudgetPeriod, BudgetPeriodBean>{
//	public WrapperBudgetPeriodList(DataModel model, List<BudgetPeriodBean> wrappedList) {
//	super(model, wrappedList, true);
//	}

//	@Override
//	public BudgetPeriodBean getWrappedObject(BudgetPeriod object) {
//	return object.getBudgetPeriodBean();
//	}

//	@Override
//	public BudgetPeriod getWrapperObject(BudgetPeriodBean object) {
//	return new BudgetPeriod(getDataModel(), object);
//	}
//	}

	public static class WrapperScheduledTransactionList extends BuddiWrapperList<ScheduledTransaction, ScheduledTransactionBean>{
		public WrapperScheduledTransactionList(Document model, List<ScheduledTransactionBean> wrappedList) {
			super(model, wrappedList, true);
		}

		@Override
		public ScheduledTransactionBean getWrappedObject(ScheduledTransaction object) {
			return object.getScheduledTransactionBean();
		}

		@Override
		public ScheduledTransaction getWrapperObject(ScheduledTransactionBean object) {
			try {
				return new ScheduledTransactionImpl(object);
			}
			catch (ModelException me){
				return null;
			}
		}
	}

	public static class WrapperTransactionList extends BuddiWrapperList<Transaction, TransactionBean>{
		public WrapperTransactionList(Document model, List<TransactionBean> wrappedList) {
			super(model, wrappedList, true);
		}

		@Override
		public TransactionBean getWrappedObject(Transaction object) {
			return object.getTransactionBean();
		}

		@Override
		public Transaction getWrapperObject(TransactionBean object) {
			try {
				return new TransactionImpl(object);
			}
			catch (ModelException me){
				return null;
			}
		}
	}

	public static class ImmutableAccountList extends BuddiWrapperList<ImmutableAccount, Account> {
		public ImmutableAccountList(Document model, List<Account> wrappedList) {
			super(model, wrappedList, true);
		}

		@Override
		public Account getWrappedObject(ImmutableAccount object) {
			return (Account) object.getRaw();
		}

		@Override
		public ImmutableAccount getWrapperObject(Account object) {
			return new ImmutableAccountImpl(object);
		}
	}

	public static class ImmutableObjectWrapperList<T extends ImmutableModelObject, W extends ModelObjectImpl> extends BuddiWrapperList<T, W> {
		public ImmutableObjectWrapperList(Document model, List<W> wrappedList) {
			super(model, wrappedList, true);
		}

		@SuppressWarnings("unchecked")
		@Override
		public W getWrappedObject(T object) {
			return (W) object.getRaw();
		}

		@SuppressWarnings("unchecked")
		@Override
		public T getWrapperObject(W object) {
			if (object instanceof Account)
				return (T) new MutableAccountImpl((Account) object);
			if (object instanceof BudgetCategory)
				return (T) new MutableBudgetCategoryImpl((BudgetCategory) object);
//			if (object instanceof BudgetPeriod)
//			return (T) new ImmutableBudgetPeriodImpl((BudgetPeriod) object);
			if (object instanceof Transaction)
				return (T) new MutableTransactionImpl((Transaction) object);
			if (object instanceof AccountType)
				return (T) new MutableTypeImpl((AccountType) object);

			//Catch all
			return null;
		}
	}
}
