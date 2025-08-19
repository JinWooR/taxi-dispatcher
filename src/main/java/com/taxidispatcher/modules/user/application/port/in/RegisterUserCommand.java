package com.taxidispatcher.modules.user.application.port.in;

import com.taxidispatcher.modules.user.domain.model.Address;
import com.taxidispatcher.modules.user.domain.model.City;

import java.util.UUID;

public record RegisterUserCommand(
        UUID accountId,
        String name,
        City city,
        Address address
) {
}
