/*
 * Created on May 12, 2006 by wyatt
 */
package org.homeunix.drummer.view.components;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreePath;

import org.homeunix.drummer.Strings;
import org.homeunix.drummer.model.Account;
import org.homeunix.drummer.model.Category;
import org.homeunix.drummer.util.Formatter;


public class SourceCellRenderer extends JPanel implements TreeCellRenderer {
	public static final long serialVersionUID = 0;
	
	private final JLabel nameLabel;
	private final JLabel amountLabel;
	
	public SourceCellRenderer(){
		nameLabel = new JLabel();
		amountLabel = new JLabel();
		
		nameLabel.setPreferredSize(new Dimension(250, nameLabel.getPreferredSize().height));
		amountLabel.setPreferredSize(new Dimension(100, amountLabel.getPreferredSize().height));
		
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		this.add(nameLabel);
		this.add(amountLabel);
	}
	
	public Component getTreeCellRendererComponent(JTree tree, Object node, boolean isSelected, boolean expanded, boolean leaf, int row, boolean hasFocus) {
//		super.getTreeCellRendererComponent(tree, node, isSelected, expanded, leaf, row, hasFocus);
		
		Object obj;
		DefaultMutableTreeNode n;
		if (node instanceof DefaultMutableTreeNode) {
			n = (DefaultMutableTreeNode) node;
			obj = n.getUserObject();
		}
		else
			return this;
		
		this.setBorder(BorderFactory.createEmptyBorder());
//		this.setIcon(null);
		
		if (obj instanceof Category) {
			Category c = (Category) obj;
			StringBuffer sbOpen = new StringBuffer("<html>");
			StringBuffer sbClose = new StringBuffer("</html>");
			
			if (c.isDeleted()){
				sbOpen.append("<s>");
				sbClose.insert(0, "</s>");
			}

			if (!c.isIncome()){
				sbOpen.append("<font color='red'>");
				sbClose.insert(0, "</font>");
			}

			
//			sbOpen.append(
//					c.toString())
//					.append(": ")
//					.append(Strings.inst().get(Strings.CURRENCY_SIGN));
			
			long amount;
			
			if (tree.isCollapsed(new TreePath(n.getPath())))
				amount = getTotalAmount(n);
			else
				amount = c.getBudgetedAmount();
			
//			sbOpen.append(Formatter.getInstance().getDecimalFormat().format(Math.abs((double) amount / 100.0)));
//			sbOpen.append(sbClose.toString());
			
			nameLabel.setText(
					sbOpen.toString() 
					+ c.toString()
					+ sbClose.toString()
			);

			amountLabel.setText(
					sbOpen.toString()
					+ Strings.inst().get(Strings.CURRENCY_SIGN)
					+ Formatter.getInstance().getDecimalFormat().format(Math.abs((double) amount / 100.0))
					+ sbClose.toString()
			);
//			this.setText(sbOpen.toString());	
		}
		else if (obj instanceof Account) {			
			Account a = (Account) obj;
			StringBuffer sbOpen = new StringBuffer("<html>");
			StringBuffer sbClose = new StringBuffer("</html>");
			
			if (a.isDeleted()){
				sbOpen.append("<s>");
				sbClose.insert(0, "</s>");
			}
			
			if (a.getBalance() < 0){
				sbOpen.append("<font color='red'>");
				sbClose.insert(0, "</font>");
			}
			
//			sbOpen.append(a.toStringLong()).append(sbClose.toString());
			
			nameLabel.setText(
					sbOpen.toString() 
					+ a.toString()
					+ sbClose.toString()
			);

			amountLabel.setText(
					sbOpen.toString()
					+ Strings.inst().get(Strings.CURRENCY_SIGN)
					+ Formatter.getInstance().getDecimalFormat().format(Math.abs((double) a.getBalance() / 100.0))
					+ sbClose.toString()
			);	
		}
		
		return this;
	}
	
	private long getTotalAmount(DefaultMutableTreeNode node){
		long amount = 0;
		if (node.getUserObject() instanceof Category){
			amount = ((Category) node.getUserObject()).getBudgetedAmount();
		}
		
		for (int i = 0; i < node.getChildCount(); i++) {
			amount += getTotalAmount((DefaultMutableTreeNode) node.getChildAt(i));
		}
		
		return amount;
	}
}
