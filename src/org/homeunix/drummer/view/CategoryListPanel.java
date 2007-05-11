/*
 * Created on May 6, 2006 by wyatt
 */
package org.homeunix.drummer.view;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import net.sourceforge.buddi.api.manager.APICommonFormatter;

import org.homeunix.drummer.Const;
import org.homeunix.drummer.controller.SourceController;
import org.homeunix.drummer.controller.Translate;
import org.homeunix.drummer.controller.TranslateKeys;
import org.homeunix.drummer.model.Category;
import org.homeunix.drummer.prefs.ListAttributes;
import org.homeunix.drummer.prefs.PrefsInstance;
import org.homeunix.thecave.moss.gui.abstractwindows.StandardContainer;
import org.homeunix.thecave.moss.util.Log;

public class CategoryListPanel extends AbstractListPanel {
	public static final long serialVersionUID = 0;

	public CategoryListPanel(){
		super();


	}

	@Override
	public StandardContainer init() {
		super.init();

		openButton.setVisible(false);

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

	public AbstractBuddiPanel updateContent(){
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

//		if (income < expenses)
//			balanceLabel.setForeground(Color.RED);
//		else
//			balanceLabel.setForeground(Color.BLACK);

//		StringBuffer sb = new StringBuffer();
//		sb.append("<html>")
//		.append(Translate.getInstance().get(TranslateKeys.BUDGET_NET_INCOME))
//		.append(" ")
//		.append(Translate.getInstance().get(PrefsInstance.getInstance().getSelectedInterval().getName()))
//		.append(": ")
//		.append(FormatterWrapper.getFormattedCurrencyForCategory(income - expenses, true))
//		.append("</html>");

		balanceLabel.setForeground(APICommonFormatter.isRed(income - expenses) ? Color.RED : Color.BLACK);
		balanceLabel.setText(Translate.getInstance().get(TranslateKeys.BUDGET_NET_INCOME)
				+ " "
				+ Translate.getInstance().get(PrefsInstance.getInstance().getSelectedInterval().getName())
				+ ": "  
				+ APICommonFormatter.getFormattedCurrency(income - expenses));

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

//	@Override
//	protected int getTableNumber() {
//		return 1;
//	}

//	public void clickNew(){
//		newButton.doClick();
//	}
//	public void clickEdit(){
//		editButton.doClick();
//	}
//	public void clickDelete(){
//		deleteButton.doClick();
//	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(newButton)){
			tree.clearSelection();
			new CategoryModifyDialog(null).clear().openWindow();
		}
		else if (e.getSource().equals(editButton)){
			if (getSelectedCategory() != null)
				new CategoryModifyDialog(getSelectedCategory()).openWindow();
		}
		else if (e.getSource().equals(deleteButton)){
			if (getSelectedCategory() != null) {
				Category c = getSelectedCategory();

				if (deleteButton.getText().equals(Translate.getInstance().get(TranslateKeys.BUTTON_DELETE))){
					SourceController.deleteCategory(c);
				}
				else{
					SourceController.undeleteSource(c);
//					CategoryListPanelController.undeleteCategory(c);
				}

				//We always want to update everything.  It's the cool thing to do.
				this.updateContent();
				this.updateButtons();
			}
		}
	}
}
