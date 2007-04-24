/*
 * Created on Sep 8, 2006 by wyatt
 */
package org.homeunix.drummer.view.components;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
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
	private final Account account;
	private Transaction transaction;

	private boolean showAdvanced = PrefsInstance.getInstance().getPrefs().isShowAdvanced();
	private int lastWidth;
	private int descriptionLength = 50, toFromLength = 40;

	private static final Color GREEN = new Color(0, 128, 0);

	/**
	 * Creates a new TransactionCellRenderer object
	 */
	public TransactionCellRenderer(Account account){
		this.account = account;

		if (OperatingSystemUtil.isMac()){
			this.putClientProperty("Quaqua.Component.visualMargin", new Insets(0,0,0,0));
		}
		else {
			this.setOpaque(true);
		}
		
		//Make this big enough to have two lines of text.  Since the 
		// FontMetrics object is not yet initialized, we have to guess
		// the correct size, based on JLabel size.
		this.setPreferredSize(new Dimension(200, (int) new JLabel("<html>A<br>B</html>").getPreferredSize().getHeight()));
	}

	@Override
	public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
		if (value instanceof Transaction){
			transaction = (Transaction) value;
		}
		else {
			transaction = null;
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

			//Make this big enough to have two lines of text.
//			this.setPreferredSize(new Dimension(200, (this.getGraphics() != null ? (int) (this.getGraphics().getFontMetrics().getHeight() * 2.2) : 40)));
		}

		return super.getListCellRendererComponent(list, "", index, isSelected, cellHasFocus);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		int height = this.getHeight();
		int width = this.getWidth();

		if (transaction != null){
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

//	public void setAccount(Account account) {
//	this.account = account;
//	}
}
