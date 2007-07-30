/*
 * Created on Jul 29, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.model.converter;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.homeunix.drummer.model.Category;
import org.homeunix.drummer.model.Schedule;
import org.homeunix.drummer.model.impl.DataModelImpl;
import org.homeunix.thecave.buddi.model.DataModel;
import org.homeunix.thecave.buddi.model.beans.Account;
import org.homeunix.thecave.buddi.model.beans.BudgetCategory;
import org.homeunix.thecave.buddi.model.beans.BudgetPeriod;
import org.homeunix.thecave.buddi.model.beans.ScheduledTransaction;
import org.homeunix.thecave.buddi.model.beans.Source;
import org.homeunix.thecave.buddi.model.beans.Transaction;
import org.homeunix.thecave.buddi.model.beans.Type;
import org.homeunix.thecave.buddi.model.beans.UniqueID;

/**
 * @author wyatt
 * 
 * Class to convert from the old (Buddi 2) model to new (Buddi 3) model.
 */
public class ModelConverter {
	
	
	public static DataModel convert(DataModelImpl oldModel){
		DataModel newModel = new DataModel();

		Map<org.homeunix.drummer.model.Type, Type> typeMap = new HashMap<org.homeunix.drummer.model.Type, Type>();
		Map<Category, BudgetCategory> categoryMap = new HashMap<Category, BudgetCategory>();
		Map<org.homeunix.drummer.model.Source, Source> sourceMap = new HashMap<org.homeunix.drummer.model.Source, Source>();
		
		//We need to convert in the order that we need the objects.
		
		//First we convert Types.
		List<Type> newTypes = new LinkedList<Type>();
		for (Object oldTypeObject : oldModel.getAllTypes().getTypes()){
			org.homeunix.drummer.model.Type oldType = (org.homeunix.drummer.model.Type) oldTypeObject;
			
			Type newType = new Type();
			
			newType.setCredit(oldType.isCredit());
			newType.setName(oldType.getName());
			newType.setModifiedDate(new Date());
			newType.setSystemUid(UniqueID.getNextUID());
			
			typeMap.put(oldType, newType);
			
			newTypes.add(newType);
		}
		newModel.setTypes(newTypes);
		
		//Now we can convert Accounts
		List<Account> newAccounts = new LinkedList<Account>();
		for (Object oldAccountObject : oldModel.getAllAccounts().getAccounts()) {
			org.homeunix.drummer.model.Account oldAccount = (org.homeunix.drummer.model.Account) oldAccountObject;
			
			Account newAccount = new Account();
			
			newAccount.setBalance(oldAccount.getBalance());
			newAccount.setCreatedDate(oldAccount.getCreationDate());
			newAccount.setCreditLimit(oldAccount.getCreditLimit());
			newAccount.setDeleted(oldAccount.isDeleted());
			newAccount.setInterestRate(oldAccount.getInterestRate());
			newAccount.setModifiedDate(new Date());
			newAccount.setName(oldAccount.getName());
			newAccount.setStartingBalance(oldAccount.getStartingBalance());
			newAccount.setSystemUid(UniqueID.getNextUID());
			newAccount.setType(typeMap.get(oldAccount.getAccountType()));
			
			sourceMap.put(oldAccount, newAccount);
			
			newAccounts.add(newAccount);
		}
		newModel.setAccounts(newAccounts);
		
		//Categories next...
		List<BudgetCategory> newBudgetCategories = new LinkedList<BudgetCategory>();
		for (Object oldCategoryObject : oldModel.getAllCategories().getCategories()){
			Category oldCategory = (Category) oldCategoryObject;
			
			BudgetCategory newBudgetCategory = new BudgetCategory();
			newBudgetCategory.setAmount(oldCategory.getBudgetedAmount());
			newBudgetCategory.setCreatedDate(oldCategory.getCreationDate());
			newBudgetCategory.setDeleted(oldCategory.isDeleted());
			newBudgetCategory.setExpanded(true);
			newBudgetCategory.setIncome(oldCategory.isIncome());
			newBudgetCategory.setModifiedDate(new Date());
			newBudgetCategory.setName(oldCategory.getName());
			newBudgetCategory.setSystemUid(UniqueID.getNextUID());
			
			categoryMap.put(oldCategory, newBudgetCategory);
			sourceMap.put(oldCategory, newBudgetCategory);
			
			newBudgetCategories.add(newBudgetCategory);
		}
		
		//We needed to wait until all categories are processed before we start 
		// setting the children. 
		for (Object oldCategoryObject : oldModel.getAllCategories().getCategories()){
			Category oldCategory = (Category) oldCategoryObject;

			if (oldCategory.getParent() != null){
				BudgetCategory parent = categoryMap.get(oldCategory.getParent());
				parent.getChildren().add(categoryMap.get(oldCategory));

				//Now we can remove the new categories which have a parent - these will be
				// referenced by traversing the BudgetCategory tree.
				newBudgetCategories.remove(categoryMap.get(oldCategory));
			}
		}
		
		//Now that we have the list of categories, we need to set the default 
		// budget period.
		BudgetPeriod bp = new BudgetPeriod();
		bp.setBudgetCategories(newBudgetCategories);
		bp.setModifiedDate(new Date());
		bp.setSystemUid(UniqueID.getNextUID());
		newModel.getBudgetPeriods().add(bp);
		

		//Let's go for transactions now...
		List<Transaction> newTransactions = new LinkedList<Transaction>();
		for (Object oldTransactionObject : oldModel.getAllTransactions().getTransactions()){
			org.homeunix.drummer.model.Transaction oldTransaction = (org.homeunix.drummer.model.Transaction) oldTransactionObject;

			Transaction newTransaction = new Transaction();
			newTransaction.setAmount(oldTransaction.getAmount());
			newTransaction.setCleared(oldTransaction.isCleared());
			newTransaction.setDate(oldTransaction.getDate());
			newTransaction.setDescription(oldTransaction.getDescription());
			newTransaction.setFrom(sourceMap.get(oldTransaction.getFrom()));
			newTransaction.setMemo(oldTransaction.getMemo());
			newTransaction.setModifiedDate(new Date());
			newTransaction.setNumber(oldTransaction.getNumber());
			newTransaction.setReconciled(oldTransaction.isReconciled());
			newTransaction.setScheduled(oldTransaction.isScheduled());
			newTransaction.setSystemUid(UniqueID.getNextUID());
			newTransaction.setTo(sourceMap.get(oldTransaction.getTo()));
			
			newTransactions.add(newTransaction);
		}
		
		newModel.setTransactions(newTransactions);
		
		
		//And scheduled transactions...
		List<ScheduledTransaction> newScheduledTransactions = new LinkedList<ScheduledTransaction>();
		for (Object oldScheduledTransactionObject : oldModel.getAllTransactions().getScheduledTransactions()){
			Schedule oldScheduledTransaction = (Schedule) oldScheduledTransactionObject;

			ScheduledTransaction newScheduledTransaction = new ScheduledTransaction();
			newScheduledTransaction.setAmount(oldScheduledTransaction.getAmount());
			newScheduledTransaction.setCleared(oldScheduledTransaction.isCleared());
			newScheduledTransaction.setDate(oldScheduledTransaction.getDate());
			newScheduledTransaction.setDescription(oldScheduledTransaction.getDescription());
			
			newScheduledTransaction.setEndDate(oldScheduledTransaction.getEndDate());
			newScheduledTransaction.setFrequencyType(oldScheduledTransaction.getFrequencyType());
			
			newScheduledTransaction.setFrom(sourceMap.get(oldScheduledTransaction.getFrom()));
			
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
			
			newScheduledTransaction.setSystemUid(UniqueID.getNextUID());
			newScheduledTransaction.setTo(sourceMap.get(oldScheduledTransaction.getTo()));
			
			newScheduledTransactions.add(newScheduledTransaction);
		}
		
		newModel.setScheduledTransactions(newScheduledTransactions);
		
		//Finally, we return the new model.
		return newModel;
	}
}
