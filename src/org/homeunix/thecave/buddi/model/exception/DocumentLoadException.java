/*
 * Created on Aug 3, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.model.exception;

public class DocumentLoadException extends Exception {
	public static final long serialVersionUID = 0;
	
	public DocumentLoadException() {
		super();
	}
	public DocumentLoadException(String message) {
		super(message);
	}
	public DocumentLoadException(Throwable cause) {
		super(cause);
	}
	public DocumentLoadException(String message, Throwable cause) {
		super(message, cause);
	}
}
