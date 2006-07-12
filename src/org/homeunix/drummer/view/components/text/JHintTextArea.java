/*
 * Created on Jul 11, 2006 by wyatt
 */
package org.homeunix.drummer.view.components.text;

import java.awt.Color;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import javax.swing.JTextArea;

public class JHintTextArea extends JTextArea implements JTextComponentHint {
	public static final long serialVersionUID = 0;

	private String hint;
	private String value = "";
	
	public JHintTextArea(String hint){
		this.hint = hint;
		
		this.addFocusListener(new FocusAdapter(){
			@Override
			public void focusGained(FocusEvent arg0) {
				JHintTextArea.this.showHint(false);
				super.focusGained(arg0);
			}

			@Override
			public void focusLost(FocusEvent arg0) {
				JHintTextArea.this.setValue(JHintTextArea.this.getText());
				JHintTextArea.this.showHint(true);
				super.focusLost(arg0);
			}
		});
		
		showHint(true);
	}
	
	/**
	 * Enables / disables the hint depending on state of value.
	 */
	public void showHint(boolean on){
		if (on && this.getValue().equals("")){
			this.setText(hint);
			this.setForeground(Color.GRAY);
		}
		else{
			this.setText(value);
			this.setForeground(Color.BLACK);
		}
	}
	
	public String getHint() {
		return hint;
	}

	public String getValue() {
		return value;
	}

	public void setHint(String hint) {
		this.hint = hint;
	}

	public void setValue(String value) {
		this.value = value;
		
		this.showHint(true);
	}
}
