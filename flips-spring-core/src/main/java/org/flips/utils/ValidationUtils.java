package org.flips.utils;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

public final class ValidationUtils {

    private ValidationUtils() {
        throw new AssertionError("No ValidationUtils instances for you!");
    }

    public static final String requireNonEmpty(String str, String message) {
        boolean isEmpty = StringUtils.isEmpty(str);
        if ( isEmpty )
            throw new IllegalArgumentException(message);
        return str;
    }

    public static final <T> T[] requireNonEmpty(T[] t, String message) {
        if ( ArrayUtils.isEmpty(t) )
            throw new IllegalArgumentException(message);
        return t;
    }

    public static final Object requireNonNull(Object o, String message) {
        if ( o == null )
            throw new IllegalArgumentException(message);
        return o;
    }
}