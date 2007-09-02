/*
 * Created on Aug 30, 2007 by wyatt
 */
package org.homeunix.thecave.buddi;

import org.homeunix.thecave.buddi.model.BudgetCategoryTest;
import org.homeunix.thecave.buddi.model.DocumentTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	DocumentTest.class,
	BudgetCategoryTest.class,
})
public class BuddiTests {}
