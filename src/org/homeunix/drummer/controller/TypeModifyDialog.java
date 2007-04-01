/*
 * Created on May 14, 2006 by wyatt
 */
package org.homeunix.drummer.controller;

import java.awt.event.ActionEvent;

import javax.swing.JOptionPane;

import org.homeunix.drummer.model.Account;
import org.homeunix.drummer.model.DataInstance;
import org.homeunix.drummer.model.Type;
import org.homeunix.drummer.view.GraphFrameLayout;
import org.homeunix.drummer.view.ModifyDialogLayout;
import org.homeunix.drummer.view.ReportFrameLayout;
import org.homeunix.thecave.moss.gui.abstractwindows.AbstractDialog;

public class TypeModifyDialog extends ModifyDialogLayout<Account> {
	public static final long serialVersionUID = 0;

	public TypeModifyDialog(){
		super(MainBuddiFrame.getInstance());

		check.setText(Translate.getInstance().get(TranslateKeys.CREDIT));
		this.setTitle(Translate.getInstance().get(TranslateKeys.EDIT_ACCOUNT_TYPES));
	}

	protected String getType(){
		return Translate.getInstance().get(TranslateKeys.ACCOUNT);
	}

	public AbstractDialog init() {
		okButton.addActionListener(this);
		cancelButton.addActionListener(this);

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

	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(okButton)){
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
		else if (e.getSource().equals(cancelButton)){
			TypeModifyDialog.this.setVisible(false);
			MainBuddiFrame.getInstance().getAccountListPanel().updateContent();
		}
	}
}
