/*
 * Created on May 8, 2006 by wyatt
 */
package org.homeunix.drummer;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import javax.swing.JOptionPane;


public class Strings {
	//The list of constants, used as keys.  Makes it easier to rememebr the keys.
	public final static String DELETE = "DELETE";
	public final static String UNDELETE = "UNDELETE";
	public final static String OK = "OK";
	public final static String CANCEL = "CANCEL";
	public final static String NEW = "NEW";
	public final static String OPEN = "OPEN";
	public final static String RECORD = "RECORD";
	public final static String UPDATE = "UPDATE";
	public final static String CLEAR = "CLEAR";
	public final static String AND = "AND";
	public final static String GO = "GO";
	public final static String NAME = "NAME";
	public final static String MY_BUDGET = "MY_BUDGET";
	public final static String MY_ACCOUNTS = "MY_ACCOUNTS";
	public final static String REPORTS = "REPORTS";
	public final static String GRAPHS = "GRAPHS";
	public final static String CATEGORY = "CATEGORY";
	public final static String ACCOUNT = "ACCOUNT";
	public final static String BUDGETED_AMOUNT = "BUDGETED_AMOUNT";
	public final static String STARTING_BALANCE = "STARTING_BALANCE";
	public final static String TRANSACTIONS = "TRANSACTIONS";
	public final static String NET_WORTH = "NET_WORTH";
	public final static String INCOME = "INCOME";
	public final static String BUDGET_NET_INCOME = "BUDGET_NET_INCOME";
	public final static String CASH = "CASH";
	public final static String SAVINGS = "SAVINGS"; 
	public final static String CHEQUING = "CHEQUING";
	public final static String INVESTMENT = "INVESTMENT";
	public final static String LIABILITY = "LIABILITY";
	public final static String CREDIT_CARD = "CREDIT_CARD";
	public final static String LINE_OF_CREDIT = "LINE_OF_CREDIT";
	public final static String CURRENCY_SIGN = "CURRENCY_SIGN";
	public final static String THIS_MONTH = "THIS_MONTH";
	public final static String LAST_MONTH = "LAST_MONTH";
	public final static String THIS_YEAR = "THIS_YEAR";
	public final static String LAST_YEAR = "LAST_YEAR";
	public final static String REPORT_FOR = "REPORT_FOR";
	public final static String REPORT_BETWEEN = "REPORT_BETWEEN";
	public final static String NO_PARENT = "NO_PARENT";
	public final static String FILE = "FILE";
	public final static String EDIT = "EDIT";
	public final static String WINDOW = "WINDOW";
	public final static String HELP = "HELP";
	public final static String CUT = "CUT";
	public final static String COPY = "COPY";
	public final static String PASTE = "PASTE";
	public final static String BACKUP_DATA_FILE = "BACKUP_DATA_FILE"; 
	public final static String OPEN_DATA_FILE = "OPEN_DATA_FILE";
	public final static String MINIMIZE = "MINIMIZE";
	public final static String ZOOM = "ZOOM";
	public final static String CLOSE_WINDOW = "CLOSE_WINDOW";
	public final static String BUDDI_HELP = "BUDDI_HELP";
	public final static String PREFERENCES = "PREFERENCES";
	public final static String LANGUAGE = "LANGUAGE";
	public final static String SHOW_DELETED_ACCOUNTS = "SHOW_DELETED_ACCOUNTS";
	public final static String SHOW_DELETED_CATEGORIES = "SHOW_DELETED_CATEGORIES";
	public final static String RESTART = "RESTART";
	public final static String BUDDI = "BUDDI";
	public final static String VERSION = "VERSION";
	public final static String ABOUT_TEXT = "ABOUT_TEXT";
	public final static String ABOUT_COPYRIGHT = "ABOUT_COPYRIGHT";
	public final static String ABOUT_EMAIL = "ABOUT_EMAIL";
	public final static String ABOUT_WEBPAGE = "ABOUT_WEBPAGE";
	public final static String ABOUT_GPL = "ABOUT_GPL";
	public final static String TOOLTIP_DATE = "TOOLTIP_DATE"; 
	public final static String TOOLTIP_AMOUNT = "TOOLTIP_AMOUNT";
	public final static String TOOLTIP_FROM = "TOOLTIP_FROM";
	public final static String TOOLTIP_TO = "TOOLTIP_TO";
	public final static String TOOLTIP_NUMBER = "TOOLTIP_NUMBER";
	public final static String TOOLTIP_DESC = "TOOLTIP_DESC";
	public final static String TOOLTIP_MEMO = "TOOLTIP_MEMO";
	public final static String CHOOSE_DATA_DIR = "CHOOSE_DATA_DIR";
	public final static String NO_DATA_DIR = "NO_DATA_DIR";
	public final static String TO = "TO";
	public final static String SOURCE = "SOURCE";
	public final static String DESTINATION = "DESTINATION";
	public final static String RECORD_BUTTON_ERROR = "RECORD_BUTTON_ERROR";
	public final static String BUDDI_FILE_DESC = "BUDDI_FILE_DESC";
	public final static String OTHER = "OTHER";	
	public final static String AUTO = "AUTO";
	public final static String ENTERTAINMENT = "ENTERTAINMENT"; 
	public final static String HOUSEHOLD = "HOUSEHOLD";
	public final static String GROCERIES = "GROCERIES";
	public final static String INVESTMENT_EXPENSES = "INVESTMENT_EXPENSES"; 
	public final static String MISC_EXPENSES = "MISC_EXPENSES";
	public final static String UTILITIES = "UTILITIES";
	public final static String BONUS = "BONUS";
	public final static String SALARY = "SALARY";
	public final static String INVESTMENT_INCOME = "INVESTMENT_INCOME";
	public final static String CHOOSE_BACKUP_FILE = "CHOOSE_BACKUP_FILE";
	public final static String CANNOT_SAVE_OVER_DIR = "CANNOT_SAVE_OVER_DIR";
	public final static String SUCCESSFUL_BACKUP = "SUCCESSFUL_BACKUP";
	public final static String FILE_SAVED = "FILE_SAVED";
	public final static String LOAD_BACKUP_FILE = "LOAD_BACKUP_FILE";
	public final static String MUST_SELECT_BUDDI_FILE = "MUST_SELECT_BUDDI_FILE";
	public final static String CONFIRM_LOAD_BACKUP_FILE = "CONFIRM_LOAD_BACKUP_FILE";
	public final static String CLOSE_DATA_FILE = "CLOSE_DATA_FILE";
	public final static String SUCCESSFUL_OPEN_FILE = "SUCCESSFUL_OPEN_FILE";
	public final static String OPENED_FILE = "OPENED_FILE";
	public final static String CANCELLED_FILE_LOAD_MESSAGE = "CANCELLED_FILE_LOAD_MESSAGE";
	public final static String CANCELLED_FILE_LOAD = "CANCELLED_FILE_LOAD";
	public final static String CHOOSE_DATE_INTERVAL = "CHOOSE_DATE_INTERVAL";
	public final static String PERMANENT_DELETE_ACCOUNT = "PERMANENT_DELETE_ACCOUNT";
	public final static String NO_TRANSACTIONS_USING_ACCOUNT = "NO_TRANSACTIONS_USING_ACCOUNT";
	public final static String PERMANENT_DELETE_CATEGORY = "PERMANENT_DELETE_CATEGORY";
	public final static String NO_TRANSACTIONS_USING_CATEGORY = "NO_TRANSACTIONS_USING_CATEGORY";
	public final static String ACCOUNT_TYPE = "ACCOUNT_TYPE";
	public final static String PARENT_CATEGORY = "PARENT_CATEGORY";
	public final static String REPORT_DATE_ERROR = "REPORT_DATE_ERROR";
	public final static String START_DATE_AFTER_END_DATE = "START_DATE_AFTER_END_DATE";
	public final static String CLEAR_TRANSACTION_LOSE_CHANGES = "CLEAR_TRANSACTION_LOSE_CHANGES";
	public final static String CLEAR_TRANSACTION = "CLEAR_TRANSACTION";
	public final static String DELETE_TRANSACTION_LOSE_CHANGES = "DELETE_TRANSACTION_LOSE_CHANGES";
	public final static String DELETE_TRANSACTION = "DELETE_TRANSACTION";
	public final static String ENTER_ACCOUNT_NAME_AND_TYPE = "ENTER_ACCOUNT_NAME_AND_TYPE";
	public final static String ENTER_CATEGORY_NAME = "ENTER_CATEGORY_NAME";
	public final static String WARNING = "WARNING";
	public final static String MORE_INFO_NEEDED = "MORE_INFO_NEEDED";
	public final static String DATE_FORMAT = "DATE_FORMAT";
	public final static String RESTART_NEEDED = "RESTART_NEEDED";
	public final static String EXPENSES = "EXPENSES";
	public final static String EXPENSE_PIE_GRAPH = "EXPENSE_PIE_GRAPH";
	public final static String NETWORTH_PIE_GRAPH = "NETWORTH_PIE_GRAPH";
	public final static String EXPENSE_ACTUAL_BUDGET_BAR_GRAPH = "EXPENSE_ACTUAL_BUDGET_BAR_GRAPH";
	public final static String NETWORTH_LINE_GRAPH = "NETWORTH_LINE_GRAPH";
	public final static String EXPENSE_ACTUAL_BUDGET = "EXPENSE_ACTUAL_BUDGET";
	public final static String TODAY = "TODAY";
	public final static String ACTUAL = "ACTUAL";
	public final static String BUDGETED = "BUDGETED";
	public final static String YESTERDAY = "YESTERDAY";
	public final static String ONE_MONTH = "ONE_MONTH";
	public final static String TWO_MONTHS = "TWO_MONTHS";
	public final static String SIX_MONTHS = "SIX_MONTHS";
	public final static String YEAR = "YEAR";
	public final static String LAST_WEEK = "LAST_WEEK";
	public final static String REPORT_AS_OF_DATE = "REPORT_AS_OF_DATE";
	public final static String DATE_AFTER_TODAY = "DATE_AFTER_TODAY";
	public final static String INCOME_PIE_GRAPH = "INCOME_PIE_GRAPH";
	public final static String NEW_TRANSACTION = "NEW_TRANSACTION";
	public final static String PRINT = "PRINT";
	public final static String NOTHING_TO_PRINT = "NOTHING_TO_PRINT";
	public final static String PRINT_ERROR = "PRINT_ERROR";
	
