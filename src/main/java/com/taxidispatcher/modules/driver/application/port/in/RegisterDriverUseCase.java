package com.taxidispatcher.modules.driver.application.port.in;

import com.taxidispatcher.modules.driver.domain.aggregate.Driver;

public interface RegisterDriverUseCase {
    Driver handle(RegisterDriverCommand command);
}
