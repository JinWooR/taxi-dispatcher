package com.taxidispatcher.modules.user.domain.model.value;

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

    public UUID value() {
        return value;
    }
}
