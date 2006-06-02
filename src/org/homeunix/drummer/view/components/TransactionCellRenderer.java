/*
 * Created on May 8, 2006 by wyatt
 * 
 * A utility class which allows display and editing of transactions.  When used 
 * as a cell renderer, it is read only; when used as a component, the user
 * can make changes.
 */
package org.homeunix.drummer.view.components;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.util.Date;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;

import org.homeunix.drummer.Buddi;
import org.homeunix.drummer.TranslateKeys;
import org.homeunix.drummer.Translate;
import org.homeunix.drummer.model.Account;
import org.homeunix.drummer.model.Transaction;
import org.homeunix.drummer.util.Formatter;

public class TransactionCellRenderer extends JPanel implements ListCellRenderer {
	public static final long serialVersionUID = 0;
	
	private final Vector<JComponent> components;
	private final JLabel date;
	private final JLabel number;
	private final JLabel description;
	private final JDecimalLabel amount;
	private final JLabel transferTo;
	private final JLabel transferFrom;
	private final JDecimalLabel balance;
	private final JLabel memo;
	
	private Account account;
		
	public TransactionCellRenderer(){
		date = new JLabel();
		number = new JLabel();
		description = new JLabel();
		amount = new JDecimalLabel();
		balance = new JDecimalLabel();
		transferTo = new JLabel();
		transferFrom = new JLabel();
		memo = new JLabel();
		components = new Vector<JComponent>();
		
		components.add(date);
		components.add(number);
		components.add(description);
		components.add(amount);
		components.add(transferTo);
		components.add(transferFrom);
		components.add(balance);
		components.add(memo);
		components.add(this);

		JPanel topPanel = new JPanel(new GridBagLayout());
		JPanel bottomPanel = new JPanel(new GridBagLayout());
		this.setLayout(new GridLayout(2, 4));
						
		this.add(date);
		this.add(amount);
		this.add(transferFrom);
		this.add(transferTo);
		this.add(number);
		this.add(description);
		this.add(memo);
		this.add(balance);
		
		if (Buddi.isMac()){
			this.putClientProperty("Quaqua.Component.visualMargin", new Insets(0,0,0,0));
			bottomPanel.putClientProperty("Quaqua.Component.visualMargin", new Insets(0,0,0,0));
			topPanel.putClientProperty("Quaqua.Component.visualMargin", new Insets(0,0,0,0));
			for (JComponent c : components) {
				c.putClientProperty("Quaqua.Component.visualMargin", new Insets(0,0,0,0));
			}
		}
	}
	
	
	
	public Component getListCellRendererComponent(JList list, Object obj, int index, boolean isSelected, boolean cellHasFocus) {
		if (obj instanceof Transaction) {
			for (JComponent c : components) {
				c.setForeground(Color.BLACK);	
			}

			Transaction transaction = (Transaction) obj;
			setTransaction(transaction);
		}
		else{
			for (JComponent c : components) {
				c.setForeground(Color.GRAY);	
			}
			
			clearTransaction();
		}
			
		if(isSelected)
			this.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		else
			this.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
		
		
		return this;
	}
	
	private void clearTransaction(){
		date.setText(Formatter.getInstance().getLengthFormat().format(
				Formatter.getInstance().getDateFormat().format(new Date()))
		);
		number.setText("");
		description.setText(Translate.inst().get(TranslateKeys.NEW_TRANSACTION));
		balance.setText("");
		memo.setText("");
		amount.setText("");
		transferFrom.setText("");
		transferTo.setText("");
	}
	
	private void setTransaction(Transaction transaction){
		date.setText(Formatter.getInstance().getLengthFormat().format(
				Formatter.getInstance().getDateFormat().format(transaction.getDate()))
		);
		
		number.setText(Formatter.getInstance().getLengthFormat().format(transaction.getNumber()));
		description.setText(Formatter.getInstance().getLengthFormat().format(transaction.getDescription()));
		balance.setText("");
		memo.setText(Formatter.getInstance().getLengthFormat().format(transaction.getMemo()));

		amount.setValue(transaction.getAmount());
		transferFrom.setText(Formatter.getInstance().getLengthFormat().format(transaction.getFrom().toString()));
		transferTo.setText(Formatter.getInstance().getLengthFormat().format(transaction.getTo().toString()));
		
		
		long balanceValue;
		if (account != null){
			if (transaction.getFrom() instanceof Account 
					&& transaction.getFrom().equals(account))
				balanceValue = transaction.getBalanceFrom();
			else
				balanceValue = transaction.getBalanceTo();
			
			if (balanceValue < 0){
				balance.setForeground(Color.RED);
				if (balanceValue <= 0 && balanceValue != 0)
					balanceValue *= -1;
			}
			else
				balance.setForeground(Color.BLACK);
			
			balance.setValue(balanceValue);
		}
		else{
			//If we have not set an account, we don't show the balance.
		}
		

//		balance.setValue(balanceValue);
	}

	public void setAccount(Account account) {
		this.account = account;
	}
}