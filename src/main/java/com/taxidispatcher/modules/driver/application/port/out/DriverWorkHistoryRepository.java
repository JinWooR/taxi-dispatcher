package com.taxidispatcher.modules.driver.application.port.out;

import com.taxidispatcher.modules.driver.domain.aggregate.DriverWorkHistory;
import com.taxidispatcher.modules.driver.domain.model.DriverId;
import com.taxidispatcher.modules.driver.domain.model.WorkHistoryId;

import java.util.Optional;

public interface DriverWorkHistoryRepository {
    Optional<DriverWorkHistory> findByActiveOne(DriverId driverId);
    WorkHistoryId save(DriverWorkHistory workHistory);
}
