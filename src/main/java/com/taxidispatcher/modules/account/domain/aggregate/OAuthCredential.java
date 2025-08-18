package com.taxidispatcher.modules.account.domain.aggregate;

import com.taxidispatcher.modules.account.domain.model.AccountId;
import com.taxidispatcher.modules.account.domain.model.OAuthKind;
import lombok.EqualsAndHashCode;

import java.time.Instant;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
public final class OAuthCredential extends Credential {
    OAuthKind oAuthKind;
    String iss; // 발급자
    String sub; // 발급 주체의 사용자 고유 식별자
    String emailLink; // 이메일(표기용). 검증 및 식별에는 사용 X

    private OAuthCredential(UUID id, AccountId accountId, boolean isLoginEnabled, Instant createdAt, Instant lastUsedAt, OAuthKind oAuthKind, String iss, String sub, String emailLink) {
        super(id, accountId, isLoginEnabled, createdAt, lastUsedAt);
        this.oAuthKind = oAuthKind;
        this.iss = iss;
        this.sub = sub;
        this.emailLink = emailLink;
    }

    public static OAuthCredential of(UUID id, AccountId accountId, boolean isLoginEnabled, Instant createdAt, Instant lastUsedAt, OAuthKind oAuthKind, String iss, String sub, String emailLink) {
        return new OAuthCredential(id, accountId, isLoginEnabled, createdAt, lastUsedAt, oAuthKind, iss, sub, emailLink);
    }

    public OAuthKind getoAuthKind() {
        return oAuthKind;
    }

    public String getIss() {
        return iss;
    }

    public String getSub() {
        return sub;
    }

    public String getEmailLink() {
        return emailLink;
    }
}
