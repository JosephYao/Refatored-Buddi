/*
 * Created on May 16, 2006 by wyatt
 */
package org.homeunix.drummer.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

import net.roydesign.app.Application;
import net.sourceforge.buddi.api.manager.APICommonHTMLHelper;
import net.sourceforge.buddi.api.manager.DataManager;
import net.sourceforge.buddi.api.manager.ImportManager;
import net.sourceforge.buddi.api.manager.APICommonHTMLHelper.HTMLWrapper;
import net.sourceforge.buddi.api.plugin.BuddiRunnablePlugin;
import net.sourceforge.buddi.impl_2_4.manager.ImportManagerImpl;

import org.homeunix.drummer.Const;
import org.homeunix.drummer.controller.DocumentController;
import org.homeunix.drummer.controller.SourceController;
import org.homeunix.drummer.controller.Translate;
import org.homeunix.drummer.controller.TranslateKeys;
import org.homeunix.drummer.model.Account;
import org.homeunix.drummer.model.Category;
import org.homeunix.drummer.model.DataInstance;
import org.homeunix.drummer.plugins.LoadedPlugins;
import org.homeunix.drummer.plugins.PluginFactory;
import org.homeunix.drummer.prefs.PrefsInstance;
import org.homeunix.drummer.prefs.WindowAttributes;
import org.homeunix.drummer.util.BuddiInternalFormatter;
import org.homeunix.thecave.moss.gui.abstractwindows.AbstractFrame;
import org.homeunix.thecave.moss.gui.abstractwindows.StandardWindow;
import org.homeunix.thecave.moss.util.Log;
import org.homeunix.thecave.moss.util.OperatingSystemUtil;


public class MainFrame extends AbstractBuddiFrame implements HTMLExport {
	public static final long serialVersionUID = 0;
	
	private final AccountListPanel accountListPanel;
	private final CategoryListPanel categoryListPanel;
	private final ReportPanel reportPanel;
	private final GraphPanel graphPanel;
	private final JTabbedPane tabs;
	private final LoadedPlugins loadedPlugins;
		
	private MainFrame(){
		tabs = new JTabbedPane();
		
		accountListPanel = new AccountListPanel();
		categoryListPanel = new CategoryListPanel();
		reportPanel = new ReportPanel();
		graphPanel = new GraphPanel();
		loadedPlugins = new LoadedPlugins();
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
		DocumentController.saveFile();
		
		savePosition();

		Log.debug("Exiting.");
		System.exit(0);
		
		return super.closeWindow();
		//We have a special handler on the Mac to do hide / unhide from
		// the dock, so we don't want the default closeWindow to kick in.
//		if (OperatingSystemUtil.isMac()){
//			this.setVisible(false);
//			return null;
//		}
//		else {
//			return super.closeWindow();
//		}
	}

	public AbstractFrame init() {
		
		//We try calling all runnable plugins as soon as possible
		// here, to allow them to modify whatever they need to
		// at startup.		
		for (BuddiRunnablePlugin runnable : PluginFactory.getRunnablePlugins()) {
			if (Const.DEVEL) Log.debug("Running plugin " + runnable.getDescription());
			runnable.run();
			if (Const.DEVEL) Log.debug("Finished running plugin " + runnable.getDescription());
		}
		
		accountListPanel.open();
		categoryListPanel.open();
		reportPanel.open();
		graphPanel.open();
		
		tabs.addTab(Translate.getInstance().get(TranslateKeys.MY_ACCOUNTS), accountListPanel);
		tabs.addTab(Translate.getInstance().get(TranslateKeys.MY_BUDGET), categoryListPanel);
		tabs.addTab(Translate.getInstance().get(TranslateKeys.REPORTS), reportPanel);
		tabs.addTab(Translate.getInstance().get(TranslateKeys.GRAPHS), graphPanel);
		
		JPanel mainPanel = new JPanel(new BorderLayout());
		mainPanel.add(tabs, BorderLayout.CENTER);
		
		JScrollPane scroller = new JScrollPane(mainPanel);
		scroller.setBorder(BorderFactory.createEmptyBorder());
		
		this.setLayout(new BorderLayout());
		this.add(scroller, BorderLayout.CENTER);
		
		
		
		// The correct Mac behaviour is to keep the program running
		// on a Window close; you must click Quit before the program 
		// stops.  We do that here.
		if (OperatingSystemUtil.isMac()){
//			getInstance().setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
			Application.getInstance().addReopenApplicationListener(this);
		}
		
//		else{
//			getInstance().setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
//		}
		
//		Timer timer = new Timer(true);
//		TimerTask task = new TimerTask(){
//			@Override
//			public void run() {
//				DocumentController.saveFile();
//			}
//		};
//		
//		//Auto save the model every 60 seconds
//		timer.schedule(task, 60 * 1000);
//
//		Application.getInstance().addOpenDocumentListener(new ActionListener(){
//			public void actionPerformed(ActionEvent e) {
//				ApplicationEvent ae = (ApplicationEvent) e;
//				File file = ae.getFile();
//				if (Const.DEVEL) Log.debug("Opening File: " + file);
//				DocumentManager.getInstance().loadDataFile(file);
//			}
//		});

		return this;
	}

