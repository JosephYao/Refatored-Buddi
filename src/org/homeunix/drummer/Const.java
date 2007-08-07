/*
 * Created on May 12, 2006 by wyatt
 */
package org.homeunix.drummer;

import java.awt.Color;

import javax.swing.JList;

import org.homeunix.thecave.moss.util.Version;


/**
 * A container for constants, used throughout the rest of the program.
 * 
 * @author wyatt
 *
 */
public class Const {
	public static final String STABLE = "STABLE";
	public static final String UNSTABLE = "UNSTABLE";
	
	//Version variables
	public static final String VERSION = "2.5.9.0";
	public static final String BRANCH = UNSTABLE;
	public static final boolean DEVEL = true;
	public static final Version API_VERSION = new Version("2.6");
	
	//Language constants
	public final static String LANGUAGE_EXTENSION = ".lang";
	public final static String LANGUAGE_FOLDER = "Languages";
	
	//Data file constants
	public final static String DATA_FILE_EXTENSION = ".buddi";
	public final static String BACKUP_FILE_EXTENSION = ".buddi.bak";
	public final static String DATA_DEFAULT_FILENAME = "Data";
	
	//Web addresses
	public final static String DONATE_URL = "http://sourceforge.net/donate/index.php?group_id=167026";
	public final static String PROJECT_URL = "http://buddi.sourceforge.net/";
	public final static String DOWNLOAD_URL_STABLE = "http://buddi.sourceforge.net/Buddi";
	public final static String DOWNLOAD_URL_UNSTABLE = "http://buddi.sourceforge.net/Buddi-development";
	public final static String VERSION_FILE = "version.txt";
	
	//File Types
	public final static String DOWNLOAD_TYPE_OSX = ".dmg";
	public final static String DOWNLOAD_TYPE_WINDOWS = ".exe";
	public final static String DOWNLOAD_TYPE_GENERIC = ".jar";
	public final static String DOWNLOAD_TYPE_DEBIAN = ".deb";
	public final static String DOWNLOAD_TYPE_REDHAT = ".rpm";
	public final static String DOWNLOAD_TYPE_SLACKWARE = ".tgz";
	
	//Local help paths
	public final static String HELP_FOLDER = "Help";
	public final static String HELP_FILE = "index.html";
	
	//Local listener configuration
	public final static int LISTEN_PORT = 4331;
	public final static String SEPARATOR = "&";
	public final static String ACCOUNT = "acct";
	public final static String DESCRIPTION = "desc";
	public final static String DATE = "date";
	public final static String NUMBER = "num";
	public final static String AMOUNT = "amt";
	public final static String MEMO = "memo";
	public final static String TO = "to";
	public final static String FROM = "from";
	
	//File names
	public final static String LOG_FILE = "buddi.log";
	
	//Colors
	public final static Color COLOR_SELECTED = new JList().getSelectionBackground(); // new Color(181, 213, 255);
	public final static Color COLOR_SELECTED_TEXT = new JList().getSelectionForeground();
	public final static Color COLOR_UNSELECTED_TEXT = new JList().getForeground();
	public final static Color COLOR_TRANSPARENT = new Color(0, 0, 0, 255);
	public final static Color COLOR_EVEN_ROW = new Color(237, 243, 254);
	public final static Color COLOR_ODD_ROW = Color.WHITE;

	
	//Languages which are included in the .jar file.  Needed since there
	// is no good method of reading the main jar file by itself (since
	// it may be wrapped in a .exe, etc).
	public final static String[] BUNDLED_LANGUAGES = {
		"Czech",
		"Deutsch",
		"English_(US)",
		"English",
		"Espanol",
		"Espanol_(MX)",
		"Greek",
		"Italiano",
		"Francais",
		"Nederlands",
		"Nederlands_(BE)",
		"Norsk",
		"Portugues",
		"Serbian",
		"Russian"
	};
	
	public final static String[] BUNDLED_DOCUMENTS = {
		"Changelog.txt",
		"License.txt",
		"Readme.txt"
	};
	
	public final static String[] BUNDLED_LICENSES = {
		"Artistic License.txt",
		"GNU General Public License.txt",
		"GNU Lesser General Public License.txt",
		"Modified BSD License.txt"
	};
	
	//Date formats to appear in Preferences.
	public final static String[] DATE_FORMATS = {
		"yyyy/MM/dd",
		"yyyy/MMM/dd",
		"dd/MM/yyyy",
		"dd/MMM/yyyy",
		"MM/dd/yyyy",
		"MMM/dd/yyyy",
		"MMMM dd, yyyy",
		"MMM dd yyyy"
	};
	
	//Currency formats to appear in Preferences
	public final static String[] CURRENCY_FORMATS = {
		"$",
		"\u20ac", //Euro
		"\u00a3", //British Pounds
		"p.",     //Russian Ruble
		"\u00a5", //Yen
		"\u20a3", //French Franc
		"SFr", 		//Swiss Franc (?)
		"Rs", 		//Indian Rupees
		"Kr", 		//Norwegian
		"Bs", 		//Venezuela
		"S/.", 		//Peru
		"\u20b1", //Peso
		"\u20aa", //Israel Sheqel 
		"Mex$",		//Mexican Peso
		"R$",			//Brazilian Real
		"Ch$",		//Chilean Peso
		"C",			//Costa Rican Colon
		"Arg$",		//Argentinan Peso
		"Kc"	//Something else; requested by a user
	};
	
	//The plugins which are included in the main Buddi jar.
	public final static String[] BUILT_IN_PLUGINS = {
		"org.homeunix.drummer.plugins.reports.IncomeExpenseReportByCategory",
		"org.homeunix.drummer.plugins.reports.IncomeExpenseReportByDescription",
		"org.homeunix.drummer.plugins.reports.AverageIncomeExpenseByCategory",
		"org.homeunix.drummer.plugins.reports.TransactionsNotCleared",
		"org.homeunix.drummer.plugins.reports.TransactionsNotReconciled",

		"org.homeunix.drummer.plugins.graphs.IncomePieGraph",
		"org.homeunix.drummer.plugins.graphs.ExpensesPieGraph",
		"org.homeunix.drummer.plugins.graphs.ExpenseBudgetedVsActual",
		"org.homeunix.drummer.plugins.graphs.NetWorthBreakdown",
		"org.homeunix.drummer.plugins.graphs.NetWorthOverTime",
		
//		"org.homeunix.thecave.plugins.graph.incomeovertime.IncomeAndExpenseOverTime",
//		"org.homeunix.thecave.plugins.export.qif.ExportQIF"
//		"org.homeunix.drummer.plugins.imports.SimpleImport"
		
//		"org.homeunix.drummer.plugins.exports.ExportHTML",
//		"org.homeunix.drummer.plugins.exports.ExportCSV"
//		"org.homeunix.drummer.plugins.exports.TestPlugin"
//		"org.homeunix.thecave.plugins.graph.parentcategorypie.IncomePieGraph",
//		"org.homeunix.thecave.plugins.report.select.ReportBySelectedAccount",
	};
		
	private Const(){}
}
