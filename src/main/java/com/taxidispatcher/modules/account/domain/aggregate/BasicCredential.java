package com.taxidispatcher.modules.account.domain.aggregate;

import com.taxidispatcher.modules.account.domain.model.AccountId;
import com.taxidispatcher.modules.account.domain.model.HashPassword;
import com.taxidispatcher.modules.account.domain.model.LoginIdentifier;
import com.taxidispatcher.modules.account.domain.model.Version;
import lombok.EqualsAndHashCode;

import java.time.Instant;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
public final class BasicCredential extends Credential {
    private LoginIdentifier loginIdentifier; // 로그인 식별 정보
    private HashPassword hashPassword; // 암호화된 비밀번호

    private BasicCredential(UUID id, AccountId accountId, boolean isLoginEnabled, Instant createdAt, Instant lastUsedAt, LoginIdentifier loginIdentifier, HashPassword hashPassword) {
        super(id, accountId, isLoginEnabled, createdAt, lastUsedAt);
        this.loginIdentifier = loginIdentifier;
        this.hashPassword = hashPassword;
    }

    public static BasicCredential of(UUID id, AccountId accountId, boolean isLoginEnabled, Instant createdAt, Instant lastUsedAt, LoginIdentifier loginIdentifier, HashPassword hashPassword) {
        return new BasicCredential(id, accountId, isLoginEnabled, createdAt, lastUsedAt, loginIdentifier, hashPassword);
    }

    public LoginIdentifier getLoginIdentifier() {
        return loginIdentifier;
    }

    public HashPassword getHashPassword() {
        return hashPassword;
    }
}
