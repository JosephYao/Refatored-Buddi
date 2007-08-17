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

import org.homeunix.thecave.buddi.Const;
import org.homeunix.thecave.buddi.i18n.BuddiKeys;
import org.homeunix.thecave.buddi.model.Account;
import org.homeunix.thecave.buddi.model.Transaction;
import org.homeunix.thecave.buddi.model.prefs.PrefsModel;
import org.homeunix.thecave.buddi.util.InternalFormatter;
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

	private boolean showCleared = PrefsModel.getInstance().isShowCleared();
	private boolean showReconciled = PrefsModel.getInstance().isShowReconciled();
	private final boolean simple;
	
	private int lastWidth;
	private int descriptionLength = 50, toFromLength = 40;

	private static Font f;
	private static Font bold;
	private static Font italic;
	
	private static int currentDescriptionLength; //Used by EditableTransaction to format description width.
	
	private static final Color GREEN = new Color(0, 128, 0);

	public static int getCurrentDescriptionLength(){
		return currentDescriptionLength;
	}
	
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
				descriptionLength = test.length() / 2;
				currentDescriptionLength = descriptionLength;
			}

			//On the bottom line, we allocate 300px for all of the
			// balances, and 50 for the C R; 
			// We can take up to (width - 350)px for the toFrom field.
			//Since we use each of these separately, we divide by two first.
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

		// Antialias text
		((Graphics2D) g).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
				RenderingHints.VALUE_TEXT_ANTIALIAS_ON );

		if (transaction != null){
			Color textColor = g.getColor();
			FontMetrics fm;
			int topRowYPos = height / 2 - 5;
			int bottomRowYPos = height - 5;

			//Date
			g.drawString(InternalFormatter.getDateFormat().format(transaction.getDate()), 10, topRowYPos);

			//Description
			f = g.getFont();
			if (simple) {
				bold = f;
				italic = f;
			}
			else {
				bold = new Font(f.getName(), Font.BOLD, f.getSize());
				italic = new Font(f.getName(), Font.ITALIC, f.getSize());				
			}
			g.setFont(bold);
			g.drawString(Formatter.getLengthFormat(descriptionLength).format(transaction.getDescription()), 150, topRowYPos);
			g.setFont(f);
			
			//Cleared and Reconciled
			if (showCleared){
				g.setColor(GREEN);
				if (transaction.isCleared())
					g.drawString(PrefsModel.getInstance().getTranslator().get(BuddiKeys.SHORT_CLEARED), 20, bottomRowYPos);
				g.setColor(textColor);
			}
			if (showReconciled){
				g.setColor(GREEN);
				if (transaction.isReconciled())
					g.drawString(PrefsModel.getInstance().getTranslator().get(BuddiKeys.SHORT_RECONCILED), 30, bottomRowYPos);
				g.setColor(textColor);
			}

			//To / From sources
			g.setFont(italic);
			g.drawString(Formatter.getLengthFormat(toFromLength).format(transaction.getFrom().getFullName() + "       " + transaction.getTo().getFullName()), 50, bottomRowYPos);
			fm = this.getGraphics().getFontMetrics();
			int arrowOffset = 50 + fm.stringWidth(transaction.getFrom().getFullName() + " ");
			g.setFont(f);
			g.drawString(PrefsModel.getInstance().getTranslator().get(BuddiKeys.TO), arrowOffset, bottomRowYPos);

			//Get the font metrics for column alignment
			fm = this.getGraphics().getFontMetrics();
			
			//Left column
			if (account != null
					&& transaction.getFrom() != null
					&& transaction.getFrom().equals(account)){
				int xPos = width - 220 - fm.stringWidth(InternalFormatter.getFormattedCurrency(transaction.getAmount())); 
				g.setColor(InternalFormatter.isRed(transaction, transaction.getTo().equals(account)) ? Color.RED : textColor);
				g.drawString(InternalFormatter.getFormattedCurrency(transaction.getAmount()), xPos, bottomRowYPos);
				g.setColor(textColor);
			}
			//Right Column
			if (account != null
					&& transaction.getTo() != null
					&& transaction.getTo().equals(account)){
				int xPos = width - 120 - fm.stringWidth(InternalFormatter.getFormattedCurrency(transaction.getAmount()));
				g.setColor(InternalFormatter.isRed(transaction, transaction.getTo().equals(account)) ? Color.RED : textColor);
				g.drawString(InternalFormatter.getFormattedCurrency(transaction.getAmount()), xPos, bottomRowYPos);
				g.setColor(textColor);
			}

			//Balance
//			g.setFont(new Font(f.getName(), Font.BOLD, f.getSize()));
			g.setFont(bold);
			fm = this.getGraphics().getFontMetrics();
			if (transaction != null){
				long balanceValue;
				if (account != null){
					if (transaction.getFrom() instanceof Account 
							&& transaction.getFrom().equals(account))
						balanceValue = transaction.getBalanceFrom();
					else
						balanceValue = transaction.getBalanceTo();

					int xPos = width - 20 - fm.stringWidth(InternalFormatter.getFormattedCurrency(balanceValue, account.getType().isCredit()));
					g.setColor(InternalFormatter.isRed(account, balanceValue) ? Color.RED : textColor);
					g.drawString(InternalFormatter.getFormattedCurrency(balanceValue, account.getType().isCredit()), xPos, bottomRowYPos);
					g.setColor(textColor);
				}
			}
			g.setFont(f);
		}
	}

//	public void setAccount(Account account) {
//	this.account = account;
//	}
}
