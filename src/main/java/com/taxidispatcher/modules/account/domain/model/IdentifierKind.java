package com.taxidispatcher.modules.account.domain.model;

import java.util.regex.Pattern;

public enum IdentifierKind {
    ID("^[a-zA-Z][a-zA-Z0-9._@-]{3,44}$"), // 로그인 아이디 정규식 (첫 시작 영문 대소문자 / 4~45자 입력 가능) (입력 허용 문자 : 영문대소문자 / 숫자 / . / _ / @ / -)
    EMAIL("^[0-9a-zA-Z]([-_\\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\\.]?[0-9a-zA-Z])*\\.[a-zA-Z]{2,3}$"), // 이메일 정규식
    PHONE("^01[016-9]\\d{7,8}$") // "-" 없이 입력
    ;

    private final Pattern pattern;

    IdentifierKind(String regex) {
        pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
    }

    public boolean validate(String value) {
        return pattern
                .matcher(value)
                .matches();
    }
}
