package com.taxidispatcher.modules.driver.application.port.in;

import com.taxidispatcher.modules.driver.adapter.web.dto.response.InternalDriverAccountResponse;

public interface InternalSearchDriverAccountUseCase {
    InternalDriverAccountResponse handle(InternalSearchDriverAccountCommand command);
}
