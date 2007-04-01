/*
 * Created on Sep 14, 2006 by wyatt
 */
package org.homeunix.drummer.plugins.reports;

import java.awt.Component;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeCellRenderer;

import org.homeunix.drummer.controller.Translate;
import org.homeunix.drummer.controller.TranslateKeys;
import org.homeunix.drummer.model.Category;
import org.homeunix.drummer.model.DataInstance;
import org.homeunix.drummer.model.Transaction;
import org.homeunix.drummer.model.impl.TransactionImpl;
import org.homeunix.drummer.plugins.BuddiPluginHelper.DateRangeType;
import org.homeunix.drummer.plugins.interfaces.BuddiReportPlugin;
import org.homeunix.drummer.prefs.Interval;
import org.homeunix.drummer.prefs.PrefsInstance;
import org.homeunix.drummer.view.components.DefaultTableCellRenderer;
import org.homeunix.drummer.view.components.DefaultTreeCellRenderer;
import org.homeunix.thecave.moss.util.DateUtil;
import org.homeunix.thecave.moss.util.Formatter;
import org.homeunix.thecave.moss.util.Log;
import org.jdesktop.swingx.treetable.DefaultTreeTableModel;
import org.jdesktop.swingx.treetable.TreeTableModel;

/**
 * Built-in plugin.  Feel free to use this as an example on how to make
 * report plugins (although this one is kind of ugly, so you may not 
 * want to use it..)
 * 
 * @author wyatt
 *
 */
public class NewIncomeExpenseReportByCategory implements BuddiReportPlugin {
	
	public TreeTableModel getTreeTableModel(Date startDate, Date endDate) {		
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
//		StringBuffer sb = new StringBuffer("<html><body><table>");
		DefaultMutableTreeNode root = new DefaultMutableTreeNode();
		ReportTreeTableModel model = new ReportTreeTableModel(root);

		Vector<Category> cats = new Vector<Category>(categories.keySet());
		Collections.sort(cats);

		long numberOfBudgetPeriods;
		Interval interval = PrefsInstance.getInstance().getSelectedInterval();
		if (!interval.isDays()){
			numberOfBudgetPeriods = (DateUtil.monthsBetween(startDate, endDate) + 1) / interval.getLength();
		}
		else{
			numberOfBudgetPeriods = (DateUtil.daysBetween(startDate, endDate) + 1) / interval.getLength();
		}

		long total = 0;

		for (Category c : cats) {
			if (c.getBudgetedAmount() > 0 || categories.get(c) > 0){
				long budgeted = c.getBudgetedAmount() * numberOfBudgetPeriods;
				long actual = categories.get(c);
				long difference = (budgeted - actual);
				if (c.isIncome())
					total += actual;
				else
					total -= actual;
				ReportRow rr = new ReportRow(c, budgeted, actual, difference);
				DefaultMutableTreeNode node = new DefaultMutableTreeNode(rr);
				root.add(node);

				//Add each transaction as a subitem of the total.
				Vector<Transaction> t = DataInstance.getInstance().getTransactions(c, startDate, endDate);
				for (Transaction transaction : t) {					
					DefaultMutableTreeNode transactionNode = new DefaultMutableTreeNode(transaction);
					node.add(transactionNode);
				}
			}
		}

		root.add(new DefaultMutableTreeNode(null));
		ReportTotal ret = new ReportTotal(total);
		DefaultMutableTreeNode totalNode = new DefaultMutableTreeNode(ret);
		root.add(totalNode);

		return model;
	}
	
	public List<TableCellRenderer> getTableCellRenderers() {
		List<TableCellRenderer> tableCellRenderers = new LinkedList<TableCellRenderer>();		
		
		//For this report, we must include renderers for either 
		// ReportRow (which has the name, budget, actual, and difference),
		// or Transactions (which just will show description).
		tableCellRenderers.add(new NameTableCellRenderer());
		tableCellRenderers.add(new BudgetedTableCellRenderer());
		tableCellRenderers.add(new ActualTableCellRenderer());
		tableCellRenderers.add(new DifferenceTableCellRenderer());
		
		return tableCellRenderers;
	}
	
	public TreeCellRenderer getTreeCellRenderer() {
		return new DefaultTreeCellRenderer();
	}

	public DateRangeType getDateRangeType() {
		return DateRangeType.INTERVAL;
	}

	public String getTitle() {
		return Translate.getInstance().get(TranslateKeys.INCOME_AND_EXPENSES_BY_CATEGORY_TITLE);
	}

	public String getDescription() {
		return TranslateKeys.REPORT_INCOME_EXPENSES_BY_CATEGORY.toString();
	}

	
	public class ReportTreeTableModel extends DefaultTreeTableModel {

		private final DefaultMutableTreeNode root;
		
		public ReportTreeTableModel() {
			this(new DefaultMutableTreeNode());
		}
		
		public ReportTreeTableModel(DefaultMutableTreeNode root){
			this.root = root;
		}
		
		public DefaultMutableTreeNode getRoot(){
			return root;
		}
		
		@Override
		public String getColumnName(int arg0) {
			if (arg0 == 0){
				return " ";
			}
			else if (arg0 == 1){
				return Translate.getInstance().get(TranslateKeys.NAME);
			}
			else if (arg0 == 2){
				return Translate.getInstance().get(TranslateKeys.BUDGETED);
			}
			else if (arg0 == 3){
				return Translate.getInstance().get(TranslateKeys.ACTUAL);
			}
			else if (arg0 == 4){
				return Translate.getInstance().get(TranslateKeys.DIFFERENCE);
			}
			else{
				return null;
			}
		}
		
		@Override
		public int getColumnCount() {
			return 5;
		}
		
