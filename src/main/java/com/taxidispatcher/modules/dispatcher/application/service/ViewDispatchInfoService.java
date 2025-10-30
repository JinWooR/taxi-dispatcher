package com.taxidispatcher.modules.dispatcher.application.service;

import com.taxidispatcher.modules.dispatcher.adapter.web.dto.response.DispatchInfoResponse;
import com.taxidispatcher.modules.dispatcher.application.port.in.ViewDispatchInfoAdapter;
import com.taxidispatcher.modules.dispatcher.application.port.in.ViewDispatchInfoCommand;
import com.taxidispatcher.modules.dispatcher.application.port.out.DispatchGeoHistoryRepository;
import com.taxidispatcher.modules.dispatcher.application.port.out.DispatchRepository;
import com.taxidispatcher.modules.dispatcher.application.port.out.SearchDriverInfoClient;
import com.taxidispatcher.shared.core.AppException;
import com.taxidispatcher.shared.core.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ViewDispatchInfoService implements ViewDispatchInfoAdapter {
    private final DispatchRepository dispatchRepository;
    private final DispatchGeoHistoryRepository dispatchGeoHistoryRepository;
    private final SearchDriverInfoClient searchDriverInfoClient;

    @Override
    public DispatchInfoResponse handle(ViewDispatchInfoCommand command) {
        var dispatch = dispatchRepository.findById(command.dispatchId())
                .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND, "배차 정보 조회 실패"));

        if (!dispatch.getUserId().equals(command.userId())) {
            throw new AppException(ErrorCode.FORBIDDEN, "배차 정보 접근 불가");
        }

        // 기사 정보 조회 (외부 API 호출)
        var driverInfo = Optional.ofNullable(dispatch.getDriverId())
                .map(searchDriverInfoClient::handle)
                .map(d -> new DispatchInfoResponse.Driver(d.driverId(), d.name(), d.taxiNumber(), d.textSize(), d.color()))
                .orElse(null);

        // 출발지 정보
        var startAddress = Optional.ofNullable(dispatch.getStartAddr())
                .map(addr -> new DispatchInfoResponse.AddressGeo(addr.addressName(), addr.x(), addr.y()))
                .orElse(null);

        // 목적지 정보
        var arrivalAddr = Optional.ofNullable(dispatch.getArrivalAddr())
                .map(addr -> new DispatchInfoResponse.AddressGeo(addr.addressName(), addr.x(), addr.y()))
                .orElse(null);

        // 배차 이동 경로
        var geoHistories = dispatchGeoHistoryRepository.findByDispatchId(command.dispatchId().id()).stream()
                .map(geoHistory -> new DispatchInfoResponse.GeoHistory(geoHistory.getId().seq(), geoHistory.getLat(), geoHistory.getLng(), geoHistory.getDeviceTs()))
                .toList();

        return new DispatchInfoResponse(
                command.dispatchId().id(),
                driverInfo,
                dispatch.getStatus(),
                startAddress,
                arrivalAddr,
                dispatch.getRequestDate(), dispatch.getCanceledDate(),
                dispatch.getFailedDate(), dispatch.getDispatchedDate(),
                dispatch.getStartedDate(), dispatch.getArrivedDate(),
                dispatch.getCompletedDate(),
                geoHistories
        );
    }
}
