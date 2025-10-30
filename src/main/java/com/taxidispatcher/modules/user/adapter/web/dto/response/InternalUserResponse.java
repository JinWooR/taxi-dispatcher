package com.taxidispatcher.modules.user.adapter.web.dto.response;

import java.util.UUID;

public record InternalUserResponse(
        UUID userId,
        String name
) {
}
