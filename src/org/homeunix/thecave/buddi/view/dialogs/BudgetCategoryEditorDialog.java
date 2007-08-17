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
import java.util.LinkedList;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;

import org.homeunix.thecave.buddi.i18n.BuddiKeys;
import org.homeunix.thecave.buddi.i18n.keys.BudgetFrameKeys;
import org.homeunix.thecave.buddi.i18n.keys.ButtonKeys;
import org.homeunix.thecave.buddi.model.BudgetCategory;
import org.homeunix.thecave.buddi.model.DataModel;
import org.homeunix.thecave.buddi.model.prefs.PrefsModel;
import org.homeunix.thecave.buddi.util.InternalFormatter;
import org.homeunix.thecave.buddi.view.BudgetFrame;
import org.homeunix.thecave.moss.data.list.CompositeList;
import org.homeunix.thecave.moss.swing.hint.JHintTextArea;
import org.homeunix.thecave.moss.swing.hint.JHintTextField;
import org.homeunix.thecave.moss.swing.window.MossDialog;
import org.homeunix.thecave.moss.util.Log;
import org.homeunix.thecave.moss.util.OperatingSystemUtil;

public class BudgetCategoryEditorDialog extends MossDialog implements ActionListener {

	public static final long serialVersionUID = 0;

	private final JHintTextField name;
	private final JComboBox parent;
	private final JRadioButton income;
	private final JRadioButton expense;
	private final JHintTextArea notes;

	private final JButton ok;
	private final JButton cancel;

	private final ParentComboBoxModel parentComboBoxModel;

	private final BudgetCategory selected;
	
	private final DataModel model;

	public BudgetCategoryEditorDialog(BudgetFrame frame, DataModel model, BudgetCategory selected) {
		super(frame);

		this.selected = selected;
		this.model = model;

		name = new JHintTextField(PrefsModel.getInstance().getTranslator().get(BuddiKeys.HINT_NAME));
		parentComboBoxModel = new ParentComboBoxModel(model);
		parent = new JComboBox(parentComboBoxModel);
		income = new JRadioButton(PrefsModel.getInstance().getTranslator().get(BudgetFrameKeys.BUDGET_EDITOR_INCOME));
		expense = new JRadioButton(PrefsModel.getInstance().getTranslator().get(BudgetFrameKeys.BUDGET_EDITOR_EXPENSE));
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

		textPanelLeft.add(new JLabel(PrefsModel.getInstance().getTranslator().get(BudgetFrameKeys.BUDGET_EDITOR_NAME)));
		textPanelLeft.add(new JLabel(PrefsModel.getInstance().getTranslator().get(BudgetFrameKeys.BUDGET_EDITOR_PARENT)));
		textPanelLeft.add(new JLabel(PrefsModel.getInstance().getTranslator().get(BudgetFrameKeys.BUDGET_EDITOR_TYPE)));
		textPanelLeft.add(new JLabel(""));
		textPanelLeft.add(new JLabel(PrefsModel.getInstance().getTranslator().get(BudgetFrameKeys.BUDGET_EDITOR_NOTES)));

		textPanelRight.add(name);
		textPanelRight.add(parent);
		textPanelRight.add(income);
		textPanelRight.add(expense);
		textPanelRight.add(new JScrollPane(notes));

		ButtonGroup group = new ButtonGroup();
		group.add(income);
		group.add(expense);

		ok.setPreferredSize(InternalFormatter.getButtonSize(ok));
		cancel.setPreferredSize(InternalFormatter.getButtonSize(cancel));

		ok.addActionListener(this);
		cancel.addActionListener(this);
		parent.addActionListener(this);
		
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
		parent.addFocusListener(focusListener);
		income.addFocusListener(focusListener);
		expense.addFocusListener(focusListener);
		notes.addFocusListener(focusListener);
		
		
		parent.setRenderer(new DefaultListCellRenderer(){
			private static final long serialVersionUID = 0;

			@Override
			public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
				super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

				if (value == null)
					this.setText(PrefsModel.getInstance().getTranslator().get(BuddiKeys.NO_PARENT));
				else if (value instanceof BudgetCategory)
					this.setText(PrefsModel.getInstance().getTranslator().get(((BudgetCategory) value).getFullName()));
				else
					this.setText(" ");

				return this;
			}
		});

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
		
		income.setEnabled(parent.getSelectedItem() == null);
		expense.setEnabled(parent.getSelectedItem() == null);

		if (parent.getSelectedItem() != null){
			BudgetCategory bc = (BudgetCategory) parent.getSelectedItem();
			income.setSelected(bc.isIncome());
			expense.setSelected(!bc.isIncome());
		}
	}

	public void updateContent() {
		super.updateContent();
		
		if (selected == null){
			name.setValue("");
			expense.setSelected(true);
			parent.setSelectedItem(null);
			notes.setValue("");
		}
		else {
			name.setValue(PrefsModel.getInstance().getTranslator().get(selected.getName()));
			if (selected.isIncome())
				income.setSelected(true);
			else
				expense.setSelected(true);
			parent.setSelectedItem(selected.getParent());
			notes.setValue(PrefsModel.getInstance().getTranslator().get(selected.getNotes()));
		}
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(ok)){
			BudgetCategory bc;
			if (selected == null){
				bc = new BudgetCategory(model, name.getValue().toString(), income.isSelected());
				bc.setParent((BudgetCategory) parent.getSelectedItem());
				bc.setNotes(notes.getValue().toString());
				Log.debug("Created new BudgetCategory " + bc);

				model.addBudgetCategory(bc);
			}
			else {
				bc = selected;
				bc.setName(name.getValue().toString());
				bc.setParent((BudgetCategory) parent.getSelectedItem());
				bc.setIncome(income.isSelected());
				bc.setNotes(notes.getValue().toString());
			}

			System.out.println(model);
			closeWindow();
		}
		else if (e.getSource().equals(cancel)){
			closeWindow();
		}
		else if (e.getSource().equals(parent)){
			updateButtons();
		}
	}

	private class ParentComboBoxModel extends DefaultComboBoxModel {
		private static final long serialVersionUID = 0; 

		private final List<BudgetCategory> availableParents;
		private int selectedIndex = 0;

		public ParentComboBoxModel(DataModel model) {
			List<BudgetCategory> blank = new LinkedList<BudgetCategory>();
			blank.add(null);
			List<List<BudgetCategory>> allLists = new LinkedList<List<BudgetCategory>>();
			allLists.add(blank);
			allLists.add(model.getBudgetCategories());
			availableParents = new CompositeList<BudgetCategory>(allLists, true);
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
