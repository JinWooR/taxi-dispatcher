package com.taxidispatcher.modules.driver.domain.model;

import java.util.UUID;

public record DriverId(UUID id) {
    public static DriverId strId(String id) {
        return new DriverId(UUID.fromString(id));
    }
    public static DriverId newId() {
        return new DriverId(UUID.randomUUID());
    }
}
