package com.taxidispatcher.modules.dispatcher.application.service;

import com.taxidispatcher.modules.dispatcher.adapter.client.dto.DriverActiveStatusRequest;
import com.taxidispatcher.modules.dispatcher.application.port.in.DispatchDrivingArrivalAdapter;
import com.taxidispatcher.modules.dispatcher.application.port.in.DispatchDrivingArrivalCommand;
import com.taxidispatcher.modules.dispatcher.application.port.out.DispatchCandidateDriverRepository;
import com.taxidispatcher.modules.dispatcher.application.port.out.DispatchRepository;
import com.taxidispatcher.modules.dispatcher.application.port.out.UpdateDriverActiveStatusClient;
import com.taxidispatcher.modules.dispatcher.domain.model.CandidateStatus;
import com.taxidispatcher.modules.dispatcher.domain.model.DispatchStatus;
import com.taxidispatcher.shared.core.AppException;
import com.taxidispatcher.shared.core.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.Instant;

@Service
@RequiredArgsConstructor
public class DispatchDrivingArrivalService implements DispatchDrivingArrivalAdapter {
    private final DispatchRepository dispatchRepository;
    private final DispatchCandidateDriverRepository dispatchCandidateDriverRepository;
    private final UpdateDriverActiveStatusClient updateDriverActiveStatusClient;
    private final Clock clock = Clock.systemUTC();

    @Override
    public void handle(DispatchDrivingArrivalCommand command) {
        var dispatch = dispatchRepository.findById(command.dispatchId())
                .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND, "배차 정보 확인 불가."));

        if (dispatch.getStatus() != DispatchStatus.DRIVING) {
            // 운행중인 배차 정보만 가능.
            throw new AppException(ErrorCode.VALIDATION, "운행 중인 배차 정보가 아닙니다.");
        } else if (!dispatch.getDriverId().equals(command.driverId())) {
            throw new AppException(ErrorCode.CONFLICT, "해당 배차는 요청 기사의 담당건이 아닙니다.");
        }

        var candidateDriver = dispatchCandidateDriverRepository.findByDriver(command.dispatchId(), command.driverId())
                .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND, "배차 기사 정보 확인 불가."));

        if (candidateDriver.getStatus() != CandidateStatus.APPROVAL) {
            throw new AppException(ErrorCode.VALIDATION, "기사가 승인하지 않은 배차입니다.");
        }

        // 기사 상태 업데이트
        updateDriverActiveStatusClient.handle(DriverActiveStatusRequest.waiting(), dispatch.getDriverId().toString());

        dispatch.updateStatus(DispatchStatus.ARRIVAL, Instant.now(clock));
        dispatchRepository.save(dispatch);
    }
}
