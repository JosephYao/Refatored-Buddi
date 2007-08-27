/*
 * Created on Jul 29, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.plugin.builtin.imports;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.homeunix.drummer.model.Account;
import org.homeunix.drummer.model.Category;
import org.homeunix.drummer.model.DataInstance;
import org.homeunix.drummer.model.Schedule;
import org.homeunix.drummer.model.Source;
import org.homeunix.drummer.model.Transaction;
import org.homeunix.drummer.model.Type;
import org.homeunix.drummer.model.impl.DataModelImpl;
import org.homeunix.thecave.buddi.model.periods.BudgetPeriodMonthly;
import org.homeunix.thecave.buddi.plugin.api.model.ModelFactory;
import org.homeunix.thecave.buddi.plugin.api.model.MutableAccount;
import org.homeunix.thecave.buddi.plugin.api.model.MutableBudgetCategory;
import org.homeunix.thecave.buddi.plugin.api.model.MutableModel;
import org.homeunix.thecave.buddi.plugin.api.model.MutableScheduledTransaction;
import org.homeunix.thecave.buddi.plugin.api.model.MutableSource;
import org.homeunix.thecave.buddi.plugin.api.model.MutableTransaction;
import org.homeunix.thecave.buddi.plugin.api.model.MutableType;
import org.homeunix.thecave.moss.exception.DocumentLoadException;

/**
 * @author wyatt
 * 
 * Class to convert from the old (Buddi 2) model to new (Buddi 3) model.
 */
