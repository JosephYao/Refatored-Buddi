/*
 * Created on Aug 4, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.model;

import java.util.List;

import org.homeunix.thecave.buddi.model.beans.AccountBean;
import org.homeunix.thecave.buddi.model.beans.BudgetCategoryBean;
import org.homeunix.thecave.buddi.model.beans.BudgetPeriodBean;
import org.homeunix.thecave.buddi.model.beans.ScheduledTransactionBean;
import org.homeunix.thecave.buddi.model.beans.TransactionBean;
import org.homeunix.thecave.buddi.model.beans.TypeBean;
import org.homeunix.thecave.buddi.plugin.api.model.ImmutableAccount;
import org.homeunix.thecave.buddi.plugin.api.model.ImmutableModelObject;
import org.homeunix.thecave.buddi.plugin.api.model.impl.ImmutableAccountImpl;
import org.homeunix.thecave.buddi.plugin.api.model.impl.ImmutableBudgetCategoryImpl;
import org.homeunix.thecave.buddi.plugin.api.model.impl.ImmutableBudgetPeriodImpl;
import org.homeunix.thecave.buddi.plugin.api.model.impl.ImmutableTransactionImpl;
import org.homeunix.thecave.buddi.plugin.api.model.impl.ImmutableTypeImpl;
import org.homeunix.thecave.moss.data.list.WrapperList;
import org.homeunix.thecave.moss.model.DocumentChangeEvent;
import org.homeunix.thecave.moss.model.DocumentChangeListener;

public class WrapperLists {

	private WrapperLists() {}
	
	private abstract static class BuddiWrapperList<T, W> extends WrapperList<T, W> {
		private final DataModel model;
		
		public BuddiWrapperList(DataModel model, List<W> wrappedList, boolean sorted) {
			super(wrappedList, sorted);
			this.model = model;
			
			model.addDocumentChangeListener(new DocumentChangeListener(){
				public void documentChange(DocumentChangeEvent event) {
					updateWrapperList();
				}
			});
		}
		
		public DataModel getDataModel(){
			return model;
		}
	}
	
	public static class WrapperAccountList extends WrapperList<Account, AccountBean>{
		private final DataModel model;
		
		public WrapperAccountList(DataModel model, List<AccountBean> wrappedList) {
			super(wrappedList, true);
			this.model = model;
		}
		
		@Override
		public AccountBean getWrappedObject(Account object) {
			return object.getAccountBean();
		}

		@Override
		public Account getWrapperObject(AccountBean object) {
			return new Account(model, object);
		}
	}

	public static class WrapperTypeList extends BuddiWrapperList<Type, TypeBean>{
		public WrapperTypeList(DataModel model, List<TypeBean> wrappedList) {
			super(model, wrappedList, true);
		}
		
		@Override
		public TypeBean getWrappedObject(Type object) {
			return object.getTypeBean();
		}

		@Override
		public Type getWrapperObject(TypeBean object) {
			return new Type(getDataModel(), object);
		}
	}

	public static class WrapperBudgetCategoryList extends BuddiWrapperList<BudgetCategory, BudgetCategoryBean>{
		public WrapperBudgetCategoryList(DataModel model, List<BudgetCategoryBean> wrappedList) {
			super(model, wrappedList, true);
		}
		
		@Override
		public BudgetCategoryBean getWrappedObject(BudgetCategory object) {
			return object.getBudgetCategoryBean();
		}

		@Override
		public BudgetCategory getWrapperObject(BudgetCategoryBean object) {
			return new BudgetCategory(getDataModel(), object);
		}
	}

	public static class WrapperBudgetPeriodList extends BuddiWrapperList<BudgetPeriod, BudgetPeriodBean>{
		public WrapperBudgetPeriodList(DataModel model, List<BudgetPeriodBean> wrappedList) {
			super(model, wrappedList, true);
		}
		
		@Override
		public BudgetPeriodBean getWrappedObject(BudgetPeriod object) {
			return object.getBudgetPeriodBean();
		}

		@Override
		public BudgetPeriod getWrapperObject(BudgetPeriodBean object) {
			return new BudgetPeriod(getDataModel(), object);
		}
	}
	
	public static class WrapperScheduledTransactionList extends BuddiWrapperList<ScheduledTransaction, ScheduledTransactionBean>{
		public WrapperScheduledTransactionList(DataModel model, List<ScheduledTransactionBean> wrappedList) {
			super(model, wrappedList, true);
		}
		
		@Override
		public ScheduledTransactionBean getWrappedObject(ScheduledTransaction object) {
			return object.getScheduledTranasactionBean();
		}

		@Override
		public ScheduledTransaction getWrapperObject(ScheduledTransactionBean object) {
			return new ScheduledTransaction(getDataModel(), object);
		}
	}

	public static class WrapperTransactionList extends BuddiWrapperList<Transaction, TransactionBean>{
		public WrapperTransactionList(DataModel model, List<TransactionBean> wrappedList) {
			super(model, wrappedList, true);
		}
		
		@Override
		public TransactionBean getWrappedObject(Transaction object) {
			return object.getTransactionBean();
		}

		@Override
		public Transaction getWrapperObject(TransactionBean object) {
			return new Transaction(getDataModel(), object);
		}
	}
	
	public static class ImmutableAccountList extends BuddiWrapperList<ImmutableAccount, Account> {
		public ImmutableAccountList(DataModel model, List<Account> wrappedList) {
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
	
	public static class ImmutableObjectWrapperList<T extends ImmutableModelObject, W extends ModelObject> extends BuddiWrapperList<T, W> {
		public ImmutableObjectWrapperList(DataModel model, List<W> wrappedList) {
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
				return (T) new ImmutableAccountImpl((Account) object);
			if (object instanceof BudgetCategory)
				return (T) new ImmutableBudgetCategoryImpl((BudgetCategory) object);
			if (object instanceof BudgetPeriod)
				return (T) new ImmutableBudgetPeriodImpl((BudgetPeriod) object);
			if (object instanceof Transaction)
				return (T) new ImmutableTransactionImpl((Transaction) object);
			if (object instanceof Type)
				return (T) new ImmutableTypeImpl((Type) object);
			
			//Catch all
			return null;
		}
	}
}
