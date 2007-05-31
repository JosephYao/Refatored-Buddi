/*
 * Created on May 14, 2006 by wyatt
 */
package org.homeunix.drummer.view;

import java.awt.event.ActionEvent;

import javax.swing.JOptionPane;

import org.homeunix.drummer.controller.Translate;
import org.homeunix.drummer.controller.TranslateKeys;
import org.homeunix.drummer.controller.TypeController;
import org.homeunix.drummer.model.Type;
import org.homeunix.thecave.moss.gui.abstractwindows.AbstractDialog;

public class TypeModifyDialog extends AbstractModifyDialog<Type> {
	public static final long serialVersionUID = 0;

	public TypeModifyDialog(Type type){
		super(MainFrame.getInstance(), type);

		check.setText(Translate.getInstance().get(TranslateKeys.CREDIT));
		this.setTitle(Translate.getInstance().get(TranslateKeys.EDIT_ACCOUNT_TYPES));
	}

	public AbstractDialog init() {
		okButton.addActionListener(this);
		cancelButton.addActionListener(this);

		if (source == null){
			name.setText("");
			check.setSelected(false);
			check.setEnabled(true);
			this.setTitle(Translate.getInstance().get(TranslateKeys.TYPE_MODIFY_NEW));
		}
		else{
			name.setText(Translate.getInstance().get(source.getName()));
			check.setSelected(source.isCredit());
			check.setEnabled(false);
			this.setTitle(Translate.getInstance().get(TranslateKeys.TYPE_MODIFY_EDIT));
		}

		return this;
	}

	public AbstractDialog updateContent(){
		return this;
	}

	@SuppressWarnings("unchecked")
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(okButton)){
			if (name.getText().length() == 0){
				String[] options = new String[1];
				options[0] = Translate.getInstance().get(TranslateKeys.BUTTON_OK);

				JOptionPane.showOptionDialog(
						TypeModifyDialog.this, 
						Translate.getInstance().get(TranslateKeys.ENTER_ACCOUNT_TYPE_NAME),
						Translate.getInstance().get(TranslateKeys.MORE_INFO_NEEDED),
						JOptionPane.DEFAULT_OPTION,
						JOptionPane.INFORMATION_MESSAGE,
						null,
						options,
						options[0]);
			}
			else{
				if (source == null){
					TypeController.addType(name.getText(), check.isSelected());
//					DataInstance.getInstance().saveDataModel();
				}
				else {
					source.setName(name.getText());
					source.setCredit(check.isSelected());
//					DataInstance.getInstance().saveDataModel();
				}

				TypeModifyDialog.this.closeWindow();
				
//				MainFrame.getInstance().getAccountListPanel().updateContent();
			}

			TransactionsFrame.updateAllTransactionWindows();
//			ReportFrame.updateAllReportWindows();
//			GraphFrame.updateAllGraphWindows();
		}
		else if (e.getSource().equals(cancelButton)){
			TypeModifyDialog.this.closeWindow();

//			MainFrame.getInstance().getAccountListPanel().updateContent();
		}
	}
}
