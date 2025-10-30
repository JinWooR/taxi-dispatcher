package com.taxidispatcher.modules.user.application.port.in;

import com.taxidispatcher.modules.user.adapter.web.dto.response.InternalUserResponse;
import com.taxidispatcher.modules.user.domain.model.UserId;

public interface InternalSearchUserIdUseCase {
    InternalUserResponse handle(InternalSearchUserIdCommand command);

    record InternalSearchUserIdCommand(UserId userId) {
    }
}
