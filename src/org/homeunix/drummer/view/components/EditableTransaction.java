/*
 * Created on May 8, 2006 by wyatt
 * 
 * A utility class which allows editing of transactions.
 */
package org.homeunix.drummer.view.components;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Date;
import java.util.Vector;

import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.homeunix.drummer.Strings;
import org.homeunix.drummer.controller.DataInstance;
import org.homeunix.drummer.controller.PrefsInstance;
import org.homeunix.drummer.model.Category;
import org.homeunix.drummer.model.Source;
import org.homeunix.drummer.model.Transaction;
import org.homeunix.drummer.util.Formatter;
import org.homeunix.drummer.util.Log;
import org.homeunix.drummer.view.components.autocomplete.AutoCompleteTextField;
import org.homeunix.drummer.view.layout.TransactionsFrameLayout;

public class EditableTransaction extends JPanel {
	public static final long serialVersionUID = 0;
	
	private final Vector<JComponent> components;

	private Transaction transaction; //Set when editing existing one; null otherwise
	
	private final JFormattedTextField date;
	private final JNumberField amount;
	private final JComboBox transferFrom;
	private final JComboBox transferTo;
	private final JTextField number;
	private final AutoCompleteTextField description;
	private final AutoCompleteTextField memo;
	
	private final DefaultComboBoxModel toModel;
	private final DefaultComboBoxModel fromModel;
		
	private TransactionsFrameLayout parent;
	
	private boolean changed = false;
		
	public EditableTransaction(TransactionsFrameLayout parent){
		this.parent = parent;
				
		date = new JFormattedTextField(Formatter.getInstance().getDateFormat());
		amount = new JNumberField(Formatter.getInstance().getDecimalFormat());
		transferFrom = new JComboBox();
		transferTo = new JComboBox();
		number = new JTextField();
		description = new AutoCompleteTextField(PrefsInstance.getInstance().getDescDict());
		memo = new AutoCompleteTextField(PrefsInstance.getInstance().getMemoDict());
		
		components = new Vector<JComponent>();
		
		toModel = new DefaultComboBoxModel();
		fromModel = new DefaultComboBoxModel();
		
		transferTo.setModel(toModel);
		transferFrom.setModel(fromModel);
		
		Dimension textDimension = new Dimension(50, date.getPreferredSize().height);
		date.setPreferredSize(textDimension);
		amount.setPreferredSize(textDimension);
		memo.setPreferredSize(textDimension);
		
		number.setPreferredSize(new Dimension(34, number.getPreferredSize().height));
		description.setPreferredSize(new Dimension(100, number.getPreferredSize().height));
		
		Dimension comboDimension = new Dimension(50, transferFrom.getPreferredSize().height);
		transferFrom.setPreferredSize(comboDimension);
		transferTo.setPreferredSize(comboDimension);
		
		date.setToolTipText(Strings.inst().get(Strings.TOOLTIP_DATE));
		amount.setToolTipText(Strings.inst().get(Strings.TOOLTIP_AMOUNT));
		transferFrom.setToolTipText(Strings.inst().get(Strings.TOOLTIP_FROM));
		transferTo.setToolTipText(Strings.inst().get(Strings.TOOLTIP_TO));
		number.setToolTipText(Strings.inst().get(Strings.TOOLTIP_NUMBER));
		description.setToolTipText(Strings.inst().get(Strings.TOOLTIP_DESC));
		memo.setToolTipText(Strings.inst().get(Strings.TOOLTIP_MEMO));
		
		JPanel topPanel = new JPanel();
		JPanel bottomPanel = new JPanel();
		topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));
		bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.X_AXIS));
		
		JPanel topRightPanel = new JPanel();
		JPanel topLeftPanel = new JPanel();
		JPanel bottomRightPanel = new JPanel();
		JPanel bottomLeftPanel = new JPanel();

		topRightPanel.setLayout(new BoxLayout(topRightPanel, BoxLayout.X_AXIS));
		topLeftPanel.setLayout(new BoxLayout(topLeftPanel, BoxLayout.X_AXIS));
		bottomRightPanel.setLayout(new BoxLayout(bottomRightPanel, BoxLayout.X_AXIS));
		bottomLeftPanel.setLayout(new BoxLayout(bottomLeftPanel, BoxLayout.X_AXIS));
		
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
				
		components.add(date);
		components.add(amount);
		components.add(transferFrom);
		components.add(transferTo);

		components.add(number);
		components.add(description);
		components.add(memo);
