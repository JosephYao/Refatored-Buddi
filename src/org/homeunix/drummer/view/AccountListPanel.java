/*
 * Created on May 6, 2006 by wyatt
 */
package org.homeunix.drummer.view;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import javax.swing.JOptionPane;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import org.homeunix.drummer.Const;
import org.homeunix.drummer.controller.AccountListPanelController;
import org.homeunix.drummer.controller.ScheduleController;
import org.homeunix.drummer.controller.SourceController;
import org.homeunix.drummer.controller.TransactionController;
import org.homeunix.drummer.controller.Translate;
import org.homeunix.drummer.controller.TranslateKeys;
import org.homeunix.drummer.model.Account;
import org.homeunix.drummer.model.Type;
import org.homeunix.drummer.prefs.ListAttributes;
import org.homeunix.drummer.prefs.PrefsInstance;
import org.homeunix.drummer.prefs.WindowAttributes;
import org.homeunix.drummer.util.FormatterWrapper;
import org.homeunix.thecave.moss.util.Log;

/**
 * The logic for the Accounts pane on the main window.
 * 
 * @author wolson
 *
 */
public class AccountListPanel extends AbstractListPanel {
	public static final long serialVersionUID = 0;

	@Override
	public AbstractBuddiPanel init(){
		super.init();
		
		tree.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent arg0) {
				if (arg0.getClickCount() >= 2)
					openButton.doClick();
				super.mouseClicked(arg0);
			}
		});
		
		return this;
	}

	/* (non-Javadoc)
	 * @see org.homeunix.drummer.view.AbstractPanel#updateContent()
	 */
	public AbstractBuddiPanel updateContent(){
		long balance = 0;

		treeModel.getRoot().removeAllChildren(); 
		selectedSource = null;

		Map<Type, DefaultMutableTreeNode> accountTypes = new HashMap<Type, DefaultMutableTreeNode>();
		Vector<DefaultMutableTreeNode> nodes = new Vector<DefaultMutableTreeNode>();

		for (Account a : SourceController.getAccounts()) {
			if (!a.isDeleted() || PrefsInstance.getInstance().getPrefs().isShowDeletedAccounts()){
				if (PrefsInstance.getInstance().getPrefs().isShowAccountTypes()){
					DefaultMutableTreeNode accountType;
					if (accountTypes.get(a.getAccountType()) != null) {
						accountType = accountTypes.get(a.getAccountType());
						if (accountType.getUserObject() instanceof TypeTotal){
							TypeTotal tt = (TypeTotal) accountType.getUserObject();
							tt.add(a.getBalance());
						}
					}
					else {
						accountType = new DefaultMutableTreeNode(new TypeTotal(a.getAccountType()));
						if (accountType.getUserObject() instanceof TypeTotal){
							TypeTotal tt = (TypeTotal) accountType.getUserObject();
							tt.add(a.getBalance());
						}
						accountTypes.put(a.getAccountType(), accountType);
						treeModel.getRoot().add(accountType);
						nodes.add(accountType);
					}
					DefaultMutableTreeNode account = new DefaultMutableTreeNode(a);
					accountType.add(account);
					nodes.add(account);
				}
				//Don't show categories
				else {
					DefaultMutableTreeNode account = new DefaultMutableTreeNode(a);
					treeModel.getRoot().add(account);
					nodes.add(account);
				}
			}

			if (Const.DEVEL) Log.debug(a);

			balance += a.getBalance();
		}

//		if (balance >= 0)
//			balanceLabel.setForeground(Color.BLACK);
//		else
//			balanceLabel.setForeground(Color.RED);

		balanceLabel.setText("<html>"
				+ Translate.getInstance().get(TranslateKeys.NET_WORTH) 
				+ ": "  
				+ FormatterWrapper.getFormattedCurrencyForAccount(balance, false)
				+ "</html>");

		treeModel.reload(treeModel.getRoot());

		for (DefaultMutableTreeNode node : nodes) {
			if (Const.DEVEL) Log.debug("Checking node: " + node);
			ListAttributes l = PrefsInstance.getInstance().getListAttributes(Translate.getInstance().get(node.getUserObject().toString()));
			if (l != null && l.isUnrolled()){
				tree.expandPath(new TreePath(node.getPath()));
				if (Const.DEVEL) Log.debug("Unrolling node: " + l);
			}
		}

		return super.updateContent();
	}

	/**
	 * Returns the account which is currently selected
	 * in the JTree, or null if there is not an account selected.
	 * @return
	 */
	public Account getSelectedAccount(){

		if (Const.DEVEL) Log.debug(selectedSource);
		if (selectedSource instanceof Account)
			return (Account) selectedSource;
		else
			return null;
	}

	/**
	 * Wrapper class used to display totals for each account type.
	 * @author wolson
	 *
	 */
	public class TypeTotal {
		private final Type type;
		private long amount;

		public TypeTotal(Type type){
			this.type = type;
			amount = 0;
		}

		public Type getType(){
			return type;
		}

		public void add(long amount){
			this.amount += amount;
		}

		public long getAmount(){
			return amount;
		}

		@Override
		public String toString() {
			return type.toString();
		}
	}

//	@Override
//	protected int getTableNumber() {
//		return 0;
//	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(newButton)){
			tree.clearSelection();
			new AccountModifyDialog().clear().openWindow();
			AccountListPanel.this.updateButtons();
		}
		else if (e.getSource().equals(editButton)) {
			new AccountModifyDialog().loadSource(getSelectedAccount()).openWindow();
			AccountListPanel.this.updateButtons();
		}
		else if (e.getSource().equals(deleteButton)){
			if (getSelectedAccount() != null) {

				Account a = getSelectedAccount();

				if (deleteButton.getText().equals(Translate.getInstance().get(TranslateKeys.BUTTON_DELETE))){
					boolean notPermanent = TransactionController.getTransactions(a).size() > 0
					|| ScheduleController.getScheduledTransactions(a).size() > 0
					|| JOptionPane.showConfirmDialog(
							AccountListPanel.this,
							Translate.getInstance().get(TranslateKeys.NO_TRANSACTIONS_USING_ACCOUNT),
							Translate.getInstance().get(TranslateKeys.PERMANENT_DELETE_ACCOUNT),
							JOptionPane.YES_NO_OPTION,
							JOptionPane.QUESTION_MESSAGE) == JOptionPane.NO_OPTION;
					AccountListPanelController.deleteAccount(notPermanent, a);							
				}
				else{
					AccountListPanelController.undeleteAccount(a);
				}

				//We always want to update everything.  It's the cool thing to do.
				AccountListPanel.this.updateContent();
				AccountListPanel.this.updateButtons();
			}
		}
		else if (e.getSource().equals(openButton)){
			if (getSelectedAccount() != null){
				long start = System.currentTimeMillis();

				TransactionsFrame tf = TransactionsFrame.getPreloader().get(getSelectedAccount());
				
				if (tf != null){
					tf.setVisible(true);
				}
				else {
					WindowAttributes wa = PrefsInstance.getInstance().getPrefs().getWindows().getTransactionsWindow();
					Dimension dimension = new Dimension(wa.getWidth(), wa.getHeight());
					Point point = new Point(wa.getX(), wa.getY());

					new TransactionsFrame(getSelectedAccount()).openWindow(dimension, point);
				}
				
				long end = System.currentTimeMillis();
				if (Const.DEVEL) Log.info("Open button time: " + (end - start));
				AccountListPanel.this.updateButtons();
			}
		}
	}
}
