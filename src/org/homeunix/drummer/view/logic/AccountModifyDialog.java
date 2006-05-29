/*
 * Created on May 14, 2006 by wyatt
 */
package org.homeunix.drummer.view.logic;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import javax.swing.JOptionPane;

import org.homeunix.drummer.Strings;
import org.homeunix.drummer.controller.DataInstance;
import org.homeunix.drummer.model.Account;
import org.homeunix.drummer.model.SubAccount;
import org.homeunix.drummer.model.Type;
import org.homeunix.drummer.util.Log;
import org.homeunix.drummer.view.AbstractBudgetDialog;
import org.homeunix.drummer.view.layout.ModifyDialogLayout;

public class AccountModifyDialog extends ModifyDialogLayout<Account> {
	public static final long serialVersionUID = 0;

	public AccountModifyDialog(){
		super(MainBudgetFrame.getInstance());
		amountLabel.setText(Strings.inst().get(Strings.STARTING_BALANCE));
		pulldownLabel.setText(Strings.inst().get(Strings.ACCOUNT_TYPE));
		
		check.setText(Strings.inst().get(Strings.SUB_ACCOUNT));
		gap.setVisible(false);
	}

	protected String getType(){
		return Strings.inst().get(Strings.ACCOUNT);
	}
		
	@Override
	protected AbstractBudgetDialog initActions() {
		okButton.addActionListener(new ActionListener(){
			@SuppressWarnings("unchecked")
			public void actionPerformed(ActionEvent arg0) {
				if (name.getText().length() == 0 || pulldown.getSelectedItem() == null){
					JOptionPane.showMessageDialog(
							AccountModifyDialog.this, 
							Strings.inst().get(Strings.ENTER_ACCOUNT_NAME_AND_TYPE),
							Strings.inst().get(Strings.MORE_INFO_NEEDED),
							JOptionPane.INFORMATION_MESSAGE);
				}
				else{
					if (pulldown.getSelectedItem() instanceof Type){
						final Account a;
						if (source == null)
							a = DataInstance.getInstance().getDataModelFactory().createAccount();
						else
							a = source;
						a.setName(name.getText());
						
						a.setAccountType((Type) pulldown.getSelectedItem());
						int startingBalance = (int) (Double.parseDouble(amount.getValue().toString()) * 100);
						if (a.isCredit())
							startingBalance = Math.abs(startingBalance) * -1;
						
						a.setStartingBalance(startingBalance);
						
						a.setCreationDate(new Date());
						
						DataInstance.getInstance().addAccount(a);
						
						a.calculateBalance();
						
						if (source == null)
							DataInstance.getInstance().addAccount(a);
						else
							DataInstance.getInstance().saveDataModel();
						
						AccountModifyDialog.this.setVisible(false);
						MainBudgetFrame.getInstance().getAccountListPanel().updateContent();
					}
					else if (pulldown.getSelectedItem() instanceof Account){
						final Account a = (Account) pulldown.getSelectedItem();
						final SubAccount sa;
						if (source == null)
							sa = DataInstance.getInstance().getDataModelFactory().createSubAccount();
						else if (source instanceof SubAccount)
							sa = (SubAccount) source;
						else{
							Log.debug("Returning");
							return;
						}
						
						sa.setName(name.getText());
						
						((Account) pulldown.getSelectedItem()).getSub().add(sa);
						
						sa.setStartingBalance(0l);
						
						sa.setCreationDate(new Date());
						sa.setParent(a);
						
						if (!a.getSub().contains(sa)){
							a.getSub().add(sa);
						}
						
						if (source == null)
							DataInstance.getInstance().addSubAccount(sa);
						else
							DataInstance.getInstance().saveDataModel();
						
						AccountModifyDialog.this.setVisible(false);
						MainBudgetFrame.getInstance().getAccountListPanel().updateContent();
					}
				}
			}
		});
		
		cancelButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				AccountModifyDialog.this.setVisible(false);
				MainBudgetFrame.getInstance().getAccountListPanel().updateContent();
			}
		});
		
		check.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				AccountModifyDialog.this.updateContent();
				AccountModifyDialog.this.updateButtons();
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
			check.setVisible(true);
			check.setEnabled(true);
		}
		else{
			name.setText(source.getName());
			amount.setValue(Math.abs((double) source.getStartingBalance() / 100.0));
			check.setEnabled(false);
			
			if (source instanceof SubAccount){
				check.setSelected(true);
				check.setVisible(true);
				pulldown.setSelectedItem(((SubAccount) source).getParent());
				pulldown.setEnabled(false);
			}
			else{
				check.setSelected(false);
				check.setVisible(false);
				pulldown.setSelectedItem(source.getAccountType());
			}
		}
		
		return this;
	}

	public AbstractBudgetDialog updateContent(){
		pulldownModel.removeAllElements();
		pulldownModel.addElement(null);

		if (check.isSelected() || source instanceof SubAccount){
			for (Account a : DataInstance.getInstance().getAccounts()) {
				pulldownModel.addElement(a);
			}
			if (source instanceof SubAccount){
				pulldown.setSelectedItem(((SubAccount) source).getParent());
			}
		}
		else{
			for (Type t : DataInstance.getInstance().getTypes()) {
				pulldownModel.addElement(t);
			}
		}
		
		return this;
	}

	@Override
	public AbstractBudgetDialog updateButtons() {
		if (check.isSelected()){
			amount.setEnabled(false);
			amount.setValue(0);
		}
		else{
			amount.setEnabled(true);
		}
			
		
		return super.updateButtons();
	}
}
