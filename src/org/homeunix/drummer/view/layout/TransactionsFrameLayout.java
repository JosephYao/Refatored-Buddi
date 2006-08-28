/*
 * Created on May 6, 2006 by wyatt
 */
package org.homeunix.drummer.view.layout;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;

import org.homeunix.drummer.Buddi;
import org.homeunix.drummer.Translate;
import org.homeunix.drummer.TranslateKeys;
import org.homeunix.drummer.controller.model.PrefsInstance;
import org.homeunix.drummer.model.Account;
import org.homeunix.drummer.model.Transaction;
import org.homeunix.drummer.util.Formatter;
import org.homeunix.drummer.view.AbstractBudgetFrame;
import org.homeunix.drummer.view.components.EditableTransaction;
import org.homeunix.drummer.view.components.text.JHintTextField;

public abstract class TransactionsFrameLayout extends AbstractBudgetFrame {
	public static final long serialVersionUID = 0;

	protected static final Map<Account, TransactionsFrameLayout> transactionInstances = new HashMap<Account, TransactionsFrameLayout>();
	
	protected final JList list;
//	protected final DefaultListModel model;
	
	protected final EditableTransaction editableTransaction;
	protected final JButton recordButton;
	protected final JButton clearButton;
	protected final JButton deleteButton;
	protected final JHintTextField searchField;
	protected final JButton clearSearchField;
	protected final JLabel creditRemaining;
	
	public TransactionsFrameLayout(Account account){
		if (transactionInstances.get(account) != null)
			transactionInstances.get(account).setVisible(false);

		transactionInstances.put(account, this);
		
		//Set up the transaction list
		list = new JList();
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		creditRemaining = new JLabel();
		
		TransactionCellRenderer renderer = new TransactionCellRenderer();
		renderer.setAccount(account);
		list.setCellRenderer(renderer);
		
		JScrollPane listScroller = new JScrollPane(list);
				
		JPanel scrollBorderPanel = new JPanel(new BorderLayout());
		scrollBorderPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
		scrollBorderPanel.add(listScroller, BorderLayout.CENTER);
		
		JPanel scrollPanel = new JPanel(new BorderLayout());
		scrollPanel.setBorder(BorderFactory.createTitledBorder(""));
		scrollPanel.add(scrollBorderPanel, BorderLayout.CENTER);
		
		//Set up the editing portion
		editableTransaction = new EditableTransaction(this);
		editableTransaction.updateContent();
		
		recordButton = new JButton(Translate.getInstance().get(TranslateKeys.RECORD));
		clearButton = new JButton(Translate.getInstance().get(TranslateKeys.CLEAR));
		deleteButton = new JButton(Translate.getInstance().get(TranslateKeys.DELETE));
		searchField = new JHintTextField(Translate.getInstance().get(TranslateKeys.DEFAULT_SEARCH));
		clearSearchField = new JButton("x");
		
		recordButton.setPreferredSize(new Dimension(Math.max(100, recordButton.getPreferredSize().width), recordButton.getPreferredSize().height));
		clearButton.setPreferredSize(new Dimension(Math.max(100, clearButton.getPreferredSize().width), clearButton.getPreferredSize().height));
		deleteButton.setPreferredSize(new Dimension(Math.max(100, deleteButton.getPreferredSize().width), deleteButton.getPreferredSize().height));
		searchField.setPreferredSize(new Dimension(200, searchField.getPreferredSize().height));
//		clearSearchField.setPreferredSize(new Dimension(clearSearchField.getPreferredSize().width, searchField.getPreferredSize().height));

		JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		searchPanel.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
		searchPanel.add(searchField);
		searchPanel.add(clearSearchField);
		
		JPanel creditRemainingPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		creditRemainingPanel.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
		creditRemainingPanel.add(creditRemaining);
		
		JPanel topPanel = new JPanel(new BorderLayout());
		topPanel.add(searchPanel, BorderLayout.EAST);
		topPanel.add(creditRemainingPanel, BorderLayout.WEST);
		
		this.getRootPane().setDefaultButton(recordButton);
		
		JPanel buttonPanelRight = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		buttonPanelRight.add(clearButton);
		buttonPanelRight.add(recordButton);

		JPanel buttonPanelLeft = new JPanel(new FlowLayout(FlowLayout.LEFT));
		buttonPanelLeft.add(deleteButton);

		JPanel buttonPanel = new JPanel(new BorderLayout());
		buttonPanel.add(buttonPanelRight, BorderLayout.EAST);
		buttonPanel.add(buttonPanelLeft, BorderLayout.WEST);

		
		JPanel editPanel = new JPanel(new BorderLayout());
		editPanel.setBorder(BorderFactory.createTitledBorder(""));
		editPanel.add(editableTransaction, BorderLayout.CENTER);
		
		JPanel editPanelHolder = new JPanel(new BorderLayout());
		editPanelHolder.setBorder(BorderFactory.createEmptyBorder(2, 7, 7, 7));
		editPanelHolder.add(editPanel, BorderLayout.CENTER);

		scrollPanel.add(editPanelHolder, BorderLayout.SOUTH);
		scrollPanel.add(topPanel, BorderLayout.NORTH);
		
		JPanel mainPanel = new JPanel(); 
		mainPanel.setLayout(new BorderLayout());
		mainPanel.setBorder(BorderFactory.createEmptyBorder(7, 17, 17, 17));

		
//		mainPanel.add(searchPanel, BorderLayout.NORTH);
		mainPanel.add(scrollPanel, BorderLayout.CENTER);
		mainPanel.add(buttonPanel, BorderLayout.SOUTH);
		
		this.setLayout(new BorderLayout());
		this.add(mainPanel, BorderLayout.CENTER);
		
		if (Buddi.isMac()){
			list.putClientProperty("Quaqua.List.style", "striped");
			listScroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
//			listScroller.putClientProperty("Quaqua.Component.visualMargin", new Insets(7,12,12,12));
			clearSearchField.putClientProperty("Quaqua.Button.style", "square");
		}
		
		//Call the method to add actions to the buttons
		initActions();
		
		//Show the window
		openWindow();
	}
		
