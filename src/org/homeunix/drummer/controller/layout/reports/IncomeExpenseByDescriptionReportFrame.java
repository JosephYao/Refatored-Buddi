/*
 * Created on May 19, 2006 by wyatt
 */
package org.homeunix.drummer.controller.layout.reports;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
import org.homeunix.drummer.Translate;
import org.homeunix.drummer.TranslateKeys;
import org.homeunix.drummer.controller.layout.TransactionsFrame;
import org.homeunix.drummer.controller.model.DataInstance;
import org.homeunix.drummer.controller.model.PrefsInstance;
import org.homeunix.drummer.model.Account;
import org.homeunix.drummer.model.Category;
import org.homeunix.drummer.model.Transaction;
import org.homeunix.drummer.util.Formatter;
import org.homeunix.drummer.util.Log;
import org.homeunix.drummer.view.AbstractBudgetFrame;
import org.homeunix.drummer.view.layout.reports.ReportFrameLayout;

public class IncomeExpenseByDescriptionReportFrame extends ReportFrameLayout {
	public static final long serialVersionUID = 0;
	
	public IncomeExpenseByDescriptionReportFrame(Date startDate, Date endDate){
		super(startDate, endDate);		
	}
	
	@Override
	protected TreeModel buildReport(Date startDate, Date endDate) {
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
	
	@Override
	protected AbstractBudgetFrame initActions() {
		reportTree.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseClicked(MouseEvent arg0) {
				editButton.setEnabled(selectedNode != null && selectedNode.getUserObject() instanceof Transaction);
				
				if (arg0.getClickCount() >= 2){
					editButton.doClick();
				}
				super.mouseClicked(arg0);
			}
		});
		
		editButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				Object o = selectedNode.getUserObject();
				if (o instanceof Transaction){
					Transaction transaction = (Transaction) o;
					Account a;
					if (transaction.getTo() instanceof Account)
						a = (Account) transaction.getTo();
					else if (transaction.getFrom() instanceof Account)
						a = (Account) transaction.getFrom();	
					else
						a = null;
					
					if (a != null) {
						new TransactionsFrame(a, transaction);
					}
				}
			}
		});
		
		return super.initActions();
	}
	
	@Override
	protected TreeCellRenderer getTreeCellRenderer() {
		return new ReportTreeCellRenderer();
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
	
	@Override
	public String getHtmlReport() {
		StringBuffer sb = getHtmlHeader(TranslateKeys.REPORT_BY_DESCRIPTION_HEADER);
		
		sb.append("<table class='main'>\n");
		
		if (reportTree.getModel().getRoot() instanceof DefaultMutableTreeNode){
			DefaultMutableTreeNode root = (DefaultMutableTreeNode) reportTree.getModel().getRoot();

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
}
