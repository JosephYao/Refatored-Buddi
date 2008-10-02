/*
 * Created on Aug 24, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.homeunix.thecave.buddi.model.impl.AccountImpl;
import org.homeunix.thecave.buddi.model.impl.AccountTypeImpl;
import org.homeunix.thecave.buddi.model.impl.BudgetCategoryImpl;
import org.homeunix.thecave.buddi.model.impl.SplitImpl;
import org.homeunix.thecave.buddi.model.impl.TransactionImpl;
import org.homeunix.thecave.buddi.model.impl.TransactionSplitImpl;
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
			t.setSplits(splits);
			
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
			t.setSplits(splits);
			
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
}
