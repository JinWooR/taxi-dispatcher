package com.taxidispatcher.modules.account.domain.model;

import java.util.UUID;

public record AccountId(UUID value) {
    public static AccountId newId() {
        return new AccountId(UUID.randomUUID());
    }
}