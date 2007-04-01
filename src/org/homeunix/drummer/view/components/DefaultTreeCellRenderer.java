/*
 * Created on Nov 10, 2006 by wyatt
 */
package org.homeunix.drummer.view.components;

import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JTree;
import javax.swing.tree.TreeCellRenderer;

public class DefaultTreeCellRenderer extends JLabel implements TreeCellRenderer {
	public final static long serialVersionUID = 0;
	
	public DefaultTreeCellRenderer() {
		super();
		this.setBorder(BorderFactory.createEmptyBorder());
		this.setText(" ");
	}
	
	public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean isSelected) {
		return this;
	}
}
