/*
 * Created on May 14, 2006 by wyatt
 */
package org.homeunix.drummer.view.components.text;

import javax.swing.JLabel;

import org.homeunix.drummer.TranslateKeys;
import org.homeunix.drummer.Translate;
import org.homeunix.drummer.util.Formatter;

public class JDecimalLabel extends JLabel {
	public static final long serialVersionUID = 0;
	
	public void setValue(Long value){
		super.setText(
				Translate.getInstance().get(TranslateKeys.CURRENCY_SIGN)
				+ Formatter.getInstance().getDecimalFormat().format((double) value / 100.0)
		);
	}
}
