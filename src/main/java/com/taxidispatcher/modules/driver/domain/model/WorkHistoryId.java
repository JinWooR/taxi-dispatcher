package com.taxidispatcher.modules.driver.domain.model;

import java.util.UUID;

public record WorkHistoryId(UUID id) {
    public static WorkHistoryId newId() {
        return new WorkHistoryId(UUID.randomUUID());
    }
}
