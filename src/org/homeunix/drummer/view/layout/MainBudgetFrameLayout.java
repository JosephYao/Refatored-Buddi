/*
 * Created on May 16, 2006 by wyatt
 */
package org.homeunix.drummer.view.layout;

import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import org.homeunix.drummer.Strings;
import org.homeunix.drummer.util.Log;
import org.homeunix.drummer.view.AbstractBudgetFrame;
import org.homeunix.drummer.view.AbstractBudgetPanel;
import org.homeunix.drummer.view.logic.AccountListPanel;
import org.homeunix.drummer.view.logic.CategoryListPanel;
import org.homeunix.drummer.view.logic.ReportPanel;


public abstract class MainBudgetFrameLayout extends AbstractBudgetFrame {
	public static final long serialVersionUID = 0;
	private final AccountListPanel accountListPanel;
	private final CategoryListPanel categoryListPanel;
	private final ReportPanel reportPanel;
	private final JTabbedPane tabs;
		
	protected MainBudgetFrameLayout(){
		JPanel mainPanel = new JPanel(new BorderLayout());
		mainPanel.setBorder(BorderFactory.createEmptyBorder(7, 17, 17, 17));
		
		tabs = new JTabbedPane();
		accountListPanel = new AccountListPanel();
		categoryListPanel = new CategoryListPanel();
		reportPanel = new ReportPanel();
		tabs.addTab(Strings.inst().get(Strings.MY_ACCOUNTS), accountListPanel);
		tabs.addTab(Strings.inst().get(Strings.MY_BUDGET), categoryListPanel);
		tabs.addTab(Strings.inst().get(Strings.REPORTS), reportPanel);
		
		mainPanel.add(tabs, BorderLayout.CENTER);
		
		this.setTitle(Strings.inst().get(Strings.BUDDI));
		this.setLayout(new BorderLayout());
		this.add(mainPanel, BorderLayout.CENTER);
	}
	
	public AccountListPanel getAccountListPanel(){
		return accountListPanel;
	}

	public CategoryListPanel getCategoryListPanel(){
		return categoryListPanel;
	}
	
	protected AbstractBudgetPanel getSelectedPanel(){
		if (tabs.getSelectedComponent().equals(accountListPanel))
			return accountListPanel;
		else if (tabs.getSelectedComponent().equals(categoryListPanel))
			return categoryListPanel;
		else if (tabs.getSelectedComponent().equals(reportPanel))
			return reportPanel;
		else{
			Log.debug("Unknown Tab");
			return null;
		}
	}
}
