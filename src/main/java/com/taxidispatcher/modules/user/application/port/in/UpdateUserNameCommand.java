package com.taxidispatcher.modules.user.application.port.in;

import com.taxidispatcher.modules.user.domain.model.UserId;

public record UpdateUserNameCommand(
        UserId userId,
        String name
) {
}
