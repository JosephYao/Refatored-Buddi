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
import org.homeunix.thecave.buddi.plugin.api.util.TextFormatter;

import ca.digitalcave.moss.common.OperatingSystemUtil;

public class MyAccountTreeNameCellRenderer extends DefaultTreeCellRenderer {
	public static final long serialVersionUID = 0;

	@Override
	public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
		if (OperatingSystemUtil.isMac() || OperatingSystemUtil.isWindows()){
			super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
			
			this.setIcon(null);
			
			if (value instanceof Account){
				Account a = (Account) value;
				this.setText("<html>" 
						+ TextFormatter.getDeletedWrapper(TextFormatter.getFormattedNameForAccount(a), a)
						+ "</html>");
			}
			else if (value instanceof AccountType){
				AccountType t = (AccountType) value;
				this.setText("<html>" + TextFormatter.getFormattedNameForType(t) + "</html>");
			}		
			else
				this.setText("");
	
			return this;
		}
		else {
			//This section, although the default for all non-OSX / non-Windows JVMs, is designed for Ubuntu Linux running the Gnome Look and Feel. 
			this.setIcon(null);
			
			if (value instanceof Account){
				Account a = (Account) value;
				this.setText(a.getFullName() + "                                                                                                 ");
				if (a.getAccountType().isCredit()) this.setForeground(Color.RED);
				else this.setForeground(Color.BLACK);
			}
			else if (value instanceof AccountType){
				AccountType t = (AccountType) value;
				this.setText(t.getName() + "                                                                                                     ");
				if (t.isCredit()) this.setForeground(Color.RED);
				else this.setForeground(Color.BLACK);
			}		
			else {
				this.setText("");
			}

			return this;
		}
	}
}
