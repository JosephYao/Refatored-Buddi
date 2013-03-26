/*
 * Created on Aug 5, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.view.swing;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;

import org.homeunix.thecave.buddi.model.Account;
import org.homeunix.thecave.buddi.model.AccountType;

public class MyAccountTreeNameCellRenderer extends DefaultTreeCellRenderer {
	public static final long serialVersionUID = 0;

	@Override
	public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
		this.setIcon(null);
		
		if (value instanceof Account){
			Account a = (Account) value;
			this.setText(a.getFullName() + "                                                                                                 ");
			if (a.getAccountType().isCredit()) this.setForeground(Color.RED);
			else this.setForeground(Color.BLACK);
//			this.setText("<html>" 
//					+ TextFormatter.getDeletedWrapper(TextFormatter.getFormattedNameForAccount(a).replaceAll(" ", "&nbsp;"), a)
//					+ "</html>");
		}
		else if (value instanceof AccountType){
			AccountType t = (AccountType) value;
			this.setText(t.getName() + "                                                                                                     ");
			if (t.isCredit()) this.setForeground(Color.RED);
			else this.setForeground(Color.BLACK);
		}		
		else
			this.setText("");

		//this.setBounds(this.getBounds().x, this.getBounds().y, this.getBounds().width + 1000, this.getBounds().height);
		return this;
	}
}
