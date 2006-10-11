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

import org.homeunix.drummer.Const;
import org.homeunix.drummer.controller.MainBuddiFrame;
import org.homeunix.drummer.controller.TransactionsFrame;
import org.homeunix.drummer.prefs.PrefsInstance;
import org.homeunix.drummer.view.components.BuddiMenu;
import org.homeunix.thecave.moss.util.Log;

public abstract class AbstractFrame extends JFrame{	
	protected AbstractFrame(){
		
		this.setJMenuBar(new BuddiMenu(this));
	}
	
	/**
	 * The method to init the action listeners for components in this window
	 * @return
	 */
	protected abstract AbstractFrame initActions();
	
	/**
	 * The method to init the content for the frame
	 * @return
	 */
	protected abstract AbstractFrame initContent();
	
	/**
	 * The method to update components on screen when content changes.
	 * @return
	 */
	public abstract AbstractFrame updateContent();
	
	/**
	 * The method to update buttons enabled / disabled state based on window state
	 * @return
	 */
	public abstract AbstractFrame updateButtons();

	
	/**
	 * Override this to give your window the ability to reset
	 * @return
	 */
	public AbstractFrame clearContent(){
		
		return this;
	}

	
	public AbstractFrame load(){
		updateContent();
		return this;
	}
	
	/**
	 * Opens the window and positions onscreen, depending on
	 * the preferences for that window type.
	 * @return
	 */
	public AbstractFrame openWindow(){
		Point p;
		Dimension d;
		
		PrefsInstance.getInstance().checkWindowSanity();
		
		if (this instanceof MainBuddiFrame){
			p = new Point(
					PrefsInstance.getInstance().getPrefs().getWindows().getMainWindow().getX(),
					PrefsInstance.getInstance().getPrefs().getWindows().getMainWindow().getY()
			);
			d = new Dimension(
					Math.max(PrefsInstance.getInstance().getPrefs().getWindows().getMainWindow().getWidth(), 500),
					Math.max(PrefsInstance.getInstance().getPrefs().getWindows().getMainWindow().getHeight(), 450)					
			);
		}
		else if (this instanceof TransactionsFrame){
			p = new Point(
					PrefsInstance.getInstance().getPrefs().getWindows().getTransactionsWindow().getX(),
					PrefsInstance.getInstance().getPrefs().getWindows().getTransactionsWindow().getY()
			); 
			d = new Dimension(
					Math.max(PrefsInstance.getInstance().getPrefs().getWindows().getTransactionsWindow().getWidth(), 400),
					Math.max(PrefsInstance.getInstance().getPrefs().getWindows().getTransactionsWindow().getHeight(), 300)	
			);	
		}
		else if (this instanceof GraphFrameLayout){
			p = new Point(
					PrefsInstance.getInstance().getPrefs().getWindows().getGraphsWindow().getX(),
					PrefsInstance.getInstance().getPrefs().getWindows().getGraphsWindow().getY()
			);
			d = null;
//			d = new Dimension(
//					PrefsInstance.getInstance().getPrefs().getGraphsWindow().getWidth(),
//					PrefsInstance.getInstance().getPrefs().getGraphsWindow().getHeight()					
//			);	
		}
		else if (this instanceof ReportFrameLayout){
			p = new Point(
					PrefsInstance.getInstance().getPrefs().getWindows().getReportsWindow().getX(),
					PrefsInstance.getInstance().getPrefs().getWindows().getReportsWindow().getY()
			);
//			d = null;
			d = new Dimension(
					Math.max(PrefsInstance.getInstance().getPrefs().getWindows().getReportsWindow().getWidth(), 300),
					Math.max(PrefsInstance.getInstance().getPrefs().getWindows().getReportsWindow().getHeight(), 400)					
			);
		}
		else{
			if (Const.DEVEL) Log.debug("Not of a known type");
			p = new Point(0, 0);
			d = null;
		}
				
		this.setLocation(p);
		if (d != null)
			this.setPreferredSize(d);
		
		ClassLoader cl = Thread.currentThread().getContextClassLoader();
		if (null == cl) {
			cl = AbstractFrame.class.getClassLoader();
		}
		
		URL imageResource = cl.getResource("Buddi.gif");
		if (null != imageResource) {
			setIconImage(Toolkit.getDefaultToolkit().getImage(imageResource));
		}
		
		this.pack();
		
		this.setVisible(true);
		return this;
	}
	
	/**
	 * Grabs the component to be printed on the window.  This 
	 * is then passed to PrintUtilities.
	 * @return
	 */
	public abstract Component getPrintedComponent();
}