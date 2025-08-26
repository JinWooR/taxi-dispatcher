package com.taxidispatcher.modules.driver.application.port.in;

import com.taxidispatcher.modules.driver.domain.model.Taxi;

import java.util.UUID;

public record RegisterDriverCommand(
        UUID accountId,
        String name,
        Taxi taxi
) {
}
