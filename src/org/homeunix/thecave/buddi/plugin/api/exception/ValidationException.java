package org.homeunix.thecave.buddi.plugin.api.exception;

public class ValidationException extends Exception {
	public static final long serialVersionUID = 0;
	
    public ValidationException() {
        super();
    }
    
    public ValidationException(String message) {
        super(message);
    }
    
    public ValidationException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public ValidationException(Throwable cause) {
        super(cause);
    }
}
