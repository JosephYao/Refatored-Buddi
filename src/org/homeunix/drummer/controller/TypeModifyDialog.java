/*
 * Created on May 14, 2006 by wyatt
 */
package org.homeunix.drummer.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import org.homeunix.drummer.model.Account;
import org.homeunix.drummer.model.DataInstance;
import org.homeunix.drummer.model.Type;
import org.homeunix.drummer.view.AbstractDialog;
import org.homeunix.drummer.view.GraphFrameLayout;
import org.homeunix.drummer.view.ModifyDialogLayout;
import org.homeunix.drummer.view.ReportFrameLayout;

public class TypeModifyDialog extends ModifyDialogLayout<Account> {
	public static final long serialVersionUID = 0;

	public TypeModifyDialog(){
		super(MainBuddiFrame.getInstance());
		
		check.setText(Translate.getInstance().get(TranslateKeys.CREDIT));
		
//		this.remove(amount);
//		this.remove(amountLabel);
//		this.remove(pulldown);
//		this.remove(pulldownLabel);
//		this.remove(interestRate);
//		this.remove(interestRateLabel);
//		this.remove(creditLimit);
//		this.remove(creditLimitLabel);
//		amount.setVisible(false);
//		pulldown.setVisible(false);
//		interestRate.setVisible(false);
//		creditLimit.setVisible(false);
//		amountLabel.setVisible(false);
//		pulldownLabel.setVisible(false);
//		interestRateLabel.setVisible(false);
//		creditLimitLabel.setVisible(false);
//		gap.setVisible(false);
		
		this.setTitle(Translate.getInstance().get(TranslateKeys.EDIT_ACCOUNT_TYPES));
	}

	protected String getType(){
		return Translate.getInstance().get(TranslateKeys.ACCOUNT);
	}
		
	@Override
	protected AbstractDialog initActions() {
		okButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				if (name.getText().length() == 0){
					JOptionPane.showMessageDialog(
							TypeModifyDialog.this, 
							Translate.getInstance().get(TranslateKeys.ENTER_ACCOUNT_TYPE_NAME),
							Translate.getInstance().get(TranslateKeys.MORE_INFO_NEEDED),
							JOptionPane.INFORMATION_MESSAGE);
				}
				else{
					final Type t;
					if (type == null)
						DataInstance.getInstance().addType(name.getText(), check.isSelected());
					else {
						t = type;
						t.setName(name.getText());
						t.setCredit(check.isSelected());
						DataInstance.getInstance().saveDataModel();
					}
															
					TypeModifyDialog.this.setVisible(false);
					MainBuddiFrame.getInstance().getAccountListPanel().updateContent();
				}
				
				TransactionsFrame.updateAllTransactionWindows();
				ReportFrameLayout.updateAllReportWindows();
				GraphFrameLayout.updateAllGraphWindows();
			}
		});
		
		cancelButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				TypeModifyDialog.this.setVisible(false);
				MainBuddiFrame.getInstance().getAccountListPanel().updateContent();
			}
		});
		
		return this;
	}

	@Override
	protected AbstractDialog initContent() {
		updateContent();
		
		if (type == null){
			name.setText("");
			check.setSelected(false);
			check.setEnabled(true);
		}
		else{
			name.setText(Translate.getInstance().get(type.getName()));
			check.setSelected(type.isCredit());
			check.setEnabled(false);
		}
		
		return this;
	}

	public AbstractDialog updateContent(){
		return this;
	}
}
