package com.taxidispatcher.modules.account.domain.model;

public record LoginIdentifier(IdentifierKind identifierKind, String loginId) {
    public static LoginIdentifier of(IdentifierKind identifierKind, String loginId) {
        if (identifierKind == null || loginId == null || loginId.isBlank()) {
            throw new IllegalArgumentException("Do not Empty!");
        }

        String normalizedLoginId = switch (identifierKind) {
            case EMAIL, ID -> loginId.trim();
            case PHONE -> loginId.replaceAll("-", "").trim();
        };

        if (!identifierKind.validate(normalizedLoginId)) {
            throw new IllegalArgumentException("로그인 수단 유효성 검사 오류 : " + identifierKind + " / " + normalizedLoginId);
        }

        return new LoginIdentifier(identifierKind, normalizedLoginId);
    }
}