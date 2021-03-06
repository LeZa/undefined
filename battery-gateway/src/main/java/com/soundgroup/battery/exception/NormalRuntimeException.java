package com.soundgroup.battery.exception;

/**
 * 常规异常
 */
public class NormalRuntimeException extends RuntimeException {

    private static final long serialVersionUID = 1112368490033399874L;

    public NormalRuntimeException() {
        super();
    }

    public NormalRuntimeException(String message, Throwable cause, boolean enableSuppression,
            boolean writableStackTrace) {
        super();
    }

    public NormalRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public NormalRuntimeException(String message) {
        super(message);
    }

    public NormalRuntimeException(Throwable cause) {
        super(cause);
    }

}
