/*
 * Created on May 16, 2006 by wyatt
 */
package org.homeunix.drummer.view.logic;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JFrame;

import net.roydesign.app.Application;

import org.homeunix.drummer.Buddi;
import org.homeunix.drummer.controller.DataInstance;
import org.homeunix.drummer.controller.PrefsInstance;
import org.homeunix.drummer.util.Log;
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
	
		DataInstance.getInstance().calculateAllBalances();
		
		initActions();
	}

	@Override
	protected AbstractBudgetFrame initActions() {
		this.addComponentListener(new ComponentAdapter(){
			@Override
			public void componentResized(ComponentEvent arg0) {
				Log.debug("Main window resized");
				
				MainBudgetFrame.this.savePosition();
								
				super.componentResized(arg0);
			}

			@Override
			public void componentHidden(ComponentEvent arg0) {
				Log.debug("Main Window hidden");
				
				MainBudgetFrame.this.savePosition();
				
				super.componentHidden(arg0);
			}
		});
		
		if (Buddi.isMac()){
			this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
			Application.getInstance().addReopenApplicationListener(new ActionListener(){
				public void actionPerformed(ActionEvent arg0) {
					if (!MainBudgetFrame.this.isVisible())
						MainBudgetFrame.this.setVisible(true);
				}
			});
		}
		else{
			this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
		}
		
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
	
	public void savePosition(){
		PrefsInstance.getInstance().checkSanity();
		
		PrefsInstance.getInstance().getPrefs().getMainWindow().setHeight(this.getHeight());
		PrefsInstance.getInstance().getPrefs().getMainWindow().setWidth(this.getWidth());
		
		PrefsInstance.getInstance().getPrefs().getMainWindow().setX(this.getX());
		PrefsInstance.getInstance().getPrefs().getMainWindow().setY(this.getY());
		
		PrefsInstance.getInstance().savePrefs();
	}

}
