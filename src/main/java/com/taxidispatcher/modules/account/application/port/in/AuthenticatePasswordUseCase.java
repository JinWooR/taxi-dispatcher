package com.taxidispatcher.modules.account.application.port.in;

public interface AuthenticatePasswordUseCase {
    String handle(AuthenticatePasswordCommand authenticatePasswordCommand);
}
