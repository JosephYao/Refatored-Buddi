/**
 * 
 */
package org.homeunix.thecave.buddi.model.swing;

import org.homeunix.thecave.buddi.model.Source;

public class IndentedSourceWrapper {
	final Source source;
	final int indent;
	
	public IndentedSourceWrapper(Source source, int indent) {
		this.source = source;
		this.indent = indent;
	}
	
	public int getIndent() {
		return indent;
	}
	
	public Source getSource() {
		return source;
	}
}