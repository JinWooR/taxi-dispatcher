package com.taxidispatcher.modules.account.domain.aggregate;

import com.taxidispatcher.modules.account.domain.model.AccountId;
import lombok.Getter;

import java.time.Instant;
import java.util.UUID;

public sealed class Credential permits BasicCredential, OAuthCredential {
    @Getter
    protected final UUID id;
    @Getter
    protected final AccountId accountId;
    @Getter
    protected boolean isLoginEnabled; // 로그인 가능 여부
    @Getter
    protected final Instant createdAt; // 등록시간
    @Getter
    protected Instant lastUsedAt; // 마지막 사용 시간

    protected Credential(UUID id, AccountId accountId, boolean isLoginEnabled, Instant createdAt, Instant lastUsedAt) {
        this.id = id;
        this.accountId = accountId;
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
