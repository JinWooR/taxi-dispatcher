package com.taxidispatcher.modules.account.domain.aggregate;

import com.taxidispatcher.modules.account.domain.model.AccountId;
import com.taxidispatcher.modules.account.domain.model.Version;

import java.time.Instant;
import java.util.UUID;

public sealed class Credential permits BasicCredential, OAuthCredential {
    protected final UUID id;
    protected final AccountId accountId;
    protected final Version version;
    protected boolean isLoginEnabled; // 로그인 가능 여부
    protected final Instant createdAt; // 등록시간
    protected Instant lastUsedAt; // 마지막 사용 시간

    protected Credential(UUID id, AccountId accountId, Version version, boolean isLoginEnabled, Instant createdAt, Instant lastUsedAt) {
        this.id = id;
        this.accountId = accountId;
        this.version = version;
        this.isLoginEnabled = isLoginEnabled;
        this.createdAt = createdAt;
        this.lastUsedAt = lastUsedAt;
    }

    public void enableLogin() {
        this.isLoginEnabled = true;
    }

    public void disableLogin() {
        this.isLoginEnabled = false;
    }

    public void recentLastUse() {
        this.lastUsedAt = Instant.now();
    }
}
