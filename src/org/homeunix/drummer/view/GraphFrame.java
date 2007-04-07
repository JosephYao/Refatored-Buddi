/*
 * Created on May 6, 2006 by wyatt
 */
package org.homeunix.drummer.view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JPanel;

import org.homeunix.drummer.Const;
import org.homeunix.drummer.controller.SourceController;
import org.homeunix.drummer.controller.TransactionController;
import org.homeunix.drummer.controller.Translate;
import org.homeunix.drummer.controller.TranslateKeys;
import org.homeunix.drummer.model.Account;
import org.homeunix.drummer.model.Category;
import org.homeunix.drummer.model.Transaction;
import org.homeunix.drummer.plugins.interfaces.BuddiGraphPlugin;
import org.homeunix.drummer.prefs.PrefsInstance;
import org.homeunix.thecave.moss.gui.abstractwindows.AbstractFrame;
import org.homeunix.thecave.moss.gui.abstractwindows.StandardContainer;
import org.homeunix.thecave.moss.util.Log;

public class GraphFrame extends AbstractBuddiFrame {
	public static final long serialVersionUID = 0;

	private static final Vector<GraphFrame> graphFrameInstances = new Vector<GraphFrame>();

	private JPanel reportPanel;
	private final JButton okButton;
	private final BuddiGraphPlugin graphPlugin;
	private final Date startDate, endDate;

	private final JPanel reportPanelSpacer;

	public GraphFrame(BuddiGraphPlugin graphPlugin, final Date startDate, final Date endDate){
		this.graphPlugin = graphPlugin;
		this.startDate = startDate;
		this.endDate = endDate;		

		okButton = new JButton(Translate.getInstance().get(TranslateKeys.OK));

		Dimension buttonSize = new Dimension(100, okButton.getPreferredSize().height);
		okButton.setPreferredSize(buttonSize);

		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		buttonPanel.add(okButton);

		reportPanelSpacer = new JPanel(new BorderLayout());

		JPanel mainPanel = new JPanel(); 
		mainPanel.setLayout(new BorderLayout());
		mainPanel.add(buttonPanel, BorderLayout.SOUTH);
		mainPanel.add(reportPanelSpacer, BorderLayout.CENTER);

		this.setLayout(new BorderLayout());
		this.add(mainPanel, BorderLayout.CENTER);

		graphFrameInstances.add(this);
	}

	protected Vector<Account> getAccounts(){
		return SourceController.getAccounts();
	}

	public AbstractFrame init() {
		okButton.addActionListener(this);

		return this;
	}

	/* (non-Javadoc)
	 * @see org.homeunix.thecave.moss.gui.abstractwindows.AbstractFrame#closeWindow()
	 */
	@Override
	public Object closeWindow() {
		saveWindowPosition();
		
		return super.closeWindow();
	}
	
	private void saveWindowPosition(){
		PrefsInstance.getInstance().checkWindowSanity();

		PrefsInstance.getInstance().getPrefs().getWindows().getGraphsWindow().setX(this.getX());
		PrefsInstance.getInstance().getPrefs().getWindows().getGraphsWindow().setY(this.getY());
		PrefsInstance.getInstance().getPrefs().getWindows().getGraphsWindow().setWidth(this.getWidth());
		PrefsInstance.getInstance().getPrefs().getWindows().getGraphsWindow().setHeight(this.getHeight());

		PrefsInstance.getInstance().savePrefs();

		graphFrameInstances.remove(this);
	}

	protected Map<Category, Long> getExpensesBetween(Date startDate, Date endDate){
		Vector<Transaction> transactions = TransactionController.getTransactions(startDate, endDate);
		Map<Category, Long> categories = new HashMap<Category, Long>();

		//This map is where we store the totals for this time period.
		for (Category category : SourceController.getCategories()) {
			if (!category.isIncome())
				categories.put(category, new Long(0));
		}

		for (Transaction transaction : transactions) {
			//Sum up the amounts for each category.
			if (transaction.getFrom() instanceof Category){
				Category c = (Category) transaction.getFrom();
				if (!c.isIncome()){
					Long l = categories.get(c);
					l += transaction.getAmount();
					categories.put(c, l);
					if (Const.DEVEL) Log.debug("Added a source");
				}
			}
			else if (transaction.getTo() instanceof Category){
				Category c = (Category) transaction.getTo();
				if (!c.isIncome()){
					Long l = categories.get(c);
					l += transaction.getAmount();
					categories.put(c, l);
					if (Const.DEVEL) Log.debug("Added a destination");
				}
			}
			else
				if (Const.DEVEL) Log.debug("Didn't add anything...");
		}

		return categories;
	}

	protected Map<Category, Long> getIncomeBetween(Date startDate, Date endDate){
		Vector<Transaction> transactions = TransactionController.getTransactions(startDate, endDate);
		Map<Category, Long> categories = new HashMap<Category, Long>();

		//This map is where we store the totals for this time period.
		for (Category category : SourceController.getCategories()) {
			if (category.isIncome())
				categories.put(category, new Long(0));
		}

		for (Transaction transaction : transactions) {
			//Sum up the amounts for each category.
			if (transaction.getFrom() instanceof Category){
				Category c = (Category) transaction.getFrom();
				if (c.isIncome()){
					Long l = categories.get(c);
					l += transaction.getAmount();
					categories.put(c, l);
					if (Const.DEVEL) Log.debug("Added a source");
				}
			}
			else if (transaction.getTo() instanceof Category){
				Category c = (Category) transaction.getTo();
				if (c.isIncome()){
					Long l = categories.get(c);
					l += transaction.getAmount();
					categories.put(c, l);
					if (Const.DEVEL) Log.debug("Added a destination");
				}
			}
			else
				if (Const.DEVEL) Log.debug("Didn't add anything...");
		}

		return categories;
	}

	protected Map<Account, Long> getAccountBalance(Date date){
		Map<Account, Long> map = new HashMap<Account, Long>();

		for (Account a : SourceController.getAccounts()) {
			if (a.getCreationDate().before(date))
				map.put(a, a.getStartingBalance());
			else
				map.put(a, 0l);
		}

		Vector<Transaction> transactions = TransactionController.getTransactions();

		for (Transaction transaction : transactions) {
			if (transaction.getDate().before(date)){
				//We are moving money *to* this account
				if (transaction.getTo() instanceof Account){
					Account a = (Account) transaction.getTo();
					map.put(a, map.get(a) + transaction.getAmount());
				}

				//We are moving money *from* this account
				if (transaction.getFrom() instanceof Account){
					Account a = (Account) transaction.getFrom();
					map.put(a, map.get(a) - transaction.getAmount());
				}
			}
			else{
				if (Const.DEVEL) Log.debug("Not including transaction.");
			}
		}

		return map;
	}

	public Component getPrintedComponent() {
		return reportPanel;
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(okButton)){
			saveWindowPosition();
			GraphFrame.this.setVisible(false);
		}
	}

	public StandardContainer clear() {
		return this;
	}

	public StandardContainer updateButtons() {
		return this;
	}

	public AbstractFrame updateContent() {
		this.setTitle(graphPlugin.getTitle());
		reportPanel = graphPlugin.getGraphPanel(startDate, endDate);

		reportPanelSpacer.removeAll();
		reportPanelSpacer.add(reportPanel, BorderLayout.CENTER);

		this.pack();

		return this;
	}

	public static void updateAllGraphWindows(){
		for (GraphFrame gfl : Collections.unmodifiableCollection(graphFrameInstances)) {
			if (gfl != null)
				gfl.updateContent();
		}
	}

}
