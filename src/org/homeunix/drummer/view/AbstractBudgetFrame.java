/*
 * Created on May 6, 2006 by wyatt
 */
package org.homeunix.drummer.view;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.net.URL;

import javax.swing.JFrame;

import org.homeunix.drummer.controller.MainBuddiFrame;
import org.homeunix.drummer.controller.TransactionsFrame;
import org.homeunix.drummer.prefs.PrefsInstance;
import org.homeunix.drummer.util.Log;
import org.homeunix.drummer.view.components.BuddiMenu;

public abstract class AbstractBudgetFrame extends JFrame{	
	protected AbstractBudgetFrame(){
		
		this.setJMenuBar(new BuddiMenu(this));
	}
	
	protected abstract AbstractBudgetFrame initActions();
	
	protected abstract AbstractBudgetFrame initContent();
	
	public abstract AbstractBudgetFrame updateContent();
	
	public abstract AbstractBudgetFrame updateButtons();

	
	/**
	 * Override this to give your window the ability to reset
	 * @return
	 */
	public AbstractBudgetFrame clearContent(){
		
		return this;
	}

	
	public AbstractBudgetFrame load(){
		updateContent();
		return this;
	}
	
	public AbstractBudgetFrame openWindow(){
		Point p;
		Dimension d;
		
		PrefsInstance.getInstance().checkWindowSanity();
		
		if (this instanceof MainBuddiFrame){
			p = new Point(
					PrefsInstance.getInstance().getPrefs().getMainWindow().getX(),
					PrefsInstance.getInstance().getPrefs().getMainWindow().getY()
			);
			d = new Dimension(
					Math.max(PrefsInstance.getInstance().getPrefs().getMainWindow().getWidth(), 400),
					Math.max(PrefsInstance.getInstance().getPrefs().getMainWindow().getHeight(), 300)					
			);
		}
		else if (this instanceof TransactionsFrame){
			p = new Point(
					PrefsInstance.getInstance().getPrefs().getTransactionsWindow().getX(),
					PrefsInstance.getInstance().getPrefs().getTransactionsWindow().getY()
			); 
			d = new Dimension(
					Math.max(PrefsInstance.getInstance().getPrefs().getTransactionsWindow().getWidth(), 400),
					Math.max(PrefsInstance.getInstance().getPrefs().getTransactionsWindow().getHeight(), 300)	
			);	
		}
		else if (this instanceof GraphFrameLayout){
			p = new Point(
					PrefsInstance.getInstance().getPrefs().getGraphsWindow().getX(),
					PrefsInstance.getInstance().getPrefs().getGraphsWindow().getY()
			);
			d = null;
//			d = new Dimension(
//					PrefsInstance.getInstance().getPrefs().getGraphsWindow().getWidth(),
//					PrefsInstance.getInstance().getPrefs().getGraphsWindow().getHeight()					
//			);	
		}
		else if (this instanceof ReportFrameLayout){
			p = new Point(
					PrefsInstance.getInstance().getPrefs().getReportsWindow().getX(),
					PrefsInstance.getInstance().getPrefs().getReportsWindow().getY()
			);
//			d = null;
			d = new Dimension(
					Math.max(PrefsInstance.getInstance().getPrefs().getReportsWindow().getWidth(), 300),
					Math.max(PrefsInstance.getInstance().getPrefs().getReportsWindow().getHeight(), 400)					
			);
		}
		else{
			Log.debug("Not of a known type");
			p = new Point(0, 0);
			d = null;
		}
				
		this.setLocation(p);
		if (d != null)
			this.setPreferredSize(d);
		
		ClassLoader cl = Thread.currentThread().getContextClassLoader();
		if (null == cl) {
			cl = AbstractBudgetFrame.class.getClassLoader();
		}
		
		URL imageResource = cl.getResource("Buddi.gif");
		if (null != imageResource) {
			setIconImage(Toolkit.getDefaultToolkit().getImage(imageResource));
		}
		
		load();
		this.pack();
		
		this.setVisible(true);
		return this;
	}
	
	public abstract Component getPrintedComponent();
}