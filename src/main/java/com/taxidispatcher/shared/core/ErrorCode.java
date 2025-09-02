package com.taxidispatcher.shared.core;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
public enum ErrorCode {
    VALIDATION(HttpStatus.BAD_REQUEST, "400", "잘못된 요청입니다."),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "401", "인증 실패."),
    FORBIDDEN(HttpStatus.FORBIDDEN, "403", "접근 권한이 없습니다."),
    NOT_FOUND(HttpStatus.NOT_FOUND, "404", "리소스를 찾을 수 없습니다."),
    CONFLICT(HttpStatus.CONFLICT, "409", "요청이 현재 리소스와 충돌합니다."),
    INTERNAL(HttpStatus.INTERNAL_SERVER_ERROR, "500", "서버 오류가 발생했습니다")
    ;

    private final HttpStatus status;
    private final String code;
    private final String defaultMsg;

    public HttpStatus getStatus() {
        return status;
    }

    public String getCode() {
        return code;
    }

    public String getDefaultMsg() {
        return defaultMsg;
    }
}
