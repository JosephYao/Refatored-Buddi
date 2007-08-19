/*
 * Created on May 6, 2006 by wyatt
 */
package org.homeunix.thecave.buddi.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.homeunix.thecave.buddi.Const;
import org.homeunix.thecave.buddi.i18n.keys.ButtonKeys;
import org.homeunix.thecave.buddi.i18n.keys.MenuKeys;
import org.homeunix.thecave.buddi.model.DataModel;
import org.homeunix.thecave.buddi.model.ScheduledTransaction;
import org.homeunix.thecave.buddi.model.swing.ScheduledTransactionTableModel;
import org.homeunix.thecave.buddi.plugin.api.util.TextFormatter;
import org.homeunix.thecave.buddi.util.InternalFormatter;
import org.homeunix.thecave.buddi.view.schedule.ScheduleEditor;
import org.homeunix.thecave.buddi.view.swing.ScheduledTransactionTableCellRenderer;
import org.homeunix.thecave.moss.swing.window.MossAssociatedDocumentFrame;
import org.homeunix.thecave.moss.swing.window.MossDocumentFrame;
import org.homeunix.thecave.moss.util.OperatingSystemUtil;
import org.jdesktop.swingx.JXTable;
import org.jdesktop.swingx.decorator.HighlighterFactory;

public class ScheduleFrame extends MossAssociatedDocumentFrame implements ActionListener {
	public static final long serialVersionUID = 0;

	private final JButton doneButton;
//	private final JButton cancelButton;
	private final JButton newButton;
	private final JButton deleteButton;

	private final ScheduleEditor scheduleEditor;

	private final JXTable list;
	private final ScheduledTransactionTableModel listModel;

	public ScheduleFrame(DataModel model, MossDocumentFrame frame){
		super(frame, "ScheduledTransactionFrame" + ((DataModel) frame.getDocument()).getUid());

		scheduleEditor = new ScheduleEditor(frame);

		doneButton = new JButton(TextFormatter.getTranslation(ButtonKeys.BUTTON_DONE));
//		cancelButton = new JButton(TextFormatter.getTranslation(ButtonKeys.BUTTON_CANCEL));
		newButton = new JButton(TextFormatter.getTranslation(ButtonKeys.BUTTON_NEW));
		deleteButton = new JButton(TextFormatter.getTranslation(ButtonKeys.BUTTON_DELETE));

		listModel = new ScheduledTransactionTableModel(model);
		list = new JXTable();
	}

	@Override
	public void updateButtons() {		
		super.updateButtons();

		deleteButton.setEnabled(list.getSelectedRows().length > 0);
	}

	@Override
	public void updateContent() {
		listModel.fireTableChanged();

		super.updateContent();
	}

	public void init() {
		scheduleEditor.loadSchedule(null);

		doneButton.setPreferredSize(InternalFormatter.getButtonSize(doneButton));
//		cancelButton.setPreferredSize(InternalFormatter.getButtonSize(cancelButton));
		newButton.setPreferredSize(InternalFormatter.getButtonSize(newButton));
		deleteButton.setPreferredSize(InternalFormatter.getButtonSize(deleteButton));

		JPanel editTransactionsButtonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		editTransactionsButtonPanel.add(newButton);
		editTransactionsButtonPanel.add(deleteButton);

		JScrollPane listScroller = new JScrollPane(list);		

		JPanel scrollPanel = new JPanel(new BorderLayout());
		scrollPanel.add(listScroller, BorderLayout.CENTER);
		scrollPanel.add(editTransactionsButtonPanel, BorderLayout.SOUTH);

		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
//		buttonPanel.add(cancelButton);
		buttonPanel.add(doneButton);

		if (OperatingSystemUtil.isMac()){
			listScroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		}

		newButton.addActionListener(this);
		deleteButton.addActionListener(this);
		doneButton.addActionListener(this);

		list.addHighlighter(HighlighterFactory.createAlternateStriping(Const.COLOR_EVEN_ROW, Const.COLOR_ODD_ROW));
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setModel(listModel);
		list.getColumn(0).setCellRenderer(new ScheduledTransactionTableCellRenderer());
		list.getColumn(0).setCellEditor(new JXTable.GenericEditor());

		list.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
			private boolean allowMessage = true;
			
			public void valueChanged(ListSelectionEvent e) {
				if (!e.getValueIsAdjusting()){
					//Save existing scheduled transactions.  If the save failed, force
					// selection back to the poorly edited transaction.
					if (allowMessage && !scheduleEditor.saveScheduledTransaction()){
						allowMessage = false;
						list.setRowSelectionInterval(listModel.getSelectedIndex(), listModel.getSelectedIndex());
						return;
					}

					if (allowMessage) {
						listModel.setSelectedScheduedTransaction(list.getSelectedRow());
						ScheduledTransaction s = (ScheduledTransaction) listModel.getValueAt(list.getSelectedRow(), -1);
						scheduleEditor.loadSchedule(s);
					}
					allowMessage = true;
//					listModel.setSelectedScheduedTransaction(list.getSelectedRow());
				}
			}
		});
//		list.addMouseListener(new MouseAdapter(){
//		@Override
//		public void mouseClicked(MouseEvent e) {
//		super.mouseClicked(e);
//		//Save existing scheduled transactions
//		modify.saveScheduledTransaction();

//		ScheduledTransaction s = (ScheduledTransaction) listModel.getValueAt(list.getSelectedRow(), -1);
//		modify.loadSchedule(s);
//		listModel.setSelectedScheduedTransaction(list.getSelectedRow());
//		}
//		});

		this.setTitle(TextFormatter.getTranslation(MenuKeys.MENU_EDIT_SCHEDULED_ACTIONS));
		this.setLayout(new BorderLayout());
		this.add(scrollPanel, BorderLayout.WEST);
		this.add(scheduleEditor, BorderLayout.CENTER);
		this.add(buttonPanel, BorderLayout.SOUTH);
		this.getRootPane().setDefaultButton(doneButton);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(newButton)){
			ScheduledTransaction s = new ScheduledTransaction((DataModel) getDocument(), "New");
			listModel.add(s);
		}
		else if (e.getSource().equals(deleteButton)){
			Object o = list.getSelectedRow();

			if (o instanceof ScheduledTransaction)
				listModel.remove((ScheduledTransaction) o);

			deleteButton.setEnabled(false);
		}
		else if (e.getSource().equals(doneButton)){
//			listModel.save();
			this.closeWindow();
		}
//		else if (e.getSource().equals(cancelButton)){
//		this.closeWindow();
//		}
	}
}
