package com.taxidispatcher.modules.account.domain.model;

import lombok.Value;

@Value
public class LoginIdentifier {
    IdentifierKind identifierKind;
    String loginId;
    String hashPassword;

    private LoginIdentifier(IdentifierKind identifierKind, String loginId, String hashPassword) {
        this.identifierKind = identifierKind;
        this.loginId = normalizeLoginId(identifierKind, loginId);
        this.hashPassword = hashPassword;

        validate();
    }

    public LoginIdentifier of(IdentifierKind identifierKind, String loginId, String hashPassword) {
        return new LoginIdentifier(identifierKind, loginId, hashPassword);
    }

    private String normalizeLoginId(IdentifierKind identifierKind, String loginId) {
        if (loginId == null) return null;

        if (IdentifierKind.PHONE.equals(identifierKind)) {
            loginId = loginId.replaceAll("-", "");
        }

        return loginId.trim();
    }

    private void validate() {
        if (!this.identifierKind.validate(this.loginId)) {
            throw new IllegalStateException("로그인 수단 유효성 검사 오류 : " + this.identifierKind + " / " + this.loginId);
        }
    }
}
