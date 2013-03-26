/*
 * Created on Aug 5, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.view.swing;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;

import org.homeunix.thecave.buddi.model.BudgetCategory;

public class MyBudgetTreeNameCellRenderer extends DefaultTreeCellRenderer {
	public static final long serialVersionUID = 0;


	@Override
	public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
		this.setIcon(null);
		
		if (value instanceof BudgetCategory){
			BudgetCategory bc = (BudgetCategory) value;
			this.setText(bc.getName() + "                                                                                       ");
			if (bc.isIncome()) this.setForeground(Color.BLACK);
			else this.setForeground(Color.RED);
		}
		else
			this.setText("");

		return super.getTreeCellRendererComponent(tree, this.getText(), sel, expanded, leaf, row, hasFocus);
	}
}
