package com.taxidispatcher.modules.user.application.port.in;

public interface RegisterUserUseCase {
    void handle(RegisterUserCommand command);
}
