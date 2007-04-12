package org.homeunix.drummer.view.components;

import java.awt.Component;

import javax.swing.JTable;
import javax.swing.tree.DefaultMutableTreeNode;

import org.homeunix.drummer.model.Account;
import org.homeunix.drummer.model.Category;
import org.homeunix.drummer.model.impl.AccountImpl;
import org.homeunix.drummer.model.impl.CategoryImpl;
import org.homeunix.drummer.prefs.PrefsInstance;
import org.homeunix.drummer.view.AccountListPanel.TypeTotal;
import org.homeunix.thecave.moss.util.Formatter;

/**
 * The cell renderer for the Name column in Accounts and Categories panes.
 * @author wyatt
 *
 */
public class SourceNameCellRenderer extends DefaultTableCellRenderer {
	public static final long serialVersionUID = 0;

	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
//		Object obj = super.prepareTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
//		super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
		Object obj = node.getUserObject();
		
		if (obj == null){
			this.setText("");
		}
		else if (obj.getClass().equals(CategoryImpl.class)) {
			Category c = (Category) obj;

			startTableCellRendererComponent(value, isSelected, row, column, (c.isIncome() ? null : "red"), c.isDeleted());			
			sb.append(c.toString());
			endTableCellRendererComponent();
			
			return this;
		}
		else if (obj.getClass().equals(AccountImpl.class)) {			
			Account a = (Account) obj;

			startTableCellRendererComponent(value, isSelected, row, column, (a.getAccountType().isCredit() ? "red" : null), a.isDeleted());			
			sb.append(a.toString());
			if (PrefsInstance.getInstance().getPrefs().isShowInterestRate()
					&& (a.getInterestRate() != 0)){
				sb.append(" (")
				.append(Formatter.getDecimalFormat().format(((double) a.getInterestRate()) / 100))
				.append("%)");
			}
			endTableCellRendererComponent();
			
			return this;
		}
		//This is a wrapper class for Type which includes the total for all accounts of this type
		else if (obj.getClass().equals(TypeTotal.class)) {			
			TypeTotal t = (TypeTotal) obj;

			startTableCellRendererComponent(value, isSelected, row, column, (t.getType().isCredit() ? "red" : null), false);
			sb.append(t.getType().toString());
			endTableCellRendererComponent();
			
			return this;
		}

		return this;
	}
}