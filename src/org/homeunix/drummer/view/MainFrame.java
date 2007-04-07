/*
 * Created on May 16, 2006 by wyatt
 */
package org.homeunix.drummer.view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Point;
import java.awt.event.ActionEvent;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

import net.roydesign.app.Application;

import org.homeunix.drummer.Const;
import org.homeunix.drummer.controller.Translate;
import org.homeunix.drummer.controller.TranslateKeys;
import org.homeunix.drummer.prefs.PrefsInstance;
import org.homeunix.drummer.prefs.WindowAttributes;
import org.homeunix.thecave.moss.gui.abstractwindows.AbstractFrame;
import org.homeunix.thecave.moss.gui.abstractwindows.StandardContainer;
import org.homeunix.thecave.moss.util.Log;
import org.homeunix.thecave.moss.util.OperatingSystemUtil;


public class MainFrame extends AbstractBuddiFrame {
	public static final long serialVersionUID = 0;
	
	private final AccountListPanel accountListPanel;
	private final CategoryListPanel categoryListPanel;
	private final ReportPanel reportPanel;
	private final GraphPanel graphPanel;
	private final JTabbedPane tabs;
		
	protected MainFrame(){
		JPanel mainPanel = new JPanel(new BorderLayout());
		
		tabs = new JTabbedPane();
		
		accountListPanel = new AccountListPanel();
		categoryListPanel = new CategoryListPanel();
		reportPanel = new ReportPanel();
		graphPanel = new GraphPanel();
		
		tabs.addTab(Translate.getInstance().get(TranslateKeys.MY_ACCOUNTS), accountListPanel);
		tabs.addTab(Translate.getInstance().get(TranslateKeys.MY_BUDGET), categoryListPanel);
		tabs.addTab(Translate.getInstance().get(TranslateKeys.REPORTS), reportPanel);
		tabs.addTab(Translate.getInstance().get(TranslateKeys.GRAPHS), graphPanel);
		
		mainPanel.add(tabs, BorderLayout.CENTER);
		
		JScrollPane scroller = new JScrollPane(mainPanel);
		scroller.setBorder(BorderFactory.createEmptyBorder());
		
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
		else if (tabs.getSelectedComponent().equals(graphPanel))
			return graphPanel;
		else{
			if (Const.DEVEL) Log.debug("Unknown Tab");
			return null;
		}
	}
	
	
	
	/**
	 * Get an instance of the main Buddi window.
	 * @return MainFrame - Main Buddi window instance
	 */
	public static MainFrame getInstance() {
		return SingletonHolder.instance;
	}

	private static class SingletonHolder {
		private static MainFrame instance = new MainFrame();

		public static void restartProgram(){
			instance = new MainFrame();
		}
	}

	/**
	 * Forces a restart of the program.
	 */
	public static void restartProgram(){
		for(Frame frame : TransactionsFrame.getFrames()){
			frame.dispose();
		}

		getInstance().dispose();

		SingletonHolder.instance = null;
		SingletonHolder.restartProgram();

		WindowAttributes wa = PrefsInstance.getInstance().getPrefs().getWindows().getMainWindow();
		Dimension dim = new Dimension(wa.getWidth(), wa.getHeight());
		Point point = new Point(wa.getX(), wa.getY());

		MainFrame.getInstance().openWindow(dim, point);
	}

	@Override
	public Object closeWindow() {
		savePosition();

		//We have a special handler on the Mac to do hide / unhide from
		// the dock, so we don't want the default closeWindow to kick in.
		if (OperatingSystemUtil.isMac()){
			this.setVisible(false);
			return null;
		}
		else {
			return super.closeWindow();
		}
	}

	public AbstractFrame init() {
		// The correct Mac behaviour is to keep the program running
		// on a Window close; you must click Quit before the program 
		// stops.  We do that here.
		if (OperatingSystemUtil.isMac()){
			getInstance().setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
			Application.getInstance().addReopenApplicationListener(this);
		}
		else{
			getInstance().setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		}

		return this;
	}

	public AbstractFrame updateButtons() {
		return this;
	}

	public AbstractFrame updateContent() {
		//Update the title to reflect current data file...
		this.setTitle(Translate.getInstance().get(TranslateKeys.BUDDI) + " - " + PrefsInstance.getInstance().getPrefs().getDataFile());

		getAccountListPanel().updateContent();
		getCategoryListPanel().updateContent();

		return getInstance();
	}

	public Component getPrintedComponent() {
		if (getSelectedPanel() instanceof AbstractListPanel){
			AbstractListPanel listPanel = (AbstractListPanel) getSelectedPanel();
			return listPanel.getTree();
		}
		else
			return null;
	}

	public void savePosition(){
		PrefsInstance.getInstance().checkWindowSanity();

		PrefsInstance.getInstance().getPrefs().getWindows().getMainWindow().setHeight(getInstance().getHeight());
		PrefsInstance.getInstance().getPrefs().getWindows().getMainWindow().setWidth(getInstance().getWidth());

		PrefsInstance.getInstance().getPrefs().getWindows().getMainWindow().setX(getInstance().getX());
		PrefsInstance.getInstance().getPrefs().getWindows().getMainWindow().setY(getInstance().getY());

		PrefsInstance.getInstance().savePrefs();
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(Application.getInstance())){
			if (!MainFrame.getInstance().isVisible())
				MainFrame.getInstance().setVisible(true);
		}
	}

	public StandardContainer clear() {
		return this;
	}
}
