/*
 * Created on Aug 7, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.view.menu.bars;

import org.homeunix.thecave.buddi.view.MainFrame;
import org.homeunix.thecave.buddi.view.menu.menus.FileMenu;
import org.homeunix.thecave.buddi.view.menu.menus.HelpMenu;
import org.homeunix.thecave.buddi.view.menu.menus.MyAccountsEditMenu;
import org.homeunix.thecave.buddi.view.menu.menus.MyAccountsViewMenu;
import org.homeunix.thecave.buddi.view.menu.menus.MyBudgetEditMenu;
import org.homeunix.thecave.buddi.view.menu.menus.MyBudgetViewMenu;
import org.homeunix.thecave.buddi.view.menu.menus.MyReportsEditMenu;
import org.homeunix.thecave.buddi.view.menu.menus.MyReportsViewMenu;
import org.homeunix.thecave.buddi.view.menu.menus.WindowMenu;
import org.homeunix.thecave.moss.swing.MossMenuBar;

public class MainFrameMenuBar extends MossMenuBar {
	public static final long serialVersionUID = 0;

	private final MainFrame frame;
	
	private final MyAccountsEditMenu accountEdit;
	private final MyBudgetEditMenu budgetEdit;
	private final MyReportsEditMenu reportEdit;
	
	private final MyAccountsViewMenu accountView;
	private final MyBudgetViewMenu budgetView;
	private final MyReportsViewMenu reportView;
	

	public MainFrameMenuBar(MainFrame frame) {
		super(frame);

		this.frame = frame;
		
		accountEdit = new MyAccountsEditMenu(frame);
		budgetEdit = new MyBudgetEditMenu(frame);
		reportEdit = new MyReportsEditMenu(frame);
		
		accountView = new MyAccountsViewMenu(frame);
		budgetView = new MyBudgetViewMenu(frame);
		reportView = new MyReportsViewMenu(frame);
		
		this.add(new FileMenu(frame));
		this.add(accountEdit);
		this.add(budgetEdit);
		this.add(reportEdit);

		this.add(accountView);
		this.add(budgetView);
		this.add(reportView);
		
		this.add(new WindowMenu(frame));
		this.add(new HelpMenu(frame));
	}

	@Override
	public void updateMenus() {
		super.updateMenus();

		accountEdit.setVisible(frame.isMyAccountsTabSelected());
		budgetEdit.setVisible(frame.isMyBudgetTabSelected());
		reportEdit.setVisible(frame.isMyReportsTabSelected());
		
		accountView.setVisible(frame.isMyAccountsTabSelected());
		budgetView.setVisible(frame.isMyBudgetTabSelected());
		reportView.setVisible(frame.isMyReportsTabSelected());

	}
}
