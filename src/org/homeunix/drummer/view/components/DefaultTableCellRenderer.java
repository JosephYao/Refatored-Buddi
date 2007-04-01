/*
 * Created on Nov 10, 2006 by wyatt
 */
package org.homeunix.drummer.view.components;

import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;

import org.homeunix.drummer.Const;

public class DefaultTableCellRenderer  extends JLabel implements TableCellRenderer {
	public static final long serialVersionUID = 0;  
	
	final protected StringBuilder sb = new StringBuilder();
	final protected StringBuilder sbOpen = new StringBuilder();
	final protected StringBuilder sbClose = new StringBuilder();
	final protected StringBuilder sbPrepend = new StringBuilder();
	
	/**
	 * Creates a new SourceCellRenderer object
	 */
	public DefaultTableCellRenderer(){
		super();
		this.setBorder(BorderFactory.createEmptyBorder());
		this.setOpaque(true);
	}

	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		startTableCellRendererComponent(value, isSelected, row, column, null, false);
		sb.append("");
		endTableCellRendererComponent();
		
		return this;
	}
	
	public void startTableCellRendererComponent(Object value, boolean isSelected, int row, int column, String htmlFontColor, boolean isCrossedOut) {
		if (isSelected){
			this.setBackground(Const.COLOR_SELECTED);
			this.setForeground(Const.COLOR_SELECTED_TEXT);
		}
		else {
			if (row % 2 == 1) {
				this.setBackground(Const.COLOR_ODD_ROW);
			}
			else {
				this.setBackground(Const.COLOR_EVEN_ROW);
			}
			this.setForeground(Const.COLOR_UNSELECTED_TEXT);
		}

		//Clears the StringBuilders for use by extending classes 
		if (sbOpen.length() > 0)
			sbOpen.delete(0, sbOpen.length());
		if (sbClose.length() > 0)
			sbClose.delete(0, sbClose.length());
		if (sbPrepend.length() > 0)
			sbPrepend.delete(0, sbPrepend.length());
		if (sb.length() > 0)
			sb.delete(0, sb.length());

		if (value.getClass().equals(DefaultMutableTreeNode.class)){
			DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
			TreeNode treeNode = node.getParent();
			while (treeNode.getParent() != null){
				treeNode = treeNode.getParent();
				sbPrepend.append("&nbsp;&nbsp;&nbsp;");
			}
		}
		
		if (isCrossedOut){
			sbOpen.append("<s>");
			sbClose.insert(0, "</s>");
		}

		if (htmlFontColor != null){
			sbOpen.append("<font color='").append(htmlFontColor).append("'>");
			sbClose.insert(0, "</font>");
		}
		
		sb.append("<html>")
		.append(sbPrepend)
		.append(sbOpen);
	}
	
	public void endTableCellRendererComponent() {
		sb.append(sbClose)
		.append("</html>");
		
		this.setText(sb.toString());
	}
	
	
}
