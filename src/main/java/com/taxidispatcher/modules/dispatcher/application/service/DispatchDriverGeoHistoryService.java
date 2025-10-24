package com.taxidispatcher.modules.dispatcher.application.service;

import com.taxidispatcher.modules.dispatcher.application.port.in.DispatchDriverGeoHistoryAdapter;
import com.taxidispatcher.modules.dispatcher.application.port.in.DispatchDriverGeoHistoryCommand;
import com.taxidispatcher.modules.dispatcher.application.port.out.DispatchGeoHistoryRepository;
import com.taxidispatcher.modules.dispatcher.application.port.out.DispatchRepository;
import com.taxidispatcher.modules.dispatcher.domain.aggregate.DispatchDriverGeoHistory;
import com.taxidispatcher.modules.dispatcher.domain.model.DispatchDriverGeoHistoryId;
import com.taxidispatcher.modules.dispatcher.domain.model.DispatchStatus;
import com.taxidispatcher.shared.core.AppException;
import com.taxidispatcher.shared.core.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class DispatchDriverGeoHistoryService implements DispatchDriverGeoHistoryAdapter {
    private final DispatchRepository dispatchRepository;
    private final DispatchGeoHistoryRepository dispatchGeoHistoryRepository;

    @Override
    public void handle(DispatchDriverGeoHistoryCommand command) {
        var dispatch = dispatchRepository.findOneByDriverIdAndStatus(command.driverId(), DispatchStatus.DRIVING)
                .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND, Map.of("dispatchId", command.driverId()), "배차 정보 조회 실패."));

        var id = new DispatchDriverGeoHistoryId(dispatch.getId(), command.seq());
        var domain = DispatchDriverGeoHistory.of(id, command.lat(), command.lng(), command.deviceTs());

        dispatchGeoHistoryRepository.save(domain);
    }
}
