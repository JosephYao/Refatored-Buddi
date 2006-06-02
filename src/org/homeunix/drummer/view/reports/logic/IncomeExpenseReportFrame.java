/*
 * Created on May 19, 2006 by wyatt
 */
package org.homeunix.drummer.view.reports.logic;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import org.homeunix.drummer.Translate;
import org.homeunix.drummer.controller.DataInstance;
import org.homeunix.drummer.model.Category;
import org.homeunix.drummer.model.Transaction;
import org.homeunix.drummer.util.DateUtil;
import org.homeunix.drummer.util.Formatter;
import org.homeunix.drummer.util.Log;
import org.homeunix.drummer.view.AbstractBudgetFrame;
import org.homeunix.drummer.view.reports.layout.ReportFrameLayout;

public class IncomeExpenseReportFrame extends ReportFrameLayout {
	public static final long serialVersionUID = 0;
	
	public IncomeExpenseReportFrame(Date startDate, Date endDate){
		super(startDate, endDate);
	}
	
	@Override
	protected String buildReport(Date startDate, Date endDate) {
		Vector<Transaction> transactions = DataInstance.getInstance().getTransactions(startDate, endDate);
		Map<Category, Long> categories = new HashMap<Category, Long>();
		
		//This map is where we store the totals for this time period.
		for (Category category : DataInstance.getInstance().getCategories()) {
			categories.put(category, new Long(0));
		}
		
		for (Transaction transaction : transactions) {
			//Sum up the amounts for each category.
			if (transaction.getFrom() instanceof Category){
				Category c = (Category) transaction.getFrom();
				Long l = categories.get(c);
				l += transaction.getAmount();
				categories.put(c, l);
				Log.debug("Added a source");
			}
			else if (transaction.getTo() instanceof Category){
				Category c = (Category) transaction.getTo();
				Long l = categories.get(c);
				l += transaction.getAmount();
				categories.put(c, l);
				Log.debug("Added a destination");
			}
			else
				Log.debug("Didn't add anything...");
		}
		
		//Print the results
		StringBuffer sb = new StringBuffer("<html><body><table>");
		
		Vector<Category> cats = new Vector<Category>(categories.keySet());
		Collections.sort(cats);
		
		sb.append("<tr><th>Category</th><th>Budgeted</th><th>Actual</th><th>Difference</th></tr>");
		
		int numberOfMonths = DateUtil.monthsBetween(startDate, endDate) + 1;
		
		for (Category c : cats) {
			sb.append("<tr><td>").append(Translate.inst().get(c.getName())).append("</td>");
			long budgetedAmount = c.getBudgetedAmount() * numberOfMonths;
			sb.append("<td>")
					.append(Formatter.getInstance().getDecimalFormat().format(budgetedAmount / 100))
					.append("</td>");
			sb.append("<td>")
					.append(Formatter.getInstance().getDecimalFormat().format((double) categories.get(c) / 100))
					.append("</td>");
			long difference = (budgetedAmount - categories.get(c)); 
			if (c.isIncome() && difference != 0)
				difference *= -1;	
			sb.append("<td>")
					.append((difference < 0 ? "<font color='red'>" : ""))
					.append(Formatter.getInstance().getDecimalFormat().format((double) (difference) / 100))
					.append((difference < 0 ? "</font>" : ""))
					.append("</td>");
		}
		
		sb.append("</table></body></html>");
		
		return sb.toString();
	}

	@Override
	protected AbstractBudgetFrame initContent() {
		return this;
	}

	@Override
	public AbstractBudgetFrame updateButtons() {
		return this;
	}

	@Override
	public AbstractBudgetFrame updateContent() {
		return this;
	}
}
