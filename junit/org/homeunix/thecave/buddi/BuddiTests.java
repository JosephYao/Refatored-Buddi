/*
 * Created on Aug 30, 2007 by wyatt
 */
package org.homeunix.thecave.buddi;

import org.homeunix.thecave.buddi.model.AccountTest;
import org.homeunix.thecave.buddi.model.BudgetCategoryTest;
import org.homeunix.thecave.buddi.model.DocumentTest;
import org.homeunix.thecave.buddi.model.ScheduledTransactionTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	DocumentTest.class,
	AccountTest.class,
	BudgetCategoryTest.class,
	ScheduledTransactionTest.class,
})
public class BuddiTests {}
