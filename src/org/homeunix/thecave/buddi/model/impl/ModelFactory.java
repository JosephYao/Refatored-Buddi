/*
 * Created on Aug 28, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.model.impl;

import java.util.Date;

import org.homeunix.thecave.buddi.model.Account;
import org.homeunix.thecave.buddi.model.AccountType;
import org.homeunix.thecave.buddi.model.BudgetCategory;
import org.homeunix.thecave.buddi.model.BudgetCategoryType;
import org.homeunix.thecave.buddi.model.ScheduledTransaction;
import org.homeunix.thecave.buddi.model.Source;
import org.homeunix.thecave.buddi.model.Transaction;
import org.homeunix.thecave.buddi.model.beans.AccountBean;
import org.homeunix.thecave.buddi.model.beans.BudgetCategoryBean;
import org.homeunix.thecave.buddi.model.beans.ModelObjectBean;
import org.homeunix.thecave.buddi.model.beans.ScheduledTransactionBean;
import org.homeunix.thecave.buddi.model.beans.TransactionBean;
import org.homeunix.thecave.buddi.model.beans.TypeBean;
import org.homeunix.thecave.buddi.model.exception.InvalidValueException;

public class ModelFactory {
	public static Account createAccount(String name, AccountType type) throws InvalidValueException {
		Account a = new AccountImpl(new AccountBean());
		
		a.setName(name);
		a.setType(type);
		
		return a;
	}
	
	public static AccountType createAccountType(String name, boolean credit) throws InvalidValueException {
		AccountType at = new AccountTypeImpl(new TypeBean());
		
		at.setName(name);
		at.setCredit(credit);
		
		return at;
	}
	
	public static BudgetCategory createBudgetCategory(String name, BudgetCategoryType type, boolean income) throws InvalidValueException {
		BudgetCategory bc = new BudgetCategoryImpl(new BudgetCategoryBean());
		
		bc.setName(name);
		bc.setPeriodType(type);
		bc.setIncome(income);
		
		return bc;
	}
		
	public static Transaction createTransaction(Date date, String description, long amount, Source from, Source to) throws InvalidValueException {
		Transaction t = new TransactionImpl(new TransactionBean());
		
		t.setDate(date);
		t.setDescription(description);
		t.setAmount(amount);
		t.setFrom(from);
		t.setTo(to);
		
		return t;
	}
	
	public static ScheduledTransaction createScheduledTransaction() throws InvalidValueException {
		ScheduledTransaction st = new ScheduledTransactionImpl(new ScheduledTransactionBean());
		
		return st;
	}
	
	/**
	 * Generate a UID string for a particular object.  This is guaranteed to be unique
	 * for each call to this method, even if the object is the same.  It is generated 
	 * by concatinating the following information, separated by the dash (-):
	 * 
	 * 1) The canonical name of the object (e.g. org.homeunix.thecave.buddi.model3.Account).
	 * 2) A hexadecimal representation of the current system time in milliseconds
	 * 3) A hexadecimal representation of a 16 bit random number
	 * 4) A hexadecimal representation of the 16 least significant bits of this object's hash code (object.hashCode()).
	 * @param object
	 * @return
	 */
	public static String getGeneratedUid(ModelObjectBean object){
		long time = System.currentTimeMillis();
		int random = (int) (Math.random() * 0xFFFF);
		int hash = object.hashCode() & 0xFFFF;

		String uid = object.getClass().getCanonicalName() + "-" + Long.toHexString(time) + "-" + Integer.toHexString(random) + "-" + Integer.toHexString(hash);

		return uid;
	}
}
