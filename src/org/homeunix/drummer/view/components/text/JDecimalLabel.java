/*
 * Created on May 14, 2006 by wyatt
 */
package org.homeunix.drummer.view.components.text;

import javax.swing.JLabel;

import org.homeunix.drummer.prefs.PrefsInstance;
import org.homeunix.drummer.util.Formatter;

public class JDecimalLabel extends JLabel {
	public static final long serialVersionUID = 0;
	
	public void setValue(Long value){
		super.setText(
				PrefsInstance.getInstance().getPrefs().getCurrencySymbol()
				+ Formatter.getInstance().getDecimalFormat().format((double) value / 100.0)
		);
	}
}
