package com.taxidispatcher.modules.dispatcher.domain.model;

import java.util.UUID;

public record DispatchId(UUID id) {
    public static DispatchId newId() {
        return new DispatchId(UUID.randomUUID());
    }
}
