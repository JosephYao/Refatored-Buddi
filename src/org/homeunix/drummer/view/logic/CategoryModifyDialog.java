/*
 * Created on May 14, 2006 by wyatt
 */
package org.homeunix.drummer.view.logic;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import org.homeunix.drummer.TranslateKeys;
import org.homeunix.drummer.Translate;
import org.homeunix.drummer.controller.DataInstance;
import org.homeunix.drummer.model.Category;
import org.homeunix.drummer.util.Log;
import org.homeunix.drummer.view.AbstractBudgetDialog;
import org.homeunix.drummer.view.layout.ModifyDialogLayout;

public class CategoryModifyDialog extends ModifyDialogLayout<Category> {
	public static final long serialVersionUID = 0;
	
	public CategoryModifyDialog(){
		super(MainBudgetFrame.getInstance());
		amountLabel.setText(Translate.inst().get(TranslateKeys.BUDGETED_AMOUNT));
		pulldownLabel.setText(Translate.inst().get(TranslateKeys.PARENT_CATEGORY));
	}

	protected String getType(){
		return Translate.inst().get(TranslateKeys.CATEGORY);
	}
		
	@Override
	protected AbstractBudgetDialog initActions() {
		okButton.addActionListener(new ActionListener(){
			@SuppressWarnings("unchecked")
			public void actionPerformed(ActionEvent arg0) {
				if (name.getText().length() == 0){
					JOptionPane.showMessageDialog(
							CategoryModifyDialog.this, 
							Translate.inst().get(TranslateKeys.ENTER_CATEGORY_NAME),
							Translate.inst().get(TranslateKeys.MORE_INFO_NEEDED),
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
								Log.debug("A Category cannot be it's own parent.  Where is your knowledge of Bio?!?");
								pulldown.setSelectedItem(c.getParent());
							}
							else
								pulldown.setSelectedItem(Translate.inst().get(TranslateKeys.NO_PARENT));
							return;
						}
						while (temp.getParent() != null){
							if (temp.getParent().equals(c)){
								Log.error("A Category cannot be it's own grandpa (or other ancestor), although it seems as though you would like that.");
								if (c.getParent() != null)
									pulldown.setSelectedItem(c.getParent());
								else
									pulldown.setSelectedItem(Translate.inst().get(TranslateKeys.NO_PARENT));
								return;
							}
							
							temp = temp.getParent();
						}
					}
					
					c.setName(name.getText());
					c.setBudgetedAmount((int) (Double.parseDouble(amount.getValue().toString()) * 100));

					//We need to clear out any old, obsolete references to parents
					// and children.
					Log.debug("No parent selected.");
					if (c.getParent() != null){
						c.getParent().getChildren().remove(c);
						Log.debug("Removing " + c + " from " + c.getParent());
					}

					if (pulldown.getSelectedItem() instanceof Category){
						Log.debug("Setting parent of " + c + " to " + pulldown.getSelectedItem());
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
					
					CategoryModifyDialog.this.setVisible(false);
					MainBudgetFrame.getInstance().getCategoryListPanel().updateContent();
				}
			}
		});
		
		cancelButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				CategoryModifyDialog.this.setVisible(false);
				MainBudgetFrame.getInstance().getCategoryListPanel().updateContent();
			}
		});
		
		pulldown.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				if (pulldown.getSelectedItem() instanceof Category){
					check.setEnabled(false);
					check.setSelected(((Category) pulldown.getSelectedItem()).isIncome());
				}
				else{
					check.setEnabled(true);
				}
			}
		});
		
		check.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				updateContent();
			}
		});
		
		return this;
	}

	@Override
	protected AbstractBudgetDialog initContent() {
		
		if (source == null){
			updateContent();
			name.setText("");
			amount.setValue(0);
			pulldown.setSelectedItem(Translate.inst().get(TranslateKeys.NO_PARENT));
			check.setSelected(false);
		}
		else{
			name.setText(Translate.inst().get(source.getName()));
			amount.setValue((double) source.getBudgetedAmount() / 100.0);
			if (source.isIncome())
				check.setSelected(true);
			else
				check.setSelected(false);
		
			updateContent();
			
			if (source.getParent() != null){
				Log.debug(source.getParent());
				pulldown.setSelectedItem(source.getParent());
			}
			else
				pulldown.setSelectedItem(Translate.inst().get(TranslateKeys.NO_PARENT));
		}
		
		
		
		return this;
	}

	public AbstractBudgetDialog updateContent(){
		pulldownModel.removeAllElements();
		pulldownModel.addElement(Translate.inst().get(TranslateKeys.NO_PARENT));

		for (Category c : DataInstance.getInstance().getCategories()) {
			if (source == null 
					|| c.isIncome() == check.isSelected())
				pulldownModel.addElement(c);
		}
		
		
		return this;
	}
}
