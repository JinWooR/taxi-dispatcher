package com.taxidispatcher.shared.core;

import lombok.Getter;

import java.util.Collections;
import java.util.Map;

@Getter
public class AppException extends RuntimeException {
    private final ErrorCode errorCode;
    private final Map<String, Object> errParams;

    public AppException(ErrorCode errorCode, Map<String, Object> errParams, String msg) {
        super(msg);
        this.errorCode = errorCode;
        this.errParams = errParams == null ? Collections.emptyMap() : errParams;
    }

    public AppException(ErrorCode errorCode, Map<String, Object> errParams) {
        throw new AppException(errorCode, errParams, errorCode.getDefaultMsg());
    }

    public AppException(ErrorCode errorCode, String msg) {
        throw new AppException(errorCode, Collections.emptyMap(), msg);
    }

}
