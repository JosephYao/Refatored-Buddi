/*
 * Created on May 12, 2006 by wyatt
 */
package org.homeunix.drummer.view.components;

import java.awt.BorderLayout;
import java.awt.Color;
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

import org.homeunix.drummer.Buddi;
import org.homeunix.drummer.Strings;
import org.homeunix.drummer.model.Account;
import org.homeunix.drummer.model.Category;
import org.homeunix.drummer.util.Formatter;


public class SourceCellRenderer extends JLabel implements TreeCellRenderer {
	public static final long serialVersionUID = 0;
	
//	private final JLabel nameLabel;
//	private final JLabel amountLabel;
	
	public SourceCellRenderer(){
		super();
		
//		nameLabel = new JLabel();
//		amountLabel = new JLabel();
//		
//		nameLabel.setPreferredSize(new Dimension(250, nameLabel.getPreferredSize().height));
//		amountLabel.setPreferredSize(new Dimension(100, amountLabel.getPreferredSize().height));
//		
//		this.setMinimumSize(new Dimension(400, 40));
		
//		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
//		this.setLayout(new BorderLayout());
//		this.setOpaque(false);
//		this.add(nameLabel, BorderLayout.WEST);
//		this.add(amountLabel, BorderLayout.EAST);
	}
	
	public Component getTreeCellRendererComponent(JTree tree, Object node, boolean isSelected, boolean expanded, boolean leaf, int row, boolean hasFocus) {
		Object obj;
		DefaultMutableTreeNode n;
		if (node instanceof DefaultMutableTreeNode) {
			n = (DefaultMutableTreeNode) node;
			obj = n.getUserObject();
		}
		else
			return this;
		
		this.setBorder(BorderFactory.createEmptyBorder());
		
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
						
			long amount;
			
			if (tree.isCollapsed(new TreePath(n.getPath())))
				amount = getTotalAmount(n);
			else
				amount = c.getBudgetedAmount();

//			this.setText(
//					sbOpen.toString() 
//					+ c.toString()
//					+ ": "
//					+ Strings.inst().get(Strings.CURRENCY_SIGN)
//					+ Formatter.getInstance().getDecimalFormat().format(Math.abs((double) amount / 100.0))
//					+ sbClose.toString()
//			);
			
			StringBuffer sb = new StringBuffer();
			
			
			sb.append("<html><table><tr><td width=180px>")
					.append(sbOpen.toString())
					.append(c.toString())
					.append(sbClose.toString())
					.append("</td><td width=70px>")
					.append(sbOpen.toString())
					.append(Strings.inst().get(Strings.CURRENCY_SIGN))
					.append(Formatter.getInstance().getDecimalFormat().format(Math.abs((double) amount / 100.0)))
					.append(sbClose.toString())
					.append("</td></tr></table></html>");
			
			this.setText(sb.toString());

//			nameLabel.setText(
//					sbOpen.toString() 
//					+ c.toString()
//					+ sbClose.toString()
//			);
//
//			amountLabel.setText(
//					sbOpen.toString()
//					+ Strings.inst().get(Strings.CURRENCY_SIGN)
//					+ Formatter.getInstance().getDecimalFormat().format(Math.abs((double) amount / 100.0))
//					+ sbClose.toString()
//			);
		}
		else if (obj instanceof Account) {			
			Account a = (Account) obj;
//			StringBuffer sbOpen = new StringBuffer("<html>");
//			StringBuffer sbClose = new StringBuffer("</html>");
			StringBuffer sbOpen = new StringBuffer();
			StringBuffer sbClose = new StringBuffer();
			
			if (a.isDeleted()){
				sbOpen.append("<s>");
				sbClose.insert(0, "</s>");
			}
			
			if (a.getBalance() < 0){
				sbOpen.append("<font color='red'>");
				sbClose.insert(0, "</font>");
			}
			
//			this.setText(
//					sbOpen.toString() 
//					+ a.toString()
//					+ ": "
//					+ Strings.inst().get(Strings.CURRENCY_SIGN)
//					+ Formatter.getInstance().getDecimalFormat().format(Math.abs((double) a.getBalance() / 100.0))
//					+ sbClose.toString()
//			);
			
			StringBuffer sb = new StringBuffer();
			
			
			sb.append("<html><table><tr><td width=200px>")
					.append(sbOpen.toString())
					.append(a.toString())
					.append(sbClose.toString())
					.append("</td><td width=70px>")
					.append(sbOpen.toString())
					.append(Strings.inst().get(Strings.CURRENCY_SIGN))
					.append(Formatter.getInstance().getDecimalFormat().format(Math.abs((double) a.getBalance() / 100.0)))
					.append(sbClose.toString())
					.append("</td></tr></table></html>");
			
			this.setText(sb.toString());
			
//			nameLabel.setText(
//					sbOpen.toString() 
//					+ a.toString()
//					+ sbClose.toString()
//			);
//
//			amountLabel.setText(
//					sbOpen.toString()
//					+ Strings.inst().get(Strings.CURRENCY_SIGN)
//					+ Formatter.getInstance().getDecimalFormat().format(Math.abs((double) a.getBalance() / 100.0))
//					+ sbClose.toString()
//			);	
		}
		
		if (!Buddi.isMac()){
			if (isSelected){
				this.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
			}
			else{
				this.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
			}
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
