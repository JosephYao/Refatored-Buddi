/*
 * Created on Jul 29, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.model.converter;

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
import org.homeunix.drummer.model.Type;
import org.homeunix.drummer.model.impl.DataModelImpl;
import org.homeunix.thecave.buddi.model.DataModel;
import org.homeunix.thecave.buddi.model.beans.AccountBean;
import org.homeunix.thecave.buddi.model.beans.BudgetCategoryBean;
import org.homeunix.thecave.buddi.model.beans.BudgetPeriodBean;
import org.homeunix.thecave.buddi.model.beans.DataModelBean;
import org.homeunix.thecave.buddi.model.beans.ScheduledTransactionBean;
import org.homeunix.thecave.buddi.model.beans.SourceBean;
import org.homeunix.thecave.buddi.model.beans.TransactionBean;
import org.homeunix.thecave.buddi.model.beans.TypeBean;
import org.homeunix.thecave.moss.exception.DocumentLoadException;
import org.homeunix.thecave.moss.util.DateFunctions;


/**
 * @author wyatt
 * 
 * Class to convert from the old (Buddi 2) model to new (Buddi 3) model.
 */
public class ModelConverter {
	
	
	public static DataModelBean convert(File oldFile) throws DocumentLoadException {
		
		DataModel newModel = new DataModel(); //TODO We only use this for getPeriodKey; we may want to change this...
		DataModelBean newModelBean = new DataModelBean(); 
		DataInstance.getInstance().loadDataFile(oldFile);
		DataModelImpl oldModel = (DataModelImpl) DataInstance.getInstance().getDataModel();

		Map<Type, TypeBean> typeMap = new HashMap<Type, TypeBean>();
		Map<TypeBean, List<AccountBean>> typeAccountMap = new HashMap<TypeBean, List<AccountBean>>();
		Map<Category, BudgetCategoryBean> categoryMap = new HashMap<Category, BudgetCategoryBean>();
		Map<Source, SourceBean> sourceMap = new HashMap<Source, SourceBean>();
		
		//We need to convert in the order that we need the objects.
		
		//First we convert Types.
		List<TypeBean> newTypes = new LinkedList<TypeBean>();
		for (Object oldTypeObject : oldModel.getAllTypes().getTypes()){
			org.homeunix.drummer.model.Type oldType = (org.homeunix.drummer.model.Type) oldTypeObject;
			
			TypeBean newType = new TypeBean();
			
			newType.setCredit(oldType.isCredit());
			newType.setName(oldType.getName());
			newType.setModifiedDate(new Date());
//			newType.setUid(DataModel.getGeneratedUid(newType));
			
			typeMap.put(oldType, newType);
			typeAccountMap.put(newType, new LinkedList<AccountBean>());
			
			newTypes.add(newType);
		}
		newModelBean.setTypes(newTypes);
		
		//Now we can convert Accounts
		List<AccountBean> newAccounts = new LinkedList<AccountBean>();
		for (Object oldAccountObject : oldModel.getAllAccounts().getAccounts()) {
			Account oldAccount = (Account) oldAccountObject;
			
			AccountBean newAccount = new AccountBean();
			
//			newAccount.setBalance(oldAccount.getBalance());
//			newAccount.setCreatedDate(oldAccount.getCreationDate());
//			newAccount.setCreditLimit(oldAccount.getCreditLimit());
			newAccount.setDeleted(oldAccount.isDeleted());
//			newAccount.setInterestRate(oldAccount.getInterestRate());
			newAccount.setModifiedDate(new Date());
			newAccount.setName(oldAccount.getName());
			newAccount.setStartingBalance(oldAccount.getStartingBalance());
//			newAccount.setUid(DataModel.getGeneratedUid(newAccount));
			newAccount.setType(typeMap.get(oldAccount.getAccountType()));
			
			sourceMap.put(oldAccount, newAccount);
			
			typeAccountMap.get(typeMap.get(oldAccount.getAccountType())).add(newAccount);
			newAccounts.add(newAccount);
		}
		newModelBean.setAccounts(newAccounts);
		
	
		//Categories next...
		BudgetPeriodBean newBudgetPeriod = new BudgetPeriodBean();
		newBudgetPeriod.setPeriodDate(DateFunctions.getStartOfMonth(new Date()));
		newBudgetPeriod.setModifiedDate(new Date());
		newModelBean.getBudgetPeriods().put(newModel.getPeriodKey(newBudgetPeriod.getPeriodDate()), newBudgetPeriod);

		List<BudgetCategoryBean> newBudgetCategories = new LinkedList<BudgetCategoryBean>();
		for (Object oldCategoryObject : oldModel.getAllCategories().getCategories()){
			Category oldCategory = (Category) oldCategoryObject;
			
			
			
			BudgetCategoryBean newBudgetCategory = new BudgetCategoryBean();
//			newBudgetCategory.setCreatedDate(oldCategory.getCreationDate());
			newBudgetCategory.setDeleted(oldCategory.isDeleted());
			newBudgetCategory.setExpanded(true);
			newBudgetCategory.setIncome(oldCategory.isIncome());
			newBudgetCategory.setModifiedDate(new Date());
			newBudgetCategory.setName(oldCategory.getName());
			
			newBudgetPeriod.getBudgetCategories().put(newBudgetCategory, oldCategory.getBudgetedAmount());
			
			categoryMap.put(oldCategory, newBudgetCategory);
			sourceMap.put(oldCategory, newBudgetCategory);
			
			newBudgetCategories.add(newBudgetCategory);
		}
		
		newModelBean.setBudgetCategories(newBudgetCategories);
		
		//We needed to wait until all categories are processed before we start 
		// setting the children. 
		for (Object oldCategoryObject : oldModel.getAllCategories().getCategories()){
			Category oldCategory = (Category) oldCategoryObject;

			if (oldCategory.getParent() != null){
				BudgetCategoryBean parent = categoryMap.get(oldCategory.getParent());
				categoryMap.get(oldCategory).setParent(parent);

				//Now we can remove the new categories which have a parent - these will be
				// referenced by traversing the BudgetCategory tree.
				newBudgetCategories.remove(categoryMap.get(oldCategory));
			}
		}
		
		//Let's go for transactions now...
		List<TransactionBean> newTransactions = new LinkedList<TransactionBean>();
		for (Object oldTransactionObject : oldModel.getAllTransactions().getTransactions()){
			org.homeunix.drummer.model.Transaction oldTransaction = (org.homeunix.drummer.model.Transaction) oldTransactionObject;

			TransactionBean newTransaction = new TransactionBean();
			newTransaction.setCleared(oldTransaction.isCleared());
			newTransaction.setDate(oldTransaction.getDate());
			newTransaction.setDescription(oldTransaction.getDescription());
			newTransaction.setAmount(oldTransaction.getAmount());
			newTransaction.setMemo(oldTransaction.getMemo());
			newTransaction.setModifiedDate(new Date());
			newTransaction.setNumber(oldTransaction.getNumber());
			newTransaction.setReconciled(oldTransaction.isReconciled());
			newTransaction.setScheduled(oldTransaction.isScheduled());
			newTransaction.setFrom(sourceMap.get(oldTransaction.getFrom()));
			newTransaction.setTo(sourceMap.get(oldTransaction.getTo()));
			
			newTransactions.add(newTransaction);
		}
		
		newModelBean.setTransactions(newTransactions);
		
		
		//And scheduled transactions...
		List<ScheduledTransactionBean> newScheduledTransactions = new LinkedList<ScheduledTransactionBean>();
		for (Object oldScheduledTransactionObject : oldModel.getAllTransactions().getScheduledTransactions()){
			Schedule oldScheduledTransaction = (Schedule) oldScheduledTransactionObject;

			ScheduledTransactionBean newScheduledTransaction = new ScheduledTransactionBean();
			newScheduledTransaction.setCleared(oldScheduledTransaction.isCleared());
			newScheduledTransaction.setDate(oldScheduledTransaction.getDate());
			newScheduledTransaction.setDescription(oldScheduledTransaction.getDescription());
			newScheduledTransaction.setEndDate(oldScheduledTransaction.getEndDate());
			newScheduledTransaction.setFrequencyType(oldScheduledTransaction.getFrequencyType());
			newScheduledTransaction.setLastDayCreated(oldScheduledTransaction.getLastDateCreated());
			newScheduledTransaction.setMemo(oldScheduledTransaction.getMemo());
			newScheduledTransaction.setMessage(oldScheduledTransaction.getMessage());
			newScheduledTransaction.setModifiedDate(new Date());
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
			
			newScheduledTransactions.add(newScheduledTransaction);
		}
		
		newModelBean.setScheduledTransactions(newScheduledTransactions);
		
		//Finally, we return the new model.
		return newModelBean;
	}
}
