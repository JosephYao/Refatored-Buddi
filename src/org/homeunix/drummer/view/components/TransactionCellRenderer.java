/*
 * Created on Sep 8, 2006 by wyatt
 */
package org.homeunix.drummer.view.components;

import java.awt.Color;
import java.awt.Component;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Insets;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;

import org.homeunix.drummer.Const;
import org.homeunix.drummer.controller.Translate;
import org.homeunix.drummer.controller.TranslateKeys;
import org.homeunix.drummer.model.Account;
import org.homeunix.drummer.model.Transaction;
import org.homeunix.drummer.prefs.PrefsInstance;
import org.homeunix.drummer.util.FormatterWrapper;
import org.homeunix.thecave.moss.util.Formatter;
import org.homeunix.thecave.moss.util.Log;
import org.homeunix.thecave.moss.util.OperatingSystemUtil;

/**
 * The cell renderer for TransactionsFrame.  On a Mac, we use Quaqua to
 * make this look pretty; on a non Mac, we have to do it ourselves.
 * 
 * @author wyatt
 *
 */
public class TransactionCellRenderer extends DefaultListCellRenderer {
	public static final long serialVersionUID = 0;
//	private static StringBuilder sb = new StringBuilder();
	private Account account;
	private Transaction transaction;
	
	private boolean showAdvanced = PrefsInstance.getInstance().getPrefs().isShowAdvanced();
//	private boolean isSelected; 
	private int lastWidth;
	private int descriptionLength, toFromLength;
	
	private static final Color GREEN = new Color(0, 128, 0);

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
	
		//Make this big enough to have two lines of text.
		this.setPreferredSize(new JLabel("<html>A<br>B</html>").getPreferredSize());
	}
	
	@Override
	public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
		if (value instanceof Transaction){
			transaction = (Transaction) value;
		}
		else {
			transaction = null;
			System.out.println("Null");
		}
		
		//If the size has changed, adjust the allowed lengths of strings
		// We do this by creating a 'worst case' string, consisting of
		// X's.  We measure the length of this string, in the current
		// font and size, against the available width which we have.
		if (this.lastWidth != list.getWidth() && this.getGraphics() != null){
			//On the top line, we allocate 150px for date; the description
			// can take up to (width - 150)px.
			FontMetrics fm = this.getGraphics().getFontMetrics();
			String test = "XXXXX";
			for(; fm.stringWidth(test) < (list.getWidth() - 150); test += "X"){
				descriptionLength = test.length();
			}
			
			//On the bottom line, we allocate 300px for all of the
			// balances, and 50 for the C R; 
			// We can take up to (width - 350)px for the toFrom field.  
			test = "XXX";
			for(; fm.stringWidth(test) < (list.getWidth() - 350); test += "X"){
				toFromLength = test.length();
			}
			
			if (Const.DEVEL) Log.debug("Recalculated string sizes to " + descriptionLength + " and " + toFromLength);
			
			lastWidth = list.getWidth();
		}
		
//		this.isSelected = isSelected;
		
		return super.getListCellRendererComponent(list, "", index, isSelected, cellHasFocus);
	}

	/* (non-Javadoc)
	 * @see javax.swing.ListCellRenderer#getListCellRendererComponent(javax.swing.JList, java.lang.Object, int, boolean, boolean)
	 */
//	public Component getListCellRendererComponent(JList list, Object obj, int index, boolean isSelected, boolean cellHasFocus) {
//		Transaction transaction = (Transaction) obj;
//		this.transaction = transaction;
//
//		if (isSelected){
//			this.setOpaque(true);
//			this.setBackground(Const.COLOR_SELECTED);
//			this.setForeground(Const.COLOR_SELECTED_TEXT);
//		}
//		else{
//			this.setForeground(Const.COLOR_UNSELECTED_TEXT);
//			
//			if (index % 2 == 0)
//				this.setBackground(Const.COLOR_EVEN_ROW);
//			else
//				this.setBackground(Const.COLOR_ODD_ROW);
//		}
//
//		return this;
//	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		int height = this.getHeight();
		int width = this.getWidth();
		
		if (transaction != null){
//			Color textColor = (isSelected ? Const.COLOR_SELECTED_TEXT : Const.COLOR_UNSELECTED_TEXT);
			Color textColor = g.getColor();
			
			
			//Date
			g.drawString(FormatterWrapper.getDateFormat().format(transaction.getDate()), 10, height / 2 - 5);
			
			//Description
			g.drawString(Formatter.getLengthFormat(descriptionLength).format(transaction.getDescription()), 150, height / 2 - 5);
			
			//Cleared and Reconciled
			if (showAdvanced){
				g.setColor(GREEN);
				if (transaction.isCleared())
					g.drawString(Translate.getInstance().get(TranslateKeys.SHORT_CLEARED), 20, height - 5);
				if (transaction.isReconciled())
					g.drawString(Translate.getInstance().get(TranslateKeys.SHORT_RECONCILED), 30, height - 5);
				g.setColor(textColor);
			}
			
			//To / From sources
			g.drawString(Formatter.getLengthFormat(toFromLength).format(transaction.getFrom() + " " + Translate.getInstance().get(TranslateKeys.TO) + " " + transaction.getTo()), 50, height - 5);

			//Left column
			if (account != null
					&& transaction.getFrom() != null
					&& transaction.getFrom().equals(account)){
				g.setColor(FormatterWrapper.isRed(transaction, transaction.getTo().equals(account)) ? Color.RED : textColor);
				g.drawString(FormatterWrapper.getFormattedCurrency(transaction.getAmount()), width - 300, height - 5);
				g.setColor(textColor);
			}
			//Right Column
			if (account != null
					&& transaction.getTo() != null
					&& transaction.getTo().equals(account)){
				g.setColor(FormatterWrapper.isRed(transaction, transaction.getTo().equals(account)) ? Color.RED : textColor);
				g.drawString(FormatterWrapper.getFormattedCurrency(transaction.getAmount()), width - 200, height - 5);
				g.setColor(textColor);
			}
			
			//Balance
			if (transaction != null){
			long balanceValue;
			if (account != null){
				if (transaction.getFrom() instanceof Account 
						&& transaction.getFrom().equals(account))
					balanceValue = transaction.getBalanceFrom();
				else
					balanceValue = transaction.getBalanceTo();

				g.setColor(FormatterWrapper.isRed(account, balanceValue) ? Color.RED : textColor);
				g.drawString(FormatterWrapper.getFormattedCurrency(balanceValue, account.isCredit()), width - 100, height - 5);
				g.setColor(textColor);
			}
		}

		}
	}

			
