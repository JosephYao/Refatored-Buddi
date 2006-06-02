/*
 * Created on May 6, 2006 by wyatt
 */
package org.homeunix.drummer.view.layout;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import org.homeunix.drummer.Buddi;
import org.homeunix.drummer.Translate;
import org.homeunix.drummer.model.Account;
import org.homeunix.drummer.view.AbstractBudgetFrame;
import org.homeunix.drummer.view.components.EditableTransaction;
import org.homeunix.drummer.view.components.TransactionCellRenderer;

public abstract class TransactionsFrameLayout extends AbstractBudgetFrame {
	public static final long serialVersionUID = 0;

	protected final JList list;
	protected final EditableTransaction editableTransaction;
	protected final JButton recordButton;
	protected final JButton clearButton;
	protected final JButton deleteButton;
	
	public TransactionsFrameLayout(Account account){
		//Set up the transaction list
		list = new JList();
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		TransactionCellRenderer renderer = new TransactionCellRenderer();
		renderer.setAccount(account);
		list.setCellRenderer(renderer);
		JScrollPane listScroller = new JScrollPane(list);
				
		JPanel scrollBorderPanel = new JPanel(new BorderLayout());
		scrollBorderPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
		scrollBorderPanel.add(listScroller, BorderLayout.CENTER);
		
		JPanel scrollPanel = new JPanel(new BorderLayout());
		scrollPanel.setBorder(BorderFactory.createTitledBorder(Translate.inst().get(Translate.TRANSACTIONS)));
		scrollPanel.add(scrollBorderPanel, BorderLayout.CENTER);
		
		//Set up the editing portion
		editableTransaction = new EditableTransaction(this);
		editableTransaction.updateContent();
		
		recordButton = new JButton(Translate.inst().get(Translate.RECORD));
		clearButton = new JButton(Translate.inst().get(Translate.CLEAR));
		deleteButton = new JButton(Translate.inst().get(Translate.DELETE));
		
		Dimension buttonSize = new Dimension(100, recordButton.getPreferredSize().height);
		recordButton.setPreferredSize(buttonSize);
		clearButton.setPreferredSize(buttonSize);
		deleteButton.setPreferredSize(buttonSize);

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
		
		JPanel mainPanel = new JPanel(); 
		mainPanel.setLayout(new BorderLayout());
		mainPanel.setBorder(BorderFactory.createEmptyBorder(7, 17, 17, 17));

		
		mainPanel.add(scrollPanel, BorderLayout.CENTER);
		mainPanel.add(buttonPanel, BorderLayout.SOUTH);
		
		this.setLayout(new BorderLayout());
		this.add(mainPanel, BorderLayout.CENTER);
		
//		this.setPreferredSize(new Dimension(700, 350));
		
		if (Buddi.isMac()){
			list.putClientProperty("Quaqua.List.style", "striped");
			listScroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
			//listScroller.putClientProperty("Quaqua.Component.visualMargin", new Insets(7,12,12,12));
		}
		
		//Call the method to add actions to the buttons
		initActions();
		
		//Show the window
		openWindow();
	}
	
	public abstract Account getAccount();
}
