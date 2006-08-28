/*
 * Created on May 6, 2006 by wyatt
 */
package org.homeunix.drummer.controller;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import javax.swing.JOptionPane;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import org.homeunix.drummer.model.Category;
import org.homeunix.drummer.model.DataInstance;
import org.homeunix.drummer.prefs.ListAttributes;
import org.homeunix.drummer.prefs.PrefsInstance;
import org.homeunix.drummer.util.Formatter;
import org.homeunix.drummer.util.Log;
import org.homeunix.drummer.view.AbstractBudgetPanel;
import org.homeunix.drummer.view.ListPanelLayout;

public class CategoryListPanel extends ListPanelLayout {
	public static final long serialVersionUID = 0;
	
	public CategoryListPanel(){
		super();
		
		openButtonPanel.setVisible(false);
	}
	
	protected AbstractBudgetPanel initActions(){
		super.initActions();
		
		newButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				tree.clearSelection();
				new CategoryModifyDialog().clearContent().openWindow();
			}
		});
		
		editButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				if (getSelectedCategory() != null)
					new CategoryModifyDialog().loadSource(getSelectedCategory()).openWindow();
			}
		});

		deleteButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				if (getSelectedCategory() != null) {
					Category c = getSelectedCategory();
					
					if (deleteButton.getText().equals(Translate.getInstance().get(TranslateKeys.DELETE))){
						
						//If there are no transactions using this source, ask if user wants to permanently delete source
						if (DataInstance.getInstance().getTransactions(c).size() > 0 
								|| c.getChildren().size() > 0
								|| JOptionPane.showConfirmDialog(
										CategoryListPanel.this,
										Translate.getInstance().get(TranslateKeys.NO_TRANSACTIONS_USING_CATEGORY),
										Translate.getInstance().get(TranslateKeys.PERMANENT_DELETE_CATEGORY),
										JOptionPane.YES_NO_OPTION,
										JOptionPane.QUESTION_MESSAGE) == JOptionPane.NO_OPTION){
							
							DataInstance.getInstance().deleteSource(c);
						}
						else{
							DataInstance.getInstance().deleteSourcePermanent(c);
							if (c.getParent() != null)
								c.getParent().getChildren().remove(c);
						}
					}
					else{
						DataInstance.getInstance().undeleteSource(c);
					}
					
					//We always want to update everything.  It's the cool thing to do.
					CategoryListPanel.this.updateContent();
					CategoryListPanel.this.updateButtons();
				}
			}
		});
		
		tree.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent arg0) {
				if (arg0.getClickCount() >= 2)
					editButton.doClick();
				else
					super.mouseClicked(arg0);
			}
		});
		
		return this;
	}
	
	protected AbstractBudgetPanel initContent(){
		DataInstance.getInstance().calculateAllBalances();
		
		return this;
	}
	
	public AbstractBudgetPanel updateContent(){
		long expenses = 0;
		long income = 0;

		root.removeAllChildren();
		selectedSource = null;
		
		//Since the persistence layer knows of all the categories, and
		// does not view them in a hierarchical fashion, we cannot
		// do a simple depth first search on the tree, for fear
		// of adding the same category twice (or more).  Instead, we
		// keep track of which ones we have already added with this map,
		// and only add ones which have not yet been added.
		Map<Category, Category> alreadyEntered = new HashMap<Category, Category>();
		
		Vector<DefaultMutableTreeNode> nodes = new Vector<DefaultMutableTreeNode>();
		
		for (Category c : DataInstance.getInstance().getCategories()) {
			DefaultMutableTreeNode n = new DefaultMutableTreeNode(c);
			DefaultMutableTreeNode child = recursiveAdd(n, c, alreadyEntered);
			if (child != null){
				root.add(child);
				nodes.add(child);
			}
			
			if (c.isIncome())
				income += c.getBudgetedAmount();
			else
				expenses += c.getBudgetedAmount();

		}
				
		if (income < expenses)
			balanceLabel.setForeground(Color.RED);
		else
			balanceLabel.setForeground(Color.BLACK);
		
		StringBuffer sb = new StringBuffer();
		sb.append(Translate.getInstance().get(TranslateKeys.BUDGET_NET_INCOME))
				.append(" ")
				.append(Translate.getInstance().get(PrefsInstance.getInstance().getSelectedInterval().getName()))
				.append(": ")
				.append(((income - expenses) >= 0 ? "" : "-"))
				.append(PrefsInstance.getInstance().getPrefs().getCurrencySymbol())
				.append(Formatter.getInstance().getDecimalFormat().format(Math.abs((double) Math.abs(income - expenses) / 100.0)));
		
		balanceLabel.setText(sb.toString());
		
		treeModel.reload(root);
		
		//Expand all the nodes
		for (DefaultMutableTreeNode node : nodes) {
			ListAttributes l = PrefsInstance.getInstance().getListAttributes(node.getUserObject().toString());
			if (l != null && l.isUnrolled())
				tree.expandPath(new TreePath(node.getPath()));
		}
		
		return this;
	}
	
	private DefaultMutableTreeNode recursiveAdd(DefaultMutableTreeNode n, Category c, Map<Category, Category> alreadyEntered){
		Log.debug("Trying to add " + c);
		if (alreadyEntered.get(c) == null){
			Log.debug("Not already added.");
			alreadyEntered.put(c, c);
			for (Object o : c.getChildren()) {
				Log.debug("Child " + o + " of " + c);
				if (o instanceof Category){
					Category cat = (Category) o;
					DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(cat);
					DefaultMutableTreeNode child = recursiveAdd(newNode, cat, alreadyEntered);
					if (child != null)
						n.add(child);
				}
			}
			Log.debug("Done processing all children.");
			return n;			
		}
		Log.debug("Already entered.");
		return null;
	}

	public Category getSelectedCategory(){
		if (selectedSource instanceof Category)
			return (Category) selectedSource;
		else
			return null;
	}

}
