package com.usage.common.exception;

import lombok.Getter;

@Getter
public class BizException extends RuntimeException {
    private final int errorCode;

    public BizException(String message) {
        super(message);
        this.errorCode = 500;
    }

    public BizException(int errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }
}
