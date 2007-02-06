/*
 * Created on Nov 6, 2006 by wyatt
 */
package org.homeunix.drummer.view.model;

import javax.swing.tree.DefaultMutableTreeNode;

import org.homeunix.drummer.controller.Translate;
import org.homeunix.drummer.controller.TranslateKeys;
import org.jdesktop.swingx.treetable.DefaultTreeTableModel;

public class SourceTreeTableModel extends DefaultTreeTableModel {

	private final DefaultMutableTreeNode root;
	
	public SourceTreeTableModel() {
		this(new DefaultMutableTreeNode());
	}
	
	public SourceTreeTableModel(DefaultMutableTreeNode root){
		this.root = root;
	}
	
	public DefaultMutableTreeNode getRoot(){
		return root;
	}
	
	@Override
	public String getColumnName(int arg0) {
		if (arg0 == 0){
			return "";
		}
		else if (arg0 == 1){
			return Translate.getInstance().get(TranslateKeys.NAME);
		}
		else if (arg0 == 2){
			return Translate.getInstance().get(TranslateKeys.AMOUNT);
		}
		else{
			return null;
		}
	}
	
	@Override
	public int getColumnCount() {
		return 3;
	}
	
	public Object getValueAt(Object arg0, int arg1) {
		if (arg0 instanceof DefaultMutableTreeNode){
			DefaultMutableTreeNode node = (DefaultMutableTreeNode) arg0;

			return node;
		}
		
		return null;
	}

	public void setValueAt(Object arg0, Object arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}
}
