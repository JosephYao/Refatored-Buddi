/*
 * Created on Aug 6, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.model;

import java.util.Date;
import java.util.List;

import org.homeunix.thecave.buddi.i18n.keys.TransactionDateFilterKeys;
import org.homeunix.thecave.buddi.plugin.api.util.TextFormatter;
import org.homeunix.thecave.moss.data.list.FilteredList;
import org.homeunix.thecave.moss.model.DocumentChangeEvent;
import org.homeunix.thecave.moss.model.DocumentChangeListener;
import org.homeunix.thecave.moss.util.DateFunctions;
import org.homeunix.thecave.moss.util.Formatter;
import org.homeunix.thecave.moss.util.Log;

public class FilteredLists {
	private FilteredLists() {}

	/**
	 * Abstract class used as a base for Buddi filtered lists.  Registers the data
	 * model for change events, and updates the filtered list accordingly. 
	 * @author wyatt
	 *
	 */
	private static abstract class BuddiFilteredList<T> extends FilteredList<T> {
		public BuddiFilteredList(DataModel model, List<T> source) {
			super(source);

			model.addDocumentChangeListener(new DocumentChangeListener(){
				public void documentChange(DocumentChangeEvent event) {
					updateFilteredList();
				}
			});
		}
	}

	/**
	 * Returns a list of all transactions which are associated with a given source
	 * @author wyatt
	 *
	 */
	public static class TransactionListFilteredBySource extends BuddiFilteredList<Transaction> {
		private final Source source;

		public TransactionListFilteredBySource(DataModel model, List<Transaction> transactions, Source source){
			super(model, transactions);
			this.source = source;
		}

		@Override
		public boolean isIncluded(Transaction t) {
			if (t.getTo().equals(source)
					|| t.getFrom().equals(source)){
				return true;
			}

			return false;
		}
	}
	
	/**
	 * Returns a list of all transactions which fall between startDate and endDate
	 * @author wyatt
	 *
	 */
	public static class TransactionListFilteredByDate extends BuddiFilteredList<Transaction> {
		private final Date startDate;
		private final Date endDate;

		public TransactionListFilteredByDate(DataModel model, List<Transaction> transactions, Date startDate, Date endDate){
			super(model, transactions);
			this.startDate = startDate;
			this.endDate = endDate;
		}

		@Override
		public boolean isIncluded(Transaction t) {
			if (t.getDate().after(startDate) 
					&& t.getDate().before(endDate)){
				return true;
			}

			return false;
		}
	}

	/**
	 * Returns a list of all transactions which match the query text and the pulldowns in 
	 * the TransactionsFrame.
	 * @author wyatt
	 *
	 */
	public static class TransactionListFilteredBySearch extends BuddiFilteredList<Transaction> {
		private String searchText;
		private TransactionDateFilterKeys dateFilter; 

		public TransactionListFilteredBySearch(DataModel model, List<Transaction> transactions){
			super(model, transactions);
		}
		public void setDateFilter(TransactionDateFilterKeys dateFilter) {
			this.dateFilter = dateFilter;
		}
		public void setSearchText(String searchText) {
			this.searchText = searchText;
		}

		@Override
		public boolean isIncluded(Transaction t) {
			if (t == null || t.getTo() == null | t.getFrom() == null){
				return false;				
			}

			return acceptDate(t) && acceptText(t);
		}

		private boolean acceptDate(Transaction t) {
			if (null == dateFilter || TransactionDateFilterKeys.TRANSACTION_FILTER_ALL == dateFilter) {
				return true;
			}

			Date today = new Date();

			if (TransactionDateFilterKeys.TRANSACTION_FILTER_TODAY == dateFilter) {
				return DateFunctions.getEndOfDay(today).equals(DateFunctions.getEndOfDay(t.getDate()));
			}
			else if (TransactionDateFilterKeys.TRANSACTION_FILTER_THIS_WEEK == dateFilter) {
				return DateFunctions.getStartOfDay(DateFunctions.addDays(today, -7)).before(t.getDate());
			}
			else if (TransactionDateFilterKeys.TRANSACTION_FILTER_THIS_MONTH == dateFilter) {
				return DateFunctions.getStartOfMonth(today).before(t.getDate());
			}
			else if (TransactionDateFilterKeys.TRANSACTION_FILTER_THIS_QUARTER == dateFilter) {
				return DateFunctions.getStartOfDay(DateFunctions.getStartOfQuarter(today, 0)).before(t.getDate());
			} 
			else if (TransactionDateFilterKeys.TRANSACTION_FILTER_THIS_YEAR == dateFilter) {
				return DateFunctions.getStartOfYear(today).before(t.getDate());				
			}
			else if (TransactionDateFilterKeys.TRANSACTION_FILTER_LAST_YEAR == dateFilter) {
				Date startOfLastYear = DateFunctions.getStartOfYear(DateFunctions.addYears(today, -1));
				Date endOfLastYear = DateFunctions.getEndOfYear(startOfLastYear);
				return startOfLastYear.before(t.getDate()) && endOfLastYear.after(t.getDate()); 
			}

			//TODO Include this in a different filter
//			else if (TransactionDateFilterKeys.TRANSACTION_FILTER_NOT_RECONCILED == dateFilter) {
//			return !t.isReconciled();
//			}
//			else if (TransactionDateFilterKeys.TRANSACTION_FILTER_NOT_CLEARED == dateFilter) {
//			return !t.isCleared();
//			}
			else {
				Log.error("Unknown filter pulldown: " + dateFilter);
				return false;
			}
		}

		private boolean acceptText(Transaction t) {
			if (searchText == null || searchText.length() == 0) {
				return true;
			}
			String decimal = Formatter.getDecimalFormat().format(100).replaceAll("\\d", "");
			return (searchText == null
					|| searchText.length() == 0
					|| t.getDescription().toLowerCase().contains(searchText.toLowerCase())
					|| t.getNumber().toLowerCase().contains(searchText.toLowerCase())
					|| t.getMemo().toLowerCase().contains(searchText.toLowerCase())
					|| t.getFrom().getName().toLowerCase().contains(searchText.toLowerCase())
					|| t.getTo().getName().toLowerCase().contains(searchText.toLowerCase())
					|| TextFormatter.getFormattedCurrency(t.getAmount()).replaceAll("[^\\d" + decimal + "]", "").contains(searchText.toLowerCase()))
					|| TextFormatter.getDateFormat().format(t.getDate()).toLowerCase().contains(searchText.toLowerCase());

		}	
	}

	/**
	 * Returns a list of all the Accounts associated with the given type. 
	 * @author wyatt
	 */
	public static class AccountListFilteredByType extends BuddiFilteredList<Account> {
		private final Type type;

		public AccountListFilteredByType(DataModel model, Type type) {
			super(model, model.getAccounts());
			this.type = type;
		}

		@Override
		public boolean isIncluded(Account object) {
			if (object != null && object.getType() != null){
				return object.getType().equals(type);
			}
			return false;
		}
	}

	/**
	 * Returns a list of all the type objects which have Accounts associated with them.
	 * @author wyatt
	 */
	public static class TypeListFilteredByAccounts extends BuddiFilteredList<Type> {
		private final DataModel model;

		public TypeListFilteredByAccounts(DataModel model) {
			super(model, model.getTypes());
			this.model = model;
		}

		@Override
		public boolean isIncluded(Type object) {
			if (object != null){
				for (Account a : model.getAccounts()) {
					if (a.getType().equals(object))
						return true;
				}
			}
			return false;
		}
	}

	public static class BudgetCategoryListFilteredByChildren extends BuddiFilteredList<BudgetCategory> {
		private final BudgetCategory parent;

		public BudgetCategoryListFilteredByChildren(DataModel model, BudgetCategory parent) {
			super(model, model.getBudgetCategories());
			this.parent = parent;
		}

		@Override
		public boolean isIncluded(BudgetCategory object) {
			//We check the parental hierarchy of each object, and if it the 
			// parent object is included, this node is included in the list.
			BudgetCategory temp = object;
			while (temp.getParent() != null){
				temp = temp.getParent();
				if (temp.equals(parent))
					return true;
			}
			return false;
		}
	}

	/**
	 * Returns a list of categories which are children of the given parent.
	 * @author wyatt
	 */
	public static class BudgetCategoryListFilteredByParent extends BuddiFilteredList<BudgetCategory> {
		private final BudgetCategory parent;

		public BudgetCategoryListFilteredByParent(DataModel model, BudgetCategory parent) {
			super(model, model.getBudgetCategories());
			this.parent = parent;
		}

		@Override
		public boolean isIncluded(BudgetCategory object) {
			if (object != null){
				if (parent == null)
					return object.getParent() == null;
				else if (object.getParent() != null)
					return object.getParent().equals(parent);
			}
			return false;
		}
	}
}
