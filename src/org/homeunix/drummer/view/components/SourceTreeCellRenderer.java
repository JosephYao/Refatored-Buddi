/*
 * Created on Nov 10, 2006 by wyatt
 */
package org.homeunix.drummer.view.components;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTree;
import javax.swing.tree.TreeCellRenderer;

public class SourceTreeCellRenderer extends JLabel implements TreeCellRenderer {
	public final static long serialVersionUID = 0;
	
	public SourceTreeCellRenderer() {
		super();
//		this.setOpaque(true);
		this.setText(" ");
	}
	
	public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) {
		return this;
	}
}
