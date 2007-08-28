/*
 * Created on May 12, 2006 by wyatt
 */
package org.homeunix.thecave.buddi;

import java.awt.Color;
import java.io.File;

import javax.swing.JList;
import javax.swing.filechooser.FileFilter;

import org.homeunix.thecave.buddi.i18n.BuddiKeys;
import org.homeunix.thecave.buddi.model.BudgetPeriodType;
import org.homeunix.thecave.buddi.model.periods.BudgetPeriodMonthly;
import org.homeunix.thecave.buddi.model.periods.BudgetPeriodQuarterly;
import org.homeunix.thecave.buddi.model.periods.BudgetPeriodWeekly;
import org.homeunix.thecave.buddi.model.periods.BudgetPeriodYearly;
import org.homeunix.thecave.buddi.model.prefs.PrefsModel;
import org.homeunix.thecave.buddi.plugin.builtin.imports.ImportLegacyData;
import org.homeunix.thecave.buddi.plugin.builtin.report.ExpensesPieGraph;
import org.homeunix.thecave.buddi.plugin.builtin.report.IncomeExpenseReportByCategory;
import org.homeunix.thecave.buddi.plugin.builtin.report.IncomePieGraph;
import org.homeunix.thecave.buddi.plugin.builtin.report.NetWorthOverTime;
import org.homeunix.thecave.buddi.view.dialogs.prefs.AdvancedPreferences;
import org.homeunix.thecave.buddi.view.dialogs.prefs.LocalePreferences;
import org.homeunix.thecave.buddi.view.dialogs.prefs.NetworkPreferences;
import org.homeunix.thecave.buddi.view.dialogs.prefs.PluginPreferences;
import org.homeunix.thecave.buddi.view.dialogs.prefs.ViewPreferences;
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
	public static final Version VERSION = new Version("2.9.0.0");
	public static final String BRANCH = UNSTABLE;
	public static final boolean DEVEL = true;
	public static final Version API_VERSION = new Version("3.0");
	
	//Language constants
	public final static String LANGUAGE_EXTENSION = ".lang";
	public final static String LANGUAGE_FOLDER = "Languages";
	public final static String LANGUAGE_RESOURCE_PATH = "/Languages";
	
	//Data file constants
	public final static String DATA_FILE_EXTENSION = ".buddi3";
	public final static String BACKUP_FILE_EXTENSION = ".buddi3.bak";
	public final static String DATA_FILE_EXTENSION_OLD = ".buddi";
	public final static String BACKUP_FILE_EXTENSION_OLD = ".buddi.bak";
	
	//Plugin Constants
	public static final String PLUGIN_FOLDER = "Plugins";
	public static final String PLUGIN_EXTENSION = ".buddi3plugin";
	
	//Data file encryption constants (This is now taken care of in the CipherStreamFactory -- Wyatt)
//	public final static String DATA_FILE_ENCRYPTION = "AES/CBC/PKCS5Padding";
//	public final static String KEY_ALGORITHM = "AES";
//	public final static int SALT_LENGTH = 16;
//	public final static String XML_PROLOGUE = "<?xml"; //Tests if the file is encrypted
//	public final static String DATA_DEFAULT_FILENAME = "Data";
	
	public static final FileFilter FILE_FILTER_DATA = new FileFilter(){
		@Override
		public boolean accept(File f) {
			if (f.isDirectory() 
					|| f.getName().endsWith(Const.DATA_FILE_EXTENSION)){
				return true;
			}
			return false;
		}

		@Override
		public String getDescription() {
			return PrefsModel.getInstance().getTranslator().get(BuddiKeys.FILE_DESCRIPTION_BUDDI_DATA_FILES);
		}
	};
	public static final FileFilter FILE_FILTER_BACKUP = new FileFilter(){
		@Override
		public boolean accept(File f) {
			if (f.isDirectory() 
					|| f.getName().endsWith(Const.BACKUP_FILE_EXTENSION)
					|| f.getName().endsWith(Const.BACKUP_FILE_EXTENSION_OLD)){
				return true;
			}
			return false;
		}

		@Override
		public String getDescription() {
			return PrefsModel.getInstance().getTranslator().get(BuddiKeys.FILE_DESCRIPTION_BUDDI_DATA_FILES);
		}
	};
	
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
	
	//Local listener configuration -- Removed in 2.5.9.0
//	public final static int LISTEN_PORT = 4331;
//	public final static String SEPARATOR = "&";
//	public final static String ACCOUNT = "acct";
//	public final static String DESCRIPTION = "desc";
//	public final static String DATE = "date";
//	public final static String NUMBER = "num";
//	public final static String AMOUNT = "amt";
//	public final static String MEMO = "memo";
//	public final static String TO = "to";
//	public final static String FROM = "from";
	
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
		"Francais",
		"Greek",
		"Italiano",
		"Nederlands",
		"Nederlands_(BE)",
		"Norsk",
		"Portugues",
		"Russian",
		"Serbian",
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
	
	public final static BudgetPeriodType[] BUDGET_PERIOD_TYPES = {
		new BudgetPeriodMonthly(),
		new BudgetPeriodWeekly(),
		new BudgetPeriodQuarterly(),
		new BudgetPeriodYearly(),
	};
	
	public final static String[] BUILT_IN_PREFERENCE_PANELS = {
		ViewPreferences.class.getCanonicalName(),
		PluginPreferences.class.getCanonicalName(),
		LocalePreferences.class.getCanonicalName(),
		NetworkPreferences.class.getCanonicalName(),
		AdvancedPreferences.class.getCanonicalName(),
	};
	
	//The report plugins which are included in the main Buddi jar.
	public final static String[] BUILT_IN_REPORTS = {
		IncomeExpenseReportByCategory.class.getCanonicalName(),
//		AverageIncomeExpenseByCategory.class.getCanonicalName(),
		IncomePieGraph.class.getCanonicalName(),
		ExpensesPieGraph.class.getCanonicalName(),
		NetWorthOverTime.class.getCanonicalName(),
	};
	
	public static final String[] BUILT_IN_IMPORTS = {
		ImportLegacyData.class.getCanonicalName(),
	};
	
	public static final String[] BUILT_IN_EXPORTS = {
		
	};	
		
	private Const(){}
}
