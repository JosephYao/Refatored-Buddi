/*
 * Created on May 16, 2006 by wyatt
 */
package org.homeunix.drummer.view.logic;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import net.roydesign.app.Application;

import org.homeunix.drummer.Buddi;
import org.homeunix.drummer.Const;
import org.homeunix.drummer.Translate;
import org.homeunix.drummer.TranslateKeys;
import org.homeunix.drummer.controller.DataInstance;
import org.homeunix.drummer.controller.PrefsInstance;
import org.homeunix.drummer.util.BrowserLauncher;
import org.homeunix.drummer.util.Log;
import org.homeunix.drummer.util.SwingWorker;
import org.homeunix.drummer.view.AbstractBudgetFrame;
import org.homeunix.drummer.view.layout.ListPanelLayout;
import org.homeunix.drummer.view.layout.MainBudgetFrameLayout;


public class MainBuddiFrame extends MainBudgetFrameLayout {
	public static final long serialVersionUID = 0;
	
	public static MainBuddiFrame getInstance() {
		return SingletonHolder.instance;
	}
	
	private static class SingletonHolder {
		private static MainBuddiFrame instance = new MainBuddiFrame();
	}
	
	private MainBuddiFrame(){
		super();

		DataInstance.getInstance().calculateAllBalances();
		initActions();
		
		startUpdateCheck();
		startVersionCheck();
	}

	@Override
	protected AbstractBudgetFrame initActions() {
		this.addComponentListener(new ComponentAdapter(){
			@Override
			public void componentResized(ComponentEvent arg0) {
				Log.debug("Main window resized");
				
				MainBuddiFrame.this.savePosition();
								
				super.componentResized(arg0);
			}

			@Override
			public void componentHidden(ComponentEvent arg0) {
				Log.debug("Main Window hidden");
				
				MainBuddiFrame.this.savePosition();
				
				super.componentHidden(arg0);
			}
		});
		
		if (Buddi.isMac()){
			this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
			Application.getInstance().addReopenApplicationListener(new ActionListener(){
				public void actionPerformed(ActionEvent arg0) {
					if (!MainBuddiFrame.this.isVisible())
						MainBuddiFrame.this.setVisible(true);
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
		PrefsInstance.getInstance().checkWindowSanity();
		
		PrefsInstance.getInstance().getPrefs().getMainWindow().setHeight(this.getHeight());
		PrefsInstance.getInstance().getPrefs().getMainWindow().setWidth(this.getWidth());
		
		PrefsInstance.getInstance().getPrefs().getMainWindow().setX(this.getX());
		PrefsInstance.getInstance().getPrefs().getMainWindow().setY(this.getY());
		
		PrefsInstance.getInstance().savePrefs();
	}

	public void startUpdateCheck(){
		if (PrefsInstance.getInstance().getPrefs().isEnableUpdateNotifications()){
			SwingWorker updateWorker = new SwingWorker(){

				@Override
				public Object construct() {
					try{
						Properties versions = new Properties();
						URL mostRecentVersion = new URL(Const.PROJECT_URL + Const.VERSION_FILE);
						
						versions.load(mostRecentVersion.openStream());
						
						if (Const.VERSION.compareTo(versions.get(Const.BRANCH).toString()) < 0){
							return versions.get(Const.BRANCH);
						}
					}
					catch (MalformedURLException mue){
						Log.error(mue);
					}
					catch (IOException ioe){
						Log.error(ioe);
					}
					
					return null;
				}

				@Override
				public void finished() {
					if (get() != null){
						String[] buttons = new String[2];
						buttons[0] = Translate.getInstance().get(TranslateKeys.DOWNLOAD);
						buttons[1] = Translate.getInstance().get(TranslateKeys.CANCEL);
						
						int reply = JOptionPane.showOptionDialog(
								MainBuddiFrame.this, 
								Translate.getInstance().get(TranslateKeys.NEW_VERSION_MESSAGE)
								+ " " + get() + "\n"
								+ Translate.getInstance().get(TranslateKeys.NEW_VERSION_MESSAGE_2),
								Translate.getInstance().get(TranslateKeys.NEW_VERSION),
								JOptionPane.YES_NO_OPTION,
								JOptionPane.INFORMATION_MESSAGE,
								null,
								buttons,
								buttons[0]);
						
						if (reply == JOptionPane.YES_OPTION){
							String fileLocation = Const.DOWNLOAD_URL + get();
							
							if (Buddi.isMac())
								fileLocation += Const.DOWNLOAD_URL_DMG;
							else
								fileLocation += Const.DOWNLOAD_URL_ZIP;
							
							try{
								BrowserLauncher.openURL(fileLocation);
							}
							catch (IOException ioe){
								Log.error(ioe);
							}
						}
					}
					
					super.finished();
				}
			};
			
			Log.debug("Starting update checking...");
			updateWorker.start();
		}
	}
	
	private void startVersionCheck(){
		if (!PrefsInstance.getInstance().getLastVersionRun().equals(Const.VERSION)){
			PrefsInstance.getInstance().updateVersion();
			
			SwingWorker worker = new SwingWorker() {
			
				@Override
				public Object construct() {
					return null;
				}
				
				@Override
				public void finished() {
					String[] buttons = new String[2];
					buttons[0] = Translate.getInstance().get(TranslateKeys.DONATE);
					buttons[1] = Translate.getInstance().get(TranslateKeys.NOT_NOW);
					
					int reply = JOptionPane.showOptionDialog(
							MainBuddiFrame.this, 
							Translate.getInstance().get(TranslateKeys.DONATE_MESSAGE),
							Translate.getInstance().get(TranslateKeys.DONATE_HEADER),
							JOptionPane.YES_NO_OPTION,
							JOptionPane.INFORMATION_MESSAGE,
							null,
							buttons,
							buttons[0]);
					
					if (reply == JOptionPane.YES_OPTION){
						try{
							BrowserLauncher.openURL(Const.DONATE_URL);
						}
						catch (IOException ioe){
							Log.error(ioe);
						}
					}
					
					super.finished();
				}
			};
			
			worker.start();
		}
	}
}
