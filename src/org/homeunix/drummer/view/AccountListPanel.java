/*
 * Created on May 6, 2006 by wyatt
 */
package org.homeunix.drummer.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
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
import org.homeunix.drummer.model.Account;
import org.homeunix.drummer.model.Type;
import org.homeunix.drummer.prefs.ListAttributes;
import org.homeunix.drummer.prefs.PrefsInstance;
import org.homeunix.drummer.prefs.WindowAttributes;
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
				//Don't show account types
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

//		balanceLabel.setText("<html>"
//				+ Translate.getInstance().get(TranslateKeys.NET_WORTH) 
//				+ ": "  
//				+ FormatterWrapper.getFormattedCurrencyForAccount(balance, false)
//				+ "</html>");

		treeModel.reload(treeModel.getRoot());

		for (DefaultMutableTreeNode node : nodes) {
			if (Const.DEVEL) Log.debug("Checking node: " + node);
			ListAttributes l = PrefsInstance.getInstance().getListAttributes(Translate.getInstance().get(node.getUserObject().toString()));
			if (l != null && l.isUnrolled()){
				tree.expandPath(new TreePath(node.getPath()));
				if (Const.DEVEL) Log.debug("Unrolling node: " + l);
			}
		}
		
		updateNetWorth();
		updateButtons();

		return super.updateContent();
	}
	
	public void updateNetWorth() {
		
		long balance = 0;
		
		for (Account a : SourceController.getAccounts()) {
			balance += a.getBalance();
		}
		
		balanceLabel.setForeground(APICommonFormatter.isRed(balance) ? Color.RED : Color.BLACK);
		balanceLabel.setText(Translate.getInstance().get(TranslateKeys.NET_WORTH) 
				+ ": "  
				+ APICommonFormatter.getFormattedCurrency(balance));
	}

	/**
	 * Returns the account which is currently selected
	 * in the JTree, or null if there is not an account selected.
	 * @return
	 */
	public Account getSelectedAccount(){

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
			new AccountModifyDialog(null).clear().openWindow();
			AccountListPanel.this.updateButtons();
		}
		else if (e.getSource().equals(editButton)) {
			new AccountModifyDialog(getSelectedAccount()).openWindow();
			AccountListPanel.this.updateButtons();
		}
		else if (e.getSource().equals(deleteButton)){
			if (getSelectedAccount() != null) {

				Account a = getSelectedAccount();

				if (deleteButton.getText().equals(Translate.getInstance().get(TranslateKeys.BUTTON_DELETE))){
					SourceController.deleteAccount(a);
				}
				else{
					SourceController.undeleteSource(a);
				}

				//We always want to update everything.  It's the cool thing to do.
				AccountListPanel.this.updateContent();
				AccountListPanel.this.updateButtons();
			}
		}
		else if (e.getSource().equals(openButton)){
			if (getSelectedAccount() != null){
				long start = System.currentTimeMillis();

//				final JStatusDialog progress = new JStatusDialog(
//						MainFrame.getInstance(), 
//						Translate.getInstance().get(TranslateKeys.MESSAGE_OPENING_WINDOW));
//				progress.openWindow(new Dimension(150, 50), null);
				
				WindowAttributes wa = PrefsInstance.getInstance().getPrefs().getWindows().getTransactionsWindow();
				Dimension dimension = new Dimension(wa.getWidth(), wa.getHeight());
				Point point = new Point(wa.getX(), wa.getY());

				new TransactionsFrame(getSelectedAccount()).openWindow(dimension, point);
				
				long end = System.currentTimeMillis();
				if (Const.DEVEL) Log.info("Open button time: " + (end - start));
				AccountListPanel.this.updateButtons();
				
//				progress.closeWindow();
			}
		}
	}
}
