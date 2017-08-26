package org.flips.exception;

public class FlipWithBeanFailedException extends RuntimeException{

    public FlipWithBeanFailedException(String message, Throwable cause) {
        super(message, cause);
    }

    public FlipWithBeanFailedException(Throwable cause) {
        super(cause);
    }

    public FlipWithBeanFailedException(String message) {
        super(message);
    }
}