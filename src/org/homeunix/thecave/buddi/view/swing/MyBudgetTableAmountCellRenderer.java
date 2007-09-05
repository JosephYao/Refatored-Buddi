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

	private final StringBuilder sb = new StringBuilder(); 
	
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

//		if (value instanceof Object[]){
			Object[] values = (Object[]) value;
			
			if (sb.length() > 0)
				sb.delete(0, sb.length());
			
			TextFormatter.appendFormattedCurrency(sb, (Long) values[1], 
					InternalFormatter.isRed((BudgetCategory) values[0], (Long) values[1]), false);

			if ((Long) values[2] != 0){
				sb.append(" (");
				TextFormatter.appendFormattedCurrency(sb,
									(Long) values[2], 
									InternalFormatter.isRed((BudgetCategory) values[0], (Long) values[2]),
									false);
				sb.append(")");
			}
			
			for (int i = 0; i < ((Integer) values[3]); i++){
				sb.insert(0, "&nbsp&nbsp&nbsp"); 
			}
			
			sb.insert(0, "<html>");
			sb.append("</html>");
			this.setText(sb.toString());
//		}

		if (hasFocus && isSelected) {
			table.editCellAt(row,column);
			table.getCellEditor(row, column).getTableCellEditorComponent(table, value, isSelected, row, column).requestFocus();
		}

		return this;
	}
}
