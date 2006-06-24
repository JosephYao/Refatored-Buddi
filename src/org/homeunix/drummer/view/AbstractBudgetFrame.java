/*
 * Created on May 6, 2006 by wyatt
 */
package org.homeunix.drummer.view;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;

import javax.swing.JFrame;

import org.homeunix.drummer.controller.PrefsInstance;
import org.homeunix.drummer.util.Log;
import org.homeunix.drummer.view.components.BudgetMenu;
import org.homeunix.drummer.view.logic.MainBudgetFrame;
import org.homeunix.drummer.view.logic.TransactionsFrame;
import org.homeunix.drummer.view.reports.layout.GraphFrameLayout;
import org.homeunix.drummer.view.reports.layout.ReportFrameLayout;

public abstract class AbstractBudgetFrame extends JFrame{	
	protected AbstractBudgetFrame(){
		
		this.setJMenuBar(new BudgetMenu(this));
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
		
		if (this instanceof MainBudgetFrame){
			p = new Point(
					PrefsInstance.getInstance().getPrefs().getMainWindow().getX(),
					PrefsInstance.getInstance().getPrefs().getMainWindow().getY()
			);
			d = new Dimension(
					PrefsInstance.getInstance().getPrefs().getMainWindow().getWidth(),
					PrefsInstance.getInstance().getPrefs().getMainWindow().getHeight()					
			);
		}
		else if (this instanceof TransactionsFrame){
			p = new Point(
					PrefsInstance.getInstance().getPrefs().getTransactionsWindow().getX(),
					PrefsInstance.getInstance().getPrefs().getTransactionsWindow().getY()
			); 
			d = new Dimension(
					PrefsInstance.getInstance().getPrefs().getTransactionsWindow().getWidth(),
					PrefsInstance.getInstance().getPrefs().getTransactionsWindow().getHeight()					
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
			d = null;
			d = new Dimension(
					PrefsInstance.getInstance().getPrefs().getReportsWindow().getWidth(),
					PrefsInstance.getInstance().getPrefs().getReportsWindow().getHeight()					
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
		
		load();
		this.pack();
		
		this.setVisible(true);
		return this;
	}
	
	public abstract Component getPrintedComponent();
}