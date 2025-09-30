package com.taxidispatcher.modules.dispatcher.application.port.out;

import com.taxidispatcher.modules.dispatcher.adapter.client.dto.DriverActiveStatusRequest;

public interface UpdateDriverActiveStatusClient {
    void handle(DriverActiveStatusRequest request, String driverId);
}
