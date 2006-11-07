/*
 * Created on Nov 6, 2006 by wyatt
 */
package org.homeunix.drummer.view.components;

import javax.swing.tree.DefaultMutableTreeNode;

import org.homeunix.drummer.controller.Translate;
import org.homeunix.drummer.controller.TranslateKeys;
import org.homeunix.drummer.controller.AccountListPanel.TypeTotal;
import org.homeunix.drummer.model.Account;
import org.homeunix.drummer.model.Category;
import org.homeunix.drummer.prefs.PrefsInstance;
import org.homeunix.thecave.moss.util.Formatter;
import org.jdesktop.swingx.JXTreeTable;
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
			return Translate.getInstance().get(TranslateKeys.NAME);
		}
		else if (arg0 == 1){
			return Translate.getInstance().get(TranslateKeys.AMOUNT);
		}
		else{
			return null;
		}
	}
	
	@Override
	public int getColumnCount() {
		return 2;
	}
	
	public Object getValueAt(Object arg0, int arg1) {
		if (arg0 instanceof DefaultMutableTreeNode){
			DefaultMutableTreeNode node = (DefaultMutableTreeNode) arg0;
//			if (arg1 == 0){
//				return node.getUserObject();
//			}
//			
//			if (arg1 == 1){
//				if (node.getUserObject() instanceof Account){
//					Account a = (Account) node.getUserObject();					
//					return new AmountFormatWrapper(a.getBalance(), a.getBalance() < 0, null, null);
//				}
//				else if (node.getUserObject() instanceof Category){
//					Category c = (Category) node.getUserObject();
//					return new AmountFormatWrapper(c.getBudgetedAmount(), !c.isIncome(), tree, node);
//				}
//				else if (node.getUserObject() instanceof TypeTotal){
//					TypeTotal t = (TypeTotal) node.getUserObject();
//					return new AmountFormatWrapper(t.getAmount(), t.getType().isCredit(), null, null);
//				}
//			}
			return node;
		}
		
		return null;
	}

	public void setValueAt(Object arg0, Object arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}
	
	public class AmountFormatWrapper {
		private long amount;
		private boolean negative;
//		private boolean deleted;
		private final DefaultMutableTreeNode node;
		private final JXTreeTable tree;
		
		public AmountFormatWrapper(long amount, boolean negative, JXTreeTable tree, DefaultMutableTreeNode node) {
			this.amount = amount;
			this.negative = negative;
//			this.deleted = deleted;
			this.node = node;
			this.tree = tree;
		}
		
		public long getAmount() {
			return amount;
		}
//		public boolean isDeleted() {
//			return deleted;
//		}
		public boolean isNegative() {
			return negative;
		}

		public DefaultMutableTreeNode getNode() {
			return node;
		}

		public JXTreeTable getTree() {
			return tree;
		}
		
	}
}
