/*
 * Created on May 6, 2006 by wyatt
 */
package org.homeunix.drummer.view.logic;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.Vector;

import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.homeunix.drummer.Strings;
import org.homeunix.drummer.controller.DataInstance;
import org.homeunix.drummer.controller.PrefsInstance;
import org.homeunix.drummer.model.Account;
import org.homeunix.drummer.model.Transaction;
import org.homeunix.drummer.util.Log;
import org.homeunix.drummer.view.AbstractBudgetFrame;
import org.homeunix.drummer.view.layout.TransactionsFrameLayout;

public class TransactionsFrame extends TransactionsFrameLayout {
	public static final long serialVersionUID = 0;
	
	private Account account;
	
	public TransactionsFrame(Account account){
		super(account);
		
		this.account = account;
		
		this.setTitle(Strings.inst().get(Strings.TRANSACTIONS) + " - " + account.getName());
		
		editableTransaction.clearTransaction();
		updateContent();
		openWindow();
	}
	
	protected AbstractBudgetFrame initActions(){
		list.addListSelectionListener(new ListSelectionListener(){
			public void valueChanged(ListSelectionEvent arg0) {
				if (list.getSelectedValue() instanceof Transaction) {
					Transaction t = (Transaction) list.getSelectedValue();
					
					editableTransaction.setTransaction(t);
				}
				else{
					editableTransaction.clearTransaction();
					editableTransaction.updateContent();
					
				}
				
				updateButtons();
			}
		});
		
		recordButton.addActionListener(new ActionListener(){
//			[TODO] Do we want to check if amount == 0 here?
			public void actionPerformed(ActionEvent arg0) {
				if (editableTransaction.getDescription().length() == 0 
						|| editableTransaction.getDate() == null
						|| editableTransaction.getAmount() < 0
						|| editableTransaction.getTransferTo() == null
						|| editableTransaction.getTransferFrom() == null
						|| (editableTransaction.getTransferFrom() != account
								&& editableTransaction.getTransferTo() != account)){
					JOptionPane.showMessageDialog(TransactionsFrame.this, Strings.inst().get(Strings.RECORD_BUTTON_ERROR));
					return;
				}

				
				Transaction t;
				if (recordButton.getText().equals(Strings.inst().get(Strings.RECORD)))
					t = DataInstance.getInstance().getDataModelFactory().createTransaction();
				else if (recordButton.getText().equals(Strings.inst().get(Strings.UPDATE)))
					t = (Transaction) list.getSelectedValue();
				else {
					Log.error("Unknown record button state: " + recordButton.getText());
					return;
				}
				
				if (editableTransaction.getTransferFrom().getCreationDate() != null
						&& editableTransaction.getTransferFrom().getCreationDate().after(editableTransaction.getDate()))
					editableTransaction.getTransferFrom().setCreationDate(editableTransaction.getDate());
				if (editableTransaction.getTransferTo().getCreationDate() != null
						&& editableTransaction.getTransferTo().getCreationDate().after(editableTransaction.getDate()))
					editableTransaction.getTransferTo().setCreationDate(editableTransaction.getDate());
				
				t.setDate(editableTransaction.getDate());
				t.setDescription(editableTransaction.getDescription());
				t.setAmount(editableTransaction.getAmount());
				t.setTo(editableTransaction.getTransferTo());
				t.setFrom(editableTransaction.getTransferFrom());
				t.setMemo(editableTransaction.getMemo());
				t.setNumber(editableTransaction.getNumber());
				
				if (recordButton.getText().equals(Strings.inst().get(Strings.RECORD)))
					DataInstance.getInstance().addTransaction(t);
				else {
					DataInstance.getInstance().calculateAllBalances();
					DataInstance.getInstance().saveDataModel();
				}
				
				if (editableTransaction.getMemo().length() > 0){
					PrefsInstance.getInstance().addMemoEntry(editableTransaction.getMemo());
				}
				PrefsInstance.getInstance().addDescEntry(editableTransaction.getDescription());
				
				editableTransaction.clearTransaction();
				updateContent();
			}
		});
		
		clearButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				if (JOptionPane.showConfirmDialog(
						TransactionsFrame.this,
						Strings.inst().get(Strings.CLEAR_TRANSACTION_LOSE_CHANGES),
						Strings.inst().get(Strings.CLEAR_TRANSACTION),
						JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
					
					editableTransaction.clearTransaction();
					editableTransaction.updateContent();
					list.clearSelection();
					list.ensureIndexIsVisible(list.getModel().getSize() - 1);
					
					updateButtons();
				}
			}
		});
		
		deleteButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				if (JOptionPane.showConfirmDialog(
						TransactionsFrame.this, 
						Strings.inst().get(Strings.DELETE_TRANSACTION_LOSE_CHANGES),
						Strings.inst().get(Strings.DELETE_TRANSACTION),
						JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
					
					Transaction t = (Transaction) list.getSelectedValue();
					DataInstance.getInstance().deleteTransaction(t);
					editableTransaction.clearTransaction();
					list.clearSelection();
					
					updateContent();
				}
			}
		});
		
		this.addComponentListener(new ComponentAdapter(){
			@Override
			public void componentHidden(ComponentEvent arg0) {
				PrefsInstance.getInstance().checkSanity();
				
				PrefsInstance.getInstance().getPrefs().getTransactionsWindow().setX(arg0.getComponent().getX());
				PrefsInstance.getInstance().getPrefs().getTransactionsWindow().setY(arg0.getComponent().getY());
				PrefsInstance.getInstance().getPrefs().getTransactionsWindow().setWidth(arg0.getComponent().getWidth());
				PrefsInstance.getInstance().getPrefs().getTransactionsWindow().setHeight(arg0.getComponent().getHeight());
				
				PrefsInstance.getInstance().savePrefs();
				
				super.componentHidden(arg0);
			}
		});
		
		return this;
	}
	
	protected AbstractBudgetFrame initContent(){
		this.setTitle(account.getName() + " - " + Strings.inst().get(Strings.TRANSACTIONS));
		list.setListData(DataInstance.getInstance().getTransactions(account));
		editableTransaction.clearTransaction();
		
		return this;
	}
	
	public AbstractBudgetFrame updateContent(){
		Vector<Transaction> data = DataInstance.getInstance().getTransactions(account);
		data.add(null);
		list.setListData(data);
		
		editableTransaction.updateContent();
		
		list.ensureIndexIsVisible(list.getModel().getSize() - 1);
		updateButtons();
		
		MainBudgetFrame.getInstance().getAccountListPanel().updateContent();
		MainBudgetFrame.getInstance().getCategoryListPanel().updateContent();
		
		return this;
	}
	
	public AbstractBudgetFrame updateButtons(){
		if (editableTransaction == null 
				|| editableTransaction.getTransaction() == null){
			recordButton.setText(Strings.inst().get(Strings.RECORD));
			clearButton.setText(Strings.inst().get(Strings.CLEAR));
			deleteButton.setEnabled(false);
		}
		else{
			recordButton.setText(Strings.inst().get(Strings.UPDATE));
			clearButton.setText(Strings.inst().get(Strings.NEW));
			deleteButton.setEnabled(true);
		}
		
//		if (editableTransaction.getDescription().length() > 0 
//				&& editableTransaction.getDate() != null
//				&& editableTransaction.getAmount() != 0
//				&& editableTransaction.getTransferTo() != null
//				&& editableTransaction.getTransferFrom() != null){
//			recordButton.setEnabled(true);
//		}
//		else
//			recordButton.setEnabled(false);
		
		return this;
	}
	
	public Account getAccount(){
		return account;
	}
	
	@Override
	public Component getPrintedComponent() {
		return list;
	}

}
