package com.taxidispatcher.modules.user.application.port.in;

import com.taxidispatcher.modules.user.domain.model.UserId;

public interface RegisterUserUseCase {
    UserId handle(RegisterUserCommand command);
}
