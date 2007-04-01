/*
 * Created on May 6, 2006 by wyatt
 */
package org.homeunix.drummer.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import org.homeunix.drummer.controller.Translate;
import org.homeunix.drummer.controller.TranslateKeys;
import org.homeunix.thecave.moss.gui.abstractwindows.AbstractDialog;
import org.homeunix.thecave.moss.gui.abstractwindows.StandardContainer;
import org.homeunix.thecave.moss.util.OperatingSystemUtil;

public abstract class TypesListDialogLayout extends AbstractBuddiDialog {
	public static final long serialVersionUID = 0;

	protected final JButton doneButton;
	protected final JButton newButton;
	protected final JButton editButton;

	protected final JList list;

	protected TypesListDialogLayout(Frame owner){
		super(owner);

		doneButton = new JButton(Translate.getInstance().get(TranslateKeys.DONE));
		newButton = new JButton(Translate.getInstance().get(TranslateKeys.NEW));
		editButton = new JButton(Translate.getInstance().get(TranslateKeys.EDIT));

		list = new JList();
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		Dimension buttonSize = new Dimension(Math.max(100, editButton.getPreferredSize().width), editButton.getPreferredSize().height);
		doneButton.setPreferredSize(buttonSize);
		newButton.setPreferredSize(buttonSize);
		editButton.setPreferredSize(buttonSize);

		JScrollPane listScroller = new JScrollPane(list);

		JPanel scrollBorderPanel = new JPanel(new BorderLayout());
		scrollBorderPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
		scrollBorderPanel.add(listScroller, BorderLayout.CENTER);

		JPanel scrollPanel = new JPanel(new BorderLayout());
		scrollPanel.setBorder(BorderFactory.createTitledBorder(Translate.getInstance().get(TranslateKeys.SCHEDULED_ACTIONS)));
		scrollPanel.add(scrollBorderPanel, BorderLayout.CENTER);

		JPanel editTransactionsButtonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		editTransactionsButtonPanel.add(newButton);
		editTransactionsButtonPanel.add(editButton);

		scrollPanel.add(editTransactionsButtonPanel, BorderLayout.SOUTH);

		JPanel mainPanel = new JPanel(); 
		mainPanel.setLayout(new BorderLayout());
		mainPanel.setBorder(BorderFactory.createEmptyBorder(7, 12, 12, 12));

		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		buttonPanel.add(doneButton);

		mainPanel.add(scrollPanel, BorderLayout.CENTER);
		mainPanel.add(buttonPanel, BorderLayout.SOUTH);

		this.setTitle(Translate.getInstance().get(TranslateKeys.EDIT_ACCOUNT_TYPES));
		this.setLayout(new BorderLayout());
		this.add(mainPanel);
		this.getRootPane().setDefaultButton(doneButton);

		if (OperatingSystemUtil.isMac()){
			list.putClientProperty("Quaqua.List.style", "striped");
			listScroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		}
	}

	public StandardContainer clear() {
		// TODO Auto-generated method stub
		return null;
	}

	public AbstractDialog updateButtons(){		
		editButton.setEnabled(list.getSelectedIndices().length > 0);

		return this;
	}
}
