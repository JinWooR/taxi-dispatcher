package com.taxidispatcher.modules.account.domain.aggregate;

import com.taxidispatcher.modules.account.domain.model.AccountId;
import com.taxidispatcher.modules.account.domain.model.LoginIdentifier;
import com.taxidispatcher.modules.account.domain.model.Version;
import lombok.EqualsAndHashCode;

import java.time.Instant;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
public final class BasicCredential extends Credential {
    private LoginIdentifier loginIdentifier; // 로그인 식별 정보

    private BasicCredential(UUID id, AccountId accountId, Version version, boolean isLoginEnabled, Instant createdAt, Instant lastUsedAt, LoginIdentifier loginIdentifier) {
        super(id, accountId, version, isLoginEnabled, createdAt, lastUsedAt);
        this.loginIdentifier = loginIdentifier;
    }

    public static BasicCredential of(UUID id, AccountId accountId, Version version, boolean isLoginEnabled, Instant createdAt, Instant lastUsedAt, LoginIdentifier loginIdentifier) {
        return new BasicCredential(id, accountId, version, isLoginEnabled, createdAt, lastUsedAt, loginIdentifier);
    }

    public LoginIdentifier getLoginIdentifier() {
        return loginIdentifier;
    }
}
