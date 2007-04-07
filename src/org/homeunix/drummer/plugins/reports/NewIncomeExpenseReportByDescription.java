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

import org.homeunix.drummer.controller.TransactionController;
import org.homeunix.drummer.controller.Translate;
import org.homeunix.drummer.controller.TranslateKeys;
import org.homeunix.drummer.model.Category;
import org.homeunix.drummer.model.Transaction;
import org.homeunix.drummer.model.impl.TransactionImpl;
import org.homeunix.drummer.plugins.BuddiPluginHelper.DateRangeType;
import org.homeunix.drummer.plugins.interfaces.BuddiReportPlugin;
import org.homeunix.drummer.view.components.DefaultTableCellRenderer;
import org.homeunix.drummer.view.components.DefaultTreeCellRenderer;
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
public class NewIncomeExpenseReportByDescription implements BuddiReportPlugin {
	
	public TreeTableModel getTreeTableModel(Date startDate, Date endDate) {		
		Vector<Transaction> transactions = TransactionController.getTransactions(startDate, endDate);
		Map<String, Long> descriptions = new HashMap<String, Long>();
		long total = 0;
		
		for (Transaction transaction : transactions) {
			//Sum up the amounts for each category.
			Category c = null;
			if (transaction.getFrom() instanceof Category)
				c = (Category) transaction.getFrom(); 
			else if (transaction.getTo() instanceof Category)
				c = (Category) transaction.getTo();
			
			if (c != null){
				String description = transaction.getDescription();
				Long l = descriptions.get(description);
				if (l == null){
					l = new Long(0);
				}
				
				if (c.isIncome()){
					total += transaction.getAmount();
					l += transaction.getAmount();
				}
				else{
					total -= transaction.getAmount();
					l -= transaction.getAmount();
				}
				descriptions.put(description, l);
				Log.debug("Added a source / destination");
			}
			else
				Log.debug("Didn't add anything...");
		}
		
		//Print the results
//		StringBuffer sb = new StringBuffer("<html><body><table>");
		DefaultMutableTreeNode root = new DefaultMutableTreeNode();
		ReportTreeTableModel model = new ReportTreeTableModel(root);
		
		Vector<String> descs = new Vector<String>(descriptions.keySet());
		Collections.sort(descs);
		
		for (String desc : descs) {
			long actual = descriptions.get(desc);
			ReportRow entry = new ReportRow(desc, actual);
			DefaultMutableTreeNode node = new DefaultMutableTreeNode(entry);
			root.add(node);
						
			//Add each transaction as a subitem of the total.
			Vector<Transaction> t = TransactionController.getTransactions(desc, startDate, endDate);
			for (Transaction transaction : t) {					
				DefaultMutableTreeNode transactionNode = new DefaultMutableTreeNode(transaction);
				node.add(transactionNode);
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
		
		tableCellRenderers.add(new NameTableCellRenderer());
		tableCellRenderers.add(new AmountTableCellRenderer());
		
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
		return TranslateKeys.REPORT_INCOME_EXPENSES_BY_DESCRIPTION.toString();
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
				return Translate.getInstance().get(TranslateKeys.AMOUNT);
			}
			else{
				return null;
			}
		}
		
		@Override
		public int getColumnCount() {
			return 3;
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
		private String name;
		private long amount;
		
		public ReportRow(String name, long amount) {
			this.name = name;
			this.amount = amount;
		}
		
		public long getAmount() {
			return amount;
		}
		public String getName() {
			return name;
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
					sb.append(Translate.getInstance().get(rr.getName()));
				}
				else if (o.getClass().equals(TransactionImpl.class)){
					Transaction t = (Transaction) o;
					startTableCellRendererComponent(value, isSelected, row, column, null, false);
					sb.append(Formatter.getInstance().getLengthFormat(25).format(t.getFrom()))
					.append(" ")
					.append(Translate.getInstance().get(TranslateKeys.TO))
					.append(" ")
					.append(Formatter.getInstance().getLengthFormat(25).format(t.getTo()));	

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
	
	class AmountTableCellRenderer extends DefaultTableCellRenderer {
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
					startTableCellRendererComponent(value, isSelected, row, column, (rr.getAmount() < 0 ? "red" : null), false);
					sb.append(Translate.getFormattedCurrency(rr.getAmount(), false));
				}
				else if (o.getClass().equals(TransactionImpl.class)){
					Transaction t = (Transaction) o;

					boolean isExpense;
					if (t.getTo() instanceof Category
							&& !((Category) t.getTo()).isIncome())
						isExpense = true;
					else
						isExpense = false;

					startTableCellRendererComponent(value, isSelected, row, column, (isExpense ? "red" : null), false);
					sb.append(Translate.getFormattedCurrency(t.getAmount(), false));
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
