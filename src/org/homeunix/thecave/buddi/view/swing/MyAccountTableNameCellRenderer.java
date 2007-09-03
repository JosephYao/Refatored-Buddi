/*
 * Created on Aug 5, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.view.swing;

import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import org.homeunix.thecave.buddi.model.Account;
import org.homeunix.thecave.buddi.model.AccountType;
import org.homeunix.thecave.buddi.plugin.api.util.TextFormatter;

public class MyAccountTableNameCellRenderer extends DefaultTableCellRenderer {
	public static final long serialVersionUID = 0;
	
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

		if (value instanceof Account){
			Account a = (Account) value;
			this.setText("<html>&nbsp&nbsp&nbsp " 
					+ TextFormatter.getDeletedWrapper(TextFormatter.getFormattedNameForAccount(a), a)
					+ "</html>");
		}
		if (value instanceof AccountType){
			AccountType t = (AccountType) value;
			this.setText("<html>" + TextFormatter.getFormattedNameForType(t) + "</html>");
		}


		if (hasFocus && isSelected) {
			table.editCellAt(row,column);
			table.getCellEditor(row, column).getTableCellEditorComponent(table, value, isSelected, row, column).requestFocus();
		}

		return this;
	}
}
