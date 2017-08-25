package org.flips.describe.controlleradvice;

import org.flips.describe.model.FeatureNotEnabledErrorResponse;
import org.flips.exception.FeatureNotEnabledException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@Order(Ordered.LOWEST_PRECEDENCE)
public class FeatureNotEnabledExceptionControllerAdvice {

    @ExceptionHandler(FeatureNotEnabledException.class)
    @ResponseStatus  (HttpStatus.NOT_IMPLEMENTED)
    public @ResponseBody
    FeatureNotEnabledErrorResponse handleFeatureNotEnabledException(FeatureNotEnabledException ex) {
        return new FeatureNotEnabledErrorResponse(ex);
    }
}