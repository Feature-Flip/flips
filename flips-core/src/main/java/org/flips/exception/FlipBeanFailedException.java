package org.flips.exception;

public class FlipBeanFailedException extends RuntimeException{

    public FlipBeanFailedException(String message, Throwable cause) {
        super(message, cause);
    }

    public FlipBeanFailedException(Throwable cause) {
        super(cause);
    }

    public FlipBeanFailedException(String message) {
        super(message);
    }
}