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

import org.homeunix.drummer.Const;
import org.homeunix.drummer.model.Account;
import org.homeunix.drummer.model.DataInstance;
import org.homeunix.drummer.model.Type;
import org.homeunix.drummer.prefs.ListAttributes;
import org.homeunix.drummer.prefs.PrefsInstance;
import org.homeunix.drummer.view.AbstractPanel;
import org.homeunix.drummer.view.ListPanelLayout;
import org.homeunix.thecave.moss.util.Formatter;
import org.homeunix.thecave.moss.util.Log;

/**
 * The logic for the Accounts pane on the main window.
 * 
 * @author wolson
 *
 */
public class AccountListPanel extends ListPanelLayout {
	public static final long serialVersionUID = 0;
		
	public AccountListPanel(){
		super();
	}
	
	/* (non-Javadoc)
	 * @see org.homeunix.drummer.view.ListPanelLayout#initActions()
	 */
	protected AbstractPanel initActions(){
		super.initActions();
		
		newButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				tree.clearSelection();
				new AccountModifyDialog().clearContent().openWindow();
				AccountListPanel.this.updateButtons();
			}
		});
		
		editButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				new AccountModifyDialog().loadSource(getSelectedAccount()).openWindow();
				AccountListPanel.this.updateButtons();
			}
		});

		deleteButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				if (getSelectedAccount() != null) {
					
					Account a = getSelectedAccount();
					
					if (deleteButton.getText().equals(Translate.getInstance().get(TranslateKeys.DELETE))){
						
						//If there are no transactions using this source, ask if user wants to permanently delete source
						if (DataInstance.getInstance().getTransactions(a).size() > 0 
								|| JOptionPane.showConfirmDialog(
										AccountListPanel.this,
										Translate.getInstance().get(TranslateKeys.NO_TRANSACTIONS_USING_ACCOUNT),
										Translate.getInstance().get(TranslateKeys.PERMANENT_DELETE_ACCOUNT),
										JOptionPane.YES_NO_OPTION,
										JOptionPane.QUESTION_MESSAGE) == JOptionPane.NO_OPTION){
							
							DataInstance.getInstance().deleteSource(a);
						}
						else{
							DataInstance.getInstance().deleteSourcePermanent(a);
						}
					}
					else{
						DataInstance.getInstance().undeleteSource(a);
					}
					
					//We always want to update everything.  It's the cool thing to do.
					AccountListPanel.this.updateContent();
					AccountListPanel.this.updateButtons();
				}
			}
		});

		
		openButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				if (getSelectedAccount() != null){
					long start = System.currentTimeMillis();
					new TransactionsFrame(getSelectedAccount());
					long end = System.currentTimeMillis();
					if (Const.DEVEL) Log.info("Open button time: " + (end - start));
					AccountListPanel.this.updateButtons();
				}
			}
		});
		
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
	 * @see org.homeunix.drummer.view.AbstractPanel#initContent()
	 */
	protected AbstractPanel initContent(){
		DataInstance.getInstance().calculateAllBalances();
		
		return this;
	}
	
	/* (non-Javadoc)
	 * @see org.homeunix.drummer.view.AbstractPanel#updateContent()
	 */
	public AbstractPanel updateContent(){
		long balance = 0;
		
		treeModel.getRoot().removeAllChildren(); 
		selectedSource = null;
		
		Map<Type, DefaultMutableTreeNode> accountTypes = new HashMap<Type, DefaultMutableTreeNode>();
		Vector<DefaultMutableTreeNode> nodes = new Vector<DefaultMutableTreeNode>();
		
		for (Account a : DataInstance.getInstance().getAccounts()) {
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
				
		if (balance >= 0)
			balanceLabel.setForeground(Color.BLACK);
		else
			balanceLabel.setForeground(Color.RED);
		
		balanceLabel.setText(Translate.getInstance().get(TranslateKeys.NET_WORTH) + ": " + (balance >= 0 ? "" : "-") + PrefsInstance.getInstance().getPrefs().getCurrencySymbol() + Formatter.getInstance().getDecimalFormat().format(Math.abs((double) Math.abs(balance) / 100.0)));
		
		treeModel.reload(treeModel.getRoot());

		for (DefaultMutableTreeNode node : nodes) {
			if (Const.DEVEL) Log.debug("Checking node: " + node);
			ListAttributes l = PrefsInstance.getInstance().getListAttributes(Translate.getInstance().get(node.getUserObject().toString()));
			if (l != null && l.isUnrolled()){
				tree.expandPath(new TreePath(node.getPath()));
				if (Const.DEVEL) Log.debug("Unrolling node: " + l);
			}
		}
		
		return this;
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
}
