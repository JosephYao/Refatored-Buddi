/*
 * Created on May 6, 2006 by wyatt
 */
package org.homeunix.drummer.view;

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

import org.homeunix.drummer.Const;
import org.homeunix.drummer.controller.CategoryListPanelController;
import org.homeunix.drummer.controller.ScheduleController;
import org.homeunix.drummer.controller.SourceController;
import org.homeunix.drummer.controller.TransactionController;
import org.homeunix.drummer.controller.Translate;
import org.homeunix.drummer.controller.TranslateKeys;
import org.homeunix.drummer.model.Category;
import org.homeunix.drummer.model.DataInstance;
import org.homeunix.drummer.prefs.ListAttributes;
import org.homeunix.drummer.prefs.PrefsInstance;
import org.homeunix.thecave.moss.util.Log;

public class CategoryListPanel extends AbstractListPanel {
	public static final long serialVersionUID = 0;

	public CategoryListPanel(){
		super();

		openButton.setVisible(false);
	}

	protected AbstractPanel initActions(){
		super.initActions();

		newButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				tree.clearSelection();
				new CategoryModifyDialog().clear().openWindow();
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
						boolean notPermanent = TransactionController.getTransactions(c).size() > 0
						|| ScheduleController.getScheduledTransactions(c).size() > 0
						|| c.getChildren().size() > 0
						|| JOptionPane.showConfirmDialog(
								CategoryListPanel.this,
								Translate.getInstance().get(TranslateKeys.NO_TRANSACTIONS_USING_CATEGORY),
								Translate.getInstance().get(TranslateKeys.PERMANENT_DELETE_CATEGORY),
								JOptionPane.YES_NO_OPTION,
								JOptionPane.QUESTION_MESSAGE) == JOptionPane.NO_OPTION;

						CategoryListPanelController.deleteCategory(notPermanent, c);
					}
					else{
						CategoryListPanelController.undeleteCategory(c);
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

	protected AbstractPanel initContent(){
		DataInstance.getInstance().calculateAllBalances();

		return this;
	}

	public AbstractPanel updateContent(){
		long expenses = 0;
		long income = 0;

		treeModel.getRoot().removeAllChildren();
		selectedSource = null;

		//Since the persistence layer knows of all the categories, and
		// does not view them in a hierarchical fashion, we cannot
		// do a simple depth first search on the tree, for fear
		// of adding the same category twice (or more).  Instead, we
		// keep track of which ones we have already added with this map,
		// and only add ones which have not yet been added.
		Map<Category, Category> alreadyEntered = new HashMap<Category, Category>();

		Vector<DefaultMutableTreeNode> nodes = new Vector<DefaultMutableTreeNode>();

		for (Category c : SourceController.getCategories()) {
			DefaultMutableTreeNode n = new DefaultMutableTreeNode(c);
			DefaultMutableTreeNode child = recursiveAdd(n, c, alreadyEntered);
			if (child != null){
				treeModel.getRoot().add(child);
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
		.append(Translate.getFormattedCurrency(income - expenses));

		balanceLabel.setText(sb.toString());

		treeModel.reload(treeModel.getRoot());

		//Expand all the nodes
		for (DefaultMutableTreeNode node : nodes) {
			ListAttributes l = PrefsInstance.getInstance().getListAttributes(node.getUserObject().toString());
			if (l != null && l.isUnrolled())
				tree.expandPath(new TreePath(node.getPath()));
		}

		return super.updateContent();
	}

	private DefaultMutableTreeNode recursiveAdd(DefaultMutableTreeNode n, Category c, Map<Category, Category> alreadyEntered){
		if (Const.DEVEL) Log.debug("Trying to add " + c);
		if (alreadyEntered.get(c) == null){
			if (Const.DEVEL) Log.debug("Not already added.");
			alreadyEntered.put(c, c);
			for (Object o : c.getChildren()) {
				if (Const.DEVEL) Log.debug("Child " + o + " of " + c);
				if (o instanceof Category){
					Category cat = (Category) o;
					DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(cat);
					DefaultMutableTreeNode child = recursiveAdd(newNode, cat, alreadyEntered);
					if (child != null)
						n.add(child);
				}
			}
			if (Const.DEVEL) Log.debug("Done processing all children.");
			return n;			
		}
		if (Const.DEVEL) Log.debug("Already entered.");
		return null;
	}

	public Category getSelectedCategory(){
		if (selectedSource instanceof Category)
			return (Category) selectedSource;
		else
			return null;
	}

	@Override
	protected int getTableNumber() {
		return 1;
	}

	public void clickNew(){
		newButton.doClick();
	}
	public void clickEdit(){
		editButton.doClick();
	}
	public void clickDelete(){
		deleteButton.doClick();
	}
}
