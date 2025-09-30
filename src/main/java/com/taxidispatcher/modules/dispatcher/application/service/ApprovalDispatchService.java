package com.taxidispatcher.modules.dispatcher.application.service;

import com.taxidispatcher.modules.dispatcher.application.port.in.ApprovalDispatchAdapter;
import com.taxidispatcher.modules.dispatcher.application.port.in.ApprovalDispatchCommand;
import com.taxidispatcher.modules.dispatcher.application.port.out.ApprovalDispatchEventPublisher;
import com.taxidispatcher.modules.dispatcher.application.port.out.DispatchCandidateDriverRepository;
import com.taxidispatcher.modules.dispatcher.application.port.out.DispatchRepository;
import com.taxidispatcher.modules.dispatcher.domain.event.ApprovalDispatchEvent;
import com.taxidispatcher.modules.dispatcher.domain.model.CandidateStatus;
import com.taxidispatcher.modules.dispatcher.domain.model.DispatchStatus;
import com.taxidispatcher.shared.core.AppException;
import com.taxidispatcher.shared.core.ErrorCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.Instant;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional
public class ApprovalDispatchService implements ApprovalDispatchAdapter {
    private final DispatchRepository dispatchRepository;
    private final DispatchCandidateDriverRepository dispatchCandidateDriverRepository;
    private final ApprovalDispatchEventPublisher eventPublisher;
    private final Clock clock = Clock.systemUTC();

    @Override
    public void handle(ApprovalDispatchCommand command) {
        // 배차 정보 조회 및 업데이트
        var dispatch = dispatchRepository.findById(command.dispatchId())
                .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND, "배차 정보를 확인할 수 없습니다."));

        if (dispatch.getDriverId() != null) {
            switch (dispatch.getStatus()) {
                case REQUEST -> {}
                case DISPATCHED, DRIVING, ARRIVAL, COMPLETE -> throw new AppException(ErrorCode.CONFLICT, "이미 배차가 완료되었습니다.");
                case CANCEL, FAILED -> throw new AppException(ErrorCode.CONFLICT, "이미 취소된 배차 정보입니다");
                default -> {
                    Map<String, Object> errParams = Map.of(
                            "dispatchId", command.dispatchId().id(),
                            "status", dispatch.getStatus()
                    );
                    throw new AppException(ErrorCode.INTERNAL, errParams, "배차 상태 정보 오류.");
                }
            }
        }
        dispatch.updateDriver(command.driverId());
        dispatch.updateStatus(DispatchStatus.DISPATCHED, Instant.now(clock));
        dispatchRepository.save(dispatch);

        // 배차 후보 기사 상태 업데이트
        var candidateDriver = dispatchCandidateDriverRepository.findByDriver(command.dispatchId(), command.driverId())
                .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND, "해당 배차 후보 기사가 아닙니다."));

        candidateDriver.updateStatus(CandidateStatus.APPROVAL);
        dispatchCandidateDriverRepository.save(candidateDriver);

        // (외부) 기사 상태 업데이트

        // 타 배차 후보 기사들 배차 만료 처리 및 알림 발송
        eventPublisher.handle(new ApprovalDispatchEvent(command.dispatchId()));
    }
}
