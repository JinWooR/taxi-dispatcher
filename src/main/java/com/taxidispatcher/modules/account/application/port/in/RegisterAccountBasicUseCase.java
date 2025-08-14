package com.taxidispatcher.modules.account.application.port.in;

import com.taxidispatcher.modules.account.domain.model.AccountId;

public interface RegisterAccountBasicUseCase {
    AccountId handle(RegisterAccountBasicCommand registerAccountBasicCommand);
}
