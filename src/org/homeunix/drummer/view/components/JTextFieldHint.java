/*
 * Created on May 21, 2006 by wyatt
 */
package org.homeunix.drummer.view.components;

import org.homeunix.drummer.view.components.autocomplete.AutoCompleteDictionary;
import org.homeunix.drummer.view.components.autocomplete.AutoCompleteTextField;

public class JTextFieldHint extends AutoCompleteTextField {
	public static final long serialVersionUID = 0;
	
	private String hint;
	private String value;
	
	public JTextFieldHint(AutoCompleteDictionary dict){
		super(dict);
	}
	
	public JTextFieldHint(AutoCompleteDictionary dict, String hint){
		super(dict);
		
		this.hint = hint;
	}

	public String getHint() {
		return hint;
	}

	public void setHint(String hint) {
		this.hint = hint;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	
}
