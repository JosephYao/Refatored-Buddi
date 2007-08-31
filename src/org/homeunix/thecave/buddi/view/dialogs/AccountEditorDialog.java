/*
 * Created on Aug 6, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.view.dialogs;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
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
import org.homeunix.thecave.buddi.model.AccountType;
import org.homeunix.thecave.buddi.model.Document;
import org.homeunix.thecave.buddi.model.exception.ModelException;
import org.homeunix.thecave.buddi.model.impl.ModelFactory;
import org.homeunix.thecave.buddi.model.prefs.PrefsModel;
import org.homeunix.thecave.buddi.util.InternalFormatter;
import org.homeunix.thecave.buddi.view.MainFrame;
import org.homeunix.thecave.moss.swing.MossDecimalField;
import org.homeunix.thecave.moss.swing.MossDialog;
import org.homeunix.thecave.moss.swing.MossHintTextArea;
import org.homeunix.thecave.moss.swing.MossHintTextField;
import org.homeunix.thecave.moss.util.Log;
import org.homeunix.thecave.moss.util.OperatingSystemUtil;

public class AccountEditorDialog extends MossDialog implements ActionListener {

	public static final long serialVersionUID = 0;

	private final MossHintTextField name;
	private final JComboBox type;
	private final MossDecimalField startingBalance;
	private final MossHintTextArea notes;

	private final JButton ok;
	private final JButton cancel;

	private final TypeComboBoxModel typeComboBoxModel;

	private final Account selected;

	private final Document model;

	public AccountEditorDialog(MainFrame frame, Document model, Account selected) {
		super(frame);

		this.selected = selected;
		this.model = model;

		name = new MossHintTextField(PrefsModel.getInstance().getTranslator().get(BuddiKeys.HINT_NAME));
		typeComboBoxModel = new TypeComboBoxModel(model);
		type = new JComboBox(typeComboBoxModel);
		startingBalance = new MossDecimalField(true);
		notes = new MossHintTextArea(PrefsModel.getInstance().getTranslator().get(BuddiKeys.HINT_NOTES));

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
		
		textPanelRight.add(name);
		textPanelRight.add(type);
		textPanelRight.add(startingBalance);
		
		JScrollPane notesScroller = new JScrollPane(notes);
		notesScroller.setPreferredSize(new Dimension(150, 75));		
		textPanel.add(notesScroller, BorderLayout.SOUTH);

		ok.setPreferredSize(InternalFormatter.getButtonSize(ok));
		cancel.setPreferredSize(InternalFormatter.getButtonSize(cancel));

		ok.addActionListener(this);
		cancel.addActionListener(this);

		type.addActionListener(this);

		type.setRenderer(new DefaultListCellRenderer(){
			private static final long serialVersionUID = 0;

			@Override
			public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
				super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

				if (value instanceof AccountType)
					this.setText(PrefsModel.getInstance().getTranslator().get(((AccountType) value).getName()));
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
			try {
				if (selected == null){
					a = ModelFactory.createAccount(name.getValue().toString(), (AccountType) type.getSelectedItem());
					a.setStartingBalance(startingBalance.getValue());
					a.setNotes(notes.getValue().toString());
					Log.debug("Created new BudgetCategory " + a);

					model.addAccount(a);
				}
				else {
					a = selected;
					a.setName(name.getValue().toString());
					a.setStartingBalance(startingBalance.getValue());
					a.setType((AccountType) type.getSelectedItem());
					a.setNotes(notes.getValue().toString());
				}
				a.updateBalance();
			}
			catch (ModelException me){
				Log.error("Error adding account", me);
			}


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

		private final List<AccountType> availableParents;
		private int selectedIndex = 0;

		public TypeComboBoxModel(Document model) {
			availableParents = model.getAccountTypes();
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
