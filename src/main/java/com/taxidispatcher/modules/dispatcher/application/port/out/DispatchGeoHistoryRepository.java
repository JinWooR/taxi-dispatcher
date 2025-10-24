package com.taxidispatcher.modules.dispatcher.application.port.out;

import com.taxidispatcher.modules.dispatcher.domain.aggregate.DispatchDriverGeoHistory;

public interface DispatchGeoHistoryRepository {
    void save(DispatchDriverGeoHistory dispatchDriverGeoHistory);
}
