package org.homeunix.drummer.view.components.text;

import java.awt.Toolkit;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.DecimalFormat;
import java.text.Format;
import java.text.NumberFormat;
import java.text.ParseException;

import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

import org.homeunix.drummer.Const;
import org.homeunix.drummer.util.Formatter;
import org.homeunix.drummer.util.Log;

/**
 * Copied and modified from http://rangiroa.essi.fr/cours/turorial%20java/uiswing/components/textfield.html
 * I could have continued using the old JNumberField, but this one allows 
 * you to select all programmatically, which the other did not.
 * 
 * I have modified this class to give access to the value as a long, 
 * not a double.  This is for currency - we assume that everything here
 * is entered in cents.
 */
public class JDecimalField extends JTextField {
	public static final long serialVersionUID = 0;
	
	private NumberFormat format;
	private final NumberFormat decimalFormat;
	private final static String badchars = "-`~!@#$%^&*()_+=\\|\"':;?/>< ";
	
	
	public JDecimalField(long value, int columns, NumberFormat f) {
		super(columns);
		setDocument(new FormattedDocument(f));
		format = f;

		decimalFormat = DecimalFormat.getInstance();
		decimalFormat.setMaximumFractionDigits(2);
		decimalFormat.setMinimumFractionDigits(2);

		setValue(value);
		this.addFocusListener(new FocusAdapter(){
			@Override
			public void focusLost(FocusEvent e) {
				if (!checkFormat())
					return;
				super.focusLost(e);
			}
		});
		
		this.addKeyListener(new KeyAdapter(){
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER){
					if (!checkFormat())
						return;
				}
				super.keyPressed(e);
			}
		});
	}
	
	private boolean checkFormat(){
		try{
			Number value = Formatter.getInstance().getDecimalFormat().parse(this.getText());
			this.setText(decimalFormat.format(value));
			return true;
		}
		catch (ParseException pe){
			return false;
		}
		
	}
	
	public long getValue() {
//		double retVal = 0.0;
		
		String value = getText().replaceAll("\\D", "");
		
		long amount = Long.parseLong(value);
		
//		try {
//			retVal = format.parse(getText()).doubleValue();
//		} catch (ParseException e) {
//			// This should never happen because insertString allows
//			// only properly formatted data to get in the field.
//			Toolkit.getDefaultToolkit().beep();
//			Log.error("getValue: could not parse: " + getText());
//		}
//		
//		if (Const.DEVEL) Log.debug("Double value: " + retVal);
//		if (Const.DEVEL) Log.debug(retVal * 100000.0);
//		
//		
//		// The weird 100000 / 1000 instead of simple *100 is to try
//		// to bypass rounding errors when converting from double
//		// to int.  This seems to work fine now.
//		long amount = (long) (retVal * 100000.0) / 1000;
		if (Const.DEVEL) Log.debug("Long value: " + amount);
		return amount;
	}
	
	public void setValue(long value) {
		double dValue = (double) value / 100.0;
		setText(format.format(dValue));
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
	
	public class FormattedDocument extends PlainDocument {
		public static final long serialVersionUID = 0;
		
		private Format format;
		
		public FormattedDocument(Format f) {
			format = f;
		}
		
		public Format getFormat() {
			return format;
		}
		
		public void insertString(int offs, String str, AttributeSet a) 
		throws BadLocationException {
			
			String currentText = getText(0, getLength());
			String beforeOffset = currentText.substring(0, offs);
			String afterOffset = currentText.substring(offs, currentText.length());
			String proposedResult = beforeOffset + str + afterOffset;
			
			try {
				format.parseObject(proposedResult);
				super.insertString(offs, str, a);
			} catch (ParseException e) {
				Toolkit.getDefaultToolkit().beep();
				System.err.println("insertString: could not parse: "
						+ proposedResult);
			}
		}
		
		public void remove(int offs, int len) throws BadLocationException {
			String currentText = getText(0, getLength());
			String beforeOffset = currentText.substring(0, offs);
			String afterOffset = currentText.substring(len + offs,
					currentText.length());
			String proposedResult = beforeOffset + afterOffset;
			
			try {
				if (proposedResult.length() != 0)
					format.parseObject(proposedResult);
				super.remove(offs, len);
			} catch (ParseException e) {
				Toolkit.getDefaultToolkit().beep();
				System.err.println("remove: could not parse: " + proposedResult);
			}
		}
	}
	
}