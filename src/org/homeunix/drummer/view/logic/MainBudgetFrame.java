/*
 * Created on May 16, 2006 by wyatt
 */
package org.homeunix.drummer.view.logic;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

import net.roydesign.app.Application;

import org.homeunix.drummer.Buddi;
import org.homeunix.drummer.controller.DataInstance;
import org.homeunix.drummer.view.AbstractBudgetFrame;
import org.homeunix.drummer.view.layout.ListPanelLayout;
import org.homeunix.drummer.view.layout.MainBudgetFrameLayout;


public class MainBudgetFrame extends MainBudgetFrameLayout {
	public static final long serialVersionUID = 0;
	
	public static MainBudgetFrame getInstance() {
		return SingletonHolder.instance;
	}
	
	private static class SingletonHolder {
		private static MainBudgetFrame instance = new MainBudgetFrame();
	}
	
	private MainBudgetFrame(){
		super();
		
		if (Buddi.isMac()){
			this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
			Application.getInstance().addReopenApplicationListener(new ActionListener(){
				public void actionPerformed(ActionEvent arg0) {
					MainBudgetFrame.this.setVisible(true);
				}
			});
		}
		else{
			this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
		}
	
		DataInstance.getInstance().calculateAllBalances();
	}

	@Override
	protected AbstractBudgetFrame initActions() {
		return this;
	}

	@Override
	protected AbstractBudgetFrame initContent() {
		return this;
	}

	@Override
	public AbstractBudgetFrame updateButtons() {
		return this;
	}

	@Override
	public AbstractBudgetFrame updateContent() {
		getAccountListPanel().updateContent();
		getCategoryListPanel().updateContent();
		
		return this;
	}

	@Override
	public Component getPrintedComponent() {
		if (getSelectedPanel() instanceof ListPanelLayout){
			ListPanelLayout listPanel = (ListPanelLayout) getSelectedPanel();
			return listPanel.getTree();
		}
		else
			return null;
	}

}
