package com.taxidispatcher.modules.dispatcher.application.service;

import com.taxidispatcher.modules.dispatcher.application.port.in.RefusalDispatchAdapter;
import com.taxidispatcher.modules.dispatcher.application.port.in.RefusalDispatchCommand;
import com.taxidispatcher.modules.dispatcher.application.port.out.DispatchCandidateDriverRepository;
import com.taxidispatcher.modules.dispatcher.application.port.out.DispatchRepository;
import com.taxidispatcher.modules.dispatcher.domain.model.CandidateStatus;
import com.taxidispatcher.shared.core.AppException;
import com.taxidispatcher.shared.core.ErrorCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class RefusalDispatchService implements RefusalDispatchAdapter {
    private final DispatchRepository dispatchRepository;
    private final DispatchCandidateDriverRepository dispatchCandidateDriverRepository;

    @Override
    public void handle(RefusalDispatchCommand command) {
        if (!dispatchRepository.existsById(command.dispatchId())) {
            throw new AppException(ErrorCode.NOT_FOUND, "배차 요청 정보를 확인할 수 없습니다.");
        }

        var candidateDriver = dispatchCandidateDriverRepository.findByDriver(command.dispatchId(), command.driverId())
                .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND, "기사 정보를 확인할 수 없습니다."));

        candidateDriver.updateStatus(CandidateStatus.REJECT);
        dispatchCandidateDriverRepository.save(candidateDriver);
    }
}
