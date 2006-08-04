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
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
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
import org.homeunix.drummer.view.components.TransactionListElementFilter;
import org.homeunix.drummer.view.layout.TransactionsFrameLayout;

import de.schlichtherle.swing.filter.FilteredStaticListModel;

public class TransactionsFrame extends TransactionsFrameLayout {
	public static final long serialVersionUID = 0;	
	
	protected final FilteredStaticListModel filteredListModel;
	protected final TransactionListElementFilter transactionFilter;
	
	private Account account;
	
	public TransactionsFrame(Account account){
		super(account);
		
		transactionFilter = new TransactionListElementFilter();
		filteredListModel = new FilteredStaticListModel();
		
		filteredListModel.setFilter(transactionFilter);
				
		this.account = account;
		
		this.setTitle(Translate.getInstance().get(TranslateKeys.TRANSACTIONS) + " - " + account.getName());
		
		editableTransaction.setTransaction(null, true);
		updateContent();
	}
	
	public TransactionsFrame(Account account, Transaction transaction) {
		this(account);
		
		list.setSelectedValue(transaction, true);
	}
	
	protected AbstractBudgetFrame initActions(){
		searchField.getDocument().addDocumentListener(new DocumentListener(){
			private void update(){
				if (!searchField.getText().equals(searchField.getHint()))
					transactionFilter.setFilterText(searchField.getText());
				else
					transactionFilter.setFilterText("");
				
				filteredListModel.update();
				Log.debug(transactionFilter.getFilterText());
			}
			
			public void changedUpdate(DocumentEvent arg0) {
				update();
			}

			public void insertUpdate(DocumentEvent arg0) {
				update();				
			}

			public void removeUpdate(DocumentEvent arg0) {
				update();				
			};
		});
		
		list.addListSelectionListener(new ListSelectionListener(){
			public void valueChanged(ListSelectionEvent arg0) {
				if (!arg0.getValueIsAdjusting()){
					if (editableTransaction.isChanged() 
							&& editableTransaction.getTransaction() != (Transaction) list.getSelectedValue()){
						int ret;
						
						if (isValidRecord()){
							ret = JOptionPane.showConfirmDialog(
								null, 
								Translate.getInstance().get(TranslateKeys.TRANSACTION_CHANGED_MESSAGE), 
								Translate.getInstance().get(TranslateKeys.TRANSACTION_CHANGED_TITLE),
								JOptionPane.YES_NO_CANCEL_OPTION);
							if (ret == JOptionPane.YES_OPTION){
								try{
									recordTransaction();
									editableTransaction.setChanged(false);
								}
								catch (InvalidTransactionException ite){}
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
									Translate.getInstance().get(TranslateKeys.TRANSACTION_CHANGED_INVALID_MESSAGE), 
									Translate.getInstance().get(TranslateKeys.TRANSACTION_CHANGED_TITLE),
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
						
						editableTransaction.setTransaction(t, false);
					}
					else if (list.getSelectedValue() == null){
						editableTransaction.setTransaction(null, false);
						editableTransaction.updateContent();
					}
					
					updateButtons();
				}
			}
		});
		
		recordButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				try{
					recordTransaction();
					editableTransaction.setTransaction(null, true);
					updateContent();
					editableTransaction.setChanged(false);
				}
				catch(InvalidTransactionException ite){}
			}
		});
		
		clearButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				if (!editableTransaction.isChanged()
						|| JOptionPane.showConfirmDialog(
								TransactionsFrame.this,
								Translate.getInstance().get(TranslateKeys.CLEAR_TRANSACTION_LOSE_CHANGES),
								Translate.getInstance().get(TranslateKeys.CLEAR_TRANSACTION),
								JOptionPane.YES_NO_OPTION,
								JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
					
					editableTransaction.setTransaction(null, true);
					editableTransaction.updateContent();
					list.setSelectedIndex(list.getModel().getSize() - 1);
					list.ensureIndexIsVisible(list.getModel().getSize() - 1);
					
					updateButtons();
				}
			}
		});
		
		deleteButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				if (JOptionPane.showConfirmDialog(
						TransactionsFrame.this, 
						Translate.getInstance().get(TranslateKeys.DELETE_TRANSACTION_LOSE_CHANGES),
						Translate.getInstance().get(TranslateKeys.DELETE_TRANSACTION),
						JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
					
					Transaction t = (Transaction) list.getSelectedValue();
					DataInstance.getInstance().deleteTransaction(t);
					editableTransaction.setTransaction(null, true);
					list.clearSelection();
					
					updateContent();
				}
			}
		});
		
		clearSearchField.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				TransactionsFrame.this.searchField.setValue("");
				
			}
		});
		
		this.addComponentListener(new ComponentAdapter(){
//			@Override
//			public void componentResized(ComponentEvent arg0) {
//				Log.debug("Transactions window resized");
//				
//				PrefsInstance.getInstance().getPrefs().getTransactionsWindow().setHeight(arg0.getComponent().getHeight());
//				PrefsInstance.getInstance().getPrefs().getTransactionsWindow().setWidth(arg0.getComponent().getWidth());
//				
//				PrefsInstance.getInstance().savePrefs();
//				
//				super.componentResized(arg0);
//			}
			
			@Override
			public void componentHidden(ComponentEvent arg0) {
				PrefsInstance.getInstance().checkWindowSanity();
				
				PrefsInstance.getInstance().getPrefs().getTransactionsWindow().setX(arg0.getComponent().getX());
				PrefsInstance.getInstance().getPrefs().getTransactionsWindow().setY(arg0.getComponent().getY());
				PrefsInstance.getInstance().getPrefs().getTransactionsWindow().setHeight(arg0.getComponent().getHeight());
				PrefsInstance.getInstance().getPrefs().getTransactionsWindow().setWidth(arg0.getComponent().getWidth());
				
				PrefsInstance.getInstance().savePrefs();
				
				transactionInstances.put(account, null);
				
				super.componentHidden(arg0);
			}
		});
		
		return this;
	}
	
	private void recordTransaction() throws InvalidTransactionException {
		if (!isValidRecord()){
			JOptionPane.showMessageDialog(
					TransactionsFrame.this,
					Translate.getInstance().get(TranslateKeys.RECORD_BUTTON_ERROR),
					Translate.getInstance().get(TranslateKeys.ERROR),
					JOptionPane.ERROR_MESSAGE
			);
			throw new InvalidTransactionException();
		}

		
		Transaction t;
		if (recordButton.getText().equals(Translate.getInstance().get(TranslateKeys.RECORD)))
			t = DataInstance.getInstance().getDataModelFactory().createTransaction();
		else if (recordButton.getText().equals(Translate.getInstance().get(TranslateKeys.UPDATE)))
//			t = (Transaction) list.getSelectedValue();
			t = editableTransaction.getTransaction();
		else {
			Log.error("Unknown record button state: " + recordButton.getText());
			throw new InvalidTransactionException();
		}
		
		if (editableTransaction.getFrom().getCreationDate() != null
				&& editableTransaction.getFrom().getCreationDate().after(editableTransaction.getDate()))
			editableTransaction.getFrom().setCreationDate(editableTransaction.getDate());
		if (editableTransaction.getTo().getCreationDate() != null
				&& editableTransaction.getTo().getCreationDate().after(editableTransaction.getDate()))
			editableTransaction.getTo().setCreationDate(editableTransaction.getDate());
		
		t.setDate(editableTransaction.getDate());
		t.setDescription(editableTransaction.getDescription());
		t.setAmount(editableTransaction.getAmount());
		t.setTo(editableTransaction.getTo());
		t.setFrom(editableTransaction.getFrom());
		t.setMemo(editableTransaction.getMemo());
		t.setNumber(editableTransaction.getNumber());
		
		if (recordButton.getText().equals(Translate.getInstance().get(TranslateKeys.RECORD)))
			DataInstance.getInstance().addTransaction(t);
		else {
			DataInstance.getInstance().calculateAllBalances();
			DataInstance.getInstance().saveDataModel();
		}
		
		//Update the autocomplete entries
		PrefsInstance.getInstance().addDescEntry(editableTransaction.getDescription());
		PrefsInstance.getInstance().setAutoCompleteEntry(
				editableTransaction.getDescription(),
				editableTransaction.getNumber(),
				editableTransaction.getAmount(),
				editableTransaction.getFrom().toString(),
				editableTransaction.getTo().toString(),
				editableTransaction.getMemo());
	}
	
	protected AbstractBudgetFrame initContent(){
		this.setTitle(account.getName() + " - " + Translate.getInstance().get(TranslateKeys.TRANSACTIONS));
		list.setListData(DataInstance.getInstance().getTransactions(account));
		editableTransaction.setTransaction(null, true);
		
		return this;
	}
	
	public AbstractBudgetFrame updateContent(){
		Vector<Transaction> data = DataInstance.getInstance().getTransactions(account);
		data.add(null);
		list.setListData(data);

		//Update the search
		if (filteredListModel != null && list != null){
			filteredListModel.setSource(list.getModel());
			list.setModel(filteredListModel);
		}
		
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
			recordButton.setText(Translate.getInstance().get(TranslateKeys.RECORD));
			clearButton.setText(Translate.getInstance().get(TranslateKeys.CLEAR));
			deleteButton.setEnabled(false);
		}
		else{
			recordButton.setText(Translate.getInstance().get(TranslateKeys.UPDATE));
			clearButton.setText(Translate.getInstance().get(TranslateKeys.NEW));
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
		editableTransaction.resetSelection();
		return super.openWindow();
	}

	protected boolean isValidRecord(){
		return (!(
				editableTransaction.getDescription().length() == 0 
				|| editableTransaction.getDate() == null
				|| editableTransaction.getAmount() < 0
				|| editableTransaction.getTo() == null
				|| editableTransaction.getFrom() == null
				|| (editableTransaction.getFrom() != account
						&& editableTransaction.getTo() != account)
		));
	}
	
	private class InvalidTransactionException extends Exception {
		public final static long serialVersionUID = 0;
	}
	

	

}
