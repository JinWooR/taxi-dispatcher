package com.taxidispatcher.modules.dispatcher.application.service;

import com.taxidispatcher.modules.dispatcher.adapter.web.dto.response.DispatchListResponse;
import com.taxidispatcher.modules.dispatcher.application.port.in.ViewDispatchListUseCase;
import com.taxidispatcher.modules.dispatcher.application.port.out.DispatchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ViewDispatchListService implements ViewDispatchListUseCase {
    private final DispatchRepository dispatchRepository;

    @Override
    public List<DispatchListResponse> handle(ViewDispatchListCommand command) {
        var dispatches = dispatchRepository.findByUserId(command.userId());

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
