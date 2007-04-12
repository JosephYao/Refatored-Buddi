package org.homeunix.drummer.view.components;

import java.awt.Component;

import javax.swing.JTable;
import javax.swing.tree.DefaultMutableTreeNode;

import org.homeunix.drummer.controller.Translate;
import org.homeunix.drummer.model.Account;
import org.homeunix.drummer.model.Category;
import org.homeunix.drummer.model.impl.AccountImpl;
import org.homeunix.drummer.model.impl.CategoryImpl;
import org.homeunix.drummer.view.AccountListPanel.TypeTotal;

/**
 * The cell renderer for the Amount column in Accounts and Categories panes.
 * @author wyatt
 *
 */
public class SourceAmountCellRenderer extends DefaultTableCellRenderer {
	public static final long serialVersionUID = 0;

	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
//		super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
		Object obj = node.getUserObject();
		
		if (obj == null){
			this.setText("");
		}
		else if (obj.getClass().equals(CategoryImpl.class)) {
			Category c = (Category) obj;

			startTableCellRendererComponent(value, isSelected, row, column, (!c.isIncome() ? "red" : null), c.isDeleted());
			
			long amount = c.getBudgetedAmount();
			sb.append(Translate.getFormattedCurrency(amount, false));
			
			long amountTotal = getTotalAmount(node);
			if (node.getChildCount() > 0){
				sb.append(" (")
				.append(Translate.getFormattedCurrency(amountTotal, false))
				.append(")");
			}

			endTableCellRendererComponent();
			
			return this;
		}
		else if (obj.getClass().equals(AccountImpl.class)) {			
			Account a = (Account) obj;

			String color = null;
			if ((a.getAccountType().isCredit() && a.getBalance() <= 0)
					|| (!a.getAccountType().isCredit() && a.getBalance() < 0))
				color = "red";
			startTableCellRendererComponent(value, isSelected, row, column, color, a.isDeleted());
			
			sb.append(Translate.getFormattedCurrency(a.getBalance(), a.getAccountType().isCredit()));
			endTableCellRendererComponent();
			
			return this;
		}
		//This is a wrapper class for Type which includes the total for all accounts of this type
		else if (obj.getClass().equals(TypeTotal.class)) {			
			TypeTotal t = (TypeTotal) obj;

			long amount = 0;
			for (int i = 0; i < node.getChildCount(); i++){
				DefaultMutableTreeNode n = (DefaultMutableTreeNode) node.getChildAt(i);
				amount += ((Account) n.getUserObject()).getBalance();
			}
				
			String color = null;
			if ((t.getType().isCredit() && amount <= 0)
					|| (!t.getType().isCredit() && amount < 0))
				color = "red";
			startTableCellRendererComponent(value, isSelected, row, column, color, false);
			
//			if (t.getAmount() < 0 ^ t.getType().isCredit())
//				sb.append("-");
			sb.append(Translate.getFormattedCurrency(amount, t.getType().isCredit()));

			endTableCellRendererComponent();
			
			return this;
		}

		return this;
	}

	/**
	 * Returns the total amount which is contained within the given node.
	 * @param node Node to check grand total of
	 * @return Grand total of all account / category objects contained within node and it's descendants.
	 */
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