		public Object getValueAt(Object arg0, int arg1) {
			if (arg0 instanceof DefaultMutableTreeNode){
				DefaultMutableTreeNode node = (DefaultMutableTreeNode) arg0;

				return node;
			}
			
			return null;
		}
	}
	
	class ReportRow {
		private Category category;
		private long budgeted;
		private long actual;
		private long difference;
		
		public ReportRow(Category category, long budgeted, long actual, long difference) {
			this.category = category;
			this.budgeted = budgeted;
			this.actual = actual;
			this.difference = difference;
		}
		
		public long getActual() {
			return actual;
		}
		public long getBudgeted() {
			return budgeted;
		}
		public long getDifference() {
			return difference;
		}
		public Category getCategory() {
			return category;
		}
	}
	
	class ReportTotal {
		private long total;
		
		public ReportTotal(long total) {
			this.total = total;
		}
		
		public long getTotal(){
			return total;
		}
	}
	
	class NameTableCellRenderer extends DefaultTableCellRenderer {
		public static final long serialVersionUID = 0;
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
			super.getTableCellRendererComponent(table, value, isSelected, hasFocus,	row, column);
			
			if (value.getClass().equals(DefaultMutableTreeNode.class)){
				DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
				Object o = node.getUserObject();
			
				if (o == null){
					this.setText("");
					return this;
				}
				else if (o.getClass().equals(ReportRow.class)){
					ReportRow rr = (ReportRow) o;
					startTableCellRendererComponent(value, isSelected, row, column, null, false);
					sb.append(Translate.getInstance().get(rr.getCategory().toString()));
				}
				else if (o.getClass().equals(TransactionImpl.class)){
					Transaction t = (Transaction) o;
					startTableCellRendererComponent(value, isSelected, row, column, null, false);
					sb.append(t.getDescription());
				}
				else if (o.getClass().equals(ReportTotal.class)){
					startTableCellRendererComponent(value, isSelected, row, column, null, false);
					sb.append(Translate.getInstance().get(TranslateKeys.TOTAL));
				}
				else {
					startTableCellRendererComponent(value, isSelected, row, column, null, false);
				}
			}
			
			endTableCellRendererComponent();
			return this;
		}
	}
	
	class BudgetedTableCellRenderer extends DefaultTableCellRenderer {
		public static final long serialVersionUID = 0;
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
			super.getTableCellRendererComponent(table, value, isSelected, hasFocus,	row, column);
			
			if (value.getClass().equals(DefaultMutableTreeNode.class)){
				DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
				Object o = node.getUserObject();
			
				if (o == null){
					this.setText("");
					return this;
				}
				else if (o.getClass().equals(ReportRow.class)){
					ReportRow rr = (ReportRow) o;
					startTableCellRendererComponent(value, isSelected, row, column, null, false);
					sb.append(Translate.getFormattedCurrency(rr.getBudgeted()));
				}
				else if (o.getClass().equals(TransactionImpl.class)){
					Transaction t = (Transaction) o;
					startTableCellRendererComponent(value, isSelected, row, column, null, false);
					sb.append(Formatter.getInstance().getDateFormat().format(t.getDate()));
				}
				else {
					startTableCellRendererComponent(value, isSelected, row, column, null, false);
				}
			}
			
			endTableCellRendererComponent();
			return this;
		}
	}
	
	class ActualTableCellRenderer extends DefaultTableCellRenderer {
		public static final long serialVersionUID = 0;
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
			super.getTableCellRendererComponent(table, value, isSelected, hasFocus,	row, column);
			
			if (value.getClass().equals(DefaultMutableTreeNode.class)){
				DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
				Object o = node.getUserObject();
			
				if (o == null){
					this.setText("");
					return this;
				}
				else if (o.getClass().equals(ReportRow.class)){
					ReportRow rr = (ReportRow) o;
					startTableCellRendererComponent(value, isSelected, row, column, null, false);
					sb.append(Translate.getFormattedCurrency(rr.getActual()));
				}
				else if (o.getClass().equals(TransactionImpl.class)){
					Transaction t = (Transaction) o;
					startTableCellRendererComponent(value, isSelected, row, column, null, false);
					sb.append(Translate.getFormattedCurrency(t.getAmount()));
				}
				else {
					startTableCellRendererComponent(value, isSelected, row, column, null, false);
				}
			}
			
			endTableCellRendererComponent();
			return this;
		}
	}
	
	class DifferenceTableCellRenderer extends DefaultTableCellRenderer {
		public static final long serialVersionUID = 0;
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
			super.getTableCellRendererComponent(table, value, isSelected, hasFocus,	row, column);
			
			if (value.getClass().equals(DefaultMutableTreeNode.class)){
				DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
				Object o = node.getUserObject();
			
				if (o == null){
					this.setText("");
					return this;
				}
				else if (o.getClass().equals(ReportRow.class)){
					ReportRow rr = (ReportRow) o;
					 
					long difference = rr.getDifference();
					
					if (rr.getCategory().isIncome() && rr.getDifference() != 0){
						difference *= -1;
					}
					
					startTableCellRendererComponent(value, isSelected, row, column, (difference < 0 ? "red" : null), false);
					sb.append(Translate.getFormattedCurrency(rr.getDifference()));
				}
				else if (o.getClass().equals(ReportTotal.class)){
					ReportTotal rt = (ReportTotal) o;
					startTableCellRendererComponent(value, isSelected, row, column, null, false);
					sb.append(Translate.getFormattedCurrency(rt.getTotal()));
				}
				else {
					startTableCellRendererComponent(value, isSelected, row, column, null, false);
				}
			}
			
			endTableCellRendererComponent();
			return this;
		}
	}
}
