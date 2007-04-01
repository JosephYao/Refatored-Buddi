package org.homeunix.drummer.view.components;

import java.awt.Component;

import javax.swing.JTable;
import javax.swing.tree.DefaultMutableTreeNode;

import org.homeunix.drummer.controller.Translate;
import org.homeunix.drummer.controller.AccountListPanel.TypeTotal;
import org.homeunix.drummer.model.Account;
import org.homeunix.drummer.model.Category;
import org.homeunix.drummer.model.impl.AccountImpl;
import org.homeunix.drummer.model.impl.CategoryImpl;

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

			super.startTableCellRendererComponent(value, isSelected, row, column, (!c.isIncome() ? "red" : null), c.isDeleted());
//			if (c.isDeleted()){
//				sbOpen.append("<s>");
//				sbClose.insert(0, "</s>");
//			}

//			if (!c.isIncome()){
//				sbOpen.append("<font color='red'>");
//				sbClose.insert(0, "</font>");
//			}

			long amountTotal = getTotalAmount(node);
			long amount = c.getBudgetedAmount();

//			sb.append("<html>")
//			.append(sbPrepend)
//			.append(sbOpen)
			sb.append(Translate.getFormattedCurrency(amount));

			if (node.getChildCount() > 0){
				sb.append(" (")
				.append(Translate.getFormattedCurrency(amountTotal))
				.append(")");
			}

//			sb.append(sbClose)
//			.append("</html>");

//			this.setText(sb.toString());
			endTableCellRendererComponent();
			
			return this;
		}
		else if (obj.getClass().equals(AccountImpl.class)) {			
			Account a = (Account) obj;

//			if (a.isDeleted()){
//				sbOpen.append("<s>");
//				sbClose.insert(0, "</s>");
//			}
//
//			if (a.getBalance() < 0){
//				sbOpen.append("<font color='red'>");
//				sbClose.insert(0, "</font>");
//			}

			startTableCellRendererComponent(value, isSelected, row, column, (a.getBalance() < 0 ? "red" : null), a.isDeleted());
//			sb.append("<html>")
//			.append(sbPrepend)
//			.append(sbOpen)
			sb.append(Translate.getFormattedCurrency(a.getBalance()));
			
//			sb.append(sbClose)
//			.append("</html>");

//			this.setText(sb.toString());
			endTableCellRendererComponent();
			
			return this;
		}
		//This is a wrapper class for Type which includes the total for all accounts of this type
		else if (obj.getClass().equals(TypeTotal.class)) {			
			TypeTotal t = (TypeTotal) obj;

//			if (t.getType().isCredit()){
//				sbOpen.append("<font color='red'>");
//				sbClose.insert(0, "</font>");
//			}
//
//			sb.append("<html>")
//			.append(sbOpen);

			startTableCellRendererComponent(value, isSelected, row, column, (t.getType().isCredit() ? "red" : null), false);
			
			if (t.getAmount() < 0 ^ t.getType().isCredit())
				sb.append("-");
			sb.append(Translate.getFormattedCurrency(t.getAmount()));
//			.append(sbClose)
//			.append("</html>");

//			this.setText(sb.toString());
			
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