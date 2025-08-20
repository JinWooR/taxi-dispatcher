package com.taxidispatcher.modules.user.application.port.in;

import com.taxidispatcher.modules.user.adapter.web.dto.response.InternalUserAccountResponse;

public interface InternalSearchUserAccountUseCase {
    InternalUserAccountResponse handle(InternalSearchUserAccountCommand command);
}
