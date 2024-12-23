package com.telco.common.exception;

import lombok.Getter;

@Getter
public class BizException extends RuntimeException {
    private final int errorCode;

    public BizException(String message, int errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
