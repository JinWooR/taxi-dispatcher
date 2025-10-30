package com.taxidispatcher.shared.core.domain.user.dto;

import java.util.UUID;

public record InternalUserResponse(
        UUID userId,
        String name
) {
}
