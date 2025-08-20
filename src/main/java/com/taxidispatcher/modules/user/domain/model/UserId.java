package com.taxidispatcher.modules.user.domain.model;

import lombok.Value;

import java.util.UUID;

@Value
public class UserId {
    UUID value;

    private UserId(UUID value) {
        this.value = value;
    }

    public static UserId of(UUID value) {
        return new UserId(value);
    }

    public static UserId of(String id) {
        return new UserId(UUID.fromString(id));
    }

    public UUID value() {
        return value;
    }

    public static UserId newId() {
        return new UserId(UUID.randomUUID());
    }
}
