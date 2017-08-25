package org.flips.utils;


public final class ValidationUtils {

    private ValidationUtils() {
        throw new AssertionError("No ValidationUtils instances for you!");
    }

    public static final String requireNonEmpty(String str, String message) {
        boolean isEmpty = Utils.isEmpty(str);
        if ( isEmpty )
            throw new IllegalArgumentException(message);
        return str;
    }

    public static final <T> T[] requireNonEmpty(T[] t, String message) {
        if ( Utils.isEmpty(t) )
            throw new IllegalArgumentException(message);
        return t;
    }

    public static final Object requireNonNull(Object o, String message) {
        if ( o == null )
            throw new IllegalArgumentException(message);
        return o;
    }
}