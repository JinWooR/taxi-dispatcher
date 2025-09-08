package com.taxidispatcher.modules.dispatcher.application.service;

import com.taxidispatcher.modules.dispatcher.application.port.in.StopFindDispatchCandidateDriverAdapter;
import com.taxidispatcher.modules.dispatcher.application.port.out.DispatchCandidateDriverRepository;
import com.taxidispatcher.modules.dispatcher.application.port.out.DispatchRepository;
import com.taxidispatcher.modules.dispatcher.domain.model.CandidateStatus;
import com.taxidispatcher.modules.dispatcher.domain.model.DispatchId;
import com.taxidispatcher.modules.dispatcher.domain.model.DispatchStatus;
import com.taxidispatcher.shared.core.AppException;
import com.taxidispatcher.shared.core.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StopFindDispatchCandidateDriverService implements StopFindDispatchCandidateDriverAdapter {
    private final DispatchRepository dispatchRepository;
    private final DispatchCandidateDriverRepository dispatchCandidateDriverRepository;

    @Override
    public void handle(DispatchId dispatchId) {
        // 후보 조회 중단 요청
        var dispatch = dispatchRepository.findById(dispatchId)
                .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND, "배차 정보가 없습니다."));

        if (!dispatch.getStatus().equals(DispatchStatus.CANCEL)) {
            throw new AppException(ErrorCode.VALIDATION, "배차 요청이 취소 상태가 아닙니다.");
        }

        // 알림이 발송된 후보 기사들에게 배차 요청 취소됨을 알림
        var candidateDrivers = dispatchCandidateDriverRepository.findByDriversAndStatus(dispatchId, CandidateStatus.REQUEST);

        if (!candidateDrivers.isEmpty()) {
            // TODO. 배차을 수신한 후보 기사들에게 해당 배차가 취소 알림 발송
        }

        candidateDrivers.forEach(candidateDriver -> candidateDriver.updateStatus(CandidateStatus.CANCEL));

        // 후보 기사 배차 취소 처리
        dispatchCandidateDriverRepository.saveAll(candidateDrivers);
    }
}