	public AbstractFrame updateButtons() {
		return this;
	}

	public AbstractFrame updateContent() {
		//Update the title to reflect current data file...
		this.setTitle(
				Translate.getInstance().get(TranslateKeys.BUDDI) 
				+ (DataInstance.getInstance().getDataModel() != null 
						? " - " + PrefsInstance.getInstance().getPrefs().getDataFile() 
								: ""));

		getAccountListPanel().updateContent();
		getCategoryListPanel().updateContent();

		return this;
	}

//	public Component getPrintedComponent() {
//		if (getSelectedPanel() instanceof AbstractListPanel){
//			AbstractListPanel listPanel = (AbstractListPanel) getSelectedPanel();
//			return listPanel.getTree();
//		}
//		else
//			return null;
//	}

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

	public StandardWindow clear() {
		return this;
	}
	
	public File exportToHTML() throws IOException {
		StringBuilder sb = APICommonHTMLHelper.getHtmlHeader(TranslateKeys.ACCOUNT_AND_CATEGORY_SUMMARY.toString(), null, null, null);
		sb.append("<h3>").append(Translate.getInstance().get(TranslateKeys.ACCOUNT)).append("</h3>\n\n");
		sb.append("<table class='main'>\n");
		
		Vector<Account> accounts = SourceController.getAccounts();
		Collections.sort(accounts, new Comparator<Account>(){
			public int compare(Account o1, Account o2) {
				//First we sort by credit
				if (o1.getAccountType().isCredit() != o1.getAccountType().isCredit()){
					if (!o1.getAccountType().isCredit()){
						return -1;
					}
					else {
						return 1;
					}
				}
				
				//Next we sort by Type Name
				if (!o1.getAccountType().getName().equals(o2.getAccountType().getName())){
					return o1.getAccountType().getName().compareTo(o2.getAccountType().getName());
				}
				
				//Finally, we sort by Account Name
				return o1.getName().compareTo(o2.getName());
			}
		});
		sb.append("<tr>");
		sb.append("<th>").append(Translate.getInstance().get(TranslateKeys.NAME)).append("</th>");
		sb.append("<th>").append(Translate.getInstance().get(TranslateKeys.AMOUNT)).append("</th>");
		sb.append("</tr>\n");
		
		for (Account account : accounts) {
			sb.append("<tr><td" + (BuddiInternalFormatter.isRed(account) ? " class='red'>" : ">"));
			sb.append(Translate.getInstance().get(account.getName()));
			sb.append("</td><td" + (BuddiInternalFormatter.isRed(account, account.getBalance()) ? " class='red'>" : ">"));
			sb.append(BuddiInternalFormatter.getFormattedCurrency(account.getBalance())).append("</td>");
			sb.append("</tr>\n");
		}
		
		sb.append("</table>\n\n");
		
		sb.append("<h3>").append(Translate.getInstance().get(TranslateKeys.CATEGORY)).append("</h3>\n\n");
		sb.append("<table class='main'>\n");
		
		Vector<Category> categories = SourceController.getCategories();
		Collections.sort(categories, new Comparator<Category>(){
			public int compare(Category o1, Category o2) {
				//First we sort by income
				if (o1.isIncome() != o2.isIncome()){
					if (o1.isIncome()){
						return -1;
					}
					else {
						return 1;
					}
				}
								
				//Finally, we sort by Category Name
				return o1.toString().compareTo(o2.toString());
			}
		});
		sb.append("<tr>");
		sb.append("<th>").append(Translate.getInstance().get(TranslateKeys.NAME)).append("</th>");
		sb.append("<th>").append(Translate.getInstance().get(TranslateKeys.BUDGETED_AMOUNT)).append("</th>");
		sb.append("</tr>");
		
		for (Category category : categories) {
			sb.append("<tr><td" + (BuddiInternalFormatter.isRed(category) ? " class='red'>" : ">"));
			sb.append(Translate.getInstance().get(category.toString()));
			sb.append("</td><td" + (BuddiInternalFormatter.isRed(category, category.getBudgetedAmount()) ? " class='red'>" : ">"));
			sb.append(BuddiInternalFormatter.getFormattedCurrency(category.getBudgetedAmount(), category.isIncome())).append("</td>");
			sb.append("</tr>\n");
		}
		
		sb.append("</table>\n\n");
		
		sb.append(APICommonHTMLHelper.getHtmlFooter());
		
		return APICommonHTMLHelper.createHTML("main", new HTMLWrapper(sb.toString(), null));
	}
	
	public LoadedPlugins getLoadedPlugins(){
		return loadedPlugins;
	}

	@Override
	public DataManager getDataManager() {
		return getImportManager();
	}
	
	@Override
	public ImportManager getImportManager() {
		if (tabs.getSelectedComponent().equals(accountListPanel))
			return new ImportManagerImpl(getAccountListPanel().getSelectedAccount(), null, null);
		else if (tabs.getSelectedComponent().equals(categoryListPanel))
			return new ImportManagerImpl(null, getCategoryListPanel().getSelectedCategory(), null);

		return new ImportManagerImpl(null, null, null);
	}
}
