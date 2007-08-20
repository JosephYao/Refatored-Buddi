/*
 * Created on Aug 7, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.view.menu.bars;

import org.homeunix.thecave.buddi.view.MainFrame;
import org.homeunix.thecave.buddi.view.menu.menus.AccountFrameEditMenu;
import org.homeunix.thecave.buddi.view.menu.menus.BudgetFrameEditMenu;
import org.homeunix.thecave.buddi.view.menu.menus.FileMenu;
import org.homeunix.thecave.buddi.view.menu.menus.HelpMenu;
import org.homeunix.thecave.buddi.view.menu.menus.ReportFrameEditMenu;
import org.homeunix.thecave.buddi.view.menu.menus.WindowMenu;
import org.homeunix.thecave.moss.swing.menu.MossMenuBar;

public class MainFrameMenuBar extends MossMenuBar {
	public static final long serialVersionUID = 0;

	private final MainFrame frame;
	
	private final AccountFrameEditMenu accountEdit;
	private final BudgetFrameEditMenu budgetEdit;
	private final ReportFrameEditMenu reportEdit;

	public MainFrameMenuBar(MainFrame frame) {
		super(frame);

		this.frame = frame;
		
		accountEdit = new AccountFrameEditMenu(frame);
		budgetEdit = new BudgetFrameEditMenu(frame);
		reportEdit = new ReportFrameEditMenu(frame);
		
		this.add(new FileMenu(frame));
		this.add(accountEdit);
		this.add(budgetEdit);
		this.add(reportEdit);
		this.add(new WindowMenu(frame));
		this.add(new HelpMenu(frame));
	}

	@Override
	public void updateMenus() {
		super.updateMenus();

		accountEdit.setVisible(frame.isMyAccountsTabSelected());
		budgetEdit.setVisible(frame.isMyBudgetTabSelected());
		reportEdit.setVisible(frame.isMyReportsTabSelected());
	}
}
