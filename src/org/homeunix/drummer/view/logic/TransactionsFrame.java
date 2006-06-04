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

import org.homeunix.drummer.Translate;
import org.homeunix.drummer.TranslateKeys;
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
		
		this.setTitle(Translate.inst().get(TranslateKeys.TRANSACTIONS) + " - " + account.getName());
		
		editableTransaction.setTransaction(null);
		updateContent();
		openWindow();
	}
	
	protected AbstractBudgetFrame initActions(){
		list.addListSelectionListener(new ListSelectionListener(){
			public void valueChanged(ListSelectionEvent arg0) {
				if (!arg0.getValueIsAdjusting()){
					if (editableTransaction.isChanged() 
							&& editableTransaction.getTransaction() != (Transaction) list.getSelectedValue()){
						int ret;
						
						if (isValidRecord()){
							ret = JOptionPane.showConfirmDialog(
								null, 
								Translate.inst().get(TranslateKeys.TRANSACTION_CHANGED_MESSAGE), 
								Translate.inst().get(TranslateKeys.TRANSACTION_CHANGED_TITLE),
								JOptionPane.YES_NO_CANCEL_OPTION);
							if (ret == JOptionPane.YES_OPTION){
								recordTransaction();
								editableTransaction.setChanged(false);
							}
							else if (ret == JOptionPane.NO_OPTION){
								editableTransaction.setChanged(false);
//								list.setSelectedValue(editableTransaction.getTransaction(), true);
//								return;
							}
							else if (ret == JOptionPane.CANCEL_OPTION){
								editableTransaction.setChanged(false);
								list.setSelectedValue(editableTransaction.getTransaction(), true);
								editableTransaction.setChanged(true);
								return;
							}
						}
						else{
							ret = JOptionPane.showConfirmDialog(
									null, 
									Translate.inst().get(TranslateKeys.TRANSACTION_CHANGED_INVALID_MESSAGE), 
									Translate.inst().get(TranslateKeys.TRANSACTION_CHANGED_TITLE),
									JOptionPane.YES_NO_OPTION);
							if (ret == JOptionPane.NO_OPTION){
								editableTransaction.setChanged(false);
								
								if (editableTransaction.getTransaction() == null)
									list.setSelectedIndex(list.getModel().getSize() - 1);
								else
									list.setSelectedValue(editableTransaction.getTransaction(), true);
								editableTransaction.setChanged(true);
								return;
							}
							else if (ret == JOptionPane.YES_OPTION){
								editableTransaction.setChanged(false);
							}
						}
					}
					
					if (list.getSelectedValue() instanceof Transaction) {
						Transaction t = (Transaction) list.getSelectedValue();
						
						editableTransaction.setTransaction(t);
					}
					else if (list.getSelectedValue() == null){
						editableTransaction.setTransaction(null);
						editableTransaction.updateContent();
					}
					
					updateButtons();
				}
			}
		});
		
		recordButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				recordTransaction();
				editableTransaction.setTransaction(null);
				updateContent();
			}
		});
		
		clearButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				if (JOptionPane.showConfirmDialog(
						TransactionsFrame.this,
						Translate.inst().get(TranslateKeys.CLEAR_TRANSACTION_LOSE_CHANGES),
						Translate.inst().get(TranslateKeys.CLEAR_TRANSACTION),
						JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
					
					editableTransaction.setTransaction(null);
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
						Translate.inst().get(TranslateKeys.DELETE_TRANSACTION_LOSE_CHANGES),
						Translate.inst().get(TranslateKeys.DELETE_TRANSACTION),
						JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
					
					Transaction t = (Transaction) list.getSelectedValue();
					DataInstance.getInstance().deleteTransaction(t);
					editableTransaction.setTransaction(null);
					list.clearSelection();
					
					updateContent();
				}
			}
		});
		
		this.addComponentListener(new ComponentAdapter(){
			@Override
			public void componentResized(ComponentEvent arg0) {
				Log.debug("Transactions window resized");
				
				PrefsInstance.getInstance().getPrefs().getTransactionsWindow().setHeight(arg0.getComponent().getHeight());
				PrefsInstance.getInstance().getPrefs().getTransactionsWindow().setWidth(arg0.getComponent().getWidth());
				
				PrefsInstance.getInstance().savePrefs();
				
				super.componentResized(arg0);
			}
			
			@Override
			public void componentHidden(ComponentEvent arg0) {
				PrefsInstance.getInstance().checkSanity();
				
				PrefsInstance.getInstance().getPrefs().getTransactionsWindow().setX(arg0.getComponent().getX());
				PrefsInstance.getInstance().getPrefs().getTransactionsWindow().setY(arg0.getComponent().getY());
				
				PrefsInstance.getInstance().savePrefs();
				
				super.componentHidden(arg0);
			}
		});
		
		return this;
	}
	
	private void recordTransaction(){
		if (!isValidRecord()){
			JOptionPane.showMessageDialog(
					TransactionsFrame.this,
					Translate.inst().get(TranslateKeys.RECORD_BUTTON_ERROR),
					Translate.inst().get(TranslateKeys.ERROR),
					JOptionPane.ERROR_MESSAGE
			);
			return;
		}

		
		Transaction t;
		if (recordButton.getText().equals(Translate.inst().get(TranslateKeys.RECORD)))
			t = DataInstance.getInstance().getDataModelFactory().createTransaction();
		else if (recordButton.getText().equals(Translate.inst().get(TranslateKeys.UPDATE)))
//			t = (Transaction) list.getSelectedValue();
			t = editableTransaction.getTransaction();
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
		
		if (recordButton.getText().equals(Translate.inst().get(TranslateKeys.RECORD)))
			DataInstance.getInstance().addTransaction(t);
		else {
			DataInstance.getInstance().calculateAllBalances();
			DataInstance.getInstance().saveDataModel();
		}
		
//		Removed when I removed the memo field
//		if (editableTransaction.getMemo().length() > 0){
//			PrefsInstance.getInstance().addMemoEntry(editableTransaction.getMemo());
//		}
		PrefsInstance.getInstance().addDescEntry(editableTransaction.getDescription());
		
//		editableTransaction.setChanged(false);
	}
	
	protected AbstractBudgetFrame initContent(){
		this.setTitle(account.getName() + " - " + Translate.inst().get(TranslateKeys.TRANSACTIONS));
		list.setListData(DataInstance.getInstance().getTransactions(account));
		editableTransaction.setTransaction(null);
		
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
			recordButton.setText(Translate.inst().get(TranslateKeys.RECORD));
			clearButton.setText(Translate.inst().get(TranslateKeys.CLEAR));
			deleteButton.setEnabled(false);
		}
		else{
			recordButton.setText(Translate.inst().get(TranslateKeys.UPDATE));
			clearButton.setText(Translate.inst().get(TranslateKeys.NEW));
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

	@Override
	public AbstractBudgetFrame openWindow() {
		list.setSelectedIndex(list.getModel().getSize() - 1);
		return super.openWindow();
	}

	protected boolean isValidRecord(){
		return (!(
				editableTransaction.getDescription().length() == 0 
				|| editableTransaction.getDate() == null
				|| editableTransaction.getAmount() < 0
				|| editableTransaction.getTransferTo() == null
				|| editableTransaction.getTransferFrom() == null
				|| (editableTransaction.getTransferFrom() != account
						&& editableTransaction.getTransferTo() != account)
		));
	}
}
