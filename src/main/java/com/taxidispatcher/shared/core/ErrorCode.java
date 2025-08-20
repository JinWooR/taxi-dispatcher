package com.taxidispatcher.shared.core;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ErrorCode {
    VALIDATION("400"),
    UNAUTHORIZED("401"),
    FORBIDDEN("403"),
    NOT_FOUND("404"),
    INTERNAL("500");

    private final String code;

    public String getCode() {
        return code;
    }
}
