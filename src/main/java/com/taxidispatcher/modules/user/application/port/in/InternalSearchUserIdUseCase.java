package com.taxidispatcher.modules.user.application.port.in;

import com.taxidispatcher.shared.core.domain.user.dto.InternalUserResponse;
import com.taxidispatcher.modules.user.domain.model.UserId;

public interface InternalSearchUserIdUseCase {
    InternalUserResponse handle(InternalSearchUserIdCommand command);

    record InternalSearchUserIdCommand(UserId userId) {
    }
}
