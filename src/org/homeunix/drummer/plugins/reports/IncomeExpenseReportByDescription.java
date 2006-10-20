/*
 * Created on Sep 14, 2006 by wyatt
 */
package org.homeunix.drummer.plugins.reports;

import java.awt.Color;
import java.awt.Component;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreeModel;

import org.homeunix.drummer.Buddi;
import org.homeunix.drummer.controller.Translate;
import org.homeunix.drummer.controller.TranslateKeys;
import org.homeunix.drummer.model.Category;
import org.homeunix.drummer.model.DataInstance;
import org.homeunix.drummer.model.Transaction;
import org.homeunix.drummer.plugins.BuddiHTMLPluginHelper;
import org.homeunix.drummer.plugins.BuddiPluginHelper.DateRangeType;
import org.homeunix.drummer.plugins.interfaces.BuddiReportPlugin;
import org.homeunix.drummer.prefs.PrefsInstance;
import org.homeunix.thecave.moss.util.Formatter;
import org.homeunix.thecave.moss.util.Log;

/**
 * Built-in plugin.  Feel free to use this as an example on how to make
 * report plugins (although this one is kind of ugly, so you may not 
 * want to use it..)
 * 
 * @author wyatt
 *
 */
public class IncomeExpenseReportByDescription implements BuddiReportPlugin {
	
	/* (non-Javadoc)
	 * @see org.homeunix.drummer.plugins.interfaces.BuddiReportPlugin#getReportHTML(java.util.Date, java.util.Date)
	 */
	public String getReportHTML(Date startDate, Date endDate) {
		StringBuffer sb = BuddiHTMLPluginHelper.getHtmlHeader(TranslateKeys.REPORT_BY_DESCRIPTION_HEADER, startDate, endDate);

		sb.append("<table class='main'>\n");

		TreeModel tm = getReportTreeModel(startDate, endDate);
		if (tm.getRoot() instanceof DefaultMutableTreeNode){
			DefaultMutableTreeNode root = (DefaultMutableTreeNode) getReportTreeModel(startDate, endDate).getRoot();

			sb.append("<tr><th>")
			.append(Translate.getInstance().get(TranslateKeys.DESCRIPTION))
			.append("</th><th>")
			.append(Translate.getInstance().get(TranslateKeys.AMOUNT))
			.append("</th></tr>\n");
			
			for (int i = 0; i < root.getChildCount(); i++) {
				DefaultMutableTreeNode child = (DefaultMutableTreeNode) root.getChildAt(i);
				Object userObject = child.getUserObject();
				if (userObject instanceof IncomeExpenseReportEntry){
					IncomeExpenseReportEntry entry = (IncomeExpenseReportEntry) userObject;
					
					sb.append("<tr><td>")
							.append(Translate.getInstance().get(entry.getDescription().toString()))
							.append("</td>")
							.append(entry.getActual() < 0 ? "<td class='red'>" : "<td>")
							.append(PrefsInstance.getInstance().getPrefs().getCurrencySymbol())
							.append(Formatter.getInstance().getDecimalFormat().format(Math.abs((double) entry.getActual() / 100.0)))
							.append("</td></tr>\n");
					
					if (child.getChildCount() > 0){
						sb.append("<tr><td colspan=2><table class='transactions'>");
						for (int j = 0; j < child.getChildCount(); j++) {
							sb.append("<tr>");
							DefaultMutableTreeNode subChild = (DefaultMutableTreeNode) child.getChildAt(j);
							Object subUserObject = subChild.getUserObject();
							if (subUserObject instanceof Transaction){
								Transaction t = (Transaction) subUserObject;
								
								boolean isExpense;
								if (t.getTo() instanceof Category
										&& !((Category) t.getTo()).isIncome())
									isExpense = true;
								else
									isExpense = false;
								
								sb.append("<td>")
										.append(t.getFrom())
										.append(" ")
										.append(Translate.getInstance().get(TranslateKeys.TO))
										.append(" ")
										.append(t.getTo())							
										.append("</td>")
										.append((isExpense ? "<td class='red'>" : "<td>"))
										.append(PrefsInstance.getInstance().getPrefs().getCurrencySymbol())
										.append(Formatter.getInstance().getDecimalFormat().format(Math.abs((double) t.getAmount() / 100.0)))
										.append("</td>");
							}
							sb.append("</tr>");
						}
						sb.append("</table></td></tr>");
					}
				}
				else if (userObject instanceof ReportEntryTotal){
					ReportEntryTotal entry = (ReportEntryTotal) userObject;
					
					sb.append(
							"<tr><th><b>")
							.append(Translate.getInstance().get(TranslateKeys.TOTAL))
							.append("</b></th>")
							.append((entry.getTotal() < 0 ? "<th class='red'>" : "<th>"))
							.append("<b>")
							.append(PrefsInstance.getInstance().getPrefs().getCurrencySymbol())
							.append(Formatter.getInstance().getDecimalFormat().format(Math.abs((double) entry.getTotal() / 100.0)))
							.append("</b></th>")
							.append("</tr>");
				}
			}
		}
		
		sb.append("</table>\n</body>\n</html>");
		
		return sb.toString();
	}

