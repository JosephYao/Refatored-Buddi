/*
 * Created on May 9, 2006 by wyatt
 * 
 * Accepts only numbers, decimals, and commas (internationalization).  Can be used
 * to accept currencies, etc.
 * 
 * To use for currency, set as follows:
 * 
 * 		NumberFormat nf = DecimalFormat.getInstance();
 *		nf.setMinimumFractionDigits(2);
 *		nf.setMaximumFractionDigits(2);
 *	
 *		JNumberField ftf = new JNumberField(nf);
 * 
 * 
 */
package org.homeunix.drummer.view.components;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyEvent;
import java.text.Format;

import javax.swing.JFormattedTextField;

public class JNumberField extends JFormattedTextField {
	public static final long serialVersionUID = 0;
	
	private final static String badchars = "-`~!@#$%^&*()_+=\\|\"':;?/>< ";
	//private int value;
	
	public JNumberField(){
		super();
		initActions();
	}
	public JNumberField(Format arg0){
		super(arg0);
		initActions();
	}
	public JNumberField(AbstractFormatter arg0){
		super(arg0);
		initActions();
	}
	public JNumberField(AbstractFormatterFactory arg0){
		super(arg0);
		initActions();
	}
	public JNumberField(AbstractFormatterFactory arg0, Object arg1){
		super(arg0, arg1);
		initActions();
	}
	public JNumberField(Object arg0){
		super(arg0);
		initActions();
	}

	private void initActions(){
		this.addFocusListener(new FocusAdapter(){
			public void focusGained(FocusEvent arg0) {
				//[TODO] Add selction stuff
				JNumberField.this.setSelectionStart(0);
				JNumberField.this.setSelectionEnd(JNumberField.this.getText().length());
				super.focusGained(arg0);
			}
		});
	}
	
	public void processKeyEvent(KeyEvent ev) {
		char c = ev.getKeyChar();
		int v = ev.getKeyCode();
				
		if (v == KeyEvent.VK_ENTER){
			super.processKeyEvent(ev);
			super.processKeyEvent(ev);
		}
		
		if((ev.getModifiers() == 0 
				&& Character.isLetter(c) 
				&& !ev.isAltDown()
				&& v != KeyEvent.VK_ENTER) 
				|| badchars.indexOf(c) > -1) {
			ev.consume();
			return;
		}
		
//		We do not want negatives in this application
//		if(c == '-' && getDocument().getLength() > 0) 
//			ev.consume();
		
		super.processKeyEvent(ev);
	}
}
