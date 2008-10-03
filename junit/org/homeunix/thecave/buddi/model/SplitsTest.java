/*
 * Created on Aug 24, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.model;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.homeunix.thecave.buddi.model.impl.AccountImpl;
import org.homeunix.thecave.buddi.model.impl.AccountTypeImpl;
import org.homeunix.thecave.buddi.model.impl.BudgetCategoryImpl;
import org.homeunix.thecave.buddi.model.impl.ModelFactory;
import org.homeunix.thecave.buddi.model.impl.SplitImpl;
import org.homeunix.thecave.buddi.model.impl.TransactionImpl;
import org.homeunix.thecave.buddi.model.impl.TransactionSplitImpl;
import org.homeunix.thecave.buddi.plugin.api.exception.ModelException;
import org.homeunix.thecave.moss.data.collection.SortedArrayList;
import org.junit.Test;



public class SplitsTest {

	@Test
	public void testCompare() throws Exception {
		List<Transaction> transactions = new SortedArrayList<Transaction>();
		
		Date date = new Date();
		Account a = new AccountImpl();
		BudgetCategory bc1 = new BudgetCategoryImpl();
		BudgetCategory bc2 = new BudgetCategoryImpl();
		bc1.setName("Foo Budget Category 1");
		bc2.setName("Foo Budget Category 2");
		
		BudgetCategory bc3 = new BudgetCategoryImpl();
		BudgetCategory bc4 = new BudgetCategoryImpl();
		bc3.setName("Foo Budget Category 3");
		bc4.setName("Foo Budget Category 4");
		bc3.setIncome(true);
		bc4.setIncome(true);
		
		a.setName("Foo Account");
		a.setAccountType(new AccountTypeImpl());
		a.getAccountType().setName("Chequing");
		a.getAccountType().setCredit(false);
		
		
		//Splitting To - all attributes are the same
		for(int i = 0; i < 1000; i++){
			Transaction t = new TransactionImpl();
			t.setAmount(1234l);
			t.setDate(date);
			t.setDescription("Foo");
			t.setFrom(a);
			t.setTo(new SplitImpl());
			List<TransactionSplit> splits = new ArrayList<TransactionSplit>();
			TransactionSplit split = new TransactionSplitImpl();
			split.setAmount(1200);
			split.setSource(bc1);
			splits.add(split);
			split = new TransactionSplitImpl();
			split.setAmount(34);
			split.setSource(bc2);
			splits.add(split);
			t.setToSplits(splits);
			
			transactions.add(t);
		}
		
		//Splitting From - All attributes are the same
		for(int i = 0; i < 1000; i++){
			Transaction t = new TransactionImpl();
			t.setAmount(1234l);
			t.setDate(date);
			t.setDescription("Foo");
			t.setTo(a);
			t.setFrom(new SplitImpl());
			List<TransactionSplit> splits = new ArrayList<TransactionSplit>();
			TransactionSplit split = new TransactionSplitImpl();
			split.setAmount(1200);
			split.setSource(bc1);
			splits.add(split);
			split = new TransactionSplitImpl();
			split.setAmount(34);
			split.setSource(bc2);
			splits.add(split);
			t.setFromSplits(splits);
			
			transactions.add(t);
		}
		
		
		while(transactions.size() > 0){
			int index = (int) (Math.random() * transactions.size());
			Transaction t = transactions.get(index);
			
			transactions.remove(t);
			
			if (transactions.contains(t))
				throw new Exception("We already removed the transaction!");
		}
	}
	
	@Test
	public void testDocument(){
		try {
			Document d = ModelFactory.createDocument();
			
			BudgetCategory bc1 = new BudgetCategoryImpl();
			BudgetCategory bc2 = new BudgetCategoryImpl();
			BudgetCategory bc3 = new BudgetCategoryImpl();
			BudgetCategory bc4 = new BudgetCategoryImpl();			
			bc1.setName("Foo Budget Category 1");
			bc2.setName("Foo Budget Category 2");
			bc3.setName("Foo Budget Category 3");
			bc4.setName("Foo Budget Category 4");
			bc3.setIncome(true);
			bc4.setIncome(true);
			
			Account a1 = new AccountImpl();
			Account a2 = new AccountImpl();
			a1.setName("Foo Account");
			a2.setName("Bar Account");
			a1.setAccountType(new AccountTypeImpl());
			a2.setAccountType(new AccountTypeImpl());
			a1.getAccountType().setName("Chequing");
			a1.getAccountType().setName("Cash");
			a1.getAccountType().setCredit(false);
			a2.getAccountType().setCredit(false);
		
		
			d.addAccount(a1);
			d.addAccount(a2);
			d.addBudgetCategory(bc1);
			d.addBudgetCategory(bc2);
			d.addBudgetCategory(bc3);
			d.addBudgetCategory(bc4);
			
			//Try adding a transaction with no splits
			Transaction t = new TransactionImpl();
			t.setAmount(1234l);
			t.setDate(new Date());
			t.setDescription("Foo");
			t.setTo(a1);
			t.setFrom(bc3);
			d.addTransaction(t);
			
			//Transaction with From splits
			t = new TransactionImpl();
			t.setAmount(1234l);
			t.setDate(new Date());
			t.setDescription("Foo");
			t.setTo(a1);
			t.setFrom(new SplitImpl());
			List<TransactionSplit> splits = new ArrayList<TransactionSplit>();
			TransactionSplit split = new TransactionSplitImpl();
			split.setAmount(1200);
			split.setSource(bc1);
			splits.add(split);
			split = new TransactionSplitImpl();
			split.setAmount(34);
			split.setSource(bc2);
			splits.add(split);
			t.setFromSplits(splits);
			d.addTransaction(t);
			
			//Transaction with To splits
			t = new TransactionImpl();
			t.setAmount(1234l);
			t.setDate(new Date());
			t.setDescription("Foo");
			t.setFrom(a1);
			t.setTo(new SplitImpl());
			splits = new ArrayList<TransactionSplit>();
			split = new TransactionSplitImpl();
			split.setAmount(1200);
			split.setSource(bc1);
			splits.add(split);
			split = new TransactionSplitImpl();
			split.setAmount(34);
			split.setSource(bc2);
			splits.add(split);
			t.setToSplits(splits);
			d.addTransaction(t);
			
			//Transaction with both splits
			t = new TransactionImpl();
			t.setAmount(1234l);
			t.setDate(new Date());
			t.setDescription("Foo");
			t.setTo(new SplitImpl());
			t.setFrom(new SplitImpl());
			splits = new ArrayList<TransactionSplit>();
			split = new TransactionSplitImpl();
			split.setAmount(1200);
			split.setSource(a1);
			splits.add(split);
			split = new TransactionSplitImpl();
			split.setAmount(34);
			split.setSource(a2);
			splits.add(split);
			t.setToSplits(splits);
			splits = new ArrayList<TransactionSplit>();
			split = new TransactionSplitImpl();
			split.setAmount(1200);
			split.setSource(bc1);
			splits.add(split);
			split = new TransactionSplitImpl();
			split.setAmount(34);
			split.setSource(bc2);
			splits.add(split);
			t.setFromSplits(splits);			
			d.addTransaction(t);
			
			try {
				t = new TransactionImpl();
				t.setAmount(1234l);
				t.setDate(new Date());
				t.setDescription("Foo");
				t.setFrom(a1);
				t.setTo(new SplitImpl());
				splits = new ArrayList<TransactionSplit>();
				split = new TransactionSplitImpl();
				split.setAmount(1200);
				split.setSource(bc1);
				splits.add(split);
				split = new TransactionSplitImpl();
				split.setAmount(3400);
				split.setSource(bc2);
				splits.add(split);
				t.setToSplits(splits);
				d.addTransaction(t);
				
				fail("Succeeded in adding transaction with unbalanced splits");
			}
			catch (ModelException me){
				//Expected to throw this.
			}
			
			assertTrue(d.getTransactions().size() == 4);
			
		}
		catch (Exception e){
			fail("Exception: " + e);
		}
	}
}
