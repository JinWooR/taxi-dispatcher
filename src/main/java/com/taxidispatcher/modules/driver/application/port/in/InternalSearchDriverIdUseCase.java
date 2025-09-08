package com.taxidispatcher.modules.driver.application.port.in;

import com.taxidispatcher.modules.driver.adapter.web.dto.response.InternalDriverResponse;

public interface InternalSearchDriverIdUseCase {
    InternalDriverResponse handle(InternalSearchDriverIdCommand command);
}
