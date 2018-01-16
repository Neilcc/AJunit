package com.zcc.ajunit;

/**
 * Created by cc on 2018/1/10.
 */
class AsyncTestException extends Exception {
    public AsyncTestException(String message, Throwable cause) {
        super(message, cause);
    }

    public static AsyncTestException getException(Throwable e) {
        e.printStackTrace();
        String message = e.getMessage() != null ? e.getMessage() : e.getLocalizedMessage();
        if (message == null) message = "async thread exception";
        return new AsyncTestException("Async test failed: " + message, e);
    }
}
