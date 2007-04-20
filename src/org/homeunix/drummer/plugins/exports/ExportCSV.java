/*
 * Created on Oct 3, 2006 by wyatt
 */
package org.homeunix.drummer.plugins.exports;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Vector;

import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;

import org.homeunix.drummer.controller.TransactionController;
import org.homeunix.drummer.controller.Translate;
import org.homeunix.drummer.controller.TranslateKeys;
import org.homeunix.drummer.model.Account;
import org.homeunix.drummer.model.Transaction;
import org.homeunix.drummer.plugins.interfaces.BuddiExportPlugin;
import org.homeunix.drummer.util.FormatterWrapper;
import org.homeunix.drummer.view.TransactionsFrame;
import org.homeunix.thecave.moss.gui.abstractwindows.AbstractFrame;

public class ExportCSV implements BuddiExportPlugin {

	public void exportData(AbstractFrame frame, final File file) {
		final Account account;

		if (frame instanceof TransactionsFrame){
			account = ((TransactionsFrame) frame).getAccount();
		}
		else{
			account = null;
		}

		Vector<Transaction> allTransactions;
		if (account == null){
			allTransactions = TransactionController.getTransactions();
		}
		else {
			allTransactions = TransactionController.getTransactions(account);
		}
		
		StringBuilder sb = new StringBuilder();
		sb.append("\"").append("Date").append("\",");
		sb.append("\"").append("Description").append("\",");
		sb.append("\"").append("Number").append("\",");
		sb.append("\"").append("Memo").append("\",");
		sb.append("\"").append("Amount").append("\",");					
		sb.append("\"").append("From").append("\",");
		sb.append("\"").append("To").append("\"");							
		sb.append("\n");

		for (Transaction transaction : allTransactions) {
			sb.append("\"").append(transaction.getDate()).append("\",");
			sb.append("\"").append(transaction.getDescription()).append("\",");
			sb.append("\"").append(transaction.getNumber()).append("\",");
			sb.append("\"").append(transaction.getMemo().replaceAll("\n", " ")).append("\",");
			sb.append("\"").append(FormatterWrapper.getFormattedCurrency(transaction.getAmount())).append("\",");					
			sb.append("\"").append((transaction.getFrom() instanceof Account ? "Account:" : "Category:")).append(Translate.getInstance().get(transaction.getFrom().getName())).append("\",");
			sb.append("\"").append((transaction.getTo() instanceof Account ? "Account:" : "Category:")).append(Translate.getInstance().get(transaction.getTo().getName())).append("\"");							
			sb.append("\n");
		}

		try{
			PrintStream out = new PrintStream(new FileOutputStream(file));
			out.println(sb.toString());
			out.close();
		}
		catch (FileNotFoundException fnfe){
			JOptionPane.showMessageDialog(null, 
					fnfe.getMessage(), 
					Translate.getInstance().get(TranslateKeys.ERROR), 
					JOptionPane.ERROR_MESSAGE);
		}
		JOptionPane.showMessageDialog(null, Translate.getInstance().get(TranslateKeys.SUCCESSFUL_EXPORT) + file.getAbsolutePath(), Translate.getInstance().get(TranslateKeys.FILE_SAVED), JOptionPane.INFORMATION_MESSAGE);
	}

	public Class[] getCorrectWindows() {
		return null;
	}

	public String getDescription() {
		return Translate.getInstance().get(TranslateKeys.EXPORT_TO_CSV);
	}

	public String getFileChooserTitle() {
		return Translate.getInstance().get(TranslateKeys.CHOOSE_EXPORT_FILE);
	}

	public FileFilter getFileFilter() {
		return new FileFilter(){

			@Override
			public boolean accept(File f) {
				if (f.getAbsolutePath().endsWith(".csv") 
						|| f.isDirectory())
					return true;
				else
					return false;
			}

			@Override
			public String getDescription() {
				return "CSV Files";
			}
		};
	}

	public boolean isPromptForFile() {
		return true;
	}
}
