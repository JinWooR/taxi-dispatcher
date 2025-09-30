package com.taxidispatcher.modules.dispatcher.adapter.client;

import com.taxidispatcher.modules.dispatcher.adapter.client.dto.DriverActiveStatusRequest;
import com.taxidispatcher.modules.dispatcher.application.port.out.UpdateDriverActiveStatusClient;
import com.taxidispatcher.shared.core.AppException;
import com.taxidispatcher.shared.core.ErrorCode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class UpdateDriverActiveStatusClientImpl implements UpdateDriverActiveStatusClient {
    private final String DRIVER_BASE_URL;
    private final String DRIVER_ACTIVE_STATUS;

    public UpdateDriverActiveStatusClientImpl(
            @Value("${endPoints.driver-bc.base-url}") String DRIVER_BASE_URL,
            @Value("${endPoints.driver-bc.internal-active-status}") String DRIVER_ACTIVE_STATUS
    ) {
        this.DRIVER_BASE_URL = DRIVER_BASE_URL;
        this.DRIVER_ACTIVE_STATUS = DRIVER_ACTIVE_STATUS;
    }

    @Override
    public void handle(DriverActiveStatusRequest request, String driverId) {
        var res = RestClient.create(DRIVER_BASE_URL)
                .post()
                .uri(DRIVER_ACTIVE_STATUS.replace("{driverId}", driverId))
                .body(request)
                .retrieve()
                .toEntity(String.class);

        if (res.getStatusCode().is2xxSuccessful()) {
            return;
        } else {
            throw new AppException(ErrorCode.VALIDATION, res.getBody());
        }
    }
}
