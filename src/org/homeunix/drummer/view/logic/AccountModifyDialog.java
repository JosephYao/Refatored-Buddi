/*
 * Created on May 14, 2006 by wyatt
 */
package org.homeunix.drummer.view.logic;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import javax.swing.JOptionPane;

import org.homeunix.drummer.TranslateKeys;
import org.homeunix.drummer.Translate;
import org.homeunix.drummer.controller.DataInstance;
import org.homeunix.drummer.model.Account;
import org.homeunix.drummer.model.Type;
import org.homeunix.drummer.view.AbstractBudgetDialog;
import org.homeunix.drummer.view.layout.ModifyDialogLayout;

public class AccountModifyDialog extends ModifyDialogLayout<Account> {
	public static final long serialVersionUID = 0;

	public AccountModifyDialog(){
		super(MainBudgetFrame.getInstance());
		amountLabel.setText(Translate.inst().get(TranslateKeys.STARTING_BALANCE));
		pulldownLabel.setText(Translate.inst().get(TranslateKeys.ACCOUNT_TYPE));
		check.setVisible(false);
		gap.setVisible(false);
	}

	protected String getType(){
		return Translate.inst().get(TranslateKeys.ACCOUNT);
	}
		
	@Override
	protected AbstractBudgetDialog initActions() {
		okButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				if (name.getText().length() == 0 || pulldown.getSelectedItem() == null){
					JOptionPane.showMessageDialog(
							AccountModifyDialog.this, 
							Translate.inst().get(TranslateKeys.ENTER_ACCOUNT_NAME_AND_TYPE),
							Translate.inst().get(TranslateKeys.MORE_INFO_NEEDED),
							JOptionPane.INFORMATION_MESSAGE);
				}
				else{
					final Account a;
					if (source == null)
						a = DataInstance.getInstance().getDataModelFactory().createAccount();
					else
						a = source;
					a.setName(name.getText());
					
					a.setAccountType((Type) pulldown.getSelectedItem());
					long startingBalance = amount.getValue();
					if (a.isCredit())
						startingBalance = Math.abs(startingBalance) * -1;
					
					a.setStartingBalance(startingBalance);
					
					a.setCreationDate(new Date());
//					We now don't check if starting balance is 0, so that you can change the balance later.
//					if (startingBalance != 0){
//					Transaction t = DataInstance.getInstance().getDataModelFactory().createTransaction();
//					t.setDate(new Date());
//					t.setAmount(startingBalance);
//					if (a.isCredit()){
//						t.setFrom(a);
//						t.setTo(DataInstance.getInstance().getDataModel().getAllCategories().getStartingBalanceExpense());
//					}
//					else{
//						t.setFrom(DataInstance.getInstance().getDataModel().getAllCategories().getStartingBalanceIncome());
//						t.setTo(a);						
//					}
//					t.setNumber("");
//					t.setDescription(Strings.inst().get(Strings.STARTING_BALANCE));
//					t.setMemo("");
					
					DataInstance.getInstance().addAccount(a);
//					DataInstance.getInstance().addTransaction(t);
//					}
//					else{
//						DataInstance.getInstance().addAccount(a);
//					}
					
					
					a.calculateBalance();
					
					if (source == null)
						DataInstance.getInstance().addAccount(a);
					else
						DataInstance.getInstance().saveDataModel();
										
					AccountModifyDialog.this.setVisible(false);
					MainBudgetFrame.getInstance().getAccountListPanel().updateContent();
				}
			}
		});
		
		cancelButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				AccountModifyDialog.this.setVisible(false);
				MainBudgetFrame.getInstance().getAccountListPanel().updateContent();
			}
		});
		
		return this;
	}

	@Override
	protected AbstractBudgetDialog initContent() {
		updateContent();
		
		if (source == null){
			name.setText("");
			amount.setValue(0);
			pulldown.setSelectedItem(null);
//			amount.setEnabled(true);
		}
		else{
			name.setText(source.getName());
			amount.setValue(source.getStartingBalance());
	
			pulldown.setSelectedItem(source.getAccountType());
//			amount.setEnabled(false);
		}
		
		return this;
	}

	public AbstractBudgetDialog updateContent(){
		pulldownModel.removeAllElements();
		pulldownModel.addElement(null);

		for (Type t : DataInstance.getInstance().getTypes()) {
			pulldownModel.addElement(t);
		}
		
		return this;
	}
}
