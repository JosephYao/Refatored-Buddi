/*
 * Created on Sep 8, 2006 by wyatt
 */
package org.homeunix.thecave.buddi.view.swing;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;

import org.homeunix.thecave.buddi.i18n.BuddiKeys;
import org.homeunix.thecave.buddi.model.Account;
import org.homeunix.thecave.buddi.model.Transaction;
import org.homeunix.thecave.buddi.model.prefs.PrefsModel;
import org.homeunix.thecave.buddi.plugin.api.util.TextFormatter;
import org.homeunix.thecave.buddi.util.InternalFormatter;
import org.homeunix.thecave.moss.util.Formatter;
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

	private boolean showCleared = PrefsModel.getInstance().isShowCleared();
	private boolean showReconciled = PrefsModel.getInstance().isShowReconciled();
	private final boolean simple;

	private int maxDescriptionLength = 50;//, maxToFromLength = 40;

	private static Font f;
	private static Font bold;
	private static Font italic;

	private static final Color GREEN = new Color(0, 128, 0);

	/**
	 * Creates a new TransactionCellRenderer object
	 */
	public TransactionCellRenderer(Account account, boolean simple){
		this.account = account;
		this.simple = simple;

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

//		//If the size has changed, adjust the allowed lengths of strings
//		// We do this by creating a 'worst case' string, consisting of
//		// X's.  We measure the length of this string, in the current
//		// font and size, against the available width which we have.
//		if (this.lastWidth != list.getWidth() && this.getGraphics() != null){
//			//On the top line, we allocate 150px for date; the description
//			// can take up to (width - 150)px.
//			FontMetrics fm = this.getGraphics().getFontMetrics();
//			String test = "XXXXX";
//			for(; fm.stringWidth(test) < (list.getWidth() - 150); test += "X"){
//				descriptionLength = test.length() / 2;
//				currentDescriptionLength = descriptionLength;
//			}
//
//			//On the bottom line, we allocate 300px for all of the
//			// balances, and 50 for the C R; 
//			// We can take up to (width - 300)px for the toFrom field.
//			//Since we use each of these separately, we divide by two first.
//			int widthDifference = (account == null ? 100 : 300);
//			test = "XXX";
//			for(; fm.stringWidth(test) < (list.getWidth() - widthDifference); test += "X"){
//				toFromLength = test.length();
//			}
//
//			if (Const.DEVEL) Log.debug("Recalculated string sizes to " + descriptionLength + " and " + toFromLength);
//
//			lastWidth = list.getWidth();
//
//			//Make this big enough to have two lines of text.
////			this.setPreferredSize(new Dimension(200, (this.getGraphics() != null ? (int) (this.getGraphics().getFontMetrics().getHeight() * 2.2) : 40)));
//		}
		
		maxDescriptionLength = list.getWidth() - 230;
//		maxToFromLength = list.getWidth() - (account == null ? 150 : 400);
		
		//Calculate the size every time... this will get as many characters into the description
		// as possible, with the possible problem of slowing down execution a little bit...
//		if (this.getGraphics() != null){
//			//On the top line, we allocate 150px for date; the description
//			// can take up to (width - 150)px.
//			FontMetrics fm = this.getGraphics().getFontMetrics();
//			int widthDifference = 230;
//			String test = transaction.getDescription();
//			for(int i = 5; i <= test.length() && fm.stringWidth(test.substring(0, i)) < list.getWidth() - widthDifference; i++){				
//				descriptionLength = i;
//			}
//
//			//On the bottom line, we allocate 350px for all of the
//			// balances, and 50 for the C R; 
//			// We can take up to (width - 400)px for the toFrom field.
//			widthDifference = (account == null ? 150 : 400);
//			test = transaction.getFrom().getFullName() + "       " + transaction.getTo().getFullName();
//			for(int i = 10; i <= test.length() && fm.stringWidth(test.substring(0, i)) < (list.getWidth() - widthDifference); i++){
//				toFromLength = i;
//			}
//		}
//		else {
//			descriptionLength = 100;
//			toFromLength = 100;
//		}

		return super.getListCellRendererComponent(list, "", index, isSelected, cellHasFocus);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		int height = this.getHeight();
		int width = this.getWidth();

		// Antialias text
		((Graphics2D) g).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
				RenderingHints.VALUE_TEXT_ANTIALIAS_ON );

		if (transaction != null){
			Color textColor = g.getColor();
			FontMetrics fm;
			int topRowYPos = height / 2 - 5;
			int bottomRowYPos = height - 5;

			f = g.getFont();
			if (simple) {
				bold = f;
				italic = f;
			}
			else {
				bold = new Font(f.getName(), Font.BOLD, f.getSize());
				italic = new Font(f.getName(), Font.ITALIC, f.getSize());				
			}
			
			//Get the font metrics for column alignment
			fm = this.getGraphics().getFontMetrics();
			
			//Date
			g.drawString(TextFormatter.getDateFormat().format(transaction.getDate()), 10, topRowYPos);
			
			//Description
			g.setFont(bold);
			g.drawString(
					Formatter.getStringToLength(
					transaction.getDescription(), maxDescriptionLength, fm), 
					150, 
					topRowYPos);
			g.setFont(f);

			//Cleared and Reconciled
			if (account != null){
				if (showCleared){
					g.setColor(GREEN);
					if (account.equals(transaction.getTo()) && transaction.isClearedTo()
							|| account.equals(transaction.getFrom()) && transaction.isClearedFrom())
						g.drawString(PrefsModel.getInstance().getTranslator().get(BuddiKeys.SHORT_CLEARED), 20, bottomRowYPos);
					g.setColor(textColor);
				}
				if (showReconciled){
					g.setColor(GREEN);
					if (account.equals(transaction.getTo()) && transaction.isReconciledTo()
							|| account.equals(transaction.getFrom()) && transaction.isReconciledFrom())
						g.drawString(PrefsModel.getInstance().getTranslator().get(BuddiKeys.SHORT_RECONCILED), 30, bottomRowYPos);
					g.setColor(textColor);
				}
			}

			//Amount renderer when account is null
			int xPos = 0, minXPos = Integer.MAX_VALUE;
			if (account == null){
				fm = this.getGraphics().getFontMetrics();
				if (transaction != null){

					long value = transaction.getAmount();
					xPos = width - 20 - fm.stringWidth(TextFormatter.getFormattedCurrency(value, false, false));
					if (xPos < minXPos) minXPos = xPos;
					g.setColor(InternalFormatter.isRed(transaction) ? Color.RED : textColor);
					g.drawString(TextFormatter.getFormattedCurrency(value, false, false), xPos, bottomRowYPos);
					g.setColor(textColor);
				}
			}
			else {
				//Left column - max 100 px
				if (transaction.getFrom() != null
						&& transaction.getFrom().equals(account)){
					xPos = width - 220 - fm.stringWidth(TextFormatter.getFormattedCurrency(transaction.getAmount(), false, false)); 
					if (xPos < minXPos) minXPos = xPos;
					g.setColor(InternalFormatter.isRed(transaction, transaction.getTo().equals(account)) ? Color.RED : textColor);
					g.drawString(TextFormatter.getFormattedCurrency(transaction.getAmount(), false, false), xPos, bottomRowYPos);
					g.setColor(textColor);
				}
				//Right Column - max 100 px
				if (transaction.getTo() != null
						&& transaction.getTo().equals(account)){
					xPos = width - 120 - fm.stringWidth(TextFormatter.getFormattedCurrency(transaction.getAmount(), false, false));
					if (xPos < minXPos) minXPos = xPos;
					g.setColor(InternalFormatter.isRed(transaction, transaction.getTo().equals(account)) ? Color.RED : textColor);
					g.drawString(TextFormatter.getFormattedCurrency(transaction.getAmount(), false, false), xPos, bottomRowYPos);
					g.setColor(textColor);
				}

				//Balance - max 150px
				g.setFont(bold);
				fm = this.getGraphics().getFontMetrics();
				if (transaction != null){
					long balanceValue;
					if (transaction.getFrom() instanceof Account 
							&& transaction.getFrom().equals(account))
						balanceValue = transaction.getBalanceFrom();
					else
						balanceValue = transaction.getBalanceTo();

					xPos = width - 20 - fm.stringWidth(TextFormatter.getFormattedCurrency(balanceValue, false, account.getAccountType().isCredit()));
					if (xPos < minXPos) minXPos = xPos;
					g.setColor(InternalFormatter.isRed(account, balanceValue) ? Color.RED : textColor);
					g.drawString(TextFormatter.getFormattedCurrency(balanceValue, false, account.getAccountType().isCredit()), xPos, bottomRowYPos);
					g.setColor(textColor);
				}
			}
			g.setFont(f);
			
			//To / From sources
			g.setFont(italic);
			g.drawString(
					Formatter.getStringToLength(
							transaction.getFrom().getFullName() + "       " + transaction.getTo().getFullName(),
							minXPos - 70,
							fm), 
							50, 
							bottomRowYPos);
			fm = this.getGraphics().getFontMetrics();
			int arrowOffset = 50 + fm.stringWidth(transaction.getFrom().getFullName() + " ");
			g.setFont(f);
			g.drawString(PrefsModel.getInstance().getTranslator().get(BuddiKeys.TO), arrowOffset, bottomRowYPos);

		}
	}
}