//			g.drawString(Translate.getInstance().get(TranslateKeys.SHORT_RECONCILED), 30, height - 5);
//			
//			g.drawString(Translate.getInstance().get(TranslateKeys.SHORT_RECONCILED), 30, height - 5);
//			
//		}
//	}
	
//	/**
//	 * Draws the transaction in HTML in the JLabel.
//	 * The table is organized as follows:
//	 * 20%	80%
//	 * 5% 45%	15% 15%	20%
//	 * 
//	 * The width of the entire table is determined by the width of the JList.
//	 * @param transaction
//	 * @param width
//	 */
//	private void setTransaction(Transaction transaction, int width){
//		if (sb.length() > 0)
//			sb.delete(0, sb.length());
//
//		sb.append("<html><table width='" + (width - 200) + "px'><tr><td colspan='4' width='20%'>");
////		sb.append("<html><table width='500px'><tr><td colspan='4' width='20%'>");
//		if (transaction != null) {
//			sb.append(FormatterWrapper.getDateFormat().format(transaction.getDate()));
//		}
//		
//		sb.append("</td><td colspan='16' width='80%'>");
//		if (transaction != null) {
//			sb.append(Formatter.getLengthFormat(width / 9).format(transaction.getDescription()));
//		}
//		else {
//			sb.append("<font color='gray'>")
//			.append(Translate.getInstance().get(TranslateKeys.NEW_TRANSACTION))
//			.append("</font>");
//		}
//
//		sb.append("</td></tr><tr><td colspan='1' width='5%'>");
//		if (transaction != null){
//			if (PrefsInstance.getInstance().getPrefs().isShowAdvanced()){
//				sb.append((transaction.isCleared() ? "<font color='green'>" + Translate.getInstance().get(TranslateKeys.SHORT_CLEARED) + " </font>" : "  "));
//				sb.append((transaction.isReconciled() ? "<font color='green'>" + Translate.getInstance().get(TranslateKeys.SHORT_RECONCILED) + " </font>" : ""));
//			}
//		}
//		
//		sb.append("</td><td colspan='9' width='45%'>");
//		if (transaction != null){
//			sb.append(Formatter.getLengthFormat(width / 20).format(
//					transaction.getFrom() 
//					+ " " 
//					+ Translate.getInstance().get(TranslateKeys.TO)
//					+ " "
//					+ transaction.getTo()
//			));	
//		}
//
//		
//		//We have two columns here - one for debits, one for credits.
//		// Debits is on the left, credits on the right.
//		sb.append("</td><td align='right' colspan='3' width='15%'>");
//		if (transaction != null){
//			if (account != null
//					&& transaction.getFrom() != null
//					&& transaction.getFrom().equals(account)){
//				sb.append(FormatterWrapper.getFormattedCurrencyForTransaction(transaction.getAmount(), false));
////				sb.append("<font color='red'>")
////				.append(Translate.getFormattedCurrency(transaction.getAmount(), false))
////				.append("</font>");
//			}
//		}
//
//		sb.append("</td><td align='right' colspan='3' width='15%'>");
//		if (transaction != null){
//			if (account != null
//					&& transaction.getTo() != null
//					&& transaction.getTo().equals(account)){
////				sb.append(Translate.getFormattedCurrency(transaction.getAmount(), false));
//				sb.append(FormatterWrapper.getFormattedCurrencyForTransaction(transaction.getAmount(), true));
//			}
//		}
//
//		sb.append("</td><td align='right' colspan='4' width='20%'>");
//		if (transaction != null){
//			long balanceValue;
//			if (account != null){
//				if (transaction.getFrom() instanceof Account 
//						&& transaction.getFrom().equals(account))
//					balanceValue = transaction.getBalanceFrom();
//				else
//					balanceValue = transaction.getBalanceTo();
//
//				sb.append("<b>");
//				sb.append(FormatterWrapper.getFormattedCurrencyForAccount(balanceValue, account.getAccountType().isCredit()));
////				if (balanceValue < 0){
////					sb.append("<font color='red'>");
////					if (balanceValue <= 0 && balanceValue != 0)
////						balanceValue *= -1;
////					sb.append(FormatterWrapper.getFormattedCurrencyForAccount(balanceValue, account.getAccountType().isCredit()))
////					.append("</font>");
////				}
////				else{
////					sb.append(Translate.getFormattedCurrency(balanceValue, false));
////				}
//				sb.append("</b>");
//			}
//		}
//		
//		sb.append("</td></tr></table></html>");
//
//		this.setText(sb.toString());
//	}

	public void setAccount(Account account) {
		this.account = account;
	}
}
