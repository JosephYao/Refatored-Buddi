/*
 * Created on Nov 6, 2006 by wyatt
 */
package org.homeunix.drummer.view.components;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;
import javax.swing.tree.DefaultMutableTreeNode;

import org.homeunix.drummer.model.Category;
import org.homeunix.drummer.prefs.PrefsInstance;
import org.homeunix.drummer.view.components.SourceTreeTableModel.AmountFormatWrapper;
import org.homeunix.thecave.moss.util.Formatter;

/**
 * The cell renderer for the second (amount) column of the JXTreeTable
 * @author wyatt
 *
 */
public class SourceAmountTableCellRenderer extends JLabel implements TableCellRenderer {
	public final static long serialVersionUID = 0;
	
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		if (value instanceof AmountFormatWrapper){
			AmountFormatWrapper a = (AmountFormatWrapper) value;
			this.setForeground((a.isNegative() ? Color.RED : Color.BLACK));

			this.setText(
					PrefsInstance.getInstance().getPrefs().getCurrencySymbol() + 
					Formatter.getInstance().getDecimalFormat().format(Math.abs((double) a.getAmount() / 100.0))
			);
		}
		else {
			this.setForeground(Color.BLACK);
			this.setText("");
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
