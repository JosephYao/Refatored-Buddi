/*
 * Created on Aug 5, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.view.swing;

import java.awt.Color;
import java.awt.Component;

import javax.swing.AbstractCellEditor;
import javax.swing.JTable;
import javax.swing.border.LineBorder;
import javax.swing.table.TableCellEditor;

import org.homeunix.thecave.moss.swing.formatted.JDecimalField;

public class DecimalCellEditor extends AbstractCellEditor implements TableCellEditor {
	public static final long serialVersionUID = 0;
	
	private JDecimalField editor = new JDecimalField(0, true, 2);
	
	public DecimalCellEditor() {
        editor.setBorder(new LineBorder(Color.black));
	}
	
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
		if (value instanceof Long)
		editor.setValue((Long) value);
		
		return editor;
	}

	public Object getCellEditorValue() {
		return editor.getValue();
	}
}
