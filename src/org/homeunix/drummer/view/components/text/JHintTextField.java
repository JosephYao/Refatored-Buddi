/*
 * Created on Jul 11, 2006 by wyatt
 */
package org.homeunix.drummer.view.components.text;

import java.awt.Color;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import javax.swing.JTextField;

public class JHintTextField extends JTextField implements JTextComponentHint {
	public static final long serialVersionUID = 0;

	private String hint;
	private String value = "";
	
	public JHintTextField(String hint){
		this.hint = hint;
		
		this.addFocusListener(new FocusAdapter(){
			@Override
			public void focusGained(FocusEvent arg0) {
				JHintTextField.this.showHint(false);
				super.focusGained(arg0);
			}

			@Override
			public void focusLost(FocusEvent arg0) {
				JHintTextField.this.setValue(JHintTextField.this.getText());
				JHintTextField.this.showHint(true);
				super.focusLost(arg0);
			}
		});
		
		showHint(true);
		
//		this.getDocument().addDocumentListener(new DocumentListener(){
//
//			public void changedUpdate(DocumentEvent arg0) {
//				// TODO Auto-generated method stub
//				if (!JHintTextField.this.getText().equals(JHintTextField.this.hint))
//					JHintTextField.this.setValueInternal(JHintTextField.this.getText());
//				else
//					JHintTextField.this.setValueInternal("");
//			}
//
//			public void insertUpdate(DocumentEvent arg0) {
//				// TODO Auto-generated method stub
//				if (!JHintTextField.this.getText().equals(JHintTextField.this.hint))
//					JHintTextField.this.setValueInternal(JHintTextField.this.getText());
//				else
//					JHintTextField.this.setValueInternal("");
//				
//			}
//
//			public void removeUpdate(DocumentEvent arg0) {
//				// TODO Auto-generated method stub
//				if (!JHintTextField.this.getText().equals(JHintTextField.this.hint))
//					JHintTextField.this.setValueInternal(JHintTextField.this.getText());
//				else
//					JHintTextField.this.setValueInternal("");
//
//			}
//			
//		});
		
//		this.addKeyListener(new KeyListener(){
//			public void keyPressed(KeyEvent arg0) {
//				JHintTextField.this.setValueInternal(JHintTextField.this.getText());
//			}
//
//			public void keyReleased(KeyEvent arg0) {
//				JHintTextField.this.setValueInternal(JHintTextField.this.getText());				
//			}
//
//			public void keyTyped(KeyEvent arg0) {
//				JHintTextField.this.setValueInternal(JHintTextField.this.getText());				
//			}
//		});
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
