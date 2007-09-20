/*
 * Created on Aug 24, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.Calendar;
import java.util.Date;

import org.homeunix.thecave.buddi.i18n.keys.ScheduleFrequency;
import org.homeunix.thecave.buddi.model.impl.ModelFactory;
import org.homeunix.thecave.moss.util.DateFunctions;
import org.junit.Test;



public class ScheduledTransactionTest {

	@Test
	public void testScheduledTransactions(){
		try {

			Object[][] tests = {
					{ScheduleFrequency.SCHEDULE_FREQUENCY_EVERY_DAY.toString(), //Frequency
						DateFunctions.getDate(2007, Calendar.JANUARY, 1), //Start Date
						DateFunctions.getDate(2007, Calendar.JANUARY, 31), //End Date
						DateFunctions.getDate(2007, Calendar.FEBRUARY, 1), //Test Date
						-3100l},	//Expected amount
					{ScheduleFrequency.SCHEDULE_FREQUENCY_EVERY_DAY.toString(), //Frequency
						DateFunctions.getDate(2007, Calendar.JANUARY, 1), //Start Date
						DateFunctions.getDate(2007, Calendar.JANUARY, 31), //End Date
						DateFunctions.getDate(2007, Calendar.MARCH, 1), //Test Date
						-3100l},	//Expected amount
					{ScheduleFrequency.SCHEDULE_FREQUENCY_EVERY_WEEKDAY.toString(), //Frequency
						DateFunctions.getDate(2007, Calendar.JANUARY, 1), //Start Date
						DateFunctions.getDate(2007, Calendar.JANUARY, 31), //End Date
						DateFunctions.getDate(2007, Calendar.FEBRUARY, 1), //Test Date
						-2300l},	//Expected amount
					{ScheduleFrequency.SCHEDULE_FREQUENCY_BIWEEKLY.toString(), //Frequency
						DateFunctions.getDate(2007, Calendar.JANUARY, 1), //Start Date
						DateFunctions.getDate(2007, Calendar.JANUARY, 31), //End Date
						DateFunctions.getDate(2007, Calendar.FEBRUARY, 1), //Test Date
						-2300l},	//Expected amount
					{ScheduleFrequency.SCHEDULE_FREQUENCY_EVERY_WEEKDAY.toString(), //Frequency
						DateFunctions.getDate(2007, Calendar.JANUARY, 1), //Start Date
						DateFunctions.getDate(2007, Calendar.JANUARY, 31), //End Date
						DateFunctions.getDate(2007, Calendar.FEBRUARY, 1), //Test Date
						-2300l},	//Expected amount
			};

			for (Object[] test : tests){
				Document d = ModelFactory.createDocument();
				AccountType at = d.getAccountType("Cash");
				Account a1 = ModelFactory.createAccount("Test1", at);
				d.addAccount(a1);

				d.addScheduledTransaction(
						ModelFactory.createScheduledTransaction(
								"Test", 
								null, 
								(Date) test[1], 
								(Date) test[2], 
								(String) test[0], 
								0, 
								0, 
								0, 
								"Description", 
								100, 
								a1, 
								d.getBudgetCategory("Groceries")));


				d.updateScheduledTransactions();

				d.updateAllBalances();

				assertEquals((Long) test[4], (Long) a1.getBalance((Date) test[3]));
			}
		}
		catch (Exception e){
			fail("Exception: " + e);
		}
	}

}
