/*
 * Created on Jul 30, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeExpansionListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import org.homeunix.thecave.buddi.Const;
import org.homeunix.thecave.buddi.i18n.BuddiKeys;
import org.homeunix.thecave.buddi.i18n.keys.ButtonKeys;
import org.homeunix.thecave.buddi.i18n.keys.MessageKeys;
import org.homeunix.thecave.buddi.model.Account;
import org.homeunix.thecave.buddi.model.DataModel;
import org.homeunix.thecave.buddi.model.Type;
import org.homeunix.thecave.buddi.model.prefs.PrefsModel;
import org.homeunix.thecave.buddi.model.swing.AccountTreeTableModel;
import org.homeunix.thecave.buddi.util.InternalFormatter;
import org.homeunix.thecave.buddi.view.menu.bars.AccountFrameMenuBar;
import org.homeunix.thecave.buddi.view.menu.items.EditViewTransactions;
import org.homeunix.thecave.buddi.view.menu.items.FileSave;
import org.homeunix.thecave.moss.model.DocumentChangeEvent;
import org.homeunix.thecave.moss.model.DocumentChangeListener;
import org.homeunix.thecave.moss.swing.window.MossDocumentFrame;
import org.homeunix.thecave.moss.util.OperatingSystemUtil;
import org.jdesktop.swingx.JXTreeTable;
import org.jdesktop.swingx.decorator.HighlighterFactory;

public class AccountFrame extends MossDocumentFrame {
	public static final long serialVersionUID = 0;

	private final JXTreeTable tree;
	private final JLabel balanceLabel;

	private final AccountTreeTableModel treeTableModel;
	
	public AccountFrame(DataModel model) {
		super(model, "AccountFrame" + model.getUid());
		this.treeTableModel = new AccountTreeTableModel(model);
		
		tree = new JXTreeTable(treeTableModel);
		balanceLabel = new JLabel("Change Me");
	}
	
	public List<Account> getSelectedAccounts(){
		List<Account> accounts = new LinkedList<Account>();
		
		for (Integer i : tree.getSelectedRows()) {
			if (tree.getModel().getValueAt(i, -1) instanceof Account)
				accounts.add((Account) tree.getModel().getValueAt(i, -1));
		}
		
		return accounts;
	}
	
	public List<Type> getSelectedTypes(){
		List<Type> types = new LinkedList<Type>();
		
		for (Integer i : tree.getSelectedRows()) {
			if (tree.getModel().getValueAt(i, -1) instanceof Type)
				types.add((Type) tree.getModel().getValueAt(i, -1));
		}
		
		return types;
	}
	
	public void actionPerformed(ActionEvent e) {
		
	}

	public void init() {
		super.init();
		
		tree.setRootVisible(false);
		tree.setShowsRootHandles(true);
		tree.setAutoResizeMode(JXTreeTable.AUTO_RESIZE_ALL_COLUMNS);
		tree.setClosedIcon(null);
		tree.setOpenIcon(null);
		tree.setLeafIcon(null);
//		tree.setColumnSelectionAllowed(false);
		tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		tree.setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		tree.addHighlighter(HighlighterFactory.createAlternateStriping(Const.COLOR_EVEN_ROW, Const.COLOR_ODD_ROW));
		
		int treeColumnWidth = 30;
		tree.getColumn(0).setMaxWidth(treeColumnWidth);
		tree.getColumn(0).setMinWidth(treeColumnWidth);
		tree.getColumn(0).setPreferredWidth(treeColumnWidth);

		getDocument().addDocumentChangeListener(new DocumentChangeListener(){
			public void documentChange(DocumentChangeEvent event) {
				updateContent();
			}
		});
		
		JScrollPane listScroller = new JScrollPane(tree);

		JPanel balanceLabelPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		balanceLabelPanel.add(balanceLabel);

		JPanel listScrollerPanel = new JPanel(new BorderLayout());
		listScrollerPanel.add(listScroller, BorderLayout.CENTER);

		JPanel mainPanel = new JPanel(); 
		mainPanel.setLayout(new BorderLayout());

		mainPanel.add(listScrollerPanel, BorderLayout.CENTER);
		mainPanel.add(balanceLabelPanel, BorderLayout.SOUTH);
		
		if (OperatingSystemUtil.isMac()){
			listScroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		}

		tree.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				super.mouseClicked(e);
			}
		});
		
		tree.addTreeSelectionListener(new TreeSelectionListener(){
			public void valueChanged(TreeSelectionEvent arg0) {
				updateButtons();
			}
		});
		
		tree.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent arg0) {
				if (arg0.getClickCount() >= 2)
					new EditViewTransactions(AccountFrame.this).doClick();
				super.mouseClicked(arg0);
			}
		});
		
		tree.addTreeExpansionListener(new TreeExpansionListener(){
			public void treeCollapsed(TreeExpansionEvent event) {
				Object o = event.getPath().getPath()[event.getPath().getPath().length - 1];
				if (o instanceof Type){
					Type t = (Type) o;
					t.setExpanded(false);
				}
			}
			public void treeExpanded(TreeExpansionEvent event) {
				Object o = event.getPath().getPath()[event.getPath().getPath().length - 1];
				if (o instanceof Type){
					Type t = (Type) o;
					t.setExpanded(true);
				}				
			}
		});
		
		updateButtons();
		
		this.setJMenuBar(new AccountFrameMenuBar(this));
		this.setLayout(new BorderLayout());
		this.add(mainPanel, BorderLayout.CENTER);
	}

	public void updateContent() {
		String dataFile = getDocument().getFile() == null ? " - Unsaved " : " - " + getDocument().getFile().getAbsolutePath();
		this.setTitle(PrefsModel.getInstance().getTranslator().get(BuddiKeys.MY_ACCOUNTS) + dataFile + " - " + PrefsModel.getInstance().getTranslator().get(BuddiKeys.BUDDI));

		//We need to set the title first. 
		super.updateContent();
		
		//Fire a change event on the table model.
		treeTableModel.fireStructureChanged();
		
		//Restore the state of the expanded / unrolled nodes.
		for (Type t : getDataModel().getTypes()) {
			TreePath path = new TreePath(new Object[]{treeTableModel.getRoot(), t});
			if (t.isExpanded())
				tree.expandPath(path);
			else
				tree.collapsePath(path);
		}
		
		getDataModel().updateAllBalances();
		
		long netWorth = 0;
		for (Account a : getDataModel().getAccounts()) {
			netWorth += a.getBalance();
		}
		
		balanceLabel.setText(PrefsModel.getInstance().getTranslator().get(BuddiKeys.NET_WORTH) + ": " + InternalFormatter.getFormattedCurrency(netWorth));
		
	}
	
	@Override
	public boolean canClose() {
		if (getDataModel().isChanged()){
			String[] buttons = new String[3];
			buttons[0] = PrefsModel.getInstance().getTranslator().get(ButtonKeys.BUTTON_YES);
			buttons[1] = PrefsModel.getInstance().getTranslator().get(ButtonKeys.BUTTON_NO);
			buttons[2] = PrefsModel.getInstance().getTranslator().get(ButtonKeys.BUTTON_CANCEL);

			int reply = JOptionPane.showOptionDialog(
					this, 
					PrefsModel.getInstance().getTranslator().get(MessageKeys.MESSAGE_PROMPT_FOR_SAVE),
					PrefsModel.getInstance().getTranslator().get(MessageKeys.MESSAGE_PROMPT_FOR_SAVE_TITLE),
					JOptionPane.YES_NO_CANCEL_OPTION,
					JOptionPane.QUESTION_MESSAGE,
					null,
					buttons,
					buttons[0]);

			if (reply == JOptionPane.YES_OPTION){
				//We want to exit, but save first.
				new FileSave(this).doClick();
			}
			else if (reply == JOptionPane.CANCEL_OPTION){
				//We don't want to exit.
				return false;
			}
		}
		return true;
	}
	
//	void addAssociatedFrame(AbstractFrame frame){
//		associatedFrames.add(frame);
//	}
//	
//	void removeAssociatedFrame(AbstractBuddiFrame frame){
//		associatedFrames.remove(frame);
//	}
	
	@Override
	public Object closeWindow() {
		PrefsModel.getInstance().setAccountWindowSize(this.getSize());
		PrefsModel.getInstance().setAccountWindowLocation(this.getLocation());
		PrefsModel.getInstance().save();
				
		return super.closeWindow();
	}
	
	public DataModel getDataModel(){
		return (DataModel) getDocument();
	}
}
