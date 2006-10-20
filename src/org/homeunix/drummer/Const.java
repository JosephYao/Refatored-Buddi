/*
 * Created on May 12, 2006 by wyatt
 */
package org.homeunix.drummer;

import org.homeunix.drummer.controller.TranslateKeys;


/**
 * A container for constants, used throughout the rest of the program.
 * 
 * @author wyatt
 *
 */
public class Const {
	//Version variables
	public static final String VERSION = "2.0.0rc1";
	public static final String BRANCH = "UNSTABLE";
	public static final boolean DEVEL = true;
	
	//Language constants
	public final static String LANGUAGE_EXTENSION = ".lang";
	public final static String LANGUAGE_FOLDER = "Languages";
	
//	public final static String HELP_FOLDER = "Help";
//	public final static String HELP_FILE = "index.html";
	
	public final static String DATA_FILE_EXTENSION = ".buddi";
	public final static String DATA_DEFAULT_FILENAME = "Data";
	
	//Web addresses
	public final static String DONATE_URL = "http://sourceforge.net/donate/index.php?group_id=167026";
	public final static String PROJECT_URL = "http://buddi.sourceforge.net/";
	public final static String DOWNLOAD_URL = "http://buddi.sourceforge.net/Buddi-";
	public final static String DOWNLOAD_URL_DMG = ".dmg?download";
	public final static String DOWNLOAD_URL_ZIP = ".zip?download";
	public final static String DOWNLOAD_URL_TGZ = ".tgz?download";
	public final static String VERSION_FILE = "version.txt";
	
	//Languages which are included in the .jar file.  Needed since there
	// is no good method of reading the main jar file by itself (since
	// it may be wrapped in a .exe, etc).
	public final static String[] BUNDLED_LANGUAGES = {
		"English",
		"English_(US)",
		"Espanol",
		"Deutsch",
		"Norsk",
		"Nederlands",
		"Nederlands_(BE)"
	};
	
	//Date formats to appear in Preferences.
	public final static String[] DATE_FORMATS = {
		"yyyy-MM-dd",
		"yyyy-MMM-dd",
		"MMMM dd, yyyy",
		"MMM dd yyyy",
		"yyyy/MMM/d",
		"yyyy/MM/dd",
		"dd.MM.yyyy",
		"dd/MM/yyyy"
	};
	
	//Currency formats to appear in Preferences
	public final static String[] CURRENCY_FORMATS = {
		"$",
		"\u20ac",  //Euro
		"\u00a3",  //British Pounds
		"p.",      //Russian Ruble
		"\u00a5",  //Yen
		"\u20a3",  //French Franc
		"S\u20a3", //Swiss Franc (?)
		"Rs", //Indian Rupees
		"Kr", //Norwegian
		"Bs", //Venezuela
		"S/.", //Peru
		"\u20b1",  //Peso
		"\u20aa"  //Israel Sheqel
	};
	
	//The days in the week.  Used in Scheduled Transactions.
	public final static TranslateKeys[] DAYS_IN_WEEK = {
		TranslateKeys.SUNDAY,
		TranslateKeys.MONDAY,
		TranslateKeys.TUESDAY,
		TranslateKeys.WEDNESDAY,
		TranslateKeys.THURSDAY,
		TranslateKeys.FRIDAY,
		TranslateKeys.SATURDAY
	};
	
	//The plugins which are included in the main Buddi jar.
	public final static String[] BUILT_IN_PLUGINS = {
		"org.homeunix.drummer.plugins.reports.IncomeExpenseReportByCategory",
		"org.homeunix.drummer.plugins.reports.IncomeExpenseReportByDescription",
		"org.homeunix.drummer.plugins.graphs.IncomePieGraph",
		"org.homeunix.drummer.plugins.graphs.ExpensesPieGraph",
		"org.homeunix.drummer.plugins.graphs.ExpenseBudgetedVsActual",
		"org.homeunix.drummer.plugins.graphs.NetWorthBreakdown",
		"org.homeunix.drummer.plugins.graphs.NetWorthOverTime",
		
		"org.homeunix.drummer.plugins.exports.ExportHTML",
		"org.homeunix.drummer.plugins.exports.ExportCSV"
		
	};
		
	private Const(){}
}
