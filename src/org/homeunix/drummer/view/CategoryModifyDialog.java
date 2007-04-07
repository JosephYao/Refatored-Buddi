/*
 * Created on May 14, 2006 by wyatt
 */
package org.homeunix.drummer.view;

import java.awt.event.ActionEvent;

import javax.swing.JOptionPane;

import org.homeunix.drummer.Const;
import org.homeunix.drummer.controller.Translate;
import org.homeunix.drummer.controller.TranslateKeys;
import org.homeunix.drummer.model.Category;
import org.homeunix.drummer.model.DataInstance;
import org.homeunix.thecave.moss.gui.abstractwindows.AbstractDialog;
import org.homeunix.thecave.moss.util.Log;

public class CategoryModifyDialog extends AbstractModifyDialog<Category> {
	public static final long serialVersionUID = 0;

	public CategoryModifyDialog(){
		super(MainFrame.getInstance());
		amountLabel.setText(Translate.getInstance().get(TranslateKeys.BUDGETED_AMOUNT));
		pulldownLabel.setText(Translate.getInstance().get(TranslateKeys.PARENT_CATEGORY));

		creditLimit.setVisible(false);
		creditLimitLabel.setVisible(false);
		interestRate.setVisible(false);
		interestRateLabel.setVisible(false);
	}

	protected String getType(){
		return Translate.getInstance().get(TranslateKeys.CATEGORY);
	}

	public AbstractDialog init() {
		okButton.addActionListener(this);
		cancelButton.addActionListener(this);
		check.addActionListener(this);
		pulldown.addActionListener(this);

		if (source == null){
			updateContent();
			name.setText("");
			amount.setValue(0);
			pulldown.setSelectedItem(Translate.getInstance().get(TranslateKeys.NO_PARENT));
			check.setSelected(false);
		}
		else{
			name.setText(Translate.getInstance().get(source.getName()));
			amount.setValue(source.getBudgetedAmount());
			if (source.isIncome())
				check.setSelected(true);
			else
				check.setSelected(false);

			updateContent();

			if (source.getParent() != null){
				if (Const.DEVEL) Log.debug(source.getParent());
				pulldown.setSelectedItem(source.getParent());
			}
			else
				pulldown.setSelectedItem(Translate.getInstance().get(TranslateKeys.NO_PARENT));
		}

		return this;
	}

	public AbstractDialog updateContent(){
		pulldownModel.removeAllElements();
		pulldownModel.addElement(Translate.getInstance().get(TranslateKeys.NO_PARENT));

		for (Category c : DataInstance.getInstance().getCategories()) {
			if (source == null 
					|| c.isIncome() == check.isSelected())
				pulldownModel.addElement(c);
		}


		return this;
	}

	@SuppressWarnings("unchecked")
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(okButton)){
			if (name.getText().length() == 0){
				JOptionPane.showMessageDialog(
						CategoryModifyDialog.this, 
						Translate.getInstance().get(TranslateKeys.ENTER_CATEGORY_NAME),
						Translate.getInstance().get(TranslateKeys.MORE_INFO_NEEDED),
						JOptionPane.INFORMATION_MESSAGE);
			}
			else{
				final Category c;
				if (source == null)
					c = DataInstance.getInstance().getDataModelFactory().createCategory();
				else
					c = source;

				//Check to make sure that there are no loops in the family tree...
				if (pulldown.getSelectedItem() instanceof Category){
					Category temp = (Category) pulldown.getSelectedItem();
					if (temp.equals(c)){
						if (c.getParent() != null){
							if (Const.DEVEL) Log.debug("A Category cannot be it's own parent.  Where is your knowledge of Bio?!?");
							pulldown.setSelectedItem(c.getParent());
						}
						else
							pulldown.setSelectedItem(Translate.getInstance().get(TranslateKeys.NO_PARENT));
						return;
					}
					while (temp.getParent() != null){
						if (temp.getParent().equals(c)){
							Log.error("A Category cannot be it's own grandpa (or other ancestor), although it seems as though you would like that.");
							if (c.getParent() != null)
								pulldown.setSelectedItem(c.getParent());
							else
								pulldown.setSelectedItem(Translate.getInstance().get(TranslateKeys.NO_PARENT));
							return;
						}

						temp = temp.getParent();
					}
				}

				c.setName(name.getText());
				c.setBudgetedAmount(amount.getValue());

				//We need to clear out any old, obsolete references to parents
				// and children.
				if (Const.DEVEL) Log.debug("No parent selected.");
				if (c.getParent() != null){
					c.getParent().getChildren().remove(c);
					if (Const.DEVEL) Log.debug("Removing " + c + " from " + c.getParent());
				}

				if (pulldown.getSelectedItem() instanceof Category){
					if (Const.DEVEL) Log.debug("Setting parent of " + c + " to " + pulldown.getSelectedItem());
					((Category) pulldown.getSelectedItem()).getChildren().add(c);
					c.setParent((Category) pulldown.getSelectedItem());
				}
				else
					c.setParent(null);

				if (check.isSelected())
					c.setIncome(true);
				else
					c.setIncome(false);

				if (source == null)
					DataInstance.getInstance().addCategory(c);
				else
					DataInstance.getInstance().saveDataModel();

				CategoryModifyDialog.this.closeWindow();
				
				MainFrame.getInstance().getCategoryListPanel().updateContent();
			}

			TransactionsFrame.updateAllTransactionWindows();
			ReportFrame.updateAllReportWindows();
			GraphFrame.updateAllGraphWindows();
		}
		else if (e.getSource().equals(cancelButton)){
			CategoryModifyDialog.this.closeWindow();
			
			MainFrame.getInstance().getCategoryListPanel().updateContent();
		}
		else if (e.getSource().equals(pulldown)){
			if (pulldown.getSelectedItem() instanceof Category){
				check.setEnabled(false);
				check.setSelected(((Category) pulldown.getSelectedItem()).isIncome());
			}
			else{
				check.setEnabled(true);
			}
		}
		else if (e.getSource().equals(check)){
			updateContent();
		}
	}
}
