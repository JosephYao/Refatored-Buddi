/*
 * Created on May 16, 2006 by wyatt
 */
package org.homeunix.drummer.view;

import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

import org.homeunix.drummer.Buddi;
import org.homeunix.drummer.Const;
import org.homeunix.drummer.controller.AccountListPanel;
import org.homeunix.drummer.controller.CategoryListPanel;
import org.homeunix.drummer.controller.ReportPanel;
import org.homeunix.drummer.controller.Translate;
import org.homeunix.drummer.controller.TranslateKeys;
import org.homeunix.thecave.moss.util.Log;


public abstract class MainBuddiFrameLayout extends AbstractFrame {
	public static final long serialVersionUID = 0;
	private final AccountListPanel accountListPanel;
	private final CategoryListPanel categoryListPanel;
	private final ReportPanel reportPanel;
	private final JTabbedPane tabs;
		
	protected MainBuddiFrameLayout(){
		JPanel mainPanel = new JPanel(new BorderLayout());
		
		tabs = new JTabbedPane();
		
		accountListPanel = new AccountListPanel();
		categoryListPanel = new CategoryListPanel();
		reportPanel = new ReportPanel();
		
		tabs.addTab(Translate.getInstance().get(TranslateKeys.MY_ACCOUNTS), accountListPanel);
		tabs.addTab(Translate.getInstance().get(TranslateKeys.MY_BUDGET), categoryListPanel);
		tabs.addTab(Translate.getInstance().get(TranslateKeys.REPORTS), reportPanel);
		
		mainPanel.add(tabs, BorderLayout.CENTER);
		
		JScrollPane scroller = new JScrollPane(mainPanel);
		scroller.setBorder(BorderFactory.createEmptyBorder());
		
		if (Buddi.isMac()){
			mainPanel.setBorder(BorderFactory.createEmptyBorder(7, 17, 17, 17));
		}
		
//		this.setTitle(Translate.getInstance().get(TranslateKeys.BUDDI) + PrefsInstance.getInstance().getPrefs().getDataFile());
		this.setLayout(new BorderLayout());
		this.add(scroller, BorderLayout.CENTER);		
	}
	
	public AccountListPanel getAccountListPanel(){
		return accountListPanel;
	}

	public CategoryListPanel getCategoryListPanel(){
		return categoryListPanel;
	}
	
	protected JPanel getSelectedPanel(){
		if (tabs.getSelectedComponent().equals(accountListPanel))
			return accountListPanel;
		else if (tabs.getSelectedComponent().equals(categoryListPanel))
			return categoryListPanel;
		else if (tabs.getSelectedComponent().equals(reportPanel))
			return reportPanel;
		else{
			if (Const.DEVEL) Log.debug("Unknown Tab");
			return null;
		}
	}	
}
