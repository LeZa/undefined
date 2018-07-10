package com.soundgroup.battery.exception;

public class RespException extends RuntimeException {

    private static final long serialVersionUID = -3910639417037386503L;

    public RespException() {
        super();
        // TODO Auto-generated constructor stub
    }

    public RespException(String message, Throwable cause, boolean enableSuppression,
            boolean writableStackTrace) {
        super();
    }

    public RespException(String message, Throwable cause) {
        super(message, cause);
    }

    public RespException(String message) {
        super(message);
    }

    public RespException(Throwable cause) {
        super(cause);
    }

}
