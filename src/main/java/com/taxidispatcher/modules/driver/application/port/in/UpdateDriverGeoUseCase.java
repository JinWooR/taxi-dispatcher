package com.taxidispatcher.modules.driver.application.port.in;

import com.taxidispatcher.modules.driver.domain.aggregate.Driver;

public interface UpdateDriverGeoUseCase {
    Driver handle(UpdateDriverGeoCommand command);
}
