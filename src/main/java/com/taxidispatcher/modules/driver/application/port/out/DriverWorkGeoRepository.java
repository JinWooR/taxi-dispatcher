package com.taxidispatcher.modules.driver.application.port.out;

import com.taxidispatcher.modules.driver.domain.aggregate.DriverWorkGeo;
import com.taxidispatcher.modules.driver.domain.model.WorkGeoId;

public interface DriverWorkGeoRepository {
    WorkGeoId save(DriverWorkGeo workGeo);
}