//		components.add(this);
		
		topLeftPanel.add(date);
		topLeftPanel.add(amount);
		
		topRightPanel.add(transferFrom);
		topRightPanel.add(new JLabel(Strings.inst().get(Strings.TO)));
		topRightPanel.add(transferTo);

		bottomLeftPanel.add(number);
		bottomLeftPanel.add(description);
		
		bottomRightPanel.add(memo);

		topPanel.add(topLeftPanel);
		topPanel.add(topRightPanel);
		bottomPanel.add(bottomLeftPanel);
		bottomPanel.add(bottomRightPanel);
		
		
		this.add(topPanel);
		this.add(bottomPanel);
		
		topRightPanel.setPreferredSize(new Dimension(400, topRightPanel.getPreferredSize().height));
		topLeftPanel.setPreferredSize(new Dimension(200, topLeftPanel.getPreferredSize().height));
		bottomRightPanel.setPreferredSize(new Dimension(350, bottomRightPanel.getPreferredSize().height));
		bottomLeftPanel.setPreferredSize(new Dimension(300, bottomLeftPanel.getPreferredSize().height));
		
		
		
		initActions();
		
		//updateContent();
	}
	
	public void setTransaction(Transaction transaction){
		//SimpleDateFormat sdf = new SimpleDateFormat(TransactionCell.dateFormat);
		date.setValue(transaction.getDate());
		
		number.setText(transaction.getNumber());
		description.setText(transaction.getDescription());
		//balance.setText("");
		memo.setText(transaction.getMemo());
		
		amount.setValue((double) transaction.getAmount() / 100.0);
		
		transferFrom.setSelectedItem(transaction.getFrom());
		transferTo.setSelectedItem(transaction.getTo());
		
		this.transaction = transaction;
		
		//Reset change flag
		setChanged(false);
	}
	
	public void clearTransaction(){
		if (date.getValue() == null)
			date.setValue(new Date());
		number.setText("");
		description.setText("");
		amount.setValue(0);
		//balance.setText("");
		transferTo.setSelectedIndex(0);
		transferFrom.setSelectedIndex(0);
		memo.setText("");
		
		date.requestFocus();
		
		//Reset change flag
		setChanged(false);
		
		//Reset transaction ID
		transaction = null;
	}
	
	protected void setChanged(){
		changed = true;
		parent.updateButtons();
	}
	
	protected void setChanged(boolean changed){
		this.changed = changed;		
	}
	
	public boolean isChanged(){
		return changed;
	}
	
	public Date getDate(){
		return (Date) date.getValue();
	}
	
	public String getNumber(){
		return number.getText();
	}
	
	public String getDescription(){
		return description.getText();
	}
	
	public Transaction getTransaction(){
		return transaction;
	}
	
	public int getAmount(){
		int amount = 0;
		
		//We record the amount in cents, to avoid decimal point issues
		amount = (int) (Double.parseDouble(this.amount.getValue().toString()) * 100);
		
		return amount;
	}
	
	public Source getTransferFrom(){
		if (transferFrom.getSelectedItem() instanceof Source) {
			return (Source) transferFrom.getSelectedItem();	
		}
		else if (transferFrom.getSelectedItem() != null)
			Log.error("Unknown object selected in TransferFrom combobox; returning null.");
		return null;
	}

	public Source getTransferTo(){
		if (transferTo.getSelectedItem() instanceof Source) {
			return (Source) transferTo.getSelectedItem();	
		}
		else if (transferTo.getSelectedItem() != null)
			Log.error("Unknown object selected in TransferTo combobox; returning null.");
		
		return null;
	}

	
	public String getMemo(){
		return memo.getText();
	}
	
	public void updateContent(){
		//Update the dropdown lists
		setEnabled(true);
		
		toModel.removeAllElements();
		fromModel.removeAllElements();
		toModel.addElement(null);
		fromModel.addElement(null);
		for (Source source : DataInstance.getInstance().getAccounts()) {
			toModel.addElement(source);
			fromModel.addElement(source);
		}
		
		for (Category c : DataInstance.getInstance().getCategories()){
			if (c.isIncome())
				fromModel.addElement(c);
			else
				toModel.addElement(c);	
		}
	}
	
	private void initActions(){
		for (JComponent c : components) {
			c.addKeyListener(new KeyAdapter() {
				public void keyTyped(KeyEvent arg0) {
					EditableTransaction.this.setChanged();
				}
			});
			
			c.addFocusListener(new FocusAdapter(){
				public void focusGained(FocusEvent arg0) {
					if (arg0.getSource() instanceof JTextField 
							|| arg0.getSource() instanceof JFormattedTextField){
						JTextField text = (JTextField) arg0.getSource();
						text.selectAll();
					}
				}
			});
		}
		
		transferFrom.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				EditableTransaction.this.setChanged();
				if (parent.getAccount() != null) {
					if (!parent.getAccount().equals(transferFrom.getSelectedItem())){
						transferTo.setSelectedItem(parent.getAccount());
					}
					
					if (parent.getAccount().equals(transferFrom.getSelectedItem())
							&& parent.getAccount().equals(transferTo.getSelectedItem())){
						transferTo.setSelectedItem(null);
					}
				}
			}
		});
		
		transferTo.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				EditableTransaction.this.setChanged();
				if (transferTo.getSelectedItem() instanceof Source) {
					if (!parent.getAccount().equals(transferTo.getSelectedItem())){
						transferFrom.setSelectedItem(parent.getAccount());
					}

					if (parent.getAccount().equals(transferFrom.getSelectedItem())
							&& parent.getAccount().equals(transferTo.getSelectedItem())){
						transferFrom.setSelectedItem(null);
					}
				}
			}
		});
	}

	@Override
	public void setEnabled(boolean arg0) {
		for (JComponent c : components) {
			c.setEnabled(arg0);
		}
		
	}
	
	public void select(){
		amount.requestFocus();
		amount.selectAll();
	}
}
