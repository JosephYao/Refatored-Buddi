/*
 * Created on Aug 6, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.view.dialogs;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.homeunix.thecave.buddi.i18n.BuddiKeys;
import org.homeunix.thecave.buddi.i18n.keys.AccountFrameKeys;
import org.homeunix.thecave.buddi.i18n.keys.ButtonKeys;
import org.homeunix.thecave.buddi.model.Account;
import org.homeunix.thecave.buddi.model.DataModel;
import org.homeunix.thecave.buddi.model.Type;
import org.homeunix.thecave.buddi.model.prefs.PrefsModel;
import org.homeunix.thecave.buddi.util.BuddiInternalFormatter;
import org.homeunix.thecave.buddi.view.AccountFrame;
import org.homeunix.thecave.moss.swing.formatted.JDecimalField;
import org.homeunix.thecave.moss.swing.hint.JHintTextArea;
import org.homeunix.thecave.moss.swing.hint.JHintTextField;
import org.homeunix.thecave.moss.swing.window.MossDialog;
import org.homeunix.thecave.moss.util.Log;
import org.homeunix.thecave.moss.util.OperatingSystemUtil;

public class AccountEditorDialog extends MossDialog implements ActionListener {

	public static final long serialVersionUID = 0;

	private final JHintTextField name;
	private final JComboBox type;
	private final JDecimalField startingBalance;
	private final JHintTextArea notes;

	private final JButton ok;
	private final JButton cancel;

	private final TypeComboBoxModel typeComboBoxModel;

	private final Account selected;
	
	private final DataModel model;

	public AccountEditorDialog(AccountFrame frame, DataModel model, Account selected) {
		super(frame);

		this.selected = selected;
		this.model = model;

		name = new JHintTextField(PrefsModel.getInstance().getTranslator().get(BuddiKeys.HINT_NAME));
		typeComboBoxModel = new TypeComboBoxModel(model);
		type = new JComboBox(typeComboBoxModel);
		startingBalance = new JDecimalField(true);
		notes = new JHintTextArea(PrefsModel.getInstance().getTranslator().get(BuddiKeys.HINT_NOTES));

		ok = new JButton(PrefsModel.getInstance().getTranslator().get(ButtonKeys.BUTTON_OK));
		cancel = new JButton(PrefsModel.getInstance().getTranslator().get(ButtonKeys.BUTTON_CANCEL));
	}

	public void init() {
		super.init();
		
		JPanel textPanel = new JPanel(new BorderLayout());
		JPanel textPanelLeft = new JPanel(new GridLayout(0, 1));
		JPanel textPanelRight = new JPanel(new GridLayout(0, 1));
		textPanel.add(textPanelLeft, BorderLayout.WEST);
		textPanel.add(textPanelRight, BorderLayout.EAST);

		textPanelLeft.add(new JLabel(PrefsModel.getInstance().getTranslator().get(AccountFrameKeys.ACCOUNT_EDITOR_NAME)));
		textPanelLeft.add(new JLabel(PrefsModel.getInstance().getTranslator().get(AccountFrameKeys.ACCOUNT_EDITOR_TYPE)));
		textPanelLeft.add(new JLabel(PrefsModel.getInstance().getTranslator().get(AccountFrameKeys.ACCOUNT_EDITOR_STARTING_BALANCE)));
		textPanelLeft.add(new JLabel(PrefsModel.getInstance().getTranslator().get(AccountFrameKeys.ACCOUNT_EDITOR_NOTES)));

		textPanelRight.add(name);
		textPanelRight.add(type);
		textPanelRight.add(startingBalance);
		textPanelRight.add(new JScrollPane(notes));

		ok.setPreferredSize(BuddiInternalFormatter.getButtonSize(ok));
		cancel.setPreferredSize(BuddiInternalFormatter.getButtonSize(cancel));

		ok.addActionListener(this);
		cancel.addActionListener(this);

		type.addActionListener(this);
		
		type.setRenderer(new DefaultListCellRenderer(){
			private static final long serialVersionUID = 0;

			@Override
			public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
				super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

				if (value instanceof Type)
					this.setText(PrefsModel.getInstance().getTranslator().get(((Type) value).getName()));
				else
					this.setText(" ");

				return this;
			}
		});
		
		name.addKeyListener(new KeyAdapter(){
			@Override
			public void keyReleased(KeyEvent e) {
				super.keyReleased(e);
				
				updateButtons();
			}
		});
		
		FocusListener focusListener = new FocusListener(){
			public void focusGained(FocusEvent e) {}
			public void focusLost(FocusEvent e) {
				updateButtons();
			}
		};
		
		ok.addFocusListener(focusListener);
		cancel.addFocusListener(focusListener);
		name.addFocusListener(focusListener);
		type.addFocusListener(focusListener);
		startingBalance.addFocusListener(focusListener);
		notes.addFocusListener(focusListener);

		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		if (OperatingSystemUtil.isMac()){
			buttonPanel.add(cancel);			
			buttonPanel.add(ok);
		}
		else {
			buttonPanel.add(ok);
			buttonPanel.add(cancel);
		}

		this.getRootPane().setDefaultButton(ok);
		this.setLayout(new BorderLayout());
		this.add(textPanel, BorderLayout.CENTER);
		this.add(buttonPanel, BorderLayout.SOUTH);
	}

	public void updateButtons() {
		super.updateButtons();
		
		ok.setEnabled(name.getValue() != null && name.getValue().toString().length() > 0);
	}

	public void updateContent() {
		super.updateContent();
		
		if (selected == null){
			name.setValue("");
//			type.setSelectedItem(null);
			startingBalance.setValue(0l);
			notes.setValue("");
		}
		else {
			name.setValue(PrefsModel.getInstance().getTranslator().get(selected.getName()));
			type.setSelectedItem(selected.getType());
			startingBalance.setValue(selected.getStartingBalance());
			notes.setValue(PrefsModel.getInstance().getTranslator().get(selected.getNotes()));
		}
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(ok)){
			Account a;
			if (selected == null){
				a = new Account(model, name.getValue().toString(), startingBalance.getValue(), (Type) type.getSelectedItem());
				a.setNotes(notes.getValue().toString());
				Log.debug("Created new BudgetCategory " + a);

				model.addAccount(a);
			}
			else {
				a = selected;
				a.setName(name.getValue().toString());
				a.setStartingBalance(startingBalance.getValue());
				a.setType((Type) type.getSelectedItem());
				a.setNotes(notes.getValue().toString());
			}

			System.out.println(model);
			closeWindow();
		}
		else if (e.getSource().equals(cancel)){
			closeWindow();
		}
		else if (e.getSource().equals(type)){
			updateButtons();
		}
	}

	private class TypeComboBoxModel extends DefaultComboBoxModel {
		private static final long serialVersionUID = 0; 

		private final List<Type> availableParents;
		private int selectedIndex = 0;

		public TypeComboBoxModel(DataModel model) {
			availableParents = model.getTypes();
		}

		public Object getSelectedItem() {
			return availableParents.get(selectedIndex);
		}

		public void setSelectedItem(Object arg0) {
			selectedIndex = availableParents.indexOf(arg0);
		}

		public Object getElementAt(int arg0) {
			return availableParents.get(arg0);
		}

		public int getSize() {
			return availableParents.size();
		}
	}
}
