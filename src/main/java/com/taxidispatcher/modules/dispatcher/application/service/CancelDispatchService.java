package com.taxidispatcher.modules.dispatcher.application.service;

import com.taxidispatcher.modules.dispatcher.application.port.in.CancelDispatchAdapter;
import com.taxidispatcher.modules.dispatcher.application.port.in.CancelDispatchCommand;
import com.taxidispatcher.modules.dispatcher.application.port.out.DispatchRepository;
import com.taxidispatcher.modules.dispatcher.application.port.out.StopFindDispatchCandidateDriverEventPublisher;
import com.taxidispatcher.modules.dispatcher.domain.event.StopFindDispatchCandidateDriverEvent;
import com.taxidispatcher.modules.dispatcher.domain.model.DispatchStatus;
import com.taxidispatcher.shared.core.AppException;
import com.taxidispatcher.shared.core.ErrorCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.Instant;

@Service
@RequiredArgsConstructor
@Transactional
public class CancelDispatchService implements CancelDispatchAdapter {
    private final DispatchRepository dispatchRepository;
    private final Clock clock = Clock.systemUTC();
    private final StopFindDispatchCandidateDriverEventPublisher eventPublisher;

    @Override
    public void handle(CancelDispatchCommand command) {
        var dispatch = dispatchRepository.findOneIdAndStatus(command.dispatchId(), DispatchStatus.REQUEST)
                .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND, "요청 중인 배차 정보가 없습니다."));

        if (!dispatch.getStatus().equals(DispatchStatus.REQUEST)) {
            // 배차 상태값이 '요청중(REQUEST)'이 아니므로 취소 불가능
            throw new AppException(ErrorCode.VALIDATION, "현재 배차 요청을 취소할 수 없습니다.");
        }

        dispatch.updateStatus(DispatchStatus.CANCEL, Instant.now(clock));
        dispatchRepository.save(dispatch);

        // 이벤트 발행
        eventPublisher.publish(new StopFindDispatchCandidateDriverEvent(command.dispatchId()));
    }
}
