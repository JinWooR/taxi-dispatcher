package com.taxidispatcher.modules.account.domain.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Value;

import java.util.UUID;

@Value
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class AccountId {
    UUID value;

    public static AccountId newId() {
        return new AccountId(UUID.randomUUID());
    }
}
