package org.flips.exception;

public class SpringExpressionEvaluationFailureException extends RuntimeException {

    public SpringExpressionEvaluationFailureException(String message, Throwable cause) {
        super(message, cause);
    }

    public SpringExpressionEvaluationFailureException(String message) {
        super(message);
    }
}
