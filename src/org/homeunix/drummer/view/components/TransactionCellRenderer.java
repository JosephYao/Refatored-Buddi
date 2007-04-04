/*
 * Created on Sep 8, 2006 by wyatt
 */
package org.homeunix.drummer.view.components;

import java.awt.Component;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import org.homeunix.drummer.Const;
import org.homeunix.drummer.controller.Translate;
import org.homeunix.drummer.controller.TranslateKeys;
import org.homeunix.drummer.model.Account;
import org.homeunix.drummer.model.Transaction;
import org.homeunix.drummer.prefs.PrefsInstance;
import org.homeunix.thecave.moss.util.Formatter;
import org.homeunix.thecave.moss.util.OperatingSystemUtil;

/**
 * The cell renderer for TransactionsFrame.  On a Mac, we use Quaqua to
 * make this look pretty; on a non Mac, we have to do it ourselves.
 * 
 * @author wyatt
 *
 */
public class TransactionCellRenderer extends JLabel implements ListCellRenderer {
	public static final long serialVersionUID = 0;
	private static StringBuilder sb = new StringBuilder();
	private Account account;

	/**
	 * Creates a new TransactionCellRenderer object
	 */
	public TransactionCellRenderer(){
		if (OperatingSystemUtil.isMac()){
			this.putClientProperty("Quaqua.Component.visualMargin", new Insets(0,0,0,0));
		}
		else {
			this.setOpaque(true);
		}
	}

	/* (non-Javadoc)
	 * @see javax.swing.ListCellRenderer#getListCellRendererComponent(javax.swing.JList, java.lang.Object, int, boolean, boolean)
	 */
	public Component getListCellRendererComponent(JList list, Object obj, int index, boolean isSelected, boolean cellHasFocus) {
		if (obj != null){
			Transaction transaction = (Transaction) obj;
			setTransaction(transaction, list.getWidth());
		}
		else{
			setTransaction(null, list.getWidth());
		}

		if (isSelected){
			this.setOpaque(true);
			this.setBackground(Const.COLOR_SELECTED);
			this.setForeground(Const.COLOR_SELECTED_TEXT);
		}
		else{
			this.setForeground(Const.COLOR_UNSELECTED_TEXT);
			
			if (index % 2 == 0)
				this.setBackground(Const.COLOR_EVEN_ROW);
			else
				this.setBackground(Const.COLOR_ODD_ROW);
		}

		return this;
	}

	/**
	 * Draws the transaction in HTML in the JLabel.
	 * The table is organized as follows:
	 * 20%	80%
	 * 5% 45%	15% 15%	20%
	 * 
	 * The width of the entire table is determined by the width of the JList.
	 * @param transaction
	 * @param width
	 */
	private void setTransaction(Transaction transaction, int width){
		if (sb.length() > 0)
			sb.delete(0, sb.length());

		sb.append("<html><table width='" + (width - 200) + "px'><tr><td colspan='4' width='20%'>");
//		sb.append("<html><table width='500px'><tr><td colspan='4' width='20%'>");
		if (transaction != null) {
			sb.append(Formatter.getInstance().getDateFormat().format(transaction.getDate()));
		}
		
		sb.append("</td><td colspan='16' width='80%'>");
		if (transaction != null) {
			sb.append(Formatter.getInstance().getLengthFormat(width / 9).format(transaction.getDescription()));
		}
		else {
			sb.append("<font color='gray'>")
			.append(Translate.getInstance().get(TranslateKeys.NEW_TRANSACTION))
			.append("</font>");
		}

		sb.append("</td></tr><tr><td colspan='1' width='5%'>");
		if (transaction != null){
			if (PrefsInstance.getInstance().getPrefs().isShowAdvanced()){
				sb.append((transaction.isCleared() ? "<font color='green'>" + Translate.getInstance().get(TranslateKeys.CLEARED_SHORT) + " </font>" : "  "));
				sb.append((transaction.isReconciled() ? "<font color='green'>" + Translate.getInstance().get(TranslateKeys.RECONCILED_SHORT) + " </font>" : ""));
			}
		}
		
		sb.append("</td><td colspan='9' width='45%'>");
		if (transaction != null){
			sb.append(Formatter.getInstance().getLengthFormat(width / 16).format(
					transaction.getFrom() 
					+ " " 
					+ Translate.getInstance().get(TranslateKeys.TO)
					+ " "
					+ transaction.getTo()
			));	
		}

		
		//We have two columns here - one for debits, one for credits.
		// Debits is on the left, credits on the right.
		sb.append("</td><td align='right' colspan='3' width='15%'>");
		if (transaction != null){
			if (account != null
					&& transaction.getFrom() != null
					&& transaction.getFrom().equals(account)){
				sb.append("<font color='red'>")
				.append(Translate.getFormattedCurrency(transaction.getAmount()))
				.append("</font>");
			}
		}

		sb.append("</td><td align='right' colspan='3' width='15%'>");
		if (transaction != null){
			if (account != null
					&& transaction.getTo() != null
					&& transaction.getTo().equals(account)){
				sb.append(Translate.getFormattedCurrency(transaction.getAmount()));
			}
		}

		sb.append("</td><td align='right' colspan='4' width='20%'>");
		if (transaction != null){
			long balanceValue;
			if (account != null){
				if (transaction.getFrom() instanceof Account 
						&& transaction.getFrom().equals(account))
					balanceValue = transaction.getBalanceFrom();
				else
					balanceValue = transaction.getBalanceTo();

				sb.append("<b>");
				if (balanceValue < 0){
					sb.append("<font color='red'>");
					if (balanceValue <= 0 && balanceValue != 0)
						balanceValue *= -1;
					sb.append(Translate.getFormattedCurrency(balanceValue))
					.append("</font>");
				}
				else{
					sb.append(Translate.getFormattedCurrency(balanceValue));
				}
				sb.append("</b>");
			}
		}
		
		sb.append("</td></tr></table></html>");

		this.setText(sb.toString());
	}

	public void setAccount(Account account) {
		this.account = account;
	}
}
