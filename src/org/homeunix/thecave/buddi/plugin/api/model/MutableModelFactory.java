/*
 * Created on Aug 26, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.plugin.api.model;

import java.io.File;
import java.util.Date;

import org.homeunix.thecave.buddi.model.Account;
import org.homeunix.thecave.buddi.model.AccountType;
import org.homeunix.thecave.buddi.model.BudgetCategory;
import org.homeunix.thecave.buddi.model.BudgetCategoryType;
import org.homeunix.thecave.buddi.model.ScheduledTransaction;
import org.homeunix.thecave.buddi.model.Transaction;
import org.homeunix.thecave.buddi.model.impl.ModelFactory;
import org.homeunix.thecave.buddi.plugin.api.exception.ModelException;
import org.homeunix.thecave.buddi.plugin.api.model.impl.MutableAccountImpl;
import org.homeunix.thecave.buddi.plugin.api.model.impl.MutableBudgetCategoryImpl;
import org.homeunix.thecave.buddi.plugin.api.model.impl.MutableModelImpl;
import org.homeunix.thecave.buddi.plugin.api.model.impl.MutableScheduledTransactionImpl;
import org.homeunix.thecave.buddi.plugin.api.model.impl.MutableTransactionImpl;
import org.homeunix.thecave.buddi.plugin.api.model.impl.MutableTypeImpl;
import org.homeunix.thecave.moss.exception.DocumentLoadException;
import org.homeunix.thecave.moss.exception.OperationCancelledException;

public class MutableModelFactory {
	/**
	 * Creates a new MutableModel, with the default budget categories, types, etc.
	 * @return
	 * @throws ModelException
	 */
	public static MutableModel createDocument() throws ModelException {
		return new MutableModelImpl(ModelFactory.createDocument());
	}
	
	/**
	 * Loads an existing MutableModel from the specified file.
	 * @param file
	 * @return
	 * @throws DocumentLoadException
	 * @throws OperationCancelledException
	 */
	public static MutableModel createDocument(File file) throws DocumentLoadException, OperationCancelledException {
		return new MutableModelImpl(ModelFactory.createDocument(file));
	}
	
	/**
	 * Creates a new MutableAccount with the given details.
	 * @param name
	 * @param startingBalance
	 * @param type
	 * @return
	 * @throws ModelException
	 */
	public static MutableAccount createMutableAccount(String name, long startingBalance, MutableAccountType type)  throws ModelException {
		Account a = ModelFactory.createAccount(name, type.getType());

		return new MutableAccountImpl(a);
	}
	
	/**
	 * Creates a new MutableAccountType with the given details.
	 * @param name
	 * @param credit
	 * @return
	 * @throws ModelException
	 */
	public static MutableAccountType createMutableAccountType(String name, boolean credit) throws ModelException {
		AccountType t = ModelFactory.createAccountType(name, credit);
		
		return new MutableTypeImpl(t);
	}
	
	/**
	 * Creates a new MutableBudgetCategory with the given details.
	 * @param name
	 * @param periodType
	 * @param isIncome
	 * @return
	 * @throws ModelException
	 */
	public static MutableBudgetCategory createMutableBudgetCategory(String name, BudgetCategoryType periodType, boolean isIncome) throws ModelException {
		BudgetCategory bc = ModelFactory.createBudgetCategory(name, periodType, isIncome);
		
		return new MutableBudgetCategoryImpl(bc);
	}
	
	/**
	 * Creates a new MutableScheduledTransaction
	 * @return
	 * @throws ModelException
	 */
	public static MutableScheduledTransaction createMutableScheduledTransaction() throws ModelException{
		ScheduledTransaction st = ModelFactory.createScheduledTransaction();
		
		return new MutableScheduledTransactionImpl(st);
	}
	
	/**
	 * Creates a new MutableTransaction with the given details.
	 * @param date
	 * @param description
	 * @param amount
	 * @param from
	 * @param to
	 * @return
	 * @throws ModelException
	 */
	public static MutableTransaction createMutableTransaction(Date date, String description, long amount, MutableSource from, MutableSource to) throws ModelException {
		Transaction t = ModelFactory.createTransaction(date, description, amount, from.getSource(), to.getSource());
		
		return new MutableTransactionImpl(t);
	}
}
