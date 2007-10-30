/*
 * Created on Aug 24, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.Calendar;
import java.util.Date;

import org.homeunix.thecave.buddi.model.impl.ModelFactory;
import org.homeunix.thecave.moss.util.DateFunctions;
import org.junit.Test;



public class AccountTest {
	
	@Test
	public void testGetBalanceDate(){
		try {
			Document d = ModelFactory.createDocument();
			AccountType at = d.getAccountType("Cash");
			Account a = ModelFactory.createAccount("Test", at);
			d.addAccount(a);
			
			d.addTransaction(ModelFactory.createTransaction(
					DateFunctions.getDate(2007, Calendar.AUGUST, 1), "Test 1", 100, a, d.getBudgetCategory("Groceries")));
			d.addTransaction(ModelFactory.createTransaction(
					DateFunctions.getDate(2007, Calendar.AUGUST, 5), "Test 2", 100, a, d.getBudgetCategory("Groceries")));
			d.addTransaction(ModelFactory.createTransaction(
					DateFunctions.getDate(2007, Calendar.AUGUST, 10), "Test 3", -100, a, d.getBudgetCategory("Groceries")));
			d.addTransaction(ModelFactory.createTransaction(
					DateFunctions.getDate(2007, Calendar.SEPTEMBER, 1), "Test 4", -100, a, d.getBudgetCategory("Groceries")));
			d.addTransaction(ModelFactory.createTransaction(
					DateFunctions.getDate(2007, Calendar.SEPTEMBER, 2), "Test 5", -100, a, d.getBudgetCategory("Groceries")));
			
			d.updateAllBalances();
			
			Object[][] tests = {
					{DateFunctions.getDate(2007, Calendar.JULY, 30), 0l},
					{DateFunctions.getDate(2007, Calendar.AUGUST, 1), -100l},
					{DateFunctions.getDate(2007, Calendar.AUGUST, 2), -100l},
					{DateFunctions.getDate(2007, Calendar.AUGUST, 4), -100l},
					{DateFunctions.getDate(2007, Calendar.AUGUST, 5), -200l},
					{DateFunctions.getDate(2007, Calendar.AUGUST, 6), -200l},
					{DateFunctions.getDate(2007, Calendar.AUGUST, 30), -100l},
					{DateFunctions.getDate(2007, Calendar.SEPTEMBER, 3), 100l}
			};
			
			for (Object[] test : tests) {
				assertEquals((Long) test[1], (Long) a.getBalance((Date) test[0]));
			}
		}
		catch (Exception e){
			fail("Exception: " + e);
		}
	}
	
	@Test
	public void accountStartDate() throws Exception {
		Document d = ModelFactory.createDocument();
		AccountType at = d.getAccountType("Cash");
		Account a = ModelFactory.createAccount("Test", at);
		BudgetCategory bc = d.getBudgetCategory("Groceries");
		d.addAccount(a);

		//StartDate == null, No transactions; return today
		// We wrap each in getStartOfDay() to avoid millisecond differences, if the get()
		// method takes a few millis.
		assertEquals(DateFunctions.getStartOfDay(a.getStartDate()), DateFunctions.getStartOfDay(new Date()));
		
		//StartDate != null, No transactions; return startDate
		Date startDate = DateFunctions.getDate(2007, Calendar.JANUARY, 3);
		a.setStartDate(startDate);
		assertEquals(startDate, a.getStartDate());
		
		//StartDate != null, Transactions exist, and are after startDate; return startDate
		Transaction t = ModelFactory.createTransaction(
				DateFunctions.getDate(2007, Calendar.JANUARY, 5), 
				"Test", 
				1234, 
				a,
				bc);
		d.addTransaction(t);
		assertEquals(a.getStartDate(), startDate);
		
		//StartDate != null, Transactions exist, and are before startDate; return first transaction
		t = ModelFactory.createTransaction(
				DateFunctions.getDate(2007, Calendar.JANUARY, 1), 
				"Test", 
				1234, 
				a,
				bc);
		d.addTransaction(t);
		assertEquals(a.getStartDate(), DateFunctions.getDate(2007, Calendar.JANUARY, 1));
		
		//StartDate == null, Transactions exist; return first transaction
		a.setStartDate(null);
		assertEquals(a.getStartDate(), DateFunctions.getDate(2007, Calendar.JANUARY, 1));
	}
	
}
