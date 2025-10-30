package com.taxidispatcher.modules.dispatcher.application.service;

import com.taxidispatcher.modules.dispatcher.adapter.web.dto.response.DispatchListResponse;
import com.taxidispatcher.modules.dispatcher.application.port.in.ViewDispatchListUseCase;
import com.taxidispatcher.modules.dispatcher.application.port.out.DispatchRepository;
import com.taxidispatcher.modules.dispatcher.domain.aggregate.Dispatch;
import com.taxidispatcher.modules.dispatcher.domain.model.DispatchStatus;
import com.taxidispatcher.shared.core.AppException;
import com.taxidispatcher.shared.core.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ViewDispatchListService implements ViewDispatchListUseCase {
    private final DispatchRepository dispatchRepository;

    private final List<DispatchStatus> statusList = List.of(
            DispatchStatus.DISPATCHED, DispatchStatus.DRIVING, DispatchStatus.ARRIVAL, DispatchStatus.COMPLETE
    );

    @Override
    public List<DispatchListResponse> handle(ViewDispatchListCommand command) {
        List<Dispatch> dispatches;
        if (command.userId() != null) {
            dispatches = dispatchRepository.findByUserId(command.userId());
        } else if (command.driverId() != null) {
            dispatches = dispatchRepository.findByDriverIdAndStatusIn(command.driverId(), statusList);
        } else {
            throw new AppException(ErrorCode.VALIDATION, "사용자 또는 기사 정보 조회 불가능.");
        }

        return dispatches.stream()
                .map(dispatch -> {
                    // 출발지 정보
                    var startAddress = Optional.ofNullable(dispatch.getStartAddr())
                            .map(addr -> new DispatchListResponse.AddressGeo(addr.addressName(), addr.x(), addr.y()))
                            .orElse(null);

                    // 목적지 정보
                    var arrivalAddr = Optional.ofNullable(dispatch.getArrivalAddr())
                            .map(addr -> new DispatchListResponse.AddressGeo(addr.addressName(), addr.x(), addr.y()))
                            .orElse(null);

                    return new DispatchListResponse(
                            dispatch.getDriverId(),
                            dispatch.getStatus(),
                            startAddress,
                            arrivalAddr,
                            dispatch.getRequestDate(), dispatch.getCanceledDate(),
                            dispatch.getFailedDate(), dispatch.getDispatchedDate(),
                            dispatch.getStartedDate(), dispatch.getArrivedDate(),
                            dispatch.getCompletedDate()
                    );
                })
                .toList();
    }
}
