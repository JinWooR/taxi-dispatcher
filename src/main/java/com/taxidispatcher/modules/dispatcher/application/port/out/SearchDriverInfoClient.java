package com.taxidispatcher.modules.dispatcher.application.port.out;

import com.taxidispatcher.modules.dispatcher.adapter.client.dto.DriverInfoResponse;

import java.util.UUID;

public interface SearchDriverInfoClient {
    DriverInfoResponse handle(UUID driverId);
}
