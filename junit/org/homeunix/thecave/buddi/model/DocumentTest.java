/*
 * Created on Aug 24, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.util.List;

import org.homeunix.thecave.buddi.i18n.keys.BudgetCategoryTypes;
import org.homeunix.thecave.buddi.model.impl.ModelFactory;
import org.homeunix.thecave.moss.util.DateFunctions;
import org.homeunix.thecave.moss.util.Log;
import org.junit.Before;
import org.junit.Test;



public class DocumentTest {

	private Document d;

	@Before
	public void setup() throws Exception{
		d = ModelFactory.createDocument();
	}

	@Test
	public void testDocument(){
		try {
			assertTrue(d.getBudgetCategories().size() > 1);

		}
		catch (Exception e){
			fail("Exception: " + e);
		}
	}

	@Test
	public void testClone() throws Exception {
		Document model = ModelFactory.createDocument();

		for (int i = 0; i < 5; i++){
			Account a = ModelFactory.createAccount("ACME " + i, model.getAccountTypes().get((int) (Math.random() * model.getAccountTypes().size())));
			model.addAccount(a);				
		}

		for (int i = 0; i < 15; i++){
			BudgetCategory bc = ModelFactory.createBudgetCategory("ACME" + i, ModelFactory.getBudgetCategoryType(BudgetCategoryTypes.BUDGET_CATEGORY_TYPE_MONTH), (Math.random() > 0.5));
			model.addBudgetCategory(bc);
		}

		List<BudgetCategory> budgetCategories = model.getBudgetCategories();
		List<Account> accounts = model.getAccounts();

		int MAX = 2000;
		for (int i = 0; i < MAX; i++){
			if (i % 100 == 0)
				Log.info("Creating transaction " + i + " of " + MAX);
			Account a = accounts.get((int) (Math.random() * accounts.size()));
			BudgetCategory bc = budgetCategories.get((int) (Math.random() * budgetCategories.size()));
			Transaction t = ModelFactory.createTransaction(DateFunctions.getDate(2006, (int) (Math.random() * 12), (int) (Math.random() * 28)), "Test Transaction " + i, (long) (Math.random() * 1000000), a, bc);
			t.setMemo(Math.random() + "");
			model.addTransaction(t);
		}
		
//		long start = System.currentTimeMillis();
		Document clonedModel = model.clone();
//		long end = System.currentTimeMillis();
//		System.out.println("Time to clone: " + (end - start) + " millis.");
		
		File f = new File("temp.test");
//		start = System.currentTimeMillis();
//		model.saveAs(f);
//		end = System.currentTimeMillis();
//		System.out.println("Time to save: " + (end - start) + " millis.");
		f.delete();
		
		if (clonedModel == model)
			throw new Exception("Cloned object must not be identical to original!");
		
		for (Account a1 : model.getAccounts()) {
			for (Account a2 : clonedModel.getAccounts()) {
				if (a1 == a2)
					throw new Exception("Cloned object must not be identical to original!");
				if (a1.equals(a2))
					throw new Exception("Cloned object must not be equal to original!");
			}
		}
		
		for (BudgetCategory bc1 : model.getBudgetCategories()) {
			for (BudgetCategory bc2 : clonedModel.getBudgetCategories()) {
				if (bc1 == bc2)
					throw new Exception("Cloned object must not be identical to original!");
				if (bc1.equals(bc2))
					throw new Exception("Cloned object must not be equal to original!");
			}
		}
		
		for (Transaction t1 : model.getTransactions()) {
			for (Transaction t2 : clonedModel.getTransactions()) {
				if (t1 == t2)
					throw new Exception("Cloned object must not be identical to original!");
				if (t1.equals(t2))
					throw new Exception("Cloned object must not be equal to original!");				
			}
		}
		
		assertEquals(model.getAccounts().size(), clonedModel.getAccounts().size());
		assertEquals(model.getAccountTypes().size(), clonedModel.getAccountTypes().size());
		assertEquals(model.getBudgetCategories().size(), clonedModel.getBudgetCategories().size());
		assertEquals(model.getTransactions().size(), clonedModel.getTransactions().size());
		assertEquals(model.getScheduledTransactions().size(), clonedModel.getScheduledTransactions().size());
		
		for (int i = 0; i < model.getAccounts().size(); i++){
			assertEquals(model.getAccounts().get(i).getName(), clonedModel.getAccounts().get(i).getName());
			assertEquals(model.getAccounts().get(i).isDeleted(), clonedModel.getAccounts().get(i).isDeleted());
			assertEquals(model.getAccounts().get(i).getBalance(), clonedModel.getAccounts().get(i).getBalance());
		}
		
		for (int i = 0; i < model.getTransactions().size(); i++){
			assertEquals(model.getTransactions().get(i).getDescription(), clonedModel.getTransactions().get(i).getDescription());
			assertEquals(model.getTransactions().get(i).getAmount(), clonedModel.getTransactions().get(i).getAmount());
			assertEquals(model.getTransactions().get(i).getMemo(), clonedModel.getTransactions().get(i).getMemo());
			assertEquals(model.getTransactions().get(i).getTo().getName(), clonedModel.getTransactions().get(i).getTo().getName());
			assertEquals(model.getTransactions().get(i).getFrom().getName(), clonedModel.getTransactions().get(i).getFrom().getName());
		}
		
//		ByteArrayOutputStream modelStream = new ByteArrayOutputStream();
//		ByteArrayOutputStream clonedModelStream = new ByteArrayOutputStream();
//		model.saveToStream(modelStream);
//		clonedModel.saveToStream(clonedModelStream);
//		
//		System.out.println(modelStream.toString().replaceAll("[ \\n\\t]", "").substring(1, 10000));
//		System.out.println(clonedModelStream.toString().replaceAll("[ \\n\\t]", "").substring(1, 10000));
//		
//		assertEquals(modelStream.toString().length(), clonedModelStream.toString().length());
	}
}
