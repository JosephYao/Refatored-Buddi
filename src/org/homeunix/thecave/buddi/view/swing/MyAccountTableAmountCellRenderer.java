/*
 * Created on Aug 5, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.view.swing;

import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import org.homeunix.thecave.buddi.model.Account;
import org.homeunix.thecave.buddi.model.AccountType;
import org.homeunix.thecave.buddi.model.Document;
import org.homeunix.thecave.buddi.model.impl.FilteredLists;
import org.homeunix.thecave.buddi.plugin.api.util.TextFormatter;
import org.homeunix.thecave.buddi.util.InternalFormatter;

public class MyAccountTableAmountCellRenderer extends DefaultTableCellRenderer {
	public static final long serialVersionUID = 0;
	
	private final Document document;
	
	public MyAccountTableAmountCellRenderer(Document document) {
		this.document = document;
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);




		if (value instanceof Account){
			Account a = (Account) value;
			this.setText(TextFormatter.getHtmlWrapper(
					TextFormatter.getDeletedWrapper(
							TextFormatter.getFormattedCurrency(
									a.getBalance(), 
									InternalFormatter.isRed(a, a.getBalance()), 
									a.getAccountType().isCredit()
							), a)));
		}
		if (value instanceof AccountType){
			AccountType t = (AccountType) value;
			int amount = 0;
			for (Account a : FilteredLists.getAccountsByType(document, document.getAccounts(), t)) {
				amount += a.getBalance();
			}
			this.setText(TextFormatter.getHtmlWrapper(
					TextFormatter.getFormattedCurrency(
							amount, 
							InternalFormatter.isRed(t, amount), 
							t.isCredit())));
		}


		if (hasFocus && isSelected) {
			table.editCellAt(row,column);
			table.getCellEditor(row, column).getTableCellEditorComponent(table, value, isSelected, row, column).requestFocus();
		}

		return this;
	}
}