	public final static String RESTORE_DATA_FILE = "RESTORE_DATA_FILE";
	public final static String CONFIRM_RESTORE_BACKUP_FILE = "CONFIRM_RESTORE_BACKUP_FILE";
	public final static String SUCCESSFUL_RESTORE_FILE = "SUCCESSFUL_RESTORE_FILE";
	public final static String RESTORED_FILE = "RESTORED_FILE";
	public final static String CANCELLED_FILE_RESTORE = "CANCELLED_FILE_RESTORE";
	public final static String CANCEL_FILE_RESTORE_MESSAGE = "CANCEL_FILE_RESTORE_MESSAGE";
	
	private final Properties translations = new Properties();
	
	public static Strings inst() {
		return SingletonHolder.instance;
	}
	
	private static class SingletonHolder {
		private static Strings instance = new Strings();		
	}
	
	private Strings(){}
	
	public Strings loadLanguage(String language){
		String languageFile = Const.LANGUAGE_FOLDER + File.separator + language + Const.LANGUAGE_EXTENSION;
		try{
			translations.load(new BufferedInputStream(new FileInputStream(languageFile)));
		}
		catch(IOException ioe){
			JOptionPane.showMessageDialog(null, "Error loading language file " + languageFile + ": " + ioe);
		}
		return this;
	}
		
	public String get(String key){
		String ret = translations.getProperty(key);
		if (ret == null)
			return key;
		return ret;
	}
}
