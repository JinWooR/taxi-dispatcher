package com.taxidispatcher.shared.web;

import com.taxidispatcher.shared.core.ApiError;
import com.taxidispatcher.shared.core.AppException;
import com.taxidispatcher.shared.core.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.security.sasl.AuthenticationException;
import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class BasicExceptionAdvice {

    protected ApiError apiError(ErrorCode code, String msg) {
        return new ApiError(code.getCode(), msg);
    }

    @ExceptionHandler(AppException.class)
    protected ResponseEntity<ApiError> app(AppException e) {
        ErrorCode errCode = e.getErrorCode();

        return ResponseEntity
                .status(errCode.getStatus())
                .body(apiError(errCode, e.getMessage()));
    }

    @ExceptionHandler({MethodArgumentNotValidException.class, BindException.class})
    protected ResponseEntity<ApiError> invalid(Exception e) {
        List<FieldError> fieldErrors = new ArrayList<>();
        if (e instanceof MethodArgumentNotValidException m) {
            fieldErrors = m.getFieldErrors();
        } else if (e instanceof BindException b) {
            fieldErrors = b.getFieldErrors();
        }

        String msg = fieldErrors.stream()
                .map(fe -> fe.getField() + " " + fe.getDefaultMessage() + "(value : " + fe.getRejectedValue() + ")")
                .findFirst().orElse("잘못된 요청");

        return ResponseEntity.badRequest()
                .body(apiError(ErrorCode.VALIDATION, msg));
    }

    @ExceptionHandler(AuthenticationException.class)
    protected ResponseEntity<ApiError> unauthorized(AuthenticationException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(apiError(ErrorCode.UNAUTHORIZED, "인증 실패"));
    }

    @ExceptionHandler({AuthorizationDeniedException.class, AccessDeniedException.class})
    protected ResponseEntity<ApiError> accessDenied(Exception e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(apiError(ErrorCode.FORBIDDEN, "접근 권한이 없습니다."));
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ApiError> internal(Exception e) {
        return ResponseEntity.internalServerError()
                .body(apiError(ErrorCode.INTERNAL, "서버 에러가 발생했습니다."));
    }
}
