/*
 * Created on Aug 19, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.view;

import java.awt.BorderLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;

import org.homeunix.thecave.buddi.i18n.BuddiKeys;
import org.homeunix.thecave.buddi.i18n.keys.ButtonKeys;
import org.homeunix.thecave.buddi.i18n.keys.MessageKeys;
import org.homeunix.thecave.buddi.model.Account;
import org.homeunix.thecave.buddi.model.BudgetCategory;
import org.homeunix.thecave.buddi.model.DataModel;
import org.homeunix.thecave.buddi.model.prefs.PrefsModel;
import org.homeunix.thecave.buddi.plugin.api.util.TextFormatter;
import org.homeunix.thecave.buddi.view.menu.bars.MainFrameMenuBar;
import org.homeunix.thecave.buddi.view.menu.items.FileSave;
import org.homeunix.thecave.moss.swing.MossDocumentFrame;

public class MainFrame extends MossDocumentFrame {
	public static final long serialVersionUID = 0;

	private final MyAccountsPanel myAccounts;
	private final MyBudgetPanel myBudget;
	private final MyReportsPanel myReports;
	
	private final JTabbedPane tabs;
	
	public MainFrame(DataModel model) {
		super(model, "MainWindow" + model.getUid());
		
		myAccounts = new MyAccountsPanel(this);
		myBudget = new MyBudgetPanel(this);
		myReports = new MyReportsPanel(this);
		
		tabs = new JTabbedPane();
	}
	
	public void init() {
		tabs.addTab(TextFormatter.getTranslation(BuddiKeys.MY_ACCOUNTS), myAccounts);
		tabs.addTab(TextFormatter.getTranslation(BuddiKeys.MY_BUDGET), myBudget);
		tabs.addTab(TextFormatter.getTranslation(BuddiKeys.MY_REPORTS), myReports);

		MainFrame.this.setJMenuBar(new MainFrameMenuBar(MainFrame.this));
		
		ComponentAdapter tabSelectListener = new ComponentAdapter(){
			@Override
			public void componentShown(ComponentEvent e) {
				super.componentShown(e);
				
				updateContent();
			}
		};
		
		myAccounts.addComponentListener(tabSelectListener);
		myBudget.addComponentListener(tabSelectListener);
		myReports.addComponentListener(tabSelectListener);
		
		this.add(tabs, BorderLayout.CENTER);
	}
	
	@Override
	public void updateContent() {
		String dataFile = getDocument().getFile() == null ? "" : " - " + getDocument().getFile().getAbsolutePath();
		this.setTitle(TextFormatter.getTranslation(BuddiKeys.BUDDI) + dataFile);

		super.updateContent();
	}
	
	@Override
	public boolean canClose() {
		if (getDocument().isChanged()){
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
	
	@Override
	public void closeWindowWithoutPrompting() {
		PrefsModel.getInstance().setMainWindowSize(this.getSize());
		PrefsModel.getInstance().setMainWindowLocation(this.getLocation());
		PrefsModel.getInstance().save();
		
		super.closeWindowWithoutPrompting();
	}
	
	public List<BudgetCategory> getSelectedBudgetCategories(){
		if (isMyBudgetTabSelected())
			return myBudget.getSelectedBudgetCategories();
			
		return new LinkedList<BudgetCategory>();
	}
	
	public List<Account> getSelectedAccounts(){
		if (isMyAccountsTabSelected())
			return myAccounts.getSelectedAccounts();
		
		return new LinkedList<Account>();
	}
	
	public boolean isMyAccountsTabSelected(){
		return tabs.getSelectedIndex() == 0;
	}
	public boolean isMyBudgetTabSelected(){
		return tabs.getSelectedIndex() == 1;
	}
	public boolean isMyReportsTabSelected(){
		return tabs.getSelectedIndex() == 2;
	}
}