	/* (non-Javadoc)
	 * @see org.homeunix.drummer.plugins.interfaces.BuddiReportPlugin#getReportTreeModel(java.util.Date, java.util.Date)
	 */
	public TreeModel getReportTreeModel(Date startDate, Date endDate) {
		Vector<Transaction> transactions = DataInstance.getInstance().getTransactions(startDate, endDate);
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
		TreeModel model = new DefaultTreeModel(root);
		
		Vector<String> descs = new Vector<String>(descriptions.keySet());
		Collections.sort(descs);
		
		for (String desc : descs) {
			long actual = descriptions.get(desc);
			IncomeExpenseReportEntry entry = new IncomeExpenseReportEntry(desc, actual);
			DefaultMutableTreeNode node = new DefaultMutableTreeNode(entry);
			root.add(node);
						
			//Add each transaction as a subitem of the total.
			Vector<Transaction> t = DataInstance.getInstance().getTransactions(desc, startDate, endDate);
			for (Transaction transaction : t) {					
				DefaultMutableTreeNode transactionNode = new DefaultMutableTreeNode(transaction);
				node.add(transactionNode);
			}
		}
		
		root.add(new DefaultMutableTreeNode(null));
		ReportEntryTotal ret = new ReportEntryTotal(total);
		DefaultMutableTreeNode totalNode = new DefaultMutableTreeNode(ret);
		root.add(totalNode);
				
		return model;
	}

	public TreeCellRenderer getTreeCellRenderer() {
		return new ReportTreeCellRenderer();
	}

	public DateRangeType getDateRangeType() {
		return DateRangeType.INTERVAL;
	}

	public String getTitle() {
		return "";
	}

	public String getDescription() {
		return TranslateKeys.REPORT_INCOME_EXPENSES_BY_DESCRIPTION.toString();
	}

	public class ReportEntryTotal {
		private long total;

		public ReportEntryTotal(long total){
			this.total = total;
		}

		public long getTotal() {
			return total;
		}

		public void setTotal(long total) {
			this.total = total;
		}
	}
	
	public class IncomeExpenseReportEntry {
		private String description;
		private long actual;
		
		public IncomeExpenseReportEntry(String name, long actual) {
			this.description = name;
			this.actual = actual;
		}
			
		public long getActual() {
			return actual;
		}
		public void setActual(long actual) {
			this.actual = actual;
		}
		public String getDescription() {
			return description;
		}
		public void setCategory(String name) {
			this.description = name;
		}
	}

	public class ReportTreeCellRenderer extends JLabel implements TreeCellRenderer {
		public static final long serialVersionUID = 0;
				
		public ReportTreeCellRenderer(){
			super();
		}
		
		public Component getTreeCellRendererComponent(JTree tree, Object node, boolean isSelected, boolean expanded, boolean leaf, int row, boolean hasFocus) {
			Object obj;
			DefaultMutableTreeNode n;
			if (node instanceof DefaultMutableTreeNode) {
				n = (DefaultMutableTreeNode) node;
				obj = n.getUserObject();
			}
			else
				return this;
			
			this.setBorder(BorderFactory.createEmptyBorder());
			
			if (obj == null){
				this.setText("");
			}
			else if (obj instanceof ReportEntryTotal){
				ReportEntryTotal entry = (ReportEntryTotal) obj;
				
				StringBuffer sb = new StringBuffer();
				
				sb.append(
						"<html><table><tr><td width=250px><b>")
						.append(Translate.getInstance().get(TranslateKeys.TOTAL))
						.append("</b></td><td width=70px><b>")
						.append((entry.getTotal() < 0 ? "<font color='red'>" : ""))
						.append(PrefsInstance.getInstance().getPrefs().getCurrencySymbol())
						.append(Formatter.getInstance().getDecimalFormat().format(Math.abs((double) entry.getTotal() / 100.0)))
						.append((entry.getTotal() < 0 ? "</font>" : ""))
						.append("</b></td></tr></table></html>");
				
				this.setText(sb.toString());
			}
			else if (obj instanceof IncomeExpenseReportEntry){
				IncomeExpenseReportEntry entry = (IncomeExpenseReportEntry) obj;
 				
				StringBuffer sb = new StringBuffer();
				
				
				sb.append("<html><table><tr><td width=250px><u>")
						.append(Formatter.getInstance().getLengthFormat(25).format(entry.getDescription().toString()))
						.append("</u></td><td width=70px>")
						.append(entry.getActual() < 0 ? "<font color='red'>" : "")
						.append(PrefsInstance.getInstance().getPrefs().getCurrencySymbol())
						.append(Formatter.getInstance().getDecimalFormat().format(Math.abs((double) entry.getActual() / 100.0)))
						.append(entry.getActual() < 0 ? "</font>" : "")
						.append("</td></tr></table></html>");
				
				this.setText(sb.toString());
			}
			else if (obj instanceof Transaction){
				Transaction transaction = (Transaction) obj;
				
				StringBuffer sb = new StringBuffer();
				
				
				boolean isExpense;
				if (transaction.getTo() instanceof Category
						&& !((Category) transaction.getTo()).isIncome())
					isExpense = true;
				else
					isExpense = false;
				
				sb.append("<html><table><tr><td width=80px>")
						.append((isExpense ? "<font color='red'>" : ""))
						.append(PrefsInstance.getInstance().getPrefs().getCurrencySymbol())
						.append(Formatter.getInstance().getDecimalFormat().format(Math.abs((double) transaction.getAmount() / 100.0)))
						.append((isExpense ? "</font>" : ""))
						.append("</td><td width=350px>")
						.append(Formatter.getInstance().getLengthFormat(25).format(transaction.getFrom()))
						.append(" ")
						.append(Translate.getInstance().get(TranslateKeys.TO))
						.append(" ")
						.append(Formatter.getInstance().getLengthFormat(25).format(transaction.getTo()))							
						.append("</td></tr></table></html>");
				
				this.setText(sb.toString());
			}
			
			if (!Buddi.isMac()){
				if (isSelected){
					this.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
				}
				else{
					this.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
				}
			}
			
			return this;
		}		
	}
}
