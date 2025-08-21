package com.taxidispatcher.modules.user.application.port.in;

import com.taxidispatcher.modules.user.adapter.web.dto.response.UserResponse;

public interface UpdateUserNameUseCase {
    UserResponse handle(UpdateUserNameCommand command);
}
