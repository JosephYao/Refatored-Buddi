package org.homeunix.drummer.view.components;

import java.awt.Component;

import javax.swing.JTable;

import org.homeunix.drummer.controller.AccountListPanel.TypeTotal;
import org.homeunix.drummer.model.Account;
import org.homeunix.drummer.model.Category;
import org.homeunix.drummer.model.impl.AccountImpl;
import org.homeunix.drummer.model.impl.CategoryImpl;
import org.homeunix.drummer.prefs.PrefsInstance;
import org.homeunix.thecave.moss.util.Formatter;

/**
 * The cell renderer for the Name column in Accounts and Categories panes.
 * @author wyatt
 *
 */
public class SourceNameCellRenderer extends SourceCellRenderer {
	public static final long serialVersionUID = 0;

	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		Object obj = super.prepareTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

		if (obj == null){
			this.setText("");
		}
		else if (obj.getClass().equals(CategoryImpl.class)) {
			Category c = (Category) obj;

			if (c.isDeleted()){
				sbOpen.append("<s>");
				sbClose.insert(0, "</s>");
			}

			if (!c.isIncome()){
				sbOpen.append("<font color='red'>");
				sbClose.insert(0, "</font>");
			}

			sb.append("<html>")
			.append(sbPrepend)
			.append(sbOpen)
			.append(c.toString())
			.append(sbClose)
			.append("</html>");

			this.setText(sb.toString());
		}
		else if (obj.getClass().equals(AccountImpl.class)) {			
			Account a = (Account) obj;

			if (a.isDeleted()){
				sbOpen.append("<s>");
				sbClose.insert(0, "</s>");
			}

			if (a.getBalance() < 0){
				sbOpen.append("<font color='red'>");
				sbClose.insert(0, "</font>");
			}

			sb.append("<html>")
			.append(sbPrepend)
			.append(sbOpen)
			.append(a.toString());
			if (PrefsInstance.getInstance().getPrefs().isShowInterestRate()
					&& (a.getInterestRate() != 0)){
				sb.append(" (")
				.append(Formatter.getInstance().getDecimalFormat().format(((double) a.getInterestRate()) / 100))
				.append("%)");
			}
			sb.append(sbClose)
			.append("</html>");

			this.setText(sb.toString());
		}
		//This is a wrapper class for Type which includes the total for all accounts of this type
		else if (obj.getClass().equals(TypeTotal.class)) {			
			TypeTotal t = (TypeTotal) obj;

			if (t.getType().isCredit()){
				sbOpen.append("<font color='red'>");
				sbClose.insert(0, "</font>");
			}

			sb.append("<html>")
			.append(sbOpen)
			.append(t.getType().toString())
			.append(sbClose)
			.append("</html>");

			this.setText(sb.toString());
		}

		return this;
	}
}