package com.taxidispatcher.modules.user.application.port.in;

import java.util.UUID;

public record RegisterUserCommand(
        UUID accountId,
        String name,
        String address,
        String addressDetail
) {
}
