package com.taxidispatcher.modules.dispatcher.adapter.client;

import com.taxidispatcher.modules.dispatcher.adapter.client.dto.DriverInfoResponse;
import com.taxidispatcher.modules.dispatcher.application.port.out.SearchDriverInfoClient;
import com.taxidispatcher.shared.core.AppException;
import com.taxidispatcher.shared.core.ErrorCode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.UUID;

@Component
public class SearchDriverInfoClientImpl implements SearchDriverInfoClient {
    private final String DRIVER_BASE_URL;
    private final String DRIVER_INFO;

    public SearchDriverInfoClientImpl(
            @Value("${endPoints.driver-bc.base-url}") String DRIVER_BASE_URL,
            @Value("${endPoints.driver-bc.internal-driver-id}") String DRIVER_INFO
    ) {
        this.DRIVER_BASE_URL = DRIVER_BASE_URL;
        this.DRIVER_INFO = DRIVER_INFO;
    }

    @Override
    public DriverInfoResponse handle(UUID driverId) {
        var res = RestClient.create(DRIVER_BASE_URL)
                .get()
                .uri(DRIVER_INFO, driverId.toString())
                .retrieve()
                .toEntity(DriverInfoResponse.class);

        if (res.getStatusCode().is2xxSuccessful()) {
            return res.getBody();
        } else {
            throw new AppException(ErrorCode.VALIDATION, "DRIVER-INFO 조회 실패");
        }
    }
}
