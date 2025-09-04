package com.taxidispatcher.modules.dispatcher.adapter.client;

import com.taxidispatcher.modules.dispatcher.adapter.client.dto.CandidateDriverRequest;
import com.taxidispatcher.modules.dispatcher.application.port.out.FindDispatchCandidateDriverClient;
import com.taxidispatcher.shared.core.AppException;
import com.taxidispatcher.shared.core.ErrorCode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.UUID;

@Component
public class FindDispatchCandidateDriverClientImpl implements FindDispatchCandidateDriverClient {
    private final String DRIVER_BASE_URL;
    private final String DRIVER_DISPATCH_CANDIDATE;

    public FindDispatchCandidateDriverClientImpl(
            @Value("${endPoints.driver-bc.base-url}") String DRIVER_BASE_URL,
            @Value("${endPoints.driver-bc.internal-dispatch-candidate}") String DRIVER_DISPATCH_CANDIDATE
    ) {
        this.DRIVER_BASE_URL = DRIVER_BASE_URL;
        this.DRIVER_DISPATCH_CANDIDATE = DRIVER_DISPATCH_CANDIDATE;
    }

    @Override
    public List<UUID> callDrivers(CandidateDriverRequest request) {
        var res = RestClient.create(DRIVER_BASE_URL)
                .post()
                .uri(DRIVER_DISPATCH_CANDIDATE)
                .body(request)
                .retrieve()
                .toEntity(List.class);

        if (res.getStatusCode().is2xxSuccessful()) {
            return res.getBody();
        } else {
            throw new AppException(ErrorCode.VALIDATION, "DRIVER-CANDIDATE 조회 실패");
        }
    }
}
