/*
 * Created on Aug 7, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.view.swing;

import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import org.homeunix.thecave.buddi.model.ScheduledTransaction;

public class ScheduledTransactionTableCellRenderer extends DefaultTableCellRenderer {
	public static final long serialVersionUID = 0;

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

		if (value instanceof ScheduledTransaction)
//			this.setText(((ScheduledTransaction) value).getScheduleName());
			this.setText("Test");
		else
			this.setText("");
		
		return this;
	}
}
