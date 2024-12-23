package com.usage.common.exception;

import lombok.Getter;

@Getter
public class InfraException extends RuntimeException {
    private final int errorCode;

    public InfraException(String message) {
        super(message);
        this.errorCode = 500;
    }

    public InfraException(int errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }
}
