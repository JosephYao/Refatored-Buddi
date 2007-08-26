/*
 * Created on Aug 5, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.view.swing;

import java.awt.Color;
import java.awt.Component;
import java.util.EventObject;

import javax.swing.AbstractCellEditor;
import javax.swing.JTable;
import javax.swing.border.LineBorder;
import javax.swing.table.TableCellEditor;

import org.homeunix.thecave.moss.swing.MossDecimalField;

public class DecimalCellEditor extends AbstractCellEditor implements TableCellEditor {
	public static final long serialVersionUID = 0;

//	private final JTable table;
	private final MossDecimalField editor;// = new JDecimalField(0, true, 2);//{
//		@Override
//		public void keyPressed(KeyEvent e) {
//			if (e.getKeyCode() == KeyEvent.VK_UP
//					|| e.getKeyCode() == KeyEvent.VK_DOWN
//					|| e.getKeyCode() == KeyEvent.VK_RIGHT
//					|| e.getKeyCode() == KeyEvent.VK_LEFT){
//				System.out.println("Arrows");
//				return;
//			}
//
//			super.keyPressed(e);
//		}
//	};

	public DecimalCellEditor(MossDecimalField editor) {
//		this.table = table;
		this.editor = editor;
		this.editor.setBorder(new LineBorder(Color.black));
	}

	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
		if (value instanceof Long) {
			editor.setValue((Long) value);
		}
		return editor;
	}

	public Object getCellEditorValue() {
		return editor.getValue();
	}

	@Override
	public boolean isCellEditable(EventObject arg0) {
		return true;
	}
}
