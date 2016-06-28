package com.anderson.exceptions;

/**
 * Created by cesarferreira on 10/1/14.
 */
public class InitNotSetException extends RuntimeException {
    public InitNotSetException() {
        super("AndroidUtil 未初始化");
    }

    public InitNotSetException(String detailMessage) {
        super(detailMessage);
    }

    public InitNotSetException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public InitNotSetException(Throwable throwable) {
        super(throwable);
    }
}
