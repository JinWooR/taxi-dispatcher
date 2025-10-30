package com.taxidispatcher.modules.dispatcher.application.port.out;

import com.taxidispatcher.shared.core.domain.user.dto.InternalUserResponse;

import java.util.UUID;

public interface SearchUserInfoClient {
    InternalUserResponse handle(UUID userId);
}
