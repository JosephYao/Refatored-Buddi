
/**
 * This class implements the exporting of Buddi data to QIF (Quicken Interchange Format).  
 * This class is responsible for getting and formatting the transaction and account details 
 * and formatting the output and propmting the user for path where the exported QIF file be stored.
 * This class is also responsible for mapping the translate key for Export to QIF menu item.
 * This class is also responsible for displaying error message if there are any exceptions 
 * while exporting and displaying a success message if exporting is done successfully.
 * 
 * @author Sujatha and Suranjana
 * @date Nov 3, 2006
 */

package org.homeunix.thecave.plugins.export.qif;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Vector;

import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;

import org.homeunix.drummer.controller.TransactionsFrame;
import org.homeunix.drummer.controller.Translate;
import org.homeunix.drummer.controller.TranslateKeys;
import org.homeunix.drummer.model.Account;
import org.homeunix.drummer.model.DataInstance;
import org.homeunix.drummer.model.Transaction;
import org.homeunix.drummer.plugins.interfaces.BuddiExportPlugin;
import org.homeunix.drummer.view.AbstractFrame;
import org.homeunix.thecave.moss.util.Formatter;


public class ExportQIF implements BuddiExportPlugin {
	
	public static final Locale LOCALE = Locale.US;
	public static final DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy",LOCALE);
	

	/**
	 * <!-- begin-user-doc -->
	 * formats date to a simple date format
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Date</em>' 
	 * @param value of the '<em>Date</em>' attribute.
	 * @see org.homeunix.drummer.plugins.exports#formatDate()
	 * @generated
	 */
	//method to format date to a simple date format
	public static String formatDate(Date date) {
        return dateFormat.format(date);
    }
	
	/**
	 * <!-- begin-user-doc -->
	 * this method exports gets all transactions and account details and formats them
	 * to QIF format. This method also responsible for displaying error message if there are any exceptions
	 * while exporting and displaying a success message if exporting is done successfully.
	 * There are no preconditions for this method as exception handling has been included for all types of input data
	 * postconditions- given valid frame and file object, this method cretes a qif file. if valid frame and file object are not given, throws an exception.
	 * <!-- end-user-doc -->
	 * @return void 
	 * @param value of the '<em>AbstractFrame</em>' and '<em>File</em>' attribute.
	 * @see org.homeunix.drummer.plugins.exports#exportData()
	 * @generated
	 */
	
	public void exportData(AbstractFrame frame, final File file) {
		final Account account;
		
		//Get the account for the transactions listed
		if (frame instanceof TransactionsFrame){
			account = ((TransactionsFrame) frame).getAccount();
		}
		else{
			account = null;
		}

		//get all the transactions for current account
		Vector<Transaction> allTransactions;
		if (account == null){
			allTransactions = DataInstance.getInstance().getTransactions();
		}
		else {
			allTransactions = DataInstance.getInstance().getTransactions(account);
		}
				
		StringBuilder sb = new StringBuilder();
		/* Retrieve and format Account Details into QIF format*/
		if(account!=null)
		{
			sb.append("!Type:").append(account.getAccountType());
			sb.append("\n");
			sb.append("D").append(formatDate(account.getCreationDate()));
			sb.append("\n");
			sb.append("T").append(Formatter.getInstance().getDecimalFormat().format(account.getBalance()));
			sb.append("\n");
			sb.append("CX").append("\n");
			sb.append("P").append("Opening Balance\n");
			sb.append("L").append("[").append(account.getName()).append("]");
			sb.append("\n");
			//^ End of entry
			sb.append("^\n");
		}
				
		for (Transaction transaction : allTransactions) {
								
			/* Retrieve and format Transaction Details to QIF format*/
			sb.append("D").append(formatDate(transaction.getDate()));
			sb.append("\n");
			sb.append("L").append(transaction.getDescription());
			sb.append("\n");
			sb.append("N").append(transaction.getNumber());
			sb.append("\n");
			sb.append("M").append(transaction.getMemo().replaceAll("\n", " "));
			sb.append("\n");
			sb.append("T").append(Formatter.getInstance().getDecimalFormat().format(transaction.getAmount() / 100.0));
			sb.append("\n");
			sb.append("L").append((transaction.getFrom() instanceof Account ? "Account:" : "Category:")).append(Translate.getInstance().get(transaction.getFrom().getName()));
			sb.append("\n");
			sb.append("L").append((transaction.getTo() instanceof Account ? "Account:" : "Category:")).append(Translate.getInstance().get(transaction.getTo().getName()));							
			sb.append("\n");
//			^ End of entry
	        sb.append("^\n");
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

	/**
	 * <!-- begin-user-doc -->
	 * this method gets the correct windows
	 * <!-- end-user-doc -->
	 * @return Class[] 
	 * @param none
	 * @see org.homeunix.drummer.plugins.exports#getCorrectWindows()
	 * @generated
	 */
	public Class[] getCorrectWindows() {
		Class[] windows = new Class[0];
		return windows;
	}

	/**
	 * <!-- begin-user-doc -->
	 * This method gets description for the translate key of Export to QIF
	 * <!-- end-user-doc -->
	 * @return String 
	 * @param none
	 * @see org.homeunix.drummer.plugins.exports#getDescription()
	 * @generated
	 */
	public String getDescription() {
		return Translate.getInstance().get("EXPORTQIF");
	}

	/**
	 * <!-- begin-user-doc -->
	 * This method gets the title for the menu option chosen
	 * <!-- end-user-doc -->
	 * @return String 
	 * @param none
	 * @see org.homeunix.drummer.plugins.exports#getFileChooserTitle()
	 * @generated
	 */
	public String getFileChooserTitle() {
		return Translate.getInstance().get(TranslateKeys.CHOOSE_EXPORT_FILE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * This method implements saving the output files into default "qif" extension
	 * <!-- end-user-doc -->
	 * @return FileFilter 
	 * @param none
	 * @see org.homeunix.drummer.plugins.exports#getFileFilter()
	 * @generated
	 */
	public FileFilter getFileFilter() {
		return new FileFilter(){

			@Override
			public boolean accept(File f) {
				if (f.isDirectory() || f.getAbsolutePath().toLowerCase().endsWith(".qif"))
					return true;
				else
					return false;
			}

			@Override
			public String getDescription() {
				return Translate.getInstance().get("QIF_FILES");
			}
		};
	}

	/**
	 * <!-- begin-user-doc -->
	 * This method returns true if prompted for file
	 * <!-- end-user-doc -->
	 * @return boolean
	 * @param none
	 * @see org.homeunix.drummer.plugins.exports#isPromptForFile()
	 * @generated
	 */
	public boolean isPromptForFile() {
		return true;
	}
}
