package org.flips.describe.controlleradvice;

import org.flips.describe.model.FeatureNotEnabledErrorResponse;
import org.flips.exception.FeatureNotEnabledException;
import org.flips.exception.FlipBeanFailedException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@Order(Ordered.LOWEST_PRECEDENCE)
public class FlipControllerAdvice {

    @ExceptionHandler(FeatureNotEnabledException.class)
    @ResponseStatus  (HttpStatus.NOT_IMPLEMENTED)
    @ResponseBody
    public FeatureNotEnabledErrorResponse handleFeatureNotEnabledException(FeatureNotEnabledException ex) {
        return new FeatureNotEnabledErrorResponse(ex);
    }

    @ExceptionHandler(FlipBeanFailedException.class)
    @ResponseStatus  (HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public String handleFlipBeanFailedException(FlipBeanFailedException ex) {
        return ex.getMessage();
    }
}