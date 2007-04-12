/*
 * Created on Apr 11, 2007 by wyatt
 */
package org.homeunix.drummer.util;

import java.text.DateFormat;

import org.homeunix.drummer.prefs.PrefsInstance;
import org.homeunix.thecave.moss.util.Formatter;

public class FormatterWrapper {
	public static DateFormat getDateFormat(){
		return Formatter.getDateFormat(PrefsInstance.getInstance().getPrefs().getDateFormat());
	}
}
