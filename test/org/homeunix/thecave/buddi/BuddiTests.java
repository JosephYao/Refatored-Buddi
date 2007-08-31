/*
 * Created on Aug 30, 2007 by wyatt
 */
package org.homeunix.thecave.buddi;

import org.homeunix.thecave.buddi.model.DataModelTest;
import org.homeunix.thecave.buddi.plugin.api.util.BudgetCalculatorTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	DataModelTest.class,
	BudgetCalculatorTest.class
})
public class BuddiTests {}
