/*
 * Created on Oct 3, 2006 by wyatt
 */
package org.homeunix.drummer.plugins.exports;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Vector;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import org.homeunix.drummer.controller.MainBuddiFrame;
import org.homeunix.drummer.controller.TransactionsFrame;
import org.homeunix.drummer.controller.Translate;
import org.homeunix.drummer.controller.TranslateKeys;
import org.homeunix.drummer.model.Account;
import org.homeunix.drummer.model.DataInstance;
import org.homeunix.drummer.model.Transaction;
import org.homeunix.drummer.plugins.BuddiExportPlugin;
import org.homeunix.drummer.plugins.BuddiPluginFactory.DateRangeType;
import org.homeunix.drummer.util.Formatter;
import org.homeunix.drummer.util.SwingWorker;
import org.homeunix.drummer.view.AbstractFrame;
import org.homeunix.drummer.view.ReportFrameLayout;

public class ExportCSV implements BuddiExportPlugin {

	public void exportData(AbstractFrame frame) {
		final JFileChooser jfc = new JFileChooser();
		jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
		jfc.setDialogTitle(Translate.getInstance().get(TranslateKeys.CHOOSE_EXPORT_FILE));
		if (jfc.showSaveDialog(MainBuddiFrame.getInstance()) == JFileChooser.APPROVE_OPTION){
			if (jfc.getSelectedFile().isDirectory())
				JOptionPane.showMessageDialog(null, Translate.getInstance().get(TranslateKeys.CANNOT_SAVE_OVER_DIR), Translate.getInstance().get(TranslateKeys.CHOOSE_BACKUP_FILE), JOptionPane.ERROR_MESSAGE);
			else{
				final String location = jfc.getSelectedFile().getAbsolutePath();
				final Account account;
				
				if (frame instanceof TransactionsFrame){
					account = ((TransactionsFrame) frame).getAccount();
				}
				else{
					account = null;
				}
				
				
				SwingWorker worker = new SwingWorker(){
					@Override
					public Object construct() {
						Vector<Transaction> allTransactions;
						if (account == null){
							allTransactions = DataInstance.getInstance().getTransactions();
						}
						else {
							allTransactions = DataInstance.getInstance().getTransactions(account);
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
							sb.append("\"").append(Formatter.getInstance().getDecimalFormat().format(transaction.getAmount() / 100.0)).append("\",");					
							sb.append("\"").append((transaction.getFrom() instanceof Account ? "Account:" : "Category:")).append(Translate.getInstance().get(transaction.getFrom().getName())).append("\",");
							sb.append("\"").append((transaction.getTo() instanceof Account ? "Account:" : "Category:")).append(Translate.getInstance().get(transaction.getTo().getName())).append("\"");							
							sb.append("\n");
						}
						
						try{
							PrintStream out = new PrintStream(new FileOutputStream(location));
							out.println(sb.toString());
							out.close();
						}
						catch (FileNotFoundException fnfe){
							return fnfe;
						}

						return null;
					}
					
					@Override
					public void finished() {
						if (get() == null){
							JOptionPane.showMessageDialog(null, Translate.getInstance().get(TranslateKeys.SUCCESSFUL_EXPORT) + location, Translate.getInstance().get(TranslateKeys.FILE_SAVED), JOptionPane.INFORMATION_MESSAGE);
						}
						else if (get() instanceof Exception){
							JOptionPane.showMessageDialog(null, ((Exception) get()).getMessage(), Translate.getInstance().get(TranslateKeys.ERROR), JOptionPane.ERROR_MESSAGE);
						}
					}
				};
				
				worker.start();
			}
		}
	}
	
	public Class[] getCorrectWindows() {
		Class[] windows = new Class[1];
		windows[0] = ReportFrameLayout.class;
		return windows;
	}

	public DateRangeType getDateRangeType() {
		return null;  //Not used by ExportPlugin
	}

	public String getDescription() {
		return Translate.getInstance().get(TranslateKeys.EXPORT_TO_CSV);
	}

	public String getTitle() {
		return null; //Not used by ExportPlugin
	}

}
