/*
 * Created on Aug 5, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.view.swing;

import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import org.homeunix.thecave.buddi.model.BudgetCategory;
import org.homeunix.thecave.buddi.plugin.api.util.TextFormatter;
import org.homeunix.thecave.buddi.util.InternalFormatter;

public class MyBudgetTableAmountCellRenderer extends DefaultTableCellRenderer {
	public static final long serialVersionUID = 0;

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

		if (value instanceof Object[]){
			Object[] values = (Object[]) value;

			String text = TextFormatter.getFormattedCurrency(
					(Long) values[1], 
					InternalFormatter.isRed((BudgetCategory) values[0], (Long) values[1])) 
					+ (((Long) values[2]) != 0 ? 
							" (" + TextFormatter.getFormattedCurrency(
									(Long) values[2], 
									InternalFormatter.isRed((BudgetCategory) values[0], (Long) values[2]))
									+ ")"
									: "");

			for (int i = 0; i < ((Integer) values[3]); i++){
				text = "&nbsp&nbsp&nbsp" + text; 
			}
			
			this.setText(TextFormatter.getHtmlWrapper(text));
		}

		if (hasFocus && isSelected) {
			table.editCellAt(row,column);
			table.getCellEditor(row, column).getTableCellEditorComponent(table, value, isSelected, row, column).requestFocus();
		}

		return this;
	}
}