public class LegacyModelConverter {
	
	
	public static void convert(MutableModel model, File oldFile) throws DocumentLoadException {
		
//		DataModelBean newModelBean = new DataModelBean(); 
		DataInstance.getInstance().loadDataFile(oldFile);
		DataModelImpl oldModel = (DataModelImpl) DataInstance.getInstance().getDataModel();

		Map<Type, MutableType> typeMap = new HashMap<Type, MutableType>();
		Map<MutableType, List<MutableAccount>> typeAccountMap = new HashMap<MutableType, List<MutableAccount>>();
		Map<Category, MutableBudgetCategory> categoryMap = new HashMap<Category, MutableBudgetCategory>();
		Map<Source, MutableSource> sourceMap = new HashMap<Source, MutableSource>();
		
		//We need to convert in the order that we need the objects.
		
		//First we convert Types.
//		List<Type> newTypes = new LinkedList<Type>();
		for (Object oldTypeObject : oldModel.getAllTypes().getTypes()){
			org.homeunix.drummer.model.Type oldType = (org.homeunix.drummer.model.Type) oldTypeObject;
			
			MutableType newType = ModelFactory.createMutableType(model, oldType.getName(), oldType.isCredit());
						
			typeMap.put(oldType, newType);
			typeAccountMap.put(newType, new LinkedList<MutableAccount>());

			model.addType(newType);
		}
//		newModelBean.setTypes(newTypes);
		
		//Now we can convert Accounts
//		List<MutableAccount> newAccounts = new LinkedList<AccountBean>();
		for (Object oldAccountObject : oldModel.getAllAccounts().getAccounts()) {
			Account oldAccount = (Account) oldAccountObject;
			
			MutableAccount newAccount = ModelFactory.createMutableAccount(model, oldAccount.getName(), oldAccount.getStartingBalance(), typeMap.get(oldAccount.getAccountType()));
			newAccount.setDeleted(oldAccount.isDeleted());
			
//			newAccount.setBalance(oldAccount.getBalance());
//			newAccount.setCreatedDate(oldAccount.getCreationDate());
//			newAccount.setCreditLimit(oldAccount.getCreditLimit());
//			newAccount.setInterestRate(oldAccount.getInterestRate());
//			newAccount.setModifiedDate(new Date());
//			newAccount.setName(oldAccount.getName());
//			newAccount.setStartingBalance(oldAccount.getStartingBalance());
//			newAccount.setUid(DataModel.getGeneratedUid(newAccount));
//			newAccount.setType(typeMap.get(oldAccount.getAccountType()));
			
			sourceMap.put(oldAccount, newAccount);
			
			model.addAccount(newAccount);
			
//			typeAccountMap.get(typeMap.get(oldAccount.getAccountType())).add(newAccount);
//			newAccounts.add(newAccount);
		}
//		newModelBean.setAccounts(newAccounts);
		
	
		//Categories next...
//		BudgetPeriodBean newBudgetPeriod = new BudgetPeriodBean();
//		newBudgetPeriod.setPeriodDate(DateFunctions.getStartOfMonth(new Date()));
//		newBudgetPeriod.setModifiedDate(new Date());
//		newModelBean.getBudgetPeriods().put(newModel.getPeriodKey(newBudgetPeriod.getPeriodDate()), newBudgetPeriod);

//		List<BudgetCategoryBean> newBudgetCategories = new LinkedList<BudgetCategoryBean>();
		for (Object oldCategoryObject : oldModel.getAllCategories().getCategories()){
			Category oldCategory = (Category) oldCategoryObject;
			
			MutableBudgetCategory newBudgetCategory = ModelFactory.createMutableBudgetCategory(model, oldCategory.getName(), new BudgetPeriodMonthly(), oldCategory.isIncome());
//			newBudgetCategory.setCreatedDate(oldCategory.getCreationDate());
			newBudgetCategory.setDeleted(oldCategory.isDeleted());
//			newBudgetCategory.setPeriodType(BudgetPeriodType.BUDGET_PERIOD_MONTH.toString());
//			newBudgetCategory.setExpanded(true);
//			newBudgetCategory.setIncome(oldCategory.isIncome());
//			newBudgetCategory.setModifiedDate(new Date());
//			newBudgetCategory.setName(oldCategory.getName());
			newBudgetCategory.setAmount(new Date(), oldCategory.getBudgetedAmount());
//			newBudgetPeriod.getBudgetCategories().put(newBudgetCategory, oldCategory.getBudgetedAmount());
			
			categoryMap.put(oldCategory, newBudgetCategory);
			sourceMap.put(oldCategory, newBudgetCategory);
			
			model.addBudgetCategory(newBudgetCategory);
//			newBudgetCategories.add(newBudgetCategory);
		}
		
//		newModelBean.setBudgetCategories(newBudgetCategories);
		
		//We needed to wait until all categories are processed before we start 
		// setting the children. 
		for (Object oldCategoryObject : oldModel.getAllCategories().getCategories()){
			Category oldCategory = (Category) oldCategoryObject;

			if (oldCategory.getParent() != null){
				MutableBudgetCategory parent = categoryMap.get(oldCategory.getParent());
				categoryMap.get(oldCategory).setParent(parent);
			}
		}
		
		//Let's go for transactions now...
//		List<TransactionBean> newTransactions = new LinkedList<TransactionBean>();
		for (Object oldTransactionObject : oldModel.getAllTransactions().getTransactions()){
			Transaction oldTransaction = (Transaction) oldTransactionObject;

			MutableTransaction newTransaction = ModelFactory.createMutableTransaction(
					model, 
					oldTransaction.getDate(), 
					oldTransaction.getDescription(), 
					oldTransaction.getAmount(), 
					sourceMap.get(oldTransaction.getFrom()),
					sourceMap.get(oldTransaction.getTo())
			);
			newTransaction.setCleared(oldTransaction.isCleared());
			newTransaction.setMemo(oldTransaction.getMemo());
			newTransaction.setNumber(oldTransaction.getNumber());
			newTransaction.setReconciled(oldTransaction.isReconciled());
			newTransaction.setFrom(sourceMap.get(oldTransaction.getFrom()));
			newTransaction.setTo(sourceMap.get(oldTransaction.getTo()));
			newTransaction.setScheduled(oldTransaction.isScheduled());
			
			model.addTransaction(newTransaction);
		}
		
//		newModelBean.setTransactions(newTransactions);
		
		
		//And scheduled transactions...
//		List<ScheduledTransactionBean> newScheduledTransactions = new LinkedList<ScheduledTransactionBean>();
		for (Object oldScheduledTransactionObject : oldModel.getAllTransactions().getScheduledTransactions()){
			Schedule oldScheduledTransaction = (Schedule) oldScheduledTransactionObject;

			MutableScheduledTransaction newScheduledTransaction = ModelFactory.createMutableScheduledTransaction(model);
			newScheduledTransaction.setCleared(oldScheduledTransaction.isCleared());
			newScheduledTransaction.setDate(oldScheduledTransaction.getDate());
			newScheduledTransaction.setDescription(oldScheduledTransaction.getDescription());
			newScheduledTransaction.setEndDate(oldScheduledTransaction.getEndDate());
			newScheduledTransaction.setFrequencyType(oldScheduledTransaction.getFrequencyType());
			newScheduledTransaction.setLastDayCreated(oldScheduledTransaction.getLastDateCreated());
			newScheduledTransaction.setMemo(oldScheduledTransaction.getMemo());
			newScheduledTransaction.setMessage(oldScheduledTransaction.getMessage());
			newScheduledTransaction.setNumber(oldScheduledTransaction.getNumber());
			newScheduledTransaction.setReconciled(oldScheduledTransaction.isReconciled());
			newScheduledTransaction.setScheduled(oldScheduledTransaction.isScheduled());
			newScheduledTransaction.setScheduleDay(oldScheduledTransaction.getScheduleDay());
			newScheduledTransaction.setScheduleWeek(oldScheduledTransaction.getScheduleWeek());
			newScheduledTransaction.setScheduleMonth(oldScheduledTransaction.getScheduleMonth());
			newScheduledTransaction.setScheduleName(oldScheduledTransaction.getScheduleName());
			newScheduledTransaction.setStartDate(oldScheduledTransaction.getStartDate());
			newScheduledTransaction.setAmount(oldScheduledTransaction.getAmount());
			newScheduledTransaction.setFrom(sourceMap.get(oldScheduledTransaction.getFrom()));
			newScheduledTransaction.setTo(sourceMap.get(oldScheduledTransaction.getTo()));
			
			model.addScheduledTransaction(newScheduledTransaction);
		}
		
//		newModelBean.setScheduledTransactions(newScheduledTransactions);
		
		//Finally, we return the new model.
//		return newModelBean;
	}
}