	public abstract Account getAccount();
	
	public class TransactionCellRenderer extends JLabel implements ListCellRenderer {
		public static final long serialVersionUID = 0;
			
		private Account account;
			
		public TransactionCellRenderer(){
			if (Buddi.isMac()){
				this.putClientProperty("Quaqua.Component.visualMargin", new Insets(0,0,0,0));
			}
		}

		public Component getListCellRendererComponent(JList list, Object obj, int index, boolean isSelected, boolean cellHasFocus) {
			if (obj instanceof Transaction) {
				Transaction transaction = (Transaction) obj;
				setTransaction(transaction, list.getWidth());
			}
			else{
				setTransaction(null, list.getWidth());
			}
				
			if(isSelected)
				this.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
			else
				this.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
			
			
			return this;
		}
			
		/**
		 * Draws the transaction in HTML in the JLabel.
		 * The table is organized as follows:
		 * 20%	40%		20%	20%
		 * 20%	40%		40%
		 * 
		 * The width of the entire table is determined by the width of the JList.
		 * @param transaction
		 * @param width
		 */
		private void setTransaction(Transaction transaction, int width){
			StringBuffer sb = new StringBuffer();
			
			sb.append("<html><table width='" + (width - 20) + "'><tr><td width='20%'>");
			if (transaction != null)
				sb.append(//Formatter.getInstance().getLengthFormat(width / 40).format(
						Formatter.getInstance().getDateFormat().format(transaction.getDate()));
//				);
			sb.append("</td><td width='40%'>");
			if (transaction != null)
				sb.append(Formatter.getInstance().getLengthFormat(width / 20).format(transaction.getDescription()));
			else
				sb.append("<font color='gray'>")
				.append(Translate.getInstance().get(TranslateKeys.NEW_TRANSACTION))
				.append("</font>");
			
			sb.append("</td><td width='20%'>");
			if (transaction != null)
				sb.append(PrefsInstance.getInstance().getPrefs().getCurrencySymbol())
				.append(Formatter.getInstance().getDecimalFormat().format(((double) transaction.getAmount()) / 100.0));

			sb.append("</td><td width='20%'>");
			if (transaction != null){
				long balanceValue;
				if (account != null){
					if (transaction.getFrom() instanceof Account 
							&& transaction.getFrom().equals(account))
						balanceValue = transaction.getBalanceFrom();
					else
						balanceValue = transaction.getBalanceTo();
					
					if (balanceValue < 0){
						sb.append("<font color='red'>");
						if (balanceValue <= 0 && balanceValue != 0)
							balanceValue *= -1;
						sb.append(PrefsInstance.getInstance().getPrefs().getCurrencySymbol());
						sb.append(Formatter.getInstance().getDecimalFormat().format(((double) balanceValue) / 100.0))
						.append("</font>");
					}
					else{
						sb.append(PrefsInstance.getInstance().getPrefs().getCurrencySymbol());
						sb.append(Formatter.getInstance().getDecimalFormat().format(((double) balanceValue) / 100.0));
					}
				}
			}
			sb.append("</td></tr><tr><td width='20%'>");
			if (transaction != null)
				sb.append(Formatter.getInstance().getLengthFormat(width / 40).format(transaction.getNumber()));
			
			sb.append("</td><td width='40%'>");
			if (transaction != null){
				sb.append(Formatter.getInstance().getLengthFormat(width / 20).format(
						transaction.getFrom() 
						+ " " 
						+ Translate.getInstance().get(TranslateKeys.TO)
						+ " "
						+ transaction.getTo()
				));	
			}
			sb.append("</td><td colspan=2 width='40%'>");
			if (transaction != null){
				sb.append(Formatter.getInstance().getLengthFormat(width / 20).format(transaction.getMemo()));
			}
			sb.append("</td></tr></table></html>");
			
			this.setText(sb.toString());
		}

		public void setAccount(Account account) {
			this.account = account;
		}
	}
}
