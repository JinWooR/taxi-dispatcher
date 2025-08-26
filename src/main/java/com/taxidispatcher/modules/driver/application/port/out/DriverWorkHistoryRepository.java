package com.taxidispatcher.modules.driver.application.port.out;

import com.taxidispatcher.modules.driver.domain.aggregate.DriverWorkHistory;
import com.taxidispatcher.modules.driver.domain.model.WorkHistoryId;

public interface DriverWorkHistoryRepository {
    WorkHistoryId save(DriverWorkHistory workHistory);
}
