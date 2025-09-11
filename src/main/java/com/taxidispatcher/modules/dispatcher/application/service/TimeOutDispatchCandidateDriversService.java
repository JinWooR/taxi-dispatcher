package com.taxidispatcher.modules.dispatcher.application.service;

import com.taxidispatcher.modules.dispatcher.application.port.in.TimeOutDispatchCandidateDriversAdapter;
import com.taxidispatcher.modules.dispatcher.application.port.out.DispatchCandidateDriverRepository;
import com.taxidispatcher.modules.dispatcher.domain.model.CandidateStatus;
import com.taxidispatcher.modules.dispatcher.domain.model.DispatchId;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class TimeOutDispatchCandidateDriversService implements TimeOutDispatchCandidateDriversAdapter {
    private final DispatchCandidateDriverRepository dispatchCandidateDriverRepository;

    @Override
    public void handle(DispatchId dispatchId) {
        var drivers = dispatchCandidateDriverRepository.findByDriversAndStatus(dispatchId, CandidateStatus.REQUEST)
                .stream().peek(candidateDriver -> candidateDriver.updateStatus(CandidateStatus.TIME_OUT))
                .toList();

        dispatchCandidateDriverRepository.saveAll(drivers);
        
        // TODO. 배차 시간 초과 알림 발송
    }
}
