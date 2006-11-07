package org.homeunix.drummer.view.components;

import java.awt.Color;
import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.table.TableCellRenderer;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import org.homeunix.drummer.Buddi;
import org.homeunix.drummer.controller.AccountListPanel.TypeTotal;
import org.homeunix.drummer.model.Account;
import org.homeunix.drummer.model.Category;
import org.homeunix.drummer.model.impl.AccountImpl;
import org.homeunix.drummer.model.impl.CategoryImpl;
import org.homeunix.drummer.prefs.PrefsInstance;
import org.homeunix.thecave.moss.util.Formatter;

/**
 * The cell renderer for Accounts pane and Categories pane.
 * @author wyatt
 *
 */
public class SourceAmountCellRenderer extends JLabel implements TableCellRenderer {
	public static final long serialVersionUID = 0;
	private static final StringBuilder sb = new StringBuilder();
	private static final StringBuilder sbOpen = new StringBuilder();
	private static final StringBuilder sbClose = new StringBuilder();

	private static final boolean isMac = Buddi.isMac();

//	private static Color LIGHT_BLUE = new Color(237, 243, 254);
	private static Color SELECTED = new Color(181, 213, 255);
	private static Color WHITE = Color.WHITE;

	/**
	 * Creates a new SourceCellRenderer object
	 */
	public SourceAmountCellRenderer(){
		super();

		if (!isMac){
			this.setOpaque(true);
		}
	}

	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {


		if (!isMac){
			if (isSelected){
				this.setBackground(SELECTED);
			}
			else{
				this.setBackground(WHITE);
//				if (row % 2 == 0)
//				this.setBackground(LIGHT_BLUE);
//				else
//				this.setBackground(WHITE);
			}
		}

		this.setBorder(BorderFactory.createEmptyBorder());

		if (sbOpen.length() > 0)
			sbOpen.delete(0, sbOpen.length());
		if (sbClose.length() > 0)
			sbClose.delete(0, sbClose.length());
		if (sb.length() > 0)
			sb.delete(0, sb.length());


		if (value == null){
			this.setText("");
		}
		else if (value.getClass().equals(DefaultMutableTreeNode.class)){
			DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
			Object obj = node.getUserObject();
			
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

				long amountTotal = getTotalAmount(node);
				long amount = c.getBudgetedAmount();

				sb.append("<html>")
				.append(sbOpen.toString())
				.append(PrefsInstance.getInstance().getPrefs().getCurrencySymbol())
				.append(Formatter.getInstance().getDecimalFormat().format(Math.abs((double) amount / 100.0)));

				if (node.getChildCount() > 0){
					sb.append(" (")
					.append(PrefsInstance.getInstance().getPrefs().getCurrencySymbol())
					.append(Formatter.getInstance().getDecimalFormat().format(Math.abs((double) amount / 100.0)))
					.append(")");
				}
				
				sb.append(sbClose.toString())
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
				.append(sbOpen.toString())
				.append(PrefsInstance.getInstance().getPrefs().getCurrencySymbol())
				.append(Formatter.getInstance().getDecimalFormat().format(Math.abs((double) a.getBalance() / 100.0)))
				.append(sbClose.toString())
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
				.append(sbOpen.toString())
				.append(PrefsInstance.getInstance().getPrefs().getCurrencySymbol())
				.append(Formatter.getInstance().getDecimalFormat().format(Math.abs((double) t.getAmount() / 100.0)))
				.append(sbClose.toString())
				.append("</html>");

				this.setText(sb.toString());
			}
		}

//		if (!Buddi.isMac()){
//		if (isSelected){
//		this.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
//		}
//		else{
//		this.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
//		}
//		}


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