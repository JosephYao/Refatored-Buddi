/*
 * Created on Nov 10, 2006 by wyatt
 */
package org.homeunix.drummer.view.components;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;

import org.homeunix.drummer.Const;

public abstract class AbstractSourceCellRenderer  extends JLabel implements TableCellRenderer {

	final StringBuilder sb = new StringBuilder();
	final StringBuilder sbOpen = new StringBuilder();
	final StringBuilder sbClose = new StringBuilder();
	final StringBuilder sbPrepend = new StringBuilder();
	
	/**
	 * Creates a new SourceCellRenderer object
	 */
	AbstractSourceCellRenderer(){
		super();

		setOpaque(true);
	}

	Object prepareTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		if (isSelected){
			this.setBackground(Const.COLOR_SELECTED);
		}
		else {
			this.setBackground(Const.COLOR_TRANSPARENT);
		}

		if (value == null){
			this.setText("");
		}
		else if (value.getClass().equals(DefaultMutableTreeNode.class)){
			DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
			Object obj = node.getUserObject();

			this.setBorder(BorderFactory.createEmptyBorder());

			if (sbOpen.length() > 0)
				sbOpen.delete(0, sbOpen.length());
			if (sbClose.length() > 0)
				sbClose.delete(0, sbClose.length());
			if (sbPrepend.length() > 0)
				sbPrepend.delete(0, sbPrepend.length());
			if (sb.length() > 0)
				sb.delete(0, sb.length());

			TreeNode treeNode = node.getParent();
			while (treeNode.getParent() != null){
				treeNode = treeNode.getParent();
				sbPrepend.append("&nbsp;&nbsp;&nbsp;");
			}

			return obj;
		}

		return null;
	}
}
