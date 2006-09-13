package org.homeunix.drummer.view.components;

import java.awt.Color;
import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreePath;

import org.homeunix.drummer.Buddi;
import org.homeunix.drummer.controller.AccountListPanel.TypeTotal;
import org.homeunix.drummer.model.Account;
import org.homeunix.drummer.model.Category;
import org.homeunix.drummer.prefs.PrefsInstance;
import org.homeunix.drummer.util.Formatter;

public class SourceCellRenderer extends JLabel implements TreeCellRenderer {
	public static final long serialVersionUID = 0;
	private static final StringBuilder sb = new StringBuilder();
	private static final StringBuilder sbOpen = new StringBuilder();
	private static final StringBuilder sbClose = new StringBuilder();
	
	public SourceCellRenderer(){
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

		if (sbOpen.length() > 0)
			sbOpen.delete(0, sbOpen.length());
		if (sbClose.length() > 0)
			sbClose.delete(0, sbClose.length());
		if (sb.length() > 0)
			sb.delete(0, sb.length());
		
		
		if (obj == null){
			this.setText("");
		}
		else if (obj instanceof Category) {
			Category c = (Category) obj;
			sbOpen.append("<html>");
			sbClose.append("</html>");

			if (c.isDeleted()){
				sbOpen.append("<s>");
				sbClose.insert(0, "</s>");
			}

			if (!c.isIncome()){
				sbOpen.append("<font color='red'>");
				sbClose.insert(0, "</font>");
			}

			long amount;

			if (tree.isCollapsed(new TreePath(n.getPath())))
				amount = getTotalAmount(n);
			else
				amount = c.getBudgetedAmount();

			sb.append("<html><table><tr><td width=180px>")
			.append(sbOpen.toString())
			.append(c.toString())
			.append(sbClose.toString())
			.append("</td><td width=70px>")
			.append(sbOpen.toString())
			.append(PrefsInstance.getInstance().getPrefs().getCurrencySymbol())
			.append(Formatter.getInstance().getDecimalFormat().format(Math.abs((double) amount / 100.0)))
			.append(sbClose.toString())
			.append("</td></tr></table></html>");

			this.setText(sb.toString());
		}
		else if (obj instanceof Account) {			
			Account a = (Account) obj;

			if (a.isDeleted()){
				sbOpen.append("<s>");
				sbClose.insert(0, "</s>");
			}

			if (a.getBalance() < 0){
				sbOpen.append("<font color='red'>");
				sbClose.insert(0, "</font>");
			}

			sb.append("<html><table><tr><td width=200px>")
			.append(sbOpen.toString())
			.append(a.toString());
			if (PrefsInstance.getInstance().getPrefs().isShowInterestRate()
					&& (a.getInterestRate() != 0)){
				sb.append(" (")
				.append(Formatter.getInstance().getDecimalFormat().format(((double) a.getInterestRate()) / 100))
				.append("%)");
			}
			sb.append(sbClose.toString())
			.append("</td><td width=70px>")
			.append(sbOpen.toString())
			.append(PrefsInstance.getInstance().getPrefs().getCurrencySymbol())
			.append(Formatter.getInstance().getDecimalFormat().format(Math.abs((double) a.getBalance() / 100.0)))
			.append(sbClose.toString())
			.append("</td></tr></table></html>");

			this.setText(sb.toString());
		}
		//This is a wrapper class for Type which includes the total for all accounts of this type
		else if (obj instanceof TypeTotal) {			
			TypeTotal t = (TypeTotal) obj;

			if (t.getType().isCredit()){
				sbOpen.append("<font color='red'>");
				sbClose.insert(0, "</font>");
			}

			sb.append("<html><table><tr><td width=200px>")
			.append(sbOpen.toString())
			.append(t.getType().toString())
			.append(sbClose.toString())
			.append("</td><td width=70px>")
			.append(sbOpen.toString())
			.append(PrefsInstance.getInstance().getPrefs().getCurrencySymbol())
			.append(Formatter.getInstance().getDecimalFormat().format(Math.abs((double) t.getAmount() / 100.0)))
			.append(sbClose.toString())
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

	private long getTotalAmount(DefaultMutableTreeNode node){
		long amount = 0;
		if (node.getUserObject() instanceof Category){
			amount = ((Category) node.getUserObject()).getBudgetedAmount();
		}

		for (int i = 0; i < node.getChildCount(); i++) {
			amount += getTotalAmount((DefaultMutableTreeNode) node.getChildAt(i));
		}

		return amount;
	}
}