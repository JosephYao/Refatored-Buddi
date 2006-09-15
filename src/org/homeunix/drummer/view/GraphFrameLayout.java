/*
 * Created on May 6, 2006 by wyatt
 */
package org.homeunix.drummer.view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

import org.homeunix.drummer.Const;
import org.homeunix.drummer.controller.Translate;
import org.homeunix.drummer.controller.TranslateKeys;
import org.homeunix.drummer.model.Account;
import org.homeunix.drummer.model.Category;
import org.homeunix.drummer.model.DataInstance;
import org.homeunix.drummer.model.Transaction;
import org.homeunix.drummer.plugins.BuddiGraphPlugin;
import org.homeunix.drummer.prefs.PrefsInstance;
import org.homeunix.drummer.util.Log;

public class GraphFrameLayout extends AbstractFrame {
	public static final long serialVersionUID = 0;

	protected final JPanel reportPanel;
	protected final JButton okButton;
		
	public GraphFrameLayout(BuddiGraphPlugin graphPlugin, final Date startDate, final Date endDate){
		this.setTitle(graphPlugin.getTitle());

		okButton = new JButton(Translate.getInstance().get(TranslateKeys.OK));
		
		Dimension buttonSize = new Dimension(100, okButton.getPreferredSize().height);
		okButton.setPreferredSize(buttonSize);
						
		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		buttonPanel.add(okButton);
		
		reportPanel = graphPlugin.getGraphPanel(startDate, endDate);
				
		JPanel reportPanelSpacer = new JPanel(new BorderLayout());
		reportPanelSpacer.setBorder(BorderFactory.createEmptyBorder(7, 17, 17, 17));
		reportPanelSpacer.add(reportPanel, BorderLayout.CENTER);
		
		JPanel reportPanel = new JPanel(new BorderLayout());
		reportPanel.setBorder(BorderFactory.createTitledBorder(""));
		reportPanel.add(reportPanelSpacer, BorderLayout.CENTER);
		
		JPanel mainPanel = new JPanel(); 
		mainPanel.setLayout(new BorderLayout());
		mainPanel.setBorder(BorderFactory.createEmptyBorder(7, 17, 17, 17));
		mainPanel.add(buttonPanel, BorderLayout.SOUTH);
		mainPanel.add(reportPanel, BorderLayout.CENTER);
		
		this.setLayout(new BorderLayout());
		this.add(mainPanel, BorderLayout.CENTER);
		
//		if (Buddi.isMac()){
//			reportLabelScroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
//		}
		
		//reportPanel.setText(buildReport(startDate, endDate));
				
//		reportPanel.setPreferredSize(new Dimension(reportPanel.getPreferredSize().width, Math.min(400, reportPanel.getPreferredSize().height)));
		
		//Call the method to add actions to the buttons
		initActions();
		
		//Show the window
		openWindow();
	}
	
	protected Vector<Account> getAccounts(){
		return DataInstance.getInstance().getAccounts();
	}
	
	
	
	@Override
	protected AbstractFrame initActions() {
		this.addComponentListener(new ComponentAdapter(){
			@Override
			public void componentHidden(ComponentEvent arg0) {
				PrefsInstance.getInstance().checkWindowSanity();
				
				PrefsInstance.getInstance().getPrefs().getReportsWindow().setX(arg0.getComponent().getX());
				PrefsInstance.getInstance().getPrefs().getReportsWindow().setY(arg0.getComponent().getY());
				PrefsInstance.getInstance().getPrefs().getReportsWindow().setWidth(arg0.getComponent().getWidth());
				PrefsInstance.getInstance().getPrefs().getReportsWindow().setHeight(arg0.getComponent().getHeight());
								
				PrefsInstance.getInstance().savePrefs();
				
				super.componentHidden(arg0);
			}
		});
		
		okButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				PrefsInstance.getInstance().checkWindowSanity();
				
				PrefsInstance.getInstance().getPrefs().getReportsWindow().setX(GraphFrameLayout.this.getX());
				PrefsInstance.getInstance().getPrefs().getReportsWindow().setY(GraphFrameLayout.this.getY());
				PrefsInstance.getInstance().getPrefs().getReportsWindow().setWidth(GraphFrameLayout.this.getWidth());
				PrefsInstance.getInstance().getPrefs().getReportsWindow().setHeight(GraphFrameLayout.this.getHeight());
								
				PrefsInstance.getInstance().savePrefs();
				
				GraphFrameLayout.this.setVisible(false);
			}
		});
		
		return this;
	}

	protected Map<Category, Long> getExpensesBetween(Date startDate, Date endDate){
		Vector<Transaction> transactions = DataInstance.getInstance().getTransactions(startDate, endDate);
		Map<Category, Long> categories = new HashMap<Category, Long>();
		
		//This map is where we store the totals for this time period.
		for (Category category : DataInstance.getInstance().getCategories()) {
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
		Vector<Transaction> transactions = DataInstance.getInstance().getTransactions(startDate, endDate);
		Map<Category, Long> categories = new HashMap<Category, Long>();
		
		//This map is where we store the totals for this time period.
		for (Category category : DataInstance.getInstance().getCategories()) {
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
		
		for (Account a : DataInstance.getInstance().getAccounts()) {
			if (a.getCreationDate().before(date))
				map.put(a, a.getStartingBalance());
			else
				map.put(a, 0l);
		}
		
		Vector<Transaction> transactions = DataInstance.getInstance().getTransactions();
		
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
	
	@Override
	public Component getPrintedComponent() {
		return reportPanel;
	}
	
	@Override
	protected AbstractFrame initContent() {
		return this;
	}
	
	@Override
	public AbstractFrame updateButtons() {
		return this;
	}
	
	@Override
	public AbstractFrame updateContent() {
		return this;
	}
}
