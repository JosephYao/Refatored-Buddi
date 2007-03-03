/*
 * Created on May 6, 2006 by wyatt
 */
package org.homeunix.drummer.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import org.homeunix.drummer.controller.Translate;
import org.homeunix.drummer.controller.TranslateKeys;
import org.homeunix.drummer.model.Account;
import org.homeunix.drummer.model.Transaction;
import org.homeunix.drummer.prefs.PrefsInstance;
import org.homeunix.drummer.view.components.EditableTransaction;
import org.homeunix.drummer.view.components.TransactionCellRenderer;
import org.homeunix.thecave.moss.gui.JSearchField;
import org.homeunix.thecave.moss.util.Formatter;
import org.homeunix.thecave.moss.util.OperatingSystemUtil;

public abstract class TransactionsFrameLayout extends AbstractFrame {
	public static final long serialVersionUID = 0;

	protected static final Map<Account, TransactionsFrameLayout> transactionInstances = new HashMap<Account, TransactionsFrameLayout>();

	protected final JList list;
	protected final JScrollPane listScroller;

	protected final EditableTransaction editableTransaction;
	protected final JButton recordButton;
	protected final JButton clearButton;
	protected final JButton deleteButton;
	protected final JSearchField searchField;
	protected final JComboBox dateRangeComboBox;
	protected final JLabel creditRemaining;

	public TransactionsFrameLayout(Account account){
		if (transactionInstances.get(account) != null)
			transactionInstances.get(account).setVisible(false);

		transactionInstances.put(account, this);

		//Set up the transaction list
		list = new JList(){
			public final static long serialVersionUID = 0;

			@Override
			public String getToolTipText(MouseEvent event) {
				int i = locationToIndex(event.getPoint());
				Object o = getModel().getElementAt(i);

				if (o instanceof Transaction){
					Transaction transaction = (Transaction) o;

					if (transaction != null){
						StringBuilder sb = new StringBuilder();

						sb.append("<html>");
						sb.append(transaction.getDescription());

						if (transaction.getNumber().length() > 0){
							sb.append("<br>");
							sb.append("#");
							sb.append(transaction.getNumber());
						}

						sb.append("<br>");
						sb.append(PrefsInstance.getInstance().getPrefs().getCurrencySymbol());
						sb.append(Formatter.getInstance().getDecimalFormat().format(((double) transaction.getAmount()) / 100.0));

						sb.append(transaction.getFrom())
								.append(" ")
								.append(Translate.getInstance().get(TranslateKeys.TO))
								.append(" ")
								.append(transaction.getTo());

						if (transaction.getMemo().length() > 0){
							sb.append("<br>");
							sb.append(transaction.getMemo());
						}

						sb.append("</html>");

						return sb.toString();
					}
				}

				return "";
			}
		};
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		dateRangeComboBox = new JComboBox();
		creditRemaining = new JLabel();

		TransactionCellRenderer renderer = new TransactionCellRenderer();
		renderer.setAccount(account);
		list.setCellRenderer(renderer);

		listScroller = new JScrollPane(list);

		JPanel scrollPanel = new JPanel(new BorderLayout());

		//Set up the editing portion
		editableTransaction = new EditableTransaction(this);
		editableTransaction.updateContent();

		recordButton = new JButton(Translate.getInstance().get(TranslateKeys.RECORD));
		clearButton = new JButton(Translate.getInstance().get(TranslateKeys.CLEAR));
		deleteButton = new JButton(Translate.getInstance().get(TranslateKeys.DELETE));
		searchField = new JSearchField(Translate.getInstance().get(TranslateKeys.DEFAULT_SEARCH));

		recordButton.setPreferredSize(new Dimension(Math.max(100, recordButton.getPreferredSize().width), recordButton.getPreferredSize().height));
		clearButton.setPreferredSize(new Dimension(Math.max(100, clearButton.getPreferredSize().width), clearButton.getPreferredSize().height));
		deleteButton.setPreferredSize(new Dimension(Math.max(100, deleteButton.getPreferredSize().width), deleteButton.getPreferredSize().height));
		searchField.setPreferredSize(new Dimension(160, searchField.getPreferredSize().height));
		dateRangeComboBox.setPreferredSize(new Dimension(100, dateRangeComboBox.getPreferredSize().height));

		JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		searchPanel.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
		searchPanel.add(new JLabel(Translate.getInstance().get(TranslateKeys.DATE_FILTER)));
		searchPanel.add(dateRangeComboBox);
		searchPanel.add(searchField);

		JPanel creditRemainingPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		creditRemainingPanel.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
		creditRemainingPanel.add(creditRemaining);

		JPanel topPanel = new JPanel(new BorderLayout());
		topPanel.add(searchPanel, BorderLayout.EAST);
		topPanel.add(creditRemainingPanel, BorderLayout.WEST);

		this.getRootPane().setDefaultButton(recordButton);

		JPanel buttonPanelRight = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		buttonPanelRight.add(clearButton);
		buttonPanelRight.add(recordButton);

		JPanel buttonPanelLeft = new JPanel(new FlowLayout(FlowLayout.LEFT));
		buttonPanelLeft.add(deleteButton);

		JPanel buttonPanel = new JPanel(new BorderLayout());
		buttonPanel.add(buttonPanelRight, BorderLayout.EAST);
		buttonPanel.add(buttonPanelLeft, BorderLayout.WEST);

		scrollPanel.add(topPanel, BorderLayout.NORTH);
		scrollPanel.add(listScroller, BorderLayout.CENTER);
		scrollPanel.add(editableTransaction, BorderLayout.SOUTH);

		JPanel mainPanel = new JPanel(); 
		mainPanel.setLayout(new BorderLayout());

		mainPanel.add(scrollPanel, BorderLayout.CENTER);
		mainPanel.add(buttonPanel, BorderLayout.SOUTH);

		this.setLayout(new BorderLayout());
		this.setTitle(Translate.getInstance().get(TranslateKeys.TRANSACTIONS) + " - " + account.toString());
		this.add(mainPanel, BorderLayout.CENTER);

		if (OperatingSystemUtil.isMac()){
			list.putClientProperty("Quaqua.List.style", "striped");
			listScroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
//			clearSearchField.putClientProperty("Quaqua.Button.style", "square");
			mainPanel.setBorder(BorderFactory.createEmptyBorder(7, 17, 17, 17));
			listScroller.setBorder(BorderFactory.createCompoundBorder(
					BorderFactory.createEmptyBorder(5, 10, 5, 10),
					listScroller.getBorder()));
			scrollPanel.setBorder(BorderFactory.createTitledBorder(""));
			editableTransaction.setBorder(BorderFactory.createEmptyBorder(2, 8, 5, 8));
			searchField.putClientProperty("Quaqua.Component.visualMargin", new Insets(0,0,0,0));
		}
		else {
			editableTransaction.setBorder(BorderFactory.createEmptyBorder(3, 0, 3, 0));
		}

		//Call the method to add actions to the buttons
		initActions();

		//Show the window
		openWindow();
	}

	public abstract Account getAccount();
}
